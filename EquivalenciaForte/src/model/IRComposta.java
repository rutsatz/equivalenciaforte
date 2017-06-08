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
public class IRComposta {

    

    private int ir;

    private Rotulo rotuloV; // Tipo Rotulo verdadeiro
    private String OpV; // Operacao verdadeira
    private String idOpV; // Num da operacao Verdadeira

    private Rotulo rotuloF; // Tipo Rotulo Falso
    private String OpF; // Operacao falso
    private String idOpF; // Num da operacao Falso

    public IRComposta() {
    }

    public IRComposta(int ir, Rotulo rotuloV, String OpV, String idOpV, Rotulo rotuloF, String OpF, String idOpF) {
        this.ir = ir;
        this.rotuloV = rotuloV;
        this.OpV = OpV;
        this.idOpV = idOpV;
        this.rotuloF = rotuloF;
        this.OpF = OpF;
        this.idOpF = idOpF;
    }

    @Override
    public String toString() {
        return "\n\tIRComposta{" + "ir=" + ir + ", rotuloV=" + rotuloV + ", OpV=" + OpV + ", idOpV=" + idOpV
                + ", \n\t\t\trotuloF=" + rotuloF + ", OpF=" + OpF + ", idOpF=" + idOpF + "}";
    }

    public int getIr() {
        return ir;
    }

    public void setIr(int ir) {
        this.ir = ir;
    }

    public Rotulo getRotuloV() {
        return rotuloV;
    }

    public void setRotuloV(Rotulo rotuloV) {
        this.rotuloV = rotuloV;
    }

    public String getOpV() {
        return OpV;
    }

    public void setOpV(String OpV) {
        this.OpV = OpV;
    }

    public String getIdOpV() {
        return idOpV;
    }

    public void setIdOpV(String idOpV) {
        this.idOpV = idOpV;
    }

    public Rotulo getRotuloF() {
        return rotuloF;
    }

    public void setRotuloF(Rotulo rotuloF) {
        this.rotuloF = rotuloF;
    }

    public String getOpF() {
        return OpF;
    }

    public void setOpF(String OpF) {
        this.OpF = OpF;
    }

    public String getIdOpF() {
        return idOpF;
    }

    public void setIdOpF(String idOpF) {
        this.idOpF = idOpF;
    }

   
    
}
