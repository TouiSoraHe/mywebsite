package cn.zzy.mywebsite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.zzy.mywebsite.Data.Mapper")
public class MywebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MywebsiteApplication.class, args);
	}


}
