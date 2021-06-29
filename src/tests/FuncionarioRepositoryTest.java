package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entities.Funcionario;
import factories.ConnectionFactory;
import interfaces.IFuncionarioRepository;
import repositories.FuncionarioRepository;

class FuncionarioRepositoryTest {

	private IFuncionarioRepository funcionarioRepository;
	private Funcionario funcionario;

	@BeforeEach
	void setUp() throws Exception {

		funcionario = new Funcionario();

		funcionario.setNome("Julio Cesar");
		funcionario.setCpf("123.456.789-00");
		funcionario.setMatricula("COTI2021-001");
		funcionario.setSalario(1000.00);

		// produzindo uma conexao com o banco de dados

		ConnectionFactory factory = new ConnectionFactory();
		funcionarioRepository = new FuncionarioRepository(factory.getConnection());
	}

	@Test
	void testCreate() throws Exception {
		// realizar um cadastro de funcionário
		funcionarioRepository.create(funcionario);

		/*
		 * Criar um critério de teste Todos os funcionários, após cadastrados Devem
		 * possuir um id gerado pelo banco de dados
		 */
		assertNotNull(funcionario.getIdFuncionario());

		/*
		 * Ao buscar no banco de dados um funcionário através do id gerado Deveremos
		 * receber o mesmo funcionário cadastrado
		 */

		Funcionario funcionarioRegistradoBanco = funcionarioRepository.findById(funcionario.getIdFuncionario());

		// comparar se os funcionarios sao iguais
		assertEquals(funcionario, funcionarioRegistradoBanco);

	}

	@Test
	void testUpdate() throws Exception {

		// realizar um cadastro de funcionário
		funcionarioRepository.create(funcionario);

		funcionario.setNome("Leo cabelo");
		funcionario.setCpf("999.999.999-11");
		funcionario.setMatricula("MPGMI-2021");
		funcionario.setSalario(3000.00);

		funcionarioRepository.update(funcionario);

		/*
		 * Ao buscar no banco de dados um funcionário através do id Deveremos receber o
		 * mesmo funcionário atualizado
		 */
		Funcionario funcionarioBanco = funcionarioRepository.findById(funcionario.getIdFuncionario());

		assertEquals(funcionario, funcionarioBanco);
	}

	@Test
	void testDelete() throws Exception {

		// realizar um cadastro de funcionário
		funcionarioRepository.create(funcionario);

		// exclui o funcionario
		funcionarioRepository.delete(funcionario);

		/*
		 * Ao buscar no banco de dados um funcionário através do id Deveremos receber
		 * como resultado 'null' vazio. Pois no metodo delete, removemos o registro.
		 */
		Funcionario funcionarioBanco = funcionarioRepository.findById(funcionario.getIdFuncionario());

		assertNull(funcionarioBanco);
	}

	@Test
	void testFindAll() throws Exception {

		// realizar um cadastro de funcionário
		funcionarioRepository.create(funcionario);
		
		/*
		 * Ao consultarmos os registros de funcionarios no banco de dados
		 * a quantidade obtida deve ser maior do que 0. 
		 */
		List<Funcionario> funcionariosBanco = funcionarioRepository.findAll();
		
		assertTrue(funcionariosBanco.size() > 0);
	}

}
