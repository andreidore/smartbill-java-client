package com.sety.smartbillclient;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sety.smartbillclient.SeriesInfo.SeriesType;
import com.sety.smartbillclient.json.JacksonProcessor;

import net.dongliu.requests.RawResponse;
import net.dongliu.requests.Requests;
import net.dongliu.requests.json.JsonLookup;
import net.dongliu.requests.json.JsonProcessor;

public class SmartBillClient {

    public static final String API_URL = "https://ws.smartbill.ro:8183";

    private String username;

    private String token;

    private String url;

    public SmartBillClient(String username, String token) {
	this(username, token, API_URL);
    }

    public SmartBillClient(String username, String token, String url) {

	this.username = username;
	this.token = token;
	this.url = url;

	JsonProcessor jsonProcessor = new JacksonProcessor();
	JsonLookup.getInstance().register(jsonProcessor);

    }

    private void handleError400(RawResponse response) {

	Map<String, Object> responseMap = response.readToJson(Map.class);

	String errorText = (String) responseMap.get("errorText");

	throw new SmartBillException(errorText);
    }

    public List<Tax> getTaxes(String cif) {

	String taxesUrl = url + "/SBORO/api/tax";

	Map<String, Object> params = new HashMap<>();
	params.put("cif", cif);

	RawResponse response = Requests.get(taxesUrl).basicAuth(username, token).requestCharset(StandardCharsets.UTF_8)
		.params(params).send();

	if (response.getStatusCode() == 200) {

	    Map responseMap = response.readToJson(Map.class);

	    List<Map<String, Object>> responseList = (List<Map<String, Object>>) responseMap.get("taxes");

	    List<Tax> taxList = new ArrayList<Tax>();

	    for (Map<String, Object> m : responseList) {

		String name = (String) m.get("name");
		BigDecimal percentage = (BigDecimal) m.get("percentage");

		taxList.add(new Tax(name, percentage));

	    }

	    return taxList;

	} else if (response.getStatusCode() >= 400 && response.getStatusCode() <= 499) {
	    handleError400(response);
	}

	return null;

    }

    public List<SeriesInfo> getSeries(String cif, SeriesType type) {

	String seriesUrl = url + "/SBORO/api/series";

	Map<String, Object> params = new HashMap<>();
	params.put("cif", cif);

	if (type != null) {
	    params.put("type", type.getValue());

	}

	RawResponse response = Requests.get(seriesUrl).basicAuth(username, token).requestCharset(StandardCharsets.UTF_8)
		.params(params).send();

	if (response.getStatusCode() == 200) {

	    Map<String, Object> responseMap = response.readToJson(Map.class);

	    List<Map<String, Object>> responseList = (List<Map<String, Object>>) responseMap.get("list");

	    List<SeriesInfo> seriesList = new ArrayList<SeriesInfo>();

	    for (Map<String, Object> m : responseList) {

		String name = (String) m.get("name");
		long nextNumber = ((Long) m.get("nextNumber")).longValue();
		String t = (String) m.get("type");

		seriesList.add(new SeriesInfo(name, nextNumber, SeriesType.fromString(t)));

	    }

	    return seriesList;

	} else if (response.getStatusCode() >= 400 && response.getStatusCode() <= 499) {
	    handleError400(response);
	}

	return null;

    }

    public List<SeriesInfo> getSeries(String cif) {

	return getSeries(cif, null);
    }

}
