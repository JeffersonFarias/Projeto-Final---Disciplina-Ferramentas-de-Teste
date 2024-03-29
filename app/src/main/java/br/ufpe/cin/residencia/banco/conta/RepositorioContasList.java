package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.excecoes.ContaInexistenteException;

import java.util.ArrayList;
import java.util.List;

public class RepositorioContasList implements IRepositorioContas {
	private List<ContaAbstrata> contas;
	private final static int tamCache = 100;
	public RepositorioContasList() {
		contas = new ArrayList<>(tamCache);
	}

	@Override
	public void inserir(ContaAbstrata c) {
		contas.add(c);
	}

	@Override
	public boolean existe(String num) {
		boolean resp = false;

		int i = this.procurarIndice(num);
		if (i != -1) {
			resp = true;
		}

		return resp;
	}

	private int procurarIndice(String num) {
		int ind = -1;
		for (ContaAbstrata c : contas) {
			if ((c.getNumero()).equals(num)) {
				return contas.indexOf(c);
			}
		}
		return ind;
	}

	@Override
	public void atualizar(ContaAbstrata c) throws ContaInexistenteException {
		int i = this.procurarIndice(c.getNumero());
		if (i != -1) {
			contas.remove(i);
			contas.add(c);
		} else {
			throw new ContaInexistenteException();
		}
	}

	@Override
	public ContaAbstrata procurar(String num) throws ContaInexistenteException {
		if (existe(num)) {
			return contas.get(this.procurarIndice(num));
		} else {
			throw new ContaInexistenteException();
		}
	}

	@Override
	public void remover(String num) throws ContaInexistenteException {
		int i = this.procurarIndice(num);
		if (i != -1) {
			contas.remove(i);
		} else {
			throw new ContaInexistenteException();
		}
	}

	@Override
	public List<ContaAbstrata> listar() {
		return contas;
	}
}
