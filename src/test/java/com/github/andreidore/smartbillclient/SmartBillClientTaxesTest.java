package com.github.andreidore.smartbillclient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.client.BasicCredentials;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class SmartBillClientTaxesTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void getTaxesTest() {
	stubFor(get(urlEqualTo("/SBORO/api/tax?cif=RO123")).withBasicAuth("username", "token")
		.withHeader("Accept", equalTo("application/json"))
		.withHeader("Content-Type", equalTo("application/json"))
		.willReturn(aResponse().withStatus(200).withBody(
			" {\"taxes\": [{\"name\": \"Redusa\",\"percentage\": 9},{\"name\": \"Normala\",\"percentage\": 19.1}]}")));

	SmartBillClient client = new SmartBillClient("username", "token", "http://localhost:8089");

	List<Tax> taxes = client.getTaxes("RO123");

	assertNotNull(taxes);
	assertEquals(2, taxes.size());
	assertEquals("Redusa", taxes.get(0).getName());
	assertEquals(new BigDecimal(9), taxes.get(0).getPercentage());
	assertEquals("Normala", taxes.get(1).getName());
	assertEquals(new BigDecimal("19.1"), taxes.get(1).getPercentage());

	verify(getRequestedFor(urlEqualTo("/SBORO/api/tax?cif=RO123"))
		.withBasicAuth(new BasicCredentials("username", "token"))
		.withHeader("Accept", matching("application/json"))
		.withHeader("Content-Type", matching("application/json")));
    }

    @Test
    public void getTaxes404Test() {

	stubFor(get(urlEqualTo("/SBORO/api/tax?cif=RO123")).withBasicAuth("username", "token")
		.withHeader("Accept", equalTo("application/json"))
		.withHeader("Content-Type", equalTo("application/json")).willReturn(aResponse().withStatus(404)));

	SmartBillClient client = new SmartBillClient("username", "token", "http://localhost:8089");

	try {
	    client.getTaxes("RO123");
	    fail("Expected exception to be thrown");

	} catch (SmartBillException e) {

	    assertEquals(404, e.getHttpCode());
	}

    }

    @Test
    public void getTaxes401Test() {

	stubFor(get(urlEqualTo("/SBORO/api/tax?cif=RO123")).withBasicAuth("username", "token")
		.withHeader("Accept", equalTo("application/json"))
		.withHeader("Content-Type", equalTo("application/json")).willReturn(aResponse().withStatus(401)));

	SmartBillClient client = new SmartBillClient("username", "token", "http://localhost:8089");

	try {
	    client.getTaxes("RO123");
	    fail("Expected exception to be thrown");

	} catch (SmartBillException e) {

	    assertEquals(401, e.getHttpCode());
	}

    }

    @Test
    public void getTaxes500Test() {

	stubFor(get(urlEqualTo("/SBORO/api/tax?cif=RO123")).withBasicAuth("username", "token")
		.withHeader("Accept", equalTo("application/json"))
		.withHeader("Content-Type", equalTo("application/json")).willReturn(aResponse().withStatus(500)));

	SmartBillClient client = new SmartBillClient("username", "token", "http://localhost:8089");

	try {
	    client.getTaxes("RO123");
	    fail("Expected exception to be thrown");

	} catch (SmartBillException e) {

	    assertEquals(500, e.getHttpCode());
	}

    }

}
