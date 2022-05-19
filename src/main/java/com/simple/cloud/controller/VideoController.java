package com.simple.cloud.controller;

import com.simple.cloud.data.model.Video;
import com.simple.cloud.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController("video")
public class VideoController {

    @Autowired
    VideoService videoService;

    @GetMapping("/{id}")
    public Video get(@PathVariable Long id) {
        Optional<Video> video = videoService.getVideoById(id);
        if (video.isPresent()) {
            return video.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Video with id %d not found", id));
        }
    }

    @PostMapping()
    public Video create(@RequestBody Video video, @RequestParam("file") MultipartFile file) {
        return videoService.saveVideo(video, file);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        videoService.deleteVideoById(id);
    }

    @GetMapping(value = "/video/{id}/stream", produces = "video/mp4")
    public Mono<Resource> getStream(@PathVariable Long id) {
        return videoService.getVideoResource(id);
    }

}
