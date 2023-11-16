package com.soundlab.utils.mappers;

import com.soundlab.domain.User;
import com.soundlab.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<User, UserDTO> {

  @Override
  UserDTO toDTO(User user);

  @Override
  User toEntity(UserDTO userDto);
}