package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

import java.util.Objects;

public class StringValue implements Value{
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue(){return value;}

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof  StringValue)
            return Objects.equals(((StringValue)other).getValue(),value);
        return false;
    }

    @Override
    public Value deepCopy(){
        return new StringValue(value);
    }
}
