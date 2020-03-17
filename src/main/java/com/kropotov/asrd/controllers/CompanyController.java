package com.kropotov.asrd.controllers;

import com.kropotov.asrd.entities.Company;
import com.kropotov.asrd.services.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("")
    public String showCompany(Model model) {
        List<Company> companies = companyService.findAll();
        model.addAttribute("companies", companies);
        return "companies/list-companies";
    }

    @GetMapping("/{id}")
    public String editCompanyPage(Model model, @PathVariable("id") Long id) {
        Company company = companyService.findById(id);
        if (company == null) {
            company = new Company();
        }
        model.addAttribute("company", company);
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
        Company existing = companyService.findByTitle(company.getTitle());
        if (existing != null && !company.getId().equals(existing.getId())) {
            model.addAttribute("company", company);
            model.addAttribute("companyCreationError", "Company title already exists");
            return "companies/edit-company";
        }
        companyService.saveOrUpdate(company);
        return "redirect:/companies";
    }

}
