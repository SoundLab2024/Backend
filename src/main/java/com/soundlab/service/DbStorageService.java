package com.soundlab.service;

import com.soundlab.domain.File;
import com.soundlab.repository.FileRepository;
import com.soundlab.service.base.StorageService;
import com.soundlab.utils.common.Generators;
import com.soundlab.utils.exceptions.StorageException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DbStorageService implements StorageService<MultipartFile, File, String> {
  private final FileRepository repository;

  @Override
  public String store(MultipartFile file) {
    try {
      return repository.save(
          File.builder()
              .name(Generators.generate(15))
              .type(file.getContentType())
              .data(file.getBytes())
              .build()
      ).getId();
    } catch (IOException e) {
      throw new RuntimeException("Failed to store file");
    }
  }

  @Override
  public File get(String s) {
    return this.repository.findById(s)
        .orElseThrow(() -> new StorageException("failed to retrieve specified file"));
  }
}