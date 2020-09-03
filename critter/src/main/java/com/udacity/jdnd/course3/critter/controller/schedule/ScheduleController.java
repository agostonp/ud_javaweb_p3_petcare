package com.udacity.jdnd.course3.critter.controller.schedule;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.schedule.Schedule;
import com.udacity.jdnd.course3.critter.data.user.Employee;
import com.udacity.jdnd.course3.critter.service.ScheduleService;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTO2Schedule(scheduleDTO);
        Schedule newSchedule = scheduleService.save(schedule);
        return convertSchedule2ScheduleDTO(newSchedule);
    }

    @GetMapping("/{scheduleId}")
    public ScheduleDTO getSchedule(@PathVariable long scheduleId) {
        Schedule schedule = scheduleService.get(scheduleId);
        return convertSchedule2ScheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAll();
        return convertScheduleList2ScheduleDTOList(schedules);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getSchedulesForPet(petId);
        return convertScheduleList2ScheduleDTOList(schedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getSchedulesForEmployee(employeeId);
        return convertScheduleList2ScheduleDTOList(schedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getSchedulesForCustomer(customerId);
        return convertScheduleList2ScheduleDTOList(schedules);
    }

    private static ScheduleDTO convertSchedule2ScheduleDTO(Schedule schedule) {
        if(schedule == null) {
            return null;
        }
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        if(schedule.getActivities() != null) {
            scheduleDTO.setActivities(new HashSet<>(schedule.getActivities()));
        }

        if(schedule.getPets() != null) {
            List<Long> petIds = schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList());
            scheduleDTO.setPetIds(petIds);
        }

        if(schedule.getEmployees() != null) {
            List<Long> employeeIds = schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList());
            scheduleDTO.setEmployeeIds(employeeIds);
        }

        return scheduleDTO;
    }

    private static List<ScheduleDTO> convertScheduleList2ScheduleDTOList(List<Schedule> schedules) {
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        if(schedules != null) {
            scheduleDTOs = schedules.stream().map(ScheduleController::convertSchedule2ScheduleDTO).collect(Collectors.toList());
        }
        return scheduleDTOs;
    }

    private static Schedule convertScheduleDTO2Schedule(ScheduleDTO scheduleDTO) {
        if(scheduleDTO == null) {
            return null;
        }
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        if(schedule.getId() != null && schedule.getId() <= 0L) {
            schedule.setId(null);
        }

        if(scheduleDTO.getActivities() != null) {
            schedule.setActivities(new HashSet<>(scheduleDTO.getActivities()));
        }

        if(scheduleDTO.getPetIds() != null) {
            // List<Pet> petList = new ArrayList<>();
            // for(Long petId : scheduleDTO.getPetIds()) {
            //     Pet pet = new Pet();
            //     pet.setId(petId);
            //     petList.add(pet);
            // }
            List<Pet> petList = scheduleDTO.getPetIds().stream().map(Pet::builder).collect(Collectors.toList());

            schedule.setPets(petList);
        }

        if(scheduleDTO.getEmployeeIds() != null) {
            // List<Employee> employeeList = new ArrayList<>();
            // for(Long employeeId : scheduleDTO.getEmployeeIds()) {
            //     Employee employee = new Employee();
            //     employee.setId(employeeId);
            //     employeeList.add(employee);
            // }
            List<Employee> employeeList = scheduleDTO.getEmployeeIds().stream().map(Employee::builder).collect(Collectors.toList());
            schedule.setEmployees(employeeList);
        }

        return schedule;
    }

}
