# nwscript-doc

A library that can create documentation for nss functions defined in NWScript files.  NWScript is a C like scripting language used by the Neverwinter Nights engine.


Console command:
nwscript-doc [nss_directory_path] [doc_output_path] [format]

Example:

java -jar nwscript-doc.jar e:\Arelith-Development\are-resources\scripts\ e:\Arelith\docs\ TSV

This will read all .nss files from e:\Arelith-Development\are-resources\scripts\ and will create a TSV file in e:\Arelith\docs\output.tsv
