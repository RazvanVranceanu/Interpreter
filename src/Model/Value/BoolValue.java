package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

import java.util.Objects;

public class BoolValue implements Value{
    private final boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue(){return this.value;}

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof  BoolValue)
            return Objects.equals(((BoolValue)other).getValue(),value);
        return false;
    }

    @Override
    public String toString(){return String.valueOf(value);}

    @Override
    public Value deepCopy() {
        return new BoolValue(value);
    }
}
