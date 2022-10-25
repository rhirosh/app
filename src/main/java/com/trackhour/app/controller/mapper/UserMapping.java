package com.trackhour.app.controller.mapper;

import com.trackhour.app.controller.dto.UserRequest;
import com.trackhour.app.controller.dto.UserResponse;
import com.trackhour.app.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapping {

    User requestToModel(UserRequest request);

    UserResponse modelToRequest(User user);

    List<UserResponse> modelsToRequests(List<User> users);

}
