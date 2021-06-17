package com.unict.sagaorchestrator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unict.config.CompressionUtil;

@Component
public class CompressorProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		String payload = exchange.getIn().getBody(String.class);		
		exchange.getMessage().setBody(CompressionUtil.compressAndReturnB64(payload));
	}

}
