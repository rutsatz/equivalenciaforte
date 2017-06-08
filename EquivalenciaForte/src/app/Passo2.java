/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.LinkedList;
import model.CadeiaFinitaConjunto;
import model.IRComposta;
import model.Rotulo;

/**
 *
 * @author Rafael Rutsatz
 */
public class Passo2 {

    public LinkedList<IRComposta> lista; // Lista original dos rótulos.
    public static LinkedList<IRComposta> rotulosFora = new LinkedList<>();
    public static LinkedList<IRComposta> rotulosDentro = new LinkedList<>();
    public LinkedList<CadeiaFinitaConjunto> listaCadeiaFinita = new LinkedList<>();

    public void gerar() {

        int posParada = 0; // guarda a posição da parada quando a encontramos.

        // Primeiro conjunto da cadeia finita.
        CadeiaFinitaConjunto cadeiaFinita = new CadeiaFinitaConjunto("&", "A0");
        listaCadeiaFinita.add(cadeiaFinita);

        int contRotulo = 1; // contador da qtd de rótulos percorridos.

        // Percorre a lista de tras para frente, marcando se encontrou a parada 
        // e gera a lista de cadeia de conjuntos finitos.
        for (int i = lista.size() - 1; i >= 0; i--) {

            IRComposta instrucao = lista.get(i);

            // se posParada != 0, então já encontrou a parada e o resto dos
            // rótulos estão dentro.
            if (posParada != 0) {

                // Se o rótulo já existe na lista, ignora.
                if (verificaSeExiste(instrucao, rotulosDentro)) {
                    continue;
                }
                cadeiaFinita = new CadeiaFinitaConjunto(cadeiaFinita.getConjunto() + "," + instrucao.getIr(),
                        "A" + contRotulo);
                listaCadeiaFinita.add(cadeiaFinita);
                rotulosDentro.add(instrucao);

            } else {

                if (instrucao.getRotuloF() == Rotulo.PARADA || instrucao.getRotuloV() == Rotulo.PARADA) {
                    posParada = i;
                    cadeiaFinita = new CadeiaFinitaConjunto(cadeiaFinita.getConjunto() + "," + instrucao.getIr(),
                            "A" + contRotulo);
                    listaCadeiaFinita.add(cadeiaFinita);
                    rotulosDentro.add(instrucao);
                    contRotulo++;
                    continue;
                }

                cadeiaFinita = new CadeiaFinitaConjunto(cadeiaFinita.getConjunto() + "," + instrucao.getIr(),
                        "A" + contRotulo);
                listaCadeiaFinita.add(cadeiaFinita);
                rotulosFora.add(instrucao);

            }

            contRotulo++;
        }

        // Último conjunto da cadeia finita.
        cadeiaFinita = new CadeiaFinitaConjunto(cadeiaFinita.getConjunto(), "A" + contRotulo);
        listaCadeiaFinita.add(cadeiaFinita);

    }

    // Verifica se o rótulo já existe na lista. 
    public boolean verificaSeExiste(IRComposta instrucao, LinkedList<IRComposta> lista) {
        for (int i = 0; i < lista.size(); i++) {
            IRComposta instrucaoLista = lista.get(i);
            if (instrucao.getOpV() == instrucaoLista.getOpV()
                    && instrucao.getIdOpV() == instrucaoLista.getIdOpV()
                    && instrucao.getOpF() == instrucaoLista.getOpF()
                    && instrucao.getIdOpF() == instrucaoLista.getIdOpF()) {
                return true;
            }
        }
        return false;
    }

    public static String comoString(LinkedList<CadeiaFinitaConjunto> lista) {
        StringBuilder texto = new StringBuilder();

        for (CadeiaFinitaConjunto cadeiaFinita : lista) {
            texto.append(cadeiaFinita + "\n");
        }
        return texto.toString();
    }

    public static LinkedList<IRComposta> getRotulosFora() {
        return rotulosFora;
    }

    public static void setRotulosFora(LinkedList<IRComposta> rotulosFora) {
        Passo2.rotulosFora = rotulosFora;
    }

    public static LinkedList<IRComposta> getRotulosDentro() {
        return rotulosDentro;
    }

    public static void setRotulosDentro(LinkedList<IRComposta> rotulosDentro) {
        Passo2.rotulosDentro = rotulosDentro;
    }

    public LinkedList<CadeiaFinitaConjunto> getListaCadeiaFinita() {
        return listaCadeiaFinita;
    }

    public void setListaCadeiaFinita(LinkedList<CadeiaFinitaConjunto> listaCadeiaFinita) {
        this.listaCadeiaFinita = listaCadeiaFinita;
    }

    public Passo2(LinkedList<IRComposta> lista) {
        this.lista = lista;
    }

    public LinkedList<IRComposta> getLista() {
        return lista;
    }

    public void setLista(LinkedList<IRComposta> lista) {
        this.lista = lista;
    }

}
