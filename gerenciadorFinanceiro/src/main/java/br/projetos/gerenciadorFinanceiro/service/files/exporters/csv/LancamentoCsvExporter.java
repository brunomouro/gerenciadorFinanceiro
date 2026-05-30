package br.projetos.gerenciadorFinanceiro.service.files.exporters.csv;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;
import br.projetos.gerenciadorFinanceiro.service.files.FileType;
import br.projetos.gerenciadorFinanceiro.service.files.exporters.FileExporter;

@Component
public class LancamentoCsvExporter implements FileExporter<LancamentoDTO> {
	
	@Override
	public FileType supportsFileType() {
		return FileType.CSV;
	}
	
	@Override
	public Class<LancamentoDTO> supportsDomain() {
		return LancamentoDTO.class;
	}
	
    @Override
    public Resource exportFile(List<LancamentoDTO> lancamentos) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setHeader("Id", 
                		   "data", 
                		   "descricao", 
                		   "valor", 
                		   "id_categoria", 
                		   "nome_categoria", 
                		   "id_cartao", 
                		   "nome_cartao")
                .setSkipHeaderRecord(false)
                .get();

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat)){
            for(LancamentoDTO lancamento : lancamentos) {
                csvPrinter.printRecord(
                		lancamento.id(),
                		lancamento.data(),
                		lancamento.descricao(),
                		lancamento.valor(),
                		lancamento.categoria().getId(),
                		lancamento.categoria().getNome(),
                		lancamento.cartao().id(),
                		lancamento.cartao().nome()
                );
            }
        }
        
        return new ByteArrayResource(outputStream.toByteArray());
    }
}