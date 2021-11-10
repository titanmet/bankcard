package com.ratnikov.bankcard.controller;

import com.ratnikov.bankcard.model.Category;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    @GetMapping("/categories")
    public String listCategories(Model model) {
        List<Category> listCategories = categoryRepository.findAll();
        model.addAttribute("listCategories", listCategories);
        findPaginatedCategory(1, "id", "asc", model);
        return "categories";
    }

    @GetMapping("/searchcategories")
    public String listCategorySearch(Category category, Model model, String keyword) {
        if (keyword != null) {
            List<Category> listCategories = categoryService.getByKeyword(keyword);
            model.addAttribute("listCategories", listCategories);
        } else {
            List<Category> listCategories = categoryRepository.findAll();
            model.addAttribute("listCategories", listCategories);
        }
        return "categories";
    }

    @RequestMapping(value="/categories/new", method = RequestMethod.GET)
    public ModelAndView categoriesNew(){
        ModelAndView modelAndView = new ModelAndView();
        Category category = new Category();
        modelAndView.addObject("category", category);
        modelAndView.setViewName("category_form");
        return modelAndView;
    }

    @RequestMapping(value = "/categories/save", method = RequestMethod.POST)
    public ModelAndView showCategoryNewForm(@Valid Category category, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Category categoryExists = categoryService.findCategoryByName(category.getName());
        if (categoryExists != null) {
            bindingResult
                    .rejectValue("name", "error.category",
                            "Категория с таким именем уже есть.");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("category_form");
        } else {
            modelAndView.addObject("category", new Category());
            categoryRepository.save(category);
            modelAndView.setViewName("redirect:/categories");
        }
        return modelAndView;
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) {
        categoryRepository.deleteById(id);

        return "redirect:/categories";
    }

    @GetMapping("/categories/page/{pageNo}")
    public String findPaginatedCategory(@PathVariable(value = "pageNo") int pageNo,
                                        @RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir,
                                        Model model) {
        int pageSize = 5;

        Page<Category> page = categoryService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Category> listCategories = page.getContent();

        model.addAttribute("currentPageCat", pageNo);
        model.addAttribute("totalPagesCat", page.getTotalPages());
        model.addAttribute("totalItemsCat", page.getTotalElements());

        model.addAttribute("sortFieldCat", sortField);
        model.addAttribute("sortDirCat", sortDir);
        model.addAttribute("reverseSortDirCat", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCategories", listCategories);
        return "categories";
    }
}
