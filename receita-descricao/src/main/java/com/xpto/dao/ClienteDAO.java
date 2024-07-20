package com.xpto.dao;

import com.xpto.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Connection connection;

    public ClienteDAO() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado", e);
        }
        this.connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "user", "password");
    }

    public void adicionarCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO CLIENTES (nome, cpf, cnpj, endereco, tipo, telefone) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getCnpj());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getTipo());
            stmt.setString(6, cliente.getTelefone());
            stmt.executeUpdate();
        }
    }

    public List<Cliente> listarClientes() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTES";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setCnpj(rs.getString("cnpj"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTipo(rs.getString("tipo"));
                cliente.setTelefone(rs.getString("telefone"));
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    public void atualizarCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE CLIENTES SET nome = ?, cpf = ?, cnpj = ?, endereco = ?, tipo = ?, telefone = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getCnpj());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getTipo());
            stmt.setString(6, cliente.getTelefone());
            stmt.setInt(7, cliente.getId());
            stmt.executeUpdate();
        }
    }

    public void removerCliente(int id) throws SQLException {
        String sql = "DELETE FROM CLIENTES WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}