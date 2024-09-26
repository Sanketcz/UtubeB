package com.clone.UTube.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.clone.UTube.entity.Video;

public interface VideoService {

	public String uploadVideo(MultipartFile file,String title,String description,byte[] image, String imageType);
	
	public List<Video> getAllVideos();
	
	public Video getVideoById(int id);
	
}
