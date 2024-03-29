package br.ufpe.cin.residencia.banco.fachada;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.cliente.TipoCliente;
import br.ufpe.cin.residencia.banco.conta.*;
import br.ufpe.cin.residencia.banco.excecoes.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FachadaTest {

    Fachada fachada;

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
        fachada = Fachada.obterInstancia();

        //Instanciando um cliente para cada tipo de conta
        clientTest1 = new Cliente("77884460092", "Antonio", VIP);
        clientTest2 = new Cliente("98625741002", "Amanda", CLASS);
        clientTest3 = new Cliente("60068269099", "Victor", ESPECIAL);
        clientTest4 = new Cliente("78209599089", "Maria", VIP);

        contaBonificadaTest = new ContaBonificada("12542", clientTest1);
        contaEspecialTest = new ContaEspecial("45826", clientTest2);
        contaImpostoTest = new ContaImposto("78546", clientTest3);
        contaPoupancaTest = new Poupanca("47851", clientTest4);
    }

    @AfterEach
    void tearDown() {
        try {
            Field instance = Fachada.class.getDeclaredField("instancia");
            instance.setAccessible(true);
            instance.set(null, null);
            fachada = null;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
	public void obterInstanciaFachada() {
		Fachada f2 = Fachada.obterInstancia();
		assertEquals(fachada,f2);
	}

    @Test
    void cadastrarContaExistenteException() throws ContaExistenteException, ClienteInexistenteException, ClienteInvalidoException, ClienteExistenteException {
        fachada.cadastrar(clientTest1);
        fachada.cadastrar(clientTest2);
        fachada.cadastrar(clientTest3);
        fachada.cadastrar(clientTest4);

        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);

        assertThrows(
                ContaExistenteException.class,
                () -> {
                    fachada.cadastrar(contaEspecialTest);
                });


    }

    @Test
    void cadastrarContaInexistenteException() throws ContaExistenteException, ClienteInexistenteException, ClienteInvalidoException, ClienteExistenteException {


        fachada.cadastrar(clientTest1);
        fachada.cadastrar(clientTest2);
        fachada.cadastrar(clientTest3);
        fachada.cadastrar(clientTest4);

        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);

        assertDoesNotThrow(
                () -> {
                    fachada.procurarConta("47851");
                });


    }

    @Test
    void cadastrarContaArrayVazio() throws ClienteExistenteException {
        assertDoesNotThrow(
                () -> {
                    fachada.cadastrar(contaImpostoTest);
                });

    }

    @Test
    public void creditarConta() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException, ClienteInexistenteException, ClienteInvalidoException {
        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);
        fachada.cadastrar(contaImpostoTest);

        ContaAbstrata c = fachada.procurarConta("12542");


        assertEquals(0, c.getSaldo());

        fachada.creditar("12542", 5015.14);

        assertEquals(5015.14, c.getSaldo());

    }

    @Test
    public void creditarContaInexistenteException() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException, ClienteInexistenteException, ClienteInvalidoException {
        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);
        fachada.cadastrar(contaImpostoTest);

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    fachada.creditar("4445", 45153);
                });

    }

    @Test
    public void debitarConta() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException, ClienteInexistenteException, ClienteInvalidoException {
        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);
        fachada.cadastrar(contaImpostoTest);

        ContaAbstrata c = fachada.procurarConta("12542");


        fachada.creditar("12542", 1425.25);


        assertEquals(1425.25, c.getSaldo());

        fachada.debitar("12542", 425.25);

        assertEquals(1000, c.getSaldo());

    }

    @Test
    public void debitarContaSaldoInsuficiente() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException, ClienteInexistenteException, ClienteInvalidoException {
        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);
        fachada.cadastrar(contaImpostoTest);

        ContaAbstrata c = fachada.procurarConta("12542");

        fachada.creditar("12542", 1425.25);


        assertEquals(1425.25, c.getSaldo());


        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    fachada.debitar("12542", 1425.26);
                });

    }

    @Test
    void cadastrarClienteArrayVazio() throws ClienteExistenteException {
        assertDoesNotThrow(
                () -> {
                    fachada.cadastrar(clientTest1);
                });

    }

    @Test
    void cadastrarClienteExistenteArray() throws ClienteExistenteException {
        fachada.cadastrar(clientTest1);
        assertThrows(
                ClienteExistenteException.class,
                () -> {
                    fachada.cadastrar(clientTest1);
                });

    }

    @Test
    void descadastrarClienteArray() throws ClienteExistenteException {
        fachada.cadastrar(clientTest1);
        fachada.cadastrar(clientTest2);
        fachada.cadastrar(clientTest3);
        fachada.cadastrar(clientTest4);

        assertDoesNotThrow(
                () -> {
                    fachada.descadastrarCliente(clientTest1.getCpf());
                });

        assertDoesNotThrow(
                () -> {
                    fachada.descadastrarCliente(clientTest2.getCpf());
                });

    }

    @Test
    void descadastrarEProcurarClienteInexistenteArray() throws ClienteExistenteException, ClienteInexistenteException {
        fachada.cadastrar(clientTest1);
        fachada.cadastrar(clientTest2);
        fachada.cadastrar(clientTest3);
        fachada.cadastrar(clientTest4);

        fachada.descadastrarCliente(clientTest1.getCpf());
        fachada.descadastrarCliente(clientTest4.getCpf());

        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    fachada.procurarCliente(clientTest1.getCpf());
                });

        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    fachada.procurarCliente(clientTest4.getCpf());
                });

        assertDoesNotThrow(
                () -> {
                    fachada.procurarCliente(clientTest2.getCpf());
                });

        assertDoesNotThrow(
                () -> {
                    fachada.procurarCliente(clientTest3.getCpf());
                });

    }

    @Test
    void descadastrarClienteInexistenteArray() throws ClienteExistenteException {


        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    fachada.descadastrarCliente(clientTest1.getCpf());
                });

    }

    @Test
    void atualizarClienteExistenteArrayException() throws ClienteInexistenteException, ClienteExistenteException {
        fachada.cadastrar(clientTest1);
        fachada.cadastrar(clientTest2);

        Cliente c = new Cliente("98625741002", "Amanda Maria", VIP);
        assertDoesNotThrow(
                () -> {
                    fachada.atualizar(c);
                });

    }

    @Test
    void atualizarClienteNaoExistenteArrayException() throws ClienteInexistenteException, ClienteExistenteException {
        fachada.cadastrar(clientTest1);
        fachada.cadastrar(clientTest2);

        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    fachada.atualizar(clientTest3);
                });

    }

    @Test
    void atualizarClienteExistenteArray() throws ClienteExistenteException, ClienteInexistenteException {
        fachada.cadastrar(clientTest1);
        fachada.cadastrar(clientTest2);

        Cliente c = new Cliente("98625741002", "Amanda Maria", VIP);

        fachada.atualizar(c);

        Cliente atualizado = fachada.procurarCliente("98625741002");

        assertEquals("98625741002", atualizado.getCpf());
        assertEquals("Amanda Maria", atualizado.getNome());
        assertEquals(VIP, atualizado.getTipo());

    }

    @Test
    void procurarCliente() throws ClienteExistenteException, ClienteInexistenteException {

        fachada.cadastrar(clientTest1);
        fachada.cadastrar(clientTest2);
        fachada.cadastrar(clientTest3);
        fachada.cadastrar(clientTest4);

        String cpf = clientTest1.getCpf();

        assertEquals(clientTest1, fachada.procurarCliente(cpf));

    }

    @Test
    void procurarClienteArrayVazio() throws ClienteExistenteException {

        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    fachada.procurarCliente("451235");
                });

    }

    @Test
    void procurarClienteNaoExistenteArray() throws ClienteExistenteException {
        fachada.cadastrar(clientTest1);

        assertDoesNotThrow(

                () -> {
                    fachada.procurarCliente(clientTest1.getCpf());
                });

    }

    @Test
    public void debitarContaInexistenteException() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException, ClienteInexistenteException, ClienteInvalidoException {
        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);
        fachada.cadastrar(contaImpostoTest);

        ContaAbstrata c = fachada.procurarConta("12542");


        fachada.creditar("12542", 1425.25);


        assertEquals(1425.25, c.getSaldo());

        fachada.descadastrarConta("12542");


        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    fachada.debitar("12542", 1425.26);
                });

    }

    @Test
    void procurarContaExistenteArrayException() throws ClienteExistenteException, ContaExistenteException, ClienteInexistenteException, ClienteInvalidoException {
        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);
        assertDoesNotThrow(
                () -> {
                    fachada.procurarConta("12542");
                });

    }

    @Test
    void procurarContaNaoExistenteArrayException() throws ClienteInexistenteException, ClienteExistenteException, ContaExistenteException, ClienteInvalidoException {
        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    fachada.procurarConta("41579");
                });

    }

    @Test
    void procurarContaExistenteArray() throws ClienteExistenteException, ClienteInexistenteException, ContaExistenteException, ContaInexistenteException, ClienteInvalidoException {
        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);
        fachada.cadastrar(contaImpostoTest);

        double saldo = contaImpostoTest.getSaldo();
        String numero = contaImpostoTest.getNumero();
        Cliente cliente = contaImpostoTest.getCliente();

        ContaAbstrata conta = fachada.procurarConta("78546");

        assertEquals(saldo, conta.getSaldo());
        assertEquals(numero, conta.getNumero());
        assertEquals(cliente, conta.getCliente());

    }

    @Test
    void atualizarContaExistenteArrayException() throws ClienteInexistenteException, ClienteExistenteException, ContaExistenteException, ClienteInvalidoException {
        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);
        fachada.cadastrar(contaImpostoTest);

        ContaAbstrata conta = new ContaEspecial("12542", 5000, clientTest4);

        assertDoesNotThrow(
                () -> {
                    fachada.atualizar(conta);
                });

    }

    @Test
    void atualizarContaNaoExistenteArrayException() throws ClienteInexistenteException, ClienteExistenteException, ContaExistenteException, ClienteInvalidoException {
        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);
        fachada.cadastrar(contaImpostoTest);

        ContaAbstrata conta = new ContaEspecial("11111", 5000, clientTest4);

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    fachada.atualizar(conta);
                });

    }

    @Test
    void atualizarContaExistentePrimeiraPosicaoArray() throws ClienteExistenteException, ClienteInexistenteException, ContaExistenteException, ContaInexistenteException, ClienteInvalidoException {
        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);
        fachada.cadastrar(contaImpostoTest);

        ContaAbstrata conta = new ContaEspecial("12542", 4152, clientTest3);

        fachada.atualizar(conta);

        ContaAbstrata conta2 = fachada.procurarConta("12542");


        assertEquals(4152, conta2.getSaldo());
        assertEquals("12542", conta.getNumero());
        assertEquals(clientTest3, conta2.getCliente());

    }

    @Test
    void procurarContaRemovidaArrayException() throws ClienteExistenteException, ClienteInexistenteException, ContaExistenteException, ContaInexistenteException, ClienteInvalidoException {
        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);
        fachada.cadastrar(contaImpostoTest);


        fachada.descadastrarConta("47851");

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    fachada.procurarConta("47851");
                });

    }

    @Test
    void removerContaInexistenteArrayException() throws ContaExistenteException, ContaInexistenteException, ClienteInexistenteException, ClienteInvalidoException {
        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);
        fachada.cadastrar(contaImpostoTest);


        fachada.descadastrarConta("12542");
        fachada.descadastrarConta("47851");

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    fachada.descadastrarConta("47851");
                });
    }

    @Test
    void buscarContaExistenteAposRemover() throws ClienteExistenteException, ClienteInexistenteException, ContaExistenteException, ContaInexistenteException, ClienteInvalidoException {
        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);
        fachada.cadastrar(contaImpostoTest);

        fachada.descadastrarConta("12542");
        fachada.descadastrarConta("78546");


        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    fachada.descadastrarConta("12542");
                });
        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    fachada.descadastrarConta("78546");
                });

        assertEquals(contaEspecialTest, fachada.procurarConta("45826"));
        assertEquals(contaPoupancaTest, fachada.procurarConta("47851"));
    }

    @Test
    void transferenciaDeConta() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException, ClienteInexistenteException, ClienteInvalidoException {

        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);
        fachada.cadastrar(contaImpostoTest);

        fachada.creditar(contaBonificadaTest.getNumero(), 5000);
        fachada.creditar(contaPoupancaTest.getNumero(), 1425);


        String contaOrigem = contaBonificadaTest.getNumero();
        String contaDestino = contaPoupancaTest.getNumero();

        fachada.transferir(contaOrigem, contaDestino, 4000);

        assertEquals(1000, contaBonificadaTest.getSaldo());
        assertEquals(5425, contaPoupancaTest.getSaldo());

    }

    @Test
    void transferenciaDeContaSaldoInsuficiente() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException, ClienteInexistenteException, ClienteInvalidoException {

        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);
        fachada.cadastrar(contaImpostoTest);

        fachada.creditar(contaBonificadaTest.getNumero(), 5000);
        fachada.creditar(contaPoupancaTest.getNumero(), 1425);

        String contaOrigem = contaBonificadaTest.getNumero();
        String contaDestino = contaPoupancaTest.getNumero();

        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    fachada.transferir(contaOrigem, contaDestino, 5001);
                });


    }

    @Test
    void transferenciaDeContaInexistenteException() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException, ClienteInexistenteException, ClienteInvalidoException {

        fachada.cadastrar(contaBonificadaTest);
        fachada.cadastrar(contaEspecialTest);
        fachada.cadastrar(contaPoupancaTest);
        fachada.cadastrar(contaImpostoTest);

        fachada.creditar(contaBonificadaTest.getNumero(), 5000);
        fachada.creditar(contaPoupancaTest.getNumero(), 1425);

        String contaOrigem = contaBonificadaTest.getNumero();
        String contaDestino = contaPoupancaTest.getNumero();

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    fachada.transferir("42563", contaDestino, 5001);
                });

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    fachada.transferir(contaOrigem, "56314", 5001);
                });

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    fachada.transferir("47159", "56314", 5001);
                });


    }

}