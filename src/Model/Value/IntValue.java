package Model.Value;

import Model.Type.IntType;
import Model.Type.Type;

import java.util.Objects;

public class IntValue implements Value{

    private final int value;

    public IntValue(int value){
        this.value = value;
    }

    public int getValue() {return value;}

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof IntValue)
            return Objects.equals(((IntValue)other).getValue(), value);
        return false;
    }

    @Override
    public String toString() { return String.valueOf(value);}

    @Override
    public Value deepCopy(){
        return new IntValue(value);
    }
}
