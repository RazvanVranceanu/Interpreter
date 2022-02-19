package Controller;

import Model.PrgState;
import Model.Value.Value;
import Model.Value.refValue;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GarbageCollector {

    static Map<Integer, Value> unsafeGarbageCollector(List<Integer> symbolTableAddr, Map<Integer, Value> heap)
    {
        return heap.entrySet().stream().filter(e->symbolTableAddr.contains(
                e.getKey()
        )).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    static Set<Integer> getAddrFromSymTable(Collection<Value> symTableValues, Collection<Value> heap){
        return Stream.concat(symTableValues.stream(),heap.stream()).filter(
                value->value instanceof refValue
        ).map(value->{
            refValue value1=(refValue)value;
            return value1.getAddress();
        }).collect(Collectors.toSet());
    }

    public static void conservativeGarbageCollector(List<PrgState> prgStates){

        var heap=Objects.requireNonNull(prgStates.stream().map(
                prg->getAddrFromSymTable(prg.getSymbolTable().getContent().values(),
                        prg.getHeap().getHeap().values())
        ).map(Collection::stream).reduce(Stream::concat).orElse(null).collect(
                Collectors.toList()
        ));

        prgStates.forEach(prg->{
            prg.getHeap().setHeap(
                    (HashMap<Integer, Value>) unsafeGarbageCollector(heap,prgStates.get(0).getHeap().getHeap())
            );
        });

    }

}
