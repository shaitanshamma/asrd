package com.kropotov.asrd.controllers;

import com.kropotov.asrd.dto.company.AddressDto;
import com.kropotov.asrd.dto.company.CompanyPhoneDto;
import com.kropotov.asrd.dto.company.EmployeeDto;
import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.facades.CompanyFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyFacade companyFacade;

    @GetMapping("")
    public String showCompanies(Model model, Pageable pageable) {
        companyFacade.fillPage(model, pageable);
        return "companies/list-companies";
    }

    @GetMapping("/{id}/update")
    public String editCompanyPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("company", companyFacade.getCompanyDTOById(id));
        return "companies/edit-company";
    }

    // @Valid проверяет в соответствии с аннотациями сущности
    // результаты проверки приходят в BindingResult
    @PostMapping("/{id}/update")
    public String editCompany(@Valid @ModelAttribute("company") Company company, BindingResult bindingResult, Model model, @PathVariable("id") Long id) {
        if (companyFacade.saveOrEditCompany(company, bindingResult, model)) return "companies/edit-company";
        return String.format("redirect:/companies/%s/show", id);
    }

    @GetMapping("/create")
    public String addCompanyPage(Model model) {
        model.addAttribute("company", companyFacade.addCompany());
        return "companies/add-company";
    }

    @PostMapping("/create")
    public String addCompany(@Valid @ModelAttribute("company") Company company, BindingResult bindingResult, Model model) {
        if (companyFacade.saveOrEditCompany(company, bindingResult, model)) return "companies/edit-company";
        return "redirect:/companies/";
    }

    @GetMapping("/{id}/show")
    public String companyInfoPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("company", companyFacade.getCompanyDTOById(id));
        model.addAttribute("employees", companyFacade.showEmployeesByCompanyId(id));
        model.addAttribute("phones", companyFacade.showPhoneByCompanyId(id));
        model.addAttribute("addresses", companyFacade.showAddressByCompanyId(id));
        return "companies/info";
    }

    @GetMapping("/{companyId}/address/{id}/update")
    public String editCompanyAddressPage(Model model, @PathVariable("id") Long id,@PathVariable("companyId") Long companyId) {
        model.addAttribute("address", companyFacade.getAddressById(id));
        model.addAttribute("company", companyFacade.getCompanyDTOById(companyId));
        return "companies/edit-company-address";
    }

    @PostMapping("/{companyId}/address/{id}/update")
    public String editCompanyAddressPage(@Valid @ModelAttribute("address") AddressDto addressDto, BindingResult bindingResult, Model model,
                                         @PathVariable("id") Long id, @PathVariable("companyId") Long companyId) {
        return companyFacade.saveAddress(addressDto, companyId);
    }

    @GetMapping("/{companyId}/phone/{id}/update")
    public String editCompanyPhonePage(Model model, @PathVariable("id") Long id,@PathVariable("companyId") Long companyId) {
        model.addAttribute("phone", companyFacade.getPhoneById(id));
        model.addAttribute("company", companyFacade.getCompanyDTOById(companyId));
        return "companies/edit-company-phone";
    }

    @PostMapping("/{companyId}/phone/{id}/update")
    public String editCompanyPhonePage(@Valid @ModelAttribute("phone") CompanyPhoneDto phoneDto, BindingResult bindingResult, Model model,
                                       @PathVariable("id") Long id, @PathVariable("companyId") Long companyId) {
        return companyFacade.savePhone(phoneDto,companyId);
    }

    @GetMapping("/{companyId}/employee/{id}/update")
    public String editCompanyEmployeePage(Model model, @PathVariable("id") Long id, @PathVariable("companyId") Long companyId) {
        model.addAttribute("employee", companyFacade.getEmployeeById(id));
        model.addAttribute("company", companyFacade.getCompanyDTOById(companyId));
        return "companies/edit-company-employee";
    }

    @PostMapping("/{companyId}/employee/{id}/update")
    public String editCompanyEmployeePage(@Valid @ModelAttribute("employee") EmployeeDto employeeDto,
                                          @PathVariable("id") Long id, @PathVariable("companyId") Long companyId) {
        return companyFacade.saveEmployee(employeeDto, companyId);
    }

}
