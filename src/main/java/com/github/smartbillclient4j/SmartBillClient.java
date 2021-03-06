package com.github.smartbillclient4j;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.dongliu.requests.RawResponse;
import net.dongliu.requests.Requests;

public class SmartBillClient {

	public static final String API_URL = "https://ws.smartbill.ro:8183";

	private final String username;

	private final String token;

	private final String url;

	public SmartBillClient(String username, String token) {
		this(username, token, API_URL);
	}

	public SmartBillClient(String username, String token, String url) {

		Objects.requireNonNull(username, "The argument username ca not be null.");
		if (username.length() == 0) {
			throw new IllegalArgumentException("The argument username can not be null or empty.");
		}

		Objects.requireNonNull(token, "The argument token ca not be null.");
		if (token.length() == 0) {
			throw new IllegalArgumentException("The argument token can not be null or empty.");
		}

		Objects.requireNonNull(url, "The argument url ca not be null.");
		if (url.length() == 0) {
			throw new IllegalArgumentException("The argument url can not be null or empty.");
		}

		this.username = username;
		this.token = token;
		this.url = url;



	}

	/**
	 * Download invoice PDF.
	 * 
	 * @param cif        cif of the company.
	 * @param seriesName series.
	 * @param number     number.
	 * @return the invoice like array of bytes.
	 */
	public byte[] getInvoicePdf(String cif, String seriesName, String number) {

		Objects.requireNonNull(cif, "The argument cif can not be null.");
		Objects.requireNonNull(seriesName, "The argument seriesName can not be null.");
		Objects.requireNonNull(number, "The argument number can not be null.");

		String pdfUrl = url + "/SBORO/api/invoice/pdf";

		Map<String, Object> headers = new HashMap<>();
		headers.put("Accept", "application/octet-stream");
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");

		Map<String, Object> params = new HashMap<>();
		params.put("cif", cif);
		params.put("seriesname", seriesName);
		params.put("number", number);

		RawResponse response = Requests.get(pdfUrl).basicAuth(username, token)
				.requestCharset(StandardCharsets.UTF_8).params(params).send();

		if (response.statusCode() == 200) {

			return response.readToBytes();

		} else {

			throw SmartBillException.createFromResponse(response);
		}

	}

	/**
	 * Download estimate (proforma invoice) PDF.
	 * 
	 * @param cif        cif.
	 * @param seriesName seriesName.
	 * @param number     number.
	 * @return bytes.
	 */
	public byte[] getEstimatePdf(String cif, String seriesName, String number) {

		Objects.requireNonNull(cif, "The argument cif can not be null.");
		Objects.requireNonNull(seriesName, "The argument seriesName can not be null.");
		Objects.requireNonNull(number, "The argument number can not be null.");

		String pdfUrl = url + "/SBORO/api/estimate/pdf";

		Map<String, Object> headers = new HashMap<>();
		headers.put("Accept", "application/octet-stream");
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");

		Map<String, Object> params = new HashMap<>();
		params.put("cif", cif);
		params.put("seriesname", seriesName);
		params.put("number", number);

		RawResponse response = Requests.get(pdfUrl).basicAuth(username, token)
				.requestCharset(StandardCharsets.UTF_8).params(params).send();

		if (response.statusCode() == 200) {

			return response.readToBytes();

		} else {

			throw SmartBillException.createFromResponse(response);
		}

	}

	public void sendEmail(String cif, String seriesName, String number, DocumentType type) {

		sendEmail(cif, seriesName, number, type, null, null, null);

	}

	public void sendEmail(String cif, String seriesName, String number, DocumentType type,
			String to, String subject, String bodyText) {

		Objects.requireNonNull(cif, "The argument cif can not be null.");
		Objects.requireNonNull(seriesName, "The argument seriesName can not be null.");
		Objects.requireNonNull(number, "The argument number can not be null.");
		Objects.requireNonNull(type, "The argument type can not be null.");

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
			body.put("subject",
					Base64.getEncoder().encodeToString(subject.getBytes(Charset.forName("UTF-8"))));
		}

		if (bodyText != null) {
			body.put("bodyText", Base64.getEncoder()
					.encodeToString(bodyText.getBytes(Charset.forName("UTF-8"))));
		}

		RawResponse response =
				Requests.post(sendEmailUrl).basicAuth(username, token).jsonBody(body).send();

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

		Objects.requireNonNull(cif, "The argument cif can not be null.");

		String taxesUrl = url + "/SBORO/api/tax";

		Map<String, Object> headers = new HashMap<>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");

		Map<String, Object> params = new HashMap<>();
		params.put("cif", cif);

		RawResponse response = Requests.get(taxesUrl).headers(headers).basicAuth(username, token)
				.requestCharset(StandardCharsets.UTF_8).params(params).send();

		if (response.statusCode() == 200) {

			return response.readToJson(TaxResponse.class).getTaxes();

		} else {
			throw SmartBillException.createFromResponse(response);
		}

	}

	public List<SeriesInfo> getSeries(String cif, DocumentType type) {

		Objects.requireNonNull(cif, "The argument cif can not be null.");
		Objects.requireNonNull(type, "The argument type can not be null.");

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

		RawResponse response = Requests.get(seriesUrl).basicAuth(username, token)
				.requestCharset(StandardCharsets.UTF_8).params(params).send();

		if (response.getStatusCode() == 200) {

			Map<String, Object> responseMap = response.readToJson(Map.class);

			List<Map<String, Object>> responseList =
					(List<Map<String, Object>>) responseMap.get("list");

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

		} else {
			throw SmartBillException.createFromResponse(response);
		}

	}

	public List<SeriesInfo> getSeries(String cif) {
		return getSeries(cif, null);
	}

	/**
	 * 
	 * Returns all products stock in all warehouses.
	 * 
	 * @param cif  company cif
	 * @param date date
	 * @return list of products stock
	 */
	public List<Stock> getStocks(String cif, Date date) {
		return getStocks(cif, date, null, null, null);
	}

	/**
	 * Returns all products stock in warehouse
	 * 
	 * @param cif           company cif
	 * @param date          date
	 * @param warehouseName warehouse name
	 * @return list of products stock
	 */
	public List<Stock> getStocks(String cif, Date date, String warehouseName) {
		return getStocks(cif, date, warehouseName, null, null);
	}

	/**
	 * Returns products stock
	 * 
	 * @param cif           conpany cif
	 * @param date          date
	 * @param warehouseName If the parameter is null the method will returns products stock for all
	 *                      warehouses.
	 * @param productName   If the parameter is null the method will returns products stock for all
	 *                      products.
	 * @param productCode   If the parameter is null the method will returns products stock for all
	 *                      products.
	 * @return list of stocks.
	 */
	public List<Stock> getStocks(String cif, Date date, String warehouseName, String productName,
			String productCode) {

		Objects.requireNonNull(cif, "The argument cif can not be null.");
		Objects.requireNonNull(date, "The argument date can not be null.");


		String stocksUrl = url + "/SBORO/api/stocks";

		Map<String, Object> params = new HashMap<>();
		params.put("cif", cif);

		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-mm-dd");
		String dateString = dateFormat.format(date);
		params.put("date", dateString);

		if (warehouseName != null) {
			try {
				params.put("warehouseName",
						URLEncoder.encode(warehouseName, StandardCharsets.UTF_8.toString()));
			} catch (UnsupportedEncodingException e) {

			}
		}

		if (productCode != null) {
			try {
				params.put("productCode",
						URLEncoder.encode(productCode, StandardCharsets.UTF_8.toString()));
			} catch (UnsupportedEncodingException e) {

			}
		}

		if (productName != null) {
			try {
				params.put("productName",
						URLEncoder.encode(productName, StandardCharsets.UTF_8.toString()));
			} catch (UnsupportedEncodingException e) {

			}
		}

		RawResponse response = Requests.get(stocksUrl).basicAuth(username, token)
				.requestCharset(StandardCharsets.UTF_8).params(params).send();

		if (response.statusCode() == 200) {


			/*
			 * 
			 * String text = response.readToText();
			 * 
			 * System.out.println(text);
			 * 
			 * try { FileUtils.writeStringToFile(new File("test.json"), text); } catch (IOException
			 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
			 */

			StockResponse stockResponse = response.readToJson(StockResponse.class);

			return stockResponse.getStocks();



		} else {
			throw SmartBillException.createFromResponse(response);
		}

	}

}
