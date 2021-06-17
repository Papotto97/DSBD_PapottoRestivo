package com.unict.sagaorchestrator;

import java.util.concurrent.TimeUnit;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.springframework.stereotype.Component;

import com.unict.model.AuctionBean;

@Component
public class Auction extends RouteBuilder{
	
	private static final String headerAsset="relBean";
	
	@Override
	public void configure() {
		restConfiguration().component("undertow").port(8081);
		rest().path("/auction").post().type(AuctionBean.class).route()
			.process(new CompressorProcessor()).to("kafka:auctions");
		
		from("kafka:auctions")
		.process(new CheckRequestProcessor())
		.choice()
			.when(body().convertTo(AuctionBean.class).contains(null)).to("direct:updateOffer")
			.otherwise().to("direct:createAuction")
		.doTry()
			.bean(AuctionService.class, "addAuction")
			.to("direct:createAuction")
		.doCatch(SagaException.class)
			.transform()
			.simple("${exception}")
			.log("exception generate : ${body}")
			.bean(AuctionService.class, "error")
		.endDoTry();
		
		from("direct:createAuction")
		.saga()//<- new saga lra-coordinator/startNewSaga http request
			.timeout(5, TimeUnit.MINUTES) 
			.propagation(SagaPropagation.REQUIRES_NEW)
			.option(headerAsset, body())
			.compensation("direct:cancelAuction")
			.completion("direct:completeAuction")
				.to("direct:setAuction")
				.to("direct:setWallet")
				.end();
		
		from("direct:completeAuction")
		.transform(header(headerAsset))
		.bean(AuctionService.class, "success")
		.log("all done!");
		
		from("direct:cancelAuction")
		.transform(header(headerAsset))
		.log("ops!").end();

	}
}