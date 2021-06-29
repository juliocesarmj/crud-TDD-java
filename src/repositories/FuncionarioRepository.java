package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entities.Funcionario;
import interfaces.IFuncionarioRepository;

public class FuncionarioRepository implements IFuncionarioRepository {

	private Connection connection;

	public FuncionarioRepository(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void create(Funcionario funcionario) throws Exception {

		String sql = "insert into funcionario(nome, cpf, matricula, salario) values(?, ?, ?, ?)";

		PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		// A cada insert feito no banco de dados o comando
		// PreparedStatement.RETURN_GENERATED_KEYS
		// retorna um identificador do registro inserido.
		// o comando inteiro diz. BANCO DE DADOS, CRIE NA TABELA FUNCIONARIO O REGISTRO
		// INFORMADO
		// E RETORNE PRA MIM UM IDENTIFICADOR DESSE REGISTRO QUE ACABAMOS DE FAZER.

		statement.setString(1, funcionario.getNome());
		statement.setString(2, funcionario.getCpf());
		statement.setString(3, funcionario.getMatricula());
		statement.setDouble(4, funcionario.getSalario());

		statement.execute();

		// ler a chave do funcionario (id funcionario) gerado
		// no banco de dados após a execução do comando INSERT
		ResultSet result = statement.getGeneratedKeys();

		if (result.next()) {
			funcionario.setIdFuncionario(result.getInt(1));
		}
		statement.close();

	}

	@Override
	public void update(Funcionario funcionario) throws Exception {

		String sql = "update funcionario set nome = ?, cpf = ?, matricula = ?, salario = ? where idfuncionario = ?";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, funcionario.getNome());
		statement.setString(2, funcionario.getCpf());
		statement.setString(3, funcionario.getMatricula());
		statement.setDouble(4, funcionario.getSalario());
		statement.setInt(5, funcionario.getIdFuncionario());

		statement.execute();
		statement.close();

	}

	@Override
	public void delete(Funcionario funcionario) throws Exception {
		
		String sql = "delete from funcionario where idfuncionario = ?";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setInt(1, funcionario.getIdFuncionario());

		statement.execute();
		statement.close();

	}

	@Override
	public List<Funcionario> findAll() throws Exception {
		
		String sql = "select * from funcionario";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet result = statement.executeQuery();
		
		List<Funcionario> lista = new ArrayList<Funcionario>();
		while(result.next()) {
			Funcionario funcionario = new Funcionario();
			
			funcionario.setIdFuncionario(result.getInt("idfuncionario"));
			funcionario.setNome(result.getString("nome"));
			funcionario.setMatricula(result.getString("matricula"));
			funcionario.setCpf(result.getString("cpf"));
			funcionario.setSalario(result.getDouble("salario"));
			
			lista.add(funcionario);
				
		}
		return lista;
	}

	@Override
	public Funcionario findById(Integer idFuncionario) throws Exception {

		String sql = "select * from funcionario where idfuncionario = ?";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setInt(1, idFuncionario);

		ResultSet result = statement.executeQuery();

		if (result.next()) {

			Funcionario funcionario = new Funcionario();

			funcionario.setIdFuncionario(result.getInt("idfuncionario"));
			funcionario.setNome(result.getString("nome"));
			funcionario.setCpf(result.getString("cpf"));
			funcionario.setMatricula(result.getString("matricula"));
			funcionario.setSalario(result.getDouble("salario"));

			return funcionario;
		}
		return null;
	}

}
