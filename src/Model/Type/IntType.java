package Model.Type;

import Model.Value.IntValue;
import Model.Value.Value;

public class IntType implements Type{

    @Override
    public boolean equals(Object other){
        return other instanceof IntType;
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    @Override
    public String toString(){return " int_type";}
}
