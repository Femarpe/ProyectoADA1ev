package Login;

import Conect.DatabaseConection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginAccessDB {
    Connection con = DatabaseConection.getConnection();


    public List<Login> getLogins() throws SQLException {
        String sql = "SELECT * from login";
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<Login> logins = new ArrayList<>();

        while (resultSet.next()) {
            Login login = new Login();
            login.setId(resultSet.getInt("id"));
            login.setUser(resultSet.getString("username"));
            login.setPassword(resultSet.getString(3));
            login.setFecha(resultSet.getTimestamp(4));
            logins.add(login);
        }
        return logins;
    }

    public void insert(Login login) throws SQLException {
        String sql = "insert into login (id,username,password)values(" + login.getId() + ",'" + login.getUser() + "','" + login.getPassword() + "')";

        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
        System.out.println("usuario con id: " + login.getId() + " creado");
    }
    public void update(Login login) throws SQLException {
        String query = "update login set username = ?, password = ? where id = ?";
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setString(1, login.getUser());
        preparedStmt.setString(2, login.getPassword());
        preparedStmt.setInt(3, login.getId());
        preparedStmt.executeUpdate();
        System.out.println("usuario con id: " + login.getId() + " actualizado");
    }

    public void delete(int id) throws SQLException {
        String sql = "delete from login where id = " + id;
        PreparedStatement statement = con.prepareStatement(sql);
        statement.execute();
        System.out.println("usuario con id: " + id + " borrado");

    }


    public boolean idInside(int id, List<Login> logins) {
        boolean resultado = false;
        for (int i = 0; i < logins.size(); i++) {
            if (logins.get(i).getId() == id) {
                resultado = true;
            }
        }

        return resultado;
    }

    public int getPosition(int id, List<Login> logins) {
        int salida = 0;
        for (int i = 0; i < logins.size(); i++) {
            if (logins.get(i).getId() == id) {
                salida = i;
            }
        }
        return salida;
    }
}
