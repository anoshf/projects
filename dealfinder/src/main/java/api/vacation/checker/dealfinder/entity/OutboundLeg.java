package api.vacation.checker.dealfinder.entity;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;


public class OutboundLeg {

    @JsonAlias("CarrierIds")
    List<Integer> carrierIds;
    @JsonAlias("OriginId")
    Integer originId;
    @JsonAlias("DestinationId")
    Integer destinationId;
    @JsonAlias("DepartureDate")
    String departureDate;

    public OutboundLeg() {
    }

    public List<Integer> getCarrierIds() {
        return carrierIds;
    }

    public void setCarrierIds(List<Integer> carrierIds) {
        this.carrierIds = carrierIds;
    }

    public Integer getOriginId() {
        return originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    @Override
    public String toString() {
        return "{\"OutboundLeg\":{"
                + "\"CarrierIds\":" + carrierIds
                + ",\"OriginId\":\"" + originId + "\""
                + ",\"DestinationId\":\"" + destinationId + "\""
                + ",\"DepartureDate\":\"" + departureDate + "\""
                + "}}";
    }
}
