package br.ufpe.cin.residencia.banco.cliente;

import br.ufpe.cin.residencia.banco.conta.ContaAbstrata;
import br.ufpe.cin.residencia.banco.excecoes.ClienteInexistenteException;

import java.util.ArrayList;
import java.util.List;

public class RepositorioClientesList implements IRepositorioClientes {
    private List<Cliente> clientes;
    private final static int tamCache = 100;

    public RepositorioClientesList() {
        clientes = new ArrayList<>(tamCache);
    }

    @Override
    public void atualizar(Cliente c) throws ClienteInexistenteException {
        int i = procurarIndice(c.getCpf());
        if (i != -1) {
            clientes.remove(i);
            clientes.add(c);
        } else {
            throw new ClienteInexistenteException();
        }
    }

    @Override
    public boolean existe(String cpf) {
        boolean resp = false;

        int i = this.procurarIndice(cpf);
        if (i != -1) {
            resp = true;
        }

        return resp;
    }

    @Override
    public void inserir(Cliente c) {
        clientes.add(c);
    }

    @Override
    public Cliente procurar(String cpf) throws ClienteInexistenteException {
        if (existe(cpf)) {
            return clientes.get(this.procurarIndice(cpf));
        } else {
            throw new ClienteInexistenteException();
        }
    }

    private int procurarIndice(String cpf) {
        int ind = -1;
        for (Cliente c : clientes) {
            if ((c.getCpf()).equals(cpf)) {
                return clientes.indexOf(c);
            }
        }
        return ind;
    }

    @Override
    public void remover(String cpf) throws ClienteInexistenteException {
        if (existe(cpf)) {
            clientes.remove(this.procurarIndice(cpf));
        } else {
            throw new ClienteInexistenteException();
        }
    }

    @Override
    public List<Cliente> listar() {
        return clientes;
    }
}