package Aplicacion;

import Acceso.*;
import Ficha.*;
import Login.*;
import sun.rmi.runtime.Log;

import java.io.DataInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AplicacionConsola {
    Scanner scanner = new Scanner(System.in);

    public DatosCredenciales comprobarCredenciales(List<Login> usuarios, LoginAccessDB loginAccessDB) {

        boolean salirIS = false;
        int contador = 0;
        DatosCredenciales dc = new DatosCredenciales();


        do {
            System.out.println("Introduzca su id de usuario");
            String idac = scanner.nextLine();
            int entIdUser = 0;
            if (idac.equals("")) {
                entIdUser = 0;
            } else {
                try {
                    entIdUser = Integer.parseInt(idac);
                } catch (Exception e) {
                    System.err.println("Solo se aceptan valores numericos");
                    break;
                }
            }


            if (loginAccessDB.idInside(entIdUser, usuarios)) {
                dc.posUser = loginAccessDB.getPosition(entIdUser, usuarios);
            } else {
                System.out.println("las el id indroducido no esta en el sistrma");
            }

            System.out.println("introduzca su contraseña");
            String entPassw = scanner.nextLine();

            if (usuarios.get(dc.posUser).getPassword().equals(entPassw)) {
                salirIS = true;
                dc.credencialesCorrectas = true;

            } else {
                System.out.println("la contraseña introducida no es valida para el usuario: " +
                        usuarios.get(dc.posUser).getUser() + " con el id: " + entIdUser);
            }

            if (dc.credencialesCorrectas == false) {
                if (contador < 5) {
                    contador++;
                }
            }
            if ((contador >= 5) && dc.credencialesCorrectas == false) {
                boolean salirfallos = false;
                do {
                    System.out.println("\nHa fallado 5 veces, que desea hacer: \n1-intentarlo de nuevo \n2-salir");
                    String eleccionFallos = scanner.nextLine();

                    if (eleccionFallos.equals("1")) {
                        System.out.println("Continuando:");
                        contador = 0;
                        salirfallos = true;
                    } else if (eleccionFallos.equals("2")) {
                        salirfallos = true;
                        salirIS = true;

                    }
                } while (salirfallos == false);
            }
        } while (salirIS == false);
        return dc;
    }

    public void mostrarFicha(Ficha fichaAmostrar) {
        System.out.println("ID: " + fichaAmostrar.getIdpersonaje() +
                " |Nombre: " + fichaAmostrar.getNombre() +
                " |Nivel: " + fichaAmostrar.getNivel() +
                " |Clase: " + fichaAmostrar.getClase() +
                " |Raza: " + fichaAmostrar.getRaza() +
                " |Alineamiento: " + fichaAmostrar.getAlin() +
                " |Transfondo: " + fichaAmostrar.getTransfondo() + "\n");
    }

    public void mostrarDEditarFicha(Ficha fichaAeditar) {
        System.out.println(
                "1-Nombre: " + fichaAeditar.getNombre() +
                        " |2-Nivel: " + fichaAeditar.getNivel() +
                        " |3-Clase: " + fichaAeditar.getClase() +
                        " |4-Raza: " + fichaAeditar.getRaza() +
                        " |5-Alineamiento: " + fichaAeditar.getAlin() +
                        " |6-Transfondo: " + fichaAeditar.getTransfondo() +
                        "|7-Guardar y salir" +
                        "|8-Salir sin guardar");

    }

    public class DatosCredenciales {
        boolean credencialesCorrectas = false;
        int posUser = 0;

        public boolean isCredencialesCorrectas() {
            return credencialesCorrectas;
        }

        public void setCredencialesCorrectas(boolean credencialesCorrectas) {
            this.credencialesCorrectas = credencialesCorrectas;
        }

        public int getPosUser() {
            return posUser;
        }

        public void setPosUser(int posUser) {
            this.posUser = posUser;
        }
    }

    public List<Ficha> getFichasPorUsuario(List<Ficha> fichas, List<Acceso> accesos, AccesoAccesDB accesoAccesDB, FichaAccessDB fichaAccessDB, int idlogeado) {
        List<Acceso> accesosPorUsuario = accesoAccesDB.accesosPorLogin(idlogeado, accesos);
        List<Ficha> fichasPorUsuario = new ArrayList<>();

        for (int i = 0; i < accesosPorUsuario.size(); i++) {
            if (fichaAccessDB.idInside(accesosPorUsuario.get(i).getIdficha(), fichas)) {
                //System.out.println("-" + fichas.get(fichaAccessDB.getPosition(accesosPorUsuario.get(i).getIdficha(), fichas)).getNombre());
                fichasPorUsuario.add(fichas.get(fichaAccessDB.getPosition(accesosPorUsuario.get(i).getIdficha(), fichas)));
            }
        }
        return fichasPorUsuario;
    }

    public void editarFicha(List<Ficha> fichasPorUsuario, FichaAccessDB fichaAccessDB, AplicacionConsola app) throws SQLException {
        boolean salirEl2 = false;
        do {

            System.out.println("Itrodizca el id de la ficha que desea editar");
            int idfichaEditar;
            try {
                idfichaEditar = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.err.println("Solo se aceptan valores numericos");
                break;
            }
            if (fichaAccessDB.idInside(idfichaEditar, fichasPorUsuario)) {
                int posFicha = fichaAccessDB.getPosition(idfichaEditar, fichasPorUsuario);
                Ficha fichaEditar = fichasPorUsuario.get(posFicha);

                boolean terminarDeEditar = false;
                do {
                    System.out.println("¿Que dato quieres editar?");
                    app.mostrarDEditarFicha(fichaEditar);
                    String elecEdit = scanner.nextLine();
                    if (elecEdit.equals("1")) {
                        System.out.println("introduzca el nuevo nombre");
                        fichaEditar.setNombre(scanner.nextLine());
                    } else if (elecEdit.equals("2")) {
                        System.out.println("introduzca el nuevo nivel");
                        fichaEditar.setNivel(Integer.parseInt(scanner.nextLine()));
                    } else if (elecEdit.equals("3")) {
                        System.out.println("introduzca la nueva clase ");
                        fichaEditar.setClase(scanner.nextLine());
                    } else if (elecEdit.equals("4")) {
                        System.out.println("introduzca la nueva raza");
                        fichaEditar.setRaza(scanner.nextLine());
                    } else if (elecEdit.equals("5")) {
                        System.out.println("introduzca el nuevo alineamiento");
                        fichaEditar.setAlin(scanner.nextLine());
                    } else if (elecEdit.equals("6")) {
                        System.out.println("introduzca el nuevo transfondo");
                        fichaEditar.setTransfondo(scanner.nextLine());
                    } else if (elecEdit.equals("7")) {
                        System.out.println("Guardando canbios");
                        fichaAccessDB.update(fichaEditar);
                        terminarDeEditar = true;
                    } else if (elecEdit.equals("8")) {
                        System.out.println("Saliendo sin Guardar");
                        terminarDeEditar = true;
                    } else {
                        System.out.println("la eleccion introducida no es valida");
                    }

                } while (!terminarDeEditar);


                salirEl2 = true;

            } else {
                System.out.println("El id introducido no corresponde a ninguna ficha");
            }


        } while (!salirEl2);
    }

    public void crearFicha(List<Ficha> fichas, AplicacionConsola app,
                           List<Acceso> accesos, FichaAccessDB fichaAccessDB,
                           AccesoAccesDB accesoAccesDB, int idlogeado) throws SQLException {


        boolean salirEl3 = false;
        do {
            System.out.println("¿Desea crear una nuava ficha?\n1-si | 2-no");
            String elec = scanner.nextLine();
            if (elec.equals("1")) {

                Ficha fichaNueva = new Ficha();

                if (fichas.size() == 0) {
                    fichaNueva.setIdpersonaje(1);
                } else {
                    fichaNueva.setIdpersonaje(fichas.get(fichas.size() - 1).getIdpersonaje() + 1);
                }

                System.out.println("introduzca el nombre");
                fichaNueva.setNombre(scanner.nextLine());

                System.out.println("introduzca el nivel");

                try {
                    fichaNueva.setNivel(Integer.parseInt(scanner.nextLine()));
                } catch (Exception e) {
                    System.err.println("Solo se aceptan valores numericos");
                    break;
                }
                System.out.println("introduzca la clase ");
                fichaNueva.setClase(scanner.nextLine());

                System.out.println("introduzca la raza");
                fichaNueva.setRaza(scanner.nextLine());

                System.out.println("introduzca el alineamiento");
                fichaNueva.setAlin(scanner.nextLine());

                System.out.println("introduzca el transfondo");
                fichaNueva.setTransfondo(scanner.nextLine());

                boolean salirGuardar = false;
                do {
                    System.out.println("Datos Recogidos;");
                    app.mostrarFicha(fichaNueva);
                    System.out.println("¿desea guardar la ficha? \n1-si | 2-no");
                    String guardar = scanner.nextLine();
                    if (guardar.equals("1")) {


                        if (idlogeado == 1) {
                            Acceso accesoNuevo = new Acceso();
                            if (accesos.size() == 0) {
                                accesoNuevo.setIdacceso(1);
                            } else {
                                accesoNuevo.setIdacceso(accesos.get(accesos.size() - 1).getIdacceso() + 1);
                            }
                            accesoNuevo.setIdlogin(1);
                            accesoNuevo.setIdficha(fichaNueva.getIdpersonaje());
                            accesoAccesDB.insert(accesoNuevo);
                        } else {
                            Acceso accesoNuevo1 = new Acceso();
                            if (accesos.size() == 0) {
                                accesoNuevo1.setIdacceso(1);
                            } else {
                                accesoNuevo1.setIdacceso(accesos.get(accesos.size() - 1).getIdacceso() + 1);
                            }
                            accesoNuevo1.setIdlogin(1);
                            accesoNuevo1.setIdficha(fichaNueva.getIdpersonaje());


                            Acceso accesoNuevo2 = new Acceso();
                            if (accesos.size() == 0) {
                                accesoNuevo2.setIdacceso(1);
                            } else {
                                accesoNuevo2.setIdacceso(accesos.get(accesos.size() - 1).getIdacceso() + 2);
                            }
                            accesoNuevo2.setIdlogin(idlogeado);
                            accesoNuevo2.setIdficha(fichaNueva.getIdpersonaje());
                            accesoAccesDB.insert(accesoNuevo1);
                            accesoAccesDB.insert(accesoNuevo2);
                            accesos = accesoAccesDB.getAccesos();
                        }
                        fichaAccessDB.insert(fichaNueva);
                        salirGuardar = true;
                    } else if (guardar.equals("2")) {
                        salirGuardar = true;
                    } else {
                        System.out.println("la eleccion introducida no es valida");
                    }
                } while (!salirGuardar);
                fichas = fichaAccessDB.getFichas();
                accesos = accesoAccesDB.getAccesos();


            } else if (elec.equals("2")) {
                salirEl3 = true;
            } else {
                System.out.println("la eleccion introducida no es valida");
            }
        } while (!salirEl3);
    }

    public void mostrarFicha(List<Ficha> fichasPorUsuario, FichaAccessDB fichaAccessDB, AplicacionConsola app) {
        boolean salirEl1 = false;
        do {
            System.out.println("Itrodizca el id de la ficha que desea ver");
            int idfichaAver;

            try {
                idfichaAver = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.err.println("Solo se aceptan valores numericos");
                break;
            }
            if (fichaAccessDB.idInside(idfichaAver, fichasPorUsuario)) {

                Ficha fichaAmostrar = fichasPorUsuario.get(fichaAccessDB.getPosition(idfichaAver, fichasPorUsuario));
                app.mostrarFicha(fichaAmostrar);
                salirEl1 = true;
                scanner.nextLine();

            } else {
                System.out.println("El id introducido no corresponde a ninguna ficha");
            }
        } while (!salirEl1);
    }

    public void borrarFicha(List<Ficha> fichasPorUsuario, List<Acceso> accesos, FichaAccessDB fichaAccessDB, AccesoAccesDB accesoAccesDB) throws SQLException {

        System.out.println("Itrodizca el id de la ficha que desea borrar");

        int idfichaBorrar = 0;

        try {
            idfichaBorrar = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.err.println("Solo se aceptan valores numericos");
        }

        String fb = "borrarFicha" + idfichaBorrar;

        if (fichaAccessDB.idInside(idfichaBorrar, fichasPorUsuario)) {
            boolean salirBorrar = false;
            do {
                System.out.println("si quiere borrar la ficha con id: " + idfichaBorrar + " escriba: " + fb
                        + "\nSi no desea hacerlo escriba: 'Cancelar'");
                String fraseSeg = scanner.nextLine();
                if (fb.equals(fraseSeg)) {
                    for (int i = 0; i < accesos.size(); i++) {
                        if (accesos.get(i).getIdficha() == idfichaBorrar) {
                            accesoAccesDB.delete(accesos.get(i).getIdacceso());
                        }
                        fichaAccessDB.delete(idfichaBorrar);
                        salirBorrar = true;
                    }
                } else if (fraseSeg.toLowerCase().equals("cancelar")) {
                    salirBorrar = true;
                } else {
                    System.out.println("la eleccion introducida no es valida");
                }
            } while (!salirBorrar);
        } else {
            System.out.println("El id introducido no corresponde a ninguna ficha");
        }
    }

    public void crearUsuario(List<Login> usuarios, LoginAccessDB loginAccessDB) throws SQLException {
        boolean salirCU = false;
        do {
            System.out.println("¿desea crear un usuario nuevo?\n1-si | 2-no");
            String ele = scanner.nextLine();
            if (ele.equals("1")) {

                Login nuevoUsuario = new Login();

                nuevoUsuario.setId(usuarios.get(usuarios.size() - 1).getId() + 1);
                boolean usucompra = false;
                String nombre;
                do {
                    System.out.println("introduzca el nombre");
                    nombre = scanner.nextLine();
                    if (nombre.equals("")) {
                        System.out.println("la contraseña no puede estar en blanco");
                    } else {
                        boolean nombreExiste = false;
                        for (int i = 0; i < usuarios.size(); i++) {
                            if (usuarios.get(i).getUser().toLowerCase().equals(nombre.toLowerCase())) {
                                nombreExiste = true;
                            }
                        }
                        if (nombreExiste == false) {
                            nuevoUsuario.setUser(nombre);
                            usucompra = true;
                        } else {
                            System.out.println("el nombre (" + nombre + ") ya esta usado");
                        }
                    }
                } while (usucompra == false);


                String pass1, pass2;
                boolean passEq = false;
                do {
                    System.out.println("introduzca contraseña");
                    pass1 = scanner.nextLine();
                    System.out.println("confirme contraseña");
                    pass2 = scanner.nextLine();
                    if (pass1.equals(pass2)) {
                        if (pass1.equals("")) {
                            System.out.println("la contraseña no puede estar en blanco");
                        } else {
                            nuevoUsuario.setPassword(pass1);
                            passEq = true;
                        }
                    } else {
                        System.out.println("las cortraseñas no coinciden");
                    }
                } while (passEq == false);

                loginAccessDB.insert(nuevoUsuario);
                salirCU = true;
            } else if (ele.equals("2")) {
                salirCU = true;
            }
            System.out.println("la eleccion introducida no es valida");
        } while (!salirCU);
    }

    public void mostrarUsusarios(List<Login> usuarios) {
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println("Id-" + usuarios.get(i).getId() + " Nombre-" + usuarios.get(i).getUser());
        }
    }
    public void borrarUsuario(List<Login> usuarios,LoginAccessDB loginAccessDB,List<Acceso> accesos,AccesoAccesDB accesoAccesDB) throws SQLException {
        boolean terminarBorrar = false;
        do {
            int idUsuAborrar = 0;
            System.out.println("introduzca el id del usuario a borrar");
            try {
                idUsuAborrar = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.err.println("Solo se aceptan valores numericos");
                break;
            }
            if (loginAccessDB.idInside(idUsuAborrar,usuarios)){
                int pos = loginAccessDB.getPosition(idUsuAborrar,usuarios);
                System.out.println("para borrar el usuario "+
                        usuarios.get(pos).getUser()+ " con id: "+
                        usuarios.get(pos).getId()+
                        "tiene que introducir su contraseña o la de admin"
                );
                System.out.println("introduzca su contraseña");
                String entPassw = scanner.nextLine();

                if (usuarios.get(pos).getPassword().equals(entPassw) || usuarios.get(0).getPassword().equals(entPassw)) {






                    if (idUsuAborrar == 1) {
                        System.out.println("El usuario Administrador no se puede eliminar");
                    } else {

                        List<Acceso> accesosPorUsu = accesoAccesDB.accesosPorLogin(idUsuAborrar,accesos);
                        for (int i = 0; i <accesosPorUsu.size() ; i++) {
                            accesoAccesDB.delete(accesosPorUsu.get(i).getIdacceso());
                        }
                        loginAccessDB.delete(idUsuAborrar);

                    }




                } else {
                    System.out.println("la contraseña introducida no es valida para el usuario: " +
                            usuarios.get(pos).getUser() + " con el id: " + idUsuAborrar);
                }

                terminarBorrar= true;
            } else {
                System.out.println("el id introducido no existe en la base de datos");
            }
        }while (terminarBorrar == false);
    }
}
