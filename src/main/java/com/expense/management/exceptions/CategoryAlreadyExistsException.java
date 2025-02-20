package com.expense.management.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException{
    public CategoryAlreadyExistsException(String message){
        super(message);
    }
}
