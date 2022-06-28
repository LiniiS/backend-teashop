package br.com.asantos.teashop.service.cliente;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.asantos.teashop.dto.cliente.ClienteDto;
import br.com.asantos.teashop.dto.cliente.ClienteSenhaDto;
import br.com.asantos.teashop.dto.cliente.ResponseDto;
import br.com.asantos.teashop.dto.cliente.SignInDto;
import br.com.asantos.teashop.dto.cliente.SignInResponseDto;
import br.com.asantos.teashop.dto.cliente.SignUpDto;
import br.com.asantos.teashop.exceptions.AuthenticationFailException;
import br.com.asantos.teashop.exceptions.ClienteNotExistException;
import br.com.asantos.teashop.exceptions.CustomException;
import br.com.asantos.teashop.model.TokenAutenticacao;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.repository.cliente.ClienteRepository;
import br.com.asantos.teashop.utils.Helper;

/**
 * Classe responsável por orquestrar os serviços solicitados via Controller para
 * o cliente a ser cadastrado e já cadastrados
 * 
 * @author Aline Santos
 *
 */
@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	AutenticacaoService autenticacaoService;

	/**
	 * Método que cadastra um novo cliente, verifica por meio do email se é um
	 * cliente novo ou não retornando uma exception, caso seja um novo cliente a
	 * senha inserida será criptografada antes de armazená-la no bd. Requer o escopo
	 * de uma transação
	 * 
	 * @param signUpDto
	 * @return
	 */
	@Transactional
	public ResponseDto signUp(SignUpDto signUpDto) {
		// verifica se já existe ou não avaliando o email passado
		if (Helper.notNull(clienteRepository.findByEmail(signUpDto.getEmail()))) {
			throw new CustomException("Cliente já possui cadastro.");
		}
		// verifica se as senhas digitadas são iguais
		String senha = signUpDto.getSenha();
		String senhaVerificada = signUpDto.getSenhaVerificada();
		if (!senha.contentEquals(senhaVerificada)) {
			throw new CustomException("Senhas não conferem.");
		}
		// modo basico de encriptar o password via Java Security apenas para fins
		// educativos, não indicado pra produção
		String senhaEncriptada = signUpDto.getSenha();
		try {
			senhaEncriptada = hashSenha(signUpDto.getSenha());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// salva o cliente já com a senha hasheada
		Cliente cliente = new Cliente(signUpDto.getNome(), signUpDto.getGenero(), signUpDto.getDtNascimento(),
				signUpDto.getCpf(), signUpDto.getTelefone(), signUpDto.getEmail(), senhaEncriptada,
				signUpDto.getStatusAtivo());
		clienteRepository.save(cliente);

		// cria um token e atribui a este cliente, passa-se o cliente e a ele é
		// atribuido um token para esta sessão
		final TokenAutenticacao tokenAutenticacao = new TokenAutenticacao(cliente);
		autenticacaoService.salvaTokenConfirmacao(tokenAutenticacao);

		// retorna resposta da requisição
		ResponseDto responseDto = new ResponseDto("success", "Novo cliente cadastrado");
		return responseDto;

	}

	/**
	 * Método para encriptar a senha do cliente para ser armazenada no bd
	 * 
	 * @param senha
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private String hashSenha(String senha) throws NoSuchAlgorithmException {
		// defineo modo de transformação/digestão do texto passado
		MessageDigest md = MessageDigest.getInstance("MD5");
		// codifica a string pra uma sequencia de bytes
		md.update(senha.getBytes());
		// completa o hash
		byte[] digest = md.digest();
		// passa para a variavel a senha hash convertida e em caixa alta
		String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return hash;

	}

	/**
	 * Método para efetuar login de cliente cadastrado mediante email e senha um
	 * token para a sessão é gerado o token acompanha o cliente logado
	 * 
	 * @param signInDto
	 * @return
	 */
	public SignInResponseDto signIn(SignInDto signInDto) {
		// localiza o cliente pelo email cadastrado
		Cliente cliente = clienteRepository.findByEmail(signInDto.getEmail());
		System.out.println("senha dentro do SignInDto: " + cliente.getSenha());
		if (!Helper.notNull(cliente)) {
			throw new AuthenticationFailException("Cliente inválido. Verique os dados inseridos");
		}
		// a senha inserida precisa ser hasheada e comparada com a presente no banco
		// caso a senha inserida seja incorreta:
		try {
			if (!cliente.getSenha().equals(hashSenha(signInDto.getSenha()))) {
				System.out.println("Senha Cliente dentro do try: " + cliente.getSenha());
				System.out.println("Senha digitada hasheada: " + hashSenha(signInDto.getSenha()));

				System.out.println("Senha digitada : " + signInDto.getSenha());
				throw new AuthenticationFailException("Dados incorretos. Verique e tente novamente.");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// caso a senha inserida esteja correta
		TokenAutenticacao token = autenticacaoService.getToken(cliente);
		// verifica a presença do token
		if (!Helper.notNull(token)) {
			throw new CustomException("Token inexistente.");
		}
		// retorna uma resposta de sucesso com o token associado ao cliente
		return new SignInResponseDto("success", token.getToken());
	}

	/**
	 * Método para atualizar os dados cadastrais do cliente usando formulario por
	 * meio do DTO
	 * 
	 * @param clienteId
	 * @param clienteDto
	 * @throws NoSuchAlgorithmException
	 */
	@Transactional
	public void atualizaCliente(Long clienteId, @Valid ClienteDto clienteDto) {
		// extrai os dados do DTO para um Cliente
		Cliente cliente = getClienteDoDto(clienteDto);
		String senhaEncriptada = "";
		try {

			senhaEncriptada = hashSenha(clienteDto.getSenha());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// atribui ao novo Clienteo id do cliente oriundo do DTO
		cliente.setId(clienteId);
		cliente.setSenha(senhaEncriptada);

		clienteRepository.save(cliente);

	}

	// veriifcar essa Exception
	@Transactional
	public void atualizaSenhaCliente(Cliente cliente, ClienteSenhaDto clienteSenhaDto) {

		String novaSenha = clienteSenhaDto.getNovaSenha();
		try {
			cliente.setSenha(hashSenha(novaSenha));
			clienteRepository.save(cliente);
			System.out.println("senha dentro do atualizarSenhaCliente: " + novaSenha);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método para extrair os dados atualizados do Cliente extrai os dados
	 * atualizados (ou não) do DTO e constroi um cliente convertendo um ClienteDto
	 * em Cliente
	 * 
	 * @param clienteDto
	 * @return
	 */
	public static Cliente getClienteDoDto(ClienteDto clienteDto) {
		Cliente cliente = new Cliente(clienteDto);
		return cliente;
	}

	/**
	 * Método para extrair para um DTO os dados de um cliente convertendo um Cliente
	 * em ClienteDto
	 * 
	 * @param cliente
	 * @return
	 */
	public static ClienteDto getDtoDoCliente(Cliente cliente) {
		ClienteDto clienteDto = new ClienteDto(cliente);
		return clienteDto;
	}

	/**
	 * Método que remove um cliente associado a um id, lança uma exception caso o id
	 * seja inválido
	 * 
	 * @param clienteId
	 * @throws ClienteNotExistException
	 */
	// !TODO considerar verificação do id e do clienteId desse médoto + token
	@Transactional
	public void deletaClientePorId(Long clienteId) throws ClienteNotExistException {
		Optional<Cliente> optionalCliente = clienteRepository.findById(clienteId);
		if (!optionalCliente.isPresent())
			throw new ClienteNotExistException("Cliente com id: " + clienteId + "não existe.");
		clienteRepository.deleteById(clienteId);
	}

	/**
	 * Método que busca um cliente dado um id. Caso o id passado seja inválido uma
	 * exception será lançada.
	 * 
	 * @param clienteId
	 * @return
	 * @throws ClienteNotExistException
	 */
	public Optional<Cliente> buscaCliente(Long clienteId) throws ClienteNotExistException {
		Optional<Cliente> optionalCliente = clienteRepository.findById(clienteId);
		if (!optionalCliente.isPresent())
			throw new ClienteNotExistException("Cliente com ID: " + clienteId + " não encontrado ou inválido");
		return Optional.of(optionalCliente.get());
	}

	/**
	 * Método que retorna uma lista de clientes cadastrados a partir do Dto
	 * 
	 * @return
	 */
	public List<ClienteDto> listaClientes() {
		List<Cliente> clientes = clienteRepository.findAll();
		List<ClienteDto> clienteDtos = new ArrayList<>();
		for (Cliente cliente : clientes) {
			ClienteDto clienteDto = getDtoDoCliente(cliente);
			clienteDtos.add(clienteDto);
		}

		return clienteDtos;
	}

	public Optional<Cliente> buscaClienteAdmin(Long clienteId) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		if (!cliente.isPresent()) {
			throw new ClienteNotExistException("Cliente não encontrado");
		}
		return Optional.of(cliente.get());
	}

	public Cliente buscaCliente(String clienteNome) {
		return clienteRepository.findByNome(clienteNome);
	}

	public ClienteDto exibeCliente(Optional<Cliente> optionalCliente) {
		ClienteDto optionalClienteDto = getDtoDoOptionalCliente(optionalCliente);
		return optionalClienteDto;
	}

	public static ClienteDto getDtoDoOptionalCliente(Optional<Cliente> optionalCliente) {
		ClienteDto clienteDto = new ClienteDto(optionalCliente);
		return clienteDto;
	}

	public ClienteDto buscaDadosClienteLogado(Cliente cliente) {

		ClienteDto clienteDto = getDtoDoOptionalCliente(buscaClienteAdmin(cliente.getId()));

		return clienteDto;
	}

}
