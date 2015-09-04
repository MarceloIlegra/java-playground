package br.com.csvpoc;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class Main {

    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final Object[] FILE_HEADER = {"email", "partner"};

    public static void main(String[] args) {
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);


        List<Model> models = Arrays.asList(new Model("email@email.com", "email"), new Model("aaaa@aaaa.com", "ddsdsds"), new Model("444@email.com", "afafdsfds"));

        try {
            fileWriter = new FileWriter("arquivofinal.csv");
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
            csvFilePrinter.printRecord("sep=,");
            csvFilePrinter.printRecord(FILE_HEADER);

            for(Model model : models){
                csvFilePrinter.printRecord(Arrays.asList(model.getEmail(), model.getPartner()));
            }

            fileWriter.flush();
            fileWriter.close();
            csvFilePrinter.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

