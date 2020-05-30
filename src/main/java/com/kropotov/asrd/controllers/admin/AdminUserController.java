package com.kropotov.asrd.controllers.admin;

import com.kropotov.asrd.entities.SystemUser;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.services.UserService;
import com.kropotov.asrd.services.springdatajpa.security.RoleServiceImpl;
import com.kropotov.asrd.services.springdatajpa.security.StatusUserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Collectors;

@Controller
@RequestMapping("admin")
@Slf4j
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;
    private final RoleServiceImpl roleServiceImpl;
    private final StatusUserServiceImpl statusUserService;

    @GetMapping("")
    public String adminStartPage() {
        return "admin/index";
    }

    @GetMapping("/users")
    public String usersPage(Model model) {
        model.addAttribute("activePage", "Users");
        model.addAttribute("users", userService.getAll());
        return "admin/users";
    }

    @GetMapping("/user/create")
    public String createUser(Model model) {
        model.addAttribute("activePage", "Users");
        model.addAttribute("create", true);
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleServiceImpl.getAll());
        model.addAttribute("allStatusUser", statusUserService.getAll());
        return "admin/user_form";
    }

    @GetMapping("/user/{id}/update")
    public String editUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("activePage", "Users");
        model.addAttribute("update", true);
        model.addAttribute("user", userService.getById(id).orElse(new User()));
        model.addAttribute("allRoles", roleServiceImpl.getAll());
        model.addAttribute("allStatusUser", statusUserService.getAll());
        return "admin/user_form";
    }

    @PostMapping("/user")
    public String saveUser(SystemUser user, RedirectAttributes redirectAttributes) {
        try {
            userService.saveDto(user);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", true);
            log.error("Failed to save the 'user' object with the username '{}'", user.getUserName());
            if(user.getId() == null) {
                return "redirect:/admin/user/create";
            }
            else return "redirect:/admin/user/" + user.getId() + "/edit";
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/user/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try{
            userService.deleteById(id);
        } catch (Exception ex) {
            log.error("Failed to delete an object by id = {}!!!", id);
            redirectAttributes.addFlashAttribute("error", true);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/usersNew")
    public String newUsersPage(Model model) {
        model.addAttribute("activePage", "NewUsers");
        model.addAttribute("users", userService.getAll().stream().filter((u) -> u.getStatusUser().getName().contains("confirmed")).collect(Collectors.toList()));
        return "admin/users";
    }

    @GetMapping("/user/{id}/activate")
    public String activUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try{
            userService.activateUser(id);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", true);
            log.error("The user's id = {} status could not be changed to active", id);
        }
        return "redirect:/admin/usersNew";
    }
}
