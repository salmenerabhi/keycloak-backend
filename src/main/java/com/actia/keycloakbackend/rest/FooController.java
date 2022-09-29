package com.actia.keycloakbackend.rest;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.actia.keycloakbackend.domain.dto.ResponseMessage;
import com.actia.keycloakbackend.domain.model.Foo;

@RestController
@RequestMapping("/foo")
@CrossOrigin
public class FooController {

	List<Foo> foos = Stream.of(new Foo(1, "foo1"), new Foo(2, "foo2"), new Foo(3, "foo3")).collect(Collectors.toList());

	@GetMapping("/list")
	public ResponseEntity<List<Foo>> list() {
		return new ResponseEntity(foos, HttpStatus.OK);
	}

	@GetMapping("/detail/{id}")
	public ResponseEntity<Foo> detail (@PathVariable("id") long id) {
		Foo foo= foos.stream().filter(f->f.getId()==id).findFirst().orElse(null);
		return new ResponseEntity(foo,HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody Foo foo) {
		long maxIndex= foos.stream().max(Comparator.comparing(m->m.getId())).get().getId();
		foo.setId(maxIndex+1);
		foos.add(foo);
		return new ResponseEntity(new ResponseMessage("created"), HttpStatus.OK);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		Foo foo= foos.stream().filter(f->f.getId()==id).findFirst().orElse(null);
		foos.remove(foo);
		return new ResponseEntity(new ResponseMessage("deleted"), HttpStatus.OK);
	}
	
}
