package com.ddd.controller;

import com.ddd.domain.ResponseResult;
import com.ddd.service.uploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {

	@Autowired
	 private com.ddd.service.uploadService uploadService;

	@PreAuthorize("@ps.hasPermission('content:category:export')")
	@PostMapping("/upload")
	public ResponseResult uploadImg(@RequestParam("img") MultipartFile multipartFile){
		try {
			return uploadService.upload(multipartFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("文件上传上传失败");
		}
	}
}
