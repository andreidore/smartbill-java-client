package com.github.andreidore.smartbillclient;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.andreidore.smartbillclient.json.JacksonProcessor;

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

	if (username == null || username.length() == 0) {
	    throw new IllegalArgumentException("The argument username can not be null or empty.");
	}

	if (token == null || token.length() == 0) {
	    throw new IllegalArgumentException("The argument token can not be null or empty.");
	}

	if (url == null || url.length() == 0) {
	    throw new IllegalArgumentException("The argument url can not be null or empty.");
	}

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

    public void sendEmail(String cif, String seriesName, String number, DocumentType type) {
	sendEmail(cif, seriesName, number, type, null, null, null);
    }

    public void sendEmail(String cif, String seriesName, String number, DocumentType type, String to, String subject,
	    String bodyText) {

	String sendEmailUrl = url + "/SBORO/api/document/send";

	Map<String, Object> body = new HashMap<>();
	body.put("companyVatCode", cif);
	body.put("seriesName", seriesName);
	body.put("number", number);
	switch (type) {
	case INVOICE:
	    body.put("type", "factura");
	    break;
	case PROFORMA:
	    body.put("type", "proforma");
	    break;
	}

	if (to != null) {
	    body.put("to", to);
	}

	if (subject != null) {
	    body.put("subject", Base64.getEncoder().encodeToString(subject.getBytes(Charset.forName("UTF-8"))));
	}

	if (bodyText != null) {
	    body.put("bodyText", Base64.getEncoder().encodeToString(bodyText.getBytes(Charset.forName("UTF-8"))));
	}

	RawResponse response = Requests.post(sendEmailUrl).basicAuth(username, token).jsonBody(body).send();

	if (response.getStatusCode() == 200) {

	    return;

	} else if (response.getStatusCode() == 400) {

	    Map<String, Object> responseMap = response.readToJson(Map.class);
	    Map<String, Object> statusMap = (Map<String, Object>) responseMap.get("status");
	    String errorText = (String) statusMap.get("message");
	    int errorCode = ((Long) statusMap.get("code")).intValue();
	    throw new SmartBillException(errorText, 400, errorCode);
	} else {
	    throw SmartBillException.createFromResponse(response);
	}

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

	} else {
	    throw SmartBillException.createFromResponse(response);
	}
	return null;

    }

    public List<SeriesInfo> getSeries(String cif, DocumentType type) {

	String seriesUrl = url + "/SBORO/api/series";

	Map<String, Object> params = new HashMap<>();
	params.put("cif", cif);

	if (type != null) {
	    switch (type) {
	    case INVOICE:
		params.put("type", "f");
		break;

	    case PROFORMA:
		params.put("type", "p");
		break;

	    }

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

		DocumentType typeDocument = null;
		if (t.equals("f")) {
		    typeDocument = DocumentType.INVOICE;
		} else if (t.equals("p")) {
		    typeDocument = DocumentType.PROFORMA;
		}

		seriesList.add(new SeriesInfo(name, nextNumber, typeDocument));

	    }

	    return seriesList;

	} else if (response.getStatusCode() >= 400 && response.getStatusCode() <= 499) {
	    handleError400(response);
	} else {
	    throw SmartBillException.createFromResponse(response);
	}
	return null;

    }

    public List<SeriesInfo> getSeries(String cif) {

	return getSeries(cif, null);
    }

}
