package com.nwnarelith.documentcreator;

import com.nwnarelith.datamodel.NssFile;
import com.nwnarelith.datamodel.NssFunction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NssDocumentCreator {

    private final String SEPARATOR = "\t";
    private final String END_OF_LINE = "\n";

    private List<NssFile> nssFiles = new ArrayList<>();
    private Set<String> functionSet = new HashSet<>();

    public void create(String path){
        File folder = new File(path);

        if (folder.exists()) {
            File[] nssFiles = folder.listFiles();
            int to = nssFiles.length;
            System.out.println("Processing nss files:");
            for(File f : nssFiles){

                if(f.isFile()){
                    processNssFile(f);
                }
            }
        }
    }

    public void createMarkDown(){


    }

    public void createTsv(String filePath){

        StringBuilder sb = new StringBuilder();
        sb.append("File Name").append(SEPARATOR);
        sb.append("Full Ref").append(SEPARATOR);
        sb.append("Function Name").append(SEPARATOR);
        sb.append("Parameter List").append(SEPARATOR);
        sb.append("Returns").append(SEPARATOR);
        sb.append("Description").append(END_OF_LINE);

        for(NssFile file: nssFiles){
            for(NssFunction func :file.getFunctions()){
                sb.append(file.getName()).append(SEPARATOR);
                sb.append(func.getFullRef()).append(SEPARATOR);
                sb.append(func.getName()).append(SEPARATOR);
                sb.append(func.getParameterList()).append(SEPARATOR);
                sb.append(func.getReturns()).append(SEPARATOR);
                sb.append(func.getDescription()).append(END_OF_LINE);
            }
        }

        saveFile(filePath, sb.toString());

    }

    private void processNssFile(File file){
        List<String> textCache = new ArrayList<>();

        String fileName = file.getName();
        NssFile nssFile = new NssFile(fileName);
        System.out.println(fileName);
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int counter = 0;
            while ((line = br.readLine()) != null) {
                // process the line.
                Pattern pattern = Pattern.compile("^\\s*(int|string|void|object|float|(?:struct\\s+[a-zA-Z_][a-zA-Z0-9_]*))\\s+([a-zA-Z_][a-zA-Z0-9_]*)\\((.+?)\\);\\s*$");
                Matcher matcher = pattern.matcher(line);
                textCache.add(line);

                if (matcher.find()) {
                    addFunction(nssFile, line, textCache);
                    textCache = new ArrayList<>();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(nssFile.getFunctions().size()>0)
        nssFiles.add(nssFile);
    }

    private void addFunction(NssFile nssFile, String functionRef, List<String> textCache){
        functionSet.add(functionRef);
        NssFunction nssFunction = new NssFunction(functionRef);
        nssFunction.parse(textCache);
        nssFile.getFunctions().add(nssFunction);
    }

    private void saveFile(String filePath, String content){
        Path path = Paths.get(filePath+"out.tsv");
        byte[] strToBytes = content.getBytes();

        try {
            Files.write(path, strToBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean existingDir(String path){
        File folder = new File(path);
        return folder.exists();
    }

}
