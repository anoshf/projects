package api.vacation.checker.dealfinder.service;

import api.vacation.checker.dealfinder.response.ResponseObj;
import api.vacation.checker.dealfinder.util.DFUtils;
import api.vacation.checker.dealfinder.component.OfflineComponent;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

import static api.vacation.checker.dealfinder.util.DFUtils.verifyInputParams;

public class BaseController {

    @Autowired
    OfflineComponent utils;
    @Autowired
    SearchService service;

    public final static Logger LOG = Logger.getLogger(SearchService.class);
    public static String message = "success";
    public final static String defaultAirport = "LAX", defaultDate = "2020-04", defaultSize = "8", defaultDuration = "5-11";
    protected ResponseObj json = null;


    protected static HttpHeaders setContentTypeHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    ResponseEntity<?> getFlightsData(String f, String t, String d, String x, String s, String r) {
        try {
            json = service.getQuotesRoundTripMultiple(f, t, d, x, Integer.valueOf(s), Boolean.valueOf(r));
        } catch (Exception e) {
            e.printStackTrace();
            message = "failed";
            LOG.error(message + ": " + e.getMessage());
        }
        return new ResponseEntity<>(json, setContentTypeHeaders(), message.startsWith("error")
                ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    /**
     * UNUSED
     * @param input
     * @return
     */
    ResponseEntity<?> getQuotesFromAirportCodes(String... input) {
        final String route = DFUtils.getRouteDetails(input[0], input[1], input[2]);
        try {
            String endpoint = DFUtils.link.replace("{f}", input[0]).replace("{t}", input[1]).replace("{d}", input[2]).replace("{d}", input[2]);
            json = OfflineComponent.getApiResponse(route, message, endpoint, 0);
        }
        catch (Exception e) {
            message = "failed";
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, setContentTypeHeaders(), message.startsWith("error")
                ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @GetMapping("/api/v0/{f}/{t}")
    public ResponseEntity<?> browseDates1(HttpServletRequest request, @PathVariable("f") String f, @PathVariable("t") String t) {
        return browseDates(request, f, t, defaultDate);
    }

    @GetMapping("/api/v0/{f}/{t}/{d}")
    public ResponseEntity<?> browseDates(HttpServletRequest request, @PathVariable("f") String f, @PathVariable("t") String t, @PathVariable("d") String d) {
        LOG.info(request.getContextPath() + request.getPathInfo() + request.getRequestURI());
        return getQuotesFromAirportCodes(new String[]{f, t, d});
    }

    @GetMapping("/api/version")  //http://localhost:9000/api/v2/lax/hnl,ogg/2020-04
    public ResponseEntity<String> version() {
        return new ResponseEntity<String>("1.0.5", setContentTypeHeaders(), HttpStatus.OK);
    }

    @GetMapping("/api")  //http://localhost:9000/api/v2/lax/hnl,ogg/2020-04
    public void findFlight() {
        final String url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsedates/v1.0/US/USD/en-US/LAX-sky/SJD-sky/2020-12-24?inboundpartialdate=2020-12-28";
        utils.search(url);
    }

    @GetMapping("/api/v1/dates")
    public ResponseEntity<?> findDates() {
        final String url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsedates/v1.0/US/USD/en-US/LAX-sky/FLL-sky/2020-02?inboundpartialdate=2020-02";
        JsonNode search = utils.search(url, true, "bd3");
        return new ResponseEntity<JsonNode>(search, setContentTypeHeaders(), search != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api/v1/off")
    public ResponseEntity<?> offline(HttpRequest request) {
        try {
            JsonNode jn = OfflineComponent.execute(null, 0, false);
            json = new ResponseObj(message, request.getURI().toString(), null, (List<?>) jn);
        }
        catch (IOException e) {
            message = "failed";
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, setContentTypeHeaders(), message.startsWith("error")
                ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @GetMapping("/api/v1/{f}/{t}/{x}")
    public ResponseEntity<?> browseDates1(HttpServletRequest request, @PathVariable("f") String f, @PathVariable("t") String t, @PathVariable("x") String x) {
        return browseDates2(request, f, t, defaultDate, StringUtils.isNotBlank(x) ? x : defaultDuration, defaultSize);
    }

    @GetMapping("/api/v1/{f}/{t}")
    public ResponseEntity<?> browseDates3(HttpServletRequest request, @PathVariable("f") String f, @PathVariable("t") String t) {
        return browseDates2(request, f, t, defaultDate, defaultDuration, defaultSize);
    }

    @GetMapping("/api/v1/{f}/{t}/{d}/{x}")
    public ResponseEntity<?> browseDates3(HttpServletRequest request, @PathVariable("f") String f, @PathVariable("t") String t, @PathVariable("d") String d, @PathVariable("x") String x) {
        return browseDates2(request, f, t, StringUtils.isNotBlank(d) ? d : defaultDate, x, defaultSize);
    }

    @GetMapping("/api/v1/{f}/{t}/{d}/{x}/{s}")
    public ResponseEntity<?> browseDates2(HttpServletRequest request, @PathVariable("f") String f, @PathVariable("t") String t, @PathVariable("d") String d, @PathVariable("x") String x, @PathVariable("s") String s) {
        List<String> inputs = verifyInputParams(d, x, s, null);
        return getFlightsData(f, t, inputs.get(0), inputs.get(1), inputs.get(2), inputs.get(3));
    }
}
