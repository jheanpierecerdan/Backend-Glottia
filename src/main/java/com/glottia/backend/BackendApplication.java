package com.glottia.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		configureRenderDatabaseUrl();
		SpringApplication.run(BackendApplication.class, args);
	}

	private static void configureRenderDatabaseUrl() {
		if (hasValue(System.getenv("SPRING_DATASOURCE_URL")) || hasValue(System.getenv("DB_URL"))) {
			return;
		}

		String databaseUrl = System.getenv("DATABASE_URL");
		if (!hasValue(databaseUrl)) {
			return;
		}

		if (databaseUrl.startsWith("jdbc:postgresql://")) {
			System.setProperty("spring.datasource.url", databaseUrl);
			return;
		}

		URI uri = URI.create(databaseUrl);
		String[] userInfo = uri.getUserInfo() != null ? uri.getUserInfo().split(":", 2) : new String[0];
		String username = userInfo.length > 0 ? decode(userInfo[0]) : "";
		String password = userInfo.length > 1 ? decode(userInfo[1]) : "";
		String port = uri.getPort() > 0 ? ":" + uri.getPort() : "";
		String jdbcUrl = "jdbc:postgresql://" + uri.getHost() + port + uri.getPath();

		if (hasValue(uri.getQuery())) {
			jdbcUrl += "?" + uri.getQuery();
		}

		System.setProperty("spring.datasource.url", jdbcUrl);
		System.setProperty("spring.datasource.username", username);
		System.setProperty("spring.datasource.password", password);
	}

	private static boolean hasValue(String value) {
		return value != null && !value.isBlank();
	}

	private static String decode(String value) {
		return URLDecoder.decode(value, StandardCharsets.UTF_8);
	}

}
