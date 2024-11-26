package com.tourbooking.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.tourbooking.model.Transport;
import com.tourbooking.service.TransportService;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TransportControllerAdmin {
	
	private static final Logger logger = LoggerFactory.getLogger(TransportControllerAdmin.class);
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
        return "admin/transport-all"; 
    }
    
    @GetMapping("/transports/listTransport")
    @ResponseBody
    public ResponseEntity<List<Transport>> getAllTransportsJson() {
        List<Transport> transports = transportService.getAllTransports();  // Lấy tất cả phương tiện từ service
        if (transports.isEmpty()) {
        	 System.out.println("ko có transport " + transports.size());
        	logger.warn("No transport found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(transports);  // Trả về 204 nếu không có phương tiện
        } else {
        	logger.info("Total accounts: {}", transports.size());
        	 System.out.println("Total transports: " + transports.size());
            return ResponseEntity.ok(transports);  // Trả về danh sách phương tiện với mã 200
        }
    }

    @GetMapping("/transports/{id}")
    @ResponseBody
    public Transport getTransportById(@PathVariable("id") int id) {
        return transportService.getTransportById(id);
    }

    // Phương thức hiển thị form chỉnh sửa phương tiện
    @PostMapping("/transports/add")
    public ResponseEntity<String> addTransport(@RequestBody Transport transport) {
        transportService.addTransport(transport);
        return ResponseEntity.ok("Phương tiện đã được thêm thành công");
    }

    @PutMapping("/transports/update/{id}")
    @ResponseBody
    public ResponseEntity<String> updateTransport(@PathVariable("id") int id, @RequestBody Transport transport) {
        boolean updatedTransport = transportService.updateTransport(transport);
        if (updatedTransport) {
            return ResponseEntity.ok("Transport updated successfully");
        } else {                                                                                                                                                                 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transport not found");
        }
    }
//    public ResponseEntity<String> updateTransport(@PathVariable("id") int id, @RequestBody Transport transport) {
//        boolean updatedTransport = transportService.updateTransport(transport);
//        if (updatedTransport) {
//            return ResponseEntity.ok("Transport updated successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transport not found");
//        }
//    }


//    @DeleteMapping("/transports/delete/{id}")
//    public ResponseEntity<String> deleteTransport(@PathVariable("id") int id) {
//        transportService.deleteTransport(id);
//        return ResponseEntity.ok("Phương tiện đã được xóa thành công");
//    }
    @DeleteMapping("/transports/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deactivateTransport(@PathVariable String id) {
        boolean deactivated = transportService.deleteTransport(id);
        if (deactivated) {
            return ResponseEntity.ok("Account deactivated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to deactivate account.");
        }
    }
}
