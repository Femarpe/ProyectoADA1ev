package Ficha;

import Ficha.Ficha;
import Conect.DatabaseConection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FichaAccessDB {
    Connection con = DatabaseConection.getConnection();


    public List<Ficha> getFichas() throws SQLException {
        String sql = "SELECT * from fichasper";
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<Ficha> fichas = new ArrayList<>();

        while (resultSet.next()) {
            Ficha ficha = new Ficha();
            ficha.setIdpersonaje(resultSet.getInt("idpersonaje"));
            ficha.setNombre(resultSet.getString("nombre"));
            ficha.setNivel(resultSet.getInt("nivel"));
            ficha.setClase(resultSet.getString("clase"));
            ficha.setRaza(resultSet.getString("raza"));
            ficha.setAlin(resultSet.getString("alin"));
            ficha.setTransfondo(resultSet.getString("transfondo"));
            fichas.add(ficha);
        }
        return fichas;
    }

    public void insert(Ficha ficha) throws SQLException {
        String sql = "insert into fichasper (idpersonaje,nombre,nivel,clase,raza,alin,transfondo)values("
                + ficha.getIdpersonaje() + ",'" + ficha.getNombre() + "'," + ficha.getNivel() + ",'" + ficha.getClase()
                + "','" + ficha.getRaza() + "','" + ficha.getAlin() + "','" + ficha.getTransfondo() + "')";

        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
        System.out.println("ficha de personaje con id: " + ficha.getIdpersonaje() + " creada");
    }

    public void update(Ficha ficha) throws SQLException {
        String query = "update fichasper set nombre = ?,nivel = ?,clase = ?,raza  = ?,alin = ?,transfondo = ? where idpersonaje = ?";
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setString(1, ficha.getNombre());
        preparedStmt.setInt(2, ficha.getNivel());
        preparedStmt.setString(3, ficha.getClase());
        preparedStmt.setString(4, ficha.getRaza());
        preparedStmt.setString(5, ficha.getAlin());
        preparedStmt.setString(6, ficha.getTransfondo());
        preparedStmt.setInt(7, ficha.getIdpersonaje());
        preparedStmt.executeUpdate();
        System.out.println("ficha con id: " + ficha.getIdpersonaje() + " actualizada");
    }

    public void delete(int id) throws SQLException {
        String sql = "delete from fichasper where idpersonaje = " + id;
        PreparedStatement statement = con.prepareStatement(sql);
        statement.execute();
        System.out.println("la ficha con id: " + id + " ha sido borrada");
    }


    public boolean idInside(int id, List<Ficha> fichas) {
        boolean resultado = false;
        for (int i = 0; i < fichas.size(); i++) {
            if (fichas.get(i).getIdpersonaje() == id) {
                resultado = true;
            }
        }

        return resultado;
    }

    public int getPosition(int id, List<Ficha> fichas) {
        int salida = 0;
        for (int i = 0; i < fichas.size(); i++) {
            if (fichas.get(i).getIdpersonaje() == id) {
                salida = i;
            }
        }
        return salida;
    }

}
