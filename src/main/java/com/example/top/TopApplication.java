package com.example.top;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.IOException;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TopApplication {

	public static void main(String[] args) throws IOException {
		var ctx = new SpringApplicationBuilder(TopApplication.class)
				.headless(false).run(args);

		TopSwing.run();
	}

}
