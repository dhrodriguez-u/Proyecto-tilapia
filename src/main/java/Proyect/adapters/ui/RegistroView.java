package Proyect.adapters.ui;

import Proyect.entities.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RegistroView {

    private StackPane root;

    public Parent getView() { return root; }

    public RegistroView(MainView mainView) {
        root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right,#ECE9FF,#F7F5FF);");

        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        VBox izquierda = new VBox(25);
        izquierda.setAlignment(Pos.CENTER_LEFT);
        izquierda.setPadding(new Insets(60));
        izquierda.setPrefWidth(500);

        Label tituloSistema = new Label("Registro de Usuarios\nPlataforma Sensorial");
        tituloSistema.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #4B2CBF;");

        Label descripcion = new Label("Cree una cuenta seleccionando su rol\npara interactuar con las herramientas\nde la Norma NTC 1443.");
        descripcion.setStyle("-fx-font-size: 16px; -fx-text-fill: #666666;");
        izquierda.getChildren().addAll(tituloSistema, descripcion);

        VBox card = new VBox(20);
        card.setPadding(new Insets(40));
        card.setPrefWidth(400);
        card.setMaxHeight(550);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 20, 0, 0, 8);");

        Label titulo = new Label("Crear Cuenta");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2D2D2D;");

        TextField txtNombre = crearCampo("Nombre completo");
        TextField txtCorreo = crearCampo("Correo electrónico");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña de acceso");
        txtPassword.setPrefHeight(45);
        txtPassword.setStyle(estiloCampo());

        // COMBOBOX PARA SELECCIÓN DE ROL
        ComboBox<String> cbRol = new ComboBox<>();
        cbRol.getItems().addAll("Experto Evaluador", "Piscicultor", "Investigador");
        cbRol.setValue("Experto Evaluador"); // Valor inicial por defecto
        cbRol.setMaxWidth(Double.MAX_VALUE);
        cbRol.setPrefHeight(45);
        cbRol.setStyle("-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #D6D6F5; -fx-padding: 0 5; -fx-font-size: 15px; -fx-background-color: #FAFAFF;");

        Button registrar = new Button("Registrarse");
        registrar.setMaxWidth(Double.MAX_VALUE);
        registrar.setPrefHeight(45);
        registrar.setStyle("-fx-background-color: #4B2CBF; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 12; -fx-cursor: hand;");

        Button volver = new Button("Volver al Login");
        volver.setMaxWidth(Double.MAX_VALUE);
        volver.setStyle("-fx-background-color: transparent; -fx-text-fill: #7F8C8D; -fx-font-size: 14px; -fx-cursor: hand;");

        registrar.setOnAction(e -> {
            if (txtNombre.getText().isEmpty() || txtCorreo.getText().isEmpty() || txtPassword.getText().isEmpty()) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Por favor complete todos los campos.");
                a.showAndWait();
                return;
            }

            Usuario usuario = new Usuario(txtNombre.getText().trim(), txtCorreo.getText().trim(), txtPassword.getText().trim(), cbRol.getValue());
            mainView.getUsuarioRepositorio().guardar(usuario);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Usuario registrado exitosamente como " + cbRol.getValue());
            alerta.showAndWait();
            mainView.mostrarLogin();
        });

        volver.setOnAction(e -> mainView.mostrarLogin());

        card.getChildren().addAll(titulo, new Label("Nombre:"), txtNombre, new Label("Correo:"), txtCorreo,
                new Label("Contraseña:"), txtPassword, new Label("Seleccione su Rol del Sistema:"), cbRol, registrar, volver);
        container.getChildren().addAll(izquierda, card);
        root.getChildren().add(container);
    }

    private TextField crearCampo(String placeholder) {
        TextField campo = new TextField();
        campo.setPromptText(placeholder);
        campo.setPrefHeight(45);
        campo.setStyle(estiloCampo());
        return campo;
    }

    private String estiloCampo() {
        return "-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #D6D6F5; -fx-padding: 0 15 0 15; -fx-font-size: 15px; -fx-background-color: #FAFAFF;";
    }
}
