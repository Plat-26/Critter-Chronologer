package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);

    }

    public Employee getEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(
                () -> new IllegalStateException("This is is not mapped to a user")
        );
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = getEmployeeById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findForService(EmployeeRequestDTO employeeDTO) {
        List<Employee> availableEmps = new ArrayList<>();
        boolean isAvailable = false;
        boolean isSkilled = false;

        List<Employee> allEmployees = employeeRepository.findAll();
        for(Employee emp : allEmployees) {
            for(DayOfWeek day : emp.getDaysAvailable()) {
                if(employeeDTO.getDate().getDayOfWeek() == day) {
                    isAvailable = true;
                    break;
                }
            }
            for(EmployeeSkill skill : employeeDTO.getSkills()) {
                if(!emp.getSkills().contains(skill)) {
                    break;
                }
                isSkilled = true;
            }
            if(isAvailable && isSkilled) {
                availableEmps.add(emp);
                isAvailable = false;
                isSkilled = false;
            }
        }
        return availableEmps;
    }
}
