package com.clone.UTube.service.Impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.clone.UTube.entity.Video;
import com.clone.UTube.repository.VideoRepository;
import com.clone.UTube.service.VideoService;

@Service
public class VideoServiceImpl implements VideoService{
	
	public static final String vidDir="E:\\Videos";
	
	@Autowired
	private VideoRepository videoRepository;

	@Override
	public String uploadVideo(MultipartFile file, String title, String description) {

		try {
            // Store the video file locally
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(vidDir, fileName);
            Files.copy(file.getInputStream(), filePath);

            // Save video metadata to the database
            Video video = new Video();
            video.setTitle(title);
            video.setDescription(description);
            video.setVideoPath(filePath.toString());
            videoRepository.save(video);

            return "Video uploaded successfully!";
        } catch (Exception e) {
            return "Video upload failed.";
        }
		
	}

}
