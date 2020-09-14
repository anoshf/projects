package api.vacation.checker.dealfinder.component;

import api.vacation.checker.dealfinder.entity.Quotes;
import api.vacation.checker.dealfinder.response.ResponseObj;
import api.vacation.checker.dealfinder.util.DFUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static api.vacation.checker.dealfinder.util.DFUtils.invokeApi;


@Component
public class OfflineComponent {

    @Value("${spring.project.online:true}")
    boolean isOnline;

    public static final Logger LOG = Logger.getLogger(OfflineComponent.class);
    public static final ObjectMapper mapper = new ObjectMapper();
    private static final String jsonString = "{\"Dates\":{\"OutboundDates\":[{\"PartialDate\":\"2020-03-05\",\"QuoteIds\":[1,2],\"Price\":93.0,\"QuoteDateTime\":\"2020-03-04T03:19:00\"},{\"PartialDate\":\"2020-03-06\",\"QuoteIds\":[3,4],\"Price\":97.0,\"QuoteDateTime\":\"2020-03-04T21:45:00\"},{\"PartialDate\":\"2020-03-07\",\"QuoteIds\":[5],\"Price\":88.0,\"QuoteDateTime\":\"2020-03-04T21:45:00\"},{\"PartialDate\":\"2020-03-08\",\"QuoteIds\":[6],\"Price\":88.0,\"QuoteDateTime\":\"2020-03-04T20:10:00\"},{\"PartialDate\":\"2020-03-09\",\"QuoteIds\":[7],\"Price\":88.0,\"QuoteDateTime\":\"2020-03-04T21:29:00\"},{\"PartialDate\":\"2020-03-10\",\"QuoteIds\":[8],\"Price\":97.0,\"QuoteDateTime\":\"2020-03-04T07:54:00\"},{\"PartialDate\":\"2020-03-11\",\"QuoteIds\":[9],\"Price\":70.0,\"QuoteDateTime\":\"2020-03-04T20:50:00\"},{\"PartialDate\":\"2020-03-12\",\"QuoteIds\":[10],\"Price\":89.0,\"QuoteDateTime\":\"2020-03-04T19:47:00\"},{\"PartialDate\":\"2020-03-13\",\"QuoteIds\":[11],\"Price\":90.0,\"QuoteDateTime\":\"2020-03-04T10:39:00\"},{\"PartialDate\":\"2020-03-14\",\"QuoteIds\":[12],\"Price\":88.0,\"QuoteDateTime\":\"2020-03-04T14:21:00\"},{\"PartialDate\":\"2020-03-15\",\"QuoteIds\":[13],\"Price\":126.0,\"QuoteDateTime\":\"2020-03-04T02:48:00\"},{\"PartialDate\":\"2020-03-16\",\"QuoteIds\":[14],\"Price\":89.0,\"QuoteDateTime\":\"2020-03-04T22:35:00\"},{\"PartialDate\":\"2020-03-17\",\"QuoteIds\":[15],\"Price\":70.0,\"QuoteDateTime\":\"2020-03-04T18:37:00\"},{\"PartialDate\":\"2020-03-18\",\"QuoteIds\":[16],\"Price\":70.0,\"QuoteDateTime\":\"2020-03-04T21:00:00\"},{\"PartialDate\":\"2020-03-19\",\"QuoteIds\":[17],\"Price\":104.0,\"QuoteDateTime\":\"2020-03-04T21:41:00\"},{\"PartialDate\":\"2020-03-20\",\"QuoteIds\":[18,19],\"Price\":168.0,\"QuoteDateTime\":\"2020-03-04T04:04:00\"},{\"PartialDate\":\"2020-03-21\",\"QuoteIds\":[20],\"Price\":149.0,\"QuoteDateTime\":\"2020-03-04T02:48:00\"},{\"PartialDate\":\"2020-03-22\",\"QuoteIds\":[21],\"Price\":209.0,\"QuoteDateTime\":\"2020-03-04T15:17:00\"},{\"PartialDate\":\"2020-03-23\",\"QuoteIds\":[22],\"Price\":156.0,\"QuoteDateTime\":\"2020-03-03T07:28:00\"},{\"PartialDate\":\"2020-03-24\",\"QuoteIds\":[23],\"Price\":70.0,\"QuoteDateTime\":\"2020-03-04T20:48:00\"},{\"PartialDate\":\"2020-03-25\",\"QuoteIds\":[24,25],\"Price\":112.0,\"QuoteDateTime\":\"2020-03-04T02:04:00\"},{\"PartialDate\":\"2020-03-26\",\"QuoteIds\":[26,27],\"Price\":147.0,\"QuoteDateTime\":\"2020-03-04T02:03:00\"},{\"PartialDate\":\"2020-03-27\",\"QuoteIds\":[28,29],\"Price\":118.0,\"QuoteDateTime\":\"2020-03-04T14:48:00\"},{\"PartialDate\":\"2020-03-28\",\"QuoteIds\":[30],\"Price\":136.0,\"QuoteDateTime\":\"2020-03-04T15:55:00\"},{\"PartialDate\":\"2020-03-29\",\"QuoteIds\":[31,32],\"Price\":291.0,\"QuoteDateTime\":\"2020-03-03T01:39:00\"},{\"PartialDate\":\"2020-03-30\",\"QuoteIds\":[33,34],\"Price\":137.0,\"QuoteDateTime\":\"2020-03-04T19:17:00\"},{\"PartialDate\":\"2020-03-31\",\"QuoteIds\":[35,36],\"Price\":112.0,\"QuoteDateTime\":\"2020-03-04T19:18:00\"}]},\"Quotes\":[{\"QuoteId\":1,\"MinPrice\":108.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1482],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-05T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T03:19:00\"},{\"QuoteId\":2,\"MinPrice\":93.0,\"Direct\":false,\"OutboundLeg\":{\"CarrierIds\":[1793],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-05T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T17:06:00\"},{\"QuoteId\":3,\"MinPrice\":97.0,\"Direct\":false,\"OutboundLeg\":{\"CarrierIds\":[851],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-06T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T21:45:00\"},{\"QuoteId\":4,\"MinPrice\":108.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1482],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-06T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T21:45:00\"},{\"QuoteId\":5,\"MinPrice\":88.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1606],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-07T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T21:45:00\"},{\"QuoteId\":6,\"MinPrice\":88.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1606],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-08T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T20:10:00\"},{\"QuoteId\":7,\"MinPrice\":88.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1606],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-09T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T21:29:00\"},{\"QuoteId\":8,\"MinPrice\":97.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1907],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-10T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T07:54:00\"},{\"QuoteId\":9,\"MinPrice\":70.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1907],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-11T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T20:50:00\"},{\"QuoteId\":10,\"MinPrice\":89.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1793],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-12T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T19:47:00\"},{\"QuoteId\":11,\"MinPrice\":90.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1793],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-13T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T10:39:00\"},{\"QuoteId\":12,\"MinPrice\":88.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1793],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-14T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T14:21:00\"},{\"QuoteId\":13,\"MinPrice\":126.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1907],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-15T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T02:48:00\"},{\"QuoteId\":14,\"MinPrice\":89.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1793],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-16T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T22:35:00\"},{\"QuoteId\":15,\"MinPrice\":70.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1907],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-17T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T18:37:00\"},{\"QuoteId\":16,\"MinPrice\":70.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1907],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-18T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T21:00:00\"},{\"QuoteId\":17,\"MinPrice\":104.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1907],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-19T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T21:41:00\"},{\"QuoteId\":18,\"MinPrice\":183.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1907],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-20T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T04:04:00\"},{\"QuoteId\":19,\"MinPrice\":168.0,\"Direct\":false,\"OutboundLeg\":{\"CarrierIds\":[851],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-20T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T04:04:00\"},{\"QuoteId\":20,\"MinPrice\":149.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1907],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-21T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T02:48:00\"},{\"QuoteId\":21,\"MinPrice\":209.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1758],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-22T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T15:17:00\"},{\"QuoteId\":22,\"MinPrice\":156.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1907],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-23T00:00:00\"},\"QuoteDateTime\":\"2020-03-03T07:28:00\"},{\"QuoteId\":23,\"MinPrice\":70.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1907],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-24T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T20:48:00\"},{\"QuoteId\":24,\"MinPrice\":199.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1907],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-25T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T18:50:00\"},{\"QuoteId\":25,\"MinPrice\":112.0,\"Direct\":false,\"OutboundLeg\":{\"CarrierIds\":[851],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-25T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T02:04:00\"},{\"QuoteId\":26,\"MinPrice\":209.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1907],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-26T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T02:03:00\"},{\"QuoteId\":27,\"MinPrice\":147.0,\"Direct\":false,\"OutboundLeg\":{\"CarrierIds\":[851],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-26T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T14:47:00\"},{\"QuoteId\":28,\"MinPrice\":253.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1482],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-27T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T14:48:00\"},{\"QuoteId\":29,\"MinPrice\":118.0,\"Direct\":false,\"OutboundLeg\":{\"CarrierIds\":[851],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-27T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T14:48:00\"},{\"QuoteId\":30,\"MinPrice\":136.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1907],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-28T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T15:55:00\"},{\"QuoteId\":31,\"MinPrice\":362.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1907],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-29T00:00:00\"},\"QuoteDateTime\":\"2020-03-03T01:39:00\"},{\"QuoteId\":32,\"MinPrice\":291.0,\"Direct\":false,\"OutboundLeg\":{\"CarrierIds\":[851],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-29T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T08:04:00\"},{\"QuoteId\":33,\"MinPrice\":163.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1758],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-30T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T20:50:00\"},{\"QuoteId\":34,\"MinPrice\":137.0,\"Direct\":false,\"OutboundLeg\":{\"CarrierIds\":[851],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-30T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T19:17:00\"},{\"QuoteId\":35,\"MinPrice\":163.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1482],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-31T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T19:18:00\"},{\"QuoteId\":36,\"MinPrice\":112.0,\"Direct\":false,\"OutboundLeg\":{\"CarrierIds\":[851],\"OriginId\":65368,\"DestinationId\":96322,\"DepartureDate\":\"2020-03-31T00:00:00\"},\"QuoteDateTime\":\"2020-03-04T19:18:00\"}],\"Places\":[{\"PlaceId\":65368,\"IataCode\":\"LAX\",\"Name\":\"Los Angeles International\",\"Type\":\"Station\",\"SkyscannerCode\":\"LAX\",\"CityName\":\"Los Angeles\",\"CityId\":\"LAXA\",\"CountryName\":\"United States\"},{\"PlaceId\":96322,\"IataCode\":\"YVR\",\"Name\":\"Vancouver International\",\"Type\":\"Station\",\"SkyscannerCode\":\"YVR\",\"CityName\":\"Vancouver\",\"CityId\":\"YVRA\",\"CountryName\":\"Canada\"}],\"Carriers\":[{\"CarrierId\":851,\"Name\":\"Alaska Airlines\"},{\"CarrierId\":1482,\"Name\":\"Air New Zealand\"},{\"CarrierId\":1606,\"Name\":\"Qantas\"},{\"CarrierId\":1758,\"Name\":\"Air Tahiti Nui\"},{\"CarrierId\":1793,\"Name\":\"United\"},{\"CarrierId\":1907,\"Name\":\"WestJet\"}],\"Currencies\":[{\"Code\":\"USD\",\"Symbol\":\"$\",\"ThousandsSeparator\":\",\",\"DecimalSeparator\":\".\",\"SymbolOnLeft\":true,\"SpaceBetweenAmountAndSymbol\":false,\"RoundingCoefficient\":0,\"DecimalDigits\":2}]}";
    static ResponseEntity<?> response;



    private static String readLineByLineJava8(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public static JsonNode getOffline(String filename) {
        JsonNode root = null;
        File file;
        //JsonNode node = mapper.valueToTree(fromValue);
        //JsonNode json = mapper.valueToTree("src/main/resources/json/res2.json");
        final String filePath = "src/main/resources/json/" + filename;
        //final String filePath = "src/main/resources/json/bd3.json";
        //            ClassLoader classLoader = getClass().getClassLoader();
        //URL resource = classLoader.getResource(filePath);
        //if (resource == null) {
        //    throw new IllegalArgumentException("file is not found!");
        //} else {
        //    file = new File(resource.getFile());
        //}
        //file = new File( getClass().getClassLoader().getResource(filePath).getFile() );
        root = OfflineComponent.mapper.convertValue(readLineByLineJava8(filePath), JsonNode.class);
        OfflineComponent.LOG.info("offline response object =>\n" + root);
        return root;
    }

    public static JsonNode getOnline(String url) {
        JsonNode json = null;
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("x-rapidapi-key", "61f831e4c7mshb9321efc06d65d9p16eae0jsnced0b62a0a57")
                    .build();

            //Response response = client.newCall(request).execute();
            //System.err.println(response.body().toString());

            Response response = client.newCall(request).execute();
            //SimpleEntity entity = mapper.readValue(response.body().string(), SimpleEntity.class);

            //SimpleEntity entity = objectMapper.readValue(responseBody.string(), SimpleEntity.class);
            //if (StringUtils.isNotBlank(responseBody.toString()))
            //    result = responseBody.toString();

            //json = mapper.readValue(String.valueOf(responseBody), JsonNode.class);
            //System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
            System.err.println("request: " + request.toString());
            System.err.println("response: " + response.body());
            //return mapper.readTree(entity.toString());
        } catch (IOException e) {
            LOG.warn(e.getMessage());
        }
        return json;
    }

    public static JsonNode execute(String json, int count, boolean isNonStop) {
        JsonNode detailsJson = null;
        try {
            Map<Object, Object> carriersMap = DFUtils.getCarrierNamesMapFromResponse(json);

            //detailsJson = getOffline("bd3.json");
            detailsJson = OfflineComponent.mapper.readTree(StringUtils.isNotBlank(json) ? json : jsonString);
            OfflineComponent.LOG.debug("json string => " + detailsJson.toString());

            String quoteStr = detailsJson.get("Quotes").toString();
            List<Quotes> quotes = OfflineComponent.mapper.readValue(quoteStr, new TypeReference<List<Quotes>>() {});
            detailsJson = OfflineComponent.mapper.readTree(quotes.toString());

            List<Quotes> direct = quotes.stream().filter(r -> r.isDirect())
                    .sorted(Comparator.comparingInt(Quotes::getPrice))
                    .collect(Collectors.toList());

            direct.stream().forEach(r -> {
                OfflineComponent.LOG.debug(r.getQuoteId() + " carrier-id-1:" + r.getOutboundLeg().getCarrierIds().get(0)
                        + " map-1:" + String.valueOf(carriersMap.get(r.getOutboundLeg().getCarrierIds().get(0))));
                r.setCarrierName(String.valueOf(carriersMap.get(r.getOutboundLeg().getCarrierIds().get(0))));
                r.setDepartureDate(r.getOutboundLeg().getDepartureDate());
            });
            if (count > 0)
                direct = (List<Quotes>) direct.stream().limit(count).collect(Collectors.toList());


            ObjectNode results = OfflineComponent.mapper.createObjectNode();
            results.put("direct", OfflineComponent.mapper.readTree(direct.toString()));

            if (!isNonStop) {
                List<Quotes> stops = quotes.stream().filter(r -> !r.isDirect())
                        .sorted(Comparator.comparingInt(Quotes::getPrice)).collect(Collectors.toList());

                stops.stream().forEach(r -> {
                    OfflineComponent.LOG.debug(r.getQuoteId() + " carrier-id-2:" + r.getOutboundLeg().getCarrierIds().get(0)
                            + " map-2:" + String.valueOf(carriersMap.get(r.getOutboundLeg().getCarrierIds().get(0))));
                    r.setCarrierName(String.valueOf(carriersMap.get(r.getOutboundLeg().getCarrierIds().get(0))));
                    r.setDepartureDate(r.getOutboundLeg().getDepartureDate());
                });
                if (count > 0)
                    stops = (List<Quotes>) stops.stream().limit(count).collect(Collectors.toList());

                results.put("stops", OfflineComponent.mapper.readTree(stops.toString()));
                detailsJson = results;
            }

            //JsonNode result = mapper.createObjectNode().set("direct", mapper.readTree(direct.toString())).set("direct", mapper.readTree(direct.toString()));
            //result.put("stops", mapper.readTree(direct.toString()));
        }
        catch (Exception e) {
            System.err.println("error: " + e.getMessage() + "\ncause: " + e.getCause());
        }
        return detailsJson;
    }

    public static ResponseObj getApiResponse(String route, String message, String url, int count) throws Exception {
        LOG.warn(route); //response.content_type = 'application/json'
        response = (ResponseEntity<?>) invokeApi(url);
        JsonNode jn = execute(response.getBody().toString(), count, false);
        return new ResponseObj(message, route, null, (List<?>) jn);
    }

    public JsonNode search(final String search) {
        return (isOnline) ? getOnline(search) : getOffline("res2.json");
    }

    public JsonNode search(final String url, boolean isOnline, String resp) { return (isOnline) ? getOnline(url) : getOffline(resp + ".json"); }


    //@NotNull
    //ResponseEntity<?> getPolledResults(HttpServletRequest request, @PathVariable("f") String f, @PathVariable("t") String t, String d) {
    //    final String route = f + " -> " + t + " [" + d + "]";
    //    try {
    //        String url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsedates" +
    //                "/v1.0/US/USD/en-US/" + f + "-sky/" + t + "-sky/" + d + "?inboundpartialdate=" + d;
    //
    //        url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/uk2/v1.0/450cab21-489d-4140-9f7f-c79d263d9b6e?originAirports="
    //                + f + "&destinationAirports=" + t + "&sortType=price&stops=0";
    //        json = getApiResponse(route, message, url, 0);
    //    } catch (IOException e) {
    //        message = "failed";
    //        e.printStackTrace();
    //    }
    //    return new ResponseEntity<>(json, setContentTypeHeaders(), message.startsWith("error")
    //            ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    //}
    //
    //public Object dates(final String search) throws JsonProcessingException {
    //    return (isOnline) ? HelperUtils.invokeApi(search) : getOffline("res2.json");
    //}
    //
    //
    //public JsonNode readJson() {
    //    return getOffline("bd3.json");
    //}
}
