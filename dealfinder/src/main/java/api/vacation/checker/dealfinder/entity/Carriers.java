package api.vacation.checker.dealfinder.entity;

import com.fasterxml.jackson.annotation.JsonAlias;


public class Carriers {

    @JsonAlias("CarrierId")
    private int carrierId;
    @JsonAlias("Name")
    private String name;

    public Carriers() {}

    public int getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
