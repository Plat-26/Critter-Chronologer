package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

//@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }


    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getByPet(Long petId){
        return scheduleRepository.findAllByPets(petId);
    }

    public List<Schedule> getByEmployee(Long employeeId) {
        return scheduleRepository.findAllByEmployees(employeeId);
    }

    public List<Schedule> getByCustomer(Long customerId) {
        return scheduleRepository.findAllByEmployees(customerId);
    }
}
