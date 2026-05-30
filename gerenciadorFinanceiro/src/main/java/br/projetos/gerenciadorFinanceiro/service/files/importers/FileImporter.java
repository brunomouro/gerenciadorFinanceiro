package br.projetos.gerenciadorFinanceiro.service.files.importers;

import java.io.InputStream;
import java.util.List;

import br.projetos.gerenciadorFinanceiro.service.files.FileType;

public interface FileImporter<T> {

    FileType supportsFileType();     // CSV, XLSX
    Class<T> supportsDomain();       // PersonDTO, ExpenseDTO

    List<T> importFile(InputStream inputStream) throws Exception;
}

