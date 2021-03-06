package com.github.smartbillclient4j;

import java.util.Map;

import net.dongliu.requests.RawResponse;

/**
 * 
 * Thrown when status code is not 2xx
 *
 * @author Andrei Dore
 *
 */
public class SmartBillException extends RuntimeException {

	/**
	 * operation code. Sometimes SmartBill return an error code.
	 */
	private int code = -1;

	/**
	 * http code return by SmartBill.
	 */
	private int httpCode = -1;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SmartBillException(String error) {
		super(error);
	}

	public SmartBillException(String error, int httpCode, int code) {
		super(error);
		this.httpCode = httpCode;
		this.code = code;
	}

	/**
	 * Return operation error code. Sometimes SmartBill return an error code.
	 * 
	 * @return error code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Return http code.
	 * 
	 * @return http code
	 */
	public int getHttpCode() {
		return httpCode;
	}

	/**
	 * Generate exception from HTTP response.
	 * 
	 * @param rawResponse http response.
	 * @return exception.
	 */
	public static SmartBillException createFromResponse(RawResponse rawResponse) {

		StringBuilder errorText = new StringBuilder();

		int statusCode = rawResponse.statusCode();

		errorText.append("HTTP code:");
		errorText.append(statusCode);
		errorText.append(". ");

		errorText.append("URL:");
		errorText.append(rawResponse.url());
		errorText.append(". ");

		switch (statusCode) {

			case 400:

				Map<String, Object> responseMap = null;
				try {
					responseMap = rawResponse.readToJson(Map.class);
				} catch (Exception e) {
					// ignore if the response is not json
				}

				if (responseMap != null && responseMap.containsKey("errorText")) {
					String errorMessage = (String) responseMap.get("errorText");

					errorText.append(errorMessage);
					errorText.append(". ");

				}

				errorText.append(
						"Datele din request nu au fost completate corect sau sunt incomplete sau actiunea nu este permisa pentru datele furnizate. Pentru mai multe detalii si sugestii, va rugam sa urmariti mesajele de eroare returnate in raspuns.");
				break;

			case 401:
				errorText.append(
						"Datele de autentificare nu sunt corecte sau datele companiei nu sunt corecte. In acest caz este nevoie sa verificati user, parola si companie. Este posibil sa primiti aceasta eroare daca folositi datele de autentificare de la o alta firma decat cea pentru care incercati sa faceti apelul.");
				break;
			case 403:
				errorText.append(
						"Utilizatorul a fost blocat pentru ca s-a depasit limita maxima de apeluri permise.");
				break;
			case 404:
			case 410:
				errorText.append(
						"Apelul facut este blocat de aplicatie deoarece contravine unor reguli interne de functionare a aplicatiei. Pentru mai multe detalii si sugestii va rugam sa urmariti mesajele de eroare returnate in raspuns.");
				break;
			case 405:
				errorText.append("Verificati daca metoda folosita este corecta/permisa.");
				break;
			case 415:
				errorText.append(
						"Formatul continutului nu este acceptat de server, este posibil sa nu fie in conformitate cu cel declarat in header.");
				break;
			case 500:
				errorText.append(
						"Apelul nu a putut fi procesat din cauza unei erori interne a serverului.");
				break;

		}

		return new SmartBillException(errorText.toString(), statusCode, -1);

	}

}
