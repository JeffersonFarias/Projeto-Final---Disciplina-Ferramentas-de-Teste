package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.cliente.TipoCliente;
import br.ufpe.cin.residencia.banco.excecoes.SaldoInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PoupancaTest {

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

    }@Test
    void instanciarPoupancaComSaldo(){
        contaPoupancaTest = new Poupanca("12542", 100, clientTest1);

        assertEquals(100, contaPoupancaTest.getSaldo());

    }

    @Test
    void getContaPoupancaNum(){
        String num = contaPoupancaTest.getNumero();
        assertEquals("78546", num);

    }

    @Test
    void getContaPoupancaSaldoZero(){
        Double saldo = contaPoupancaTest.getSaldo();
        assertEquals(0, saldo);

    }

    @Test
    void getContaPoupancaCliente(){
        Cliente c = contaPoupancaTest.getCliente() ;

        assertEquals("Maria", c.getNome());
        assertEquals("78209599089", c.getCpf());
        assertEquals(VIP, c.getTipo());
    }

    @Test
    void setContaPoupancaNumero(){
        contaPoupancaTest.setNumero("44444");

        assertEquals("44444", contaPoupancaTest.getNumero());

    }

    @Test
    void setContaPoupancaSaldo(){
        contaPoupancaTest.setSaldo(10000);

        assertEquals(10000, contaPoupancaTest.getSaldo());

    }

    @Test
    void setContaPoupancaCliente(){
        contaPoupancaTest.setCliente(clientTest2);

        assertEquals(clientTest2, contaPoupancaTest.getCliente());
    }

    @Test
    void transferenciaDeContaPoupanca() throws SaldoInsuficienteException {
        contaPoupancaTest.setSaldo(10000);

        contaPoupancaTest.transferir(contaEspecialTest, 500);

        assertEquals(9500, contaPoupancaTest.getSaldo());
        assertEquals(500, contaEspecialTest.getSaldo());
    }

    @Test
    public void transferenciaDeContaPoupancaSaldoInsuficienteException() throws SaldoInsuficienteException {
        contaPoupancaTest.setSaldo(10000);

        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    contaPoupancaTest.transferir(contaEspecialTest, 11000);
                });

    }

    @Test
    public void transferenciaDeContaPoupancaSaldoSuficienteException() throws SaldoInsuficienteException {
        contaPoupancaTest.setSaldo(10000);

        assertDoesNotThrow(
                () -> {
                    contaPoupancaTest.transferir(contaEspecialTest, 9000);
                });

    }

    @Test
    public void debitarContaPoupanca() throws SaldoInsuficienteException {
        contaPoupancaTest.setSaldo(250);

        contaPoupancaTest.debitar(200);

        assertEquals(50, contaPoupancaTest.getSaldo());

    }

    @Test
    public void debitarContaPoupancaException() throws SaldoInsuficienteException {
        contaPoupancaTest.setSaldo(250);

        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    contaPoupancaTest.debitar(251);
                });

    }

    @Test
    public void creditarContaPoupanca() throws SaldoInsuficienteException {
        contaPoupancaTest.creditar(1000);

        assertEquals(1000, contaPoupancaTest.getSaldo());

    }

    @Test
    void renderJuros() {
        contaPoupancaTest.setSaldo(1000);

        assertEquals(1000, contaPoupancaTest.getSaldo());

        contaPoupancaTest.renderJuros(0.01);

        assertEquals(1010, contaPoupancaTest.getSaldo());

    }
}