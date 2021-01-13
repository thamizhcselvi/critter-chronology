package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.ObjectNotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class ScheduleImpl {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PetRepository petRepository;

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(long petId) {
        return scheduleRepository.getDetailsByPet(petRepository.getOne(petId));
    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {
        return scheduleRepository.getDetailsByEmployee(employeeRepository.getOne(employeeId));
    }

    public List<Schedule> getScheduleForCustomer(long customerId) {
        return scheduleRepository.getDetailsByCustomer(customerRepository.getOne(customerId));
    }
}
