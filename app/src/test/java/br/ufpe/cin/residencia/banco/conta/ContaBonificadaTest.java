package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.cliente.TipoCliente;
import br.ufpe.cin.residencia.banco.excecoes.SaldoInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContaBonificadaTest {

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

    //Testes para Conta Bonificada

    @Test
    void instanciarContaBonificadaComSaldo(){
        contaBonificadaTest = new ContaBonificada("12542", 100, clientTest1);

        assertEquals(100, contaBonificadaTest.getSaldo());

    }

    @Test
    void getContaBonificadaNum(){
        String num = contaBonificadaTest.getNumero();
        assertEquals("12542", num);

    }

    @Test
    void getContaBonificadaSaldoZero(){
        Double saldo = contaBonificadaTest.getSaldo();
        assertEquals(0, saldo);

    }

    @Test
    void getContaBonificadaCliente(){
        Cliente c = contaBonificadaTest.getCliente() ;

        assertEquals("Antonio", c.getNome());
        assertEquals("77884460092", c.getCpf());
        assertEquals(VIP, c.getTipo());
    }

    @Test
    void setContaBonificadaNumero(){
        contaBonificadaTest.setNumero("44444");

        assertEquals("44444", contaBonificadaTest.getNumero());

    }

    @Test
    void setContaBonificadaSaldo(){
        contaBonificadaTest.setSaldo(10000);

        assertEquals(10000, contaBonificadaTest.getSaldo());

    }

    @Test
    void setContaBonificadaCliente(){
        contaBonificadaTest.setCliente(clientTest2);

        assertEquals(clientTest2, contaBonificadaTest.getCliente());
    }

    @Test
    void transferenciaDeContaBonificada() throws SaldoInsuficienteException {
        contaBonificadaTest.setSaldo(10000);

        contaBonificadaTest.transferir(contaEspecialTest, 500);

        assertEquals(9500, contaBonificadaTest.getSaldo());
        assertEquals(500, contaEspecialTest.getSaldo());
    }

    @Test
    public void transferenciaDeContaBonificadaSaldoInsuficienteException() throws SaldoInsuficienteException {
        contaBonificadaTest.setSaldo(10000);

        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    contaBonificadaTest.transferir(contaEspecialTest, 11000);
                });

    }

    @Test
    public void transferenciaDeContaBonificadaSaldoSuficienteException() throws SaldoInsuficienteException {
        contaBonificadaTest.setSaldo(10000);

        assertDoesNotThrow(
                () -> {
                    contaBonificadaTest.transferir(contaEspecialTest, 9000);
                });

    }

    @Test
    public void debitarContaBonificada() throws SaldoInsuficienteException {
        contaBonificadaTest.setSaldo(250);

        contaBonificadaTest.debitar(200);

        assertEquals(50, contaBonificadaTest.getSaldo());

    }

    @Test
    public void debitarContaBonificadaException() throws SaldoInsuficienteException {
        contaBonificadaTest.setSaldo(250);

        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    contaBonificadaTest.debitar(251);
                });

    }

    @Test
    public void creditarContaBonificada() throws SaldoInsuficienteException {
        contaBonificadaTest.creditar(1000);

        assertEquals(1000, contaBonificadaTest.getSaldo());

    }

    @Test
    public void creditarContaBonificadaCalculaBonus() throws SaldoInsuficienteException {
        contaBonificadaTest.creditar(1000);

        assertEquals(10, contaBonificadaTest.getBonus());

    }

    @Test
    public void creditarContaBonificadaCalculaBonus2() throws SaldoInsuficienteException {
        contaBonificadaTest.creditar(1000);

        contaBonificadaTest.creditar(1050);

        contaBonificadaTest.creditar(10000);

        assertEquals(120.5, contaBonificadaTest.getBonus());

    }

    @Test
    public void renderBonusContaBonificada(){

        contaBonificadaTest.creditar(1000);

        contaBonificadaTest.creditar(1050);

        contaBonificadaTest.creditar(10000);

        contaBonificadaTest.renderBonus();

        assertEquals(0, contaBonificadaTest.getBonus());
        assertEquals(12170.5, contaBonificadaTest.getSaldo());

    }





}