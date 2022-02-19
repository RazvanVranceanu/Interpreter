package Exceptions;

public class StmtExcept extends Exception {
    public StmtExcept(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "\n STMT ERROR: " + super.getMessage();
    }
}
