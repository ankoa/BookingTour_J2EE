package com.tourbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tourbooking.model.Customer;
import com.tourbooking.service.CustomerService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CustomerControllerAdmin {

    private final CustomerService customerService;

    @Autowired
    public CustomerControllerAdmin(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public String getAllCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        System.out.println("Total customers: " + customers.size());
        model.addAttribute("customers", customers);
        return "customer-all"; 
    }


    @GetMapping("/customer-edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "customer-edit";
    }

    @PostMapping("/customer-edit/{id}")
    public String updateCustomer(@PathVariable("id") int id, @ModelAttribute("customer") Customer customer) {
        customerService.updateCustomer(id, customer);
        return "redirect:/admin/customer";
    }


    @PostMapping("/customer-delete/{id}")
    public String deleteCustomer(@PathVariable("id") int id) {
        customerService.deleteCustomer(id); 
        return "redirect:/admin/customer"; 
    }
}
