package com.bmdb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
		//TODO Add null check for valid id
		return m.get();
	}
	
	@PostMapping("")
	public Movie addMovie(@RequestBody Movie movie) {
		//TODO Check for existence by movie.getId() before save?
		return movieRepo.save(movie);
	}
	
	@PutMapping("/{id}")
	public Movie updateMovie(@PathVariable int id, @RequestBody Movie movie) {
		Movie m = null;
		if (id != movie.getId()) {
			System.err.println("Movie id does not match path id.");
			//TODO Return error to front end.
		}
		else if (!movieRepo.existsById(id)) {
			System.err.println("Movie does not exist for id: "+id);
			//TODO Return error to front end.
		}
		else {
			m = movieRepo.save(movie);
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
		}
		return success;
	}
	
}
