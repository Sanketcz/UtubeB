package com.clone.UTube.service;

import org.springframework.web.multipart.MultipartFile;

import com.clone.UTube.entity.Video;

public interface VideoService {

	public String uploadVideo(MultipartFile file,String title,String description);
	
}
