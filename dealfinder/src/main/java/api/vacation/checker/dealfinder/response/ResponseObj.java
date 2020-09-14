package api.vacation.checker.dealfinder.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "message", "request", "details", "results", "flights" })
public class ResponseObj {

    private String message, request;
    private int results;
	private JsonNode details;
	private List<?> flights;


	public ResponseObj(String message, String request, JsonNode details, List<?> flights) throws IOException {
        this.message = message;
        this.request = request;
        this.details = details;
        this.results = flights.size();
		this.flights = flights != null ? flights : null;
    }

	public JsonNode getDetails() {
		return details;
	}

	public void setDetails(JsonNode details) {
		this.details = details;
	}

	public void setResults(int results) {
		this.results = results;
	}

	public int getResults() {
		return results;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public void setFlights(List<?> flights) {
		this.flights = flights;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public Object getFlights() {
		return flights;
	}
}
