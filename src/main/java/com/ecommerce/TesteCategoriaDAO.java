package com.ecommerce;
import java.util.List;

public class TesteCategoriaDAO {

    public static void main(String[] args) {

        CategoriaDAO categoriaDAO = new CategoriaDAO();

        System.out.println("Testando Inserção");
        boolean insercao1 = categoriaDAO.inserir("Roupas");
        System.out.println("Inserção 'Roupas' bem sucedida? " + insercao1);

        boolean insercao2 = categoriaDAO.inserir("Calçados");
        System.out.println("Inserção 'Calçados' bem sucedida? " + insercao2);

        boolean insercao3 = categoriaDAO.inserir("Roupas");
        System.out.println("Inserção 'Roupas' (duplicado) bem sucedida? " + insercao3);

        System.out.println("Testando Obter Todas");
        List<Categoria> todasCategorias = categoriaDAO.obterTodas();
        if (todasCategorias.isEmpty()) {
            System.out.println("Nenhuma categoria encontrada.");
        } else {
            System.out.println("Categorias encontradas:");
            for (Categoria cat : todasCategorias) {
                System.out.println(cat);
            }
        }

        System.out.println("Testando Obter por ID");
        int idParaBuscar = 1;
        Categoria categoriaPorId = categoriaDAO.obter(idParaBuscar);
        if (categoriaPorId != null) {
            System.out.println("Categoria encontrada com ID " + idParaBuscar + ": " + categoriaPorId);
        } else {
            System.out.println("Nenhuma categoria encontrada com ID " + idParaBuscar);
        }

        System.out.println("Testando Obter ID Inexistente");
        int idInexistente = 9999;
        Categoria categoriaInexistente = categoriaDAO.obter(idInexistente);
        if (categoriaInexistente != null) {
            System.out.println("Erro: Encontrou categoria com ID " + idInexistente + ": " + categoriaInexistente);
        } else {
            System.out.println("Nenhuma categoria encontrada com ID " + idInexistente + " (Comportamento esperado)");
        }


        System.out.println("Testando Atualização");
        int idParaAtualizar = 1;
        boolean atualizacao = categoriaDAO.atualizar("Vestuário", idParaAtualizar);
        System.out.println("Atualização da categoria ID " + idParaAtualizar + " bem sucedida? " + atualizacao);

        System.out.println("Categorias Após Atualização");
        todasCategorias = categoriaDAO.obterTodas();
        if (todasCategorias.isEmpty()) {
            System.out.println("Nenhuma categoria encontrada.");
        } else {
            System.out.println("Categorias encontradas:");
            for (Categoria cat : todasCategorias) {
                System.out.println(cat);
            }
        }

        System.out.println("Testando Atualização de ID Inexistente");
        int idParaAtualizarInexistente = 9998;
        boolean atualizacaoInexistente = categoriaDAO.atualizar("Teste Inexistente", idParaAtualizarInexistente);
        System.out.println("Atualização da categoria ID " + idParaAtualizarInexistente + " bem sucedida? " + atualizacaoInexistente);

        System.out.println("Testando Remoção");
        int idParaRemover = 2;
        boolean remocao = categoriaDAO.remover(idParaRemover);
        System.out.println("Remoção da categoria ID " + idParaRemover + " bem sucedida? " + remocao);

        System.out.println("Categorias Após Remoção");
        todasCategorias = categoriaDAO.obterTodas();
        if (todasCategorias.isEmpty()) {
            System.out.println("Nenhuma categoria encontrada.");
        } else {
            System.out.println("Categorias encontradas:");
            for (Categoria cat : todasCategorias) {
                System.out.println(cat);
            }
        }

        System.out.println("Testando Remoção de ID Inexistente");
        int idParaRemoverInexistente = 9997;
        boolean remocaoInexistente = categoriaDAO.remover(idParaRemoverInexistente);
        System.out.println("Remoção da categoria ID " + idParaRemoverInexistente + " bem sucedida? " + remocaoInexistente);
    }
}