package com.coredb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coredb.qlvt.repository.PhuongTienRepository;

@Controller
@RequestMapping(value = "/api/v1/phuongtiens")
public class PhuongTienController {
	@Autowired
	PhuongTienRepository phuongTienRepository;
}
