package it.trefin.gestioneCinema.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.trefin.gestioneCinema.model.Projection;
import it.trefin.gestioneCinema.model.keys.ProjectionKey;
import it.trefin.gestioneCinema.service.ProjectionService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/projection")
//@CrossOrigin(origins = "http://localhost:4200")	
public class ProjectionController {

	private final ProjectionService service;

	public ProjectionController(ProjectionService service) {
		this.service = service;
	}
	
	@GetMapping("/all")
	public List<Projection> getAll(){
		return service.getAll();
	}
	
	@GetMapping("/projectionsByMovie/{id}")
	public List<Projection> getProjectionsForMovie(@PathVariable long id){
		return service.getProjectionsForMovie(id);
	}
	
	@GetMapping("byMovieTitle/{title}")
	public List<Projection> getByMovieTitle(@PathVariable String title){
		return service.findByMovieTitle(title);
	}
	
	@GetMapping("/projectionsByData")
	public List<Projection> getProjectionsForData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data){
		return service.getProjectionsForData(data);
	}
	
	@PostMapping("/insert")
	@Transactional
	public void insert(@RequestBody Projection p) {
		service.insert(p);
	}
	
	@GetMapping("/seatsLeft")
	public int seatsLeft(@RequestParam LocalDateTime projection_time, @RequestParam String hall) {
		return service.seatsLeft(projection_time, hall);
	}
	
}
