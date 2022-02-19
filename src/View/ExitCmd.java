package View;

public class ExitCmd extends Command{
    public ExitCmd(String key, String desc){
        super(key, desc);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
