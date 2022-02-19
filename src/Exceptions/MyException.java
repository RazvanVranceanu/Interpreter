package Exceptions;

public class MyException extends Exception{
    public MyException(String msg){super(msg);}

    @Override
    public String getMessage(){
        return "UNIMPLEMENTED ERROR: " + super.getMessage();
    }
}
