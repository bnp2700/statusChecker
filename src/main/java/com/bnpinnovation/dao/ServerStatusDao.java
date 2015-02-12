package com.bnpinnovation.dao;

import java.util.List;

import com.bnpinnovation.domain.CheckingServer;
import com.bnpinnovation.domain.ServerStatus;

public interface ServerStatusDao {

	List<CheckingServer> queryServerListToCheck();

	void addServerStatus(ServerStatus status);

}
