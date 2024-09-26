package com.clone.UTube.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Video {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String description;
	private String videoPath;
	private Date uploadDate;
	private String imageType;
	@Lob
	private byte[] imageFile;
	
	public Video(int id, String title, String description, String videoPath, Date uploadDate,byte[] imageFile,String imageType) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.videoPath = videoPath;
		this.uploadDate = uploadDate;
		this.imageFile = imageFile;
		this.imageType =imageType;
	}
	
	
	public String getImageType() {
		return imageType;
	}


	public void setImageType(String imageType) {
		this.imageType = imageType;
	}


	public byte[] getImageFile() {
		return imageFile;
	}

	public void setImageFile(byte[] bs) {
		this.imageFile = bs;
	}

	public Video() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVideoPath() {
		return videoPath;
	}
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	@Override
	public String toString() {
		return "Video [id=" + id + ", title=" + title + ", description=" + description + ", videoPath=" + videoPath
				+ ", uploadDate=" + uploadDate + "]";
	}
	
	
	
}
