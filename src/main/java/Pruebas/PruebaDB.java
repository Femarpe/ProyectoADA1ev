package Pruebas;

import Acceso.Acceso;
import Acceso.AccesoAccesDB;
import Aplicacion.AplicacionConsola;
import Ficha.*;
import Login.*;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PruebaDB {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        LoginAccessDB loginAccessDB = new LoginAccessDB();
        List<Login> usuarios = loginAccessDB.getLogins();

        FichaAccessDB fichaAccessDB = new FichaAccessDB();
        List<Ficha> fichas = fichaAccessDB.getFichas();

        AccesoAccesDB accesoAccesDB = new AccesoAccesDB();
        List<Acceso> accesos = accesoAccesDB.getAccesos();

        AplicacionConsola app = new AplicacionConsola();
        boolean salir = false;
        do {
            usuarios = loginAccessDB.getLogins();
            accesos = accesoAccesDB.getAccesos();

            System.out.println("Que accion desea realizar: \n1-iniciar sesion \n2-crear usuario \n3-listar usuarios \n4-borrar usuario \n 5-salir");
            String entrada = scanner.nextLine();


            /**Iniciar sesion*/
            if (entrada.equals("1")) {
                accesos = accesoAccesDB.getAccesos();

                AplicacionConsola.DatosCredenciales dc = app.comprobarCredenciales(usuarios, loginAccessDB);


                if (dc.isCredencialesCorrectas()) {
                    System.out.println("las credencialse son correctas, bienvenid@ " + usuarios.get(dc.getPosUser()).getUser() + "\n");
                    int idlogeado = usuarios.get(dc.getPosUser()).getId();


                    boolean salirSINI = false;
                    do {
                        fichas = fichaAccessDB.getFichas();
                        accesos = accesoAccesDB.getAccesos();

                        List<Ficha> fichasPorUsuario = app.getFichasPorUsuario(fichas, accesos, accesoAccesDB, fichaAccessDB, idlogeado);
                        if (fichasPorUsuario.size() > 0) {
                            System.out.println("tienes acceso a las sguientes fichas:");
                            for (int i = 0; i < fichasPorUsuario.size(); i++) {
                                System.out.println("-id: " + fichasPorUsuario.get(i).getIdpersonaje() + " -nombre: " + fichasPorUsuario.get(i).getNombre());
                            }
                        } else {
                            System.out.println("este usuario no tiene fichas");
                        }
                        System.out.println("\n¿Que desea hacer a continuacion? \n1-Ver Ficha \n2-Editar Ficha \n3-Crear Ficha \n4-Borrar ficha \n5-Salir");
                        String eleccionSINI = scanner.nextLine();


                        /**Mostrar ficha*/
                        if (eleccionSINI.equals("1")) {
                            app.mostrarFicha(fichasPorUsuario, fichaAccessDB, app);
                            /**Editar ficha*/
                        } else if (eleccionSINI.equals("2")) {

                            app.editarFicha(fichasPorUsuario, fichaAccessDB, app);

                            /**Crea ficha*/
                        } else if (eleccionSINI.equals("3")) {

                            app.crearFicha(fichas, app, accesos, fichaAccessDB, accesoAccesDB, idlogeado);

                            /**Borrar ficha*/
                        } else if (eleccionSINI.equals("4")) {

                            app.borrarFicha(fichasPorUsuario, accesos, fichaAccessDB, accesoAccesDB);

                            /**Cerrar sesion*/
                        } else if (eleccionSINI.equals("5")) {
                            salirSINI = true;
                        } else {
                            System.out.println("la eleccion introducida no es valida");
                        }

                    } while (!salirSINI);

                }

                /**Crear usuario*/
            } else if (entrada.equals("2")) {

                app.crearUsuario(usuarios, loginAccessDB);



            } else if (entrada.equals("3")) {
                app.mostrarUsusarios(usuarios);
                System.out.println("\npulse 'enter' para continuar");
                scanner.nextLine();

            } else if (entrada.equals("4")) {



                app.borrarUsuario(usuarios,loginAccessDB,accesos,accesoAccesDB);










                /**Salir del programa*/
            } else if (entrada.equals("5")) {
                System.out.println("Adios");
                salir = true;
            } else {
                System.out.println("Eleccion no valida");
            }


        } while (!salir);


    }
}
