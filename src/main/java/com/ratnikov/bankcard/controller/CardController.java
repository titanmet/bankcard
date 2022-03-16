package com.ratnikov.bankcard.controller;

import com.ratnikov.bankcard.dto.CardDTO;
import com.ratnikov.bankcard.dto.CustomerDTO;
import com.ratnikov.bankcard.model.Card;
import com.ratnikov.bankcard.model.Customer;
import com.ratnikov.bankcard.properties.ConfigurationProperties;
import com.ratnikov.bankcard.properties.MessageProperties;
import com.ratnikov.bankcard.repository.CardRepository;
import com.ratnikov.bankcard.repository.CustomerRepository;
import com.ratnikov.bankcard.service.CardService;
import com.ratnikov.bankcard.service.CustomerService;
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
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private final CustomerService customerService;
    private final MessageProperties messageProperties;
    private final ConfigurationProperties configurationProperties;

    @RequestMapping(value = CardUrls.Card.FuLL, method = RequestMethod.GET)
    public String listCustomer(Model model) {
        List<CardDTO> listCardsDTO = cardService.findAll();
        model.addAttribute("listCards", listCardsDTO);
        findPaginatedCard(1, "id", "asc", model);
        return CardUrls.Card.FuLL;
    }

    @RequestMapping(value = CardUrls.SearchCards.FuLL, method = RequestMethod.GET)
    public String listCardSearch(Model model, String keyword) {
        if (keyword != null) {
            List<CardDTO> listCards = cardService.getByKeyword(keyword);
            model.addAttribute("listCards", listCards);
        } else {
            List<CardDTO> listCards = cardService.findAll();
            model.addAttribute("listCards", listCards);
        }
        return CardUrls.Card.FuLL;
    }

    @RequestMapping(value = CardUrls.Card.New.FULL, method = RequestMethod.GET)
    public ModelAndView cardNew() {
        ModelAndView modelAndView = new ModelAndView();
        List<CustomerDTO> listCustomers = customerService.findAll();
        modelAndView.addObject("card", new Card());
        modelAndView.addObject("listCustomers", listCustomers);
        modelAndView.setViewName("card_form");
        return modelAndView;
    }

    @RequestMapping(value = CardUrls.Card.Save.FULL, method = RequestMethod.POST)
    public ModelAndView showCardNewForm(@Valid Card card, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        CardDTO cardExists = cardService.findByCardNumber(card.getNumber());
        if (cardExists != null) {
            bindingResult
                    .rejectValue("number", "error.card",
                            messageProperties.getCardError());
            List<CustomerDTO> listCustomers = customerService.findAll();
            modelAndView.addObject("listCustomers", listCustomers);
            modelAndView.setViewName("card_form");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("card_form");
        } else {
            cardService.save(card);
            modelAndView.setViewName("redirect:/card");
        }
        return modelAndView;
    }

    @RequestMapping(value = CardUrls.Card.EditSave.FULL, method = RequestMethod.POST)
    public ModelAndView showCardEditNewForm(@Valid Card card, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("card_form");
        } else {
            cardService.save(card);
            modelAndView.setViewName("redirect:/card");
        }
        return modelAndView;
    }

    @RequestMapping(value = CardUrls.Card.Edit.EditId.FULL, method = RequestMethod.GET)
    public String showEditCardForm(@PathVariable("id") Long id, Model model) {
        CardDTO cardDTO = cardService.findByCardId(id);
        model.addAttribute("card", cardDTO);
        List<CustomerDTO> listCustomersDTO = customerService.findAll();
        model.addAttribute("listCustomers", listCustomersDTO);

        return "card_form";
    }

    @RequestMapping(value = CardUrls.Card.Delete.DeleteId.FULL, method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteCard(@PathVariable("id") Long id) {
        cardService.deleteById(id);

        return "redirect:/card";
    }

    @RequestMapping(value = CardUrls.Card.Page.PageNo.FULL, method = RequestMethod.GET)
    public String findPaginatedCard(@PathVariable(value = "pageNo") int pageNo,
                                    @RequestParam("sortField") String sortField,
                                    @RequestParam("sortDir") String sortDir,
                                    Model model) {
        Page<CardDTO> page = cardService.findPaginated(pageNo, configurationProperties.getPageSize(), sortField, sortDir);
        List<CardDTO> listCards = page.getContent();

        model.addAttribute("currentPageCard", pageNo);
        model.addAttribute("totalPagesCard", page.getTotalPages());
        model.addAttribute("totalItemsCard", page.getTotalElements());

        model.addAttribute("sortFieldCard", sortField);
        model.addAttribute("sortDirCard", sortDir);
        model.addAttribute("reverseSortDirCard", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCards", listCards);
        return CardUrls.Card.FuLL;
    }
}
