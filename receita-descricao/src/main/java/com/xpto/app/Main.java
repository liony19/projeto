package com.xpto.app;

import com.xpto.dao.ClienteDAO;
import com.xpto.dao.ContaBancariaDAO;
import com.xpto.model.Cliente;
import com.xpto.model.ContaBancaria;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            ContaBancariaDAO contaBancariaDAO = new ContaBancariaDAO();
            
            // Adicionando um cliente
            Cliente cliente = new Cliente();
            cliente.setNome("João Silva");
            cliente.setCpf("123.456.789-00");
            cliente.setCnpj(null);
            cliente.setEndereco("Rua das Flores, 123");
            cliente.setTipo("PF");
            cliente.setTelefone("98765-4321");
            clienteDAO.adicionarCliente(cliente);
            
            // Listando clientes
            List<Cliente> clientes = clienteDAO.listarClientes();
            for (Cliente c : clientes) {
                System.out.println("Cliente: " + c.getNome() + ", Tipo: " + c.getTipo());
            }

            // Adicionando uma conta bancária
            ContaBancaria conta = new ContaBancaria();
            conta.setClienteId(clientes.get(0).getId()); // Usando o primeiro cliente adicionado
            conta.setBanco("Banco XPTO");
            conta.setAgencia("1234");
            conta.setNumeroConta("56789-0");
            contaBancariaDAO.adicionarConta(conta);

            // Listando contas bancárias
            List<ContaBancaria> contas = contaBancariaDAO.listarContas();
            for (ContaBancaria c : contas) {
                System.out.println("Conta: " + c.getNumeroConta() + ", Banco: " + c.getBanco());
            }

            // Atualizando um cliente
            cliente.setNome("João da Silva");
            clienteDAO.atualizarCliente(cliente);

            // Atualizando uma conta bancária
            conta.setBanco("Banco ABC");
            contaBancariaDAO.atualizarConta(conta);

            // Removendo uma conta bancária
            contaBancariaDAO.removerConta(conta.getId());

            // Removendo um cliente
            clienteDAO.removerCliente(cliente.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}