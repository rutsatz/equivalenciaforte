/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import model.AplicacaoModel;
import model.IRComposta;
import model.InstrucaoRotulada;
import model.Rotulo;

/**
 *
 * @author Rafael Rutsatz
 */
public class Passo1 {

    public final static int QTD_LOOP_CICLO = 50;

    public LinkedList<InstrucaoRotulada> instrucoes;

    public LinkedList<IRComposta> lista = new LinkedList<>();

    public static int contadorRotuloComposto = 1;
    public int contPosAtu = 0;
    StringBuilder texto;

    public IRComposta iRC = new IRComposta();
    public InstrucaoRotulada instrucaoInicial;
    public int executou_em_loop = 0;
    public boolean primeira_coluna = true;
    public boolean encontrou = false;
    public int indiceInstrucaoEmTeste;

    public boolean erro = false;
    public boolean temCiclo = false;

    public Passo1(LinkedList<InstrucaoRotulada> instrucoes) {
        this.instrucoes = instrucoes;
        contPosAtu = 0;
        executou_em_loop = 0;
        primeira_coluna = true;
        encontrou = false;
    }

    public int busca_proxima_instrucao(boolean primeiroConjunto, InstrucaoRotulada instrucaoInicial2) {
        // SE FOR OPERACAO, NAO IMPORTA SE É PRIMEIRO OU SEGUNDO CONJUNTO.. O RESULTADO É IGUAL.
        if (instrucaoInicial2.getOpTeste().getRotulo() == Rotulo.TESTE
                || instrucaoInicial2.getOpTeste().getRotulo() == Rotulo.PARTIDA) {

            if (primeiroConjunto) {
                return Integer.valueOf(instrucaoInicial2.getOpVerdade().getDesvio());
            } else {
                return Integer.valueOf(instrucaoInicial2.getOpFalso().getDesvio());
            }
        } else {

            // Devo retornar sempre o destino da operação, pois posso ter uma operação
            // por exemplo (G,3) mas devo buscar a linha 1.
            return instrucaoInicial2.getDestinoOperacao();
        }

    }

    // rotula as operacoes das intrucoes monoliticas, conforme serão executadas em instrucoes compostas
    public List<InstrucaoRotulada> rotulaOperacoesEmOrdemExecucao(List<InstrucaoRotulada> instrucoes2) {
        List<Integer> listaOrdemExecucaoOperacoes = new ArrayList<>();

        int contadorRotuloComposto = 1;

        int posicaoAtual = 0;
        // adiciona em uma lista, a ordem com que os rotulos sao executadas!
        for (InstrucaoRotulada instrucao : instrucoes) {

            if (instrucao.getOpTeste().getRotulo() == Rotulo.PARTIDA) {
                contadorRotuloComposto++;
            }

            if (instrucao.getOpTeste().getRotulo() == Rotulo.TESTE) {
                listaOrdemExecucaoOperacoes.add(Integer.valueOf(instrucao.getOpVerdade().getDesvio()));
                listaOrdemExecucaoOperacoes.add(Integer.valueOf(instrucao.getOpFalso().getDesvio()));
            }

            // Se for operacao
            if (instrucao.getOpTeste().getRotulo() != Rotulo.TESTE
                    && instrucao.getOpTeste().getRotulo() != Rotulo.PARTIDA) {
                // caso de quando a primeira instrucao e uma operacao, ele nao executa a proxima! ele executa ele mesmo!
                if (posicaoAtual == 1) {
                    listaOrdemExecucaoOperacoes.add(1);
                } else {
                    // executa o destino
                    listaOrdemExecucaoOperacoes.add(Integer.valueOf(
                            instrucao.getOpVerdade().getDesvio()));
                }
            }

            posicaoAtual++;
        }

        // percorre a lista de ordem, identificando operacoes e numerando elas (rotulo) conforme sao executadas
        for (Integer indexTesteAtual : listaOrdemExecucaoOperacoes) {
            try {
                InstrucaoRotulada instrucao = instrucoes.get(indexTesteAtual.intValue());
                // Se for operacao
                if (instrucao.getOpTeste().getRotulo() == Rotulo.NENHUM
                        && instrucao.verificado == false) {

                    instrucao.getOpVerdade().setDesvio(String.valueOf(contadorRotuloComposto));
                    instrucao.getOpFalso().setDesvio(String.valueOf(contadorRotuloComposto));

                    instrucao.verificado = true;
                    instrucoes.set(indexTesteAtual, instrucao);
                    contadorRotuloComposto++;
                }
            } catch (Exception e) {
                // as vezes o destino e uma operacao vazia/ nao existe o rotulo, nao faz nada neste caso
            }
        }

        return instrucoes;
    }

    /*
    Gera a nova lista de Instruções Rotuladas Compostas baseado na lista
    com as instruções monolíticas.
     */
    public LinkedList<IRComposta> gerar() throws Exception {

        if (AplicacaoModel.LOG_SAIDA_ATIVO) {
            System.out.println("\nInstruções na classe IRComposta geradas:");
        }

        rotulaOperacoesEmOrdemExecucao(instrucoes);

        instrucaoInicial = instrucoes.get(contPosAtu);

        int aux = 0;

        // Faz o for percorrendo todas as instruções e 
        // fica em loop enquanto for um teste, até achar a operação correta.
        for (int i = 0; i < instrucoes.size(); i++) {
            InstrucaoRotulada instrucao = instrucoes.get(contPosAtu);

            // Se for uma operacao ou a partida
            if (instrucao.getOpTeste().getRotulo() == Rotulo.NENHUM
                    || instrucao.getOpTeste().getRotulo() == Rotulo.PARTIDA) {

                primeira_coluna = true;

                indiceInstrucaoEmTeste = busca_proxima_instrucao(primeira_coluna, instrucaoInicial);

                iRC = new IRComposta();
                instrucaoInicial = instrucoes.get(contPosAtu);
                executou_em_loop = 0;
                primeira_coluna = true;
                encontrou = false;
                indiceInstrucaoEmTeste = busca_proxima_instrucao(primeira_coluna, instrucaoInicial);
                erro = false;

                aux = indiceInstrucaoEmTeste;

                // Loops que ficam procurando a instrução enquanto for um teste.
                while (iRC.getOpV() == null) {
                    iRC = buscaOperacao(Rotulo.OPERACAO_VERDADEIRO, instrucao, iRC, instrucoes);
                }
                while (iRC.getOpF() == null) {

                    IRComposta iRC2 = buscaOperacao(Rotulo.OPERACAO_FALSO, instrucao, iRC, instrucoes);
                    iRC.setOpF(iRC2.getOpF());
                    iRC.setIdOpF(iRC2.getIdOpF());
                    iRC.setRotuloF(iRC2.getRotuloF());
                }

                iRC.setIr(contadorRotuloComposto);
                lista.add(iRC);
                contadorRotuloComposto++;

                indiceInstrucaoEmTeste = aux;
            }
            contPosAtu++;
        }

        if (AplicacaoModel.LOG_SAIDA_ATIVO) {
            System.out.println("\nIRComposta formatada:");
            System.out.println(comoString(lista));

        }
        return lista;
    }

    public void incrementaRotulos(int valor) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getRotuloV() == Rotulo.OPERANDO) {
                lista.get(i).setIdOpV(String.valueOf(Integer.valueOf(lista.get(i).getIdOpV()) + valor));
            }
            if (lista.get(i).getRotuloF() == Rotulo.OPERANDO) {
                lista.get(i).setIdOpF(String.valueOf(Integer.valueOf(lista.get(i).getIdOpF()) + valor));
            }
        }
    }

    public String comoString(LinkedList<IRComposta> lista) {
        texto = new StringBuilder();

        for (IRComposta iRC : lista) {
            texto.append(iRC.getIr()).append(":(" + iRC.getOpV() + "," + iRC.getIdOpV() + "),("
                    + iRC.getOpF() + "," + iRC.getIdOpF() + ")\n");

        }

        return texto.toString();
    }

    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder();

        for (InstrucaoRotulada instrucao : instrucoes) {
            texto.append("(");
            if (instrucao.getOpTeste().getRotulo() == Rotulo.TESTE) {
                texto.append(instrucao.getOpVerdade().getOperacao()).append(",")
                        .append(instrucao.getOpFalso().getOperacao()).append(")");
            } else {
                texto.append(instrucao.getOpVerdade().getOperacao()).append(",")
                        .append(instrucao.getOpFalso().getOperacao()).append(")");
            }
            texto.append("\n");
        }

        return texto.toString();
    }

    public IRComposta buscaOperacao(Rotulo rotuloOp, InstrucaoRotulada instrucao, IRComposta iRC, LinkedList<InstrucaoRotulada> instrucoes) throws Exception {

        erro = false; // reseta variavel

        InstrucaoRotulada instrucaoEmTeste = null;

        // Já verifica se é uma parada.
        int qtdInstrucoes = instrucoes.size();
        if (indiceInstrucaoEmTeste >= qtdInstrucoes) {
            erro = true;
        } else {
            instrucaoEmTeste = instrucoes.get(indiceInstrucaoEmTeste);
        }

        if (rotuloOp == Rotulo.OPERACAO_VERDADEIRO) {
            primeira_coluna = true;
        } else {
            primeira_coluna = false;
        }
        if (!erro) {
            if (executou_em_loop > QTD_LOOP_CICLO) {
                if (rotuloOp == Rotulo.OPERACAO_VERDADEIRO) {
                    iRC.setRotuloV(Rotulo.CICLO);
                    iRC.setOpV("ciclo");
                    iRC.setIdOpV("w");
                } else {
                    iRC.setRotuloF(Rotulo.CICLO);
                    iRC.setOpF("ciclo");
                    iRC.setIdOpF("w");
                }
                executou_em_loop = 0;
                primeira_coluna = false;
                encontrou = true;
                temCiclo = true;
            } else // Se é uma operacao
            if (instrucaoEmTeste.getOpTeste().getRotulo() == Rotulo.NENHUM) {
                if (rotuloOp == Rotulo.OPERACAO_VERDADEIRO) {
                    iRC.setRotuloV(Rotulo.OPERANDO);
                    iRC.setOpV(instrucaoEmTeste.getOpVerdade().getOperacao());
                    iRC.setIdOpV(instrucaoEmTeste.getOpVerdade().getDesvio());
                } else {
                    iRC.setRotuloF(Rotulo.OPERANDO);
                    iRC.setOpF(instrucaoEmTeste.getOpVerdade().getOperacao());
                    iRC.setIdOpF(instrucaoEmTeste.getOpVerdade().getDesvio());
                }
                executou_em_loop = 0;
                primeira_coluna = false;
                encontrou = true;

            } else {
                // é um teste
                qtdInstrucoes = instrucoes.size();
                if (Integer.parseInt(instrucaoEmTeste.getOpVerdade().getDesvio()) > qtdInstrucoes) {
                    erro = true;
                }
            }
        }
        if (erro) {
            if (rotuloOp == Rotulo.OPERACAO_VERDADEIRO) {
                iRC.setRotuloV(Rotulo.PARADA);
                iRC.setOpV("parada");
                iRC.setIdOpV("&");
            } else {
                iRC.setRotuloF(Rotulo.PARADA);
                iRC.setOpF("parada");
                iRC.setIdOpF("&");
            }
            executou_em_loop = 0;
            primeira_coluna = false;
            encontrou = true;
        }

        // Se encontrou a instrução que estava procurando, busca a proxima da instrução inicial.
        if (encontrou) {
            indiceInstrucaoEmTeste = busca_proxima_instrucao(primeira_coluna, instrucaoInicial);
            encontrou = false;
        } else {
            indiceInstrucaoEmTeste = busca_proxima_instrucao(primeira_coluna, instrucaoEmTeste);
            executou_em_loop++;
        }
        return iRC;
    }

}
