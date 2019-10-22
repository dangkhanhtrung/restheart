package com.fds.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/v1/ping")
public class PingController {
	@GetMapping
	@ResponseBody
	public String ping() {
		return "";
	}

}
