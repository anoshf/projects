package api.vacation.checker.dealfinder.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericRerun {

    protected final ObjectMapper mapper = new ObjectMapper();
    protected String endpoint, message, dataset;
    protected Object details;


    public void setEndpointMessage(String message, String endpoint) {
        this.message = message;
        this.endpoint = endpoint;
    }

    public String getDataset() { return dataset; }

    public String getEndpoint() {
        return endpoint;
    }

    public String getMessage() {
        return message;
    }

    public Object getDetails() { return details; }
}
