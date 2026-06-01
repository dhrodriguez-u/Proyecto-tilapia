package Proyect.adapters.ui;

import Proyect.entities.EvaluacionSensorial;
import Proyect.usecases.services.ConsultarReportesCalidadUseCase;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ConsultarReportesView {

    private final BorderPane root;
    private final MainView mainView;
    private final ConsultarReportesCalidadUseCase useCase;

    // Componentes de la interfaz
    private ListView<EvaluacionSensorial> listaReportes;
    private Label lblTituloReporte;
    private Label lblResultadoDictamen;
    private Label lblFechaYCantidad;
    private CheckBox chkOjos1, chkOjos2, chkOjos3;
    private CheckBox chkBranquias1, chkBranquias2, chkBranquias3;
    private CheckBox chkCavidad1, chkCavidad2, chkCavidad3;
    private CheckBox chkOlor1, chkOlor2, chkOlor3;
    private TextArea txtObservacionesExperto;

    public Parent getView() { return root; }

    public ConsultarReportesView(MainView mainView, ConsultarReportesCalidadUseCase useCase) {
        this.mainView = mainView;
        this.useCase = useCase;

        root = new BorderPane();
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: #F8F9FA;");

        // --- ENCABEZADO SUPERIOR ---
        VBox topBox = new VBox(5);
        Label titulo = new Label("🔬 Auditoría de Calidad Organoléptica (Norma NTC 1443)");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #4B2CBF;");
        Label subtitulo = new Label("Consulte los dictámenes sensoriales emitidos sobre los lotes de tilapia.");
        subtitulo.setStyle("-fx-font-size: 13px; -fx-text-fill: #7F8C8D;");
        topBox.getChildren().addAll(titulo, subtitulo);
        root.setTop(topBox);

        // --- PANEL IZQUIERDO: LISTA DE REPORTES ---
        VBox leftBox = new VBox(10);
        leftBox.setPrefWidth(320);
        leftBox.setPadding(new Insets(15, 15, 0, 0));

        Label lblLista = new Label("Reportes Disponibles");
        lblLista.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495E;");

        listaReportes = new ListView<>();
        listaReportes.setPrefHeight(500);

        listaReportes.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(EvaluacionSensorial item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // SE USA getCodigoLote() EN LUGAR DE getLoteId()
                    setText("📋 Reporte Lote: " + item.getCodigoLote());
                }
            }
        });

        // Cargar datos utilizando el método del caso de uso
        listaReportes.getItems().addAll(useCase.obtenerReportesCalidad());
        leftBox.getChildren().addAll(lblLista, listaReportes);
        root.setLeft(leftBox);

        // --- PANEL CENTRAL: DETALLE DEL REPORTE SELECCIONADO ---
        VBox detalleBox = new VBox(15);
        detalleBox.setPadding(new Insets(15, 0, 0, 15));
        detalleBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 25;");

        lblTituloReporte = new Label("Seleccione un reporte de la lista para auditar");
        lblTituloReporte.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #4B2CBF;");

        lblResultadoDictamen = new Label("Clasificación: Ninguno seleccionado");
        lblResultadoDictamen.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #27AE60;");

        lblFechaYCantidad = new Label("Fecha: -- | Cantidad Muestreada: --");
        lblFechaYCantidad.setStyle("-fx-font-size: 12px; -fx-text-fill: #7F8C8D;");

        GridPane gridParametros = new GridPane();
        gridParametros.setHgap(40);
        gridParametros.setVgap(20);

        // 1. Estado de los ojos
        VBox boxOjos = new VBox(8);
        Label titOjos = new Label("1. Estado de los ojos");
        titOjos.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #34495E;");
        chkOjos1 = new CheckBox("Excelente (Convexos, pupila brillante)");
        chkOjos2 = new CheckBox("Regular (Planos, opalescentes)");
        chkOjos3 = new CheckBox("Deficiente (Cóncavos, totalmente opacos)");
        deshabilitarCampos(chkOjos1, chkOjos2, chkOjos3);
        boxOjos.getChildren().addAll(titOjos, chkOjos1, chkOjos2, chkOjos3);

        // 2. Aspecto de las branquias
        VBox boxBranquias = new VBox(8);
        Label titBranquias = new Label("2. Aspecto de las branquias");
        titBranquias.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #34495E;");
        chkBranquias1 = new CheckBox("Excelente (Rojo brillante, sin moco)");
        chkBranquias2 = new CheckBox("Regular (Rosadas, pálidas)");
        chkBranquias3 = new CheckBox("Deficiente (Pardas/Grises, moco espeso)");
        deshabilitarCampos(chkBranquias1, chkBranquias2, chkBranquias3);
        boxBranquias.getChildren().addAll(titBranquias, chkBranquias1, chkBranquias2, chkBranquias3);

        // 3. Cavidad Abdominal
        VBox boxCavidad = new VBox(8);
        Label titCavidad = new Label("3. Cavidad Abdominal (Vísceras/Piel)");
        titCavidad.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #34495E;");
        chkCavidad1 = new CheckBox("Excelente (Limpia, carne firme)");
        chkCavidad2 = new CheckBox("Regular (Ligera pérdida de tono)");
        chkCavidad3 = new CheckBox("Deficiente (Blanda, desgarres)");
        deshabilitarCampos(chkCavidad1, chkCavidad2, chkCavidad3);
        boxCavidad.getChildren().addAll(titCavidad, chkCavidad1, chkCavidad2, chkCavidad3);

        // 4. Olor
        VBox boxOlor = new VBox(8);
        Label titOlor = new Label("4. Olor Característico");
        titOlor.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #34495E;");
        chkOlor1 = new CheckBox("Excelente (Fresco, algas marinas o neutro)");
        chkOlor2 = new CheckBox("Regular (Ligeramente ácido)");
        chkOlor3 = new CheckBox("Deficiente (Putrefacto o amoniacal)");
        deshabilitarCampos(chkOlor1, chkOlor2, chkOlor3);
        boxOlor.getChildren().addAll(titOlor, chkOlor1, chkOlor2, chkOlor3);

        gridParametros.add(boxOjos, 0, 0);
        gridParametros.add(boxBranquias, 1, 0);
        gridParametros.add(boxCavidad, 0, 1);
        gridParametros.add(boxOlor, 1, 1);

        // Observaciones textuales del Experto
        VBox boxObs = new VBox(5);
        Label lblObs = new Label("Observaciones del Experto Evaluador:");
        lblObs.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495E;");
        txtObservacionesExperto = new TextArea();
        txtObservacionesExperto.setEditable(false);
        txtObservacionesExperto.setWrapText(true);
        txtObservacionesExperto.setPrefRowCount(3);
        txtObservacionesExperto.setStyle("-fx-control-inner-background: #F8F9F9;");
        boxObs.getChildren().addAll(lblObs, txtObservacionesExperto);

        detalleBox.getChildren().addAll(lblTituloReporte, lblResultadoDictamen, lblFechaYCantidad, new Separator(), gridParametros, boxObs);
        root.setCenter(detalleBox);

        // --- EVENTO DE SELECCIÓN DINÁMICA ---
        listaReportes.getSelectionModel().selectedItemProperty().addListener((obs, antiguo, seleccionado) -> {
            if (seleccionado != null) {
                lblTituloReporte.setText("Reporte - Lote #" + seleccionado.getCodigoLote());
                lblResultadoDictamen.setText("Clasificación Final: " + seleccionado.getClasificacion() + " (Puntaje: " + seleccionado.getPuntajeTotal() + " pts)");
                lblFechaYCantidad.setText("Fecha Análisis: " + seleccionado.getFechaEvaluacion() + " | Cantidad de Tilapias: " + seleccionado.getCantidadTilapias());
                mapearDatosAInterfaz(seleccionado);
            }
        });

        // --- BARRA INFERIOR ---
        HBox barraInferior = new HBox();
        barraInferior.setAlignment(Pos.CENTER_RIGHT);
        barraInferior.setPadding(new Insets(15, 0, 0, 0));
        Button btnVolver = new Button("Volver al menú");
        btnVolver.setStyle("-fx-background-color: #D6D6F5; -fx-text-fill: #4B2CBF; -fx-font-weight: bold; -fx-padding: 10 25; -fx-background-radius: 8; -fx-cursor: hand;");
        btnVolver.setOnAction(e -> mainView.mostrarDashboard());
        barraInferior.getChildren().add(btnVolver);
        root.setBottom(barraInferior);
    }

    private void deshabilitarCampos(CheckBox... checks) {
        for (CheckBox cb : checks) {
            cb.setDisable(true);
            cb.setStyle("-fx-opacity: 1.0; -fx-text-fill: black;");
        }
    }

    private void mapearDatosAInterfaz(EvaluacionSensorial ev) {
        limpiarTodo();

        // Mapeo basado en puntajes numéricos estándar de inspección organoléptica

        // 1. Ojos
        if (ev.getOjos() == 0) chkOjos1.setSelected(true);
        else if (ev.getOjos() == 1) chkOjos2.setSelected(true);
        else chkOjos3.setSelected(true);

        // 2. Branquias
        if (ev.getBranquias() == 0) chkBranquias1.setSelected(true);
        else if (ev.getBranquias() == 1) chkBranquias2.setSelected(true);
        else chkBranquias3.setSelected(true);

        // 3. Cavidad Abdominal
        if (ev.getCavidadAbdominal() == 0) chkCavidad1.setSelected(true);
        else if (ev.getCavidadAbdominal() == 1) chkCavidad2.setSelected(true);
        else chkCavidad3.setSelected(true);

        // 4. Olor
        if (ev.getOlor() == 0) chkOlor1.setSelected(true);
        else if (ev.getOlor() == 1) chkOlor2.setSelected(true);
        else chkOlor3.setSelected(true);

        // 5. Observaciones
        if (ev.getObservaciones() != null && !ev.getObservaciones().trim().isEmpty()) {
            txtObservacionesExperto.setText(ev.getObservaciones());
        } else {
            txtObservacionesExperto.setText("El experto no registró comentarios adicionales en esta evaluación.");
        }
    }

    private void limpiarTodo() {
        chkOjos1.setSelected(false); chkOjos2.setSelected(false); chkOjos3.setSelected(false);
        chkBranquias1.setSelected(false); chkBranquias2.setSelected(false); chkBranquias3.setSelected(false);
        chkCavidad1.setSelected(false); chkCavidad2.setSelected(false); chkCavidad3.setSelected(false);
        chkOlor1.setSelected(false); chkOlor2.setSelected(false); chkOlor3.setSelected(false);
        txtObservacionesExperto.clear();
    }
}
