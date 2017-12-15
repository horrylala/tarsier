package com.sf.tarsier.mvc.system.filter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 类名:HttpServletRequestTrimParamWrapper 功能: 去掉提交数据的前后空格，提高用户界面容错度
 * 如果要管理session，或session实现共享就用HttpSessionWrapper
 */
public class HttpServletRequestTrimParamWrapper extends HttpServletRequestWrapper {

	public HttpServletRequestTrimParamWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		return trim(super.getParameter(name));
	}

	@Override
	public String[] getParameterValues(String name) {
		return trim(super.getParameterValues(name));
	}

	private ParameterMap2 pm2;

	@Override
	public Map<String, String[]> getParameterMap() {
		if (pm2 == null) {
			pm2 = new ParameterMap2(super.getParameterMap());
		}
		return pm2;
	}

	@SuppressWarnings("serial")
	private class ParameterMap2 extends HashMap<String, String[]> {
		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			if (!super.equals(o)) {
				return false;
			}

			ParameterMap2 that = (ParameterMap2) o;

			return entryHashSet != null ? entryHashSet.equals(that.entryHashSet) : that.entryHashSet == null;
		}

		@Override
		public int hashCode() {
			int result = super.hashCode();
			result = 31 * result + (entryHashSet != null ? entryHashSet.hashCode() : 0);
			return result;
		}

		private transient Set<Map.Entry<String, String[]>> entryHashSet;

		/**
		 * 若要构造此类对象，则需要传入一个map参数，该map对应的客户端请求的参数(K,V)。
		 * 
		 * @param map
		 *            映射客户端参数。
		 */
		public ParameterMap2(Map<String, String[]> map) {
			super(map);
		}

		@Override
		public Set<java.util.Map.Entry<String, String[]>> entrySet() {
			if (entryHashSet == null) {
				entryHashSet = new HashSet<>();
				Set<Map.Entry<String, String[]>> temSet = super.entrySet();
				for (Iterator<Map.Entry<String, String[]>> iterator = temSet.iterator(); iterator.hasNext();) {
					Map.Entry<String, String[]> me = iterator.next();
					Entry2 entry = new Entry2(me);
					entryHashSet.add(entry);
				}
			}
			return entryHashSet;
		}

		// 若直接从map使用key取得
		@SuppressWarnings("unused")
		public String[] get(String key) {
			String[] value = super.get(key);

			if (value != null) {
				return trim(value);
			}
			return new String[]{};
		}
	}

	private class Entry2 implements Map.Entry<String, String[]> {
		private Map.Entry<String, String[]> me;

		private boolean isTrim = true;

		public Entry2(Map.Entry<String, String[]> me) {
			if (me == null) {
				throw new IllegalArgumentException("Map.Entiry argument not null.");
			}
			this.me = me;
		}

		@Override
		public String getKey() {
			return me.getKey();
		}

		@Override
		public String[] getValue() {
			if (isTrim) {
				return HttpServletRequestTrimParamWrapper.this.trim(me.getValue());
			}
			return me.getValue();
		}

		@Override
		public String[] setValue(String[] value) {
			return me.setValue(value);
		}

	}

	/**
	 * 去除一个Object类型对应的前后空格，因为客户端提交参数有两种，一种：String，另一种：String[]，此方法会自动判断调用哪个方法。
	 * 
	 * @param value
	 *            需要处理的参数。
	 * @return 处理后的值。
	 */
	protected Object trim(Object value) {
		if (value instanceof String[]) {
			return trim((String[]) value);
		}
		return trim(value.toString());
	}

	/**
	 * 去除某个字符串的前后空格。
	 * 
	 * @param value
	 *            需要处理的参数。
	 * @return 处理后的值。
	 */
	protected String trim(String value) {
		if (value != null && value.length() > 0) {
			return value.trim();
		}
		return value;
	}

	/**
	 * 去除某个数组中所有的值的前后空格。
	 * 
	 * @param values
	 *            需要处理的数组。
	 * @return 处理后的值，当数组的length为1时，则返回一个String，反之返回一个数组。
	 */
	protected String[] trim(String[] values) {
		if (values != null && values.length > 0) {
			int len = values.length;
			for (int i = 0; i < len; i++) {
				values[i] = trim(values[i]);
			}
		}
		return values;
	}


}
