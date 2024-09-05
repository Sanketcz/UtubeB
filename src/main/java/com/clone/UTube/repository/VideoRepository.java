package com.clone.UTube.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.UTube.entity.Video;

public interface VideoRepository extends JpaRepository<Video,Integer>{

}
