package com.ecommerce;

import java.util.List;

public class TesteCategoriaDAO {

    public static void main(String[] args) {
        CategoriaDAO dao = new CategoriaDAO();

        // criando
        System.out.println("Testando criação");
        boolean inseriu1 = dao.inserir("Eletrônicos");
        System.out.println("Criação Eletrônicos: " + (inseriu1 ? "sucesso" : "falha"));

        boolean inseriu2 = dao.inserir("Roupas");
        System.out.println("Criação Roupas: " + (inseriu2 ? "sucesso" : "falha"));

        boolean inseriu3 = dao.inserir("Alimentos");
        System.out.println("Criação Alimentos: " + (inseriu3 ? "sucesso" : "falha"));

        // lendo
        System.out.println("\nLendo todas as categorias");
        List<Categoria> categorias = dao.obterTodas();
        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria cadastrada.");
        } else {
            for (Categoria c : categorias) {
                System.out.println("ID: " + c.getId() + ", Descrição: " + c.getDescricao());
            }
        }

        // atualizando
        if (!categorias.isEmpty()) {
            int idParaAtualizar = categorias.get(0).getId();
            System.out.println("\nTestando atualização");
            boolean atualizou = dao.atualizar("Eletrônicos Atualizados", idParaAtualizar);
            System.out.println("Atualização da categoria com ID " + idParaAtualizar + ": " + (atualizou ? "sucesso" : "falha"));

            // Mostrar categoria atualizada
            Categoria cAtualizada = dao.obter(idParaAtualizar);
            if (cAtualizada != null) {
                System.out.println("Categoria atualizada: ID: " + cAtualizada.getId() + ", Descrição: " + cAtualizada.getDescricao());
            }
        } else {
            System.out.println("\nNenhuma categoria para testar atualização.");
        }

        // removendo
        if (!categorias.isEmpty()) {
            int idParaRemover = categorias.get(categorias.size() - 1).getId();
            System.out.println("\nTestando remoção");
            boolean removeu = dao.remover(idParaRemover);
            System.out.println("Remoção da categoria com ID " + idParaRemover + ": " + (removeu ? "sucesso" : "falha"));
        } else {
            System.out.println("\nNenhuma categoria para testar remoção.");
        }

        // confirmando que removeu
        System.out.println("\n Listagem final de categorias");
        categorias = dao.obterTodas();
        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria cadastrada.");
        } else {
            for (Categoria c : categorias) {
                System.out.println("ID: " + c.getId() + ", Descrição: " + c.getDescricao());
            }
        }
    }
}
