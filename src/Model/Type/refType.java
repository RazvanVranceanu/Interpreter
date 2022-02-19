package Model.Type;

import Model.Value.Value;
import Model.Value.refValue;

public class refType implements Type{
    private final Type inner;

    public refType(Type locationType){
        this.inner = locationType;
    }

    public Type getInner(){
        return inner;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof refType) {
            return inner.equals(((refType)obj).getInner());
        }
        else {
            return false;
        }
    }

    @Override
    public Value defaultValue() {
        return new refValue(0,inner);
    }

    @Override
    public String toString(){
        return " ref_type"+inner.toString();
    }
}
