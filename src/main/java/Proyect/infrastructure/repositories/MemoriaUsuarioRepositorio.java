package Proyect.infrastructure.repositories;

import Proyect.entities.Usuario;
import Proyect.usecases.ports.UsuarioRepositorio;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MemoriaUsuarioRepositorio implements UsuarioRepositorio {
    private final String ARCHIVO = "usuarios.txt";

    @Override
    public void guardar(Usuario usuario) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            writer.write(usuario.getNombre() + ";" + usuario.getCorreo() + ";" + usuario.getPassword() + ";" + usuario.getRol());
            writer.newLine();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public Usuario obtenerPorCorreo(String correo) {
        return obtenerTodos().stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo))
                .findFirst().orElse(null);
    }

    @Override
    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return usuarios;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 4) {
                    usuarios.add(new Usuario(d[0], d[1], d[2], d[3]));
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return usuarios;
    }

    @Override
    public void eliminar(String correo) {
        List<Usuario> todos = obtenerTodos();
        todos.removeIf(u -> u.getCorreo().equalsIgnoreCase(correo));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, false))) {
            for (Usuario u : todos) {
                writer.write(u.getNombre() + ";" + u.getCorreo() + ";" + u.getPassword() + ";" + u.getRol());
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}