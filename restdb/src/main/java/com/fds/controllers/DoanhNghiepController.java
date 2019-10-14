package com.fds.controllers;

import com.fds.repository.qlvtdb.DoanhNghiepRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/v1/doanhnghieps")
public class DoanhNghiepController {
	@Autowired
	DoanhNghiepRepository doanhNghiepRepository;
	
}
