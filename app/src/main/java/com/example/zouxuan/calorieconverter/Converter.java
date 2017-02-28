package com.example.zouxuan.calorieconverter;

import java.util.HashMap;

/**
 * Created by zouxuan on 2/4/17.
 */

public class Converter {
    HashMap<String,Integer> map;
    HashMap<String,RadioType> typeMap;

    public Converter(){
        map=new HashMap<>();
        map.put("pushup",350);
        map.put("situp",200);
        map.put("jumping",10);
        map.put("jogging",12);
        typeMap=new HashMap<>();
        typeMap.put("pushup",RadioType.REPS);
        typeMap.put("situp",RadioType.REPS);
        typeMap.put("jumping",RadioType.MINUTES);
        typeMap.put("jogging",RadioType.MINUTES);
    }

    public int convertFromExercise(String exercise,double amount,RadioType type){
        if(!typeMap.containsKey(exercise)||typeMap.get(exercise)!=type||amount<0){
            return -1;
        }
        int num=map.get(exercise);
        return (int)(100*amount/num);
    }

    public int convertToAnotherExercise(String exercise,double amount,String targetExercise){
        double calorie=convertFromExercise(exercise,amount,typeMap.get(exercise));
        return (int)(calorie*map.get(targetExercise)/100);
    }

    public String getTypeString(String exercise){
        if(!typeMap.containsKey(exercise)){
            return "";
        }
        if(typeMap.get(exercise)==RadioType.MINUTES){
            return "minutes";
        }
        else{
            return "reps";
        }
    }
}
