package com.simple.cloud.controller;

import com.simple.cloud.data.model.Video;
import com.simple.cloud.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    VideoService videoService;

    @GetMapping("/{id}")
    public Video get(@PathVariable Long id) {
        return videoService.getVideoById(id);
    }

    @PostMapping
    public Video create(@RequestParam("video") Video video) {
        return videoService.saveVideo(video);
    }

    @PostMapping("/{id}/attach")
    public Video attachFile(@PathVariable Long id, @RequestBody MultipartFile file) {
        return videoService.attachFile(id, file);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        videoService.deleteVideoById(id);
    }

    @GetMapping(value = "/{id}/stream", produces = "video/mp4")
    public Mono<Resource> getStream(@PathVariable Long id) {
        return videoService.getVideoResource(id);
    }

}
