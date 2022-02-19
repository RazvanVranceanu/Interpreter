package Exceptions;

public class HeapExcept extends Exception {
    public HeapExcept(String message){super(message);}
    @Override
    public String getMessage() {
        return "\n± HEAP ERROR: " + super.getMessage();
    }
}
