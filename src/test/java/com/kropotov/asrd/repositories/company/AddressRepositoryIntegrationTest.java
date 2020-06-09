package com.kropotov.asrd.repositories.company;

import com.kropotov.asrd.entities.company.Address;
import com.kropotov.asrd.entities.company.Company;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class AddressRepositoryIntegrationTest {

    private List<Address> addresses = new ArrayList<>();

    private Company company = new Company();

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressRepository addressRepository;
    @Before
    public void setUp() {

        for (int i = 0; i <5 ; i++) {
            Address address_i = new Address();
            address_i.setCompany(company);
            addresses.add(address_i);
            entityManager.persist(address_i);
            entityManager.persist(company);
        }
        entityManager.flush();
    }
    @Test
    public void whenFindAll_thenReturnListAddresses() {

        List<Address> found = addressRepository.findAll();

        assertThat(found).isEqualTo(addresses);
    }

    @Test
    public void whenFindAllByCompany_thenReturnListAddresses() {

        List<Address> found = addressRepository.findAllByCompany(company);

        assertThat(found).isEqualTo(addresses);
    }
}
