package com.kropotov.asrd.controllers;

import com.kropotov.asrd.entities.Company;
import com.kropotov.asrd.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {
    private CompanyService companyService;

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("")
    public String showCompany(Model model) {
        List<Company> companies = companyService.findAll();
        model.addAttribute("companies", companies);
        return "company-page";
    }

    @GetMapping("/edit/{id}")
    public String editCompanyPage(Model model, @PathVariable("id") Long id) {
        Company company = companyService.findById(id);
        if (company == null) {
            company = new Company();
        }
        model.addAttribute("company", company);
        return "edit-company";
    }

    // @Valid проверяет в соответствии с аннотациями сущности
    // результаты проверки приходят в BindingResult
    @PostMapping("/edit")
    public String editCompany(@Valid @ModelAttribute("company") Company company, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("companyCreationError", "BindingResult error!");
            return "edit-company";
        }
        Company existing = companyService.findByTitle(company.getTitle());
        if (existing != null && !company.getId().equals(existing.getId())) {
            model.addAttribute("company", company);
            model.addAttribute("companyCreationError", "Company title already exists");
            return "edit-company";
        }
        companyService.saveOrUpdate(company);
        return "redirect:/company/";
    }

}
