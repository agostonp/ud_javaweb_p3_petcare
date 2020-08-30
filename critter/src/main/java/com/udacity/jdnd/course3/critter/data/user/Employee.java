package com.udacity.jdnd.course3.critter.data.user;

import java.time.DayOfWeek;
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

import com.udacity.jdnd.course3.critter.data.schedule.Schedule;

import org.hibernate.annotations.Nationalized;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
      name = "schedule_employee",
      joinColumns = { @JoinColumn(name = "schedule_id") },
      inverseJoinColumns = { @JoinColumn(name = "employee_id")}
    )
    private List<Schedule> schedules;
}