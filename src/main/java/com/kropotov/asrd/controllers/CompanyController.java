package com.kropotov.asrd.controllers;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.services.springdatajpa.titles.company.CompanyService;
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

    private final CompanyService companyService;

    @GetMapping("")
    public String showCompany(Model model) {
        model.addAttribute("companies", companyService.getAll());
        return "companies/list-companies";
    }

    @GetMapping("/edit/{id}")
    public String editCompanyPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("company", companyService.getById(id).orElse(new Company()));
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
        model.addAttribute("company", companyService.getById(id).get());
        model.addAttribute("employees", companyService.getById(id).get().getEmployee());
        model.addAttribute("phones", companyService.getById(id).get().getCompanyPhones());
        model.addAttribute("addresses", companyService.getById(id).get().getAddress());
        return "companies/info";
    }
    private boolean saveOrEditCompany(@ModelAttribute("company") @Valid Company company, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("companyCreationError", "BindingResult error!");
            return true;
        }
        Company existing = companyService.getOneByTitle(company.getTitle());
        if (existing != null && !existing.getId().equals(company.getId())) {
            model.addAttribute("company", company);
            model.addAttribute("companyCreationError", "Компания с таким названием уже существует!");
            return true;
        }
        companyService.save(company);
        return false;
    }

}
