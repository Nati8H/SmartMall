package com.ngsolutions.SmartMall.service;

import com.ngsolutions.SmartMall.model.dto.user.UserEditDTO;
import com.ngsolutions.SmartMall.model.dto.user.UserEditRolesDTO;
import com.ngsolutions.SmartMall.model.dto.user.UserRegistrationDTO;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.List;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO);

    void updateUser(UserEditDTO userEditDTO) throws IOException;

    void updateUsersRoles(List<UserEditRolesDTO> usersEdit);

    UserEditDTO getUserEditDTOByEmail(String email) throws IOException;

    List<UserEditRolesDTO> getUserEditRolesDTOs();

    void createUserIfNotExist(String email, String names);

    Authentication login(String email);
}
