package com.sf.tarsier.mvc.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("marketBaseTask")
public class MarketBaseAsyncTask{
	
	private static final int POOL_SIZE = 4;
	
	private ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE); 
	
	@Autowired
	private MarketBaseService marketBaseService;
	
	public void execute(){
		pool.execute(new Runnable() {
			
			@Override
			public void run() {
				marketBaseService.createNewMarket();
			}
		});
	}
	
}
