package api.vacation.checker.dealfinder.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotesEntity implements Serializable {

    @JsonAlias("Quotes")
    private List<Quotes> quotes;

    //private Dates dates;
    //@JsonIgnoreProperties(ignoreUnknown = true)
    //private List<?> places;
    //@JsonIgnoreProperties(ignoreUnknown = true)
    //private List<?> carriers;
    //@JsonIgnoreProperties(ignoreUnknown = true)
    //private List<?> currencies;


    public QuotesEntity() {}

    public QuotesEntity(List<Quotes> quotes) {
        this.quotes = quotes;
    }

    public List<Quotes> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quotes> quotes) {
        this.quotes = quotes;
    }


    @Override
    public String toString() {
        return "{\"Quotes\":{"
                + "\"quotes\":" + quotes
                + "}}";
    }
}