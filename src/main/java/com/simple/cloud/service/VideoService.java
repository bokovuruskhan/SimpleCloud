package com.simple.cloud.service;

import com.simple.cloud.data.model.Video;
import com.simple.cloud.data.repo.VideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
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

    public Video saveVideo(Video video) {
        return videoRepository.save(video);
    }

    public Video attachFile(Long id, MultipartFile file) {
        Video video = getVideoById(id);
        try {
            File savedFile = new File(String.format(VIDEO_FILE_PATH_FORMAT, video.getId()));
            file.transferTo(savedFile);
            video.setAttachFilePath(savedFile.getPath());
            videoRepository.save(video);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return video;
    }

    public Video getVideoById(Long id) {
        Optional<Video> video = videoRepository.findById(id);
        if (video.isPresent()) {
            return video.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Video with id %d not found", id));
        }
    }

    public void deleteVideoById(Long id) {
        if (videoRepository.existsById(id)) {
            videoRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Video with id %d not found", id));
        }
    }

}
