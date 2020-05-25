package com.kropotov.asrd.controllers;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.services.springdatajpa.titles.company.AddressService;
import com.kropotov.asrd.services.springdatajpa.titles.company.CompaniesService;
import com.kropotov.asrd.services.springdatajpa.titles.company.CompanyPhoneService;
import com.kropotov.asrd.services.springdatajpa.titles.company.EmployeesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompaniesService companiesService;
    private final EmployeesService employeesService;
    private final AddressService addressService;
    private final CompanyPhoneService companyPhoneService;


    @GetMapping("")
    public String showCompany(Model model) {
        List<Company> companies = companiesService.findAll();
        model.addAttribute("companies", companies);
        return "companies/list-companies";
    }

    @GetMapping("/{id}")
    public String editCompanyPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("company", companiesService.findById(id).orElse(new Company()));
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
        Company existing = companiesService.findOneByTitle(company.getTitle());
        if (existing != null && !existing.getId().equals(company.getId())) {
            model.addAttribute("company", company);
            model.addAttribute("companyCreationError", "CompanyOld title already exists");
            return "companies/edit-company";
        }
        companiesService.save(company);
        return "redirect:/companies";
    }

    @GetMapping("/info/{id}")
    public String companyInfoPage(Model model, @PathVariable("id") Long id) {
        Optional<Company> company = companiesService.findById(id);
        model.addAttribute("company", company.get());
        model.addAttribute("employees", employeesService.findAllByCompany(company.get()));
        model.addAttribute("phones", companyPhoneService.findAllByCompany(company.get()));
        model.addAttribute("addresses", addressService.findAllByCompany(company.get()));
        return "companies/info";
    }

}
