package com.bnpinnovation.service.consumer;

import java.util.concurrent.BlockingQueue;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.bnpinnovation.dao.ServerStatusDao;
import com.bnpinnovation.domain.ServerStatus;
import com.bnpinnovation.exception.StatusCheckerException;
import com.bnpinnovation.service.alive.StatusCheckerForServer;
import com.bnpinnovation.service.manager.StatusManager;

@Component
public class StatusConsumer implements Consumer{
	private static Logger logger = Logger.getLogger(StatusConsumer.class);

	@Resource
	private BlockingQueue<ServerStatus> serverStatusQueue;
	@Resource
	private TaskExecutor statusManageExecutor;
	
	@Autowired
	private StatusCheckerForServer statusChecker;
	
	@Autowired
	private StatusManager statusManager;
	
	@Override
	public void initConsumer() {
		statusManageExecutor.execute(new Runnable() {
			
			@Override
			public void run() {
				jobLooper();
				
			}
		});
	}
	
	private void jobLooper() {
		logger.info("server Checking Joblooper start");
		while (true) {
			try {
				ServerStatus status = serverStatusQueue.take();
				statusManager.add(status);
			} catch (Exception e) {
				throw new StatusCheckerException(StatusCheckerException.statusTakeErrorCode);
			}
		}
	}


}