package com.codegym.controller;

import com.codegym.model.MyFile;
import com.codegym.model.Product;
import com.codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/home")
    public ModelAndView showStore(@RequestParam("search") Optional<String> search, @PageableDefault(size = 5,sort = "price") Pageable pageable) {
        Page<Product> products;
        if (search.isPresent()) {
            products = productService.findAllByNameContaining(search.get(), pageable);
            ModelAndView modelAndView = new ModelAndView("product/home");
            modelAndView.addObject("products", products);
            modelAndView.addObject("search", search.get());
            return modelAndView;
        } else {
            products = productService.findAll(pageable);
            ModelAndView modelAndView = new ModelAndView("product/home");
            modelAndView.addObject("products", products);
            return modelAndView;
        }
    }

    @GetMapping("/create-product")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("product/create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @PostMapping("/create-product")
    public ModelAndView doCreateForm(@ModelAttribute("product") Product product) {
        Product product1 = productService.save(product);
        ModelAndView modelAndView = new ModelAndView("product/upload");
        modelAndView.addObject("myFile", new MyFile());
        modelAndView.addObject("product", product1);
        return modelAndView;

    }

    @GetMapping("/delete-product/{id}")
    public ModelAndView showDeleteCustomer(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (product != null) {
            ModelAndView modelAndView = new ModelAndView("product/delete");
            modelAndView.addObject("product", product);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-product")
    public String deleteCustomer(@ModelAttribute("product") Product product) {
        productService.remote(product.getId());
        return "redirect:home";
    }

    @GetMapping("/edit-product/{id}")
    public ModelAndView showEditCustomer(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (product != null) {
            ModelAndView modelAndView = new ModelAndView("product/edit");
            modelAndView.addObject("myFile",new MyFile());
            modelAndView.addObject("product", product);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-product")
    public ModelAndView editCustomer(@ModelAttribute("product") Product product) {
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("product/edit");
        modelAndView.addObject("product", product);
        modelAndView.addObject("message", "Product update successfully");
        return modelAndView;
    }

    @GetMapping("/info/{id}")
    public ModelAndView showItem(@PathVariable Long id) {
        Product product = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView("product/item");
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    //Up load images
    @RequestMapping(value = "/uploadFile/{id}", method = RequestMethod.POST)
    public String uploadFile(@PathVariable long id, MyFile myFile) throws IOException {

        MultipartFile multipartFile = myFile.getMultipartFile();
        String fileName = multipartFile.getOriginalFilename();
        Product product = productService.findById(id);
        product.setImage(fileName.intern());
        productService.save(product);

        File file = new File("/home/dieunguyen/Downloads/", fileName);
        multipartFile.transferTo(file);
        return "redirect:/home";
    }
}
