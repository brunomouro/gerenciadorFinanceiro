package br.projetos.gerenciadorFinanceiro.service.files.importers.csv;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import br.projetos.gerenciadorFinanceiro.dto.CartaoDTO;
import br.projetos.gerenciadorFinanceiro.dto.CategoriaDTO;
import br.projetos.gerenciadorFinanceiro.dto.DespesaDTO;
import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;
import br.projetos.gerenciadorFinanceiro.dto.ReceitaDTO;
import br.projetos.gerenciadorFinanceiro.service.files.FileType;

@Component
public class LancamentoCsvImporter extends AbstractCsvImporter<LancamentoDTO> {
	
    @Override
    public FileType supportsFileType() {
        return FileType.CSV;
    }

    @Override
    public Class<LancamentoDTO> supportsDomain() {
        return LancamentoDTO.class;
    }

    @Override
    public List<LancamentoDTO> importFile(InputStream inputStream) throws Exception {
        Iterable<CSVRecord> records = parse(inputStream);
        return toLancamentos(records);
    }

    private List<LancamentoDTO> toLancamentos(Iterable<CSVRecord> records) {
        List<LancamentoDTO> lancamentos = new ArrayList<>();

        for (CSVRecord record : records) {
        	CategoriaDTO categoria;
        	if(record.get("tipo").equals( "D" ) ) {
        		categoria = new DespesaDTO(Long.parseLong(record.get("id_categoria")), 
        								   record.get("nome_categoria"));
        	}
        	else {
        		categoria = new ReceitaDTO(Long.parseLong(record.get("id_categoria")), 
        								   record.get("nome_categoria"));
        	}
        	
        	LancamentoDTO lancamento = new LancamentoDTO(Long.parseLong(record.get("id")),
        												 record.get("data"),
        												 record.get("descricao"),
        												 Double.parseDouble(record.get("valor")),
        												 categoria,
					 									 new CartaoDTO(Long.parseLong(record.get("id_cartao")),
					 									 			   record.get("nome_cartao")));

            lancamentos.add(lancamento);
        }
        return lancamentos;
    }
}

