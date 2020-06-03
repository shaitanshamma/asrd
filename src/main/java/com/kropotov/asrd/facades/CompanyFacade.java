package com.kropotov.asrd.facades;

import com.kropotov.asrd.controllers.util.PageValues;
import com.kropotov.asrd.controllers.util.PageWrapper;
import com.kropotov.asrd.converters.company.*;
import com.kropotov.asrd.dto.company.AddressDto;
import com.kropotov.asrd.dto.company.CompanyDto;
import com.kropotov.asrd.dto.company.CompanyPhoneDto;
import com.kropotov.asrd.dto.company.EmployeeDto;
import com.kropotov.asrd.entities.company.Address;
import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.CompanyPhone;
import com.kropotov.asrd.entities.company.Employee;
import com.kropotov.asrd.services.springdatajpa.titles.company.AddressService;
import com.kropotov.asrd.services.springdatajpa.titles.company.CompanyPhoneService;
import com.kropotov.asrd.services.springdatajpa.titles.company.CompanyService;
import com.kropotov.asrd.services.springdatajpa.titles.company.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyFacade {

    private final CompanyService companyService;
    private final AddressService addressService;
    private final CompanyPhoneService companyPhoneService;
    private final EmployeeService employeeService;
    private final CompanyToDto companyToDto;
    private final EmployeeToDto employeeToDto;
    private final AddressToDto addressToDto;
    private final CompanyPhoneToDto companyPhoneToDto;
    private final DtoAddressToCompany dtoAddressToCompany;
    private final DtoCompanyPhoneToCompanyPhone dtoCompanyPhoneToCompanyPhone;
    private final DtoEmployeeToCompany dtoEmployeeToCompany;
    private List<CompanyDto> companyDtos;
    private List<EmployeeDto> employeeDtos;
    private List<AddressDto> addressDtos;
    private List<CompanyPhoneDto> companyPhoneDtos;

    // Тут нужно переделать на dto, Я сделал просто чтобы работало
    public List<CompanyDto> fillPage(Model model, Pageable pageable) {
        //companyDtos = companyService.getAll().get().stream().map(company -> companyToDto.convert(company)).collect(Collectors.toList());
        pageable = PageValues.getPageableOrDefault(pageable);
        PageWrapper<Company> page = new PageWrapper<>(companyService.getAll(pageable.previousOrFirst()), "/companies");
        PageValues.addContentToModel(model, page);
        return companyDtos;
    }

    public List<EmployeeDto> showEmployeesByCompanyId(Long id) {

        employeeDtos = companyService.getById(id).get().getEmployee().stream().map(employee -> employeeToDto.convert(employee)).collect(Collectors.toList());
        return employeeDtos;
    }

    public List<AddressDto> showAddressByCompanyId(Long id) {

        addressDtos = companyService.getById(id).get().getAddress().stream().map(address -> addressToDto.convert(address)).collect(Collectors.toList());
        return addressDtos;
    }

    public List<CompanyPhoneDto> showPhoneByCompanyId(Long id) {

        companyPhoneDtos = companyService.getById(id).get().getCompanyPhones().stream().map(phone -> companyPhoneToDto.convert(phone)).collect(Collectors.toList());
        return companyPhoneDtos;
    }

    public Company getCompanyById(Long id) {
        return companyService.getById(id).get();
    }

    public CompanyDto getCompanyDTOById(Long id) {
        return companyToDto.convert(companyService.getById(id).get());
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

    public AddressDto getAddressById(Long id) {
        return addressDtos.stream().filter(addressDto -> addressDto.getId() == id).findFirst().get();
    }

    //
    public String saveAddress(AddressDto addressDto, Long companyId) {
        Address address = dtoAddressToCompany.convert(addressDto);
        addressService.save(address);
        String url = String.format("redirect:/companies/company/%s/show",companyId);
        return url;
    }
//

    public CompanyPhoneDto getPhoneById(Long id) {
        return companyPhoneDtos.stream().filter(phoneDto ->phoneDto.getId() == id).findFirst().get();
    }

    //
    public String savePhone(CompanyPhoneDto phoneDto, Long companyId) {
        CompanyPhone companyPhone = dtoCompanyPhoneToCompanyPhone.convert(phoneDto);
        companyPhoneService.save(companyPhone);
        String url = String.format("redirect:/companies/company/%s/show", companyId);
        return url;
    }

    //
    public Employee getEmployeeById(Long id) {
        return employeeService.getById(id).get();
    }
//

    public String saveEmployee(EmployeeDto employeeDto,Long companyId) {
        Employee employee = dtoEmployeeToCompany.convert(employeeDto);
        employeeService.save(employee);
        String url = String.format("redirect:/companies/company/%s/show", companyId);
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
