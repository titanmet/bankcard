package com.ratnikov.bankcard.controller;


import com.ratnikov.bankcard.model.Customer;
import com.ratnikov.bankcard.model.User;
import com.ratnikov.bankcard.service.CustomerService;
import com.ratnikov.bankcard.service.EmailServiceImpl;
import com.ratnikov.bankcard.service.UserService;
import lombok.RequiredArgsConstructor;
import com.ratnikov.bankcard.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppController {
    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final EmailServiceImpl emailService;
    private final CustomerService customerService;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "Пользователь с указанным именем уже зарегистрирован.");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "Пользователь успешно зарегистрирован");
            modelAndView.addObject("infologin", "Войдите в систему");
            modelAndView.addObject("user", new User());
            try {
                emailService.send(user.getEmail(), "Вы зарегистрировались на портале, Банковские карты", "Ваш логин: " + user.getUserName());
            } catch (Exception e){
                log.error("Email can't be sent", e);
            }
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value="/indexcards", method = RequestMethod.GET)
    public ModelAndView home(Model model){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("userName", "Пользователь: " + user.getUserName() + " / " + user.getName() + " " + user.getLastName() + " ( " + user.getEmail() + " )");
        List<Customer> listCustomer = customerRepository.findAll();
        modelAndView.addObject("listCustomer", listCustomer);
        model.addAttribute("listCustomer", listCustomer);
        findPaginated(1, "id", "asc", model);
        modelAndView.setViewName("indexcards");
        return modelAndView;
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<Customer> page = customerService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Customer> listCustomers = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCustomers", listCustomers);
        return "indexcards";
    }
}
