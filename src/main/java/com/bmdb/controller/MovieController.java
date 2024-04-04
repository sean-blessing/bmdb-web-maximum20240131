package com.bmdb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.db.MovieRepo;
import com.bmdb.model.Movie;

@CrossOrigin
@RestController
@RequestMapping("/api/movies")
public class MovieController {
	
	@Autowired
	private MovieRepo movieRepo;
	
	@GetMapping("/")
	public List<Movie> getAllMovies() {
		return movieRepo.findAll();
	}

	@GetMapping("/{id}")
	public Movie getMovieById(@PathVariable int id) {
		Optional<Movie> m = movieRepo.findById(id);
		if (m.isPresent()) {
			return m.get();
		}
		else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Movie Not Found: id ["+id+"]");
		}
	}
	
	@PostMapping("")
	public Movie addMovie(@RequestBody Movie movie) {
		return movieRepo.save(movie);
	}
	
	@PutMapping("/{id}")
	public Movie updateMovie(@PathVariable int id, @RequestBody Movie movie) {
		Movie m = null;
		if (id != movie.getId()) {
			System.err.println("Movie id [" + movie.getId()+"] does not match path id["+id+"].");
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "Movie Not Found");
		}
		else if (!movieRepo.existsById(id)) {
			System.err.println("Movie does not exist for id: "+id);
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Movie Not Found");
		}
		else {
			try {
				m = movieRepo.save(movie);
			}
			catch (Exception e) {
				System.err.println(e);
				throw e;
			}
		}
		return m;
	}

	@DeleteMapping("/{id}")
	public boolean deleteMovie(@PathVariable int id) {
		boolean success = false;
		if (movieRepo.existsById(id)) {
			movieRepo.deleteById(id);
			success = true;
		}
		else {
			System.err.println("Delete Error: No movie exists for id: "+id);
			success = false;
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Movie Not Found");
		}
		return success;
	}
	
	@GetMapping("/rating/{rating}")
	public List<Movie> getMoviesByRating(@PathVariable String rating) {
		List<Movie> movies = movieRepo.findAllByRating(rating);
		return movies;
		
	}
	
	@GetMapping("/rating-gt-year/{rating}/{year}")
	public List<Movie> getMoviesByRatingGreaterThanYear(@PathVariable String rating, @PathVariable int year) {
		List<Movie> movies = movieRepo.findAllByRatingAndYearGreaterThan(rating, year);
		return movies;	
	}
}
