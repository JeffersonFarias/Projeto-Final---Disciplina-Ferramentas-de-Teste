package br.ufpe.cin.residencia.banco.fachada;

import br.ufpe.cin.residencia.banco.cliente.*;
import br.ufpe.cin.residencia.banco.conta.*;
import br.ufpe.cin.residencia.banco.excecoes.*;

public class Fachada {

	private static Fachada instancia;

	private CadastroContas contas;

	private CadastroClientes clientes;

	private Fachada() {
		initCadastros();
	}

	private void initCadastros() {
		IRepositorioContas rep = new RepositorioContasArray();
		contas = new CadastroContas(rep);
		IRepositorioClientes repClientes = new RepositorioClientesArray();
		clientes = new CadastroClientes(repClientes);
	}

	public static Fachada obterInstancia() {
		if (instancia == null) {
			instancia = new Fachada();
		}
		return instancia;
	}

	public void atualizar(Cliente c) throws ClienteInexistenteException {
		clientes.atualizar(c);
	}

	public Cliente procurarCliente(String cpf) throws ClienteInexistenteException {
		return clientes.procurar(cpf);
	}

	public void cadastrar(Cliente c) throws ClienteExistenteException {
		clientes.cadastrar(c);
	}

	public void descadastrarCliente(String cpf) throws ClienteInexistenteException {
		clientes.descadastrar(cpf);
	}

	public void atualizar(ContaAbstrata c) throws ContaInexistenteException {
		contas.atualizar(c);
	}

	public ContaAbstrata procurarConta(String n) throws ContaInexistenteException {
		return contas.procurar(n);
	}

	public void cadastrar(ContaAbstrata c) throws ContaExistenteException, ClienteInexistenteException, ClienteInvalidoException {
		contas.cadastrar(c);
	}

	public void descadastrarConta(String n) throws ContaInexistenteException {
		contas.remover(n);
	}

	public void creditar(String n, double v) throws ContaInexistenteException {
		contas.creditar(n, v);
	}

	public void debitar(String n, double v) throws ContaInexistenteException, SaldoInsuficienteException {
		contas.debitar(n, v);
	}

	public void transferir(String origem, String destino, double val) throws ContaInexistenteException, SaldoInsuficienteException {
		contas.transferir(origem, destino, val);
	}
}