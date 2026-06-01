package Proyect.adapters.ui;

import Proyect.entities.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import java.util.Optional;

public class DashboardView {

    private BorderPane root;
    private MainView mainView;

    public Parent getView() { return root; }

    public DashboardView(MainView mainView) {
        this.mainView = mainView;
        root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F7FB;");

        String rolUsuario = mainView.getUsuarioActual().getRol().trim();

        // --- SIDEBAR DINÁMICO ---
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(25));
        sidebar.setPrefWidth(300);
        sidebar.setStyle("-fx-background-color: linear-gradient(to bottom, #6C4DFF, #4B2CBF);");

        Label logo = new Label("Tilapia\nSensorial");
        logo.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");

        Label lblUser = new Label("Usuario: " + mainView.getUsuarioActual().getNombre() + "\nPerfil: " + rolUsuario);
        lblUser.setStyle("-fx-text-fill: #E0D6FF; -fx-font-size: 13px; -fx-font-weight: bold;");

        sidebar.getChildren().addAll(logo, lblUser, new Separator());

        // --- PANEL CENTRAL ---
        VBox contenidoPrincipal = new VBox(25);
        contenidoPrincipal.setPadding(new Insets(40));

        Label tituloPanel = new Label("Panel de Operaciones - " + rolUsuario);
        tituloPanel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #2D2D2D;");

        // BANNER
        HBox bannerBienvenida = new HBox(30);
        bannerBienvenida.setPadding(new Insets(30));
        bannerBienvenida.setAlignment(Pos.CENTER_LEFT);
        bannerBienvenida.setStyle("-fx-background-color: linear-gradient(to right, #6C4DFF, #8A6FFF); -fx-background-radius: 20;");

        VBox textoBanner = new VBox(10);
        Label saludo = new Label("¡Bienvenido de nuevo, " + mainView.getUsuarioActual().getNombre() + "!");
        saludo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");
        Label subsaludo = new Label("Que tengas un excelente día de trabajo");
        subsaludo.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0D6FF;");
        textoBanner.getChildren().addAll(saludo, subsaludo);

        StackPane contenedorPez = crearFiguritaTilapia();
        Region spacerBanner = new Region();
        HBox.setHgrow(spacerBanner, Priority.ALWAYS);

        bannerBienvenida.getChildren().addAll(textoBanner, spacerBanner, contenedorPez);
        contenidoPrincipal.getChildren().addAll(tituloPanel, bannerBienvenida);

        FlowPane cardsContainer = new FlowPane(20, 20);

        // --- CONTROL Y ASIGNACIÓN DE ACCIONES POR ROL ---
        if (rolUsuario.equalsIgnoreCase("Experto Evaluador")) {
            Button btnAnalisis = crearBotonsidebar("Realizar Análisis Sensorial");
            btnAnalisis.setOnAction(e -> mainView.mostrarAnalisis());

            Button btnNorma = crearBotonsidebar("Consultar Norma NTC 1443");
            btnNorma.setOnAction(e -> mainView.mostrarConsultarNorma());

            Button btnReportes = crearBotonsidebar("Generar Reportes");
            btnReportes.setOnAction(e -> mainView.mostrarReportes());

            sidebar.getChildren().addAll(btnAnalisis, btnNorma, btnReportes);

            VBox cardAnalisis = crearCard("Nuevo Análisis", "Inspección de ojos, agallas, abdomen y olor.");
            cardAnalisis.setOnMouseClicked(e -> mainView.mostrarAnalisis());

            VBox cardNorma = crearCard("Normativa", "Parámetros tolerables de frescura.");
            cardNorma.setOnMouseClicked(e -> mainView.mostrarConsultarNorma());

            VBox cardReportes = crearCard("Reportes", "Consolidados listos para distribución.");
            cardReportes.setOnMouseClicked(e -> mainView.mostrarReportes());

            cardsContainer.getChildren().addAll(cardAnalisis, cardNorma, cardReportes);

        } else if (rolUsuario.equalsIgnoreCase("Piscicultor")) {
            Button btnRegistrar = crearBotonsidebar("Registrar Estado Estación");
            btnRegistrar.setOnAction(e -> mainView.mostrarRegistrarEstado());

            Button btnGestionar = crearBotonsidebar("Gestionar Estación Piscícola");
            btnGestionar.setOnAction(e -> mainView.mostrarGestionarEstacion());

            sidebar.getChildren().addAll(btnRegistrar, btnGestionar);

            VBox cardRegistrar = crearCard("Registro de Estación", "Ingresar condiciones fisicoquímicas del agua.");
            cardRegistrar.setOnMouseClicked(e -> mainView.mostrarRegistrarEstado());

            VBox cardGestionar = crearCard("Gestión de Fincas", "Administrar estanques.");
            cardGestionar.setOnMouseClicked(e -> mainView.mostrarGestionarEstacion());

            cardsContainer.getChildren().addAll(cardRegistrar, cardGestionar);

        } else if (rolUsuario.equalsIgnoreCase("Investigador")) {
            // ENLAZADO DE ACCIONES DEL INVESTIGADOR (TODAS COMPLEMENTADAS)
            Button btnConsReportes = crearBotonsidebar("Consultar Reportes de Calidad");
            btnConsReportes.setOnAction(e -> mainView.mostrarConsultarReportes());

            Button btnConsEstacion = crearBotonsidebar("Consultar Estación Piscícola");
            btnConsEstacion.setOnAction(e -> mainView.mostrarConsultarEstacion()); // COMPLETO

            sidebar.getChildren().addAll(btnConsReportes, btnConsEstacion);

            VBox cardReportesHistoricos = crearCard("Reportes Históricos", "Curvas de degradación de la carne y análisis sensorial.");
            cardReportesHistoricos.setOnMouseClicked(e -> mainView.mostrarConsultarReportes());

            VBox cardAuditoria = crearCard("Auditoría", "Correlación estanque-calidad de las muestras.");
            cardAuditoria.setOnMouseClicked(e -> mainView.mostrarConsultarEstacion()); // COMPLETO

            cardsContainer.getChildren().addAll(cardReportesHistoricos, cardAuditoria);
        }

        // --- BOTONES INFERIORES ---
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        sidebar.getChildren().add(spacer);

        Button btnEliminarCuenta = new Button("Eliminar Cuenta");
        btnEliminarCuenta.setMaxWidth(Double.MAX_VALUE);
        btnEliminarCuenta.setStyle("-fx-background-color: transparent; -fx-text-fill: #FF8F8F; -fx-padding: 8; -fx-cursor: hand; -fx-font-size: 13px; -fx-alignment: center-left;");

        btnEliminarCuenta.setOnAction(e -> {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Estás seguro de eliminar tu cuenta?");
            confirmacion.setContentText("Esta acción es irreversible.");

            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isPresent() && legacyEquals(resultado.get(), ButtonType.OK)) {
                String correo = mainView.getUsuarioActual().getCorreo();
                mainView.getUsuarioRepositorio().eliminar(correo);

                mainView.setUsuarioActual(null);
                new Alert(Alert.AlertType.INFORMATION, "Cuenta eliminada exitosamente.").showAndWait();
                mainView.mostrarLogin();
            }
        });

        Button btnSalir = new Button("Cerrar Sesión");
        btnSalir.setMaxWidth(Double.MAX_VALUE);
        btnSalir.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; -fx-padding: 10; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-weight: bold;");
        btnSalir.setOnAction(e -> mainView.mostrarLogin());

        sidebar.getChildren().addAll(btnEliminarCuenta, btnSalir);
        contenidoPrincipal.getChildren().add(cardsContainer);

        root.setLeft(sidebar);
        root.setCenter(contenidoPrincipal);
    }

    private StackPane crearFiguritaTilapia() {
        StackPane contenedor = new StackPane();
        contenedor.setPrefSize(180, 100);
        Group pez = new Group();
        Polygon cola = new Polygon(); cola.getPoints().addAll(new Double[]{-40.0, 0.0, -75.0, -25.0, -75.0, 25.0}); cola.setFill(Color.web("#BCA6FF"));
        Polygon aletaDorsal = new Polygon(); aletaDorsal.getPoints().addAll(new Double[]{-15.0, -25.0, 25.0, -23.0, 5.0, -42.0}); aletaDorsal.setFill(Color.web("#5B3CC4"));
        Ellipse cuerpo = new Ellipse(45, 28); cuerpo.setFill(Color.WHITE);
        Circle ojo = new Circle(25, -8, 5); ojo.setFill(Color.web("#4B2CBF"));
        Circle pupila = new Circle(26, -8, 2); pupila.setFill(Color.WHITE);
        Ellipse agalla = new Ellipse(5, 0, 3, 12); agalla.setFill(Color.web("#E0D6FF"));
        pez.getChildren().addAll(cola, aletaDorsal, cuerpo, agalla, ojo, pupila);
        contenedor.getChildren().add(pez);
        return contenedor;
    }

    private Button crearBotonsidebar(String texto) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        String normal = "-fx-background-color: transparent; -fx-text-fill: #E0D6FF; -fx-font-size: 14px; -fx-padding: 10 12; -fx-alignment: center-left; -fx-cursor: hand;";
        String hover = "-fx-background-color: rgba(255,255,255,0.12); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 12; -fx-alignment: center-left; -fx-background-radius: 8; -fx-cursor: hand;";
        btn.setStyle(normal);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(normal));
        return btn;
    }

    private VBox crearCard(String titulo, String descripcion) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setPrefWidth(260);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 15, 0, 0, 4); -fx-cursor: hand;");
        Label t = new Label(titulo); t.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #4B2CBF;");
        Label d = new Label(descripcion); d.setStyle("-fx-font-size: 13px; -fx-text-fill: #666;"); d.setWrapText(true);
        card.getChildren().addAll(t, d);
        return card;
    }

    private boolean legacyEquals(Object a, Object b) {
        return a != null && a.equals(b);
    }
}