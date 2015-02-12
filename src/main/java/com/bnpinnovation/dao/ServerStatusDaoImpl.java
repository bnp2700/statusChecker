package com.bnpinnovation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.bnpinnovation.domain.CheckingServer;
import com.bnpinnovation.domain.ServerStatus;

@Repository
public class ServerStatusDaoImpl implements ServerStatusDao {
	private Logger logger = Logger.getLogger(ServerStatusDaoImpl.class);
	
	@Resource
	private JdbcTemplate jdbcTemplate;

	private String serverQuery = "select name, ip from management_server";
	private String insertServerStatus = "insert into disorder (name, d_date,icmp_result) values(?,?,?)";

	@Override
	public List<CheckingServer> queryServerListToCheck() {
		return jdbcTemplate.query(serverQuery, new RowMapper<CheckingServer>() {

			@Override
			public CheckingServer mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CheckingServer server = new CheckingServer();
				server.setIp(rs.getString("ip"));
				server.setName(rs.getString("name"));
				return server;
			}
		});
	}


	@Override
	public synchronized void addServerStatus(final ServerStatus status) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(insertServerStatus,Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, status.getName());
				ps.setObject(2, status.getCheckDate());
				ps.setBoolean(3, status.getServerStatus());
				
				return ps;
			}
		}, keyHolder);
		logger.info("dao : "+ status +", key : "+ keyHolder.getKey());
	}

}
