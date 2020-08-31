package com.udacity.jdnd.course3.critter.data.user;

import java.time.DayOfWeek;
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

    public void persist(Employee employee) {
        entityManager.persist(employee);
    }

    public Employee find(Long id) {
        return entityManager.find(Employee.class, id);
    }

    public Employee merge(Employee employee) {
        return entityManager.merge(employee);
    }

    public void delete(Long id) {
        Employee employeeRef = entityManager.getReference(Employee.class, id);
        entityManager.remove(employeeRef);
    }

    public List<Employee> findAllBySkillsAndDay(Set<EmployeeSkill> skills, DayOfWeek day) {
        TypedQuery<Employee> query = entityManager.createNamedQuery("Employee.findAllByServicesAndDay", Employee.class);
        query.setParameter("skills", skills);
        query.setParameter("day", day);
        return query.getResultList();
    }

}
