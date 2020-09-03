package com.udacity.jdnd.course3.critter.service;

import java.util.List;

import com.udacity.jdnd.course3.critter.data.schedule.Schedule;
import com.udacity.jdnd.course3.critter.data.schedule.ScheduleRepository;

import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Schedule get(Long id) {
        return scheduleRepository.findById(id).orElseThrow();
    }

    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesForPet(Long petId) {
        return scheduleRepository.findAllByPetsId(petId);
    }

    public List<Schedule> getSchedulesForEmployee(Long employeeId) {
        return scheduleRepository.findAllByEmployeesId(employeeId);
    }

    public List<Schedule> getSchedulesForCustomer(Long customerId) {
        return scheduleRepository.findAllByPetsOwnerId(customerId);
    }
}