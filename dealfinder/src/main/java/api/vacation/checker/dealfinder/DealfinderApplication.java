package api.vacation.checker.dealfinder;

import api.vacation.checker.dealfinder.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DealfinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealfinderApplication.class, args);
        //SearchService.execute(null);
    }
}
