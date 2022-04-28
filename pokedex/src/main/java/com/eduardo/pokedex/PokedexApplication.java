package com.eduardo.pokedex;

import com.eduardo.pokedex.model.Pokemon;
import com.eduardo.pokedex.repository.PokedexRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class PokedexApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokedexApplication.class, args);
	}

	@Bean
	CommandLineRunner init (ReactiveMongoOperations operations,
							PokedexRepository repository) {
		return args -> {
			Flux<Pokemon> pokemonFlux = Flux.just(
					new Pokemon(null, "Bulbassauro", "Semente", "OverGrow", 6.09),
					new Pokemon(null, "Charizard", "Fogo", "Blaze", 90.05),
					new Pokemon(null, "Caterpie", "Minhoca", "Poeira do escudo", 2.09),
					new Pokemon(null, "Blastoise", "Marisco", "Torrente", 12.09))
			.flatMap(repository::save);
			pokemonFlux
					.thenMany(repository.findAll())
					.subscribe(System.out::println);
		};
	}
}
