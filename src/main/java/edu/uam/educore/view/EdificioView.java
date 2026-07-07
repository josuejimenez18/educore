package edu.uam.educore.view;

import edu.uam.educore.controller.EdificioController;
import edu.uam.educore.dao.Repositorio;
import edu.uam.educore.model.infraestructura.Aula;
import edu.uam.educore.model.infraestructura.Edificio;
import edu.uam.educore.model.infraestructura.TipoAula;
import java.util.List;
import java.util.Scanner;

public class EdificioView extends VistaBase {

  private final EdificioController controller;

  public EdificioView(Scanner scanner, Repositorio<Edificio> repo) {

    super(scanner);
    this.controller = new EdificioController(repo);
  }

  public void iniciar() {

    int opcion;

    do {

      System.out.println("\n--- MENÚ EDIFICIOS ---");
      System.out.println("1. Registrar edificio");
      System.out.println("2. Listar edificios");
      System.out.println("3. Buscar edificio");
      System.out.println("5. Agregar aula");
      System.out.println("6. Listar aulas");
      System.out.println("7. Eliminar aula");
      System.out.println("0. Salir");

      opcion = leerEntero("Seleccione una opción: ");

      switch (opcion) {
        case 1:
          registrarEdificio();
          break;

        case 2:
          listarEdificios();
          break;

        case 3:
          buscarEdificio();
          break;

        case 5:
          agregarAulaAEdificio();
          break;

        case 6:
          listarAulasDeEdificio();
          break;

        case 7:
          eliminarAulaDeEdificio();
          break;

        case 0:
          System.out.println("Saliendo...");
          break;

        default:
          System.out.println("Opción inválida");
      }

    } while (opcion != 0);
  }

  private void registrarEdificio() {

    String codigo = leerTexto("Código del edificio");

    String nombre = leerTexto("Nombre del edificio");

    try {

      Edificio edificio = controller.registrar(codigo, nombre);

      mostrarMensaje("Edificio registrado:\n" + edificio.getId() + " | " + edificio.getInfo());

    } catch (Exception ex) {

      mostrarError(ex.getMessage());
    }
  }

  private void listarEdificios() {

    try {

      List<Edificio> edificios = controller.listar();

      if (edificios.isEmpty()) {

        mostrarMensaje("No hay edificios registrados");

        return;
      }

      System.out.println("\n--- EDIFICIOS REGISTRADOS ---");

      for (Edificio edificio : edificios) {

        System.out.println(edificio.getId() + " | " + edificio.getInfo());
      }

    } catch (Exception ex) {

      mostrarError(ex.getMessage());
    }
  }

  private void buscarEdificio() {

    int id = leerEntero("ID del edificio");

    try {

      Edificio edificio = controller.buscarPorId(id);

      if (edificio == null) {

        mostrarError("No existe edificio con ID " + id);

        return;
      }

      System.out.println("\n--- EDIFICIO ENCONTRADO ---");

      System.out.println(edificio.getId() + " | " + edificio.getInfo());

    } catch (Exception ex) {

      mostrarError(ex.getMessage());
    }
  }

  private void agregarAulaAEdificio() {

    int idEdificio = leerEntero("ID del edificio");

    String numero = leerTexto("Número del aula");

    int capacidad = leerEntero("Capacidad");

    System.out.println("\nTipos de aula");
    System.out.println("0. REGULAR");
    System.out.println("1. LABORATORIO");
    System.out.println("2. AUDITORIO");

    int opcionTipo = leerEntero("Seleccione una opción");

    try {

      TipoAula tipo = TipoAula.values()[opcionTipo];

      Aula aula = controller.agregarAula(idEdificio, numero, capacidad, tipo);

      mostrarMensaje("Aula registrada:\n" + aula.getId() + " | " + aula.getInfo());

    } catch (ArrayIndexOutOfBoundsException ex) {

      mostrarError("Tipo de aula inválido");

    } catch (Exception ex) {

      mostrarError(ex.getMessage());
    }
  }

  private void listarAulasDeEdificio() {

    int idEdificio = leerEntero("ID del edificio");

    try {

      Edificio edificio = controller.buscarPorId(idEdificio);

      if (edificio == null) {

        mostrarError("No existe edificio con ID " + idEdificio);

        return;
      }

      List<Aula> aulas = edificio.getAulas();

      if (aulas.isEmpty()) {

        mostrarMensaje("Este edificio no tiene aulas registradas");

        return;
      }

      System.out.println("\n--- AULAS DEL EDIFICIO ---");

      for (Aula aula : aulas) {

        System.out.println(aula.getId() + " | " + aula.getInfo());
      }

    } catch (Exception ex) {

      mostrarError(ex.getMessage());
    }
  }

  private void eliminarAulaDeEdificio() {

    int idAula = leerEntero("ID del aula");

    String confirmacion = leerTexto("¿Está seguro de eliminar el aula? (S/N)");

    if (!confirmacion.equalsIgnoreCase("S")) {

      mostrarMensaje("Operación cancelada");
      return;
    }

    try {

      controller.eliminarAula(idAula);

      mostrarMensaje("Aula eliminada correctamente");

    } catch (Exception ex) {

      mostrarError(ex.getMessage());
    }
  }
}
