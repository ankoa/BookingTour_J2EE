package com.tourbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tourbooking.model.Transport;
import com.tourbooking.service.TransportService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TransportControllerAdmin {

    private final TransportService transportService;

    @Autowired
    public TransportControllerAdmin(TransportService transportService) {
        this.transportService = transportService;
    }

    @GetMapping("/transport")
    public String getAllTransports(Model model) {
        List<Transport> transports = transportService.getAllTransports();
        System.out.println("Total transports: " + transports.size());
        model.addAttribute("transports", transports);
        return "transport-all"; 
    }

    // Phương thức hiển thị form chỉnh sửa phương tiện
    @GetMapping("/transport-edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Transport transport = transportService.getTransportById(id);
        model.addAttribute("transport", transport);
        return "transport-edit"; 
    }

    @PostMapping("/transport-edit/{id}")
    public String updateTransport(@PathVariable("id") int id, @ModelAttribute("transport") Transport transport) {
        transportService.updateTransport(id, transport);
        return "redirect:/admin/transport";
    }

    @PostMapping("/transport-delete/{id}")
    public String deleteTransport(@PathVariable("id") int id) {
        transportService.deleteTransport(id); 
        return "redirect:/admin/transport"; 
    }
    @PostMapping("/transport-add")
    public String addTransport(@ModelAttribute("transport") Transport transport) {
        transportService.addTransport(transport); 
        return "redirect:/admin/transport"; 
    }

    @GetMapping("/transport-add")
    public String showAddForm(Model model) {
        model.addAttribute("transport", new Transport());
        return "transport-add";
    }

}
