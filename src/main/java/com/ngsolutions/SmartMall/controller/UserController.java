package com.ngsolutions.SmartMall.controller;

import com.ngsolutions.SmartMall.model.dto.user.UserEditDTO;
import com.ngsolutions.SmartMall.model.dto.user.UserEditRolesDTO;
import com.ngsolutions.SmartMall.model.dto.user.UserEditRolesDTOHolder;
import com.ngsolutions.SmartMall.model.entity.Role;
import com.ngsolutions.SmartMall.service.RoleService;
import com.ngsolutions.SmartMall.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users/edit/settings")
    public ModelAndView editUser(Model model, @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        UserEditDTO user = userService.getUserEditDTOByEmail(userDetails.getUsername());

        if (!model.containsAttribute("userEditDTO")) {
            model.addAttribute("userEditDTO", user);
        }

        return new ModelAndView("profile-settings");
    }

    @PostMapping("/users/edit/settings")
    public String editUser(@ModelAttribute("userEditDTO") @Valid UserEditDTO userEditDTO, BindingResult bindingResult) throws IOException {
        if (!bindingResult.hasErrors()) {
            UserEditDTO user = userService.getUserEditDTOByEmail(userEditDTO.getEmail());
            userEditDTO.setPassword(user.getPassword());
            userEditDTO.setRoles(user.getRoles());
            userService.updateUser(userEditDTO);
        }

        return ("redirect:/users/edit/settings");
    }

    @GetMapping("/users/manage-roles")
    public ModelAndView manageUserRoles(Model model, @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        UserEditDTO user = userService.getUserEditDTOByEmail(userDetails.getUsername());
        UserEditRolesDTOHolder usersHolder = new UserEditRolesDTOHolder();
        usersHolder.setUsers(userService.getUserEditRolesDTOs());
        List<Role> roles = roleService.getAllRoles();

        if (!model.containsAttribute("userEditDTO")) {
            model.addAttribute("userEditDTO", user);
        }

        if (!model.containsAttribute("usersHolder")) {
            model.addAttribute("usersHolder", usersHolder);
        }

        if (!model.containsAttribute("allRoles")) {
            model.addAttribute("allRoles", roles);
        }

        return new ModelAndView("manage-user-roles");
    }
}
