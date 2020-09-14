package api.vacation.checker.dealfinder.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;


@JsonPropertyOrder({"price", "route", "airline", "departureDate", "arrivalDate", "duration", "direct", "quotesDateTime"})
public class Flights {

    String quoteIds;
    int price, numOfDays;
    boolean directOut, directIn;
    Date quotesDateTime;
    String route, airline, departureDate, arrivalDate;


    public Flights(String quoteId, int price, boolean directOut, boolean directIn, Date quotesDateTime, String route, String airline, String departureDate, String arrivalDate, int duration) {
        this.quoteIds = quoteId;
        this.price = price;
        this.numOfDays = duration;
        this.directOut = directOut;
        this.directIn = directIn;
        this.quotesDateTime = quotesDateTime;
        this.route = route;
        this.airline = airline;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate.split("T")[0];
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getQuoteIds() {
        return quoteIds;
    }

    public void setQuoteIds(String quoteIds) {
        this.quoteIds = quoteIds;
    }

    public boolean isDirectOut() {
        return directOut;
    }

    public void setDirectOut(boolean directOut) {
        this.directOut = directOut;
    }

    public boolean isDirectIn() {
        return directIn;
    }

    public void setDirectIn(boolean directIn) {
        this.directIn = directIn;
    }

    public Date getQuotesDateTime() {
        return quotesDateTime;
    }

    public void setQuotesDateTime(Date quotesDateTime) {
        this.quotesDateTime = quotesDateTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(int numOfDays) {
        this.numOfDays = numOfDays;
    }


    @Override
    public String toString() {
        return "{\"Flights\":{"
                + "\"quoteId\":\"" + quoteIds + "\""
                + ",\"price\":\"" + price + "\""
                + ",\"nbrOfDays\":\"" + numOfDays + "\""
                + ",\"directOutbound\":\"" + directOut + "\""
                + ",\"directInbound\":\"" + directIn + "\""
                + ",\"airline\":\"" + airline + "\""
                + ",\"departureDate\":\"" + departureDate + "\""
                + ",\"arrivalDate\":\"" + arrivalDate + "\""
                + ",\"quotesDateTime\":" + quotesDateTime
                + ",\"route\":\"" + route + "\""
                + "}}";
    }
}
