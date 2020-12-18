/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snlp.simplefactchecker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */

public class Main {
    public static void main(String[]args){
        Map<String, String> statement_map = new LinkedHashMap<>();
        Map<String, Boolean> statement_value = new LinkedHashMap<>();
        try {
            BufferedReader TSVReader = new BufferedReader(new FileReader("./SNLP2019_training.tsv"));
            String line = null;
            while((line = TSVReader.readLine()) != null){
                String[] lineItems = line.split("\t"); //splitting the line and adding its items in String[]
                statement_map.put(lineItems[0], lineItems[1]);
                statement_value.put(lineItems[0], "1.0".equals(lineItems[2]));
            }
        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("FILE NOT FOUND");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("------- FACTS ----------");
        Set<String> keys = statement_map.keySet();
        for(String key:keys){
            System.out.println("Fact ID:"+key+"\tFact Statement:"+statement_map.get(key)+"\tFact Value:"+statement_value.get(key));
        }
    }  
}
