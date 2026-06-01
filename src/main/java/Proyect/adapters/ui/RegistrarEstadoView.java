package Proyect.adapters.ui;

import Proyect.entities.RegistroEstado;
import Proyect.entities.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RegistrarEstadoView {

    private ScrollPane root;
    private final MainView mainView;

    public Parent getView() { return root; }

    public RegistrarEstadoView(MainView mainView) {
        this.mainView = mainView;

        root = new ScrollPane();
        root.setFitToWidth(true);
        root.setStyle("-fx-background-color: #F7F7FC;");

        VBox contenido = new VBox(30);
        contenido.setPadding(new Insets(40));
        contenido.setStyle("-fx-background-color: #F7F7FC;");

        // TÍTULO DE LA VISTA
        Label titulo = new Label("Monitoreo Físico-Químico del Agua");
        titulo.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #5B3CC4;");
        contenido.getChildren().add(titulo);

        // =====================================================
        // 1. SELECCIÓN DE LA ESTACIÓN / ESTANQUE
        // =====================================================
        VBox estacionBox = crearSeccion("Selección de Estación Piscícola");

        Label lblEstacion = new Label("Seleccione el estanque o estación a evaluar:");
        lblEstacion.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #444;");

        ComboBox<String> comboEstaciones = new ComboBox<>();
        comboEstaciones.getItems().addAll("Estación Alpha - Tilapia Roja", "Estación Beta - Estanque de Engorde", "Estación Gamma - Alevinos", "Estación Delta - Reproductores");
        comboEstaciones.setPromptText("Seleccione una estación...");
        comboEstaciones.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #D6D6F5; -fx-padding: 5;");
        comboEstaciones.setMaxWidth(350);

        estacionBox.getChildren().addAll(lblEstacion, comboEstaciones);

        // =====================================================
        // 2. PARÁMETROS FÍSICO-QUÍMICOS
        // =====================================================
        VBox parametrosBox = crearSeccion("Mediciones de Parámetros de Calidad");
        GridPane gridParametros = new GridPane();
        gridParametros.setHgap(30);
        gridParametros.setVgap(20);

        // --- TEMPERATURA ---
        Label lblTemp = new Label("Temperatura del Agua (°C):");
        lblTemp.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #444;");
        Spinner<Double> spinTemp = new Spinner<>(15.0, 40.0, 26.5, 0.5);
        spinTemp.setEditable(true);
        spinTemp.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");
        Label hintTemp = new Label("Óptimo: 24.0°C - 32.0°C");
        hintTemp.setStyle("-fx-font-size: 12px; -fx-text-fill: #7F8C8D;");

        // --- PH ---
        Label lblPh = new Label("Potencial de Hidrógeno (pH):");
        lblPh.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #444;");
        Spinner<Double> spinPh = new Spinner<>(1.0, 14.0, 7.2, 0.1);
        spinPh.setEditable(true);
        spinPh.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");
        Label hintPh = new Label("Óptimo: 6.5 - 8.5");
        hintPh.setStyle("-fx-font-size: 12px; -fx-text-fill: #7F8C8D;");

        // --- OXÍGENO DISUELTO ---
        Label lblOxigeno = new Label("Oxígeno Disuelto (mg/L):");
        lblOxigeno.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #444;");
        Spinner<Double> spinOxigeno = new Spinner<>(0.0, 15.0, 6.0, 0.2);
        spinOxigeno.setEditable(true);
        spinOxigeno.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");
        Label hintOxigeno = new Label("Óptimo: >= 5.0 mg/L");
        hintOxigeno.setStyle("-fx-font-size: 12px; -fx-text-fill: #7F8C8D;");

        // --- TURBIDEZ ---
        Label lblTurbidez = new Label("Turbidez / Disco Secchi (cm):");
        lblTurbidez.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #444;");
        Spinner<Double> spinTurbidez = new Spinner<>(5.0, 100.0, 30.0, 1.0);
        spinTurbidez.setEditable(true);
        spinTurbidez.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");
        Label hintTurbidez = new Label("Recomendado: 25.0 - 40.0 cm");
        hintTurbidez.setStyle("-fx-font-size: 12px; -fx-text-fill: #7F8C8D;");

        // Posicionar en el Grid
        gridParametros.add(lblTemp, 0, 0);       gridParametros.add(spinTemp, 1, 0);       gridParametros.add(hintTemp, 2, 0);
        gridParametros.add(lblPh, 0, 1);         gridParametros.add(spinPh, 1, 1);         gridParametros.add(hintPh, 2, 1);
        gridParametros.add(lblOxigeno, 0, 2);    gridParametros.add(spinOxigeno, 1, 2);    gridParametros.add(hintOxigeno, 2, 2);
        gridParametros.add(lblTurbidez, 0, 3);   gridParametros.add(spinTurbidez, 1, 3);   gridParametros.add(hintTurbidez, 2, 3);

        parametrosBox.getChildren().add(gridParametros);

        // =====================================================
        // 3. OBSERVACIONES GENERALES
        // =====================================================
        VBox observacionesBox = crearSeccion("Observaciones del Estado");
        TextArea txtObservaciones = new TextArea();
        txtObservaciones.setPromptText("Escriba comentarios adicionales sobre el estado del estanque (color del agua, comportamiento de los peces, etc.)...");
        txtObservaciones.setPrefRowCount(3);
        txtObservaciones.setWrapText(true);
        txtObservaciones.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #D6D6F5;");
        observacionesBox.getChildren().add(txtObservaciones);

        // =====================================================
        // BOTONES DE ACCIÓN (Guardar / Cancelar)
        // =====================================================
        HBox acciones = new HBox(20);
        acciones.setAlignment(Pos.CENTER_LEFT);

        Button btnGuardar = new Button("Guardar Registro");
        btnGuardar.setStyle("-fx-background-color: #5B3CC4; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12 25; -fx-background-radius: 10; -fx-cursor: hand;");

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12 25; -fx-background-radius: 10; -fx-cursor: hand;");

        // LÓGICA DEL BOTÓN GUARDAR CON ENLACE DE SEGURIDAD
        btnGuardar.setOnAction(e -> {
            if (comboEstaciones.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Debe seleccionar una estación piscícola para registrar su estado.").showAndWait();
                return;
            }

            // Mapear los datos de la interfaz al objeto entidad
            RegistroEstado nuevoRegistro = new RegistroEstado();
            nuevoRegistro.setEstacionNombre(comboEstaciones.getValue());
            nuevoRegistro.setTemperatura(spinTemp.getValue());
            nuevoRegistro.setPh(spinPh.getValue());
            nuevoRegistro.setOxigenoDisuelto(spinOxigeno.getValue());
            nuevoRegistro.setTurbidez(spinTurbidez.getValue());
            nuevoRegistro.setObservaciones(txtObservaciones.getText().trim());

            // 🔒 SEGURIDAD: Recuperamos el usuario de la sesión activa
            Usuario usuarioActivo = mainView.getUsuarioActual();

            // Ejecutar caso de uso enviando el registro junto al piscicultor logueado
            String resultadoAlerta = mainView.getRegistrarEstadoUseCase().ejecutar(nuevoRegistro, usuarioActivo);

            // Mostrar el resultado final al Piscicultor
            Alert alertaResultado = new Alert(Alert.AlertType.INFORMATION);
            alertaResultado.setTitle("Registro Exitoso");

            if (resultadoAlerta.equals("Parámetros Óptimos")) {
                alertaResultado.setHeaderText("¡Estado registrado de manera exitosa!");
                alertaResultado.setContentText("Los parámetros físico-químicos del agua en la '" + nuevoRegistro.getEstacionNombre() + "' están en rangos óptimos.");
            } else {
                alertaResultado.setAlertType(Alert.AlertType.WARNING);
                alertaResultado.setHeaderText("Registro Guardado con Advertencias");
                alertaResultado.setContentText("Atención: Algunos parámetros están fuera del rango óptimo para el cultivo de tilapia:\n\n" + resultadoAlerta);
            }

            alertaResultado.showAndWait();
            mainView.mostrarDashboard(); // Regresar al menú principal
        });

        btnCancelar.setOnAction(e -> mainView.mostrarDashboard());

        acciones.getChildren().addAll(btnGuardar, btnCancelar);

        // Unir todo el contenido en la vista
        contenido.getChildren().addAll(estacionBox, parametrosBox, observacionesBox, acciones);
        root.setContent(contenido);
    }

    private VBox crearSeccion(String titulo) {
        VBox box = new VBox(12);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.02), 10, 0, 0, 4);");
        Label lbl = new Label(titulo);
        lbl.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #5B3CC4;");
        box.getChildren().add(lbl);
        return box;
    }
}
