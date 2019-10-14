package com.fds.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fds.repository.coredb.DictCollection;
import com.fds.repository.coredb.DictCollectionRepository;

@Controller
@RequestMapping(value = "/api/v1/dictcollections")
public class DictCollectionController {
	@Autowired
	DictCollectionRepository dictCollectionRepository;
	
	@GetMapping
	@ResponseBody
	public List<DictCollection> getAllDictCollections() {
		return dictCollectionRepository.findAll();
	}
	
	@GetMapping(value = "/{shortName}")
	@ResponseBody
    public DictCollection getDictCollectionByShortName(@PathVariable("shortName") String shortName) {
        return dictCollectionRepository.findByShortName(shortName);
    }
	
}
