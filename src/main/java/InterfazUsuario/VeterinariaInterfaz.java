package InterfazUsuario;
import DAO.*;
import Lambda.ComparadorTurnos;
import Lambda.ComparadorTurnosPorFecha;
import Lambda.FormatoString;
import Lambda.formato;
import entity.*;
import service.*;

import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
public class VeterinariaInterfaz {
    private Scanner scanner;
    private final MascotaService mascotaService;
    private final MedicoService medicoService;
    private final TurnoMedicoService turnoMedicoService;

    public VeterinariaInterfaz() {
        this.scanner = new Scanner(System.in);
        MascotaDAO mascotaDAO = new MascotaDAOImpl();
        mascotaService = new MascotaServiceImpl(mascotaDAO);
        TurnoMedicoDAO turnoMedicoDAO = new TurnoMedicoDAOImpl();
        turnoMedicoService = new TurnoMedicoServiceImpl(turnoMedicoDAO);
        MedicoDAO medicoDAO = new MedicoDAOImpl();
        medicoService = new MedicoServiceImpl(medicoDAO);
    }

    public void iniciar() {
        while (true) {

            System.out.println("Seleccione una opción:");
            System.out.println("1. Registrar mascota");
            System.out.println("2. Sacar turno");
            System.out.println("3. Ingresar como médico");
            System.out.println("4. Registrar Medico");
            System.out.println("5. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarMascota();
                    break;
                case 2:
                    sacarTurno(null);
                    break;
                case 3:
                    ingresarComoMedico();
                    break;
                case 4:
                    registrarMedico();
                    break;
                case 5:
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void registrarMascota() {
        FormatoString formatString = new formato();
        Mascota nuevaMascota = new Mascota();

        System.out.print("Ingrese el nombre de la mascota: ");
        String nombre = scanner.nextLine();
        nombre = formatString.darFormato(nombre);
        if(nombre.isEmpty()){System.out.println("Nombre invalido");}

        System.out.println();
        System.out.print("Ingrese la raza de la mascota: ");
        String raza = scanner.nextLine();
        raza = formatString.darFormato(raza);
        if(nombre.isEmpty()){System.out.println("raza invalida");}

        System.out.println();
        System.out.print("Ingrese el color del pelo de la mascota: ");
        String colorPelo = scanner.nextLine();
        colorPelo = formatString.darFormato(colorPelo);
        if(nombre.isEmpty()){System.out.println("Color de pelo invalido");}

        System.out.println();
        System.out.print("Ingrese la edad de la mascota: ");
        int edad = scanner.nextInt();
        if(nombre.isEmpty()){System.out.println("Edad invalida");}

        System.out.println();
        System.out.print("Ingrese el peso de la mascota: ");
        double peso = scanner.nextDouble();
        if(nombre.isEmpty()){System.out.println("Peso invalido");}

        System.out.println();
        System.out.print("Seleccione el tipo de mascota (1: Perro, 2: Gato): ");
        int tipoMascota = scanner.nextInt();


        switch (tipoMascota) {
            case 1:
                nuevaMascota = crearPerro(nombre, raza, colorPelo, edad, peso);
                agregarMascota(nuevaMascota);
                break;

            case 2:
                nuevaMascota = crearGato(nombre, raza, colorPelo, edad, peso);
                agregarMascota(nuevaMascota);
                break;
            default:
                System.out.println("Tipo de mascota no válido.");
        }

        System.out.println("Desea asignarle un turno a su mascota? (1: Si, 2: NO)");
        int entrada = scanner.nextInt();
        if(entrada==1){
            sacarTurno(nuevaMascota);
        }
    }
    private void sacarTurno(Mascota mascota) {
        FormatoString formatString = new formato();
        LocalDateTime fechaConvertida = null;
        Mascota mascotaNueva = new Mascota();
        if(mascota==null){
            System.out.print("Ingrese el nombre de la mascota: ");
            String nombre = scanner.nextLine();
            nombre = formatString.darFormato(nombre);
            List<Mascota> mascotas = mascotaService.buscarPorNombre(nombre);
            if(mascotas.isEmpty()){
                System.out.println("No se encuentran mascotas con el nombre: " + nombre);
                System.out.println();
                return;
            }else if(mascotas.size()>1){
                System.out.println("Se han encontrado " + mascotas.size() + " mascotas con el nombre: " + nombre);
                for(Mascota m : mascotas){
                    System.out.println(m.toString() + " - Nombre: " + m.getNombre() + " - Id " + m.getId() + " - Raza: " + m.getRaza());
                }
                System.out.println();
                scanner.nextLine();
                System.out.print("Ingrese el Id de la mascota que desea: ");
                int idMascota = scanner.nextInt();
                mascotaNueva = mascotaService.obtenerMascotaPorId(idMascota);
            } else{
                mascotaNueva = mascotas.get(0);
            }

            boolean estado = true;
            while(estado) {
                Scanner scanner = new Scanner(System.in);
                System.out.println();
                System.out.println("---------------------------------------------------------------");
                System.out.println("Atendemos de Lunes a Liernes de 8hs a 16hs");
                System.out.print("Ingrese una fecha y hora (Formato: yyyy-MM-dd HH:mm:ss):");
                String fechaString = scanner.nextLine();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                try {
                    fechaConvertida = LocalDateTime.parse(fechaString, formatter);

                    DayOfWeek dayOfWeek = fechaConvertida.getDayOfWeek();
                    LocalTime hora = fechaConvertida.toLocalTime();
                    if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                        System.out.println();
                        System.out.println("La fecha ingresada cae en un fin de semana");
                    } else if (hora.isBefore(LocalTime.of(8, 0)) || hora.isAfter(LocalTime.of(16, 0))) {
                        System.out.println();
                        System.out.println("La hora ingresada no está entre las 8:00 y las 16:00hs");
                    } else{
                        estado = false;
                    }
                } catch (Exception e) {
                    System.out.println();
                    System.out.println("---------------------------------------------------------------");
                    System.out.println("Error al procesar la fecha. Asegúrate de ingresarla en el formato correcto.");
                    System.out.println("---------------------------------------------------------------");
                    System.out.println();
                }
            }

            TurnoMedico miTurno = new TurnoMedico();
            miTurno.setFechaTurno(fechaConvertida);
            miTurno.setMascota(mascotaNueva);
            turnoMedicoService.crearTurnoMedico(miTurno);

            System.out.println("---------------------------------------------------------------");
            System.out.println("Turno registrado con exito");
            System.out.println("---------------------------------------------------------------");
        }

    }
    private void registrarMedico(){
        List<Medico> medicoList = medicoService.obtenerTodosLosMedicos();
        String nombreUsuario = new String();
        String contraseña;
        clearConsola();
        Scanner scanner = new Scanner(System.in);

        boolean estado = true;
        while (estado){
            boolean nombreUsuarioExistente = false;
            System.out.print("Ingrese el nombre de usuario: ");
            nombreUsuario = scanner.nextLine();

            for(Medico m : medicoList){
                if(m.getNombre().equals(nombreUsuario)){
                    nombreUsuarioExistente = true;
                    System.out.println("El nombre " + nombreUsuario + " ya esta registrado");
                    System.out.println();
                    System.out.println();
                    break;
                }
            }
            if (!nombreUsuarioExistente) {
                estado = false;
            }
        }

        System.out.print("Ingrese la contraseña:");
        contraseña = scanner.nextLine();

        Medico nuevoMedico = new Medico();
        try {
            nuevoMedico.setNombre(nombreUsuario);
            nuevoMedico.setContraseña(contraseña);
            medicoService.crearMedico(nuevoMedico);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al crear el médico. Por favor, inténtelo nuevamente.");
        }
        clearConsola();
        System.out.println("Registro exitoso.");
        System.out.println();
    }
    private void ingresarComoMedico(){
        ComparadorTurnos comparadorFecha = new ComparadorTurnosPorFecha();
        clearConsola();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese su nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();

        Medico medico = medicoService.obtenerMedicoPorNombre(nombreUsuario);

        if(medico == null) {
            System.out.println();
            System.out.println("Nombre de usuario incorrecto");
            clearConsola();
            return;
        } else{
            System.out.print("Ingrese su contraseña: ");
            String contrasena = scanner.nextLine();
            if(medico.getContraseña().equals(contrasena)){
                System.out.println("Bienvenido, Dr. " + nombreUsuario + "!");
            }
            else{
                System.out.println();
                System.out.println("Contraseña Incorrecta");
                clearConsola();
                return;
            }
        }
        clearConsola();
        List<TurnoMedico> turnosLibres = turnoMedicoService.obtenerTurnosLibres();

        Collections.sort(turnosLibres, comparadorFecha::compararTurnos);

        if(turnosLibres.isEmpty()){
            System.out.println("---------------------------------------------------------------");
            System.out.println();
            System.out.println("No Hay Turnos Disponibles");
            System.out.println();
            System.out.println("---------------------------------------------------------------");
            return;
        }
        System.out.println("Turnos Disponibles:");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-5s %-25s %-40s%n", "ID", "Fecha Turno", "Nombre de la Mascota");
        for (TurnoMedico turno : turnosLibres) {
            System.out.printf("%-5d %-25s %-40s%n",
                    turno.getId(),
                    formatoFechaTurno(turno.getFechaTurno()),
                    (turno.getMascota() != null) ? turno.getMascota().getNombre() : "Sin Mascota");
        }
        System.out.println("---------------------------------------------------------------");
        System.out.println();

        boolean estado = true;
        while (estado) {
            System.out.print("Ingrese el ID del turno que desea asignar (o 0 para salir): ");
            int id = scanner.nextInt();

            if (id == 0) {
                System.out.println("Operación cancelada.");
                clearConsola();
                break;
            }
            TurnoMedico nuevoTurno = turnoMedicoService.obtenerTurnoMedicoPorId(id);

            if(!turnoLibreMedico(medico, nuevoTurno.getFechaTurno())){
                System.out.println("Ya tienes un turno asignado para esa fecha");
                clearConsola();
            } else if (nuevoTurno == null) {
                clearConsola();
                System.out.println("ID incorrecto. Por favor, ingrese un ID válido.");
            } else {
                nuevoTurno.setMedico(medico);
                medico.addTurno(nuevoTurno);
                medicoService.actualizarMedico(medico);
                turnoMedicoService.actualizarTurnoMedico(nuevoTurno);
                estado = false;
                clearConsola();
                System.out.println("Turno asignado correctamente.");
                System.out.println();
            }
        }
    }
    private Perro crearPerro(String nombre, String raza, String colorPelo, int edad, double peso) {
        Perro nuevoPerro = new Perro();
        nuevoPerro.setNombre(nombre);
        nuevoPerro.setRaza(raza);
        nuevoPerro.setColorPelo(colorPelo);
        nuevoPerro.setEdad(edad);
        nuevoPerro.setPeso(peso);
        return nuevoPerro;
    }
    private Gato crearGato(String nombre, String raza, String colorPelo, int edad, double peso) {
        Gato nuevoGato = new Gato();
        nuevoGato.setNombre(nombre);
        nuevoGato.setRaza(raza);
        nuevoGato.setColorPelo(colorPelo);
        nuevoGato.setEdad(edad);
        nuevoGato.setPeso(peso);
        return nuevoGato;
    }
    private void agregarMascota(Mascota mascota) {
        if (mascota != null) {
            try {
                mascotaService.crearMascota(mascota);
                System.out.println();
                System.out.println("------------------------------------------------------------------------");
                System.out.println("Mascota registrada exitosamente.");
                System.out.println("------------------------------------------------------------------------");
                System.out.println();
            } catch (Exception e) {
                System.out.println();
                System.out.println("------------------------------------------------------------------------");
                System.out.println("Error al registrar la mascota: " + e.getMessage());
                System.out.println("------------------------------------------------------------------------");
                System.out.println();
            }
        }
    }
    private boolean turnoLibreMedico(Medico medico, LocalDateTime fecha) {
        return medico.getTurnos().stream()
                .noneMatch(turno -> turno.getFechaTurno().equals(fecha));
    }
    private static String formatoFechaTurno(LocalDateTime fechaTurno) {
        return String.format("%s %02d-%02d-%04d %02d:%02d",
                fechaTurno.getDayOfWeek(),
                fechaTurno.getDayOfMonth(),
                fechaTurno.getMonthValue(),
                fechaTurno.getYear(),
                fechaTurno.getHour(),
                fechaTurno.getMinute());
    }
    private static void clearConsola() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }

}
