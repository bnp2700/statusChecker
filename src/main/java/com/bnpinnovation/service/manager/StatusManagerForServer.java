package com.bnpinnovation.service.manager;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bnpinnovation.dao.ServerStatusDao;
import com.bnpinnovation.domain.ServerStatus;

@Component
public class StatusManagerForServer implements StatusManager {

	private Logger logger =Logger.getLogger(StatusManagerForServer.class);
	@Autowired
	private ServerStatusDao serverStatusDao;
	
	private static Map<String, ServerStatus> statusRepository = new HashMap<String, ServerStatus>();   
	
	@Override
	public synchronized void add(ServerStatus status) {
		boolean isPersist = manageStatus(status);
		processingStatus(status, isPersist);
	}

	private void processingStatus(ServerStatus status, boolean isPersist) {
		if(isPersist) {
			serverStatusDao.addServerStatus(status);
		}
	}

	private boolean manageStatus(ServerStatus newStatus) {
		boolean isPersist = false;
		if( statusRepository.containsKey(newStatus.getName()) ) {
			ServerStatus status = statusRepository.get(newStatus.getName());
			if(status.getServerStatus() != newStatus.getServerStatus()) {
				statusRepository.put(newStatus.getName(), newStatus);
				isPersist = true;
				logger.info("manager-change server status : " + newStatus.getServerStatus());
			}
		} else {
			statusRepository.put(newStatus.getName(), newStatus);
			isPersist = true;
			logger.info("manager-new server status : " + newStatus.getServerStatus());
		}
		
		return isPersist;
	}

}
