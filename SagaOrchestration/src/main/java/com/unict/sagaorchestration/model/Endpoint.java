package com.unict.sagaorchestration.model;

import java.io.Serializable;

public class Endpoint implements Serializable{
	
	private static final long serialVersionUID = -3102165748368482844L;
	private String url;
	private String microservice;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMicroservice() {
		return microservice;
	}
	public void setMicroservice(String microservice) {
		this.microservice = microservice;
	}
}
