package api.vacation.checker.dealfinder.entity;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Quotes {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @JsonAlias("QuoteId")
    int quoteId;
    @JsonAlias("MinPrice")
    int price;
    @JsonAlias("Direct")
    boolean direct;
    @JsonAlias("QuoteDateTime")
    Date quotesDateTime;
    @JsonAlias("OutboundLeg")
    OutboundLeg outboundLeg;
    String carrierName;
    Date departureDate;


    public Quotes() {}

    public void setDepartureDate(String departureDate) {
        try {
            this.departureDate = format.parse(departureDate.split("T")[0]);
        } catch (ParseException e) {
            System.err.println("ParseException: " + e.getMessage());
        }
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public int getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(int quoteId) {
        this.quoteId = quoteId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isDirect() {
        return direct;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    public Date getQuotesDateTime() {
        return quotesDateTime;
    }

    public void setQuotesDateTime(Date quotesDateTime) {
        this.quotesDateTime = quotesDateTime;
    }

    public OutboundLeg getOutboundLeg() {
        return outboundLeg;
    }

    public void setOutboundLeg(OutboundLeg outboundLeg) {
        this.outboundLeg = outboundLeg;
    }


    @Override
    public String toString() {
        return "{\"quoteId\":\"" + quoteId + "\""
                + ",\"price\":\"$"+ price +".00\""
                //+ ",\"direct\":\"" + direct + "\""
                + ",\"carrierName\":\"" + carrierName + "\""
                + ",\"departureDate\":\"" + departureDate + "\""
                + ",\"quotesDateTime\":\"" + quotesDateTime + "\""
                //+ ",\"outboundLeg\":" + outboundLeg
                + "}";
    }
}
