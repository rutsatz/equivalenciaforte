/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Rafael Rutsatz
 */
public class CadeiaFinitaConjunto {

    public String rotulo;
    public String conjunto;

    public CadeiaFinitaConjunto(String conjunto, String rotulo) {
        this.conjunto = conjunto;
        this.rotulo = rotulo;
    }

    @Override
    public String toString() {
        return this.rotulo + ":{" + this.conjunto + "}";
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getConjunto() {
        return conjunto;
    }

    public void setConjunto(String conjunto) {
        this.conjunto = conjunto;
    }

}
