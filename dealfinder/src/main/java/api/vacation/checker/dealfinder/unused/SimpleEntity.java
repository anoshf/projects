//package api.vacation.checker.dealfinder.entity;
//
//import com.fasterxml.jackson.annotation.JsonAlias;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import java.io.Serializable;
//import java.util.List;
//
//
//@JsonIgnoreProperties
//public class SimpleEntity implements Serializable {
//
//    private Dates dates;
//    private List<?> quotes, places, carriers, currencies;
//
//
//    public SimpleEntity() {}
//
//    public List<?> getQuotes() {
//        return quotes;
//    }
//
//    public void setQuotes(List<?> quotes) {
//        this.quotes = quotes;
//    }
//
//    public Dates getDates() {
//        return dates;
//    }
//
//    public void setDates(Dates dates) {
//        this.dates = dates;
//    }
//
//    public List<?> getPlaces() {
//        return places;
//    }
//
//    public void setPlaces(List<?> places) {
//        this.places = places;
//    }
//
//    public List<?> getCarriers() {
//        return carriers;
//    }
//
//    public void setCarriers(List<?> carriers) {
//        this.carriers = carriers;
//    }
//
//    public List<?> getCurrencies() {
//        return currencies;
//    }
//
//    public void setCurrencies(List<?> currencies) {
//        this.currencies = currencies;
//    }
//
//
//    public class Dates {
//        List<?> outboundDates;
//    }
//}