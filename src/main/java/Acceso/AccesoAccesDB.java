package Acceso;

import Conect.DatabaseConection;
import Login.Login;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccesoAccesDB {
    Connection con = DatabaseConection.getConnection();


    public List<Acceso> getAccesos() throws SQLException {
        String sql = "SELECT * from acceso";
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<Acceso> accesos = new ArrayList<>();

        while (resultSet.next()) {
            Acceso acceso = new Acceso();
            acceso.setIdacceso(resultSet.getInt("idacceso"));
            acceso.setIdlogin(resultSet.getInt("Idlogin"));
            acceso.setIdficha(resultSet.getInt("idficha"));
            accesos.add(acceso);
        }
        return accesos;
    }

    public void insert(Acceso acceso) throws SQLException {
        String sql = "insert into acceso (idacceso,idlogin,idficha)values(" + acceso.getIdacceso() + "," + acceso.getIdlogin() + "," + acceso.getIdficha() + ")";

        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
        System.out.println("Acceso con id: " + acceso.getIdacceso() + " creado");
    }

    public void update(Acceso acceso) throws SQLException {
        String query = "update acceso set idlogin = ?, idficha = ? where idacceso = ?";
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setInt(1, acceso.getIdlogin());
        preparedStmt.setInt(2, acceso.getIdficha());
        preparedStmt.setInt(3, acceso.getIdacceso());
        preparedStmt.executeUpdate();
        System.out.println("Acceso con id: " + acceso.getIdacceso() + " actualizado");
    }

    public void delete(int idacceso) throws SQLException {
        String sql = "delete from acceso where idacceso = " + idacceso;
        PreparedStatement statement = con.prepareStatement(sql);
        statement.execute();
        System.out.println("Acceso con id: " + idacceso + " borrado");
    }


    public boolean idInside(int id, List<Acceso> accesos) {
        boolean resultado = false;
        for (int i = 0; i < accesos.size(); i++) {
            if (accesos.get(i).getIdacceso() == id) {
                resultado = true;
            }
        }

        return resultado;
    }

    public int getPosition(int id, List<Acceso> accesos) {
        int salida = 0;
        for (int i = 0; i < accesos.size(); i++) {
            if (accesos.get(i).getIdacceso() == id) {
                salida = i;
            }
        }
        return salida;
    }

    public List<Acceso> accesosPorLogin(int idlogin, List<Acceso> accesos) {
        List<Acceso> salida = new ArrayList<>();
        for (int i = 0; i < accesos.size(); i++) {
            if (accesos.get(i).getIdlogin() == idlogin) {
                salida.add(accesos.get(i));
            }
        }
        return salida;
    }

}
