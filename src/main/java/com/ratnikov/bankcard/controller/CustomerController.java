package com.ratnikov.bankcard.controller;

import com.ratnikov.bankcard.model.Category;
import com.ratnikov.bankcard.model.Customer;
import com.ratnikov.bankcard.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import com.ratnikov.bankcard.repository.CustomerRepository;
import com.ratnikov.bankcard.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final CustomerService customerService;


    @GetMapping("/searchcustomers")
    public String listCustomerSearch(Customer customer, Model model, String keyword) {
        if(keyword != null) {
            List<Customer> listCustomers = customerService.getByKeyword(keyword);
            model.addAttribute("listCustomers", listCustomers);
        } else {
            List<Customer> listCustomers = customerRepository.findAll();
            model.addAttribute("listCustomers", listCustomers);
        }
        return "customer";
    }

    @RequestMapping(value="/customer/new", method = RequestMethod.GET)
    public ModelAndView customerNew(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("customer", new Customer());
        List<Category> listCategories = categoryRepository.findAll();
        modelAndView.addObject("listCategories", listCategories);
        modelAndView.setViewName("customer_form");
        return modelAndView;
    }

    @RequestMapping(value = "/customer/save", method = RequestMethod.POST)
    public ModelAndView showCustomerNewForm(@Valid Customer customer, HttpServletRequest request, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Customer customerNameExists = customerService.findCustomerByCustomerName(customer.getCustomerName());
        if (customerNameExists != null) {
            bindingResult
                    .rejectValue("customerName", "error.customerName",
                            "Клиент с таким Ф.И.О уже есть.");
            List<Category> listCategories = categoryRepository.findAll();
            modelAndView.addObject("listCategories", listCategories);
            modelAndView.setViewName("customer_form");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("customer_form");
        } else {
            customerRepository.save(customer);
            modelAndView.setViewName("redirect:/customer");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/customer/editsave", method = RequestMethod.POST)
    public ModelAndView showCustomerEditNewForm(@Valid Customer customer, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("customer_form");
        } else {
            customerRepository.save(customer);
            modelAndView.setViewName("redirect:/customer");
        }
        return modelAndView;
    }


    @GetMapping("/customer")
    public String listCustomer(Model model) {
        List<Customer> listCustomers = customerRepository.findAll();
        model.addAttribute("listCustomers", listCustomers);
        findPaginatedCustomer(1, "id", "asc", model);
        return "customer";
    }

    @GetMapping("/customer/edit/{id}")
    public String showEditCustomerForm(@PathVariable("id") Integer id, Model model) {
        Customer customer = customerRepository.findById(id).get();
        model.addAttribute("customer", customer);
        List<Category> listCategories = categoryRepository.findAll();
        model.addAttribute("listCategories", listCategories);

        return "customer_form";
    }

    @GetMapping("/customer/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Integer id, Model model) {
        customerRepository.deleteById(id);

        return "redirect:/customer";
    }

    @GetMapping("/customer/page/{pageNo}")
    public String findPaginatedCustomer(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<Customer> page = customerService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Customer> listCustomers = page.getContent();

        model.addAttribute("currentPageCust", pageNo);
        model.addAttribute("totalPagesCust", page.getTotalPages());
        model.addAttribute("totalItemsCust", page.getTotalElements());

        model.addAttribute("sortFieldCust", sortField);
        model.addAttribute("sortDirCust", sortDir);
        model.addAttribute("reverseSortDirCust", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCustomers", listCustomers);
        return "customer";
    }
}
