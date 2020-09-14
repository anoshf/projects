package api.vacation.checker.dealfinder.service;

import api.vacation.checker.dealfinder.response.Flights;
import api.vacation.checker.dealfinder.response.ResponseObj;
import api.vacation.checker.dealfinder.util.DFUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SearchService {

    public static final Logger LOG = Logger.getLogger(SearchService.class);
    private String message = "success"; ResponseObj json = null;


    ResponseObj getQuotesRoundTripMultiple(String f, String t, String d, String duration, int size, boolean r) throws Exception {

        final String route = DFUtils.getRouteDetails(f, t, d), requestDetails = route + " " + duration + " " + size;
        LOG.info("request details => " + requestDetails);

        if (!duration.contains("-") || duration.length() < 3)
            duration = Controller.defaultDuration;
        List<String> durationArray = Arrays.asList(duration.split("-"));
        List<String> destArray = new ArrayList<>();
        List<Flights> allFlights = new ArrayList<>();

        if (t.contains(",") & t.length() > 3)
            destArray = Arrays.asList(t.split(","));
        else
            destArray.add(t);

        for (String dest : destArray) {
            List<Flights> flight = DFUtils.getFlights(f, dest, d, size, Integer.valueOf(durationArray.get(0)), Integer.valueOf(durationArray.get(1)), r);
            allFlights.addAll(flight);
        }

        Comparator<Flights> sortCriteria = Comparator.comparingInt(Flights::getPrice).thenComparing(Flights::getDepartureDate).thenComparingInt(Flights::getNumOfDays);
        allFlights = allFlights.stream().sorted(sortCriteria).limit(size).collect(Collectors.toList());

        json = new ResponseObj(message, requestDetails, null, allFlights); //DFUtils.mapper.valueToTree(obj[1])
        LOG.info("routes: " + route + " limit: " + size + " flights:\n" + allFlights);
        return json;
    }
}
