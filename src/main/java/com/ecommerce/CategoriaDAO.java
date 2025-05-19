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
        String SQL = "SELECT id, nome FROM categoria ORDER BY id";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");

                Categoria categoria = new Categoria(id, nome);
                categorias.add(categoria);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao obter todas as categorias: " + e.getMessage());
            e.printStackTrace();
        }
        return categorias;
    }

    public Categoria obter(int id) {
        String SQL = "SELECT id, nome FROM categoria WHERE id = ?";
        Categoria categoria = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int categoriaId = rs.getInt("id");
                    String nome = rs.getString("nome");

                    categoria = new Categoria(categoriaId, nome);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao obter categoria por ID (" + id + "): " + e.getMessage());
            e.printStackTrace();
        }
        return categoria;
    }

    public boolean inserir(String nome) {
        String SQL = "INSERT INTO categoria (nome) VALUES (?)";
        boolean sucesso = false;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, nome);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                sucesso = true;
                System.out.println("Categoria inserida com sucesso: " + nome);

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        System.out.println("ID gerado para '" + nome + "': " + generatedId);
                    }
                } catch (SQLException e) {
                    System.err.println("Erro ao obter ID gerado: " + e.getMessage());
                    e.printStackTrace();
                }

            } else {
                System.err.println("Falha ao inserir categoria: " + nome + ". Nenhuma linha afetada.");
            }

        } catch (SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                System.err.println("Erro: Categoria '" + nome + "' já existe.");
            } else {
                System.err.println("Erro inesperado ao inserir categoria '" + nome + "': " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sucesso;
    }

    public boolean atualizar(String nome, int id) {
        String SQL = "UPDATE categoria SET nome = ? WHERE id = ?";
        boolean sucesso = false;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, nome);
            pstmt.setInt(2, id);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                sucesso = true;
                System.out.println("Categoria com ID " + id + " atualizada para: " + nome);
            } else {
                System.out.println("Nenhuma categoria encontrada com ID " + id + " para atualizar.");
            }

        } catch (SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                System.err.println("Erro: O novo nome '" + nome + "' já existe para outra categoria.");
            } else {
                System.err.println("Erro inesperado ao atualizar categoria com ID " + id + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sucesso;
    }

    public boolean remover(int id) {
        String SQL = "DELETE FROM categoria WHERE id = ?";
        boolean sucesso = false;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                sucesso = true;
                System.out.println("Categoria com ID " + id + " removida com sucesso.");
            } else {
                System.out.println("Nenhuma categoria encontrada com ID " + id + " para remover.");
            }

        } catch (SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                System.err.println("Erro: Não é possível remover a categoria com ID " + id + " porque existem produtos associados a ela.");
            } else {
                System.err.println("Erro inesperado ao remover categoria com ID " + id + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sucesso;
    }
}