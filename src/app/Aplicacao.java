/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import controller.AplicacaoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.AplicacaoModel;

/**
 *
 * @author Rafael Rutsatz
 */
public class Aplicacao extends Application {

    public static Stage PRIMARY_STAGE;
    
   
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Ativa a impressão de alguns LOGS uteis.
        AplicacaoModel.LOG_SAIDA_ATIVO = true;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AplicacaoLayout.fxml"));

        Parent root = loader.load();

        AplicacaoController controller = loader.getController();

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Verificador de Equivalência - Trabalho de Teoria da Computação");
        primaryStage.setScene(scene);

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/img/app.png")));
        primaryStage.show();
        
        PRIMARY_STAGE = primaryStage;

    }

    public static void main(String[] args) {
        launch(args);
    }
}
