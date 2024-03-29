package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.cliente.TipoCliente;
import br.ufpe.cin.residencia.banco.excecoes.SaldoInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContaImpostoTest {

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
        contaPoupancaTest = new Poupanca("47521", clientTest4);

    }

    @Test
    void instanciarContaImpostoComSaldo(){
        contaImpostoTest = new ContaImposto("12542", 100, clientTest1);

        assertEquals(100, contaImpostoTest.getSaldo());

    }

    @Test
    void getContaImpostoNum(){
        String num = contaImpostoTest.getNumero();
        assertEquals("78546", num);

    }

    @Test
    void getContaImpostoSaldoZero(){
        Double saldo = contaImpostoTest.getSaldo();
        assertEquals(0, saldo);

    }

    @Test
    void getContaImpostoCliente(){
        Cliente c = contaImpostoTest.getCliente() ;

        assertEquals("Victor", c.getNome());
        assertEquals("60068269099", c.getCpf());
        assertEquals(ESPECIAL, c.getTipo());
    }

    @Test
    void setContaImpostoNumero(){
        contaImpostoTest.setNumero("44444");

        assertEquals("44444", contaImpostoTest.getNumero());

    }

    @Test
    void setContaImpostoSaldo(){
        contaImpostoTest.setSaldo(10000);

        assertEquals(10000, contaImpostoTest.getSaldo());

    }

    @Test
    void setContaImpostoCliente(){
        contaImpostoTest.setCliente(clientTest2);

        assertEquals(clientTest2, contaImpostoTest.getCliente());
    }

    @Test
    void transferenciaDeContaImposto() throws SaldoInsuficienteException {
        contaImpostoTest.setSaldo(10000);

        contaImpostoTest.transferir(contaEspecialTest, 500);

        assertEquals(9499.5, contaImpostoTest.getSaldo());
        assertEquals(500, contaEspecialTest.getSaldo());
    }



    @Test
    public void transferenciaDeContaImpostoSaldoInsuficienteException() throws SaldoInsuficienteException {
        contaImpostoTest.setSaldo(10000);

        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    contaImpostoTest.transferir(contaEspecialTest, 11000);
                });

    }

    @Test
    public void transferenciaDeContaImpostoSaldoSuficienteException() throws SaldoInsuficienteException {
        contaImpostoTest.setSaldo(10000);

        assertDoesNotThrow(
                () -> {
                    contaImpostoTest.transferir(contaEspecialTest, 9000);
                });

    }

    @Test
    public void debitarContaImposto() throws SaldoInsuficienteException {
        contaImpostoTest.setSaldo(250);

        contaImpostoTest.debitar(200);

        assertEquals(49.8, contaImpostoTest.getSaldo(),0.001);

    }

    @Test
    public void debitarValorTotalContaImposto() throws SaldoInsuficienteException {
        contaImpostoTest.setSaldo(10010);

        contaImpostoTest.debitar(10000);

        assertEquals(0, contaImpostoTest.getSaldo());

    }

    @Test
    public void debitarContaImpostoException() throws SaldoInsuficienteException {
        contaImpostoTest.setSaldo(250);

        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    contaImpostoTest.debitar(251);
                });

    }

    @Test
    public void creditarContaImposto() throws SaldoInsuficienteException {
        contaImpostoTest.creditar(1000);

        assertEquals(1000, contaImpostoTest.getSaldo());

    }

}