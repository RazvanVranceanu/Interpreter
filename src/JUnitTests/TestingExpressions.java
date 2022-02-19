package JUnitTests;

import Controller.Controller;
import Exceptions.*;
import Model.ADT.*;
import Model.PrgState;
import Model.Statement.*;
import Model.Type.IntType;
import Model.Type.refType;
import Model.Value.Value;
import Model.Value.*;
import Model.Expression.*;
import Repository.IRepository;
import Repository.Repository;
import org.junit.Test;

import java.io.IOException;
import java.sql.Ref;

import static org.junit.Assert.assertFalse;


public class TestingExpressions {

    @Test
    public void testRelationalExpr1(){
        IDict<String,Value> dict=new Dict<>();
        HeapInterface heap= new Heap();
        Exp exp=new RelationalExp(new ValueExp(new IntValue(5)),new ValueExp(new IntValue(7)),"<");
        try
        {
            Value val=exp.evaluate(dict, heap);
            boolean answer=((BoolValue)val).getValue();
            assert(answer);
            System.out.println("< test passed\n");
        }catch (Exception except){
            System.out.println(except.getMessage());
        }
    }

    @Test
    public void testRelationalExpr2(){
        IDict<String,Value> dict=new Dict<>();
        HeapInterface heap= new Heap();
        Exp exp=new RelationalExp(new ValueExp(new IntValue(5)),new ValueExp(new IntValue(5)),"<=");
        try
        {
            Value val=exp.evaluate(dict, heap);
            boolean answer=((BoolValue)val).getValue();
            assert(answer);
            System.out.println("<= test passed\n");
        }catch (Exception except){
            System.out.println(except.getMessage());
        }
    }

    @Test
    public void testRelationalExpr3(){
        IDict<String,Value> dict=new Dict<>();
        HeapInterface heap= new Heap();
        Exp exp=new RelationalExp(new ValueExp(new IntValue(5)),new ValueExp(new IntValue(5)),"==");
        try
        {
            Value val=exp.evaluate(dict, heap);
            boolean answer=((BoolValue)val).getValue();
            assert(answer);
            System.out.println("== test passed\n");
        }catch (Exception except){
            System.out.println(except.getMessage());
        }
    }

    @Test
    public void testRelationalExpr4(){
        IDict<String,Value> dict=new Dict<>();
        HeapInterface heap= new Heap();
        Exp exp=new RelationalExp(new ValueExp(new IntValue(8)),new ValueExp(new IntValue(7)),">=");
        try
        {
            Value val=exp.evaluate(dict, heap);
            boolean answer=((BoolValue)val).getValue();
            assert(answer);
            System.out.println(">= test passed\n");
        }catch (Exception except){
            System.out.println(except.getMessage());
        }
    }

    @Test
    public void testRelationalExpr5(){
        IDict<String,Value> dict=new Dict<>();
        HeapInterface heap= new Heap();
        Exp exp=new RelationalExp(new ValueExp(new IntValue(7)),new ValueExp(new IntValue(7)),">=");
        try
        {
            Value val=exp.evaluate(dict, heap);
            boolean answer=((BoolValue)val).getValue();
            assert(answer);
            System.out.println(">= test passed\n");
        }catch (Exception except){
            System.out.println(except.getMessage());
        }
    }

    @Test
    public void testRelationalExpr6(){
        IDict<String,Value> dict=new Dict<>();
        HeapInterface heap= new Heap();
        Exp exp=new RelationalExp(new ValueExp(new IntValue(8)),new ValueExp(new IntValue(7)),">");
        try
        {
            Value val=exp.evaluate(dict, heap);
            boolean answer=((BoolValue)val).getValue();
            assert(answer);
            System.out.println("> test passed\n");
        }catch (Exception except){
            System.out.println(except.getMessage());
        }
    }

    @Test
    public void testRelationalExpr7(){
        IDict<String,Value> dict=new Dict<>();
        HeapInterface heap= new Heap();
        Exp exp=new RelationalExp(new ValueExp(new IntValue(8)),new ValueExp(new IntValue(7)),"!=");
        try
        {
            Value val=exp.evaluate(dict, heap);
            boolean answer=((BoolValue)val).getValue();
            assert(answer);
            System.out.println("!= test passed\n");
        }catch (Exception except){
            System.out.println(except.getMessage());

        }
    }


    @Test
    public void testAll() {
        testRelationalExpr1();
        testRelationalExpr2();
        testRelationalExpr3();
        testRelationalExpr4();
        testRelationalExpr5();
        testRelationalExpr6();
        testRelationalExpr7();
        System.out.println("\nAll tests passed!\n");
    }
}
