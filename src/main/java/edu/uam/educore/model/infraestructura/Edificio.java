package edu.uam.educore.model.infraestructura;

import java.util.ArrayList;
import java.util.List;

public class Edificio {
    private int id;
    private String codigo;
    private String nombre;
    private List<Aula> aulas = new ArrayList<>();

    public Edificio(int id, String codigo, String nombre) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    // --- Getters ---
    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    // --- Métodos de gestión de Aulas ---

    /**
     * Agrega un aula a la lista del edificio.
     */
    public void agregarAula(Aula aula) {
        this.aulas.add(aula);
    }

    /**
     * Busca un aula por su ID.
     * @return El objeto Aula si se encuentra, null en caso contrario.
     */
    public Aula buscarAula(int id) {
        for (Aula aula : aulas) {
            if (aula.getId() == id) {
                return aula;
            }
        }
        return null;
    }

    /**
     * Elimina un aula de la lista basándose en su ID.
     */
    public void eliminarAula(int id) {
        this.aulas.removeIf(a -> a.getId() == id);
    }

    // --- Otros métodos ---

    public String getInfo() {
        return codigo + " | " + nombre;
    }
}
