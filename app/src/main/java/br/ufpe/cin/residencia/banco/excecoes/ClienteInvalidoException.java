package br.ufpe.cin.residencia.banco.excecoes;
public class ClienteInvalidoException extends BancoException {
	public ClienteInvalidoException() {
		super("Cliente invalido!");
	}
	private double saldo;
	//...
}
