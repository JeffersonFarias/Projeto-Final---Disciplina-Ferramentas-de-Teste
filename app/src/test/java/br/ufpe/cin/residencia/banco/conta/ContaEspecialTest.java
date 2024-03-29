package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.cliente.TipoCliente;
import br.ufpe.cin.residencia.banco.excecoes.SaldoInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContaEspecialTest {

    TipoCliente VIP = TipoCliente.VIP;
    TipoCliente CLASS = TipoCliente.CLASS;
    TipoCliente ESPECIAL = TipoCliente.ESPECIAL;

    Cliente clientTest1, clientTest2, clientTest3, clientTest4;
    ContaBonificada contaBonificadaTest;
    ContaEspecial contaEspecialTest;
    ContaImposto contaImpostoTest;
    Poupanca contaPoupancaTest;

    @BeforeEach
    void setUp() {
        //Instanciando um cliente para cada tipo de conta
        clientTest1 = new Cliente("77884460092", "Antonio", VIP);
        clientTest2 = new Cliente("98625741002", "Amanda", CLASS);
        clientTest3 = new Cliente("60068269099", "Victor", ESPECIAL);
        clientTest4 = new Cliente("78209599089", "Maria", VIP);

        //Instanciando uma conta de cada tipo
        contaBonificadaTest = new ContaBonificada("12542", clientTest1);
        contaEspecialTest = new ContaEspecial("45826", clientTest2);
        contaImpostoTest = new ContaImposto("78546", clientTest3);
        contaPoupancaTest = new Poupanca("78546", clientTest4);

    }

    @Test
    void instanciarContaEspecialComSaldo(){
        contaEspecialTest = new ContaEspecial("12542", 100, clientTest1);

        assertEquals(100, contaEspecialTest.getSaldo());

    }

    @Test
    void instanciarContaEspecialSemSaldo(){
        contaEspecialTest = new ContaEspecial("12542", clientTest1);

        assertEquals(0, contaEspecialTest.getSaldo());

    }

    @Test
    void instanciarContaEspecialComSaldoEChequeEspecial(){
        contaEspecialTest = new ContaEspecial("12542", 5000, clientTest1, 1100);

        assertEquals(6100, contaEspecialTest.getSaldo());
        assertEquals(1100, contaEspecialTest.getChequeEspecial());

    }

    @Test
    void getContaEspecialNum(){
        String num = contaEspecialTest.getNumero();
        assertEquals("45826", num);

    }

    @Test
    void getContaEspecialSaldoZero(){
        Double saldo = contaEspecialTest.getSaldo();
        assertEquals(0, saldo);

    }

    @Test
    void getContaEspecialCliente(){
        Cliente c = contaEspecialTest.getCliente() ;

        assertEquals("Amanda", c.getNome());
        assertEquals("98625741002", c.getCpf());
        assertEquals(CLASS, c.getTipo());
    }

    @Test
    void setContaEspecialNumero(){
        contaEspecialTest.setNumero("44444");

        assertEquals("44444", contaEspecialTest.getNumero());

    }

    @Test
    void setContaEspecialSaldo(){
        contaEspecialTest.setSaldo(10000);

        assertEquals(10000, contaEspecialTest.getSaldo());

    }

    @Test
    void setContaEspecialCliente(){
        contaEspecialTest.setCliente(clientTest2);

        assertEquals(clientTest2, contaEspecialTest.getCliente());
    }

    @Test
    void transferenciaDeContaEspecial() throws SaldoInsuficienteException {
        contaEspecialTest.setSaldo(10000);

        contaEspecialTest.transferir(contaBonificadaTest, 500);

        assertEquals(9500, contaEspecialTest.getSaldo());
        assertEquals(500, contaBonificadaTest.getSaldo());
    }

    //Verificar esse teste depois
    @Test
    void debitarDeContaEspecialSaldoTotal() throws SaldoInsuficienteException {

        contaEspecialTest = new ContaEspecial("12542", 100, clientTest1, 1100);
        double valorDebitado = contaEspecialTest.getSaldo()-contaEspecialTest.getChequeEspecial();
        contaEspecialTest.debitar(valorDebitado);

        assertEquals(0, (contaEspecialTest.getSaldo()-contaEspecialTest.getChequeEspecial()));

        assertEquals(1100, contaEspecialTest.getChequeEspecial());
    }

    @Test
    void transferenciaDeContaEspecialComChequeEspecial() throws SaldoInsuficienteException {
        contaEspecialTest = new ContaEspecial("12542", 5000, clientTest1, 1100);

        contaEspecialTest.transferir(contaBonificadaTest, 6100);

        assertEquals(0, contaEspecialTest.getSaldo());
        assertEquals(6100, contaBonificadaTest.getSaldo());
        assertEquals(0, contaEspecialTest.getChequeEspecial());
    }

    @Test
    void transferenciaDeContaEspecialComChequeEspecial2() throws SaldoInsuficienteException {
        contaEspecialTest = new ContaEspecial("12542", 5000, clientTest1, 1100);

        contaEspecialTest.transferir(contaBonificadaTest, 5000);

        assertEquals(1100, contaEspecialTest.getSaldo());
        assertEquals(5000, contaBonificadaTest.getSaldo());
        assertEquals(1100, contaEspecialTest.getChequeEspecial());
    }

    @Test
    void transferenciaDeContaEspecialComChequeEspecial3() throws SaldoInsuficienteException {
        contaEspecialTest = new ContaEspecial("12542", 5000, clientTest1, 1100);

        contaEspecialTest.transferir(contaBonificadaTest, 5500);

        assertEquals(600, contaEspecialTest.getSaldo());
        assertEquals(5500, contaBonificadaTest.getSaldo());
        assertEquals(600, contaEspecialTest.getChequeEspecial());
    }

    @Test
    public void transferenciaDeContaEspecialSaldoInsuficienteException() throws SaldoInsuficienteException {
        contaEspecialTest.setSaldo(10000);

        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    contaEspecialTest.transferir(contaEspecialTest, 11000);
                });

    }

    @Test
    public void transferenciaDeContaEspecialSaldoEChequeInsuficienteException() throws SaldoInsuficienteException {
        contaEspecialTest = new ContaEspecial("12542", 5000, clientTest1, 1100);

        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    contaEspecialTest.transferir(contaEspecialTest, 6101);
                });

    }

    @Test
    public void transferenciaDeContaEspecialSaldoSuficienteException() throws SaldoInsuficienteException {
        contaEspecialTest.setSaldo(10000);

        assertDoesNotThrow(
                () -> {
                    contaEspecialTest.transferir(contaEspecialTest, 9000);
                });

    }

    @Test
    public void debitarContaEspecial() throws SaldoInsuficienteException {
        contaEspecialTest.setSaldo(250);

        contaEspecialTest.debitar(200);

        assertEquals(50, contaEspecialTest.getSaldo());

    }

    @Test
    public void debitarContaEspecialException() throws SaldoInsuficienteException {
        contaEspecialTest.setSaldo(250);

        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    contaEspecialTest.debitar(251);
                });

    }

    @Test
    public void creditarContaEspecial() throws SaldoInsuficienteException {
        contaEspecialTest.creditar(1000);

        assertEquals(1000, contaEspecialTest.getSaldo());
    }




}