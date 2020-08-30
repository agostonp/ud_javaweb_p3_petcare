package com.udacity.jdnd.course3.critter.data.schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.user.Employee;
import com.udacity.jdnd.course3.critter.data.user.EmployeeSkill;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @EqualsAndHashCode
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
      name = "schedule_employee",
      joinColumns = { @JoinColumn(name = "employee_id") },
      inverseJoinColumns = { @JoinColumn(name = "schedule_id")}
    )
    private List<Employee> employees;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
      name = "schedule_pet",
      joinColumns = { @JoinColumn(name = "pet_id") },
      inverseJoinColumns = { @JoinColumn(name = "schedule_id")}
    )
    private List<Pet> pets;

    private LocalDate date;

    @ElementCollection
    private Set<EmployeeSkill> activities;

}