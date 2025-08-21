package uk.co.danielbryant.djshopping.shopfront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableHystrix
public class ShopfrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopfrontApplication.class, args);
    }

    @Bean(name = "stdRestTemplate")
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
