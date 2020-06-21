package com.nwnarelith.nssdoc.datamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NssFunction {

    private String fullRef;
    private String name;
    private String parameterList;
    private String returns;
    private String description;


    public NssFunction(String fullRef) {
        this.fullRef = fullRef;
    }


    public void parse(List<String> textCache){
        name = generateName();
        parameterList = generateParamList();
        returns = generateReturns();
        description = generateDescription(textCache);
    }

    public String generateDescription(List<String> textCache){
        int cacheSize = textCache.size();
        List<String> tempCache = new ArrayList<>();
        int rowCounter = 0;
        for(int i = cacheSize-2; i >= 0; i--){
            if(rowCounter >= 10){
                break;
            }
            else if(textCache.get(i).trim().startsWith("//")){
                tempCache.add(textCache.get(i).replace("//", ""));
            }
            else{
                break;
            }
        }

        Collections.reverse(tempCache);
        return String.join(" ",tempCache);

    }

    private String generateName(){
        int leftBracket = fullRef.indexOf("(");
        String tempName = fullRef.substring(0, leftBracket);
        String[] nameArr = tempName.split(" ");
        return nameArr[nameArr.length-1].trim();
    }

    private String generateParamList(){
        int leftBracket = fullRef.indexOf("(");
        int rightBracket = fullRef.indexOf(")");

        String parameterString = fullRef.substring(leftBracket+1, rightBracket);
        String[] paramArr = parameterString.split(",");
        String params = "(";

        for(String p: paramArr){
            if(!params.equals("(")) params+=",";
            String[] p2 =  p.trim().split("\\s");
            params += p2[0];
        }
        params +=")";
        return params;
    }

    private String generateReturns(){
        try {
            return fullRef.substring(0, fullRef.indexOf(" ")).trim();
        }
        catch(IndexOutOfBoundsException e){
            return "";
        }
    }



    public String getFullRef() {
        return fullRef;
    }

    public void setFullRef(String fullRef) {
        this.fullRef = fullRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParameterList() {
        return parameterList;
    }

    public void setParameterList(String parameterList) {
        this.parameterList = parameterList;
    }

    public String getReturns() {
        return returns;
    }

    public void setReturns(String returns) {
        this.returns = returns;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
