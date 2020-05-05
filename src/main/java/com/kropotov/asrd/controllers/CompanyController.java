package com.kropotov.asrd.controllers;

import com.kropotov.asrd.converters.UserToSimple;
import com.kropotov.asrd.entities.Company;
import com.kropotov.asrd.services.springdatajpa.titles.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final UserToSimple userToSimple;


    @GetMapping("")
    public String showCompany(Model model) {
        List<Company> companies = companyService.getAll();
        model.addAttribute("companies", companies);
        return "companies/list-companies";
    }

    @GetMapping("/{id}")
    public String editCompanyPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("company", companyService.getById(id).orElse(new Company()));
        return "companies/edit-company";
    }

    // @Valid проверяет в соответствии с аннотациями сущности
    // результаты проверки приходят в BindingResult
    @PostMapping("/edit")
    public String editCompany(@Valid @ModelAttribute("company") Company company, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("companyCreationError", "BindingResult error!");
            return "companies/edit-company";
        }
        Company existing = companyService.getByTitle(company.getTitle());
        if (existing != null && !existing.getId().equals(company.getId())) {
            model.addAttribute("company", company);
            model.addAttribute("companyCreationError", "Company title already exists");
            return "companies/edit-company";
        }
        companyService.save(company);
        return "redirect:/companies";
    }

}
