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
public class InstrucaoRotulada {

    private int IR;

    private Operacao opTeste;

    private Operacao opVerdade;

    private Operacao opFalso;
    
    private Integer destinoOperacao;

public InstrucaoRotulada(int IR, Operacao opTeste, Operacao opVerdade, Operacao opFalso) {
        this.IR = IR;
        this.opTeste = opTeste;
        this.opVerdade = opVerdade;
        this.opFalso = opFalso;
    }

public InstrucaoRotulada(int IR, Operacao opTeste, Operacao opVerdade, Operacao opFalso, Integer destinoOperacao) {
        this.IR = IR;
        this.opTeste = opTeste;
        this.opVerdade = opVerdade;
        this.opFalso = opFalso;
        this.destinoOperacao = destinoOperacao;
    }

    
    public Integer getDestinoOperacao() {
        return destinoOperacao;
    }

    public void setDestinoOperacao(Integer destinoOperacao) {
        this.destinoOperacao = destinoOperacao;
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }
    
    public Boolean verificado = new Boolean(false);

    
    @Override
    public String toString() {
        return "IRComposta{" + "IR=" + IR + ", opTeste=" + opTeste + ", opVerdade=" + opVerdade + ", opFalso=" + opFalso + '}';
    }

    /**
     * @return the IR
     */
    public int getIR() {
        return IR;
    }

    /**
     * @param IR the IR to set
     */
    public void setIR(int IR) {
        this.IR = IR;
    }

    /**
     * @return the opTeste
     */
    public Operacao getOpTeste() {
        return opTeste;
    }

    /**
     * @param opTeste the opTeste to set
     */
    public void setOpTeste(Operacao opTeste) {
        this.opTeste = opTeste;
    }

    /**
     * @return the opVerdade
     */
    public Operacao getOpVerdade() {
        return opVerdade;
    }

    /**
     * @param opVerdade the opVerdade to set
     */
    public void setOpVerdade(Operacao opVerdade) {
        this.opVerdade = opVerdade;
    }

    /**
     * @return the opFalso
     */
    public Operacao getOpFalso() {
        return opFalso;
    }

    /**
     * @param opFalso the opFalso to set
     */
    public void setOpFalso(Operacao opFalso) {
        this.opFalso = opFalso;
    }

}
