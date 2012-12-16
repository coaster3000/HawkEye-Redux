/*
 * GNU.FREE 2002
 *
 * Copyright (c) 1999, 2000, 2001, 2002
 * The Free Software Foundation (www.fsf.org)
 *
 * GNU.FREE Co-ordinator: Jason Kitcat <jeep@free-project.org>
 *
 * GNU site: http://www.gnu.org/software/free/
 *
 * FREE e-democracy site: http://www.free-project.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program (COPYING); if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

package org.cubeville.hawkeye.sql;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;

/**
 * This class represents a JDBC connection in the pool and is essentially a
 * wrapper around a real connection.
 *
 * Code originates from Java Developer Connection.
 *
 * @version 0.2  28 August 2001
 * @author Jason Kitcat
 */
public class JDCConnection implements Connection {

	private final ConnectionManager pool;
	private final Connection conn;
	private boolean inuse;
	private long timestamp;

	public JDCConnection(Connection conn, ConnectionManager pool) {
		this.conn = conn;
		this.pool = pool;
		inuse = false;
		timestamp = 0;
	}

	/**
	 * Gets the underlying connection object
	 *
	 * @return Database connection
	 */
	protected Connection getConnection() {
		return conn;
	}

	/**
	 * Leases this connection
	 *
	 * @return True if connection was leased, false if already leased
	 */
	public synchronized boolean lease() {
		if (inuse) {
			return false;
		} else {
			inuse = true;
			timestamp = System.currentTimeMillis();
			return true;
		}
	}

	/**
	 * Expires this connection's current lease
	 */
	protected void expireLease() {
		inuse = false;
	}

	/**
	 * Returns whether or not this connection is currently leased (in use)
	 *
	 * @return True if connection is in use, false if not
	 */
	public boolean inUse() {
		return inuse;
	}

	/**
	 * Returns when this connection was last leased
	 *
	 * @return Connection's last lease time
	 */
	public long getLastUse() {
		return timestamp;
	}

	/**
	 * Checks if this connection is still valid
	 *
	 * @return True if connection is valid, false if not
	 */
	public boolean validate() {
		try {
			conn.getMetaData();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Expires the lease on this connection
	 */
	@Override
	public void close() throws SQLException {
		pool.returnConnection(this);
	}

	@Override
	public void clearWarnings() throws SQLException {
		conn.clearWarnings();
	}

	@Override
	public void commit() throws SQLException {
		conn.commit();
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		return conn.createArrayOf(typeName, elements);
	}

	@Override
	public Blob createBlob() throws SQLException {
		return conn.createBlob();
	}

	@Override
	public Clob createClob() throws SQLException {
		return conn.createClob();
	}

	@Override
	public NClob createNClob() throws SQLException {
		return conn.createNClob();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		return conn.createSQLXML();
	}

	@Override
	public Statement createStatement() throws SQLException {
		return conn.createStatement();
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return conn.createStatement(resultSetType, resultSetConcurrency);
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		return conn.createStruct(typeName, attributes);
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		return conn.getAutoCommit();
	}

	@Override
	public String getCatalog() throws SQLException {
		return conn.getCatalog();
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		return conn.getClientInfo();
	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		return conn.getClientInfo(name);
	}

	@Override
	public int getHoldability() throws SQLException {
		return conn.getHoldability();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return conn.getMetaData();
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		return conn.getTransactionIsolation();
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return conn.getTypeMap();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return conn.getWarnings();
	}

	@Override
	public boolean isClosed() throws SQLException {
		return conn.isClosed();
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return conn.isReadOnly();
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		return conn.isValid(timeout);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return conn.isWrapperFor(iface);
	}

	@Override
	public String nativeSQL(String sql) throws SQLException {
		return conn.nativeSQL(sql);
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		return conn.prepareCall(sql);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return conn.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		return conn.prepareStatement(sql, autoGeneratedKeys);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return conn.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		return conn.prepareStatement(sql, columnIndexes);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		return conn.prepareStatement(sql, columnNames);
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		conn.releaseSavepoint(savepoint);
	}

	@Override
	public void rollback() throws SQLException {
		conn.rollback();
	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		conn.rollback(savepoint);
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		conn.setAutoCommit(autoCommit);
	}

	@Override
	public void setCatalog(String catalog) throws SQLException {
		conn.setCatalog(catalog);
	}

	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		conn.setClientInfo(properties);
	}

	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		conn.setClientInfo(name, value);
	}

	@Override
	public void setHoldability(int holdability) throws SQLException {
		conn.setHoldability(holdability);
	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		conn.setReadOnly(readOnly);
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		return conn.setSavepoint();
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		return conn.setSavepoint(name);
	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		conn.setTransactionIsolation(level);
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		conn.setTypeMap(map);
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return conn.unwrap(iface);
	}

}