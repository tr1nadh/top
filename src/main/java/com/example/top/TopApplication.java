package com.example.top;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.IOException;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TopApplication {

	public static void main(String[] args) throws IOException {
		launchWithSwing(false, args);
	}

	private static void launchWithSwing(boolean run, String[] args) {
		if (run) {
			runWithSwing(args);
			return;
		}

		SpringApplication.run(TopApplication.class, args);
	}

	private static void runWithSwing(String[] args) {
		var ctx = new SpringApplicationBuilder(TopApplication.class)
				.headless(false).run(args);

		TopSwing.run();
	}

}
