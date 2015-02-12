package com.bnpinnovation.service.producer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import com.bnpinnovation.dao.ServerStatusDao;
import com.bnpinnovation.domain.CheckingServer;
import com.bnpinnovation.exception.StatusCheckerException;

@Component
public class JobProducer implements Producer {
	private static Logger logger = Logger.getLogger(JobProducer.class);
	
	@Resource
	private TaskScheduler producerScheduler;
	@Autowired 
	private ServerStatusDao serverStatusDao;
	@Resource
	private BlockingQueue<CheckingServer> jobQueue;
	
	@Value("${checker.period:5}")
	private long periodTime;
	
	@Override
	public void launchJobProducer() {
		producerScheduler.schedule(new Runnable() {
			
			@Override
			public void run() {
				List<CheckingServer> serverList = serverStatusDao.queryServerListToCheck();
				String currentTime = new SimpleDateFormat ( "yyyyMMdd HHmmss" ).format(new Date()); 
				logger.info( "producer time : " + currentTime );
				for(CheckingServer checkingServer : serverList ) {
					try {
						jobQueue.put(checkingServer);
					} catch (InterruptedException e) {
						throw new StatusCheckerException(StatusCheckerException.jobProducerErrorCode);
					}
				}
				
			}
		}, new PeriodicTrigger(periodTime*1000));
	}

}
