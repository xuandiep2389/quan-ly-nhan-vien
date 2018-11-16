package will.test.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import will.test.model.Customer;


public interface CustomerService {
    Page<Customer> findAll(Pageable pageable);

    void save(Customer customer);

    Customer findById(int id);

    void remove(int id);

    Page<Customer> findAllByName(String name, Pageable pageable);

}
