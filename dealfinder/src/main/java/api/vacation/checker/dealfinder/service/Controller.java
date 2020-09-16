package api.vacation.checker.dealfinder.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;

import static api.vacation.checker.dealfinder.util.DFUtils.verifyInputParams;


@RestController
public class Controller extends BaseController {

    /*
    t - destination airport
    d - dates  'http://localhost:9000/api/v2/YVR?x=1-11&d=2020-07'
    x - duration  http://localhost:9000/api/v2/YVR?x=1-11
    s - result size  'http://localhost:9000/api/v2/YVR?x=1-11&d=2020-07&s=3'
    r - direct - default false
     */
    @GetMapping("/api/v2/{t}")
    public ResponseEntity<?> browseDates4(@PathVariable("t") String t, @PathParam("d") String d, @PathParam("x") String x,
                                          @PathParam("s") String s, @PathParam("r") String r, HttpServletRequest request) {
        try {
            LOG.info("endpoint: " + request.getRequestURI());
            List<String> inputs = verifyInputParams(d, x, s, r);
            return getFlightsData(defaultAirport, t, inputs.get(0), inputs.get(1), inputs.get(2), inputs.get(3));
        }
        catch (Exception e) {
            LOG.error(e.getMessage());
            return new ResponseEntity<>(setContentTypeHeaders(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/api/v2/{f}/{t}/{d}")
    public ResponseEntity<?> browseDates3(HttpServletRequest request, @PathVariable("f") String f, @PathVariable("t") String t, @PathVariable("d") String d) {
        return browseDates2(request, f, t, StringUtils.isNotBlank(d) ? d : defaultDate, defaultDuration, defaultSize);
    }
}
