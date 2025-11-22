package br.projetos.gerenciadorFinanceiro.controller.modelAssembler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;

import br.projetos.gerenciadorFinanceiro.controller.RequestContext;
import br.projetos.gerenciadorFinanceiro.dto.CartaoDTO;
import br.projetos.gerenciadorFinanceiro.dto.CategoriaDTO;
import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;
import br.projetos.gerenciadorFinanceiro.dto.mapper.CartaoMapper;
import br.projetos.gerenciadorFinanceiro.dto.mapper.CategoriaMapper;
import br.projetos.gerenciadorFinanceiro.enums.Status;
import br.projetos.gerenciadorFinanceiro.model.Cartao;
import br.projetos.gerenciadorFinanceiro.model.Categoria;
import br.projetos.gerenciadorFinanceiro.model.Despesa;

class LancamentoModelAssemblerTest {

	@Autowired
	LancamentoModelAssembler assembler;
	
    private Categoria categoria;
    private Cartao cartao;
    
    private CategoriaDTO categoriaDTO;
    private CartaoDTO cartaoDTO;
    private LancamentoDTO lancDTO;
    
    private LancamentoModel lancamentoModel;
	
	@BeforeEach
	void setUp() throws Exception {
		assembler = new LancamentoModelAssembler();
		
		categoria = new Despesa(1L, "Alimentacao", Status.ATIVO, Double.valueOf(100));
		cartao = new Cartao(1L, "Visa", Status.ATIVO);
		
		categoriaDTO = CategoriaMapper.toDTO(categoria);		
		cartaoDTO = CartaoMapper.toDTO(cartao);
		
		lancDTO = new LancamentoDTO(1L,
									"12/09/2025",
									"Sushi",
									130,
									categoriaDTO,
									cartaoDTO);
		
	}

	@Test
	void testLinksOnIncluirLancamento() {
		lancamentoModel = new LancamentoModel(lancDTO, RequestContext.CREATE);
		
		EntityModel<LancamentoModel> model = assembler.toModel(lancamentoModel);
		
		assertNotNull(model);
		assertNotNull(model.getContent().getLancamento());
		
		assertThat(model.getLink("self")).isPresent();
		assertThat(model.getLink("lista-lancamentos").isPresent());
		assertThat(model.getLink("inclui-lancamentos").isPresent());
		assertThat(model.getLink("altera-lancamentos").isPresent());
		assertThat(model.getLink("exclui-lancamentos").isPresent());
	}

}
