package com.bmdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bmdb.db.ActorRepo;
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

}
