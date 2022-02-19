package Exceptions;

public class FileExcept extends Exception{
    public FileExcept(String message){super(message);}
    @Override
    public String getMessage(){
        return "FILE ERROR: " + super.getMessage();
    }
}
