package com.unict.sagaorchestration.service;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unict.sagaorchestration.apimanager.ApiManager;
import com.unict.sagaorchestration.exception.ApiManagerException;
import com.unict.sagaorchestration.exception.SagaException;
import com.unict.sagaorchestration.model.AuctionBean;
import com.unict.sagaorchestration.model.MicroServices;
import com.unict.sagaorchestration.utils.CompressionUtil;

import org.springframework.http.HttpMethod;

@Service
public class AuctionService {
	
	@Autowired
	ApiManager apiManager;
	 
	public void setauction(Exchange exchange, String beanString) throws SagaException, IOException, ApiManagerException, URISyntaxException{
		ObjectMapper mapper = new ObjectMapper();
		String compressedpayload=exchange.getIn().getBody(String.class);
		String payload=CompressionUtil.decompressB64(compressedpayload);
		AuctionBean bean = mapper.readValue(payload.getBytes(), AuctionBean.class);;
		System.out.print("Updating auction "+bean.getAuctionId()+"\n");
		apiManager.dispositiveAction(MicroServices.Auction, "/auction/set", HttpMethod.POST, bean);
	}
	
	public void rollback(Exchange exchange, String beanString) throws SagaException, IOException, ApiManagerException, URISyntaxException{
		ObjectMapper mapper = new ObjectMapper();
		String compressedpayload=exchange.getIn().getBody(String.class);
		String payload=CompressionUtil.decompressB64(compressedpayload);
		AuctionBean bean = mapper.readValue(payload.getBytes(), AuctionBean.class);;
		System.out.print("Rollback auction "+bean.getAuctionId()+"\n");
		apiManager.dispositiveAction(MicroServices.Auction, "/auction/rollback", HttpMethod.POST, bean);
	}
	
}
