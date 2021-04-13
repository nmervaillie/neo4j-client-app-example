package org.example.demo;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	public Driver driver;

	@Autowired
	public Neo4jClient client;

	@Autowired
	public Neo4jTemplate template;

	@Test
	void contextLoads() {
	}

	@Test
	void should_query_database() {
		try (Session session = driver.session()) {
			Result results = session.run("RETURN 1");
			Record single = results.single();
			System.out.println(single.asMap());
		}
	}

	@Test
	void should_query_using_client() {
		Optional<String> movies = client.query("MATCH (n:Movie) RETURN n.title").fetchAs(String.class).one();
		System.out.println(movies);
	}

	@Test
	void should_query_using_template() {
		List<Movie> movies = template.findAll(Movie.class);
		System.out.println(movies);
	}
}
