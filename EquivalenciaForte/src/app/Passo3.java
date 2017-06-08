/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.LinkedList;
import model.IRComposta;
import model.Rotulo;

/**
 *
 * @author Rafael Rutsatz
 */
public class Passo3 {

    public static LinkedList<IRComposta> rotulosFora;
    public static LinkedList<IRComposta> rotulosDentro;

    public Passo3(LinkedList<IRComposta> rotulosFora, LinkedList<IRComposta> rotulosDentro) {
        this.rotulosFora = rotulosFora;
        this.rotulosDentro = rotulosDentro;
    }

    public void gerar() {

        for (int i = 0; i < rotulosDentro.size(); i++) {

            IRComposta instrucaoDentro = rotulosDentro.get(i);

            for (int j = 0; j < rotulosFora.size(); j++) {

                IRComposta instrucaoFora = rotulosFora.get(j);

                if (instrucaoDentro.getOpV() == instrucaoFora.getOpV()) {
                    instrucaoDentro.setRotuloV(Rotulo.CICLO);
                    instrucaoDentro.setIdOpV("w");
                    instrucaoDentro.setOpV("ciclo");
                    rotulosDentro.set(i, instrucaoDentro);
                }
                if (instrucaoDentro.getOpF() == instrucaoFora.getOpF()) {
                    instrucaoDentro.setRotuloF(Rotulo.CICLO);
                    instrucaoDentro.setIdOpF("w");
                    instrucaoDentro.setOpF("ciclo");
                    rotulosDentro.set(i, instrucaoDentro);
                }

            }

        }

    }

    public static LinkedList<IRComposta> getRotulosFora() {
        return rotulosFora;
    }

    public static void setRotulosFora(LinkedList<IRComposta> rotulosFora) {
        Passo3.rotulosFora = rotulosFora;
    }

    public static LinkedList<IRComposta> getRotulosDentro() {
        return rotulosDentro;
    }

    public static void setRotulosDentro(LinkedList<IRComposta> rotulosDentro) {
        Passo3.rotulosDentro = rotulosDentro;
    }

}
