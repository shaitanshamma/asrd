package com.kropotov.asrd.controllers;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.services.springdatajpa.titles.company.CompaniesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompaniesService companiesService;

    @GetMapping("")
    public String showCompany(Model model) {
        model.addAttribute("companies", companiesService.getAll());
        return "companies/list-companies";
    }

    @GetMapping("/edit/{id}")
    public String editCompanyPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("company", companiesService.getById(id).orElse(new Company()));
        return "companies/edit-company";
    }
    @GetMapping("/add/")
    public String addCompanyPage(Model model) {
        model.addAttribute("company", new Company());
        return "companies/add-company";
    }

    @PostMapping("/add/")
    public String addCompany(@Valid @ModelAttribute("company") Company company, BindingResult bindingResult, Model model) {
        if (saveOrEditCompany(company, bindingResult, model)) return "companies/edit-company";
        return "redirect:/companies/";
    }
    // @Valid проверяет в соответствии с аннотациями сущности
    // результаты проверки приходят в BindingResult
    @PostMapping("/edit/{id}")
    public String editCompany(@Valid @ModelAttribute("company") Company company, BindingResult bindingResult, Model model,@PathVariable("id") Long id) {
        if (saveOrEditCompany(company, bindingResult, model)) return "companies/edit-company";
        return "redirect:/companies/info/{id}";
    }

    @GetMapping("/info/{id}")
    public String companyInfoPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("company", companiesService.getById(id).get());
        model.addAttribute("employees", companiesService.getById(id).get().getEmployee());
        model.addAttribute("phones", companiesService.getById(id).get().getCompanyPhones());
        model.addAttribute("addresses", companiesService.getById(id).get().getAddress());
        return "companies/info";
    }
    private boolean saveOrEditCompany(@ModelAttribute("company") @Valid Company company, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("companyCreationError", "BindingResult error!");
            return true;
        }
        Company existing = companiesService.getOneByTitle(company.getTitle());
        if (existing != null && !existing.getId().equals(company.getId())) {
            model.addAttribute("company", company);
            model.addAttribute("companyCreationError", "CompanyOld title already exists");
            return true;
        }
        companiesService.save(company);
        return false;
    }

}
