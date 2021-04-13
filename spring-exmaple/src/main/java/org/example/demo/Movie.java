package org.example.demo;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node(labels = "Movie")
public class Movie {

	@Id
	public Long id;
	public String title;
}
