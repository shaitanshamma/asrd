package com.kropotov.asrd.controllers;

import com.kropotov.asrd.entities.company.Address;
import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.CompanyPhone;
import com.kropotov.asrd.entities.company.Employee;
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

    private final CompanyFasade companyFasade;

    @GetMapping("")
    public String showCompany(Model model) {
        model.addAttribute("companies", companyFasade.showCompanies());
        return "companies/list-companies";
    }

    @GetMapping("/company/{id}/update")
    public String editCompanyPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("company", companyFasade.getCompanyDTOById(id));
        return "companies/edit-company";
    }

    // @Valid проверяет в соответствии с аннотациями сущности
    // результаты проверки приходят в BindingResult
    @PostMapping("/company/{id}/update")
    public String editCompany(@Valid @ModelAttribute("company") Company company, BindingResult bindingResult, Model model, @PathVariable("id") Long id) {
        if (companyFasade.saveOrEditCompany(company, bindingResult, model)) return "companies/edit-company";
        return "redirect:/companies/company/{id}/show";
    }

    @GetMapping("/company/add/")
    public String addCompanyPage(Model model) {
        model.addAttribute("company", companyFasade.addCompany());
        return "companies/add-company";
    }

    @PostMapping("/company/add/")
    public String addCompany(@Valid @ModelAttribute("company") Company company, BindingResult bindingResult, Model model) {
        if (companyFasade.saveOrEditCompany(company, bindingResult, model)) return "companies/edit-company";
        return "redirect:/companies/";
    }

    @GetMapping("/company/{id}/show")
    public String companyInfoPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("company", companyFasade.getCompanyDTOById(id));
        model.addAttribute("employees", companyFasade.getCompanyById(id).getEmployee());
        model.addAttribute("phones", companyFasade.getCompanyById(id).getCompanyPhones());
        model.addAttribute("addresses", companyFasade.getCompanyById(id).getAddress());
        return "companies/info";
    }

    @GetMapping("/address/{id}/update")
    public String editCompanyAddressPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("address", companyFasade.getAddressById(id));
        return "companies/edit-company-address";
    }

    @PostMapping("/address/{id}/update")
    public String editCompanyAddressPage(@Valid @ModelAttribute("address") Address address, BindingResult bindingResult, Model model,
                                         @PathVariable("id") Long id) {
        return companyFasade.saveAddress(address);
    }

    @GetMapping("/phone/{id}/update")
    public String editCompanyPhonePage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("phone", companyFasade.getPhoneById(id));
        return "companies/edit-company-phone";
    }

    @PostMapping("/phone/{id}/update")
    public String editCompanyPhonePage(@Valid @ModelAttribute("phone") CompanyPhone phone, BindingResult bindingResult, Model model,
                                       @PathVariable("id") Long id) {
        return companyFasade.savePhone(phone);
    }

    @GetMapping("/employee/{id}/update")
    public String editCompanyEmployeePage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("employee", companyFasade.getEmployeeById(id));
        return "companies/edit-company-employee";
    }

    @PostMapping("/employee/{id}/update")
    public String editCompanyEmployeePage(@Valid @ModelAttribute("employee") Employee employee,
                                          @PathVariable("id") Long id) {
        return companyFasade.saveEmployee(employee);
    }

}
