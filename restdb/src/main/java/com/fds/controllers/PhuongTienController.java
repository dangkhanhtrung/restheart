package com.fds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fds.repository.qlvtdb.PhuongTienRepository;

@Controller
@RequestMapping(value = "/api/v1/phuongtiens")
public class PhuongTienController {
	@Autowired
	PhuongTienRepository phuongTienRepository;
}
