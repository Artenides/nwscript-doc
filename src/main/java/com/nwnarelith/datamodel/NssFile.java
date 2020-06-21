package com.nwnarelith.datamodel;

import java.util.ArrayList;
import java.util.List;

public class NssFile {

    private String name;
    private List<NssFunction> functions = new ArrayList<>();

    public NssFile(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NssFunction> getFunctions() {
        return functions;
    }

    public void setFunctions(List<NssFunction> functions) {
        this.functions = functions;
    }
}
