package com.xpto.dao;

import com.xpto.model.ContaBancaria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaBancariaDAO {
    private Connection connection;

    public ContaBancariaDAO() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado", e);
        }
        this.connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "user", "password");
    }

    public void adicionarConta(ContaBancaria conta) throws SQLException {
        String sql = "INSERT INTO CONTAS_BANCARIAS (cliente_id, banco, agencia, numero_conta) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, conta.getClienteId());
            stmt.setString(2, conta.getBanco());
            stmt.setString(3, conta.getAgencia());
            stmt.setString(4, conta.getNumeroConta());
            stmt.executeUpdate();
        }
    }

    public List<ContaBancaria> listarContas() throws SQLException {
        List<ContaBancaria> contas = new ArrayList<>();
        String sql = "SELECT * FROM CONTAS_BANCARIAS";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ContaBancaria conta = new ContaBancaria();
                conta.setId(rs.getInt("id"));
                conta.setClienteId(rs.getInt("cliente_id"));
                conta.setBanco(rs.getString("banco"));
                conta.setAgencia(rs.getString("agencia"));
                conta.setNumeroConta(rs.getString("numero_conta"));
                contas.add(conta);
            }
        }
        return contas;
    }

    public void atualizarConta(ContaBancaria conta) throws SQLException {
        String sql = "UPDATE CONTAS_BANCARIAS SET cliente_id = ?, banco = ?, agencia = ?, numero_conta = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, conta.getClienteId());
            stmt.setString(2, conta.getBanco());
            stmt.setString(3, conta.getAgencia());
            stmt.setString(4, conta.getNumeroConta());
            stmt.setInt(5, conta.getId());
            stmt.executeUpdate();
        }
    }

    public void removerConta(int id) throws SQLException {
        String sql = "DELETE FROM CONTAS_BANCARIAS WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}