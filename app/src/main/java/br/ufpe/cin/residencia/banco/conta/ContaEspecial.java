package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.excecoes.SaldoInsuficienteException;

public class ContaEspecial extends Conta {

    private double valorChequeEspecial;

    public ContaEspecial(String numeroConta, double saldo, Cliente c, double creditoChequeEspecial) {
        super(numeroConta, saldo, c);
        this.valorChequeEspecial = creditoChequeEspecial;

    }

    public ContaEspecial(String numeroConta, Cliente c) {
        this(numeroConta, 0, c, 0);
    }

    public ContaEspecial(String numeroConta, double saldo, Cliente c) {
        this(numeroConta, saldo, c, 0);
    }


    public double getChequeEspecial(){
        return this.valorChequeEspecial;
    }

    public void setChequeEspecial(double v){
        this.valorChequeEspecial = v;
    }

    @Override
    public void debitar(double valor) throws SaldoInsuficienteException {
        if (valor <= super.getSaldo()) {
            setSaldo(super.getSaldo() - valor);
        }
        else if(valor <= getSaldo()){
            double resto = valor - super.getSaldo();
            setSaldo(0);
            setChequeEspecial(getChequeEspecial()-resto);
        }
        else {
            throw new SaldoInsuficienteException();
        }
    }

    @Override
    public double getSaldo() {
        return super.getSaldo() + this.valorChequeEspecial;
    }
}