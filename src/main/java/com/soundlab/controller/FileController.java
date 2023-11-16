package com.soundlab.controller;

import com.soundlab.domain.File;
import com.soundlab.service.base.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Rest Controller for files related operation
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/data/files/")
public class FileController {
  private final StorageService<MultipartFile, File, String> service;

  @PostMapping
  public String store(@RequestParam("file")MultipartFile file) {
    return this.service.store(file);
  }

  @GetMapping("{file_id}")
  public ResponseEntity<Resource> download(@PathVariable(value = "file_id") String id) {
    File f = this.service.get(id);
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(f.getType()))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + f.getName() + "\"")
        .body(new ByteArrayResource(f.getData()));
  }
}
