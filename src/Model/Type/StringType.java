package Model.Type;

import Model.Value.StringValue;
import Model.Value.Value;

public class StringType implements Type{

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public String toString(){
        return " string_type";
    }

    @Override
    public boolean equals(Object other){
        return other instanceof StringType;
    }
}
