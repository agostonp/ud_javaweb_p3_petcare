package com.udacity.jdnd.course3.critter.controller.user;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.user.Customer;
import com.udacity.jdnd.course3.critter.data.user.Employee;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;

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
    private final EmployeeService employeeService;

    public UserController(CustomerService customerService, EmployeeService employeeService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertCustomerDTO2Customer(customerDTO);
        Long customerId = customerService.save(customer);
        CustomerDTO newCustomerDTO = new CustomerDTO(customerDTO);
        newCustomerDTO.setId(customerId);
        return newCustomerDTO;
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
        Employee employee = convertEmployeeDTO2Employee(employeeDTO);
        Employee newEmployee = employeeService.save(employee);
        return convertEmployee2EmployeeDTO(newEmployee);
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.get(employeeId);
        return convertEmployee2EmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public EmployeeDTO setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeService.setAvailability(daysAvailable, employeeId);
        return convertEmployee2EmployeeDTO(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        if(employeeDTO == null) {
            return new ArrayList<>();
        }
        else {
            List<Employee> employees = employeeService.getAllBySkillsAndDay(employeeDTO.getSkills(), employeeDTO.getDate());
            return convertEmployeeList2EmployeeDTOList(employees);
        }
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

    private static EmployeeDTO convertEmployee2EmployeeDTO(Employee employee) {
        if(employee == null) {
            return null;
        }
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);

        if(employee.getSkills() != null) {
            employeeDTO.setSkills(new HashSet<>(employee.getSkills()));
        }
        if(employee.getDaysAvailable() != null) {
            employeeDTO.setDaysAvailable(new HashSet<>(employee.getDaysAvailable()));
        }

        return employeeDTO;
    }

    private static List<EmployeeDTO> convertEmployeeList2EmployeeDTOList(List<Employee> employees) {
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        if(employees != null) {
            employeeDTOs = employees.stream().map(UserController::convertEmployee2EmployeeDTO).collect(Collectors.toList());
        }
        return employeeDTOs;
    }

    private static Employee convertEmployeeDTO2Employee(EmployeeDTO employeeDTO) {
        if(employeeDTO == null) {
            return null;
        }
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        if(employee.getId() != null && employee.getId() <= 0L) {
            employee.setId(null);
        }

        if(employeeDTO.getSkills() != null) {
            employee.setSkills(new HashSet<>(employeeDTO.getSkills()));
        }
        if(employeeDTO.getDaysAvailable() != null) {
            employee.setDaysAvailable(new HashSet<>(employeeDTO.getDaysAvailable()));
        }

        return employee;
    }

}
