package com.udacity.jdnd.course3.critter;

public class ObjectNotFoundException extends RuntimeException{

    public ObjectNotFoundException(){
        super();
    }

    public ObjectNotFoundException(String message){
        super(message);
    }
}
