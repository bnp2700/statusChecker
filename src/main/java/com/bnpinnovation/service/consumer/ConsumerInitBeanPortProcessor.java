package com.bnpinnovation.service.consumer;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class ConsumerInitBeanPortProcessor implements BeanPostProcessor {
	private static Logger logger = Logger.getLogger(ConsumerInitBeanPortProcessor.class);
	
	public ConsumerInitBeanPortProcessor() {
		logger.info("created ConsumerInitBeanPortProcessor");
	}
	

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		logger.info("postprocessBeforeInitialization.");
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		logger.info("postprocessAfterInitialization.");
		if( bean instanceof Consumer) {
			((Consumer) bean).initConsumer();
		}
		return bean;
	}

}
