package br.projetos.gerenciadorFinanceiro.service.files.exporters;

import java.util.List;

import org.springframework.core.io.Resource;

import br.projetos.gerenciadorFinanceiro.service.files.FileType;

public interface FileExporter<T> {
	
    FileType supportsFileType();     // CSV, XLSX
    Class<T> supportsDomain();       // PersonDTO, ExpenseDTO

    Resource exportFile(List<T> list) throws Exception;
}
