package com.unict.sagaorchestration.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unict.sagaorchestration.model.AuctionBean;
import com.unict.sagaorchestration.utils.CompressionUtil;

@Component
public class CompressorProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		AuctionBean payload = exchange.getIn().getBody(AuctionBean.class);
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);
		exchange.getMessage().setBody(CompressionUtil.compressAndReturnB64(json));
	}

}
