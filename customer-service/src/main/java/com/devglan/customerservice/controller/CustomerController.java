package com.devglan.customerservice.controller;

import com.devglan.commons.Customer;
import com.devglan.commons.DataStore;
import com.devglan.commons.Product;
import com.devglan.customerservice.dto.CustomerDto;
import com.devglan.customerservice.feign.client.ProductClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/customers")
@RestController
@ControllerAdvice
public class CustomerController {

    @Autowired
    private ProductClient productClient;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable String id) {
        Customer customer = DataStore
                .listCustomers()
                .stream()
                .filter(cust -> cust.getId().equalsIgnoreCase(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("User not found [id : " + id + "]"));
        List<Product> products = productClient.listProductsByCustomerId(id);
        CustomerDto dto = new CustomerDto();
        BeanUtils.copyProperties(customer, dto);
        dto.setProducts(products);
        //Product pr1 = productClient.getProductById("PRD1");
        //Product pr2 = productClient.create(products.get(0));
        //List<Product> pr3 = productClient.listProducts();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> returnForNotFoundUserException(RuntimeException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("Cause", exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
