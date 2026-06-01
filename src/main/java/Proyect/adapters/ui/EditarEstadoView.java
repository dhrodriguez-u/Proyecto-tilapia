package Proyect.adapters.ui;

import Proyect.entities.RegistroEstado;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class EditarEstadoView {

    private ScrollPane root;
    private final MainView mainView;
    private final RegistroEstado registro;

    public Parent getView() { return root; }

    public EditarEstadoView(MainView mainView, RegistroEstado registro) {
        this.mainView = mainView;
        this.registro = registro;

        root = new ScrollPane();
        root.setFitToWidth(true);
        root.setStyle("-fx-background-color: #F7F7FC;");

        VBox contenido = new VBox(30);
        contenido.setPadding(new Insets(40));
        contenido.setStyle("-fx-background-color: #F7F7FC;");

        Label titulo = new Label("Modificar Monitoreo de Estación");
        titulo.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #2ECC71;");
        contenido.getChildren().add(titulo);

        // SECCIÓN ESTACIÓN (Solo lectura para mantener coherencia de registros históricos)
        VBox estacionBox = new VBox(12);
        estacionBox.setPadding(new Insets(20));
        estacionBox.setStyle("-fx-background-color: #EAEDED; -fx-background-radius: 16;");
        Label lblEstacion = new Label("Estación bajo edición: " + registro.getEstacionNombre());
        lblEstacion.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #34495E;");
        estacionBox.getChildren().add(lblEstacion);

        // FORMULARIO DE EDICIÓN DE PARÁMETROS
        VBox parametrosBox = new VBox(12);
        parametrosBox.setPadding(new Insets(20));
        parametrosBox.setStyle("-fx-background-color: white; -fx-background-radius: 16;");

        GridPane gridParametros = new GridPane();
        gridParametros.setHgap(30); gridParametros.setVgap(20);

        Spinner<Double> spinTemp = new Spinner<>(15.0, 40.0, registro.getTemperatura(), 0.5);
        Spinner<Double> spinPh = new Spinner<>(1.0, 14.0, registro.getPh(), 0.1);
        Spinner<Double> spinOxigeno = new Spinner<>(0.0, 15.0, registro.getOxigenoDisuelto(), 0.2);
        Spinner<Double> spinTurbidez = new Spinner<>(5.0, 100.0, registro.getTurbidez(), 1.0);

        spinTemp.setEditable(true); spinPh.setEditable(true); spinOxigeno.setEditable(true); spinTurbidez.setEditable(true);

        gridParametros.add(new Label("Temperatura del Agua (°C):"), 0, 0);    gridParametros.add(spinTemp, 1, 0);
        gridParametros.add(new Label("Potencial Hidrógeno (pH):"), 0, 1);     gridParametros.add(spinPh, 1, 1);
        gridParametros.add(new Label("Oxígeno Disuelto (mg/L):"), 0, 2);     gridParametros.add(spinOxigeno, 1, 2);
        gridParametros.add(new Label("Turbidez (Disco Secchi cm):"), 0, 3);   gridParametros.add(spinTurbidez, 1, 3);
        parametrosBox.getChildren().add(gridParametros);

        // OBSERVACIONES
        VBox obsBox = new VBox(12);
        obsBox.setPadding(new Insets(20));
        obsBox.setStyle("-fx-background-color: white; -fx-background-radius: 16;");
        TextArea txtObservaciones = new TextArea(registro.getObservaciones());
        txtObservaciones.setWrapText(true); txtObservaciones.setPrefRowCount(3);
        obsBox.getChildren().addAll(new Label("Modificar Observaciones:"), txtObservaciones);

        // BOTONES
        HBox acciones = new HBox(20);
        Button btnActualizar = new Button("Actualizar Cambios");
        btnActualizar.setStyle("-fx-background-color: #2ECC71; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12 25; -fx-background-radius: 10;");

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12 25; -fx-background-radius: 10;");

        btnActualizar.setOnAction(e -> {
            registro.setTemperatura(spinTemp.getValue());
            registro.setPh(spinPh.getValue());
            registro.setOxigenoDisuelto(spinOxigeno.getValue());
            registro.setTurbidez(spinTurbidez.getValue());
            registro.setObservaciones(txtObservaciones.getText().trim());

            // Recalcular Alerta rápido para actualizar la columna de la BD
            StringBuilder sb = new StringBuilder();
            if(spinTemp.getValue() < 24.0 || spinTemp.getValue() > 32.0) sb.append("- Temperatura Crítica ");
            if(spinPh.getValue() < 6.5 || spinPh.getValue() > 8.5) sb.append("- pH Inestable ");
            if(spinOxigeno.getValue() < 5.0) sb.append("- Oxígeno Deficiente ");
            registro.setAlerta(sb.length() == 0 ? "Parámetros Óptimos" : sb.toString());

            // Uso exclusivo de GestionarEstacionUseCase
            String msg = mainView.getGestionarEstacionUseCase().actualizarRegistro(registro);
            new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
            mainView.mostrarGestionarEstacion();
        });

        btnCancelar.setOnAction(e -> mainView.mostrarGestionarEstacion());

        acciones.getChildren().addAll(btnActualizar, btnCancelar);
        contenido.getChildren().addAll(estacionBox, parametrosBox, obsBox, acciones);
        root.setContent(contenido);
    }
}
