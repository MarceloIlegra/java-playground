package br.com.csvpoc;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ModelWriterCSV modelWriterCSV = new ModelWriterCSV("arquivofinal");
        modelWriterCSV.generateFile(generateModelsFake());
    }

    private static List<Model> generateModelsFake() {
        return Arrays.asList(new Model("email@email.com", "email"), new Model("aaaa@aaaa.com", "ddsdsds"), new Model("444@email.com", "afafdsfds"));
    }

}

