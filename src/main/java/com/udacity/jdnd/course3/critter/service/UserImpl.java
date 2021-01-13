package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.ObjectNotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserImpl {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public Customer saveCustomer(Customer customer, List<Long> petIds){
        List<Pet> pets = new ArrayList<>();
        if(petIds!= null && !petIds.isEmpty()){
            pets = petIds.stream().map((petId)->petRepository.getOne(petId)).collect(Collectors.toList());
        }
        customer.setPets(pets);
        return customerRepository.save(customer);
    }

    public Customer findById(long customerId){
        return customerRepository.findById(customerId).orElseThrow(()->new ObjectNotFoundException("Customer not found"));
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
    public Employee getEmployeeById(long employeeId){
        return employeeRepository.findById(employeeId).orElseThrow(()->new ObjectNotFoundException("Employee not found"));
    }
    public void setAvailability(Set<DayOfWeek> daysAvailable,long employeeId){
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->new ObjectNotFoundException("Employee not found"));
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }
    public List<Employee> getEmployeesForService(Set<EmployeeSkill> skills,DayOfWeek dayOfWeek){
        List<Employee> employees = employeeRepository.findAllBySkillsInAndDaysAvailableContains(skills,dayOfWeek);
        List<Employee> employeeList = new ArrayList<>();

        employees.forEach(employee->{
            if(employee.getSkills().containsAll(skills)){
                employeeList.add(employee);
            }
        });
        return employeeList;
    }

    public List<Employee> findAllById(List<Long> employeeId){
        return employeeRepository.findAllById(employeeId);
    }
}
