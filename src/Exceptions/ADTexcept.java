package Exceptions;

public class ADTexcept extends Exception{
    public ADTexcept(String message){super(message);}
    @Override
    public String getMessage(){
        return "ADT ERROR: " + super.getMessage();
    }
}
