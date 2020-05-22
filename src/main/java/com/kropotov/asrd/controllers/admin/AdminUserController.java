package com.kropotov.asrd.controllers.admin;

import com.kropotov.asrd.entities.SystemUser;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.services.UserService;
import com.kropotov.asrd.services.springdatajpa.security.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;
    private final RoleServiceImpl roleServiceImpl;

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
        return "admin/user_form";
    }

    @GetMapping("/user/{id}/edit")
    public String editUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("activePage", "Users");
        model.addAttribute("edit", true);
        model.addAttribute("user", userService.getById(id).orElse(new User()));
        model.addAttribute("allRoles", roleServiceImpl.getAll());
        return "admin/user_form";
    }

    @PostMapping("/user")
    public String saveUser(SystemUser user, RedirectAttributes redirectAttributes) {
        try {
            userService.save(user);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", true);
            if(user.getId() == null) {
                return "redirect:/admin/user/create";
            }
            else return "redirect:/admin/user/" + user.getId() + "/edit";
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/user/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
