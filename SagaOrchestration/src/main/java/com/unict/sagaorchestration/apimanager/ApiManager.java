package com.unict.sagaorchestration.apimanager;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unict.sagaorchestration.exception.ApiManagerException;
import com.unict.sagaorchestration.model.BaseModel;
import com.unict.sagaorchestration.model.ClusterBean;
import com.unict.sagaorchestration.model.Endpoint;
import com.unict.sagaorchestration.model.MicroServices;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

@Component
public class ApiManager {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static ObjectMapper mapper;
	private static HashMap<MicroServices,String> endpoints;
	private static ClusterBean cluster;
	BaseModel<?> baseModel;

	@PostConstruct
	private void construct() {
		try {
			mapper = new ObjectMapper(new YAMLFactory());
			InputStream input = ApiManager.class.getClassLoader().getResourceAsStream("api.yaml");
			cluster=mapper.readValue(input,new TypeReference<ClusterBean>() { });
			endpoints= new HashMap<MicroServices, String>();
			for(Endpoint tmp:cluster.getEndpoint()) {
				endpoints.put(MicroServices.valueOf(tmp.getMicroservice()), tmp.getUrl());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	public void dispositiveAction(MicroServices microservice, String endpoint, HttpMethod method, Object data) throws ApiManagerException, URISyntaxException{
		URI uri = new URI(endpoints.get(microservice)+endpoint);
		RequestEntity<?> request = getRequestEntity(method,data,uri,null);
		ParameterizedTypeReference<BaseModel<?>> myBean = new ParameterizedTypeReference<BaseModel<?>>() {};
		ResponseEntity<?> resp = restTemplate.exchange(request,myBean);
//		baseModel = (BaseModel<?>) restTemplate.exchange(request,myBean).getBody();
		if (!resp.getStatusCode().is2xxSuccessful()) {
			throw new ApiManagerException(Integer.toString(resp.getStatusCodeValue()), resp.getStatusCode().getReasonPhrase());
		}
		else {
			baseModel = (BaseModel<?>) resp.getBody();
			if(!baseModel.isSuccess()) 
				throw new ApiManagerException(Integer.toString(baseModel.getError().getErrorCode().value()), baseModel.getError().getErrorMessage());
		}
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
	

