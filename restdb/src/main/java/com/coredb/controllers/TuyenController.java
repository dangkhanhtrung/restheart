package com.coredb.controllers;

import java.util.List;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coredb.qlvt.repository.*;
import com.coredb.qlvt.repository.TuyenRepository;

@Controller
@RequestMapping(value = "/api/v1/tuyens")
public class TuyenController {
	@Autowired
	TuyenRepository tuyenRepository;
	
	@GetMapping
	@ResponseBody
	public List<Tuyen> getAllTuyens() {
		return tuyenRepository.findAll();
	}

	@PostMapping
	@ResponseBody
	public Tuyen addNewTuyen(@Valid @RequestBody Tuyen tuyen) {
		tuyen.setId(ObjectId.get().toString());
	    tuyenRepository.save(tuyen);
	    return tuyen;
	}	
	
	@GetMapping(value = "/{id}")
	@ResponseBody
    public Tuyen getTuyenById(@PathVariable("id") String id) {
        return tuyenRepository.findById(id).get();
    }

	@PutMapping(value = "/{id}")
    public void updateTuyenById(@PathVariable("id") ObjectId id, @Valid @RequestBody Tuyen
    		 tuyen) {
        tuyen.setId(id.toString());
        tuyenRepository.save(tuyen);
    }
	
}
