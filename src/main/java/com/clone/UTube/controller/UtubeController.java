package com.clone.UTube.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.clone.UTube.entity.Video;
import com.clone.UTube.repository.VideoRepository;
import com.clone.UTube.service.VideoService;

@RestController
@RequestMapping("/videos")
@CrossOrigin("*")
public class UtubeController {
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private VideoRepository videoRepository;
	
	@GetMapping("/")
	public String index() {
		return "Inside index";
	}
	
	 @PostMapping("/upload")
	 public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file,
	                                              @RequestParam("title") String title,
	                                              @RequestParam("description") String description) {
		 return ResponseEntity.ok(videoService.uploadVideo(file, title, description));
	 }
	 
	 @GetMapping("/{id}")
	    public ResponseEntity<Resource> streamVideo(@PathVariable int id) {
	        Video video = videoRepository.findById(id).orElseThrow(() -> new RuntimeException("Video not found"));

	        File file = new File(video.getVideoPath());
	        Resource resource = new FileSystemResource(file);

	        return ResponseEntity.ok()
	                .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
	                .body(resource);
	    }
	
}
