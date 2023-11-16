package com.soundlab.utils.mappers;

import java.util.List;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MapperConfig;

@MapperConfig
public interface BaseMapper<S, T> {
  T toDTO(S s);

  @InheritInverseConfiguration(name = "toDto")
  S toEntity(T t);

  @InheritConfiguration(name = "toDto")
  List<T> toDto(List<S> sources);

  @InheritConfiguration(name = "toEntity")
  List<S> toEntity(List<T> targets);
}