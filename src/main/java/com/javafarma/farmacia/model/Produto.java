package com.javafarma.farmacia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O Atributo SKU é obrigatorio!")
	@Size(min = 1, max = 100, message = "O Atributo SKU deve conter no mínimo 1 e no maximo 150 caracteres! ")
	@Column(length = 100)
	private String sku;

	@NotBlank(message = "O Atributo nomeProduto é obrigatorio!")
	@Size(min = 1, max = 150, message = "O Atributo nomeProduto deve conter no mínimo 1 e no maximo 150 caracteres! ")
	@Column(length = 150)
	private String nomeProduto;

	@NotNull(message = "O atributo descrição é obrigatorio")
	private String descricao;

	@NotNull(message = "O Atributo Preço deve ser preenchido!")
	private Double preco;

	// Relacionamento com Tabela Categorias
	@ManyToOne
	@JsonIgnoreProperties("produto")
	private Categoria categoria;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
