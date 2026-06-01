package Proyect.adapters.ui;

import Proyect.entities.EvaluacionSensorial;
import Proyect.entities.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AnalisisView {

    private ScrollPane root;
    private final MainView mainView;

    public Parent getView() { return root; }

    public AnalisisView(MainView mainView) {
        this.mainView = mainView;
        root = new ScrollPane();
        root.setFitToWidth(true);
        root.setStyle("-fx-background-color: #F7F7FC;");

        VBox contenido = new VBox(30);
        contenido.setPadding(new Insets(40));
        contenido.setStyle("-fx-background-color: #F7F7FC;");

        Label titulo = new Label("Análisis Sensorial (Norma NTC 1443)");
        titulo.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #5B3CC4;");
        contenido.getChildren().add(titulo);

        // =====================================================
        // 0. DATOS DEL MUESTREO (Lote y Cantidad)
        // =====================================================
        VBox datosMuestreoBox = crearSeccion("Datos del Muestreo");
        GridPane gridDatos = new GridPane();
        gridDatos.setHgap(20);
        gridDatos.setVgap(15);

        Label lblLote = new Label("Código del Lote / Estación:");
        lblLote.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #444;");
        TextField txtLote = new TextField();
        txtLote.setPromptText("Ej: LOTE-2026-A");
        txtLote.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #D6D6F5; -fx-padding: 8;");

        Label lblCantidad = new Label("Cantidad de Tilapias Muestreadas:");
        lblCantidad.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #444;");

        Spinner<Integer> spinCantidad = new Spinner<>(1, 1000, 10);
        spinCantidad.setEditable(true);
        spinCantidad.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");
        spinCantidad.setMaxWidth(150);

        gridDatos.add(lblLote, 0, 0);
        gridDatos.add(txtLote, 1, 0);
        gridDatos.add(lblCantidad, 0, 1);
        gridDatos.add(spinCantidad, 1, 1);
        datosMuestreoBox.getChildren().add(gridDatos);

        // =====================================================
        // DESCRIPTORES ORGANOLÉPTICOS OFICIALES (NTC 1443)
        // =====================================================

        // 1. OJOS
        VBox ojosBox = crearSeccion("1. Estado de los ojos");
        ToggleGroup ojosGroup = new ToggleGroup();
        RadioButton ojos5 = crearOpcion("Convexos, córnea transparente y pupila negra brillante.", 5, ojosGroup);
        RadioButton ojos3 = crearOpcion("Planos, córnea ligeramente opaca y pupila opaca.", 3, ojosGroup);
        RadioButton ojos1 = crearOpcion("Cóncavos (hundidos), córnea totalmente opaca y pupila gris.", 1, ojosGroup);
        ojosBox.getChildren().addAll(ojos5, ojos3, ojos1);

        // 2. BRANQUIAS
        VBox branquiasBox = crearSeccion("2. Estado de las branquias (Agallas)");
        ToggleGroup branquiasGroup = new ToggleGroup();
        RadioButton bran5 = crearOpcion("Color rojo brillante, bien separadas, sin mucosidad.", 5, branquiasGroup);
        RadioButton bran3 = crearOpcion("Color rosa/rojo pálido, ligeramente opacas, mucosidad ligera.", 3, branquiasGroup);
        RadioButton bran1 = crearOpcion("Color marrón o grisáceo, mucosidad abundante y turbia.", 1, branquiasGroup);
        branquiasBox.getChildren().addAll(bran5, bran3, bran1);

        // 3. CAVIDAD ABDOMINAL Y VÍSCERAS (¡Fiel a la tabla de la Norma!)
        VBox abdomenBox = crearSeccion("3. Cavidad Abdominal y Vísceras");
        ToggleGroup abdomenGroup = new ToggleGroup();
        RadioButton abd5 = crearOpcion("Superficie de corte limpia, carne blanca/rosada, sangre roja brillante, vísceras enteras y brillantes.", 5, abdomenGroup);
        RadioButton abd3 = crearOpcion("Superficie ligeramente opaca, sangre purpúrea, vísceras algo descoloridas.", 3, abdomenGroup);
        RadioButton abd1 = crearOpcion("Superficie de corte opaca, carne grisácea, sangre marrón, vísceras rotas.", 1, abdomenGroup);
        abdomenBox.getChildren().addAll(abd5, abd3, abd1);

        // 4. OLOR
        VBox olorBox = crearSeccion("4. Olor general del espécimen");
        ToggleGroup olorGroup = new ToggleGroup();
        RadioButton olor5 = crearOpcion("Fresco, agradable, recuerda a algas marinas limpias.", 5, olorGroup);
        RadioButton olor3 = crearOpcion("Neutro, sin aroma característico pero exento de notas extrañas.", 3, olorGroup);
        RadioButton olor1 = crearOpcion("Ácido, fétido, rancio o con notas a amoníaco.", 1, olorGroup);
        olorBox.getChildren().addAll(olor5, olor3, olor1);

        // 5. OBSERVACIONES
        VBox observacionesBox = crearSeccion("5. Observaciones adicionales (Opcional)");
        TextArea txtObservaciones = new TextArea();
        txtObservaciones.setPromptText("Escriba notas del laboratorio (máximo 200 caracteres)...");
        txtObservaciones.setPrefRowCount(3);
        txtObservaciones.setWrapText(true);
        txtObservaciones.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #D6D6F5;");

        Label lblContador = new Label("Caracteres restantes: 200");
        lblContador.setStyle("-fx-font-size: 12px; -fx-text-fill: #7F8C8D;");
        txtObservaciones.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 200) txtObservaciones.setText(oldValue);
            else lblContador.setText("Caracteres restantes: " + (200 - newValue.length()));
        });
        observacionesBox.getChildren().addAll(txtObservaciones, lblContador);

        // BOTONES DE ACCIÓN
        HBox acciones = new HBox(20);
        acciones.setAlignment(Pos.CENTER_LEFT);

        Button btnGuardar = new Button("Guardar y Clasificar");
        btnGuardar.setStyle("-fx-background-color: #5B3CC4; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12 25; -fx-background-radius: 10; -fx-cursor: hand;");
        Button btnVolver = new Button("Cancelar y Volver");
        btnVolver.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12 25; -fx-background-radius: 10; -fx-cursor: hand;");

        btnGuardar.setOnAction(e -> {
            if (txtLote.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "El código del lote es obligatorio.").showAndWait();
                return;
            }
            if (ojosGroup.getSelectedToggle() == null || branquiasGroup.getSelectedToggle() == null ||
                    abdomenGroup.getSelectedToggle() == null || olorGroup.getSelectedToggle() == null) {
                new Alert(Alert.AlertType.WARNING, "Debe evaluar obligatoriamente todos los descriptores de la norma.").showAndWait();
                return;
            }

            String lote = txtLote.getText().trim();
            int cantidad = spinCantidad.getValue();
            int pOjos = (int) ojosGroup.getSelectedToggle().getUserData();
            int pBran = (int) branquiasGroup.getSelectedToggle().getUserData();
            int pAbd = (int) abdomenGroup.getSelectedToggle().getUserData();
            int pOlor = (int) olorGroup.getSelectedToggle().getUserData();
            String notas = txtObservaciones.getText().trim();

            EvaluacionSensorial eval = new EvaluacionSensorial();
            eval.setCodigoLote(lote);
            eval.setCantidadTilapias(cantidad);
            eval.setOjos(pOjos);
            eval.setBranquias(pBran);
            eval.setCavidadAbdominal(pAbd);
            eval.setOlor(pOlor);
            eval.setObservaciones(notas);

            // 🔒 MODIFICACIÓN DE SEGURIDAD CRÍTICA:
            // Extraemos el usuario logueado actualmente en MainView
            Usuario usuarioActivo = mainView.getUsuarioActual();

            // Enviamos el análisis JUNTO con el usuario activo al Caso de Uso
            mainView.getRealizarAnalisisUseCase().ejecutar(eval, usuarioActivo);

            Alert exito = new Alert(Alert.AlertType.INFORMATION);
            exito.setTitle("Análisis Exitoso");
            exito.setHeaderText("Lote Registrado: " + eval.getCodigoLote());
            exito.setContentText("Muestras: " + eval.getCantidadTilapias() + " unidades.\nClasificación: " + eval.getClasificacion() + "\nResultado: " + eval.getPuntajeTotal() + " / 20 pts.");
            exito.showAndWait();

            mainView.mostrarDashboard();
        });

        btnVolver.setOnAction(e -> mainView.mostrarDashboard());
        acciones.getChildren().addAll(btnGuardar, btnVolver);

        contenido.getChildren().addAll(datosMuestreoBox, ojosBox, branquiasBox, abdomenBox, olorBox, observacionesBox, acciones);
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

    private RadioButton crearOpcion(String texto, int puntos, ToggleGroup grupo) {
        RadioButton rb = new RadioButton(texto + "   (" + puntos + " pts)");
        rb.setToggleGroup(grupo);
        rb.setUserData(puntos);
        rb.setWrapText(true);

        String normal = "-fx-font-size: 15px; -fx-text-fill: #444444; -fx-padding: 10; -fx-background-radius: 8; -fx-cursor: hand;";
        String hover = "-fx-font-size: 15px; -fx-text-fill: #2D2D2D; -fx-padding: 10; -fx-background-color: #F0EBFF; -fx-background-radius: 8; -fx-cursor: hand;";

        rb.setStyle(normal);
        rb.setMaxWidth(Double.MAX_VALUE);
        rb.setOnMouseEntered(e -> rb.setStyle(hover));
        rb.setOnMouseExited(e -> rb.setStyle(normal));
        return rb;
    }
}
