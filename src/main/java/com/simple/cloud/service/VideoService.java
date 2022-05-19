package com.simple.cloud.service;

import com.simple.cloud.data.model.Video;
import com.simple.cloud.data.repo.VideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class VideoService {

    private final Logger logger = LoggerFactory.getLogger(VideoService.class);
    protected static final String VIDEO_FILE_PATH_FORMAT = "classpath:videos/%d.mp4";
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    VideoRepository videoRepository;

    public Mono<Resource> getVideoResource(Long videoId) {
        return Mono.fromSupplier(() -> resourceLoader.getResource(String.format(VIDEO_FILE_PATH_FORMAT, videoId)));
    }

    public Video saveVideo(Video video, MultipartFile file) {
        Video savedVideo = videoRepository.save(video);
        try {
            file.transferTo(new File(String.format(VIDEO_FILE_PATH_FORMAT, savedVideo.getId())));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return savedVideo;
    }

    public Optional<Video> getVideoById(Long id) {
        return videoRepository.findById(id);
    }

    public void deleteVideoById(Long id) {
        videoRepository.deleteById(id);
    }


}
