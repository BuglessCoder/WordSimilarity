package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PrimitiveTreeTests {
  
    public static void main(String[] args){
    	   
    	String primitive = "指代";
        List<Integer> list = Primitive.getParents(primitive);
        for(Integer i : list){
            System.out.println(i);
        }     
                 
    }
}

