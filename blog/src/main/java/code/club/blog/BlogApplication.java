package code.club.blog;

import org.apache.catalina.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
//	// Start internal H2 server so we can query the DB from IDE
//	@Bean(initMethod = "start", destroyMethod = "stop")
//	public Server inMemoryH2DatabaseServer() throws SQLException {
//		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
//	}
}

// controller -  створення ендпойнтів та перенавлення запитів на сервіси
// service -  реазація основної логіки проекту
// model - Post, User, Comment
// repository - робота із базою даних
//
// методи HTTP
// GET - отримати інформацію
// POST - додавати інформацію на сервер,
// PUT - оновлення даних
// DELETE -видалення
