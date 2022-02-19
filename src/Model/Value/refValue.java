package Model.Value;

import Model.Type.Type;
import Model.Type.refType;

import java.util.Objects;

public class refValue implements Value{
    private final Type locationType;
    private final int address;

    public refValue(int address, Type inner) {
        this.address=address;
        this.locationType=inner;
    }


    public int getAddress(){return address;}
    public Type getLocationType(){return locationType;}

    @Override
    public Type getType() {
        return new refType(locationType);
    }

    @Override
    public String toString(){
        return String.valueOf(address) + " , " +locationType.toString();
    }

    public boolean equals(Object obj){

        if(obj instanceof refValue)
            return (Objects.equals(((refValue) obj).getAddress(),address)
                    && locationType.equals(((refValue) obj).getLocationType()));
        return false;
    }

    @Override
    public Value deepCopy(){
        return new refValue(address, locationType);
    }
}
