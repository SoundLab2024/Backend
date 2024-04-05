package com.soundlab.utils.mappers;

import com.soundlab.domain.User;
import com.soundlab.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<User, UserDTO> {
    @Mappings({
            @Mapping(source = "email", target = "email"),
            @Mapping(target = "username", expression = "java(user.getName())"),
            @Mapping(source = "role", target = "role")
    })
    @Override
    UserDTO toDTO(User user);

    @Mappings({
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "role", target = "role")
    })
    @Override
    User toEntity(UserDTO userDto);
}