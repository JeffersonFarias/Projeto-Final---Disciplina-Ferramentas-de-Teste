package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.*;
import br.ufpe.cin.residencia.banco.excecoes.ClienteInexistenteException;
import br.ufpe.cin.residencia.banco.excecoes.SaldoInsuficienteException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContaTest {

    TipoCliente VIP = TipoCliente.VIP;
    TipoCliente CLASS = TipoCliente.CLASS;
    TipoCliente ESPECIAL = TipoCliente.ESPECIAL;

    Cliente clientTest1, clientTest2, clientTest3, clientTest4;
    Conta conta1, conta2, conta3, conta4;

    @BeforeEach
    void setUp() {
        //Instanciando alguns clientes para testes
        clientTest1 = new Cliente("77884460092", "Antonio", VIP);
        clientTest2 = new Cliente("98625741002", "Amanda", CLASS);
        clientTest3 = new Cliente("60068269099", "Victor", ESPECIAL);
        clientTest4 = new Cliente("78209599089", "Maria", VIP);

        //Instanciando uma algumas contas para testes
        conta1 = new Conta("12542", clientTest1);
        conta2 = new Conta("45826", clientTest2);
        conta3 = new Conta("78546", clientTest3);
        conta4 = new Conta("78546", clientTest4);

    }

    //Testes para Conta

    @Test
    void instanciarContaComSaldo(){
        conta1 = new Conta("12542", 100, clientTest1);

        assertEquals(100, conta1.getSaldo());

    }

    @Test
    void getContaNum(){
        String num = conta2.getNumero();
        assertEquals("45826", num);

    }

    @Test
    void getContaSaldoZero(){
        Double saldo = conta3.getSaldo();
        assertEquals(0, saldo);

    }

    @Test
    void getContaCliente(){
        Cliente c = conta1.getCliente() ;

        assertEquals("Antonio", c.getNome());
        assertEquals("77884460092", c.getCpf());
        assertEquals(VIP, c.getTipo());
    }

    @Test
    void setContaNumero(){
        conta2.setNumero("44444");

        assertEquals("44444", conta2.getNumero());

    }

    @Test
    void setContaSaldo(){
        conta3.setSaldo(10000);

        assertEquals(10000, conta3.getSaldo());

    }

    @Test
    void setContaCliente(){
        conta1.setCliente(clientTest2);

        assertEquals(clientTest2, conta1.getCliente());
    }

    @Test
    void transferenciaDeConta() throws SaldoInsuficienteException {
        conta4.setSaldo(10000);

        conta4.transferir(conta2, 500);

        assertEquals(9500, conta4.getSaldo());
        assertEquals(500, conta2.getSaldo());
    }

    @Test
    public void transferenciaSaldoInsuficienteException() throws SaldoInsuficienteException {
        conta3.setSaldo(10000);

        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    conta3.transferir(conta1, 11000);
                });

    }

    @Test
    public void transferenciaSaldoSuficienteException() throws SaldoInsuficienteException {
        conta1.setSaldo(10000);

        assertDoesNotThrow(
                () -> {
                    conta1.transferir(conta2, 9000);
                });

    }

    @Test
    public void debitarConta() throws SaldoInsuficienteException {
        conta1.setSaldo(250);

        conta1.debitar(200);

        assertEquals(50, conta1.getSaldo());

    }



    @Test
    public void debitarContaException() throws SaldoInsuficienteException {
        conta2.setSaldo(250);

        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    conta2.debitar(251);
                });

    }

    @Test
    public void debitarContaSaqueTotalException() throws SaldoInsuficienteException {
        conta2.setSaldo(250);

        assertDoesNotThrow(
                () -> {
                    conta2.debitar(250);
                });

    }

    @Test
    public void creditarConta() throws SaldoInsuficienteException {
        conta4.creditar(1000);

        assertEquals(1000, conta4.getSaldo());

    }










}