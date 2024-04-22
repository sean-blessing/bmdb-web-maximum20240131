package com.bmdb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.db.ActorRepo;
import com.bmdb.model.Actor;
import com.bmdb.model.Actor;

@CrossOrigin
@RestController
@RequestMapping("/api/actors")
public class ActorController {
	
	@Autowired
	private ActorRepo actorRepo;
	
	@GetMapping("/")
	public List<Actor> getAllActors() {
		return actorRepo.findAll();
	}

	@GetMapping("/{id}")
	public Actor getActorById(@PathVariable int id) {
		Optional<Actor> a = actorRepo.findById(id);
		if (a.isPresent()) {
			return a.get();
		}
		else {
			System.err.println("Get Actor error: id ["+id+"] does not exist.");
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Actor Not Found: id ["+id+"]");
		}
	}
	
	@PostMapping("")
	public Actor addActor(@RequestBody Actor actor) {
		return actorRepo.save(actor);
	}
	
	@PutMapping("/{id}")
	public Actor updateActor(@PathVariable int id, @RequestBody Actor actor) {
		Actor a = null;
		if (id != actor.getId()) {
			System.err.println("Actor id [" + actor.getId()+"] does not match path id["+id+"].");
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "Actor Not Found");
		}
		else if (!actorRepo.existsById(id)) {
			System.err.println("Actor does not exist for id: "+id);
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Actor Not Found");
		}
		else {
			try {
				a = actorRepo.save(actor);
			}
			catch (Exception e) {
				System.err.println(e);
				throw new ResponseStatusException(
						HttpStatus.INTERNAL_SERVER_ERROR, "Error Saving Actor");
			}
		}
		return a;
	}

	@DeleteMapping("/{id}")
	public boolean deleteActor(@PathVariable int id) {
		boolean success = false;
		if (actorRepo.existsById(id)) {
			try {
				actorRepo.deleteById(id);
			}
			catch (Exception e) {
				throw new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Error deleting Actor for id: "+id);
			}
			success = true;
		}
		else {
			System.err.println("Delete Error: No actor exists for id: "+id);
			success = false;
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Actor Not Found");
		}
		return success;
	}

}
