package com.ratnikov.bankcard.controller;

import com.ratnikov.bankcard.dto.CategoryDTO;
import com.ratnikov.bankcard.dto.CustomerDTO;
import com.ratnikov.bankcard.model.Category;
import com.ratnikov.bankcard.model.Customer;
import com.ratnikov.bankcard.properties.ConfigurationProperties;
import com.ratnikov.bankcard.properties.MessageProperties;
import com.ratnikov.bankcard.repository.CategoryRepository;
import com.ratnikov.bankcard.service.CategoryService;
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
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CategoryService categoryService;
    private final MessageProperties messageProperties;
    private final ConfigurationProperties configurationProperties;

    @RequestMapping(value = CustomerUrls.SearchCustomers.FuLL, method = RequestMethod.GET)
    public String listCustomerSearch(Model model, String keyword) {
        List<CustomerDTO> listCustomers;
        if (keyword != null) {
            listCustomers = customerService.getByKeyword(keyword);
        } else {
            listCustomers = customerService.findAll();
        }
        model.addAttribute("listCustomers", listCustomers);
        return CustomerUrls.Customer.FuLL;
    }

    @RequestMapping(value = CustomerUrls.Customer.New.FULL, method = RequestMethod.GET)
    public ModelAndView customerNew() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("customer", new Customer());
        List<CategoryDTO> listCategories = categoryService.findAll();
        modelAndView.addObject("listCategories", listCategories);
        modelAndView.setViewName("customer_form");
        return modelAndView;
    }

    @RequestMapping(value = CustomerUrls.Customer.Save.FULL, method = RequestMethod.POST)
    public ModelAndView showCustomerNewForm(@Valid Customer customer, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        CustomerDTO customerNameExists = customerService.findCustomerByName(customer.getName());
        if (customerNameExists != null) {
            bindingResult
                    .rejectValue("name", "error.name",
                            messageProperties.getCustomerError());
            List<CategoryDTO> listCategories = categoryService.findAll();
            modelAndView.addObject("listCategories", listCategories);
            modelAndView.setViewName("customer_form");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("customer_form");
        } else {
            customerService.save(customer);
            modelAndView.setViewName("redirect:/customer");
        }
        return modelAndView;
    }

    @RequestMapping(value = CustomerUrls.Customer.EditSave.FULL, method = RequestMethod.POST)
    public ModelAndView showCustomerEditNewForm(@Valid Customer customer, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("customer_form");
        } else {
            customerService.save(customer);
            modelAndView.setViewName("redirect:/customer");
        }
        return modelAndView;
    }

    @RequestMapping(value = CustomerUrls.Customer.FuLL, method = RequestMethod.GET)
    public String listCustomer(Model model) {
        List<CustomerDTO> listCustomers = customerService.findAll();
        model.addAttribute("listCustomers", listCustomers);
        findPaginatedCustomer(1, "id", "asc", model);
        return CustomerUrls.Customer.FuLL;
    }

    @RequestMapping(value = CustomerUrls.Customer.Edit.EditId.FULL, method = RequestMethod.GET)
    public String showEditCustomerForm(@PathVariable("id") Long id, Model model) {
        CustomerDTO customerDTO = customerService.findCustomerById(id);
        model.addAttribute("customer", customerDTO);
        List<CategoryDTO> listCategories = categoryService.findAll();
        model.addAttribute("listCategories", listCategories);

        return "customer_form";
    }

    @RequestMapping(value = CustomerUrls.Customer.Delete.DeleteId.FULL, method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteById(id);

        return "redirect:/customer";
    }

    @RequestMapping(value = CustomerUrls.Customer.Page.PageNo.FULL, method = RequestMethod.GET)
    public String findPaginatedCustomer(@PathVariable(value = "pageNo") int pageNo,
                                        @RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir,
                                        Model model) {
        Page<CustomerDTO> page = customerService.findPaginated(pageNo, configurationProperties.getPageSize(), sortField, sortDir);
        List<CustomerDTO> listCustomers = page.getContent();

        model.addAttribute("currentPageCust", pageNo);
        model.addAttribute("totalPagesCust", page.getTotalPages());
        model.addAttribute("totalItemsCust", page.getTotalElements());

        model.addAttribute("sortFieldCust", sortField);
        model.addAttribute("sortDirCust", sortDir);
        model.addAttribute("reverseSortDirCust", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCustomers", listCustomers);
        return CustomerUrls.Customer.FuLL;
    }
}