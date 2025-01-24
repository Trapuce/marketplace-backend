package com.trapuce.marketplace.exceptions;

public class UserNotFoundException  extends RuntimeException{

   public UserNotFoundException(String message){
     super(message);
   }
    
}
