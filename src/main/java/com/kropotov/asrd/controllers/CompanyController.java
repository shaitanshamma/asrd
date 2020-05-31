package com.kropotov.asrd.controllers;

import com.kropotov.asrd.dto.company.AddressDto;
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
        model.addAttribute("employees", companyFasade.showEmployeesByCompanyId(id));
        model.addAttribute("phones", companyFasade.showPhoneByCompanyId(id));
        model.addAttribute("addresses", companyFasade.showAddressByCompanyId(id));
        return "companies/info";
    }

    @GetMapping("company/{companyId}/address/{id}/update")
    public String editCompanyAddressPage(Model model, @PathVariable("id") Long id,@PathVariable("companyId") Long companyId) {
        model.addAttribute("address", companyFasade.getAddressById(id));
        model.addAttribute("company", companyFasade.getCompanyDTOById(companyId));
        return "companies/edit-company-address";
    }

    @PostMapping("company/{companyId}/address/{id}/update")
    public String editCompanyAddressPage(@Valid @ModelAttribute("address") AddressDto addressDto, BindingResult bindingResult, Model model,
                                         @PathVariable("id") Long id, @PathVariable("companyId") Long companyId) {
        return companyFasade.saveAddress(addressDto, companyId);
    }

    @GetMapping("company/{companyId}/phone/{id}/update")
    public String editCompanyPhonePage(Model model, @PathVariable("id") Long id,@PathVariable("companyId") Long companyId) {
        model.addAttribute("phone", companyFasade.getPhoneById(id));
        return "companies/edit-company-phone";
    }

    @PostMapping("company/{companyId}/phone/{id}/update")
    public String editCompanyPhonePage(@Valid @ModelAttribute("phone") CompanyPhone phone, BindingResult bindingResult, Model model,
                                       @PathVariable("id") Long id, @PathVariable("companyId") Long companyId) {
        return companyFasade.savePhone(phone);
    }

    @GetMapping("company/{companyId}/employee/{id}/update")
    public String editCompanyEmployeePage(Model model, @PathVariable("id") Long id, @PathVariable("companyId") Long companyId) {
        model.addAttribute("employee", companyFasade.getEmployeeById(id));
        return "companies/edit-company-employee";
    }

    @PostMapping("company/{companyId}/employee/{id}/update")
    public String editCompanyEmployeePage(@Valid @ModelAttribute("employee") Employee employee,
                                          @PathVariable("id") Long id, @PathVariable("companyId") Long companyId) {
        return companyFasade.saveEmployee(employee);
    }

}
