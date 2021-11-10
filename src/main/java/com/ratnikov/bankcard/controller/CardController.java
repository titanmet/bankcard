package com.ratnikov.bankcard.controller;

import com.ratnikov.bankcard.model.Card;
import com.ratnikov.bankcard.model.Customer;
import com.ratnikov.bankcard.repository.CardRepository;
import com.ratnikov.bankcard.repository.CustomerRepository;
import com.ratnikov.bankcard.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CardController {

    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;
    private final CardService cardService;

    @GetMapping("/card")
    public String listCustomer(Model model) {
        List<Card> listCards = cardRepository.findAll();
        model.addAttribute("listCards", listCards);
        findPaginatedCard(1, "id", "asc", model);
        return "card";
    }

    @GetMapping("/searchcards")
    public String listCardSearch(Card card, Model model, String keyword) {
        if (keyword != null) {
            List<Card> listCards = cardService.getByKeyword(keyword);
            model.addAttribute("listCards", listCards);
        } else {
            List<Card> listCards = cardRepository.findAll();
            model.addAttribute("listCards", listCards);
        }
        return "card";
    }

    @RequestMapping(value = "/card/new", method = RequestMethod.GET)
    public ModelAndView cardNew() {
        ModelAndView modelAndView = new ModelAndView();
        List<Customer> listCustomers = customerRepository.findAll();
        modelAndView.addObject("card", new Card());
        modelAndView.addObject("listCustomers", listCustomers);
        modelAndView.setViewName("card_form");
        return modelAndView;
    }

    @RequestMapping(value = "/card/save", method = RequestMethod.POST)
    public ModelAndView showCardNewForm(@Valid Card card, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Card cardExists = cardService.findCardByCardNumber(card.getCardNumber());
        if (cardExists != null) {
            bindingResult
                    .rejectValue("cardNumber", "error.card",
                            "Банковская карта с таким номером уже есть.");
            List<Customer> listCustomers = customerRepository.findAll();
            modelAndView.addObject("listCustomers", listCustomers);
            modelAndView.setViewName("card_form");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("card_form");
        } else {
            cardRepository.save(card);
            modelAndView.setViewName("redirect:/card");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/card/editsave", method = RequestMethod.POST)
    public ModelAndView showCardEditNewForm(@Valid Card card, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("card_form");
        } else {
            cardRepository.save(card);
            modelAndView.setViewName("redirect:/card");
        }
        return modelAndView;
    }

    @GetMapping("/card/edit/{id}")
    public String showEditCardForm(@PathVariable("id") Integer id, Model model) {
        Card card = cardRepository.findById(id).get();
        model.addAttribute("card", card);
        List<Customer> listCustomers = customerRepository.findAll();
        model.addAttribute("listCustomers", listCustomers);

        return "card_form";
    }


    @GetMapping("/card/delete/{id}")
    public String deleteCard(@PathVariable("id") Integer id, Model model) {
        cardRepository.deleteById(id);

        return "redirect:/card";
    }

    @GetMapping("/card/page/{pageNo}")
    public String findPaginatedCard(@PathVariable(value = "pageNo") int pageNo,
                                    @RequestParam("sortField") String sortField,
                                    @RequestParam("sortDir") String sortDir,
                                    Model model) {
        int pageSize = 5;

        Page<Card> page = cardService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Card> listCards = page.getContent();

        model.addAttribute("currentPageCard", pageNo);
        model.addAttribute("totalPagesCard", page.getTotalPages());
        model.addAttribute("totalItemsCard", page.getTotalElements());

        model.addAttribute("sortFieldCard", sortField);
        model.addAttribute("sortDirCard", sortDir);
        model.addAttribute("reverseSortDirCard", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCards", listCards);
        return "card";
    }
}
