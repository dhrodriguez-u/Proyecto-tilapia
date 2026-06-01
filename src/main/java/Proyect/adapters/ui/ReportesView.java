package Proyect.adapters.ui;

import Proyect.entities.EvaluacionSensorial;
import Proyect.entities.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class ReportesView {

    private final VBox root;
    private final MainView mainView;
    private TableView<EvaluacionSensorial> tabla;

    public Parent getView() { return root; }

    public ReportesView(MainView mainView) {
        this.mainView = mainView;

        root = new VBox(25);
        root.setPadding(new Insets(35));
        root.setStyle("-fx-background-color: #F7F7FC;");

        // ENCABEZADO
        VBox headerBox = new VBox(8);
        Label titulo = new Label("Módulo de Auditoría: Reportes y Consolidados de Calidad");
        titulo.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #5B3CC4;");
        Label subtitulo = new Label("Consolidación histórica de inspecciones organolépticas bajo el estándar de la norma NTC 1443.");
        subtitulo.setStyle("-fx-font-size: 14px; -fx-text-fill: #7F8C8D;");
        headerBox.getChildren().addAll(titulo, subtitulo);

        // 1. Obtener los datos globales iniciales de la base de datos
        List<EvaluacionSensorial> listaDatosRaw = mainView.getRealizarAnalisisUseCase().obtenerTodasLasEvaluaciones();

        // 2. Extraer el usuario que inició sesión
        Usuario usuarioActivo = mainView.getUsuarioActual();
        String correoLogueado = (usuarioActivo != null) ? usuarioActivo.getCorreo() : "anonimo@tilapia.com";
        String rolLogueado = (usuarioActivo != null) ? usuarioActivo.getRol() : "EXPERTO";

        // 🔒 CONTROL DE PRIVACIDAD EN LA UI:
        // Si no es INVESTIGADOR, filtramos la lista en caliente para que la tabla y los KPIs reflejen SOLO lo de este Experto.
        List<EvaluacionSensorial> listaDatos;
        if ("INVESTIGADOR".equalsIgnoreCase(rolLogueado)) {
            listaDatos = listaDatosRaw; // Ve todo el histórico global
        } else {
            listaDatos = listaDatosRaw.stream()
                    .filter(e -> e.getCreadorCorreo() != null && e.getCreadorCorreo().equalsIgnoreCase(correoLogueado))
                    .collect(Collectors.toList()); // Ve solo sus propios lotes
        }

        // MÉTRICAS AUTOMÁTICAS (Calculadas dinámicamente con la lista ya filtrada)
        int totalLotes = listaDatos.size();
        long aprobados = listaDatos.stream()
                .filter(e -> e.getPuntajeTotal() >= 13)
                .count();
        int rechazos = totalLotes - (int) aprobados;
        double porcentajeAprobacion = totalLotes > 0 ? ((double) aprobados / totalLotes) * 100 : 0.0;

        HBox kpiContainer = new HBox(25);
        kpiContainer.getChildren().addAll(
                crearTarjetaKPI("Lotes Evaluados Total", totalLotes + " Lotes", "#5B3CC4"),
                crearTarjetaKPI("Índice de Aprobación", String.format("%.1f%%", porcentajeAprobacion), "#2ECC71"),
                crearTarjetaKPI("Alertas de Sanidad (Rechazo)", rechazos + " Alertas", "#E74C3C")
        );

        // CONFIGURACIÓN DE LA TABLA
        tabla = new TableView<>();
        tabla.setPrefHeight(380);
        tabla.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-selection-bar: #E0D6FF;");

        // COLUMNAS
        TableColumn<EvaluacionSensorial, String> colLote = new TableColumn<>("Código Lote");
        colLote.setCellValueFactory(new PropertyValueFactory<>("codigoLote"));
        colLote.setPrefWidth(110);

        TableColumn<EvaluacionSensorial, Integer> colCantidad = new TableColumn<>("Cantidad Tilapias");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadTilapias"));
        colCantidad.setPrefWidth(120);

        TableColumn<EvaluacionSensorial, Integer> colPuntaje = new TableColumn<>("Puntaje");
        colPuntaje.setCellValueFactory(new PropertyValueFactory<>("puntajeTotal"));
        colPuntaje.setPrefWidth(80);

        TableColumn<EvaluacionSensorial, String> colClasif = new TableColumn<>("Clasificación NTC 1443");
        colClasif.setCellValueFactory(new PropertyValueFactory<>("clasificacion"));
        colClasif.setPrefWidth(160);

        TableColumn<EvaluacionSensorial, String> colFecha = new TableColumn<>("Fecha / Hora Cierre");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaEvaluacion"));
        colFecha.setPrefWidth(160);

        TableColumn<EvaluacionSensorial, String> colNotas = new TableColumn<>("Observaciones Técnicas del Experto");
        colNotas.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        colNotas.setPrefWidth(260);

        tabla.getColumns().addAll(colLote, colCantidad, colPuntaje, colClasif, colFecha, colNotas);
        tabla.getItems().addAll(listaDatos); // Llenado con datos seguros

        // BOTONES DE ACCIÓN
        HBox barraAcciones = new HBox(20);
        barraAcciones.setAlignment(Pos.CENTER_RIGHT);

        Button btnRegresar = new Button("⬅ Regresar al Menú Principal");
        btnRegresar.setStyle("-fx-background-color: #D6D6F5; -fx-text-fill: #4B2CBF; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");
        btnRegresar.setOnAction(e -> mainView.mostrarDashboard());

        Button btnExportar = new Button("\uD83D\uDCC4 Exportar Informe Técnico");
        btnExportar.setStyle("-fx-background-color: #5B3CC4; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");

        btnExportar.setOnAction(e -> {
            List<EvaluacionSensorial> datos = tabla.getItems();
            if (datos.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "No hay datos para exportar.").showAndWait();
                return;
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Reporte de Calidad");
            fileChooser.setInitialFileName("Reporte_Calidad_Tilapia.txt");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de Texto (*.txt)", "*.txt"));

            Stage stage = (Stage) btnExportar.getScene().getWindow();
            File destino = fileChooser.showSaveDialog(stage);

            if (destino != null) {
                // Ejecuta la exportación pasándole los parámetros al UseCase del experto
                boolean exito = mainView.getGenerarReporteUseCase().exportarATextoPlano(datos, destino, correoLogueado, rolLogueado);

                Alert alert = new Alert(exito ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
                alert.setTitle("Resultado Exportación");
                alert.setHeaderText(exito ? "¡Reporte Exportado!" : "Error de Escritura");
                alert.setContentText(exito ? "El archivo se guardó con éxito en:\n" + destino.getAbsolutePath() : "Error al escribir en la ruta.");
                alert.showAndWait();
            }
        });

        barraAcciones.getChildren().addAll(btnRegresar, btnExportar);
        root.getChildren().addAll(headerBox, kpiContainer, tabla, barraAcciones);
    }

    private VBox crearTarjetaKPI(String titulo, String valor, String colorHex) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(18, 25, 18, 25));
        card.setPrefWidth(260);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 14; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.02), 10, 0, 0, 4);");
        Label lblTit = new Label(titulo); lblTit.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #7F8C8D;");
        Label lblVal = new Label(valor); lblVal.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + colorHex + ";");
        card.getChildren().addAll(lblTit, lblVal);
        return card;
    }
}