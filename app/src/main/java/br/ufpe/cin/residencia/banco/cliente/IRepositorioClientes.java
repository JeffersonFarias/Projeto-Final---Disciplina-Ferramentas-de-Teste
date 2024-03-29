package br.ufpe.cin.residencia.banco.cliente;

import br.ufpe.cin.residencia.banco.excecoes.ClienteInexistenteException;

import java.util.List;

public interface IRepositorioClientes {

	void atualizar(Cliente c) throws ClienteInexistenteException;

	boolean existe(String cpf);

	void inserir(Cliente c);

	Cliente procurar(String cpf) throws ClienteInexistenteException;

	void remover(String cpf) throws ClienteInexistenteException;

	List<Cliente> listar();

}