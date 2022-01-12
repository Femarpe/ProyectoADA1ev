
import Acceso.Acceso;
import Acceso.AccesoAccesDB;
import Aplicacion.AplicacionConsola;
import Ficha.*;
import Login.*;


import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
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

            System.out.println("Que accion desea realizar: \n1-iniciar sesion \n2-crear usuario \n3 salir");
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


                        if (eleccionSINI.equals("1")) {
                            app.mostrarFicha(fichasPorUsuario, fichaAccessDB, app);

                        } else if (eleccionSINI.equals("2")) {

                            app.editarFicha(fichasPorUsuario, fichaAccessDB, app);

                        } else if (eleccionSINI.equals("3")) {

                            app.crearFicha(fichas, app, accesos, fichaAccessDB, accesoAccesDB, idlogeado);

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
                            if (nombre.equals("")){
                                System.out.println("la contraseña no puede estar en blanco");
                            }else {
                                boolean nombreExiste = false;
                                for (int i = 0; i < usuarios.size() ; i++) {
                                    if (usuarios.get(i).getUser().equals(nombre)){
                                        nombreExiste = true;
                                    }
                                }
                                if (nombreExiste == false){
                                    nuevoUsuario.setUser(nombre);
                                    usucompra = true;
                                } else {
                                    System.out.println("el nombre ("+nombre+") ya esta usado");
                                }
                            }
                        } while (usucompra == false);


                        String pass1,pass2;
                        boolean passEq = false;
                        do {
                            System.out.println("introduzca contraseña");
                            pass1 = scanner.nextLine();
                            System.out.println("confirme contraseña");
                            pass2 = scanner.nextLine();
                            if (pass1.equals(pass2)){
                                if (pass1.equals("")){
                                    System.out.println("la contraseña no puede estar en blanco");
                                }else {
                                    nuevoUsuario.setPassword(pass1);
                                    passEq = true;
                                }
                            } else {
                                System.out.println("las cortraseñas no coinciden");
                            }
                        }while (passEq == false);

                        loginAccessDB.insert(nuevoUsuario);
                        salirCU = true;
                    } else if (ele.equals("2")) {
                        salirCU = true;
                    }
                    System.out.println("la eleccion introducida no es valida");
                } while (!salirCU);

                /**Salir del programa*/
            } else if (entrada.equals("3")) {
                System.out.println("Adios");
                salir = true;
            } else {
                System.out.println("Eleccion no valida");
            }


        } while (!salir);


    }
}
