package com.clone.UTube.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Video {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String description;
	private String videoPath;
	private Date uploadDate;
	public Video(int id, String title, String description, String videoPath, Date uploadDate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.videoPath = videoPath;
		this.uploadDate = uploadDate;
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
