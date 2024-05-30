package com.soundlab.service.base;

import org.springframework.core.io.InputStreamSource;

public interface StorageService <S extends InputStreamSource, R, K>{
  K store(S s);
  R get(K k);
}