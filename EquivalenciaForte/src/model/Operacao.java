/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Mapeia as funções que posso realizar.
 *
 * @author Rafael Rutsatz
 */
public class Operacao {

    // rotulo que está sendo realizado
    private Rotulo rotulo;

    // descricao da variavel
    private String operacao;

    // IR de desvio.
    private String desvio;

    public Operacao(Rotulo rotulo, String operacao, String desvio) {
        this.rotulo = rotulo;
        this.operacao = operacao;
        this.desvio = desvio;
    }

    @Override
    public String toString() {
        return "Operacao{" + "rotulo=" + rotulo + ", operacao=" + operacao + ", desvio=" + desvio + '}';
    }

    public Rotulo getRotulo() {
        return rotulo;
    }

    public void setRotulo(Rotulo rotulo) {
        this.rotulo = rotulo;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getDesvio() {
        return desvio;
    }

    public void setDesvio(String desvio) {
        this.desvio = desvio;
    }

}
