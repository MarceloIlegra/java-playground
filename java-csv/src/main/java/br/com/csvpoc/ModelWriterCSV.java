
package br.com.csvpoc;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class ModelWriterCSV {

    private final String NEW_LINE_SEPARATOR = "\n";
    private final String EXTENSION_CSV = "csv";
    private final String COLUMN_SEPARATOR = "sep=,";
    private final Object[] FILE_HEADER = {"email", "partner"};
    private final CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
    private String filename;

    public ModelWriterCSV(String filename){
        this.filename = filename;
    }

    public void generateFile(List<Model> models){
        try(FileWriter fileWriter = new FileWriter(String.format("%s.%s", filename,EXTENSION_CSV)); CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat)) {
            csvFilePrinter.printRecord(COLUMN_SEPARATOR);
            csvFilePrinter.printRecord(FILE_HEADER);
            models.stream().forEach( m -> {
                try {
                    csvFilePrinter.printRecord(Arrays.asList(m.getEmail(), m.getPartner()));
                } catch (IOException ex) {
                    throw new RuntimeException("Cannot write in the file");
                }
            });
            fileWriter.flush();
        } catch (IOException ex) {
            throw new RuntimeException("Cannot create the file");
        }
    }
}
