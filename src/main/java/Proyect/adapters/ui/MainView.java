package Proyect.adapters.ui;

import Proyect.entities.Usuario;
// Importamos las implementaciones MySQL
import Proyect.infrastructure.repositories.MySqlAnalisisRepositorio;
import Proyect.infrastructure.repositories.MySqlUsuarioRepositorio;
// Importamos las Interfaces (Ports)
import Proyect.usecases.ports.AnalisisRepositorio;
import Proyect.usecases.ports.UsuarioRepositorio;
import Proyect.usecases.services.RealizarAnalisisUseCase;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView {

    private final Stage stage;

    // AHORA USAMOS LAS INTERFACES, no las clases concretas.
    // Esto desacopla el código completamente.
    private final UsuarioRepositorio usuarioRepositorio;
    private final AnalisisRepositorio analisisRepositorio;

    private final RealizarAnalisisUseCase realizarAnalisisUseCase;

    private Usuario usuarioActual;

    public MainView(Stage stage) {

        this.stage = stage;

        // Aquí es donde decidimos usar MySQL.
        // Si mañana quieres cambiar a otra base de datos,
        // solo cambias estas dos líneas aquí.
        this.usuarioRepositorio = new MySqlUsuarioRepositorio();
        this.analisisRepositorio = new MySqlAnalisisRepositorio();

        // Inyectamos la implementación de MySQL al caso de uso
        this.realizarAnalisisUseCase = new RealizarAnalisisUseCase(analisisRepositorio);
    }

    public void iniciar() {
        mostrarLogin();
    }

    public void mostrarLogin() {
        LoginView view = new LoginView(this);
        stage.setScene(new Scene(view.getView(), 1000, 700));
        stage.setTitle("Sistema Sensorial Tilapia");
        stage.show();
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

    // Retornamos las interfaces, no las clases concretas
    public UsuarioRepositorio getUsuarioRepositorio() {
        return usuarioRepositorio;
    }

    public RealizarAnalisisUseCase getRealizarAnalisisUseCase() {
        return realizarAnalisisUseCase;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
}