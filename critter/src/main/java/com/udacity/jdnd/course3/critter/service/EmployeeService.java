package com.udacity.jdnd.course3.critter.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.udacity.jdnd.course3.critter.data.user.Employee;
import com.udacity.jdnd.course3.critter.data.user.EmployeeRepository;
import com.udacity.jdnd.course3.critter.data.user.EmployeeSkill;

import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee get(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee setAvailability(Set<DayOfWeek> daysAvailable, Long id) {
        return employeeRepository.setAvailability(daysAvailable, id);
    }

    public List<Employee> getAllBySkillsAndDay(Set<EmployeeSkill> skills, LocalDate date) {
        if(skills == null || skills.isEmpty() || date == null) {
            return new ArrayList<>();
        }
        else {
            List<Employee> employees = employeeRepository.findAllBySkillsAndDay(skills, date.getDayOfWeek());
            employees.removeIf(emp -> (!emp.getSkills().containsAll(skills)));
            return employees;
        }
    }

}