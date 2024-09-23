package com.ddd.service;

import com.ddd.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface uploadService {
	ResponseResult upload(MultipartFile file) throws IOException;
}
