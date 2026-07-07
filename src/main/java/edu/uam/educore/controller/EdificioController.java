package edu.uam.educore.controller;

import edu.uam.educore.dao.Repositorio;
import edu.uam.educore.model.infraestructura.Aula;
import edu.uam.educore.model.infraestructura.Edificio;
import edu.uam.educore.model.infraestructura.TipoAula;
import java.util.List;

//test internio -- 1234   

//test 2 commit 

public class EdificioController {

    private final Repositorio<Edificio> repo;

    private int proximoId = 1;
    private int proximoIdAula = 1;

    public EdificioController(Repositorio<Edificio> repo) {
        this.repo = repo;
    }

    public Edificio registrar(String codigo, String nombre) throws Exception {

        if (codigo == null || codigo.isBlank()
                || nombre == null || nombre.isBlank()) {

            throw new IllegalArgumentException(
                    "Código y nombre son obligatorios");
        }

        Edificio edificio =
                new Edificio(
                        proximoId++,
                        codigo,
                        nombre);

        repo.guardar(edificio);

        return edificio;
    }

    public List<Edificio> listar() throws Exception {
        return repo.buscarTodos();
    }

    public Edificio buscarPorId(int id) throws Exception {
        return repo.buscarPorId(id).orElse(null);
    }

    public void eliminar(int id) throws Exception {

        Edificio edificio = buscarPorId(id);

        if (edificio == null) {
            throw new IllegalArgumentException(
                    "No existe edificio con ID " + id);
        }

        if (!edificio.getAulas().isEmpty()) {
            throw new IllegalArgumentException(
                    "No se puede eliminar el edificio porque tiene aulas registradas");
        }

        repo.eliminar(id);
    }

    public Aula agregarAula(
            int edificioId,
            String numero,
            int capacidad,
            TipoAula tipo)
            throws Exception {

        Edificio edificio = buscarPorId(edificioId);

        if (edificio == null) {
            throw new IllegalArgumentException(
                    "No existe edificio con ID " + edificioId);
        }

        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException(
                    "El número del aula es obligatorio");
        }

        if (capacidad <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser mayor que cero");
        }

        Aula aula =
                new Aula(
                        proximoIdAula++,
                        numero,
                        capacidad,
                        tipo,
                        edificio);

        edificio.agregarAula(aula);

        repo.actualizar(edificio);

        return aula;
    }

    public void eliminarAula(int idAula) throws Exception {

        boolean eliminada = false;

        for (Edificio edificio : repo.buscarTodos()) {

            Aula aulaEncontrada = null;

            for (Aula aula : edificio.getAulas()) {

                if (aula.getId() == idAula) {
                    aulaEncontrada = aula;
                    break;
                }
            }

            if (aulaEncontrada != null) {

                edificio.getAulas().remove(aulaEncontrada);

                repo.actualizar(edificio);

                eliminada = true;

                break;
            }
        }

        if (!eliminada) {
            throw new IllegalArgumentException(
                    "No se encontró un aula con ID " + idAula);
        }
    }
}