package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.Cliente;

public class Poupanca extends Conta {
	public Poupanca(String num, Cliente cli) {
		super(num, cli);
	}

	public Poupanca(String num, double s, Cliente c) {
		super(num, s, c);
	}

	public void renderJuros(double taxa) {
		double saldo = this.getSaldo();
		this.creditar(saldo * taxa);
	}
}
