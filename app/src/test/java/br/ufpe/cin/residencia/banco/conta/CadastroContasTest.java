package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.*;
import br.ufpe.cin.residencia.banco.excecoes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CadastroContasTest {

    RepositorioClientesArray repClientesArray;
    RepositorioClientesList repClientesList;
    RepositorioClientesMap repClientesMap;

    RepositorioContasArray repContasArray;
    RepositorioContasList repContasList;
    RepositorioContasMap repContasMap;

    CadastroClientes clientesArray;
    CadastroClientes clientesList;
    CadastroClientes clientesMap;

    CadastroContas contasArray;
    CadastroContas contasList;
    CadastroContas contasMap;

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
        // Iniciando um repositorio de clientes com cada tipo de conjunto de dados
        repClientesArray = new RepositorioClientesArray();
        repClientesList = new RepositorioClientesList();
        repClientesMap = new RepositorioClientesMap();

        //Iniciando uma instancia de cadastro de clientes com cada tipo de repositorio
        clientesArray = new CadastroClientes(repClientesArray);
        clientesList = new CadastroClientes(repClientesList);
        clientesMap = new CadastroClientes(repClientesMap);

        //Iniciando um repositirio de contas com cada tipo de conjunto de dados
        repContasArray = new RepositorioContasArray();
        repContasList = new RepositorioContasList();
        repContasMap = new RepositorioContasMap();

        //Iniciando uma instancia de cadastro de contas com cada tipo de repositorio
        contasArray = new CadastroContas(repContasArray);
        contasList = new CadastroContas(repContasList);
        contasMap = new CadastroContas(repContasMap);


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

    //Teste com repositorio de Contas Array
    @Test
    void cadastrarContaExistenteException() throws ContaExistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);

        assertThrows(
                ContaExistenteException.class,
                () -> {
                    contasArray.cadastrar(contaEspecialTest);
                });


    }

    @Test
    void cadastrarContaInexistenteException() throws ContaExistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);

        assertDoesNotThrow(
                () -> {
                    contasArray.procurar("47851");
                });


    }

    @Test
    void cadastrarContaArrayVazio() throws ClienteExistenteException {
        assertDoesNotThrow(
                () -> {
                    contasArray.cadastrar(contaImpostoTest);
                });

    }

    @Test
    public void creditarConta() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        contasArray.cadastrar(contaImpostoTest);

        ContaAbstrata c = contasArray.procurar("12542");


        assertEquals(0, c.getSaldo());

        contasArray.creditar("12542", 5015.14);

        assertEquals(5015.14, c.getSaldo());

    }

    @Test
    public void creditarContaInexistenteException() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        contasArray.cadastrar(contaImpostoTest);

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasArray.creditar("4445", 45153);
                });

    }

    @Test
    public void debitarConta() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        contasArray.cadastrar(contaImpostoTest);

        ContaAbstrata c = contasArray.procurar("12542");

        c.setSaldo(1425.25);


        assertEquals(1425.25, c.getSaldo());

        contasArray.debitar("12542", 425.25);

        assertEquals(1000, c.getSaldo());

    }

    @Test
    public void debitarContaSaldoInsuficiente() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        contasArray.cadastrar(contaImpostoTest);

        ContaAbstrata c = contasArray.procurar("12542");

        c.setSaldo(1425.25);


        assertEquals(1425.25, c.getSaldo());


        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    contasArray.debitar("12542", 1425.26);
                });

    }

    @Test
    public void debitarContaInexistenteException() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        contasArray.cadastrar(contaImpostoTest);

        ContaAbstrata c = contasArray.procurar("12542");

        c.setSaldo(1425.25);


        assertEquals(1425.25, c.getSaldo());

        contasArray.remover("12542");


        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasArray.debitar("12542", 1425.26);
                });

    }

    @Test
    void procurarContaExistenteArrayException() throws ClienteExistenteException, ContaExistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        assertDoesNotThrow(
                () -> {
                    contasArray.procurar("12542");
                });

    }

    @Test
    void procurarContaNaoExistenteArrayException() throws ClienteInexistenteException, ClienteExistenteException, ContaExistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasArray.procurar("41579");
                });

    }


    @Test
    void procurarContaExistenteArray() throws ClienteExistenteException, ClienteInexistenteException, ContaExistenteException, ContaInexistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        contasArray.cadastrar(contaImpostoTest);

        double saldo = contaImpostoTest.getSaldo();
        String numero = contaImpostoTest.getNumero();
        Cliente cliente = contaImpostoTest.getCliente();

        ContaAbstrata conta = contasArray.procurar("78546");

        assertEquals(saldo, conta.getSaldo());
        assertEquals(numero, conta.getNumero());
        assertEquals(cliente, conta.getCliente());

    }

    @Test
    void atualizarContaExistenteArrayException() throws ClienteInexistenteException, ClienteExistenteException, ContaExistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        contasArray.cadastrar(contaImpostoTest);

        ContaAbstrata conta = new ContaEspecial("12542", 5000, clientTest4);

        assertDoesNotThrow(
                () -> {
                    contasArray.atualizar(conta);
                });

    }

    @Test
    void atualizarContaNaoExistenteArrayException() throws ClienteInexistenteException, ClienteExistenteException, ContaExistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        contasArray.cadastrar(contaImpostoTest);

        ContaAbstrata conta = new ContaEspecial("11111", 5000, clientTest4);

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasArray.atualizar(conta);
                });

    }

    @Test
    void atualizarContaExistentePrimeiraPosicaoArray() throws ClienteExistenteException, ClienteInexistenteException, ContaExistenteException, ContaInexistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        contasArray.cadastrar(contaImpostoTest);

        ContaAbstrata conta = new ContaEspecial("12542", 4152, clientTest3);

        contasArray.atualizar(conta);

        ContaAbstrata conta2 = contasArray.procurar("12542");


        assertEquals(4152, conta2.getSaldo());
        assertEquals("12542", conta.getNumero());
        assertEquals(clientTest3, conta2.getCliente());

    }

    @Test
    void procurarContaRemovidaArrayException() throws ClienteExistenteException, ClienteInexistenteException, ContaExistenteException, ContaInexistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        contasArray.cadastrar(contaImpostoTest);


        contasArray.remover("47851");

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasArray.procurar("47851");
                });

    }

    @Test
    void verificaPrimeiraPosicaoVaziaArray() throws ClienteExistenteException, ClienteInexistenteException, ContaExistenteException, ContaInexistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        contasArray.cadastrar(contaImpostoTest);


        contasArray.remover(contaEspecialTest.getNumero());

        List<ContaAbstrata> c = contasArray.listarContas();

        assertNull(c.get(3));
        assertNull(c.get(4));
    }

    @Test
    void removerContaInexistenteArrayException() throws ContaExistenteException, ContaInexistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        contasArray.cadastrar(contaImpostoTest);


        contasArray.remover("12542");
        contasArray.remover("47851");

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasArray.remover("47851");
                });
    }

    @Test
    void buscarContaExistenteAposRemover() throws ClienteExistenteException, ClienteInexistenteException, ContaExistenteException, ContaInexistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        contasArray.cadastrar(contaImpostoTest);

        contasArray.remover("12542");
        contasArray.remover("78546");


        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasArray.remover("12542");
                });
        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasArray.remover("78546");
                });

        assertEquals(contaEspecialTest, contasArray.procurar("45826"));
        assertEquals(contaPoupancaTest, contasArray.procurar("47851"));
    }

    @Test
    void transferenciaDeConta() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {

        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        contasArray.cadastrar(contaImpostoTest);

        contaBonificadaTest.setSaldo(5000);

        contaPoupancaTest.setSaldo(1425);

        String contaOrigem = contaBonificadaTest.getNumero();
        String contaDestino = contaPoupancaTest.getNumero();

        contasArray.transferir(contaOrigem, contaDestino, 4000);

        assertEquals(1000, contaBonificadaTest.getSaldo());
        assertEquals(5425, contaPoupancaTest.getSaldo());

    }

    @Test
    void transferenciaDeContaSaldoInsuficiente() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {

        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        contasArray.cadastrar(contaImpostoTest);

        contaBonificadaTest.setSaldo(5000);

        contaPoupancaTest.setSaldo(1425);

        String contaOrigem = contaBonificadaTest.getNumero();
        String contaDestino = contaPoupancaTest.getNumero();

        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    contasArray.transferir(contaOrigem, contaDestino, 5001);
                });


    }

    @Test
    void transferenciaDeContaInexistenteException() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {

        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        contasArray.cadastrar(contaImpostoTest);

        contaBonificadaTest.setSaldo(5000);

        contaPoupancaTest.setSaldo(1425);

        String contaOrigem = contaBonificadaTest.getNumero();
        String contaDestino = contaPoupancaTest.getNumero();

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasArray.transferir("42563", contaDestino, 5001);
                });

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasArray.transferir(contaOrigem, "56314", 5001);
                });

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasArray.transferir("47159", "56314", 5001);
                });


    }

    @Test
    void testListarContasArray() throws ClienteExistenteException, ContaExistenteException {
        contasArray.cadastrar(contaBonificadaTest);
        contasArray.cadastrar(contaEspecialTest);
        contasArray.cadastrar(contaPoupancaTest);
        contasArray.cadastrar(contaImpostoTest);

        ContaAbstrata[] c = new ContaAbstrata[100];
        c[0] = contaBonificadaTest;
        c[1] = contaEspecialTest;
        c[2] = contaPoupancaTest;
        c[3] = contaImpostoTest;

        List<ContaAbstrata> asList = Arrays.asList(c);

        assertEquals(asList, contasArray.listarContas());

    }

    //Teste com repositorio de Contas List
    @Test
    void cadastrarContaExistenteListException() throws ContaExistenteException {
        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);

        assertThrows(
                ContaExistenteException.class,
                () -> {
                    contasList.cadastrar(contaEspecialTest);
                });


    }

    @Test
    void cadastrarContaInexistenteListException() throws ContaExistenteException {
        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);

        assertDoesNotThrow(
                () -> {
                    contasList.procurar("47851");
                });


    }

    @Test
    void cadastrarContaListVazio() throws ClienteExistenteException {
        assertDoesNotThrow(
                () -> {
                    contasList.cadastrar(contaImpostoTest);
                });

    }

    @Test
    public void creditarContaList() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {
        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);
        contasList.cadastrar(contaImpostoTest);

        ContaAbstrata c = contasList.procurar("12542");


        assertEquals(0, c.getSaldo());

        contasList.creditar("12542", 5015.14);

        assertEquals(5015.14, c.getSaldo());

    }

    @Test
    public void creditarContaInexistenteListException() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {
        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);
        contasList.cadastrar(contaImpostoTest);

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasList.creditar("4445", 45153);
                });

    }

    @Test
    public void debitarContaList() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {
        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);
        contasList.cadastrar(contaImpostoTest);

        ContaAbstrata c = contasList.procurar("12542");

        c.setSaldo(1425.25);


        assertEquals(1425.25, c.getSaldo());

        contasList.debitar("12542", 425.25);

        assertEquals(1000, c.getSaldo());

    }

    @Test
    public void debitarContaSaldoInsuficienteList() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {
        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);
        contasList.cadastrar(contaImpostoTest);

        ContaAbstrata c = contasList.procurar("12542");

        c.setSaldo(1425.25);


        assertEquals(1425.25, c.getSaldo());


        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    contasList.debitar("12542", 1425.26);
                });

    }

    @Test
    public void debitarContaInexistenteListException() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {
        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);
        contasList.cadastrar(contaImpostoTest);

        ContaAbstrata c = contasList.procurar("12542");

        c.setSaldo(1425.25);


        assertEquals(1425.25, c.getSaldo());

        contasList.remover("12542");


        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasList.debitar("12542", 1425.26);
                });

    }

    @Test
    void procurarContaExistenteListException() throws ClienteExistenteException, ContaExistenteException {
        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);
        assertDoesNotThrow(
                () -> {
                    contasList.procurar("12542");
                });

    }

    @Test
    void procurarContaNaoExistenteListException() throws ClienteInexistenteException, ClienteExistenteException, ContaExistenteException {
        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasList.procurar("41579");
                });

    }

    @Test
    void procurarContaExistenteList() throws ClienteExistenteException, ClienteInexistenteException, ContaExistenteException, ContaInexistenteException {
        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);
        contasList.cadastrar(contaImpostoTest);

        double saldo = contaImpostoTest.getSaldo();
        String numero = contaImpostoTest.getNumero();
        Cliente cliente = contaImpostoTest.getCliente();

        ContaAbstrata conta = contasList.procurar("78546");

        assertEquals(saldo, conta.getSaldo());
        assertEquals(numero, conta.getNumero());
        assertEquals(cliente, conta.getCliente());

    }

    @Test
    void atualizarContaExistenteListException() throws ClienteInexistenteException, ClienteExistenteException, ContaExistenteException {
        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);
        contasList.cadastrar(contaImpostoTest);

        ContaAbstrata conta = new ContaEspecial("12542", 5000, clientTest4);

        assertDoesNotThrow(
                () -> {
                    contasList.atualizar(conta);
                });

    }

    @Test
    void atualizarContaNaoExistenteListException() throws ClienteInexistenteException, ClienteExistenteException, ContaExistenteException {
        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);
        contasList.cadastrar(contaImpostoTest);

        ContaAbstrata conta = new ContaEspecial("11111", 5000, clientTest4);

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasList.atualizar(conta);
                });

    }

    @Test
    void atualizarContaExistentePrimeiraPosicaoList() throws ClienteExistenteException, ClienteInexistenteException, ContaExistenteException, ContaInexistenteException {
        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);
        contasList.cadastrar(contaImpostoTest);

        ContaAbstrata conta = new ContaEspecial("12542", 4152, clientTest3);

        contasList.atualizar(conta);

        ContaAbstrata conta2 = contasList.procurar("12542");


        assertEquals(4152, conta2.getSaldo());
        assertEquals("12542", conta.getNumero());
        assertEquals(clientTest3, conta2.getCliente());

    }

    @Test
    void procurarContaRemovidaListException() throws ClienteExistenteException, ClienteInexistenteException, ContaExistenteException, ContaInexistenteException {
        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);
        contasList.cadastrar(contaImpostoTest);


        contasList.remover("47851");

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasList.procurar("47851");
                });

    }

    @Test
    void removerContaInexistenteListException() throws ContaExistenteException, ContaInexistenteException {
        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);
        contasList.cadastrar(contaImpostoTest);


        contasList.remover("12542");
        contasList.remover("47851");

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasList.remover("47851");
                });
    }

    @Test
    void buscarContaExistenteAposRemoverList() throws ClienteExistenteException, ClienteInexistenteException, ContaExistenteException, ContaInexistenteException {
        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);
        contasList.cadastrar(contaImpostoTest);

        contasList.remover("12542");
        contasList.remover("78546");


        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasList.remover("12542");
                });
        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasList.remover("78546");
                });

        assertEquals(contaEspecialTest, contasList.procurar("45826"));
        assertEquals(contaPoupancaTest, contasList.procurar("47851"));
    }

    @Test
    void transferenciaDeContaList() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {

        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);
        contasList.cadastrar(contaImpostoTest);

        contaBonificadaTest.setSaldo(5000);

        contaPoupancaTest.setSaldo(1425);

        String contaOrigem = contaBonificadaTest.getNumero();
        String contaDestino = contaPoupancaTest.getNumero();

        contasList.transferir(contaOrigem, contaDestino, 4000);

        assertEquals(1000, contaBonificadaTest.getSaldo());
        assertEquals(5425, contaPoupancaTest.getSaldo());

    }

    @Test
    void transferenciaDeContaSaldoInsuficienteList() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {

        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);
        contasList.cadastrar(contaImpostoTest);

        contaBonificadaTest.setSaldo(5000);

        contaPoupancaTest.setSaldo(1425);

        String contaOrigem = contaBonificadaTest.getNumero();
        String contaDestino = contaPoupancaTest.getNumero();

        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    contasList.transferir(contaOrigem, contaDestino, 5001);
                });


    }

    @Test
    void transferenciaDeContaInexistenteListException() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {

        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);
        contasList.cadastrar(contaImpostoTest);

        contaBonificadaTest.setSaldo(5000);

        contaPoupancaTest.setSaldo(1425);

        String contaOrigem = contaBonificadaTest.getNumero();
        String contaDestino = contaPoupancaTest.getNumero();

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasList.transferir("42563", contaDestino, 5001);
                });

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasList.transferir(contaOrigem, "56314", 5001);
                });

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasList.transferir("47159", "56314", 5001);
                });


    }

    @Test
    void testListarContasList() throws ClienteExistenteException, ContaExistenteException {
        contasList.cadastrar(contaBonificadaTest);
        contasList.cadastrar(contaEspecialTest);
        contasList.cadastrar(contaPoupancaTest);
        contasList.cadastrar(contaImpostoTest);

        List<ContaAbstrata>c = new ArrayList<>(100);
        c.add(contaBonificadaTest);
        c.add(contaEspecialTest);
        c.add(contaPoupancaTest);
        c.add(contaImpostoTest);

        assertEquals(c, contasList.listarContas());

    }

    //Teste com repositorio de Contas Map
    @Test
    void cadastrarContaExistenteMapException() throws ContaExistenteException {
        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);

        assertThrows(
                ContaExistenteException.class,
                () -> {
                    contasMap.cadastrar(contaEspecialTest);
                });


    }

    @Test
    void cadastrarContaInexistenteMapException() throws ContaExistenteException {
        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);

        assertDoesNotThrow(
                () -> {
                    contasMap.procurar("47851");
                });


    }

    @Test
    void cadastrarContaMapVazio() throws ClienteExistenteException {
        assertDoesNotThrow(
                () -> {
                    contasMap.cadastrar(contaImpostoTest);
                });

    }

    @Test
    public void creditarContaMap() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {
        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);
        contasMap.cadastrar(contaImpostoTest);

        ContaAbstrata c =  contasMap.procurar("12542");


        assertEquals(0, c.getSaldo());

        contasMap.creditar("12542", 5015.14);

        assertEquals(5015.14, c.getSaldo());

    }

    @Test
    public void creditarContaInexistenteMapException() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {
        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);
        contasMap.cadastrar(contaImpostoTest);

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasList.creditar("4445", 45153);
                });

    }

    @Test
    public void debitarContaMap() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {
        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);
        contasMap.cadastrar(contaImpostoTest);

        ContaAbstrata c = contasMap.procurar("12542");

        c.setSaldo(1425.25);


        assertEquals(1425.25, c.getSaldo());

        contasMap.debitar("12542", 425.25);

        assertEquals(1000, c.getSaldo());

    }

    @Test
    public void debitarContaSaldoInsuficienteMap() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {
        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);
        contasMap.cadastrar(contaImpostoTest);

        ContaAbstrata c = contasMap.procurar("12542");

        c.setSaldo(1425.25);


        assertEquals(1425.25, c.getSaldo());


        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    contasMap.debitar("12542", 1425.26);
                });

    }

    @Test
    public void debitarContaInexistenteMapException() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {
        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);
        contasMap.cadastrar(contaImpostoTest);

        ContaAbstrata c = contasMap.procurar("12542");

        c.setSaldo(1425.25);


        assertEquals(1425.25, c.getSaldo());

        contasMap.remover("12542");


        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasMap.debitar("12542", 1425.26);
                });

    }

    @Test
    void procurarContaExistenteMapException() throws ClienteExistenteException, ContaExistenteException {
        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);

        assertDoesNotThrow(
                () -> {
                    contasMap.procurar("12542");
                });

    }

    @Test
    void procurarContaNaoExistenteMapException() throws ClienteInexistenteException, ClienteExistenteException, ContaExistenteException {
        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasMap.procurar("41579");
                });

    }

    @Test
    void procurarContaExistenteMap() throws ClienteExistenteException, ClienteInexistenteException, ContaExistenteException, ContaInexistenteException {
        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);
        contasMap.cadastrar(contaImpostoTest);

        double saldo = contaImpostoTest.getSaldo();
        String numero = contaImpostoTest.getNumero();
        Cliente cliente = contaImpostoTest.getCliente();

        ContaAbstrata conta = contasMap.procurar("78546");

        assertEquals(saldo, conta.getSaldo());
        assertEquals(numero, conta.getNumero());
        assertEquals(cliente, conta.getCliente());

    }

    @Test
    void atualizarContaExistenteMapException() throws ClienteInexistenteException, ClienteExistenteException, ContaExistenteException {
        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);
        contasMap.cadastrar(contaImpostoTest);

        ContaAbstrata conta = new ContaEspecial("12542", 5000, clientTest4);

        assertDoesNotThrow(
                () -> {
                    contasMap.atualizar(conta);
                });

    }

    @Test
    void atualizarContaNaoExistenteMapException() throws ClienteInexistenteException, ClienteExistenteException, ContaExistenteException {
        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);
        contasMap.cadastrar(contaImpostoTest);

        ContaAbstrata conta = new ContaEspecial("11111", 5000, clientTest4);

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasMap.atualizar(conta);
                });

    }

    @Test
    void atualizarContaExistentePrimeiraPosicaoMap() throws ClienteExistenteException, ClienteInexistenteException, ContaExistenteException, ContaInexistenteException {
        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);
        contasMap.cadastrar(contaImpostoTest);

        ContaAbstrata conta = new ContaEspecial("12542", 4152, clientTest3);

        contasMap.atualizar(conta);

        ContaAbstrata conta2 = contasMap.procurar("12542");


        assertEquals(4152, conta2.getSaldo());
        assertEquals("12542", conta.getNumero());
        assertEquals(clientTest3, conta2.getCliente());

    }

    @Test
    void procurarContaRemovidaMapException() throws ClienteExistenteException, ClienteInexistenteException, ContaExistenteException, ContaInexistenteException {
        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);
        contasMap.cadastrar(contaImpostoTest);


        contasMap.remover("47851");

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasMap.procurar("47851");
                });

    }

    @Test
    void removerContaInexistenteMapException() throws ContaExistenteException, ContaInexistenteException {
        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);
        contasMap.cadastrar(contaImpostoTest);


        contasMap.remover("12542");
        contasMap.remover("47851");

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasMap.remover("47851");
                });
    }

    @Test
    void buscarContaExistenteAposRemoverMap() throws ClienteExistenteException, ClienteInexistenteException, ContaExistenteException, ContaInexistenteException {
        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);
        contasMap.cadastrar(contaImpostoTest);

        contasMap.remover("12542");
        contasMap.remover("78546");


        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasMap.remover("12542");
                });
        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasMap.remover("78546");
                });

        assertEquals(contaEspecialTest, contasMap.procurar("45826"));
        assertEquals(contaPoupancaTest, contasMap.procurar("47851"));
    }

    @Test
    void transferenciaDeContaMap() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {

        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);
        contasMap.cadastrar(contaImpostoTest);

        contaBonificadaTest.setSaldo(5000);

        contaPoupancaTest.setSaldo(1425);

        String contaOrigem = contaBonificadaTest.getNumero();
        String contaDestino = contaPoupancaTest.getNumero();

        contasMap.transferir(contaOrigem, contaDestino, 4000);

        assertEquals(1000, contaBonificadaTest.getSaldo());
        assertEquals(5425, contaPoupancaTest.getSaldo());

    }

    @Test
    void transferenciaDeContaSaldoInsuficienteMap() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {

        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);
        contasMap.cadastrar(contaImpostoTest);

        contaBonificadaTest.setSaldo(5000);

        contaPoupancaTest.setSaldo(1425);

        String contaOrigem = contaBonificadaTest.getNumero();
        String contaDestino = contaPoupancaTest.getNumero();

        assertThrows(
                SaldoInsuficienteException.class,
                () -> {
                    contasMap.transferir(contaOrigem, contaDestino, 5001);
                });


    }

    @Test
    void transferenciaDeContaInexistenteMapException() throws SaldoInsuficienteException, ContaExistenteException, ContaInexistenteException {

        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);
        contasMap.cadastrar(contaImpostoTest);

        contaBonificadaTest.setSaldo(5000);

        contaPoupancaTest.setSaldo(1425);

        String contaOrigem = contaBonificadaTest.getNumero();
        String contaDestino = contaPoupancaTest.getNumero();

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasMap.transferir("42563", contaDestino, 5001);
                });

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasMap.transferir(contaOrigem, "56314", 5001);
                });

        assertThrows(
                ContaInexistenteException.class,
                () -> {
                    contasMap.transferir("47159", "56314", 5001);
                });


    }

    @Test
    void testListarContasMap() throws ClienteExistenteException, ContaExistenteException {
        contasMap.cadastrar(contaBonificadaTest);
        contasMap.cadastrar(contaEspecialTest);
        contasMap.cadastrar(contaPoupancaTest);
        contasMap.cadastrar(contaImpostoTest);

        Map<String,ContaAbstrata> c = new HashMap<String,ContaAbstrata>();
        c.put(contaBonificadaTest.getNumero(),contaBonificadaTest);
        c.put(contaEspecialTest.getNumero(),contaEspecialTest);
        c.put(contaPoupancaTest.getNumero(),contaPoupancaTest);
        c.put(contaImpostoTest.getNumero(),contaImpostoTest);

        assertEquals(new ArrayList<ContaAbstrata>(c.values()), contasMap.listarContas());

    }





}