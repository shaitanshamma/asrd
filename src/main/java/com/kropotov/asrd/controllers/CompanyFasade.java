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
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
//@Controller
//@RequestMapping("/companies")
public class CompanyFasade {

    private final CompanyService companyService;
    private final AddressService addressService;
    private final CompanyPhoneService companyPhoneService;
    private final EmployeeService employeeService;


    public List<Company> showCompanies() {
         return companyService.getAll();
    }

    public Company getCompanyById(Long id) {
        return companyService.getById(id).orElse(new Company());
    }

    public Company addCompany() {
        return new Company();
    }
//
//    @PostMapping("/add/")
//    public String addCompany(@Valid @ModelAttribute("company") Company company, BindingResult bindingResult, Model model) {
//        if (saveOrEditCompany(company, bindingResult, model)) return "companies/edit-company";
//        return "redirect:/companies/";
//    }
//
//    // @Valid проверяет в соответствии с аннотациями сущности
//    // результаты проверки приходят в BindingResult
//    @PostMapping("/edit/{id}")
//    public String editCompany(@Valid @ModelAttribute("company") Company company, BindingResult bindingResult, Model model, @PathVariable("id") Long id) {
//        if (saveOrEditCompany(company, bindingResult, model)) return "companies/edit-company";
//        return "redirect:/companies/info/{id}";
//    }
//
//
//

    public Address getAddressById(Long id) {
        return addressService.getById(id).get();
    }
//
    public String saveAddress(Address address) {
        addressService.save(address);
        String url = String.valueOf(new StringBuilder("redirect:/companies/info/").append(address.getCompany().getId().toString()));
        return url;
    }
//

    public CompanyPhone getPhoneById(Long id) {
        return companyPhoneService.getById(id).get();
    }
//
    public String savePhone(CompanyPhone phone) {
        companyPhoneService.save(phone);
        String url = String.valueOf(new StringBuilder("redirect:/companies/info/").append(phone.getCompany().getId().toString()));
        return url;
    }
//
    public Employee getEmployeeById(Long id) {
        return employeeService.getById(id).get();
    }
//

    public String saveEmployee(Employee employee) {
        employeeService.save(employee);
        String url = String.valueOf(new StringBuilder("redirect:/companies/info/").append(employee.getCompany().getId().toString()));
        return url;
    }
//
    public boolean saveOrEditCompany(Company company, BindingResult bindingResult, Model model) {
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
