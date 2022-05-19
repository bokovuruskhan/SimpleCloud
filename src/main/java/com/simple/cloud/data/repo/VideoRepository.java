package com.simple.cloud.data.repo;

import com.simple.cloud.data.model.Video;
import org.springframework.data.repository.CrudRepository;

public interface VideoRepository extends CrudRepository<Video, Long> {
}
