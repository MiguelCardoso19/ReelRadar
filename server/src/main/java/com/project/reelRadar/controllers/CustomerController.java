package com.project.reelRadar.controllers;

import com.project.reelRadar.dtos.CustomerRecordDTO;
import com.project.reelRadar.models.CustomerModel;
import com.project.reelRadar.repositories.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/customers")
    public ResponseEntity<CustomerModel> saveCustomer(@RequestBody @Valid CustomerRecordDTO customerRecordDTO) {
        CustomerModel customerModel = new CustomerModel();
        BeanUtils.copyProperties(customerRecordDTO, customerModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerRepository.save(customerModel));
    }
}
