package com.clone.UTube.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
			 									@RequestParam("image") MultipartFile image,
	                                              @RequestParam("title") String title,
	                                              @RequestParam("description") String description) throws IOException {
		 byte[] img =image.getBytes();
		 String imgType=image.getContentType();
		 System.err.println(  videoService.uploadVideo(file, title, description,img,imgType));
		 return ResponseEntity.ok("Done");
	 }
	 
	 @GetMapping("/video/{id}")
	    public ResponseEntity<Resource> streamVideo(@PathVariable int id) {
	        Video video = videoRepository.findById(id).orElseThrow(() -> new RuntimeException("Video not found"));

	        File file = new File(video.getVideoPath());
	        Resource resource = new FileSystemResource(file);

	        return ResponseEntity.ok()
	                .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
	                .body(resource);
	 }
	 
	 @GetMapping("/getAllVideos")
	 public ResponseEntity<List<Video>> getAllVideos(){
		 return ResponseEntity.ok(videoService.getAllVideos());
	 }
	
	 @GetMapping("/image/{id}")
	 public ResponseEntity<byte[]> getImageByVideoId(@PathVariable("id") int videoId){
		 Video video=videoService.getVideoById(videoId);
		 byte[] imageFile= video.getImageFile();
		 
		 return ResponseEntity.ok()
				 .contentType(MediaType.valueOf(video.getImageType()))
				 .body(imageFile);
	 }
	 
	 @GetMapping("/stream/{videoId}")
	 public ResponseEntity<Resource> stream(@PathVariable int videoId){
		 
		 Video video = videoService.getVideoById(videoId);
		 String filePath = video.getVideoPath();
		 String contentType ="application/octat-stream";
		 Resource resource =new FileSystemResource(filePath);
		 return ResponseEntity.ok()
				 .contentType(MediaType.parseMediaType(contentType))
				 .body(resource);
		 
	 }
	 
	 @GetMapping("/stream/range/{videoId}")
	 public ResponseEntity<Resource> streamRange(@PathVariable int videoId,
			 									@RequestHeader(value = "Range",required = false)String range){
		 System.out.println("range "+range);
		 
		 Video video=videoService.getVideoById(videoId);
		 Path path= Paths.get(video.getVideoPath());
		 Resource resource =new FileSystemResource(path);
		 String contentType ="application/octat-stream";
		 long fileLength = path.toFile().length();
		 if(range==null) {
			 return ResponseEntity.ok()
					 .contentType(MediaType.parseMediaType(contentType))
					 .body(resource);
		 }
		 
		 long  rangeStart,rangeEnd;
		 String[] ranges = range.replace("bytes","").split("-");
		 rangeStart = Long.parseLong(ranges[0].trim());
		 if(ranges.length>1) {
			 rangeEnd=Long.parseLong(ranges[1]);
		 }
		 else {
			 rangeEnd=fileLength-1;
		 }
		 
		 if(rangeEnd>fileLength) {
			 rangeEnd = fileLength-1;
		 }
		 InputStream inputStream;
		 try {
			 
			 inputStream = Files.newInputStream(path);
			 inputStream.skip(rangeStart);
			 
		 }catch(IOException e) {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		 }
		 
		 long contentLength = rangeEnd-rangeStart+1;
		 System.out.println("start = "+rangeStart+"\nEnd = "+rangeEnd);
		 
		 HttpHeaders headers =new  HttpHeaders();
		 headers.add("Content-Range", "bytes "+rangeStart+"-"+rangeEnd+"/"+fileLength);
		 headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		 headers.add("Pragma", "no-cache");
		 headers.add("Expires", "0");
		 headers.add("X-Content-Type-Options", "nosniff");
		 headers.setContentLength(contentLength);
		 return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
				 .headers(headers)
				 .contentType(MediaType.parseMediaType(contentType))
				 .body(new InputStreamResource(inputStream));
	 }
	 
}
