package com.unict.sagaorchestration.service;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unict.sagaorchestration.apimanager.ApiManager;
import com.unict.sagaorchestration.exception.ApiManagerException;
import com.unict.sagaorchestration.exception.SagaException;
import com.unict.sagaorchestration.model.AuctionBean;
import com.unict.sagaorchestration.model.MicroServices;
import com.unict.sagaorchestration.utils.CompressionUtil;

@Service
public class WalletService {
	
	@Autowired
	ApiManager apiManager;
	 
	public void updatewallet(Exchange exchange, String beanString) throws SagaException, IOException, ApiManagerException, URISyntaxException{
		ObjectMapper mapper = new ObjectMapper();
		String compressedpayload=exchange.getIn().getBody(String.class);
		String payload=CompressionUtil.decompressB64(compressedpayload);
		AuctionBean bean = mapper.readValue(payload.getBytes(), AuctionBean.class);;
		System.out.print("Updating wallet "+bean.getAuctionId()+"\n");
		apiManager.dispositiveAction(MicroServices.Wallet, "/wallet/set", HttpMethod.POST, bean);
	}
	
	public void rollback(Exchange exchange, String beanString) throws SagaException, IOException, ApiManagerException, URISyntaxException{
		ObjectMapper mapper = new ObjectMapper();
		String compressedpayload=exchange.getIn().getBody(String.class);
		String payload=CompressionUtil.decompressB64(compressedpayload);
		AuctionBean bean = mapper.readValue(payload.getBytes(), AuctionBean.class);;
		System.out.print("Rollback wallet "+bean.getAuctionId()+"\n");
		apiManager.dispositiveAction(MicroServices.Wallet, "/wallet/rollback", HttpMethod.POST, bean);
	}
}
