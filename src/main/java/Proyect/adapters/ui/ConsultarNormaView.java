package Proyect.adapters.ui;

import Proyect.entities.NormaNtc1443;
import Proyect.entities.AceptacionNorma;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ConsultarNormaView {

    private final VBox root;
    private final MainView mainView;

    public Parent getView() {
        return root;
    }

    public ConsultarNormaView(MainView mainView) {
        this.mainView = mainView;

        root = new VBox(20);
        root.setPadding(new Insets(35));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #F8F9FD;");

        Label titulo = new Label("Módulo Técnico: Consulta de Normativa Vigente");
        titulo.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #4B2CBF;");

        Label subtitulo = new Label("Parámetros oficiales de control de calidad e inocuidad para la evaluación de Tilapia.");
        subtitulo.setStyle("-fx-font-size: 14px; -fx-text-fill: #6F6B80;");

        TextArea txtNorma = new TextArea();
        txtNorma.setEditable(false);
        txtNorma.setPrefHeight(400);
        txtNorma.setWrapText(true);
        txtNorma.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 13px; -fx-control-inner-background: #FFFFFF; -fx-background-color: transparent; -fx-border-color: #E2E4EB; -fx-border-radius: 8;");

        // Recupera la entidad del dominio a través del caso de uso
        NormaNtc1443 norma = mainView.getConsultarNormaNtc1443UseCase().consultarDetallesNorma();
        txtNorma.setText(norma.getTextoDetallado());

        CheckBox chkAceptar = new CheckBox("Confirmar lectura y aceptación de criterios técnicos para auditoría");
        chkAceptar.setStyle("-fx-font-size: 14px; -fx-text-fill: #3A3554; -fx-font-weight: bold;");
        chkAceptar.setPadding(new Insets(10, 0, 10, 0));

        HBox barraAcciones = new HBox(20);
        barraAcciones.setAlignment(Pos.CENTER);
        barraAcciones.setPadding(new Insets(10, 0, 0, 0));

        Button btnProcesar = new Button("🚀 Registrar Aceptación y Ver Video HACCP");
        btnProcesar.setPrefHeight(45);
        btnProcesar.setPrefWidth(320);
        btnProcesar.setStyle("-fx-background-color: #6C4DFF; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10; -fx-font-size: 14px; -fx-cursor: hand;");

        btnProcesar.setOnAction(e -> {
            if (!chkAceptar.isSelected()) {
                Alert alerta = new Alert(Alert.AlertType.WARNING, "Atención: Debe marcar la casilla de aceptación para poder registrar la firma técnica.");
                alerta.setHeaderText(null);
                alerta.showAndWait();
                return;
            }

            AceptacionNorma registro = mainView.getConsultarNormaNtc1443UseCase().registrarAceptacionDeNorma(
                    mainView.getUsuarioActual(),
                    true
            );

            // Abre la URL que configuraste exitosamente en tu entidad
            mainView.getHostServices().showDocument(norma.getUrlVideoHaccp());

            Alert exito = new Alert(Alert.AlertType.INFORMATION);
            exito.setTitle("Auditoría Registrada");
            exito.setHeaderText("Firma Técnica Almacenada Correctamente");
            exito.setContentText("Usuario: " + registro.getCorreoUsuario() +
                    "\nNorma: " + registro.getNombreNorma() +
                    "\nFecha/Hora: " + registro.getFechaHoraAceptacion() +
                    "\n\nRedireccionando al material audiovisual de apoyo en el navegador...");
            exito.showAndWait();
        });

        // CORRECCIÓN: Nombre formal y apto para el usuario/cliente final
        Button btnVolver = new Button("⬅️ Regresar al Menú Principal");
        btnVolver.setPrefHeight(45);
        btnVolver.setPrefWidth(240); // Un poco más ancho para que el texto formal quepa perfecto
        btnVolver.setStyle("-fx-background-color: #E2E4EB; -fx-text-fill: #4B2CBF; -fx-font-weight: bold; -fx-background-radius: 10; -fx-font-size: 14px; -fx-cursor: hand;");
        btnVolver.setOnAction(e -> mainView.mostrarDashboard());

        barraAcciones.getChildren().addAll(btnVolver, btnProcesar);
        root.getChildren().addAll(titulo, subtitulo, txtNorma, chkAceptar, barraAcciones);
    }
}