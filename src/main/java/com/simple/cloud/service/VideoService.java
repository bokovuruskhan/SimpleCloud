package com.simple.cloud.service;

import com.simple.cloud.data.model.Video;
import com.simple.cloud.data.repo.VideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Optional;

@Service
public class VideoService {

    private final Logger logger = LoggerFactory.getLogger(VideoService.class);
    protected static final String VIDEO_FILE_NAME_FORMAT = "%d.mp4";
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    FileService fileService;

    public Mono<Resource> getVideoResource(Long videoId) {
        Video video = getVideoById(videoId);
        return Mono.fromSupplier(() -> fileService.loadFile(video.getAttachFilePath()));
    }

    public Video saveVideo(Video video) {
        return videoRepository.save(video);
    }

    public Video attachFile(Long id, MultipartFile file) {
        Video video = getVideoById(id);
        try {
            String path = fileService.saveFile(file.getBytes(), String.format(VIDEO_FILE_NAME_FORMAT, video.getId()));
            video.setAttachFilePath(path);
            videoRepository.save(video);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return video;
    }

    public Iterable<Video> getAllVideo() {
        return videoRepository.findAll();
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
