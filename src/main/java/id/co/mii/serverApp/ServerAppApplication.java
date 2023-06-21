package id.co.mii.serverApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerAppApplication.class, args);

		System.out.println();
		System.out.println("ServerApp is running...");
		System.out.println();
	}

}
