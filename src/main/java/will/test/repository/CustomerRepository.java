package will.test.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import will.test.model.Customer;

public interface CustomerRepository  extends PagingAndSortingRepository<Customer,Integer> {
    Page<Customer> findAllByName(String name, Pageable pageable);

}
