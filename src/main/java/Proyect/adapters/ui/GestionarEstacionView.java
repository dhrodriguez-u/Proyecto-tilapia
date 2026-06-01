package Proyect.adapters.ui;

import Proyect.entities.RegistroEstado;
import Proyect.entities.Usuario;
import Proyect.usecases.services.GestionarEstacionUseCase;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.util.Optional;

public class GestionarEstacionView {

    private final VBox root;
    private final MainView mainView;
    private final GestionarEstacionUseCase useCase;
    private TableView<RegistroEstado> tabla;

    public Parent getView() { return root; }

    public GestionarEstacionView(MainView mainView, GestionarEstacionUseCase useCase) {
        this.mainView = mainView;
        this.useCase = useCase;

        root = new VBox(25);
        root.setPadding(new Insets(35));
        root.setStyle("-fx-background-color: #F7F7FC;");

        // ENCABEZADO
        VBox headerBox = new VBox(8);
        Label titulo = new Label("Gestión de Estaciones y Monitoreos");
        titulo.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #5B3CC4;");
        Label subtitulo = new Label("Consulte, edite o elimine los registros de calidad de agua de sus estanques.");
        subtitulo.setStyle("-fx-font-size: 14px; -fx-text-fill: #7F8C8D;");
        headerBox.getChildren().addAll(titulo, subtitulo);

        // CONFIGURACIÓN DE LA TABLA
        tabla = new TableView<>();
        tabla.setPrefHeight(450);
        tabla.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-selection-bar: #E0D6FF;");

        TableColumn<RegistroEstado, String> colEstanque = new TableColumn<>("Identificador Estanque");
        colEstanque.setCellValueFactory(new PropertyValueFactory<>("estacionNombre"));
        colEstanque.setPrefWidth(180);

        TableColumn<RegistroEstado, Double> colTemp = new TableColumn<>("Temperatura (°C)");
        colTemp.setCellValueFactory(new PropertyValueFactory<>("temperatura"));
        colTemp.setPrefWidth(120);

        TableColumn<RegistroEstado, Double> colPh = new TableColumn<>("pH");
        colPh.setCellValueFactory(new PropertyValueFactory<>("ph"));
        colPh.setPrefWidth(80);

        TableColumn<RegistroEstado, Double> colOxigeno = new TableColumn<>("Oxígeno (mg/L)");
        colOxigeno.setCellValueFactory(new PropertyValueFactory<>("oxigenoDisuelto"));
        colOxigeno.setPrefWidth(120);

        TableColumn<RegistroEstado, Double> colTurbidez = new TableColumn<>("Turbidez (cm)");
        colTurbidez.setCellValueFactory(new PropertyValueFactory<>("turbidez"));
        colTurbidez.setPrefWidth(110);

        TableColumn<RegistroEstado, String> colAlerta = new TableColumn<>("Estado Alerta");
        colAlerta.setCellValueFactory(new PropertyValueFactory<>("alerta"));
        colAlerta.setPrefWidth(160);

        TableColumn<RegistroEstado, String> colObservaciones = new TableColumn<>("Observaciones Técnicas");
        colObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        colObservaciones.setPrefWidth(250);

        tabla.getColumns().addAll(colEstanque, colTemp, colPh, colOxigeno, colTurbidez, colAlerta, colObservaciones);

        // 🔒 FILTRADO EN CALIENTE DESDE LA BASE DE DATOS:
        // Se le envía el usuario activo al UseCase para que decida si muestra todo (Investigador)
        // o si discrimina trayendo solo lo de este Piscicultor de forma aislada.
        Usuario usuarioActivo = mainView.getUsuarioActual();
        tabla.getItems().addAll(useCase.consultarRegistros(usuarioActivo));

        // --- BARRA DE ACCIONES INFERIORES ---
        HBox barraAcciones = new HBox(15);
        barraAcciones.setAlignment(Pos.CENTER_RIGHT);

        Button btnRegresar = new Button("⬅ Regresar al Menú");
        btnRegresar.setStyle("-fx-background-color: #D6D6F5; -fx-text-fill: #4B2CBF; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");
        btnRegresar.setOnAction(e -> mainView.mostrarDashboard());

        Button btnEditar = new Button("✏ Editar Seleccionado");
        btnEditar.setStyle("-fx-background-color: #2ECC71; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");
        btnEditar.setOnAction(e -> editarRegistro());

        Button btnEliminar = new Button("🗑 Eliminar Seleccionado");
        btnEliminar.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");
        btnEliminar.setOnAction(e -> eliminarRegistro());

        barraAcciones.getChildren().addAll(btnRegresar, btnEditar, btnEliminar);
        root.getChildren().addAll(headerBox, tabla, barraAcciones);
    }

    private void editarRegistro() {
        RegistroEstado seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            new Alert(Alert.AlertType.WARNING, "Por favor, seleccione un registro para editar.").showAndWait();
            return;
        }
        mainView.mostrarRegistrarEstadoEdicion(seleccionado);
    }

    private void eliminarRegistro() {
        RegistroEstado seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            new Alert(Alert.AlertType.WARNING, "Por favor, seleccione un registro para eliminar.").showAndWait();
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar el registro de la estación: " + seleccionado.getEstacionNombre() + "?");
        confirmacion.setContentText("Esta operación borrará el registro permanentemente de MySQL.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            String mensaje = useCase.eliminarRegistro(seleccionado.getId(), seleccionado.getEstacionNombre());
            tabla.getItems().remove(seleccionado);
            new Alert(Alert.AlertType.INFORMATION, mensaje).showAndWait();
        }
    }
}
