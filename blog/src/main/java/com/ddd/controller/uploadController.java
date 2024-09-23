package com.ddd.controller;

import com.ddd.domain.ResponseResult;
import com.ddd.service.uploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class uploadController {
	@Autowired
	private uploadService uploadService;

	@PostMapping("/upload")
	public ResponseResult upload(MultipartFile img) throws IOException {
		ResponseResult result = uploadService.upload(img);
		return result;
	}

}
