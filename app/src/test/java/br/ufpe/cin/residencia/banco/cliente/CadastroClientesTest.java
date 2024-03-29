package br.ufpe.cin.residencia.banco.cliente;

import br.ufpe.cin.residencia.banco.conta.CadastroContas;
import br.ufpe.cin.residencia.banco.conta.RepositorioContasArray;
import br.ufpe.cin.residencia.banco.conta.RepositorioContasList;
import br.ufpe.cin.residencia.banco.conta.RepositorioContasMap;
import br.ufpe.cin.residencia.banco.excecoes.ClienteExistenteException;
import br.ufpe.cin.residencia.banco.excecoes.ClienteInexistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CadastroClientesTest {

    RepositorioClientesArray repClientesArray;
    RepositorioClientesList repClientesList;
    RepositorioClientesMap repClientesMap;


    CadastroClientes clientesArray;
    CadastroClientes clientesList;
    CadastroClientes clientesMap;

    TipoCliente VIP = TipoCliente.VIP;
    TipoCliente CLASS = TipoCliente.CLASS;
    TipoCliente ESPECIAL = TipoCliente.ESPECIAL;

    Cliente clientTest1, clientTest2, clientTest3, clientTest4;

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


        //Instanciando um cliente para cada tipo de conta
        clientTest1 = new Cliente("77884460092", "Antonio", VIP);
        clientTest2 = new Cliente("98625741002", "Amanda", CLASS);
        clientTest3 = new Cliente("60068269099", "Victor", ESPECIAL);
        clientTest4 = new Cliente("78209599089", "Maria", VIP);

    }

    //Testes com repositorio Clientes Array

    @Test
    void cadastrarClienteArrayVazio() throws ClienteExistenteException {
        assertDoesNotThrow(
                () -> {
                    clientesArray.cadastrar(clientTest1);
                });

    }

    @Test
    void cadastrarClienteExistenteArray() throws ClienteExistenteException {
        clientesArray.cadastrar(clientTest1);
        assertThrows(
                ClienteExistenteException.class,
                () -> {
                    clientesArray.cadastrar(clientTest1);
                });

    }

    @Test
    void procurarClienteExistenteArrayException() throws ClienteExistenteException {
        clientesArray.cadastrar(clientTest1);
        clientesArray.cadastrar(clientTest2);
        clientesArray.cadastrar(clientTest3);
        clientesArray.cadastrar(clientTest4);
        assertDoesNotThrow(
                () -> {
                    clientesArray.procurar("98625741002");
                });

    }

    @Test
    void procurarClienteNaoExistenteArrayException() throws ClienteInexistenteException, ClienteExistenteException {
        clientesArray.cadastrar(clientTest1);
        clientesArray.cadastrar(clientTest2);
        clientesArray.cadastrar(clientTest3);
        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesArray.procurar("78209599089");
                });

    }

    @Test
    void procurarClienteExistenteArray() throws ClienteExistenteException, ClienteInexistenteException {
        clientesArray.cadastrar(clientTest1);
        clientesArray.cadastrar(clientTest2);
        clientesArray.cadastrar(clientTest3);
        clientesArray.cadastrar(clientTest4);

        Cliente c = clientesArray.procurar("98625741002");

        assertEquals("98625741002", c.getCpf());
        assertEquals("Amanda", c.getNome());
        assertEquals(CLASS, c.getTipo());

    }

    @Test
    void atualizarClienteExistenteArrayException() throws ClienteInexistenteException, ClienteExistenteException {
        clientesArray.cadastrar(clientTest1);
        clientesArray.cadastrar(clientTest2);

        Cliente c = new Cliente("98625741002", "Amanda Maria", VIP);
        assertDoesNotThrow(
                () -> {
                    clientesArray.atualizar(c);
                });

    }

    @Test
    void atualizarClienteNaoExistenteArrayException() throws ClienteInexistenteException, ClienteExistenteException {
        clientesArray.cadastrar(clientTest1);
        clientesArray.cadastrar(clientTest2);

        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesArray.atualizar(clientTest3);
                });

    }

    @Test
    void atualizarClienteExistenteArray() throws ClienteExistenteException, ClienteInexistenteException {
        clientesArray.cadastrar(clientTest1);
        clientesArray.cadastrar(clientTest2);

        Cliente c = new Cliente("98625741002", "Amanda Maria", VIP);

        clientesArray.atualizar(c);

        Cliente atualizado = clientesArray.procurar("98625741002");

        assertEquals("98625741002", atualizado.getCpf());
        assertEquals("Amanda Maria", atualizado.getNome());
        assertEquals(VIP, atualizado.getTipo());

    }

    @Test
    void procurarClienteDescadastradoArrayException() throws ClienteExistenteException, ClienteInexistenteException {
        clientesArray.cadastrar(clientTest1);
        String cpf = clientTest1.getCpf();

        clientesArray.descadastrar(cpf);

        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesArray.procurar(cpf);
                });

    }

    @Test
    void verificaPrimeiraPosicaoVaziaArray() throws ClienteExistenteException, ClienteInexistenteException {
        clientesArray.cadastrar(clientTest1);
        clientesArray.cadastrar(clientTest2);
        clientesArray.cadastrar(clientTest3);
        clientesArray.cadastrar(clientTest4);

        clientesArray.descadastrar(clientTest2.getCpf());

        List<Cliente> c = clientesArray.listarClientes();

        assertNull(c.get(3));
    }


    @Test
    void descadastrarClienteInexistenteArrayException(){
        String cpf = clientTest1.getCpf();

        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesArray.descadastrar(cpf);
                });
    }

    @Test
    void buscarClienteExistenteAposDescadastrado() throws ClienteExistenteException, ClienteInexistenteException {
        clientesArray.cadastrar(clientTest1);
        clientesArray.cadastrar(clientTest2);
        clientesArray.cadastrar(clientTest3);

        clientesArray.descadastrar(clientTest2.getCpf());
        clientesArray.descadastrar(clientTest3.getCpf());



        assertEquals("77884460092", clientTest1.getCpf());
        assertEquals("Antonio", clientTest1.getNome());
        assertEquals(VIP, clientTest1.getTipo());

        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesArray.descadastrar(clientTest2.getCpf());
                });
        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesArray.descadastrar(clientTest3.getCpf());
                });

        assertEquals(clientTest1, clientesArray.procurar(clientTest1.getCpf()));

    }

    @Test
    void testListarClientes() throws ClienteExistenteException {
        clientesArray.cadastrar(clientTest1);
        clientesArray.cadastrar(clientTest2);
        clientesArray.cadastrar(clientTest3);
        clientesArray.cadastrar(clientTest4);

        Cliente[] c = new Cliente[100];
        c[0] = clientTest1;
        c[1] = clientTest2;
        c[2] = clientTest3;
        c[3] = clientTest4;

        List<Cliente> asList = Arrays.asList(c);

        assertEquals(asList, clientesArray.listarClientes());

    }

    //Testes com repositorio Clientes List

    @Test
    void cadastrarClienteListVazio() throws ClienteExistenteException {
        assertDoesNotThrow(
                () -> {
                    clientesList.cadastrar(clientTest1);
                });

    }

    @Test
    void cadastrarClienteExistenteList() throws ClienteExistenteException {
        clientesList.cadastrar(clientTest1);
        assertThrows(
                ClienteExistenteException.class,
                () -> {
                    clientesList.cadastrar(clientTest1);
                });

    }

    @Test
    void procurarClienteExistenteListException() throws ClienteExistenteException {
        clientesList.cadastrar(clientTest1);
        clientesList.cadastrar(clientTest2);
        clientesList.cadastrar(clientTest3);
        clientesList.cadastrar(clientTest4);
        assertDoesNotThrow(
                () -> {
                    clientesList.procurar("98625741002");
                });

    }

    @Test
    void procurarClienteNaoExistenteListException() throws ClienteInexistenteException, ClienteExistenteException {
        clientesArray.cadastrar(clientTest1);
        clientesArray.cadastrar(clientTest2);
        clientesArray.cadastrar(clientTest3);
        clientesArray.cadastrar(clientTest4);
        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesList.procurar("60068269099");
                });

    }


    @Test
    void procurarClienteExistenteList() throws ClienteExistenteException, ClienteInexistenteException {
        clientesList.cadastrar(clientTest1);
        clientesList.cadastrar(clientTest2);
        clientesList.cadastrar(clientTest3);
        clientesList.cadastrar(clientTest4);

        Cliente c = clientesList.procurar("98625741002");

        assertEquals("98625741002", c.getCpf());
        assertEquals("Amanda", c.getNome());
        assertEquals(CLASS, c.getTipo());

    }

    @Test
    void atualizarClienteExistenteListException() throws ClienteInexistenteException, ClienteExistenteException {
        clientesList.cadastrar(clientTest1);
        clientesList.cadastrar(clientTest2);
        clientesList.cadastrar(clientTest3);
        clientesList.cadastrar(clientTest4);

        Cliente c = new Cliente("98625741002", "Amanda Maria", VIP);
        assertDoesNotThrow(
                () -> {
                    clientesList.atualizar(c);
                });

    }

    @Test
    void atualizarClienteNaoExistenteListException() throws ClienteInexistenteException, ClienteExistenteException {
        clientesArray.cadastrar(clientTest1);
        clientesArray.cadastrar(clientTest2);
        clientesArray.cadastrar(clientTest3);
        clientesArray.cadastrar(clientTest4);

        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesList.atualizar(clientTest3);
                });

    }

    @Test
    void atualizarClienteExistenteList() throws ClienteExistenteException, ClienteInexistenteException {
        clientesList.cadastrar(clientTest1);
        clientesList.cadastrar(clientTest2);
        clientesList.cadastrar(clientTest3);
        clientesList.cadastrar(clientTest4);

        Cliente c = new Cliente("98625741002", "Amanda Maria", VIP);

        clientesList.atualizar(c);

        Cliente atualizado = clientesList.procurar("98625741002");

        assertEquals("98625741002", atualizado.getCpf());
        assertEquals("Amanda Maria", atualizado.getNome());
        assertEquals(VIP, atualizado.getTipo());

    }

    @Test
    void procurarClienteDescadastradoListException() throws ClienteExistenteException, ClienteInexistenteException {
        clientesList.cadastrar(clientTest1);
        String cpf = clientTest1.getCpf();

        clientesList.descadastrar(cpf);

        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesList.procurar(cpf);
                });

    }

    @Test
    void descadastrarClienteInexistenteListException(){
        String cpf = clientTest1.getCpf();

        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesList.descadastrar(cpf);
                });
    }

    @Test
    void buscarClienteExistenteListAposDescadastrado() throws ClienteExistenteException, ClienteInexistenteException {
        clientesList.cadastrar(clientTest1);
        clientesList.cadastrar(clientTest2);
        clientesList.cadastrar(clientTest3);

        clientesList.descadastrar(clientTest2.getCpf());
        clientesList.descadastrar(clientTest3.getCpf());



        assertEquals("77884460092", clientTest1.getCpf());
        assertEquals("Antonio", clientTest1.getNome());
        assertEquals(VIP, clientTest1.getTipo());

        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesList.descadastrar(clientTest2.getCpf());
                });
        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesList.descadastrar(clientTest3.getCpf());
                });

        assertEquals(clientTest1, clientesList.procurar(clientTest1.getCpf()));

    }

    @Test
    void testListarClientesList() throws ClienteExistenteException {
        clientesList.cadastrar(clientTest1);
        clientesList.cadastrar(clientTest2);
        clientesList.cadastrar(clientTest3);
        clientesList.cadastrar(clientTest4);

        List<Cliente>c = new ArrayList<>(100);
        c.add(clientTest1);
        c.add(clientTest2);
        c.add(clientTest3);
        c.add(clientTest4);

        assertEquals(c, clientesList.listarClientes());

    }

    //Testes com repositorio Clientes Map

    @Test
    void cadastrarClienteMapVazio() throws ClienteExistenteException {
        assertDoesNotThrow(
                () -> {
                    clientesMap.cadastrar(clientTest1);
                });

    }

    @Test
    void cadastrarClienteExistenteMap() throws ClienteExistenteException {
        clientesMap.cadastrar(clientTest1);
        assertThrows(
                ClienteExistenteException.class,
                () -> {
                    clientesMap.cadastrar(clientTest1);
                });

    }

    @Test
    void procurarClienteExistenteMapException() throws ClienteExistenteException {
        clientesMap.cadastrar(clientTest1);
        clientesMap.cadastrar(clientTest2);
        assertDoesNotThrow(
                () -> {
                    clientesMap.procurar("98625741002");
                });

    }

    @Test
    void procurarClienteNaoExistenteMapException() throws ClienteInexistenteException, ClienteExistenteException {
        clientesMap.cadastrar(clientTest1);
        clientesMap.cadastrar(clientTest2);
        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesMap.procurar("60068269099");
                });

    }


    @Test
    void procurarClienteExistenteMap() throws ClienteExistenteException, ClienteInexistenteException {
        clientesMap.cadastrar(clientTest1);
        clientesMap.cadastrar(clientTest2);

        Cliente c = clientesMap.procurar("98625741002");

        assertEquals("98625741002", c.getCpf());
        assertEquals("Amanda", c.getNome());
        assertEquals(CLASS, c.getTipo());

    }

    @Test
    void atualizarClienteExistenteMapException() throws ClienteInexistenteException, ClienteExistenteException {
        clientesMap.cadastrar(clientTest1);
        clientesMap.cadastrar(clientTest2);

        Cliente c = new Cliente("98625741002", "Amanda Maria", VIP);
        assertDoesNotThrow(
                () -> {
                    clientesMap.atualizar(c);
                });

    }

    @Test
    void atualizarClienteNaoExistenteMapException() throws ClienteInexistenteException, ClienteExistenteException {
        clientesMap.cadastrar(clientTest1);
        clientesMap.cadastrar(clientTest2);

        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesMap.atualizar(clientTest3);
                });

    }

    @Test
    void atualizarClienteExistenteMap() throws ClienteExistenteException, ClienteInexistenteException {
        clientesMap.cadastrar(clientTest1);
        clientesMap.cadastrar(clientTest2);

        Cliente c = new Cliente("98625741002", "Amanda Maria", VIP);

        clientesMap.atualizar(c);

        Cliente atualizado = clientesMap.procurar("98625741002");

        assertEquals("98625741002", atualizado.getCpf());
        assertEquals("Amanda Maria", atualizado.getNome());
        assertEquals(VIP, atualizado.getTipo());

    }

    @Test
    void procurarClienteDescadastradoMapException() throws ClienteExistenteException, ClienteInexistenteException {
        clientesMap.cadastrar(clientTest1);
        String cpf = clientTest1.getCpf();

        clientesMap.descadastrar(cpf);

        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesMap.procurar(cpf);
                });

    }

    @Test
    void descadastrarClienteInexistenteMapException(){
        String cpf = clientTest1.getCpf();

        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesMap.descadastrar(cpf);
                });
    }

    @Test
    void buscarClienteExistenteMapAposDescadastrado() throws ClienteExistenteException, ClienteInexistenteException {
        clientesMap.cadastrar(clientTest1);
        clientesMap.cadastrar(clientTest2);
        clientesMap.cadastrar(clientTest3);

        clientesMap.descadastrar(clientTest2.getCpf());
        clientesMap.descadastrar(clientTest3.getCpf());



        assertEquals("77884460092", clientTest1.getCpf());
        assertEquals("Antonio", clientTest1.getNome());
        assertEquals(VIP, clientTest1.getTipo());

        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesMap.descadastrar(clientTest2.getCpf());
                });
        assertThrows(
                ClienteInexistenteException.class,
                () -> {
                    clientesMap.descadastrar(clientTest3.getCpf());
                });

        assertEquals(clientTest1, clientesMap.procurar(clientTest1.getCpf()));

    }

    @Test
    void testListarClientesMap() throws ClienteExistenteException {
        clientesMap.cadastrar(clientTest1);
        clientesMap.cadastrar(clientTest2);
        clientesMap.cadastrar(clientTest3);
        clientesMap.cadastrar(clientTest4);

        Map<String,Cliente> c = new HashMap<String,Cliente>();
        c.put(clientTest1.getCpf(), clientTest1);
        c.put(clientTest2.getCpf(), clientTest2);
        c.put(clientTest3.getCpf(), clientTest3);
        c.put(clientTest4.getCpf(), clientTest4);

        assertEquals(new ArrayList<Cliente>(c.values()), clientesMap.listarClientes());

    }



}