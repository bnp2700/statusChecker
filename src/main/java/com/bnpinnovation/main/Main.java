package com.bnpinnovation.main;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bnpinnovation.exception.StatusCheckerException;
import com.bnpinnovation.service.producer.Producer;

public class Main {
	private static Logger logger = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext( "classpath:/spring/checkerContext.xml" );
		
		Producer jobProducer = context.getBean(Producer.class);
		try {
			jobProducer.launchJobProducer();
		} catch(StatusCheckerException statusCheckException) {
			logger.error("occured status check error.. code : " + statusCheckException.getErrorCode());
		}		
	}
}
