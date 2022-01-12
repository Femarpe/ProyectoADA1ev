package Pruebas;

import Ficha.Ficha;
import Ficha.FichaAccessDB;
import java.sql.SQLException;
import java.util.List;

public class PruebaFichas {
    public static void main(String[] args) throws SQLException {
        FichaAccessDB accesoFichas = new FichaAccessDB();
        List<Ficha> fichas = accesoFichas.getFichas();
        Ficha fichaP = new Ficha();

        fichaP.setIdpersonaje(25);
        fichaP.setNombre("pepe");
        fichaP.setNivel(56);
        fichaP.setClase("Barman (Monje)");
        fichaP.setRaza("Enano");
        fichaP.setAlin("Caotico neutral");
        fichaP.setTransfondo("Vietnam");

        if (!accesoFichas.idInside(fichaP.getIdpersonaje(), fichas)) {
            accesoFichas.insert(fichaP);
        } else {
            System.out.println("ya existe un usuario con la id " + fichaP.getIdpersonaje());
        }

        fichas = accesoFichas.getFichas();
        System.out.println("--------------------------------------------------------");
        for (int i = 0; i < fichas.size(); i++) {
            System.out.println((i+1) + "-> " + fichas.get(i).getIdpersonaje()
                    + " / " + fichas.get(i).getNombre()
                    + " / " + fichas.get(i).getNivel()
                    + " / " + fichas.get(i).getClase()
                    + " / " + fichas.get(i).getRaza()
                    + " / " + fichas.get(i).getAlin()
                    + " / " + fichas.get(i).getTransfondo());
        }
        System.out.println("--------------------------------------------------------");
        int id = 25;


        if (accesoFichas.idInside(25, fichas)) {
            int pos = accesoFichas.getPosition(25, fichas);
            fichas.get(pos).setNombre("Juan");
            fichas.get(pos).setNivel(42);
            accesoFichas.update(fichas.get(pos));
        }

        System.out.println("--------------------------------------------------------");
        for (int i = 0; i < fichas.size(); i++) {
            System.out.println((i+1) + "-> " + fichas.get(i).getIdpersonaje()
                    + " / " + fichas.get(i).getNombre()
                    + " / " + fichas.get(i).getNivel()
                    + " / " + fichas.get(i).getClase()
                    + " / " + fichas.get(i).getRaza()
                    + " / " + fichas.get(i).getAlin()
                    + " / " + fichas.get(i).getTransfondo());
        }
        System.out.println("--------------------------------------------------------");

        if (accesoFichas.idInside(id, fichas)) {
            accesoFichas.delete(id);
        } else {
            System.out.println("El id proporcionado no se encuentra en la base de datos");
        }

        System.out.println("--------------------------------------------------------");
        fichas = accesoFichas.getFichas();
        for (int i = 0; i < fichas.size(); i++) {
            System.out.println((i+1) + "-> " + fichas.get(i).getIdpersonaje()
                    + " / " + fichas.get(i).getNombre()
                    + " / " + fichas.get(i).getNivel()
                    + " / " + fichas.get(i).getClase()
                    + " / " + fichas.get(i).getRaza()
                    + " / " + fichas.get(i).getAlin()
                    + " / " + fichas.get(i).getTransfondo());
        }
        System.out.println("--------------------------------------------------------");


    }
}