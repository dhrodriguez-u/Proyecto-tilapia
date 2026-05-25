package Proyect.adapters.ui;

import Proyect.entities.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LoginView {

    private StackPane root;

    public LoginView(MainView mainView) {

        root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right,#ECE9FF,#F8F8FF);");

        HBox container = new HBox();
        VBox izquierda = new VBox(25);
        izquierda.setPadding(new Insets(60));
        izquierda.setPrefWidth(450);
        izquierda.setAlignment(Pos.CENTER_LEFT);

        Label titulo = new Label("Sistema de análisis\nsensorial de tilapia");
        titulo.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #4B2CBF;");

        Label descripcion = new Label("Herramienta para evaluación\n" +
                "de calidad de carne de tilapia\n" +
                "basada en la Norma NTC 1443.");
        descripcion.setStyle("-fx-font-size: 18px; -fx-text-fill: #666666;");

        VBox puntos = new VBox(10);
        puntos.getChildren().addAll(
                crearTexto("✔ Evaluación guiada"),
                crearTexto("✔ Resultados confiables"),
                crearTexto("✔ Clasificación automática"),
                crearTexto("✔ Cumplimiento técnico")
        );

        izquierda.getChildren().addAll(titulo, descripcion, puntos);

        VBox loginCard = new VBox(20);
        loginCard.setAlignment(Pos.CENTER);
        loginCard.setPadding(new Insets(40));
        loginCard.setMaxWidth(400);
        loginCard.setStyle("-fx-background-color: white; -fx-background-radius: 25; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12),20,0,0,8);");

        Label loginTitulo = new Label("Iniciar sesión");
        loginTitulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #4B2CBF;");

        TextField correo = new TextField();
        correo.setPromptText("Correo electrónico");
        correo.setPrefHeight(45);
        correo.setStyle(estiloCampo());

        PasswordField password = new PasswordField();
        password.setPromptText("Contraseña");
        password.setPrefHeight(45);
        password.setStyle(estiloCampo());

        Button ingresar = new Button("Ingresar");
        ingresar.setPrefWidth(250);
        ingresar.setPrefHeight(50);
        ingresar.setStyle("-fx-background-color: linear-gradient(to right,#6C4DFF,#5B3CC4);" +
                "-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-background-radius: 15; -fx-cursor: hand;");

        Button registro = new Button("Crear cuenta");
        registro.setStyle("-fx-background-color: transparent; -fx-text-fill: #5B3CC4; -fx-font-size: 15px; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 10; -fx-padding: 8 14 8 14;");

        // --- LÓGICA DE INGRESO (OPTMIZADA) ---
        ingresar.setOnAction(e -> {
            String correoText = correo.getText();
            String passText = password.getText();

            // Llamamos al repositorio usando el nuevo método eficiente
            Usuario usuario = mainView.getUsuarioRepositorio().obtenerPorCorreo(correoText);

            if (usuario != null && usuario.getPassword().equals(passText)) {
                mainView.setUsuarioActual(usuario);
                mainView.mostrarDashboard();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Inicio de sesión");
                alerta.setHeaderText("Credenciales incorrectas");
                alerta.setContentText("El correo o contraseña no coinciden o el usuario no existe.");
                alerta.showAndWait();
            }
        });

        registro.setOnAction(e -> mainView.mostrarRegistro());

        loginCard.getChildren().addAll(loginTitulo, correo, password, ingresar, registro);
        container.getChildren().addAll(izquierda, loginCard);
        container.setAlignment(Pos.CENTER);
        root.getChildren().add(container);
    }

    private Label crearTexto(String texto){
        Label label = new Label(texto);
        label.setStyle("-fx-font-size: 16px; -fx-text-fill: #444444;");
        return label;
    }

    private String estiloCampo(){
        return "-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #D6D6F5; -fx-padding: 0 15 0 15; -fx-font-size: 15px; -fx-background-color: #FAFAFF;";
    }

    public Parent getView() {
        return root;
    }
}