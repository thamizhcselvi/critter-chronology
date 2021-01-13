package com.udacity.jdnd.course3.critter.user;

/**
 * A example list of employee skills that could be included on an employee or a schedule request.
 */
public enum EmployeeSkill {
    PETTING("P"), WALKING("W"), FEEDING("F"), MEDICATING("M"), SHAVING("S");

    private String code;

    private EmployeeSkill(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
