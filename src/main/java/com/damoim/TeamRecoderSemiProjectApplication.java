package com.damoim;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("mapper")
public class TeamRecoderSemiProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamRecoderSemiProjectApplication.class, args);
	}

}
