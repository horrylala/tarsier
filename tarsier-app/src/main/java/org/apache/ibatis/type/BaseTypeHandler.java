package org.apache.ibatis.type;

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.apache.ibatis.session.Configuration;

/**
 * 用来替换mybatis的BaseTypeHandler，
 * 解决当使用oracle时，number/string/date为null时，存储报错的问题
 */
public abstract class BaseTypeHandler<T> extends TypeReference<T> implements
		TypeHandler<T> {
	protected Configuration configuration;

	public void setConfiguration(Configuration c) {
		this.configuration = c;
	}

	public void setParameter(PreparedStatement ps, int i, T parameter,
			JdbcType jdbcType) throws SQLException {
		if (parameter == null) {
			if (jdbcType == JdbcType.OTHER) {
				try {
					boolean useSetObject = false;
					int sqlType = Types.NULL;
					try {
						DatabaseMetaData dbmd = ps.getConnection()
								.getMetaData();
						String databaseProductName = dbmd
								.getDatabaseProductName();
						String jdbcDriverName = dbmd.getDriverName();
						if (databaseProductName.startsWith("Informix")
								|| jdbcDriverName
										.startsWith("Microsoft SQL Server")) {
							useSetObject = true;
						} else if (databaseProductName.startsWith("Oracle")
								|| databaseProductName.startsWith("DB2")
								|| jdbcDriverName.startsWith("jConnect")
								|| jdbcDriverName.startsWith("SQLServer")
								|| jdbcDriverName.startsWith("Apache Derby")) {
							sqlType = Types.VARCHAR;
						}
					} catch (Throwable ex) {
						throw new TypeException(
								"Could not check database or driver name", ex);
					}
					if (useSetObject) {
						ps.setObject(i, null);
					} else {
						ps.setNull(i, sqlType);
					}

				} catch (SQLException e) {
					throw new TypeException(
							"Error setting null for parameter #"
									+ i
									+ " with JdbcType "
									+ jdbcType
									+ " . "
									+ "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. "
									+ "Cause: " + e, e);

				}
			} else {
				ps.setNull(i, jdbcType.TYPE_CODE);
			}
		} else {
			setNonNullParameter(ps, i, parameter, jdbcType);
		}
	}

	public T getResult(ResultSet rs, String columnName) throws SQLException {
		T result = getNullableResult(rs, columnName);
		if (rs.wasNull()) {
			return null;
		}
		return result;
	}

	public T getResult(ResultSet rs, int columnIndex) throws SQLException {
		T result = getNullableResult(rs, columnIndex);
		if (rs.wasNull()) {
			return null;
		}
		return result;
	}

	public T getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		T result = getNullableResult(cs, columnIndex);
		if (cs.wasNull()) {
			return null;
		}
		return result;
	}

	public abstract void setNonNullParameter(
			PreparedStatement paramPreparedStatement, int paramInt, T paramT,
			JdbcType paramJdbcType) throws SQLException;

	public abstract T getNullableResult(ResultSet paramResultSet,
			String paramString) throws SQLException;

	public abstract T getNullableResult(ResultSet paramResultSet, int paramInt)
			throws SQLException;

	public abstract T getNullableResult(
			CallableStatement paramCallableStatement, int paramInt)
			throws SQLException;
}