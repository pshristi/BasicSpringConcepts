package com.example.learningspring.javaConcepts.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class demonstrates various Java collection types and operations.
 */
public class JavaCollections {
    
    /**
     * Demonstrates different ways to create and work with Lists and Arrays.
     */
    public static void demonstrateListsAndArrays() {
        String[] str = {"1", "2"};
        
        // Immutable list
        List<String> immutableList = List.of(str);
        System.out.println("Immutable list: " + immutableList);
        
        // Mutable ArrayList
        ArrayList<String> mutableList = new ArrayList<>(List.of("1", "2", "3", ""));
        System.out.println("Mutable list: " + mutableList);
        
        // Non-resizable but mutable list
        List<String> nonresizeablebutmutableList1 = Arrays.asList(str);
        System.out.println("Non-resizable but mutable list: " + nonresizeablebutmutableList1);
        
        // Another mutable ArrayList
        ArrayList<String> mutableList1 = new ArrayList<>(Arrays.asList("1", "2", "3", ""));
        System.out.println("Another mutable list: " + mutableList1);
        
        // Converting list to array
        String[] str1 = mutableList.toArray(new String[mutableList.size()]);
        System.out.println("Array from list (explicit size): " + Arrays.toString(str1));
        
        // More efficient way to convert list to array
        var str2 = mutableList1.toArray(new String[0]);
        System.out.println("Array from list (size 0): " + Arrays.toString(str2));
    }
}
