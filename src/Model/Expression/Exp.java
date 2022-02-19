package Model.Expression;

import Exceptions.ExpExcept;
import Model.ADT.HeapInterface;
import Model.ADT.IDict;
import Model.Type.Type;
import Model.Value.Value;

public interface Exp {
    Value evaluate(IDict<String, Value> symbolTable, HeapInterface heap) throws ExpExcept;
    Type typeCheck(IDict<String,Type> typeEnv) throws Exception;
}
