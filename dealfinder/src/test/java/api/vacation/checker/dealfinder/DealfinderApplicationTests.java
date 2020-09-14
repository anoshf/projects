package api.vacation.checker.dealfinder;

import api.vacation.checker.dealfinder.service.Controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DealfinderApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    Controller controller;

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    void contextLoads() {
    }

    //@Test
    //void findFlightTest() {
    //    controller.findDates();
    //}
    //
    //@Test
    //void browseDatesTest() {
    //    controller.browseDates();
    //}

    @Test
    void findDatesTest() throws URISyntaxException {
        //controller.findDates();
        ResponseEntity<?> response = generatePayDataRequest("/dates", "null");
        System.err.println(response.getBody());
        //assertEquals(400, response.getStatusCodeValue());
    }

    private ResponseEntity<?> generatePayDataRequest(String endpoint, String payFileId) throws URISyntaxException {
        HashMap<String, String> map = new HashMap<String, String>() {{ put("Content-Type", "application/json"); }};
        ResponseEntity<?> response = generateHttpHeaders(map, "", endpoint, false);
        System.out.println("The JSON Response is " + response);
        return response;
    }

    private ResponseEntity<?> generateHttpHeaders(HashMap<?, ?> map, Object request, String endpoint, boolean isPost) throws URISyntaxException {
        HttpHeaders httpHeaders = new HttpHeaders();
        Iterator<?> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            //System.out.println(pair.getKey() + " = " + pair.getValue());
            httpHeaders.add(String.valueOf(pair.getKey()), String.valueOf(pair.getValue()));
            it.remove();
        }
        HttpEntity<?> entity = new HttpEntity<>(request, httpHeaders);
        ResponseEntity<?> response = testRestTemplate.exchange(new URI("http://localhost:" + port + endpoint), isPost ? HttpMethod.POST : HttpMethod.GET, entity, String.class);
        return response;
    }
}
