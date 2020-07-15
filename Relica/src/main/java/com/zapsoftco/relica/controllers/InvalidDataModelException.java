package com.zapsoftco.relica.controllers;

public class InvalidDataModelException extends RuntimeException {

    public InvalidDataModelException(){
        super();
    }

    public InvalidDataModelException(String message){
        super(message);
    }


}
