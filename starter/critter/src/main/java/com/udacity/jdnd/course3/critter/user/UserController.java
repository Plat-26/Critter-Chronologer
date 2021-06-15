package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final PetService petService;

    public UserController(CustomerService customerService, EmployeeService employeeService, PetService petService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertCustomerDTOToEntity(customerDTO);
        return convertCustomerEntityToDTO(customerService.createCustomer(customer));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAll();
        return convertCustomersToListOfDTOs(customers);
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getCustomerByPetId(petId);
        return convertCustomerEntityToDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertEmployeeDTOToEntity(employeeDTO);
        return convertEmployeeEntityToDTO(employeeService.createEmployee(employee));
    }


    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return convertEmployeeEntityToDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.findForService(employeeDTO);
        return convertEmployeeListToDTOList(employees);
    }

    private List<EmployeeDTO> convertEmployeeListToDTOList(List<Employee> employees) {
        if(employees != null) {
            List<EmployeeDTO> employeeDTOS = new ArrayList<>();
            for(Employee employee : employees) {
                employeeDTOS.add(convertEmployeeEntityToDTO(employee));
            }
            return employeeDTOS;
        }
        return new ArrayList<>();
    }

    private Customer convertCustomerDTOToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
//        customer.setPets(convertPetIdsToPets(customerDTO.getPetIds()));
        return customer;
    }

    private CustomerDTO convertCustomerEntityToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        customerDTO.setPetIds(convertPetIdList(customer.getPets()));
        return customerDTO;
    }

    private List<CustomerDTO> convertCustomersToListOfDTOs(List<Customer> customers) {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for(Customer customer : customers) {
            customerDTOS.add(convertCustomerEntityToDTO(customer));
        }
        return customerDTOS;
    }

    private Employee convertEmployeeDTOToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    private EmployeeDTO convertEmployeeEntityToDTO(User employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    /**
     * This method converts a list of ids to a list of Pets
     */
    private List<Pet> convertPetIdsToPets(List<Long> petIds) {
        if(petIds != null) {
            List<Pet> pets = new ArrayList<>();
            for(Long id: petIds) {
                pets.add(petService.getPetById(id));
            }
            return pets;
        }
        return null;
    }

    /**
     * This method creates a list of ids for the employee list
     */
    private List<Long> convertPetIdList(List<Pet> pets) {
        if (pets != null) {
            List<Long> ids = new ArrayList<>();
            for(Pet pet: pets) {
                ids.add(pet.getId());
            }
            return ids;
        }
       return null;
    }

}
