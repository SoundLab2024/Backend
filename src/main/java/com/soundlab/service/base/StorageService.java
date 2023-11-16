package com.soundlab.service.base;

import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;

public interface StorageService <S extends InputStreamSource, R, K>{
  K store(S s);
  R get(K k);
}