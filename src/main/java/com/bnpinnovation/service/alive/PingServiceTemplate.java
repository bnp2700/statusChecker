package com.bnpinnovation.service.alive;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Value;

import com.bnpinnovation.exception.PingServiceException;

public abstract class PingServiceTemplate implements PingService{
	
	@Value("${checker.ping.timeout:10}")
	private long pingTimeout;
	
	@Value("${checker.timeout:3000}")
	private long timeout;
	
	public void isAlive(final String host) {
		ExecutorService singleThread = Executors.newSingleThreadExecutor();
		
		try {
			timedCall(singleThread, new Callable<Integer>() {
				public Integer call() throws Exception {
					Process process = pingCheck( host, pingTimeout);
					return process.waitFor();
				}
			}, timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException | ExecutionException executeException) {		
			throw new PingServiceException(
					PingServiceException.pingExecuteErrorCode);
		} catch (TimeoutException timeoutException) {
		
			throw new PingServiceException(
					PingServiceException.pingTimeoutErrorCode);
		} catch(Exception anotherException) {
			throw new PingServiceException(
					PingServiceException.unknownErrorCode);
		}
	}
	
	private <T> T timedCall(Executor executor, Callable<T> c, long timeout,
			TimeUnit timeUnit) throws InterruptedException, ExecutionException,
			TimeoutException {
		FutureTask<T> task = new FutureTask<T>(c);
		executor.execute(task);
		return task.get(timeout, timeUnit);
	}

	protected abstract Process pingCheck(String host, long pingTimeout);
}
