package com.bnpinnovation.service.consumer;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.bnpinnovation.domain.CheckingServer;
import com.bnpinnovation.domain.ServerStatus;
import com.bnpinnovation.exception.StatusCheckerException;
import com.bnpinnovation.service.alive.StatusCheckerForServer;

@Component
public class JobConsumer implements Consumer{
	private static Logger logger = Logger.getLogger(JobConsumer.class);
	@Resource
	private BlockingQueue<CheckingServer> jobQueue;
	@Resource
	private BlockingQueue<ServerStatus> serverStatusQueue;
	@Autowired
	private TaskExecutor jobOperatorWithThreadPooledExecutor;
	@Resource
	private TaskExecutor consumerExecutor;
	
	@Autowired
	private StatusCheckerForServer statusChecker;
	
	
	@Override
	public void initConsumer() {
		consumerExecutor.execute(new Runnable() {
			
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
				CheckingServer server = jobQueue.take();
				processingCheckServer(server);
			} catch (Exception e) {
				throw new StatusCheckerException(StatusCheckerException.jobTakeErrorCode);
			}

		}
	}

	private void processingCheckServer(final CheckingServer server) {
		jobOperatorWithThreadPooledExecutor.execute(new Runnable() {

			@Override
			public void run() {
				
				ServerStatus serverStatus = new ServerStatus();
				Date date = new Date();
				boolean status = statusChecker.isAlive(server.getIp());
				serverStatus.setCheckDate(date);
				serverStatus.setName(server.getName());
				serverStatus.setServerStatus(status);
				try {
					serverStatusQueue.put(serverStatus);
				} catch (InterruptedException e) {
					throw new StatusCheckerException(StatusCheckerException.serverStatusRegistErrorCode);
				}
			}
		});
	}

}