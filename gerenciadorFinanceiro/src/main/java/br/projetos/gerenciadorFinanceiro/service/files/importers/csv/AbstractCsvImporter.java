package br.projetos.gerenciadorFinanceiro.service.files.importers.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import br.projetos.gerenciadorFinanceiro.service.files.importers.FileImporter;

public abstract class AbstractCsvImporter<T> implements FileImporter<T> {

    protected Iterable<CSVRecord> parse(InputStream inputStream) throws IOException {
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .build();

        return format.parse(new InputStreamReader(inputStream));
    }
}

