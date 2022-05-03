package microservices.example.quoteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class QuoteserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuoteserviceApplication.class, args);
	}

}
