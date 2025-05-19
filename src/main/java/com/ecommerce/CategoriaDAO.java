package com.ecommerce;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ecommerce";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public List<Categoria> obterTodas() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id, descricao FROM categoria";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String descricao = rs.getString("descricao");
                categorias.add(new Categoria(id, descricao));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }

    public Categoria obter(int id) {
        String sql = "SELECT id, descricao FROM categoria WHERE id = ?";
        Categoria categoria = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String descricao = rs.getString("descricao");
                    categoria = new Categoria(id, descricao);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoria;
    }

    public boolean inserir(String descricao) {
        String sql = "INSERT INTO categoria (descricao) VALUES (?)";
        boolean sucesso = false;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, descricao);
            int linhasAfetadas = stmt.executeUpdate();
            sucesso = linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sucesso;
    }

    public boolean atualizar(String descricao, int id) {
        String sql = "UPDATE categoria SET descricao = ? WHERE id = ?";
        boolean sucesso = false;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, descricao);
            stmt.setInt(2, id);
            int linhasAfetadas = stmt.executeUpdate();
            sucesso = linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sucesso;
    }

    public boolean remover(int id) {
        String sql = "DELETE FROM categoria WHERE id = ?";
        boolean sucesso = false;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            sucesso = linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sucesso;
    }
}
