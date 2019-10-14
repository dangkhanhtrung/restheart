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
import com.coredb.qlvt.repository.CuaKhauRepository;

@Controller
@RequestMapping(value = "/api/v1/cuakhaus")
public class CuaKhauController {
	@Autowired
	CuaKhauRepository cuaKhauRepository;
	
	@GetMapping
	@ResponseBody
	public List<CuaKhau> getAllCuaKhaus() {
		return cuaKhauRepository.findAll();
	}
	
	@PostMapping
	@ResponseBody
	public CuaKhau addNewCuaKhau(@Valid @RequestBody CuaKhau cuaKhau) {
		cuaKhau.setId(ObjectId.get().toString());
	    cuaKhauRepository.save(cuaKhau);
	    return cuaKhau;
	}	
	
	@GetMapping(value = "/{id}")
	@ResponseBody
    public CuaKhau getCuaKhauById(@PathVariable("id") String id) {
        return cuaKhauRepository.findById(id).get();
    }

	@PutMapping(value = "/{id}")
    public void updateCuaKhauById(@PathVariable("id") ObjectId id, @Valid @RequestBody CuaKhau
    		 cuaKhau) {
        cuaKhau.setId(id.toString());
        cuaKhauRepository.save(cuaKhau);
    }
	
}
