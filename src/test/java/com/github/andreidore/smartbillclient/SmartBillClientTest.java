package com.github.andreidore.smartbillclient;

import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class SmartBillClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void createTest() {

	new SmartBillClient("username", "token", "http://localhost:8089");

    }

    @Test(expected = NullPointerException.class)
    public void usernameNullTest() {

	new SmartBillClient(null, "token", "http://localhost:8089");

    }
    
    @Test(expected = IllegalArgumentException.class)
    public void usernameIllegalTest() {

	new SmartBillClient("", "token", "http://localhost:8089");

    }

    @Test(expected = NullPointerException.class)
    public void tokenNullTest() {

	new SmartBillClient("username", null, "http://localhost:8089");

    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void tokenIllegalTest() {

	new SmartBillClient("username", "", "http://localhost:8089");

    }

    @Test(expected = NullPointerException.class)
    public void urlNullTest() {

	new SmartBillClient("username", "token", null);

    }
    
    @Test(expected = IllegalArgumentException.class)
    public void urlIllegalTest() {

	new SmartBillClient("username", "token", "");

    }

}
