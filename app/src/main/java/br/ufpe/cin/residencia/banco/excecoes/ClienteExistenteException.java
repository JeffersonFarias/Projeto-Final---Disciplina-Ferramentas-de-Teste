package br.ufpe.cin.residencia.banco.excecoes;
public class ClienteExistenteException extends BancoException {
	public ClienteExistenteException() {
		super("Cliente ja existe!");
	}
	private double saldo;
	//...
}
