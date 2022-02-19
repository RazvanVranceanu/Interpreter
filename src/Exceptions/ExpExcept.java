package Exceptions;

public class ExpExcept extends Exception{
    public ExpExcept(String message){super(message);}
    @Override
    public String getMessage() {
        return "\n EXP ERROR: " + super.getMessage();
    }
}