package br.ufpe.cin.residencia.banco.cliente;

import br.ufpe.cin.residencia.banco.excecoes.ClienteInexistenteException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositorioClientesMap implements IRepositorioClientes {
	private Map<String,Cliente> clientes;

	public RepositorioClientesMap() {
		clientes = new HashMap<String,Cliente>();
	}

	@Override
	public void atualizar(Cliente c) throws ClienteInexistenteException {
		if (existe(c.getCpf())) {
			clientes.put(c.getCpf(), c);
		} else {
			throw new ClienteInexistenteException();
		}
	}

	@Override
	public boolean existe(String cpf) {

        return clientes.containsKey(cpf);
	}

	@Override
	public void inserir(Cliente c) {
		clientes.put(c.getCpf(), c);
	}

	@Override
	public Cliente procurar(String cpf) throws ClienteInexistenteException {
		if (existe(cpf)) {
			return clientes.get(cpf);
		} else {
			throw new ClienteInexistenteException();
		}
	}

	@Override
	public void remover(String cpf) throws ClienteInexistenteException {
		if (existe(cpf)) {
			clientes.remove(cpf);
		} else {
			throw new ClienteInexistenteException();
		}
	}

	@Override
	public List<Cliente> listar() {

        return new ArrayList<Cliente>(clientes.values());
	}
}