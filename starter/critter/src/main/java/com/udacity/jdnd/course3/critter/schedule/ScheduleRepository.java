package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByPetsId(Long petId);

    List<Schedule> findAllByEmployeesId(Long employeeId);

    List<Schedule> findAllByPetsCustomerId(Long customerId);


}
