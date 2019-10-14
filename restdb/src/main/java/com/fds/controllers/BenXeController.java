package com.fds.controllers;

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

import com.fds.repository.qlvtdb.BenXe;
import com.fds.repository.qlvtdb.BenXeRepository;

@Controller
@RequestMapping(value = "/api/v1/benxes")
public class BenXeController {
	@Autowired
	BenXeRepository benXeRepository;
	
	@GetMapping
	@ResponseBody
	public List<BenXe> getAllBenXes() {
		return benXeRepository.findAll();
	}

	@PostMapping
	@ResponseBody
	public BenXe addNewBenXe(@Valid @RequestBody BenXe benXe) {
		benXe.setId(ObjectId.get().toString());
	    benXeRepository.save(benXe);
	    return benXe;
	}	
	
	@GetMapping(value = "/{id}")
	@ResponseBody
    public BenXe getBenXeById(@PathVariable("id") String id) {
        return benXeRepository.findById(id).get();
    }

	@PutMapping(value = "/{id}")
    public void updateBenXeById(@PathVariable("id") ObjectId id, @Valid @RequestBody BenXe
    		 benXe) {
        benXe.setId(id.toString());
        benXeRepository.save(benXe);
    }
	
	// @GetMapping(value = "/{ten_bx}")
	// @ResponseBody
    // public BenXe getBenXeByTenBenXe(@PathVariable("ten_bx") String ten_bx) {
    //     return benXeRepository.findByTen_bx(ten_bx);
    // }	
}
