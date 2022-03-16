package com.ratnikov.bankcard.controller;

import com.ratnikov.bankcard.dto.CategoryDTO;
import com.ratnikov.bankcard.model.Category;
import com.ratnikov.bankcard.properties.ConfigurationProperties;
import com.ratnikov.bankcard.properties.MessageProperties;
import com.ratnikov.bankcard.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import com.ratnikov.bankcard.service.CategoryService;
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
public class CategoryController {
    private final CategoryService categoryService;
    private final MessageProperties messageProperties;
    private final ConfigurationProperties configurationProperties;

    @RequestMapping(value = CategoryUrls.Categories.FuLL, method = RequestMethod.GET)
    public String listCategories(Model model) {
        List<CategoryDTO> listCategories = categoryService.findAll();
        model.addAttribute("listCategories", listCategories);
        findPaginatedCategory(1, "id", "asc", model);
        return CategoryUrls.Categories.FuLL;
    }

    @RequestMapping(value = CategoryUrls.SearchCategories.FuLL, method = RequestMethod.GET)
    public String listCategorySearch(Model model, String keyword) {
        List<CategoryDTO> listCategories;
        if (keyword != null) {
            listCategories = categoryService.getByKeyword(keyword);
        } else {
            listCategories = categoryService.findAll();
        }
        model.addAttribute("listCategories", listCategories);
        return CategoryUrls.Categories.FuLL;
    }

    @RequestMapping(value = CategoryUrls.Categories.New.FULL, method = RequestMethod.GET)
    public ModelAndView categoriesNew() {
        ModelAndView modelAndView = new ModelAndView();
        CategoryDTO categoryDTO = new CategoryDTO();
        modelAndView.addObject("category", categoryDTO);
        modelAndView.setViewName("category_form");
        return modelAndView;
    }

    @RequestMapping(value = CategoryUrls.Categories.Save.FULL, method = RequestMethod.POST)
    public ModelAndView showCategoryNewForm(@Valid Category category, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        CategoryDTO categoryExists = categoryService.findCategoryByName(category.getName());
        if (categoryExists != null) {
            bindingResult
                    .rejectValue("name", "error.category",
                            messageProperties.getCategoryError());
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("category_form");
        } else {
            modelAndView.addObject("category", new Category());
            categoryService.save(category);
            modelAndView.setViewName("redirect:/categories");
        }
        return modelAndView;
    }

    @RequestMapping(value = CategoryUrls.Categories.Delete.DeleteId.FULL, method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteById(id);

        return "redirect:/categories";
    }

    @RequestMapping(value = CategoryUrls.Categories.Page.PageNo.FULL, method = RequestMethod.GET)
    public String findPaginatedCategory(@PathVariable(value = "pageNo") int pageNo,
                                        @RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir,
                                        Model model) {
        Page<CategoryDTO> page = categoryService.findPaginated(pageNo, configurationProperties.getPageSize(), sortField, sortDir);
        List<CategoryDTO> listCategories = page.getContent();

        model.addAttribute("currentPageCat", pageNo);
        model.addAttribute("totalPagesCat", page.getTotalPages());
        model.addAttribute("totalItemsCat", page.getTotalElements());

        model.addAttribute("sortFieldCat", sortField);
        model.addAttribute("sortDirCat", sortDir);
        model.addAttribute("reverseSortDirCat", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCategories", listCategories);
        return CategoryUrls.Categories.FuLL;
    }
}