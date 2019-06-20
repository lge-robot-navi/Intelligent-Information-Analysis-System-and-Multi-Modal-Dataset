package com.lge.mams.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * Y and N Boolean Type Handler
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class YNBooleanTypeHandler extends BaseTypeHandler<Boolean> {

	/**
	 * (non-Javadoc)
	 * @see BaseTypeHandler#setNonNullParameter(PreparedStatement, int, Object, JdbcType)
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			Boolean parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, convert(parameter));
	}

	/**
	 * (non-Javadoc)
	 * @see BaseTypeHandler#getNullableResult(ResultSet, String)
	 */
	@Override
	public Boolean getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		return convert(rs.getString(columnName));
	}

	/**
	 * (non-Javadoc)
	 * @see BaseTypeHandler#getNullableResult(ResultSet, int)
	 */
	@Override
	public Boolean getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		return convert(rs.getString(columnIndex));
	}

	/**
	 * (non-Javadoc)
	 * @see BaseTypeHandler#getNullableResult(CallableStatement, int)
	 */
	@Override
	public Boolean getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return convert(cs.getString(columnIndex));
	}

	/**
	 *
	 * @method convert
	 * @param b
	 * @return
	 */
	private String convert(Boolean b) {
		return b ? "Y" : "N";
	}

	/**
	 *
	 * @method convert
	 * @param s
	 * @return
	 */
	private Boolean convert(String s) {
		return s.equalsIgnoreCase("Y");
	}
}
