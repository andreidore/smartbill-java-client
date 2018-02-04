package com.sety.smartbillclient;

import java.util.List;

import com.sety.smartbillclient.api.SeriesInfo;
import com.sety.smartbillclient.api.SeriesResponse;
import com.sety.smartbillclient.api.SeriesType;
import com.sety.smartbillclient.api.SmartBillAPI;
import com.sety.smartbillclient.api.Tax;
import com.sety.smartbillclient.api.TaxResponse;

import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;

public class SmartBillClient {

    private static final String API_URL = "https://ws.smartbill.ro:8183";

    private SmartBillAPI api;

    public SmartBillClient(String username, String token) {
	this(username, token, API_URL);
    }

    public SmartBillClient(String username, String token, String url) {

	BasicAuthRequestInterceptor basicAuthRequestInterceptor = new BasicAuthRequestInterceptor(username, token,
		feign.Util.UTF_8);

	api = Feign.builder().decoder(new GsonDecoder()).requestInterceptor(basicAuthRequestInterceptor)
		.errorDecoder(new SmartBillErrorDecoder()).target(SmartBillAPI.class, API_URL);

    }

    public List<Tax> getTaxes(String cif) {
	TaxResponse response = api.getTaxes(cif);
	return response.getTaxes();

    }

    public List<SeriesInfo> getSeries(String cif, SeriesType type) {

	SeriesResponse response = api.getSeries(cif,type);
	
	return response.getList();
    }
    
    
    public List<SeriesInfo> getSeries(String cif) {

	return getSeries(cif,null);
    }

}
