package Proyect.adapters.ui;

import Proyect.entities.Usuario;
import Proyect.entities.RegistroEstado;
import Proyect.infrastructure.repositories.MySqlAnalisisRepositorio;
import Proyect.infrastructure.repositories.MySqlUsuarioRepositorio;
import Proyect.infrastructure.repositories.SqlReporteRepository;
import Proyect.infrastructure.repositories.MySqlRegistroEstadoRepositorio;
import Proyect.usecases.services.ConsultarNormaNtc1443UseCase;
import Proyect.usecases.services.GenerarReporteUseCase;
import Proyect.usecases.services.RealizarAnalisisUseCase;
import Proyect.usecases.services.RegistrarEstadoUseCase;
import Proyect.usecases.services.GestionarEstacionUseCase;
import Proyect.usecases.services.ConsultarReportesCalidadUseCase;
import Proyect.usecases.services.ConsultarEstacionUseCase;
import Proyect.usecases.ports.AnalisisRepositorio;
import Proyect.usecases.ports.ReporteRepository;
import Proyect.usecases.ports.UsuarioRepositorio;
import Proyect.usecases.ports.RegistroEstadoRepositorio;
import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView {

    private final Stage stage;
    private final HostServices hostServices;

    // Interfaces de Infraestructura (Ports)
    private final UsuarioRepositorio usuarioRepositorio;
    private final AnalisisRepositorio analisisRepositorio;
    private final ReporteRepository reporteRepository;
    private final RegistroEstadoRepositorio registroEstadoRepositorio;

    // Casos de Uso del Sistema (Lógica de Negocio)
    private final RealizarAnalisisUseCase realizarAnalisisUseCase;
    private final GenerarReporteUseCase generarReporteUseCase;
    private final ConsultarNormaNtc1443UseCase consultarNormaNtc1443UseCase;
    private final RegistrarEstadoUseCase registrarEstadoUseCase;
    private final GestionarEstacionUseCase gestionarEstacionUseCase;
    private final ConsultarReportesCalidadUseCase consultarReportesCalidadUseCase;
    private final ConsultarEstacionUseCase consultarEstacionUseCase;

    private Usuario usuarioActual;

    public MainView(Stage stage, HostServices hostServices) {
        this.stage = stage;
        this.hostServices = hostServices;

        // 1. Inicialización de Repositorios de Datos
        this.usuarioRepositorio = new MySqlUsuarioRepositorio();
        this.analisisRepositorio = new MySqlAnalisisRepositorio();
        this.reporteRepository = new SqlReporteRepository();
        this.registroEstadoRepositorio = new MySqlRegistroEstadoRepositorio();

        // 2. Inyección de Dependencias a los Casos de Uso correspondientes
        this.realizarAnalisisUseCase = new RealizarAnalisisUseCase(analisisRepositorio);
        this.generarReporteUseCase = new GenerarReporteUseCase(reporteRepository);
        this.consultarNormaNtc1443UseCase = new ConsultarNormaNtc1443UseCase();
        this.registrarEstadoUseCase = new RegistrarEstadoUseCase(registroEstadoRepositorio);
        this.gestionarEstacionUseCase = new GestionarEstacionUseCase(registroEstadoRepositorio);
        this.consultarReportesCalidadUseCase = new ConsultarReportesCalidadUseCase(analisisRepositorio);
        this.consultarEstacionUseCase = new ConsultarEstacionUseCase(registroEstadoRepositorio);
    }

    public void iniciar() {
        mostrarLogin();
    }

    // --- MÉTODOS DE FLUJO DE PANTALLAS (GEOFENCING UI) ---

    public void mostrarLogin() {
        LoginView view = new LoginView(this);
        stage.setScene(new Scene(view.getView(), 1000, 700));
        stage.setTitle("Sistema Sensorial Tilapia");
        stage.show();
    }

    public void mostrarMain() {
        stage.setScene(stage.getScene());
    }

    public void mostrarRegistro() {
        RegistroView view = new RegistroView(this);
        stage.setScene(new Scene(view.getView(), 1000, 700));
    }

    public void mostrarDashboard() {
        DashboardView view = new DashboardView(this);
        stage.setScene(new Scene(view.getView(), 1200, 800));
    }

    public void mostrarAnalisis() {
        AnalisisView view = new AnalisisView(this);
        stage.setScene(new Scene(view.getView(), 1200, 800));
    }

    public void mostrarReportes() {
        ReportesView view = new ReportesView(this);
        stage.setScene(new Scene(view.getView(), 1200, 800));
    }

    public void mostrarConsultarNorma() {
        ConsultarNormaView view = new ConsultarNormaView(this);
        stage.setScene(new Scene(view.getView(), 1200, 800));
    }

    public void mostrarRegistrarEstado() {
        RegistrarEstadoView view = new RegistrarEstadoView(this);
        stage.setScene(new Scene(view.getView(), 1200, 800));
    }

    public void mostrarGestionarEstacion() {
        GestionarEstacionView view = new GestionarEstacionView(this, gestionarEstacionUseCase);
        stage.setScene(new Scene(view.getView(), 1200, 800));
    }

    public void mostrarRegistrarEstadoEdicion(RegistroEstado registroAEditar) {
        EditarEstadoView view = new EditarEstadoView(this, registroAEditar);
        stage.setScene(new Scene(view.getView(), 1200, 800));
    }

    public void mostrarConsultarReportes() {
        ConsultarReportesView view = new ConsultarReportesView(this, consultarReportesCalidadUseCase);
        stage.setScene(new Scene(view.getView(), 1200, 800));
    }

    public void mostrarConsultarEstacion() {
        ConsultarEstacionView view = new ConsultarEstacionView(this, consultarEstacionUseCase);
        stage.setScene(new Scene(view.getView(), 1200, 800));
    }

    // --- GETTERS Y SETTERS COMPLETOS ---

    public UsuarioRepositorio getUsuarioRepositorio() {
        return usuarioRepositorio;
    }

    public RealizarAnalisisUseCase getRealizarAnalisisUseCase() {
        return realizarAnalisisUseCase;
    }

    public GenerarReporteUseCase getGenerarReporteUseCase() {
        return generarReporteUseCase;
    }

    public ConsultarNormaNtc1443UseCase getConsultarNormaNtc1443UseCase() {
        return consultarNormaNtc1443UseCase;
    }

    public RegistrarEstadoUseCase getRegistrarEstadoUseCase() {
        return registrarEstadoUseCase;
    }

    public GestionarEstacionUseCase getGestionarEstacionUseCase() {
        return gestionarEstacionUseCase;
    }

    public ConsultarReportesCalidadUseCase getConsultarReportesCalidadUseCase() {
        return consultarReportesCalidadUseCase;
    }

    public ConsultarEstacionUseCase getConsultarEstacionUseCase() {
        return consultarEstacionUseCase;
    }

    public HostServices getHostServices() {
        return hostServices;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
}