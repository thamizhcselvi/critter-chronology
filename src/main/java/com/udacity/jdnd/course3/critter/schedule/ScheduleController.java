package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.service.PetImpl;
import com.udacity.jdnd.course3.critter.service.ScheduleImpl;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.service.UserImpl;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    UserImpl userImpl;
    @Autowired
    PetImpl petImpl;
    @Autowired
    ScheduleImpl scheduleImpl;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        List<Employee> employees = userImpl.findAllById(scheduleDTO.getEmployeeIds());
        List<Pet> pet = petImpl.findAllById(scheduleDTO.getPetIds());
        Schedule schedule = new Schedule();

        BeanUtils.copyProperties(scheduleDTO, schedule);
        schedule.setEmployee(employees);
        schedule.setPet(pet);

        scheduleImpl.createSchedule(schedule);

        employees.forEach(thisEmployee -> {
            if (thisEmployee.getSchedules() == null)
                thisEmployee.setSchedules(new ArrayList<>());
            thisEmployee.getSchedules().add(schedule);
        });
        pet.forEach(thisPet -> {
            if (thisPet.getSchedules() == null)
                thisPet.setSchedules(new ArrayList<>());
            thisPet.getSchedules().add(schedule);
        });
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleImpl.getAllSchedules();
        return schedules.stream()
                .map(this::getScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = petImpl.findById(petId).getSchedules();
        return setHashScheduleDTO(schedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee employee = userImpl.getEmployeeById(employeeId);
        if(employee.getSchedules()==null) return null;
        List<Schedule> schedules = employee.getSchedules();
        return schedules.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Pet> pets = userImpl.findById(customerId).getPets();
        HashMap<Long,Schedule> scheduleHashMap = new HashMap<>();

        pets.stream().forEach(pet -> {
            pet.getSchedules().stream().forEach(schedule -> {
                scheduleHashMap.put(schedule.getId(),schedule);
            });
        });
        return setHashScheduleDTO(new ArrayList<>(scheduleHashMap.values()));
    }

    private List<ScheduleDTO> setHashScheduleDTO(List<Schedule> schedules) {
        return schedules.stream().map(schedule -> {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            BeanUtils.copyProperties(schedule, scheduleDTO);

            scheduleDTO.setEmployeeIds(schedule.getEmployee().stream().map(Employee::getId).collect(Collectors.toList()));
            scheduleDTO.setPetIds(schedule.getPet().stream().map(Pet::getId).collect(Collectors.toList()));
            return scheduleDTO;
        }).collect(Collectors.toList());
    }

    private ScheduleDTO getScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule,scheduleDTO);
        List<Long> employeeId = schedule.getEmployee().stream().map(Employee::getId).collect(Collectors.toList());
        List<Long> petId = schedule.getPet().stream().map(Pet::getId).collect(Collectors.toList());
        scheduleDTO.setEmployeeIds(employeeId);
        scheduleDTO.setPetIds(petId);
        return scheduleDTO;
    }
}
