package br.com.asantos.teashop.model.cliente;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.asantos.teashop.dto.cliente.ClienteDto;
import br.com.asantos.teashop.model.Cupom;
import br.com.asantos.teashop.model.Pagamento;
import br.com.asantos.teashop.model.cliente.cartao.Cartao;
import br.com.asantos.teashop.model.cliente.endereco.Endereco;
import br.com.asantos.teashop.model.pedido.Pedido;

/**
 * Esta classe representa o modelo referente aos clientes cadastrados no
 * comércio eletrônico de chás
 * 
 * @author Aline Santos
 *
 */
@Entity
@Table(name = "clientes")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cli_id")
	private Long id;

	@Column(name = "cli_nome")
	@NotNull
	@NotBlank
	private String nome;

	@Column(name = "cli_genero")
	@NotNull
	@NotBlank
	private String genero;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@Column(name = "cli_dt_nascimento")
	@NotNull
	private Date dtNascimento;

	@Column(name = "cli_cpf")
	@CPF
	@NotNull
	@NotBlank
	private String cpf;

	@Column(name = "cli_telefone")
	@NotNull
	@NotBlank
	private String telefone;

	@Column(name = "cli_email")
	@Email
	@NotNull
	@NotBlank
	private String email;

	@Column(name = "cli_senha")
	@NotNull
	@NotBlank
	private String senha;

	// !TODO verificar se melhor usar um ENUM
	// statusAtivo do cliente ao se cadastrar será "true"
	@Column(name = "cli_status_ativo")
	private boolean statusAtivo;

	// um cliente para muitos: endereços, cartões e pedidos (conjuntos de elementos
	// distintos)
	// mais de um endereço
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Endereco> endereco;

	// mais de um pedido
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pedido> pedido;

	// mais de um cartao
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Cartao> cartao;
	
	//muitos cupons
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Cupom> cupom;
	
	
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pagamento> pagamento;
	

	public Cliente() {
	}

	public Cliente(@NotNull @NotBlank String nome, @NotNull @NotBlank String genero, @NotNull Date dtNascimento,
			@CPF @NotNull @NotBlank String cpf, @NotNull @NotBlank String telefone,
			@Email @NotNull @NotBlank String email, @NotNull @NotBlank String senha, boolean statusAtivo) {
		this.nome = nome;
		this.genero = genero;
		this.dtNascimento = dtNascimento;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
		this.statusAtivo = statusAtivo;

	}

	// constroi um cliente a partir dos dados do ClienteDto
	public Cliente(ClienteDto clienteDto) {
		this.nome = clienteDto.getNome();
		this.genero = clienteDto.getGenero();
		this.dtNascimento = clienteDto.getDtNascimento();
		this.cpf = clienteDto.getCpf();
		this.telefone = clienteDto.getTelefone();
		this.email = clienteDto.getEmail();
		this.senha = clienteDto.getEmail();
		this.statusAtivo = clienteDto.getStatusAtivo();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Endereco> getEndereco() {
		return endereco;
	}

	public void setEndereco(Set<Endereco> endereco) {
		this.endereco = endereco;
	}

	public Set<Pedido> getPedido() {
		return pedido;
	}

	public void setPedido(Set<Pedido> pedido) {
		this.pedido = pedido;
	}

	public Set<Cartao> getCartao() {
		return cartao;
	}

	public void setCartao(Set<Cartao> cartao) {
		this.cartao = cartao;
	}

	public boolean getStatusAtivo() {
		return statusAtivo;
	}

	public void setStatusAtivo(boolean statusAtivo) {
		this.statusAtivo = statusAtivo;
	}

}
