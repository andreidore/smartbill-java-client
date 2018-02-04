package com.sety.smartbillclient;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class SmartBillErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

	if (response.status() >= 400 && response.status() <= 499) {
	    String body = null;
	    try {
		body = IOUtils.toString(response.body().asReader());
	    } catch (IOException e) {
	    }

	    if (body == null) {
		return FeignException.errorStatus(methodKey, response);
	    }

	    Map jsonMap = new Gson().fromJson(body,Map.class);

	    return new SmartBillException((String)jsonMap.get("errorText"));
	} else {
	    return FeignException.errorStatus(methodKey, response);
	}

    }

}
