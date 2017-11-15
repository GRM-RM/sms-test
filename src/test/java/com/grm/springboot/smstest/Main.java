package com.grm.springboot.smstest;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<Character,Integer> map=new HashMap<Character,Integer>();
        Scanner sc = new Scanner(System.in);
        String str = sc.next();

        int size=0;

        for(int i=0;i<str.length();i++){
            char ch=str.charAt(i);
            if(map.size()>0&&map.containsKey(ch)){
                int value=map.get(ch);
                map.put(ch,value+1);
            }else {
                size=size+1;
                map=new HashMap<Character,Integer>();
                map.put(ch,1);
            }
        }

        double length=str.length();
        String result=String.format("%.2f",((int)(length/size*100))/100.0);
        System.out.println(result);
    }
}
