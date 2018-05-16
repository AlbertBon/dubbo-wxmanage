package com.bon.common.service.impl;

import com.bon.common.config.Constants;
import com.bon.common.domain.dto.TokenModel;
import com.bon.common.service.RedisService;
import com.bon.common.service.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: dubbo-wxmanage
 * @description: token管理类
 * @author: Bon
 * @create: 2018-05-15 12:05
 **/
@Service
public class TokenManagerImpl implements TokenManager {

    @Autowired
    private RedisService redisService;

    public TokenModel createToken(long userId) {
        // 使用 uuid 作为源 token
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenModel model = new TokenModel(userId, token);
        // 存储到 redis 并设置过期时间
        redisService.set(String.valueOf(userId),token);
        return model;
    }

    public TokenModel getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String[] param = authentication.split("_");
        if (param.length != 2) {
            return null;
        }
        // 使用 userId 和源 token 简单拼接成的 token，可以增加加密措施
        long userId = Long.parseLong(param[0]);
        String token = param[1];
        return new TokenModel(userId, token);
    }

    public boolean checkToken(TokenModel model) {
        if (model == null) {
            return false;
        }
        String token = redisService.get(String.valueOf(model.getUserId()));
        if (token == null || !token.equals(model.getToken())) {
            return false;
        }
        // 如果验证成功，说明此用户进行了一次有效操作，延长 token 的过期时间
        redisService.expire(String.valueOf(model.getUserId()),Constants.TOKEN_EXPIRES_SECONDS);
        return true;
    }

    public void deleteToken(long userId) {
        redisService.del(String.valueOf(userId));
    }

}
