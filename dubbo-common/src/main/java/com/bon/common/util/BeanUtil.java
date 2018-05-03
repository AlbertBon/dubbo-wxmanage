package com.bon.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanUtil {

	/**
	 * 参数列表转换
	 * 
	 * @author: bingye
	 * @createTime: 2015-6-26 下午04:31:37
	 * @param excludeParamNames
	 * @return List<String>
	 */
	public static List<String> paramList(String... excludeParamNames) {
		List<String> exParams = new ArrayList<String>();
		if (excludeParamNames != null && excludeParamNames.length > 0) {
			for (String excludeParamName : excludeParamNames) {
				exParams.add(excludeParamName);
			}
		}
		return exParams;
	}

	/**
	 * 相同属性值拷贝，不相同的属性保留原值
	 * 
	 * @author: bingye
	 * @createTime: 2014-11-8 上午10:33:59
	 * @param <S>
	 * @param <D>
	 * @param s
	 *            源对象
	 * @param d
	 *            目标对象
	 * @return D 目标对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <S, D> D copyPropertys(S s, D d) {
		if (s == null || d == null) {
			return d;
		}
		Field[] sfields = s.getClass().getDeclaredFields();
		Field[] dfields = d.getClass().getDeclaredFields();
		Class scls = s.getClass();
		Class dcls = d.getClass();
		try {
			for (Field sfield : sfields) {
				String sName = sfield.getName();
				if ("serialVersionUID".equals(sName)) {
					continue;
				}
				Class sType = sfield.getType();
				String sfieldName = sName.substring(0, 1).toUpperCase() + sName.substring(1);
				Method sGetMethod = scls.getMethod("get" + sfieldName);
				Object value = sGetMethod.invoke(s);
				for (Field dfield : dfields) {
					String dName = dfield.getName();
					Class dType = dfield.getType();
					if (dName.equals(sName) && sType.toString().equals(dType.toString())) {
						Method dSetMethod = dcls.getMethod("set" + sfieldName, sType);
						dSetMethod.invoke(d, value);
						break;
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		}
		return d;
	}

	/**
	 * 指定参数的相同属性值拷贝，其他的拷贝
	 * 
	 * @author: bingye
	 * @createTime: 2014-11-8 上午10:33:59
	 * @param <S>
	 * @param <D>
	 * @param s
	 *            源对象
	 * @param d
	 *            目标对象
	 * @return D 目标对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <S, D> D copyPropertysByIncParams(S s, D d, String... params) {
		if (s == null || d == null) {
			return d;
		}

		// 特定参数列表
		List<String> paramList = paramList(params);

		Field[] sfields = s.getClass().getDeclaredFields();
		Field[] dfields = d.getClass().getDeclaredFields();
		Class scls = s.getClass();
		Class dcls = d.getClass();
		try {
			for (Field sfield : sfields) {
				// 源：属性
				String sName = sfield.getName();
				// 源：属性类型
				Class sType = sfield.getType();
				String sfieldName = sName.substring(0, 1).toUpperCase() + sName.substring(1);
				Method sGetMethod = scls.getMethod("get" + sfieldName);
				// 源：值
				Object value = sGetMethod.invoke(s);

				for (Field dfield : dfields) {
					// 目
					String dName = dfield.getName();
					Class dType = dfield.getType();

					if (dName.equals(sName) && sType.toString().equals(dType.toString())) {
						if (paramList != null && paramList.contains(sName)) {
							Method dSetMethod = dcls.getMethod("set" + sfieldName, sType);
							dSetMethod.invoke(d, value);
							continue;
						}
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		}
		return d;
	}

	/**
	 * 指定参数的相同属性值不拷贝，其他的相同属性值拷贝
	 * 
	 * @author: bingye
	 * @createTime: 2014-11-8 上午10:33:59
	 * @param <S>
	 * @param <D>
	 * @param s
	 *            源对象
	 * @param d
	 *            目标对象
	 * @return D 目标对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <S, D> D copyPropertysByExcParams(S s, D d, String... params) {
		if (s == null || d == null) {
			return d;
		}

		// 特定参数列表
		List<String> paramList = paramList(params);

		Field[] sfields = s.getClass().getDeclaredFields();
		Field[] dfields = d.getClass().getDeclaredFields();
		Class scls = s.getClass();
		Class dcls = d.getClass();
		try {
			for (Field sfield : sfields) {
				// 源：属性
				String sName = sfield.getName();
				// 源：属性类型
				Class sType = sfield.getType();
				String sfieldName = sName.substring(0, 1).toUpperCase() + sName.substring(1);
				Method sGetMethod = scls.getMethod("get" + sfieldName);
				// 源：值
				Object value = sGetMethod.invoke(s);

				for (Field dfield : dfields) {
					// 目
					String dName = dfield.getName();
					Class dType = dfield.getType();

					if (dName.equals(sName) && sType.toString().equals(dType.toString())) {
						if (paramList != null && paramList.contains(sName)) {
							break;
						}
						Method dSetMethod = dcls.getMethod("set" + sfieldName, sType);
						dSetMethod.invoke(d, value);
						continue;
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		}
		return d;
	}
}