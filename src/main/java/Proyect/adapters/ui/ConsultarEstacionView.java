package Proyect.adapters.ui;

import Proyect.entities.RegistroEstado;
import Proyect.usecases.services.ConsultarEstacionUseCase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.util.List;

public class ConsultarEstacionView {

    private final VBox root;
    private final MainView mainView;
    private final ConsultarEstacionUseCase useCase;

    public Parent getView() { return root; }

    public ConsultarEstacionView(MainView mainView, ConsultarEstacionUseCase useCase) {
        this.mainView = mainView;
        this.useCase = useCase;

        root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F4F6F7;");

        // --- ENCABEZADO ---
        VBox topBox = new VBox(5);
        Label titulo = new Label("🔬 Auditoría Ambiental de Estaciones Piscícolas");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #4B2CBF;");
        Label subtitulo = new Label("Historial completo de parámetros físico-químicos del agua registrados en los estanques.");
        subtitulo.setStyle("-fx-font-size: 13px; -fx-text-fill: #7F8C8D;");
        topBox.getChildren().addAll(titulo, subtitulo);

        // --- TABLA DE DATOS ---
        TableView<RegistroEstado> tabla = new TableView<>();
        VBox.setVgrow(tabla, Priority.ALWAYS);

        // 1. Columna de ID calculada por Listeo Numérico (1, 2, 3...)
        TableColumn<RegistroEstado, Integer> colId = new TableColumn<>("#");
        colId.setCellValueFactory(cellData -> {
            int index = tabla.getItems().indexOf(cellData.getValue()) + 1;
            return new SimpleIntegerProperty(index).asObject();
        });
        colId.setPrefWidth(60);

        // 2. COLUMNA CORREGIDA: Apunta exactamente a "estacionNombre"
        TableColumn<RegistroEstado, String> colEstacion = new TableColumn<>("Identificador Estanque");
        colEstacion.setCellValueFactory(new PropertyValueFactory<>("estacionNombre"));
        colEstacion.setPrefWidth(220);

        TableColumn<RegistroEstado, Double> colTemp = new TableColumn<>("Temperatura (°C)");
        colTemp.setCellValueFactory(new PropertyValueFactory<>("temperatura"));
        colTemp.setPrefWidth(130);

        TableColumn<RegistroEstado, Double> colPh = new TableColumn<>("pH");
        colPh.setCellValueFactory(new PropertyValueFactory<>("ph"));
        colPh.setPrefWidth(80);

        TableColumn<RegistroEstado, Double> colOxigeno = new TableColumn<>("Oxígeno (mg/L)");
        colOxigeno.setCellValueFactory(new PropertyValueFactory<>("oxigenoDisuelto"));
        colOxigeno.setPrefWidth(140);

        TableColumn<RegistroEstado, Double> colTurbidez = new TableColumn<>("Turbidez (cm)");
        colTurbidez.setCellValueFactory(new PropertyValueFactory<>("turbidez"));
        colTurbidez.setPrefWidth(120);

        TableColumn<RegistroEstado, String> colAlerta = new TableColumn<>("Estado Alerta");
        colAlerta.setCellValueFactory(new PropertyValueFactory<>("alerta"));
        colAlerta.setPrefWidth(160);

        TableColumn<RegistroEstado, String> colObs = new TableColumn<>("Observaciones Técnicas");
        colObs.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        colObs.setPrefWidth(250);

        // Agregamos las columnas estructuradas
        tabla.getColumns().addAll(colId, colEstacion, colTemp, colPh, colOxigeno, colTurbidez, colAlerta, colObs);

        // Carga de datos desde el caso de uso
        List<RegistroEstado> historial = useCase.obtenerHistorialEstaciones();
        if (historial != null) {
            tabla.getItems().addAll(historial);
        }

        // --- BARRA INFERIOR ---
        HBox barraInferior = new HBox();
        barraInferior.setAlignment(Pos.CENTER_RIGHT);
        Button btnVolver = new Button("Volver al menú");
        btnVolver.setStyle("-fx-background-color: #D6D6F5; -fx-text-fill: #4B2CBF; -fx-font-weight: bold; -fx-padding: 10 25; -fx-background-radius: 8; -fx-cursor: hand;");
        btnVolver.setOnAction(e -> mainView.mostrarDashboard());
        barraInferior.getChildren().add(btnVolver);

        root.getChildren().addAll(topBox, tabla, barraInferior);
    }
}
