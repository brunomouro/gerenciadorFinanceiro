package br.projetos.gerenciadorFinanceiro.service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;
import br.projetos.gerenciadorFinanceiro.dto.mapper.CartaoMapper;
import br.projetos.gerenciadorFinanceiro.dto.mapper.CategoriaMapper;
import br.projetos.gerenciadorFinanceiro.dto.mapper.LancamentoMapper;
import br.projetos.gerenciadorFinanceiro.exception.FileStorageException;
import br.projetos.gerenciadorFinanceiro.exception.RecordNotFoundExcepttion;
import br.projetos.gerenciadorFinanceiro.model.Cartao;
import br.projetos.gerenciadorFinanceiro.model.Categoria;
import br.projetos.gerenciadorFinanceiro.model.Lancamento;
import br.projetos.gerenciadorFinanceiro.repository.CartaoRepository;
import br.projetos.gerenciadorFinanceiro.repository.CategoriaRepository;
import br.projetos.gerenciadorFinanceiro.repository.LancamentoRepository;
import br.projetos.gerenciadorFinanceiro.service.files.FileType;
import br.projetos.gerenciadorFinanceiro.service.files.exporters.FileExporter;
import br.projetos.gerenciadorFinanceiro.service.files.exporters.FileExporterFactory;
import br.projetos.gerenciadorFinanceiro.service.files.importers.FileImporter;
import br.projetos.gerenciadorFinanceiro.service.files.importers.FileImporterFactory;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class LancamentoService {
	
	private final LancamentoRepository lancamentoRepository;
	private final CategoriaRepository categoriaRepository;
	private final CartaoRepository cartaoRepository;
	private final FileImporterFactory fileImporter;
	private final FileExporterFactory fileExporter;
	
	public LancamentoService(LancamentoRepository lancamentoRepository, 
							 CategoriaRepository categoriaRepository, 
							 CartaoRepository cartaoRepository, 
							 FileImporterFactory fileImporter,
							 FileExporterFactory fileExporter) {
		this.lancamentoRepository = lancamentoRepository;
		this.categoriaRepository = categoriaRepository;
		this.cartaoRepository = cartaoRepository;
		this.fileImporter = fileImporter;
		this.fileExporter = fileExporter;
	}

	@CacheEvict(value = "lancamentos", allEntries = true)
	public LancamentoDTO incluirLancamento(LancamentoDTO lancamento) {
		Categoria categoria = null;
		Cartao cartao = null;
		
		if ( lancamento.categoria() != null) {
			categoria = categoriaRepository.findById(lancamento.categoria().getId())
										   .orElseThrow(()-> new RecordNotFoundExcepttion("Categoria", lancamento.categoria().getId()));
		}
		
		if (lancamento.cartao().id() != null ) {
			cartao = cartaoRepository.findById(lancamento.cartao().id())
									 .orElseThrow(()-> new RecordNotFoundExcepttion("Cartão", lancamento.cartao().id()));
		}
		
		Lancamento lanc = LancamentoMapper.toEntity(lancamento);
		lanc.setCategoria(categoria);
		lanc.setCartao(cartao);		
		
		return LancamentoMapper.toDTO(lancamentoRepository.save(lanc));
	}
	
	@Cacheable("lancamentos")
	public Page<LancamentoDTO> listaLancamentos(Pageable pageable) {
		Page<LancamentoDTO> lancamentos = lancamentoRepository.findAll(pageable)
														      .map(LancamentoMapper::toDTO);

		if( lancamentos.isEmpty() ) {
			throw new RecordNotFoundExcepttion();
		}
		return lancamentos;
	}

	public LancamentoDTO consultaLancamento(Long id) {
		return lancamentoRepository.findById(id)
				.map(LancamentoMapper::toDTO)
				.orElseThrow(() -> new RecordNotFoundExcepttion(id));
		
	}

	public LancamentoDTO alteraLancamento(Long id, LancamentoDTO lancamento ) {
		return lancamentoRepository.findById(id)
				.map( recordFound -> {
					recordFound.setCartao( CartaoMapper.toEntity(lancamento.cartao()) );
					recordFound.setCategoria( CategoriaMapper.toEntity(lancamento.categoria()) );
					recordFound.setData( lancamento.data() );
					recordFound.setDescricao( lancamento.descricao() );
					recordFound.setValor( lancamento.valor() );
					return LancamentoMapper.toDTO(lancamentoRepository.save( recordFound ));
				}).orElseThrow(() -> new RecordNotFoundExcepttion(id) );
	}

	public void excluiLancamento(Long id) {
		lancamentoRepository.delete( lancamentoRepository.findById( id )
				.orElseThrow( () -> new RecordNotFoundExcepttion(id)) );
	}

	public List<Lancamento> listaLancamentosPorData(String dataInicial, String dataFinal) {
		return lancamentoRepository.findBydataBetween(dataInicial, dataFinal);
	}
	
    public List<LancamentoDTO> importaLancamentos(MultipartFile file) {
    	if (file.isEmpty())throw new FileStorageException("Please set a Valid File!"); 
    
        try(InputStream inputStream = file.getInputStream()){
            String filename = Optional.ofNullable(file.getOriginalFilename())
            						  .orElseThrow(() -> new BadRequestException("File name cannot be null"));
            
            FileType type = FileType.fromFileName(filename);
            
            FileImporter<LancamentoDTO> importer = this.fileImporter.getImporter(LancamentoDTO.class, type);

            List<Lancamento> entities = importer.importFile(inputStream)
            									.stream()
            									.map(LancamentoMapper::toEntity)
            									.map(lancamentoRepository::save)
            									.toList();

            return entities.stream()
            			   .map(LancamentoMapper::toDTO)
            			   .toList();
            
        } catch (Exception e) {
            throw new FileStorageException("Error processing the file!");
        }
    }
    
    public Resource exportaLancamentos(Pageable pageable, FileType type) {
    	List<LancamentoDTO> lancamentos = lancamentoRepository.findAll(pageable)
    													      .map(LancamentoMapper::toDTO)
    													      .getContent();
    						
    	if( lancamentos.isEmpty() ) {
    		throw new RecordNotFoundExcepttion();
    	}
        
        FileExporter<LancamentoDTO> exporter;
        
		try {
			exporter = this.fileExporter.getExporter(LancamentoDTO.class, type);
			return exporter.exportFile(lancamentos);
		} catch (Exception e) {
			throw new RuntimeException("Error during file export!", e);
		}
    }
}
