package org.example;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Config;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

public class DriverTest {

	private Config OPTIONS = Config.builder()
			.withMaxConnectionLifetime(8, TimeUnit.MINUTES)
			.build();

	private Driver driver = GraphDatabase.driver("bolt://localhost", AuthTokens.basic("neo4j", "password"), OPTIONS);;

	@Test
	void should_connect_to_neo4j() {

		try (Session session = driver.session()) {
			Result results = session.run("RETURN 1");
			Record single = results.single();
			System.out.println(single.asMap());
		}
	}

	@Test
	void should_list_persons() {

		try (Session session = driver.session()) {
			Result results = session.run("MATCH (p:Person) RETURN p.name as name");
			results.forEachRemaining(record -> System.out.println(record.get("name").asString()));
		}
	}

	@Test
	void should_create_actor_and_movie() {

		try (Session session = driver.session()) {
			session.run("CREATE (m:Movie {})").consume();

			session.writeTransaction(tx -> {
				tx.run("CREATE (a:Actor {name:$name})", Map.of("name", "Tom Hanks")).consume();
				tx.run("CREAT (a:Movie {title:$title})", Map.of("title", "Apollo 13")).consume();
				return null;
			});
		}
	}
}
