package com.udacity.jdnd.course3.critter.controller.user;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.user.Customer;
import com.udacity.jdnd.course3.critter.service.CustomerService;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final CustomerService customerService;

    public UserController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertCustomerDTO2Customer(customerDTO);
        Long customerId = customerService.save(customer);
        customerDTO.setId(customerId);
        return customerDTO;
    }

    @PostMapping("/customer/{customerId}")
    public CustomerDTO saveCustomerWithIdInPath(@PathVariable long customerId, @RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return saveCustomer(customerDTO);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAll();
        return convertCustomerList2CustomerDTOList(customers);
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getByPetId(petId);
        return convertCustomer2CustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    private static CustomerDTO convertCustomer2CustomerDTO(Customer customer) {
        if(customer == null) {
            return null;
        }
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        List<Long> petIds = new ArrayList<>();
        if(customer.getPets() != null) {
            for(Pet pet : customer.getPets()) {
                petIds.add(pet.getId());
            }
        }
        customerDTO.setPetIds(petIds);

        return customerDTO;
    }

    private static List<CustomerDTO> convertCustomerList2CustomerDTOList(List<Customer> customers) {
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        if(customers != null) {
            for(Customer customer : customers) {
                customerDTOs.add(convertCustomer2CustomerDTO(customer));
            }
        }
        return customerDTOs;
    }

    private static Customer convertCustomerDTO2Customer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        if(customerDTO.getPetIds() != null) {
            List<Pet> petList = new ArrayList<>();
            for(Long petId : customerDTO.getPetIds()) {
                Pet pet = new Pet();
                pet.setId(petId);
                petList.add(pet);
            }
            customer.setPets(petList);
        }
        return customer;
    }

}
