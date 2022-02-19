package Exceptions;

public class ControllerException extends Exception{
    public ControllerException(String message){super(message);}
    @Override
    public String getMessage() {
        return "\n EXP ERROR: " + super.getMessage();
    }
}
