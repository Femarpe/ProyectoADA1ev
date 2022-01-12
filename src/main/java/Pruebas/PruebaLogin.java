package Pruebas;

import Login.Login;
import Login.LoginAccessDB;
import java.sql.SQLException;
import java.util.List;

public class PruebaLogin {
    public static void main(String[] args) throws SQLException {
        LoginAccessDB logins = new LoginAccessDB();
        List<Login> usuarios = logins.getLogins();
        Login loginP = new Login();

        loginP.setId(25);
        loginP.setUser("pepe");
        loginP.setPassword("56");

        if (!logins.idInside(loginP.getId(), usuarios)) {
            logins.insert(loginP);
        } else {
            System.out.println("ya existe un usuario con la id " + loginP.getId());
        }

        usuarios = logins.getLogins();
        System.out.println("--------------------------------------------------------");
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println("-"+usuarios.get(i).getId() + " / " + usuarios.get(i).getUser() + " / " + usuarios.get(i).getPassword() + " / " + usuarios.get(i).getFecha());

        }
        System.out.println("--------------------------------------------------------");
        int id = 25;


        if (logins.idInside(25,usuarios)){
            int pos = logins.getPosition(25,usuarios);
            usuarios.get(pos).setUser("Juan");
            usuarios.get(pos).setPassword("42");
            logins.update(usuarios.get(pos));
        }

        System.out.println("--------------------------------------------------------");
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println("-"+usuarios.get(i).getId() + " / " + usuarios.get(i).getUser() + " / " + usuarios.get(i).getPassword() + " / " + usuarios.get(i).getFecha());

        }
        System.out.println("--------------------------------------------------------");

        if (logins.idInside(id, usuarios)) {
            logins.delete(id);
        } else {
            System.out.println("El id proporcionado no se encuentra en la base de datos");
        }

        System.out.println("--------------------------------------------------------");
         usuarios = logins.getLogins();
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println("-"+usuarios.get(i).getId() + " / " + usuarios.get(i).getUser() + " / " + usuarios.get(i).getPassword() + " / " + usuarios.get(i).getFecha());
            
        }
        System.out.println("--------------------------------------------------------");


    }
}