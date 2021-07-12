package com.unict.sagaorchestration.config;

import java.util.concurrent.TimeUnit;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.unict.sagaorchestration.exception.SagaException;
import com.unict.sagaorchestration.model.AuctionBean;
import com.unict.sagaorchestration.processor.CheckRequestProcessor;
import com.unict.sagaorchestration.processor.CompressorProcessor;
import com.unict.sagaorchestration.service.AuctionService;
import com.unict.sagaorchestration.service.WalletService;

@Component
public class CamelRouteBuilderConfig extends RouteBuilder {  
	
	@Autowired
	private Environment env;
	
	private static final String headerAsset="relBean";
	
	@Override
	public void configure() {
		
		restConfiguration()
        .apiContextPath("/api-doc")
        .apiProperty("api.title", "Saga Orchestration")
        .apiProperty("api.version", "1.0")
        .apiProperty("cors", "true")
        .apiContextRouteId("doc-api")
        .port(env.getProperty("server.port", "8081"))
        .bindingMode(RestBindingMode.json);

		rest("/auction").id("set-auction").post().consumes("application/json")
			.type(AuctionBean.class)
			.route()
			.process(new CompressorProcessor()).to("kafka:auctions");
		
		from("kafka:auctions")
//		.bean(AuctionService.class)
		.log("--------------PRE PROCESS--------------")
		.process(new CheckRequestProcessor())
		.log("--------------POST PROCESS-------------")
		.doTry()
			.log("--------------CALLING SAGA PROCESS-------------")
			.to("direct:createAuction")
		.doCatch(SagaException.class)
			.transform()
			.simple("${exception}")
			.log("exception generate : ${body}")
		.endDoTry();
		
		from("direct:createAuction")
		.log("--------------SAGA PROCESS-------------")
		.saga()//<- new saga lra-coordinator/startNewSaga http request
			.log("--------------INIT SAGA-------------")
			.timeout(5, TimeUnit.MINUTES) 
			.propagation(SagaPropagation.REQUIRES_NEW)
			.option(headerAsset, body())
			.to("direct:setAuction")
			.to("direct:setWallet")
			.compensation("direct:cancelAuction")
			.completion("direct:completeAuction")
			.log("--------------END PROCESS-------------")
			.end();
		
		from("direct:setAuction")
        .log("--------------setAuction-------------")
		.saga()
		.propagation(SagaPropagation.SUPPORTS)
		.compensation("direct:cancelauction")
		.bean(AuctionService.class, "setauction")
		.option(headerAsset, body());

		from("direct:setWallet")
        .log("--------------setWallet-------------")
		.saga()
		.propagation(SagaPropagation.SUPPORTS)
		.compensation("direct:cancelwalletupdate")
		.bean(WalletService.class, "updatewallet")
		.option(headerAsset, body());
		
		from("direct:completeAuction")
		.transform(header(headerAsset))
        .log("--------------completeAuction-------------")
		.log("all done!");
		
		from("direct:cancelAuction")
		.transform(header(headerAsset))
        .log("--------------cancelAuction-------------")
		.bean(AuctionService.class, "rollback");
		
		from("direct:cancelwalletupdate")
		.transform(header(headerAsset))
        .log("--------------cancelwalletupdate-------------")
		.bean(WalletService.class, "rollback");

		from("direct:cancelauction")
        .transform(header(headerAsset))
        .log("--------------cancelauction-------------")
        .log("exception generate : ${body}");
	}
}