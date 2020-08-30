package com.udacity.jdnd.course3.critter.data.pet;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.udacity.jdnd.course3.critter.data.schedule.Schedule;
import com.udacity.jdnd.course3.critter.data.user.Customer;

import org.hibernate.annotations.Nationalized;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @EqualsAndHashCode
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PetType type;

    @Nationalized
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Customer owner;

    private LocalDate birthDate;
    private String notes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
      name = "schedule_pet",
      joinColumns = { @JoinColumn(name = "schedule_id") },
      inverseJoinColumns = { @JoinColumn(name = "pet_id")}
    )
    private List<Schedule> schedules;
}