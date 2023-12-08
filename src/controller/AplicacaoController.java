/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import app.Aplicacao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.AplicacaoModel;

/**
 * FXML Controller class
 *
 * @author Rafael Rutsatz
 */
public class AplicacaoController {

    @FXML
    public Label lblErro;

    @FXML
    public Button btVerificarEquivalencia;

    @FXML
    public Button btCancelar;

    @FXML
    public Button btAjuda;

    @FXML
    public Button btSair;

    @FXML
    public Button btImportar1;

    @FXML
    public Button btImportar2;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private TextArea entradaProgramaUm;

    @FXML
    private TextArea entradaProgramaDois;

    @FXML
    public TextArea resultadoPasso1Programa1;

    @FXML
    public TextArea resultadoPasso1Programa2;

    @FXML
    public TextArea resultadoPasso2Programa1;

    @FXML
    public TextArea resultadoPasso2Programa2;

    @FXML
    public TextArea resultadoPasso3Programa1;

    @FXML
    public TextArea resultadoPasso3Programa2;

    @FXML
    public TextArea resultadoPasso4;

    @FXML
    public Tab tabPasso3;
    @FXML
    public Tab tabPasso4;


    AplicacaoModel model;

    // Mostra a mensagem do botão verificar equivalência.
    private StringProperty strStatus = new SimpleStringProperty("Verificar Equivalência");

    // String do programa 1
    public StringProperty programaUmProperty = new SimpleStringProperty();
    public StringProperty programaDoisProperty = new SimpleStringProperty();

    public StringProperty errorMessage = new SimpleStringProperty();

    public AplicacaoController() throws Exception {
        this.model = new AplicacaoModel(this);
    }

    /**
     * Inicializa o controller. Realiza os binds com o view.
     */
    public void initialize() {

        // Bind da variável com o text area.
        programaUmProperty.bindBidirectional(entradaProgramaUm.textProperty());
        programaDoisProperty.bindBidirectional(entradaProgramaDois.textProperty());

        addTransition(btVerificarEquivalencia);
        addTransition(btCancelar);
        addTransition(btAjuda);
        addTransition(btSair);
        addTransition(btImportar1);
        addTransition(btImportar2);

        btVerificarEquivalencia.setGraphic(new ImageView(new Image(getClass()
                .getResourceAsStream("/resources/img/button_fastforward_blue.png"), 32, 32, true, true)));

        btCancelar.setGraphic(new ImageView(new Image(getClass()
                .getResourceAsStream("/resources/img/button_delete_blue.png"), 32, 32, true, true)));

        btAjuda.setGraphic(new ImageView(new Image(getClass()
                .getResourceAsStream("/resources/img/question.png"), 32, 32, true, true)));

        btSair.setGraphic(new ImageView(new Image(getClass()
                .getResourceAsStream("/resources/img/gnome_panel_force_quit.png"), 32, 32, true, true)));

        try {
            model = new AplicacaoModel(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // status da minha Thread.
        final ReadOnlyObjectProperty<Worker.State> stateProperty = model.worker.stateProperty();

        // Faz alguns bindings com a view.
        lblErro.textProperty().bind(errorMessage);

        progressIndicator.progressProperty().bind(model.worker.progressProperty());

        progressIndicator.visibleProperty().bind(
                stateProperty.isEqualTo(Worker.State.RUNNING)
                        .or(stateProperty.isEqualTo(Worker.State.SUCCEEDED)));

        strStatus.bind(Bindings.createStringBinding(() -> {
            if (stateProperty.isEqualTo(Worker.State.RUNNING).get()) {
                return "Processando...";
            }
            return "Verificar Equivalência";
        }, stateProperty));
        btVerificarEquivalencia.textProperty().bind(strStatus);

        btVerificarEquivalencia.disableProperty().bind(stateProperty.isEqualTo(Worker.State.RUNNING));
        btCancelar.disableProperty().bind(stateProperty.isNotEqualTo(Worker.State.RUNNING));

        // Listeners.
        inicializarEventos();
    }

    /**
     * Adiciona listeners.
     */
    private void inicializarEventos() {
        btVerificarEquivalencia.setOnAction(actionEvent -> {

            //Inicializa a execução da Thread.
            ((Service) model.worker).restart();

        });

        btCancelar.setOnAction(actionEvent -> {
            // Cancela a execução da Thread.
            model.worker.cancel();
        });

        btAjuda.setOnAction(actionEvent -> {
            abrirAjuda();
        });

        btSair.setOnAction(actionEvent -> {
            Platform.exit();
        });

        btImportar1.setOnAction(actionEvent -> {
            abrirArquivo(actionEvent);
        });

        btImportar2.setOnAction(actionEvent -> {
            abrirArquivo(actionEvent);
        });
    }

    private void abrirArquivo(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./docs"));
        fileChooser.setTitle("Importar Programa");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Arquivos de Texto", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(Aplicacao.PRIMARY_STAGE);
        if (selectedFile != null) {
            String txt = readFile(selectedFile);
            if (e.getSource() == btImportar1) {
                programaUmProperty.setValue(txt);
            } else if (e.getSource() == btImportar2) {
                programaDoisProperty.setValue(txt);
            }
        }
    }

    private String readFile(File file) {
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text + "\n");
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AplicacaoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AplicacaoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(AplicacaoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return stringBuffer.toString();
    }

    private void abrirAjuda() {

        Stage stage = new Stage();

        String message = "\n\n\n\n\n"
                + ""
                + "\t EQUIVALÊNCIA FORTE DE PROGRAMAS "
                + "\n\n "
                + "\t Trabalho de Teoria da Computação "
                + "\n\n\n"
                + "\t\t\tAlunos\n "
                + "\t\tRafael Fernando Rutsatz\n"
                + "\t\tRodrigo Lucke\n"
                + "\t\tTaina\n"
                + "\n\n"
                + "the Book of Genesis, closing with a holiday wish from "
                + "Commander Borman: \"We close with good night, good luck, "
                + "a Merry Christmas, and God bless all of you -- all of "
                + "you on the good Earth.\""
                + "Earthrise at Christmas: "
                + "[Forty] years ago this Christmas, a turbulent world "
                + "looked to the heavens for a unique view of our home "
                + "planet. This photo of Earthrise over the lunar horizon "
                + "was taken by the Apollo 8 crew in December 1968, showing "
                + "Earth for the first time as it appears from deep space. "
                + "Astronauts Frank Borman, Jim Lovell and William Anders "
                + "had become the first humans to leave Earth orbit, "
                + "entering lunar orbit on Christmas Eve. In a historic live "
                + "broadcast that night, the crew took turns reading from "
                + "the Book of Genesis, closing with a holiday wish from "
                + "Commander Borman: \"We close with good night, good luck, "
                + "a Merry Christmas, and God bless all of you -- all of "
                + "you on the good Earth.\""
                + "Earthrise at Christmas: "
                + "[Forty] years ago this Christmas, a turbulent world "
                + "looked to the heavens for a unique view of our home "
                + "planet. This photo of Earthrise over the lunar horizon "
                + "was taken by the Apollo 8 crew in December 1968, showing "
                + "Earth for the first time as it appears from deep space. "
                + "Astronauts Frank Borman, Jim Lovell and William Anders "
                + "had become the first humans to leave Earth orbit, "
                + "entering lunar orbit on Christmas Eve. In a historic live "
                + "broadcast that night, the crew took turns reading from "
                + "the Book of Genesis, closing with a holiday wish from "
                + "Commander Borman: \"We close with good night, good luck, "
                + "a Merry Christmas, and God bless all of you -- all of "
                + "you on the good Earth.\"";

        // Reference to the Text
        Text textRef = new Text(message);
        textRef.setLayoutY(100);
        textRef.setTextOrigin(VPos.TOP);
        textRef.setTextAlignment(TextAlignment.JUSTIFY);
        textRef.setWrappingWidth(760);
        textRef.setFill(Color.rgb(187, 195, 107));
        textRef.setFont(Font.font("SansSerif", FontWeight.BOLD, 24));

        // Provides the animated scrolling behavior for the text
        TranslateTransition transTransition = new TranslateTransition(new Duration(75000), textRef);
        transTransition.setToY(-820);
        transTransition.setInterpolator(Interpolator.LINEAR);
        transTransition.setCycleCount(Timeline.INDEFINITE);

        // Create a ScrollPane containing the text
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setLayoutX(10);
        scrollPane.setLayoutY(10);
        scrollPane.setPrefWidth(780);
        scrollPane.setPrefHeight(500);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);
        scrollPane.setContent(textRef);

        // Combine ImageView and Group
        Group group = new Group(scrollPane);

        ButtonBar buttonBar = new ButtonBar();
//        hBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBar.setPadding(new Insets(10));

        Button btContinuar = new Button("Continuar", new ImageView(new Image(getClass()
                .getResourceAsStream("/resources/img/button_play_blue.png"), 32, 32, true, true)));
        btContinuar.setOnAction(actionEvent -> {
            transTransition.play();
        });
        btContinuar.disableProperty().bind(transTransition.statusProperty()
                .isEqualTo(Animation.Status.RUNNING));

        Button btParar = new Button("Pausar", new ImageView(new Image(getClass()
                .getResourceAsStream("/resources/img/button_pause_blue.png"), 32, 32, true, true)));
        btParar.setOnAction(actionEvent -> {
            transTransition.pause();
        });
        btParar.disableProperty().bind(transTransition.statusProperty()
                .isEqualTo(Animation.Status.PAUSED));

        Button btSair = new Button("Sair", new ImageView(new Image(getClass()
                .getResourceAsStream("/resources/img/gnome_panel_force_quit.png"), 32, 32, true, true)));
        btSair.setOnAction(actionEvent -> {
            stage.close();
        });

        buttonBar.getButtons().addAll(btContinuar, btParar, btSair);

        addTransition(btContinuar);
        addTransition(btParar);
        addTransition(btSair);

        VBox vBox = new VBox(group, buttonBar);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(10));

        Scene scene = new Scene(vBox, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/view/aplicacao.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Ajuda");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        // Start the text animation
        transTransition.play();

    }

    private void addTransition(Button button) {
        button.setOnMouseEntered(me -> {
            ScaleTransition transition = new ScaleTransition(Duration.millis(300), button);
            transition.setToX(1.1);
            transition.setToY(1.1);
            transition.play();
        });

        button.setOnMouseExited(me -> {
            ScaleTransition transition = new ScaleTransition(Duration.millis(300), button);
            transition.setToX(1.0);
            transition.setToY(1.0);
            transition.play();
        });
    }
}
