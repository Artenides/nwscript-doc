package com.nwnarelith.nssdoc;

import com.nwnarelith.nssdoc.documentcreator.NssDocumentCreator;

public class Main {

    public static void main(String[] args){

        if(args.length != 3){
            System.out.println("Missing argument!");
            System.out.println("Format: nwscript-doc [nss_directory_path] [doc_output_path] [format]");
            System.out.println("Supported formats: TSV");
        }
        else{
            NssDocumentCreator documentCreator = new NssDocumentCreator();
            boolean stop = false;
            String nssDir = args[0];
            String outputDir = args[1];
            String format = args[2];

            if(!documentCreator.existingDir(nssDir)) {
                System.out.println(nssDir +" does not exist.");
                stop = true;
            }
            if(!documentCreator.existingDir(outputDir)){
                System.out.println(outputDir +" does not exist.");
                stop = true;
            }

            if(!format.equalsIgnoreCase("TSV")){
                System.out.println("Format "+ format +" is not supported.");
                stop = true;
            }

            if(!stop){
                documentCreator.create(nssDir);
                documentCreator.createTsv(outputDir);
                System.out.println("done");
            }
        }


    }
}
