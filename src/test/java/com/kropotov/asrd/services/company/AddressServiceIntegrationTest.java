package com.kropotov.asrd.services.company;

import com.kropotov.asrd.entities.company.Address;
import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.repositories.company.AddressRepository;
import com.kropotov.asrd.services.springdatajpa.titles.company.AddressService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
@RunWith(MockitoJUnitRunner.class)
public class AddressServiceIntegrationTest {

    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private AddressService addressService;

    List<Address> addresses = new ArrayList<>();

    private Company company = new Company();

    Address address = new Address();

    @Before
    public void setUp() {

        address.setCompany(company);

        addresses.add(address);

        when(addressRepository.findAll()).thenReturn(addresses);
        when(addressRepository.findAllByCompany(address.getCompany())).thenReturn(addresses);
        when(addressRepository.save(address)).thenReturn(address);
    }

    @Test
    public void whenFindAllAddresses_thenReturnAddressesList() {

        List<Address> found = addressService.getAll().get();

        assertThat(found).isEqualTo(addresses);
    }


    @Test
    public void whenFindAllByCompany_thenReturnListAddresses() {

        List<Address> found = addressService.getAllByCompany(company);

        assertThat(found).isEqualTo(addresses);
    }

    @Test
    public void whenSaveAddress_thenAddressShouldBeSave() {

        Address found = addressService.save(address);

        assertThat(found).isEqualTo(address);
    }
}
