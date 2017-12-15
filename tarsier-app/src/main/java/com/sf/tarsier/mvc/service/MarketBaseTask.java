package com.sf.tarsier.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("marketBaseTask")
public class MarketBaseTask implements Runnable {
	
	@Autowired
	private MarketBaseService marketBaseService;

	@Override
	public void run() {
		marketBaseService.createNewMarket();
	}
	
}
