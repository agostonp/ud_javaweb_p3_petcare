package com.udacity.jdnd.course3.critter.data.user;

import java.time.DayOfWeek;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Nationalized;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NamedQuery(
    name = "Employee.findAllByServicesAndDay",
    query = "select distinct e " +
    "from Employee as e " +
    "join e.skills as sk " +
    "join e.daysAvailable as da " +
    "where sk in (:skills)" +
    "and da = :day"
)


@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @EqualsAndHashCode
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    private String name;

    @ElementCollection
    private Set<EmployeeSkill> skills;

    @ElementCollection
    private Set<DayOfWeek> daysAvailable;

    public static Employee builder(Long id) {
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}