/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import app.ProcessaDados;
import controller.AplicacaoController;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

/**
 *
 * @author Rafael Rutsatz
 */
public class AplicacaoModel {

    private AplicacaoController controller;

    public Worker<String> worker;

    // Quando true, imprime na saida padrão os valores das operações.
    public static Boolean LOG_SAIDA_ATIVO = false;

    public AplicacaoModel(AplicacaoController controller) throws Exception {

        this.controller = controller;

        /**
         * Implementa Worker rodando por thread. (Está usando classe anônima
         * para rodar uma Task.)
         */
        worker = new Service<String>() {
            @Override
            protected Task<String> createTask() {

                return new Task<String>() {
                    @Override
                    protected String call() throws Exception {

                        /* Atualiza atributo message.
                        Está com bind na classe de View.
                         */
                        updateMessage("Processando...");

                        // Seta animação para status de intermitente.
                        updateProgress(-1, -1);

                        ProcessaDados processaDados = new ProcessaDados(controller);
                        Boolean sucesso;

                        try {
                            // Função responsável pelos calculos e demais atividades.
                            sucesso = processaDados.processar();
                        } catch (InterruptedException e) {

                            // Detecta se o usuário cancelou a execução.
                            if (isCancelled()) {
                                // Interrompe a execução.
                                // Se necessário, podemos usar a função cancelled
                                // para alguma ação.
                                return null;
                            }
                            throw new RuntimeException("Ocorreu um erro ao processar dados!");
                        }

                        if (sucesso) {
                            updateProgress(1, 1);
                            updateMessage("Concluído");
                        } else {
                            
                            controller.errorMessage.set("aaa");
                            throw new RuntimeException("Ocorreu um erro ao processar os dados!");

                        }
                        return "Finalizado";

                    }
                };

            }

            @Override
            /**
             * Função chamada ao completar a tarefa com sucesso.
             */
            protected void succeeded() {
                System.out.println("Completou com sucesso.");
            }

            @Override
            /**
             * Função chamada ao usuário cancelar a task.
             */
            protected void cancelled() {
                System.out.println("Cancelado pelo usuário.");
            }

            @Override
            /**
             * Função chamada quando a função que processa dados retorna false.
             */
            protected void failed() {
                Platform.runLater(() -> {
                    System.out.println("Ocorreu um erro na função processar.");
                    controller.errorMessage.set("Ocorreu um erro na função processar.");

                });
            }

        };

    }

}
