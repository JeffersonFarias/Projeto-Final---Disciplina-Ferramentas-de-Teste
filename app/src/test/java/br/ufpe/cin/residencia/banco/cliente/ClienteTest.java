package br.ufpe.cin.residencia.banco.cliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    Cliente clientTest1;

    TipoCliente VIP = TipoCliente.VIP;
    TipoCliente CLASS = TipoCliente.CLASS;
    TipoCliente ESPECIAL = TipoCliente.ESPECIAL;

    @BeforeEach
    void setup(){
        clientTest1 = new Cliente("77884460092", "Antonio", VIP);
    }

    @Test
    void getCpf() {
        assertEquals("77884460092", clientTest1.getCpf());
    }

    @Test
    void getNome() {
        assertEquals("Antonio", clientTest1.getNome());
    }

    @Test
    void getTipo() {
        assertEquals(VIP, clientTest1.getTipo());
    }

    @Test
    void setCpf() {
        String cpf = "44651326030";
        clientTest1.setCpf(cpf);

        assertEquals(cpf, clientTest1.getCpf());
    }

    @Test
    void setNome() {
        String nome = "Jorge";
        clientTest1.setNome(nome);

        assertEquals(nome, clientTest1.getNome());
    }

    @Test
    void setTipo() {
        clientTest1.setTipo(ESPECIAL);

        assertEquals(ESPECIAL, clientTest1.getTipo());
    }
}