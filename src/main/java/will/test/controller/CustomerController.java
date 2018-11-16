package will.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import will.test.model.Customer;
import will.test.service.CustomerService;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public ModelAndView listCustomer(@RequestParam("search") Optional<String> search, @PageableDefault(size = 10) Pageable pageable) {
        Page<Customer> customers;
        if (search.isPresent()) {
            customers = customerService.findAllByName(search.get(), pageable);
        } else {
            customers = customerService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }


    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }


    @PostMapping("/create")
    public ModelAndView createCustomer(@Validated @ModelAttribute("customer") Customer customer,BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()){
            ModelAndView modelAndView =new ModelAndView("create");
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("create");
            customerService.save(customer);
            modelAndView.addObject("message", "New customer created succesfully");
            modelAndView.addObject("customer", new Customer());
            return modelAndView;
        }

    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("customer", customerService.findById(id));
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView editCustomer(Customer customer) {
        ModelAndView modelAndView = new ModelAndView("edit");
        customerService.save(customer);
        modelAndView.addObject("message", "Customer was update successfully");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView viewDeleteForm(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("delete");
        modelAndView.addObject("customer", customerService.findById(id));
        return modelAndView;
    }

    @PostMapping("/delete")
    public ModelAndView deleteCustomer(@RequestParam("id") int id, Customer customer) {
        customer = customerService.findById(id);
        if (customer != null) {
            customerService.remove(id);
            ModelAndView modelAndView = new ModelAndView("delete");
            modelAndView.addObject("message", "Delete Success");
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("delete");
            modelAndView.addObject("message", "No customer to delete");
            return modelAndView;
        }
    }

    @GetMapping("/view/{id}")
    public ModelAndView showViewForm(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("customer", customerService.findById(id));
        return modelAndView;
    }

}
