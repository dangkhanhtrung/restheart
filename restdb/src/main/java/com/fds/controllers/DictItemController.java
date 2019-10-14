package com.fds.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fds.repository.coredb.DictItem;
import com.fds.repository.coredb.DictItemRepository;

@Controller
@RequestMapping(value = "/api/v1/dictitems")
public class DictItemController {
	@Autowired
	DictItemRepository dictItemRepository;
	
	@GetMapping
	@ResponseBody
	public List<DictItem> getAllDictItems() {
		return dictItemRepository.findAll();
	}
	
	@GetMapping(value = "/{shortName}")
	@ResponseBody
    public DictItem getDictItemByShortName(@PathVariable("shortName") String shortName) {
        return dictItemRepository.findByShortName(shortName);
    }

	@GetMapping(value = "/{shortName}/{collectionCode}")
	@ResponseBody
    public DictItem getDictItemByShortNameAndCollection(@PathVariable("shortName") String shortName, @PathVariable("collectionCode") String collectionCode) {
        return dictItemRepository.findByShortNameAndCollection(shortName, collectionCode);
    }	

	@GetMapping(value = "/find/{collectionCode}")
	@ResponseBody
    public List<DictItem> findDictItemByShortNameAndCollection(@RequestParam(name = "shortName", required = false) String shortName, @RequestParam(name = "title", required = false) String title, @PathVariable("collectionCode") String collectionCode) {
        if (shortName != null && !"".contentEquals(shortName)) {
    		return dictItemRepository.findLikeShortName(shortName, collectionCode);        	
        }
        if (title != null && !"".contentEquals(title)) {
    		return dictItemRepository.findLikeTitle(title, collectionCode);        	
        }
        return dictItemRepository.findLikeTitle(title, collectionCode);
    }	
}
