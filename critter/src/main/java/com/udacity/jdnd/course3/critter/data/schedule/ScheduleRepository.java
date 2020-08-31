package com.udacity.jdnd.course3.critter.data.schedule;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    public List<Schedule> findAllByPetsId(Long id);
    public List<Schedule> findAllByEmployeesId(Long id);
    public List<Schedule> findAllByPetsOwnerId(Long id);
}
