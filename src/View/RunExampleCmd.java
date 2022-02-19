package View;

import Controller.Controller;
import Model.ADT.Dict;

public class RunExampleCmd extends Command{
    private Controller ctr;
    public RunExampleCmd(String key, String desc,Controller c) {
        super(key, desc);
        ctr=c;
    }

    @Override
    public void execute(){
        try{
            this.ctr.getRepository().getPrgList().get(0).getExeStack().getValues().get(0).typeCheck(new Dict<>());;
            this.ctr.allSteps();
        }
        catch (Exception ctrexcept)
        {
            ctrexcept.printStackTrace();
            System.out.println(ctrexcept.getMessage());
        }

    }
}
