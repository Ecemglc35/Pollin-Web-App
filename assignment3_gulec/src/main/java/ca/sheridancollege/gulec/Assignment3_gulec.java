package ca.sheridancollege.gulec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Assignment3_gulec {

	public static void main(String[] args) {
		SpringApplication.run(Assignment3_gulec.class, args);
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		String password = "Admin1*34";
//		//prints the salted password
//		System.out.println(encoder.encode(password));
		
	}

}
