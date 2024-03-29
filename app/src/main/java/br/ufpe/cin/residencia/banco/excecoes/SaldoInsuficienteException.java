package br.ufpe.cin.residencia.banco.excecoes;
public class SaldoInsuficienteException extends BancoException {
	public SaldoInsuficienteException() {
		super("Saldo insuficiente!");
	}
	private double saldo;
	//...
}
