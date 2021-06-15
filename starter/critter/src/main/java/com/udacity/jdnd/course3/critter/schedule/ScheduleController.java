package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import com.udacity.jdnd.course3.critter.user.User;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final PetService petService;
    private final UserService userService;
    private final EmployeeService employeeService;

    public ScheduleController(ScheduleService scheduleService, UserService userService, PetService petService, EmployeeService employeeService) {
        this.scheduleService = scheduleService;
        this.employeeService = employeeService;
        this.userService = userService;
        this.petService = petService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.createSchedule(convertScheduleDTOToEntity(scheduleDTO));
        return convertScheduleEntityToDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAll();
        return convertTODTOList(schedules);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getByPet(petId);
        return convertTODTOList(schedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getByEmployee(employeeId);
        return convertTODTOList(schedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getByCustomer(customerId);
        return convertTODTOList(schedules);
    }

    private ScheduleDTO convertScheduleEntityToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        scheduleDTO.setEmployeeIds(convertToEmployeeIdList(schedule.getEmployees()));
        scheduleDTO.setPetIds(convertPetIdList(schedule.getPets()));
        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        schedule.setEmployees(convertIdsToListOfEmployees(scheduleDTO.getEmployeeIds()));
        schedule.setPets(convertIdsToListOfPets(scheduleDTO.getPetIds()));
        return schedule;
    }

    private List<ScheduleDTO> convertTODTOList(List<Schedule> scheduleList) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule schedule: scheduleList) {
            scheduleDTOS.add(convertScheduleEntityToDTO(schedule));
        }
        return scheduleDTOS;
    }


    /**
     * This method creates a list of ids for the employee list
     */
    private List<Long> convertToEmployeeIdList(List<Employee> employees) {
        List<Long> ids = new ArrayList<>();
        for(Employee employee: employees) {
            ids.add(employee.getId());
        }
        return ids;
    }

    /**
     * This method creates a list of ids for the employee list
     */
    private List<Long> convertPetIdList(List<Pet> pets) {
        if(pets == null) {
            return new ArrayList<>();
        }
        List<Long> ids = new ArrayList<>();
        for(Pet pet: pets) {
            ids.add(pet.getId());
        }
        return ids;
    }

    /**
     * This method converts a list of ids to a list of Pets
     */
    private List<Pet> convertIdsToListOfPets(List<Long> petIds) {
        if(petIds == null) {
            return new ArrayList<>();
        }
        List<Pet> pets = new ArrayList<>();
        for(Long id: petIds) {
            pets.add(petService.getPetById(id));
        }
        return pets;
    }

    /**
     * This method converts a list of ids to a list of Employees
     */
    private List<Employee> convertIdsToListOfEmployees(List<Long> employeeIds) {
        List<Employee> employees = new ArrayList<>();
        for(Long id : employeeIds) {
            employees.add(employeeService.getEmployeeById(id));
        }
        return employees;
    }
}
