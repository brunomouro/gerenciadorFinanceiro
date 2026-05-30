package br.projetos.gerenciadorFinanceiro;

import br.projetos.gerenciadorFinanceiro.dto.CartaoDTO;
import br.projetos.gerenciadorFinanceiro.dto.CategoriaDTO;
import br.projetos.gerenciadorFinanceiro.dto.DespesaDTO;
import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;
import br.projetos.gerenciadorFinanceiro.dto.ReceitaDTO;
import br.projetos.gerenciadorFinanceiro.enums.Status;
import br.projetos.gerenciadorFinanceiro.model.Cartao;
import br.projetos.gerenciadorFinanceiro.model.Categoria;
import br.projetos.gerenciadorFinanceiro.model.Despesa;
import br.projetos.gerenciadorFinanceiro.model.Lancamento;
import br.projetos.gerenciadorFinanceiro.model.Receita;

/**
 * Test Data Builder para criar objetos de teste de forma fluente e reutilizável.
 * Facilita a criação de dados de teste com valores padrão e permite customizações.
 */
public class TestDataBuilder {

    /**
     * Builder para LancamentoDTO
     */
    public static class LancamentoDTOBuilder {
        private Long id;
        private String data = "15/01/2025";
        private double valor = 150.50;
        private String descricao = "Compra no supermercado";
        private CategoriaDTO categoria = new CategoriaDTOBuilder().buildDespesaDTO();
        private CartaoDTO cartao = new CartaoDTOBuilder().build();

        public LancamentoDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public LancamentoDTOBuilder withData(String data) {
            this.data = data;
            return this;
        }

        public LancamentoDTOBuilder withValor(double valor) {
            this.valor = valor;
            return this;
        }

        public LancamentoDTOBuilder withDescricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public LancamentoDTOBuilder withCategoria(CategoriaDTO categoria) {
            this.categoria = categoria;
            return this;
        }

        public LancamentoDTOBuilder withCartao(CartaoDTO cartao) {
            this.cartao = cartao;
            return this;
        }

        public LancamentoDTOBuilder withoutCategoria() {
            this.categoria = null;
            return this;
        }

        public LancamentoDTOBuilder withoutCartao() {
            this.cartao = null;
            return this;
        }

        public LancamentoDTO build() {
            return new LancamentoDTO(id, data, descricao, valor, categoria, cartao);
        }
    }

    /**
     * Builder para CategoriDTO
     */
    public static class CategoriaDTOBuilder {
        private Long id = 1L;
        private String nome = "Alimentação";

        public CategoriaDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public CategoriaDTOBuilder withNome(String nome) {
            this.nome = nome;
            return this;
        }

        public CategoriaDTO buildDespesaDTO() {
            return new DespesaDTO(id, nome);
        }
        
        public CategoriaDTO buildReceitaDTO() {
            return new ReceitaDTO(id, nome);
        }
    }

    /**
     * Builder para CartaoDTO
     */
    public static class CartaoDTOBuilder {
        private Long id = 1L;
        private String nome = "Nubank";


        public CartaoDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public CartaoDTOBuilder withNome(String nome) {
            this.nome = nome;
            return this;
        }

        public CartaoDTOBuilder withoutId() {
            this.id = null;
            return this;
        }

        public CartaoDTO build() {
            return new CartaoDTO(id, nome);
        }
    }

    /**
     * Builder para Lancamento Entity
     */
    public static class LancamentoBuilder {
        private Long id;
        private String data = "15/01/2025";
        private double valor = 150.50;
        private String descricao = "Compra no supermercado";
        private Categoria categoria = new CategoriaBuilder().buildDespesa();
        private Cartao cartao = new CartaoBuilder().build();

        public LancamentoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public LancamentoBuilder withData(String data) {
            this.data = data;
            return this;
        }

        public LancamentoBuilder withValor(double valor) {
            this.valor = valor;
            return this;
        }

        public LancamentoBuilder withDescricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public LancamentoBuilder withCategoria(Categoria categoria) {
            this.categoria = categoria;
            return this;
        }

        public LancamentoBuilder withCartao(Cartao cartao) {
            this.cartao = cartao;
            return this;
        }

        public LancamentoBuilder withoutCategoria() {
            this.categoria = null;
            return this;
        }

        public LancamentoBuilder withoutCartao() {
            this.cartao = null;
            return this;
        }

        public Lancamento build() {
            return new Lancamento(id, data, valor, descricao, categoria, cartao);
        }
    }

    /**
     * Builder para Categoria Entity
     */
    public static class CategoriaBuilder {
        private Long id = 1L;
        private String nome = "Alimentação";
        private double meta = 100.0;

        public CategoriaBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public CategoriaBuilder withNome(String nome) {
            this.nome = nome;
            return this;
        }

        public CategoriaBuilder withMeta(double meta) {
            this.meta = meta;
            return this;
        }

        public Categoria buildDespesa() {
            return new Despesa(id, nome, Status.ATIVO, meta);
        }
        
        public Categoria buildReceita() {
            return new Receita();
        }
    }

    /**
     * Builder para Cartao Entity
     */
    public static class CartaoBuilder {
        private Long id = 1L;
        private String nome = "Nubank";


        public CartaoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public CartaoBuilder withNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Cartao build() {
            return new Cartao(id, nome, Status.ATIVO);
        }
    }

    // Métodos estáticos para facilitar o uso

    public static LancamentoDTOBuilder umLancamentoDTO() {
        return new LancamentoDTOBuilder();
    }

    public static CategoriaDTOBuilder umaCategoriaDTO() {
        return new CategoriaDTOBuilder();
    }

    public static CartaoDTOBuilder umCartaoDTO() {
        return new CartaoDTOBuilder();
    }

    public static LancamentoBuilder umLancamento() {
        return new LancamentoBuilder();
    }

    public static CategoriaBuilder umaCategoria() {
        return new CategoriaBuilder();
    }

    public static CartaoBuilder umCartao() {
        return new CartaoBuilder();
    }
}
