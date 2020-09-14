package api.vacation.checker.dealfinder.util;

import api.vacation.checker.dealfinder.component.OfflineComponent;
import api.vacation.checker.dealfinder.entity.Carriers;
import api.vacation.checker.dealfinder.entity.Quotes;
import api.vacation.checker.dealfinder.entity.QuotesEntity;
import api.vacation.checker.dealfinder.response.Flights;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class DFUtils {

    public final static Logger LOG = Logger.getLogger(DFUtils.class);
    public final static ObjectMapper mapper = new ObjectMapper();
    private static LocalDate currentdate = LocalDate.now();
    private final static LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    private static Map<String, Object> placesMap = new HashMap<>(), detailsMap = new HashMap<>();
    private static ResponseEntity<?> response; private final static int resultCount = 1;
    public final static String defaultSize = "2", defaultDuration = "5-11"; //defaultDate = "2020-07",
    public final static String link = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/" +
                                        "browsedates/v1.0/US/USD/en-US/{f}-sky/{t}-sky/{d}?inboundpartialdate={d}";


    public static ResponseEntity invokeApi(String url) throws Exception {
        ResponseEntity<String> result = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("x-rapidapi-key", "61f831e4c7mshb9321efc06d65d9p16eae0jsnced0b62a0a57");
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            LOG.info("URL: " + url + " Headers: " + headers);
            result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            QuotesEntity quotesEntity = mapper.readValue(result.getBody(), QuotesEntity.class);
            LOG.debug("Quotes: " + quotesEntity);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(result.getStatusCode() + ":" + e.getMessage());
        }
        return result;
    }


    public static List<Flights> getFlights(String f, String t, String d, int size, int minD, int maxD, boolean r) throws Exception {
        List<Flights> flights = new ArrayList<>();
        try {
            response = (ResponseEntity<?>) invokeApi(link.replace("{f}", f).replace("{t}", t).replace("{d}", d));
            List<Quotes> departures = cleanupResponse(response.getBody().toString(), r, size);
            placesMap.putAll(getPlacesMapFromResponse(response.getBody().toString()));
            response = (ResponseEntity<?>) invokeApi(link.replace("{f}", t).replace("{t}", f).replace("{d}", d));
            List<Quotes> arrivals = cleanupResponse(response.getBody().toString(), r, size);

            for (Quotes dep : departures) {
                for (Quotes arr : arrivals) {
                    long diffDays = TimeUnit.DAYS.convert(Math.abs(arr.getDepartureDate().getTime() - dep.getDepartureDate().getTime()), TimeUnit.MILLISECONDS);

                    if ((dep.getDepartureDate().compareTo(arr.getDepartureDate()) < 0) && diffDays >= minD && diffDays <= maxD) {
                        LOG.debug(diffDays + ", minD:" + minD + ", maxD:" + maxD);
                        flights.add(
                            new Flights(
                                dep.getQuoteId() + "-" + arr.getQuoteId(), dep.getPrice() + arr.getPrice(),
                                dep.isDirect(), arr.isDirect(), dep.getQuotesDateTime(),
                                placesMap.get(t.toUpperCase()) + " [" + t.toUpperCase() + "]",
                                dep.getCarrierName() + " - " + arr.getCarrierName(),
                                dep.getDepartureDate().toString(), arr.getDepartureDate().toString(),
                                (int) diffDays
                            )
                        );
                    }
                }
            }
        }
        catch (JsonProcessingException e) {
            LOG.warn(e.getMessage());
        }

        return flights;
    }

    //public static Object[] getFlights(String f, String t, String d, int size, int minD, int maxD) throws Exception {
    //    List<Flights> flights = new ArrayList<>();
    //    if (d.contains("-"))
    //        flights.addAll(invokeFlightsFinder(f, t, d, size, minD, maxD));
    //    else {
    //        int month = localDate.getMonthValue(), year = localDate.getYear();
    //        while (month < 13) {
    //            String monthStr = (month<10) ? "0" + month : String.valueOf(month);
    //            flights.addAll(invokeFlightsFinder(f, t, year + "-" + monthStr, size, minD, maxD));
    //            month++;
    //        }
    //    }
    //    final String msg = flights.size() + " results " + t;
    //    detailsMap.put(t.toUpperCase(), flights.size());
    //    LOG.info(msg);
    //    return new Object[]{flights, mapper.valueToTree(detailsMap)};
    //}

    private static List<Quotes> cleanupResponse(String json, boolean isDirect, int count) {

        List<Quotes> quotes = new ArrayList<>();
        try {
            Map<Object, Object> carriersMap = getCarrierNamesMapFromResponse(json);

            JsonNode detailsJson = OfflineComponent.mapper.readTree(json);
            LOG.debug("json string => " + detailsJson.toString());

            String quoteStr = detailsJson.get("Quotes").toString();
            quotes = OfflineComponent.mapper.readValue(quoteStr, new TypeReference<List<Quotes>>() {});
            detailsJson = OfflineComponent.mapper.readTree(quotes.toString());

            quotes = quotes.stream() //filter then sort
                    .filter(r -> isDirect ? r.isDirect() : !r.isDirect())
                    .sorted(Comparator.comparingInt(Quotes::getPrice))
                    .collect(Collectors.toList());
            LOG.info("filtered json => " + detailsJson.toString());

            quotes.stream().forEach(r -> {
                LOG.debug(r.getQuoteId() + " carrier-id-1:" + r.getOutboundLeg().getCarrierIds().get(0)
                        + " map-1:" + carriersMap.get(r.getOutboundLeg().getCarrierIds().get(0)));
                r.setCarrierName(String.valueOf(carriersMap.get(r.getOutboundLeg().getCarrierIds().get(0))));
                r.setDepartureDate(r.getOutboundLeg().getDepartureDate());
            });
            if (resultCount > 0)
                quotes = quotes.stream().limit(count).collect(Collectors.toList());
        }
        catch (Exception e) {
            System.err.println("error: " + e.getMessage() + "\ncause: " + e.getCause());
        }
        return quotes;
    }

    public static Map<Object, Object> getCarrierNamesMapFromResponse(String json) throws JsonProcessingException {
        String carriers = mapper.readTree(json).get("Carriers").toString();
        List<Carriers> carriersList = mapper.readValue(carriers, new TypeReference<List<Carriers>>() {});
        Map<Object, Object> carriersMap = new HashMap<>(); //new ObjectMapper().readValue(carriers, Map.class);
        for (Carriers item : carriersList)
            carriersMap.put(item.getCarrierId(), item.getName());
        LOG.debug("Carriers => " + carriersMap);
        return carriersMap;
    }

    public static Map<String, String> getPlacesMapFromResponse(String json) throws JsonProcessingException {
        String places = mapper.readTree(json).get("Places").toString();
        JsonNode placesJson = mapper.readTree(places);
        Map<String, String> map = new HashMap<>();
        for (JsonNode item : placesJson)
            map.put(String.valueOf(item.get("IataCode")).replaceAll("^\"|\"$", ""), String.valueOf(item.get("CityName")).replaceAll("^\"|\"$", ""));
        LOG.debug("Places => " + map);
        return map;
    }

    /*
    public static ArrayList<String> verifyInputParams(String... inputs) {
        final String d = (inputs[0] != null && StringUtils.isNotBlank(inputs[0])) ? inputs[0] : defaultDate,
        x = (inputs[1] != null && StringUtils.isNotBlank(inputs[1])) ? inputs[1] : defaultDuration,
        s = (inputs[2] != null && StringUtils.isNotBlank(inputs[2])) ? inputs[2] : defaultSize;
        return new ArrayList<String>() {{ add(d); add(x); add(s); }};
    }*/

    public static ArrayList<String> verifyInputParams(String d, String x, String s, String r) {
        String defaultDate, month = String.valueOf(currentdate.getMonthValue());
        defaultDate = currentdate.getYear() + "-" + (month.length() == 1 ? "0" + month : month);
        final String d1 = (d != null && StringUtils.isNotBlank(d)) ? d : defaultDate;
        final String x1 = (x != null && StringUtils.isNotBlank(x)) ? x : defaultDuration;
        final String s1 = (s != null && StringUtils.isNotBlank(s)) ? s : defaultSize;
        final String r1 = String.valueOf((r != null && !r.startsWith("t")) ? false : true);
        LOG.info("Default date: " + defaultDate);
        return new ArrayList<String>() {{ add(d1); add(x1); add(s1); add(r1); }};
    }

    public static String getRouteDetails(String f, String t, String d) {
        return f + " -> " + t + " [" + d + "]";
    }
}
