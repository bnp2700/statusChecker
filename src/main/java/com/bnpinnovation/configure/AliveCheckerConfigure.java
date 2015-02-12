package com.bnpinnovation.configure;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bnpinnovation.service.alive.PingService;
import com.bnpinnovation.service.alive.PingServiceForLinux;
import com.bnpinnovation.service.alive.PingServiceForWindows;
import com.bnpinnovation.service.alive.StatusCheckerForServer;
import com.bnpinnovation.service.alive.StatusCheckerWithInetAddress;
import com.bnpinnovation.service.alive.StatusCheckerWithPing;

@Configuration
public class AliveCheckerConfigure {
	private static Logger logger = Logger.getLogger(AliveCheckerConfigure.class);

	@Bean
	public StatusCheckerForServer aliveChecker(
			@Value("${checker.type}")String type) {
		StatusCheckerForServer checker = null;
		if("icmp".equalsIgnoreCase(type)) {
			logger.info("icmp instance create");
			checker = new StatusCheckerWithInetAddress();
		} else if("ping".equalsIgnoreCase(type)) {
			logger.info("ping instance create");
			checker = new StatusCheckerWithPing();
		} else {
			logger.info(type+" instance create");
		}
		
		return checker;
	}
	
	@Bean
	public PingService pingOperator() {
		PingService pingService = null;
		OSType osType = OSType.getOS_Type();
		if( OSType.Linux.equals(osType) || OSType.MacOS.equals(osType) ){
			pingService = new PingServiceForLinux();
		} else if( OSType.Windows.equals(osType) ) {
			pingService = new PingServiceForWindows();
		}
		
		return pingService;
	}
	
}
