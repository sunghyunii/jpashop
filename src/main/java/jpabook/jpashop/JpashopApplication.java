package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {
	public static void main(String[] args){
		SpringApplication.run(JpashopApplication.class, args);
	}
	@Bean
	Hibernate5JakartaModule hibernateModule(){
		Hibernate5JakartaModule hibernate5JakartaModule = new Hibernate5JakartaModule();
		//Hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING,true);
		return hibernate5JakartaModule;
	}

}


