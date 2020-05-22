package com.kropotov.asrd.controllers.admin;

import com.kropotov.asrd.entities.Role;
import com.kropotov.asrd.services.springdatajpa.security.RoleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminRoleController {

    private final RoleServiceImpl roleService;

    public AdminRoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public String rolePage(Model model) {
        model.addAttribute("activePage", "Roles");
        model.addAttribute("roles", roleService.getAll());
        return "/admin/roles";
    }

    @GetMapping("/role/create")
    public String roleCreate(Model model) {
        model.addAttribute("activePage", "Roles");
        model.addAttribute("create", true);
        model.addAttribute("role", new Role());
        return "/admin/role_form";
    }

    @GetMapping("/role/{id}/edit")
    public String roleEdit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("activePage", "Roles");
        model.addAttribute("edit", true);
        model.addAttribute("role", roleService.getById(id).orElse(new Role()));
        return "/admin/role_form";
    }

    @PostMapping("/role")
    public String saveRole(Role role, RedirectAttributes redirectAttributes) {
        try {
            roleService.save(role);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", true);
            if(role.getId() == null) {
                return "redirect:admin/role/create";
            }
            else return "redirect:admin/" + role.getId() + "role/edit";
        }
        return "redirect:/admin/roles";
    }

    @GetMapping("/role/{id}/delete")
    public String deleteRole(@PathVariable("id") Long id, Model model) {
        model.addAttribute("activePage", "Roles");
        try {
            roleService.delete(id);
        } catch (Exception ex) {
            log.info("failed to delete the role = {}! {}", id, ex);
        }
        return "redirect:/admin/roles";
    }
}
