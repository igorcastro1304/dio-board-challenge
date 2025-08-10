package br.com.dio.board;

import br.com.dio.board.persistence.migration.MigrationStrategy;
import br.com.dio.board.ui.MainMenu;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	CommandLineRunner runMigrations(MigrationStrategy migrationStrategy) {
		return args -> {
			migrationStrategy.executeMigration();
			
			 new MainMenu().execute();
		};
	}
}