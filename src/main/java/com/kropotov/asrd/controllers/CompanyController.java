package com.kropotov.asrd.controllers;

import com.kropotov.asrd.entities.company.Address;
import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.CompanyPhone;
import com.kropotov.asrd.entities.company.Employee;
import com.kropotov.asrd.services.springdatajpa.titles.company.AddressService;
import com.kropotov.asrd.services.springdatajpa.titles.company.CompanyPhoneService;
import com.kropotov.asrd.services.springdatajpa.titles.company.CompanyService;
import com.kropotov.asrd.services.springdatajpa.titles.company.EmployeeService;
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
    private final AddressService addressService;
    private final CompanyPhoneService companyPhoneService;
    private final EmployeeService employeeService;

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
    public String editCompany(@Valid @ModelAttribute("company") Company company, BindingResult bindingResult, Model model, @PathVariable("id") Long id) {
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

    @GetMapping("/edit/address/{id}")
    public String editCompanyAddressPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("address", addressService.getById(id).get());
        return "companies/edit-company-address";
    }

    @PostMapping("/edit/address/{id}")
    public String editCompanyAddressPage(@Valid @ModelAttribute("address") Address address, BindingResult bindingResult, Model model,
                                         @PathVariable("id") Long id) {
        addressService.save(address);
        String url = String.valueOf(new StringBuilder("redirect:/companies/info/").append(address.getCompany().getId().toString()));
        return url;
    }

    @GetMapping("/edit/phone/{id}")
    public String editCompanyPhonePage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("phone", companyPhoneService.getById(id).get());
        return "companies/edit-company-phone";
    }

    @PostMapping("/edit/phone/{id}")
    public String editCompanyPhonePage(@Valid @ModelAttribute("phone")CompanyPhone phone, BindingResult bindingResult, Model model,
                                       @PathVariable("id") Long id) {
        companyPhoneService.save(phone);
        String url = String.valueOf(new StringBuilder("redirect:/companies/info/").append(phone.getCompany().getId().toString()));
        return url;
    }

    @GetMapping("/edit/employee/{id}")
    public String editCompanyEmployeePage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("employee", employeeService.getById(id).get());
        return "companies/edit-company-employee";
    }

    @PostMapping("/edit/employee/{id}")
    public String editCompanyEmployeePage(@Valid @ModelAttribute("phone") Employee employee, BindingResult bindingResult, Model model,
                                          @PathVariable("id") Long id) {
        employeeService.save(employee);
        String url = String.valueOf(new StringBuilder("redirect:/companies/info/").append(employee.getCompany().getId().toString()));
        return url;
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
