/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.ArrayList;
import java.util.LinkedList;
import static javafx.application.Platform.exit;
import model.IRComposta;

/**
 *
 * @author rodrigolucke
 */
class Passo4 {

    private static LinkedList<IRComposta> irPrograma1;
    private static LinkedList<IRComposta> irPrograma2;

    public Passo4(LinkedList<IRComposta> irPrograma1, LinkedList<IRComposta> irPrograma2) {
        this.irPrograma1 = irPrograma1;
        this.irPrograma2 = irPrograma2;

    }

    public static LinkedList<IRComposta> getIrPrograma1() {
        return irPrograma1;
    }

    public static void setIrPrograma1(LinkedList<IRComposta> irPrograma1) {
        Passo4.irPrograma1 = irPrograma1;
    }

    public static LinkedList<IRComposta> getIrPrograma2() {
        return irPrograma2;
    }

    public static void setIrPrograma2(LinkedList<IRComposta> irPrograma2) {
        Passo4.irPrograma2 = irPrograma2;
    }

    public String gerar() {
        int limitelista1 = 0;
        int limitelista2 = 0;
        limitelista1 = irPrograma1.size();
        limitelista2 = irPrograma2.size();

        String texto = "";

        int j = 0;
        for (int i = 0; i <= limitelista1 - 1; i++) {

            for (j = i + 1; j <= limitelista1 - 1; j++) {

                // while( j  != limitelista1-1 ){
                if ((irPrograma1.get(i).getOpF().equals(irPrograma1.get(j).getOpF())
                        && irPrograma1.get(i).getIdOpF().equals(irPrograma1.get(j).getIdOpF()))
                        && (irPrograma1.get(i).getOpV().equals(irPrograma1.get(j).getOpV())
                        && irPrograma1.get(i).getIdOpV().equals(irPrograma1.get(j).getIdOpV()))) {

                    irPrograma1.remove(j);

                    limitelista1--;
                    j--;
                }
            }
        }

        j = 0;
        for (int i = 0; i <= limitelista2 - 1; i++) {
            for (j = i + 1; j <= limitelista2 - 1; j++) {

                if ((irPrograma2.get(i).getOpF().equals(irPrograma2.get(j).getOpF())
                        && irPrograma2.get(i).getIdOpF().equals(irPrograma2.get(j).getIdOpF()))
                        && (irPrograma2.get(i).getOpV().equals(irPrograma2.get(j).getOpV())
                        && irPrograma2.get(i).getIdOpV().equals(irPrograma2.get(j).getIdOpV()))) {

                    irPrograma2.remove(j);

                    limitelista2--;
                    j--;
                }
            }
        }

        int limite = 0;
        if (irPrograma1.size() > irPrograma2.size()) {
            limite = irPrograma1.size();

        } else {
            limite = irPrograma2.size();
        }

        String equivalente = "";

        if (limitelista1 > limitelista2) {
            equivalente += "Não é Equivalente pois o número \n ";
            equivalente += "de instruções do programa 1 \n é maior que a do programa 2! \n\n\n";
            return equivalente;
        } else if (limitelista1 < limitelista2) {
            equivalente += "Não é Equivalente pois o número \n ";
            equivalente += "de instruções do programa 2 \n é maior que a do programa 1! \n\n\n";
            return equivalente;
        }

        for (int i = 0; i <= limite - 1; i++) {

            if ((irPrograma1.get(i).getRotuloF().equals(irPrograma2.get(i).getRotuloF()))
                    && (irPrograma1.get(i).getRotuloV().equals(irPrograma2.get(i).getRotuloV()))) {
                equivalente += "";
            } else {
                equivalente += irPrograma1.get(i).getRotuloF() + " - "
                        + irPrograma1.get(i).getRotuloV() + "\n" + "não é equivalente a" + " \n"
                        + irPrograma2.get(i).getRotuloF() + " - " + irPrograma2.get(i).getRotuloV() + "\n\n";
                equivalente += irPrograma1.get(i).getIr() + ":(" + irPrograma1.get(i).getOpV()
                        + "," + irPrograma1.get(i).getIdOpV() + ") ," + "(" + irPrograma1.get(i).getOpF()
                        + " , " + irPrograma1.get(i).getIdOpF() + ")" + " ; \n\n";
                equivalente += irPrograma2.get(i).getIr() + ":(" + irPrograma2.get(i).getOpV() + ","
                        + irPrograma2.get(i).getIdOpV() + ") ," + "(" + irPrograma2.get(i).getOpF()
                        + " , " + irPrograma2.get(i).getIdOpF() + ")" + " ; \n\n";
                return equivalente;
            }
        }
        equivalente = "Os programas são fortemente equivalentes!";
        return equivalente;
    }
}
