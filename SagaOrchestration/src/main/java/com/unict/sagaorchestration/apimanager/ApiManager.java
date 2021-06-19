package com.unict.sagaorchestration.apimanager;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unict.sagaorchestration.exception.ApiManagerException;
import com.unict.sagaorchestration.model.BaseModel;
import com.unict.sagaorchestration.model.Endpoint;
import com.unict.sagaorchestration.model.MicroServices;

@Component
public class ApiManager {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static ObjectMapper mapper;
	private static HashMap<MicroServices,String> endpoints;
//	private static ClusterBean cluster;
	BaseModel<?> baseModel;

	@PostConstruct
	private void construct() {
		try {
			mapper = new ObjectMapper(new JsonFactory());
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			InputStream input = ApiManager.class.getClassLoader().getResourceAsStream("api.yaml");
			List<Endpoint> endpointList = mapper.readValue(input, new TypeReference<List<Endpoint>>() {});
			HashMap<MicroServices, String> endpoints = new HashMap<>();
			for(Endpoint tmp: endpointList) {
				endpoints.put(MicroServices.valueOf(tmp.getMicroservice()), tmp.getUrl());
			}
//			cluster=mapper.readValue(input,new TypeReference<ClusterBean>() { });
//			for(Endpoint tmp:cluster.getEndpoint()) {
//				endpoints.put(MicroServices.valueOf(tmp.getMicroservice()), tmp.getUrl());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	public void dispositiveAction(MicroServices microservice, String endpoint, HttpMethod method, Object data) throws ApiManagerException, URISyntaxException{
		URI uri = new URI(endpoints.get(microservice)+endpoint);
		RequestEntity<?> request = getRequestEntity(method,data,uri,null);
		ParameterizedTypeReference<BaseModel<?>> myBean =
				new ParameterizedTypeReference<BaseModel<?>>() {};
				baseModel = (BaseModel<?>) restTemplate.exchange(request,myBean).getBody();
				if(!baseModel.isSuccess()) throw new ApiManagerException(baseModel.getErrorCode().getErrorCode(),baseModel.getErrorCode().getErrorMessage());
	}
	
	private RequestEntity<?> getRequestEntity(HttpMethod method,Object data,URI uri,String token) throws URISyntaxException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if(token!=null)headers.add("Authorization", token);
		RequestEntity<?> request = null;
		if(method.equals(HttpMethod.POST)) {
			request=RequestEntity
					.post(uri).headers(headers)
					.accept(MediaType.APPLICATION_JSON)
					.body(data);
		}
		if(method.equals(HttpMethod.GET)) {
			request=RequestEntity
					.get(uri).headers(headers).build();
		}
		return request;
	}
}
	

