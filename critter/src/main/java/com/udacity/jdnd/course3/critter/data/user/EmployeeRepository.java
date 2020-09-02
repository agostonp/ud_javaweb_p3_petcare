package com.udacity.jdnd.course3.critter.data.user;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class EmployeeRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Employee save(Employee employee) {
        if(entityManager.contains(employee)) {
            System.out.println("#### contains");
            return employee; // Do nothing. Changes will be saved at the end of the PersistenceContext
        }
        else if(employee.getId() == null || employee.getId() <= 0L) {
            System.out.println("#### persist");
            employee.setId(null);
            entityManager.persist(employee);
            return employee;
        }
        else {
            System.out.println("#### merge");
            return entityManager.merge(employee);
        }
    }

    public Employee findById(Long id) {
        return entityManager.find(Employee.class, id);
    }

    public void delete(Long id) {
        Employee employeeRef = entityManager.getReference(Employee.class, id);
        entityManager.remove(employeeRef);
    }

    public Employee setAvailability(Set<DayOfWeek> daysAvailable, Long id) {
        Employee employee = findById(id);
        if(employee != null && daysAvailable != null) {
            employee.setDaysAvailable(new HashSet<>(daysAvailable));
        }
        return employee;
    }

    public List<Employee> findAllBySkillsAndDay(Set<EmployeeSkill> skills, DayOfWeek day) {
        TypedQuery<Employee> query = entityManager.createNamedQuery("Employee.findAllByServicesAndDay", Employee.class);
        query.setParameter("skills", skills);
        query.setParameter("day", day);
        return query.getResultList();
    }

}
