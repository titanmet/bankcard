package com.ratnikov.bankcard.controller;


import com.ratnikov.bankcard.dto.CustomerDTO;
import com.ratnikov.bankcard.dto.UserDTO;
import com.ratnikov.bankcard.model.User;
import com.ratnikov.bankcard.properties.ConfigurationProperties;
import com.ratnikov.bankcard.properties.MessageProperties;
import com.ratnikov.bankcard.service.CustomerService;
import com.ratnikov.bankcard.service.EmailServiceImpl;
import com.ratnikov.bankcard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AppController {
    private final UserService userService;
    private final EmailServiceImpl emailService;
    private final CustomerService customerService;
    private final MessageProperties messageProperties;
    private final ConfigurationProperties configurationProperties;

    @RequestMapping(value = {"/", AppUrls.Login.FuLL}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = AppUrls.Registration.FuLL, method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = AppUrls.Registration.FuLL, method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        UserDTO userExists = userService.findUserByUserNameDTO(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            messageProperties.getUserError());
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", messageProperties.getUserSuccess());
            modelAndView.addObject("infologin", messageProperties.getWelcome());
            modelAndView.addObject("user", new User());
            try {
                emailService.send(user.getEmail(), messageProperties.getBanner(), "Ваш логин: " + user.getUserName());
            } catch (Exception e) {
                log.error("Email can't be sent", e);
            }
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value = AppUrls.IndexCards.FuLL, method = RequestMethod.GET)
    public ModelAndView home(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = userService.findUserByUserNameDTO(auth.getName());
        modelAndView.addObject("userName", "Пользователь: " + userDTO.getUserName() + " / " + userDTO.getName() + " " + userDTO.getLastName() + " ( " + userDTO.getEmail() + " )");
        List<CustomerDTO> listCustomer = customerService.findAll();
        modelAndView.addObject("listCustomer", listCustomer);
        model.addAttribute("listCustomer", listCustomer);
        findPaginated(1, "id", "asc", model);
        modelAndView.setViewName("indexcards");
        return modelAndView;
    }

    @RequestMapping(value = AppUrls.Page.PageNo.FULL, method = RequestMethod.GET)
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {

        Page<CustomerDTO> page = customerService.findPaginated(pageNo, configurationProperties.getPageSize(), sortField, sortDir);
        List<CustomerDTO> listCustomers = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCustomers", listCustomers);
        return AppUrls.IndexCards.FuLL;
    }
}
