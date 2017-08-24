package pedrodacosta.desafio.gazeus.testegazeusapplication.listener;

import android.os.AsyncTask;
import android.os.SystemClock;

import java.util.List;
import java.util.Random;

import pedrodacosta.desafio.gazeus.testegazeusapplication.model.CampoGrid;
import pedrodacosta.desafio.gazeus.testegazeusapplication.util.ConstantesJV;

/**
 * Created by Pedro Henrique on 23/08/17.
 */

public class JogadaIATask extends AsyncTask {

    private GerenciarJogoListener listener;
    private List<CampoGrid> campos;
    private CampoGrid[][] camposGrid;
    private int idDrawableMarca;
    private int dificuldade;
    private int idCampoVazio;
    private boolean executando;

    //Intancia o asyncTask da jogada da IA
    public JogadaIATask(GerenciarJogoListener listener, List<CampoGrid> campos, int idDrawableMarca, int dificuldade) {
        this.listener = listener;
        this.campos = campos;
        this.idDrawableMarca = idDrawableMarca;
        this.dificuldade = dificuldade;
    }

    public JogadaIATask(GerenciarJogoListener listener, CampoGrid[][] camposGrid, int idDrawableMarca, int dificuldade) {
        this.listener = listener;
        this.camposGrid = camposGrid;
        this.idDrawableMarca = idDrawableMarca;
        this.dificuldade = dificuldade;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        executando = true;

        //Delay da IA
        SystemClock.sleep(2000);

        switch (dificuldade) {
            case ConstantesJV.FACIL:
                //No modo fácil, a IA pega qualquer espaço q não foi marcado por X ou O
                if (possuiCampoVazio()) {
                    executaJogadaFacilMatrix();
                }
                break;

            case ConstantesJV.MEDIO:
                //No modo médio, a IA dá prioridade para atrapalhar o jogo do adversário
                if (possuiCampoVazio()) {
                    executaJogadaMedio();
                }
                break;

            case ConstantesJV.DIFICIL:
                if (possuiCampoVazio()) {
                    executaJogadaMedio();
                }
                break;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        //Marcando o campo vazio com o marcador da IA
        listener.marcarCampo(idCampoVazio, idDrawableMarca);
        if (listener.isVitoria(idDrawableMarca)) {
            listener.finalizarJogo("COMP ganhou!");
        }
        listener.trocarVezJogador(true);
        executando = false;
    }

    //TODO usar no onUserInterface da tela do jogo para evitar ação do jogador enquanto for a vez da IA
    public boolean isExecutando() {
        return executando;
    }

    private void executaJogadaFacilList() {
        Random random = new Random();
        int i = random.nextInt(campos.size());

        if (!campos.get(i).isMarcado()) {
            idCampoVazio = campos.get(i).getIdView();
        }
    }

    private void executaJogadaFacilMatrix() {
        int l = 0;
        int c = 0;

        do {
            Random random = new Random();
            l = random.nextInt(camposGrid.length);
            c = random.nextInt(camposGrid[l].length);

            if (!camposGrid[l][c].isMarcado()) {
                idCampoVazio = camposGrid[l][c].getIdView();
            }
        } while (idCampoVazio == 0);
    }

    private void executaJogadaMedio() {
        int linhaAFechar = -1;
        int colunaAFechar = -1;
        boolean diagonalAFechar = false;
        int xCampoMarcado = -1;
        int yCampoMarcado = -1;
        int countMarcados = 0;

        //Verificando as opções de jogada
        for (int l = 0; l < camposGrid.length; l++) {
            for (int c = 0; c < camposGrid[l].length; c++) {
                CampoGrid campo = camposGrid[l][c];

                if (campo.isMarcado() && campo.getIdDrawable() != idDrawableMarca) {
                    if (l == xCampoMarcado && c != yCampoMarcado) {
                        linhaAFechar = l;
                        if (countMarcados >= 2)
                            break;
                    } else if (c == yCampoMarcado && l != xCampoMarcado) {
                        colunaAFechar = c;
                        if (countMarcados >= 2)
                            break;
                    } else if (l == c && l != xCampoMarcado) {
                        diagonalAFechar = true;
                        if (countMarcados >= 2)
                            break;
                    }
                    countMarcados++;
                    xCampoMarcado = l;
                    yCampoMarcado = c;
                }
            }

            if (countMarcados >= 2
                    && (linhaAFechar > -1 || colunaAFechar > -1 || diagonalAFechar)) {
                break;
            }
        }

        //Efetuando jogada
        if (countMarcados >= 2) {
            if (linhaAFechar >= 0) {
                for (int c = 0; c < camposGrid[linhaAFechar].length; c++) {
                    CampoGrid campo = camposGrid[linhaAFechar][c];
                    if (!campo.isMarcado()) {
                        idCampoVazio = campo.getIdView();
                    }
                }
            }
            else if (colunaAFechar >= 0) {
                for (int l = 0; l < camposGrid.length; l++) {
                    CampoGrid campo = camposGrid[l][colunaAFechar];
                    if (!campo.isMarcado()) {
                        idCampoVazio = campo.getIdView();
                    }
                }
            }
            else if (diagonalAFechar) {
                for (int l = 0; l < camposGrid.length; l++) {
                    for (int c = 0; c < camposGrid[l].length; c++) {
                        CampoGrid campo = camposGrid[l][c];

                        if (l == c && !campo.isMarcado()) {
                            idCampoVazio = campo.getIdView();
                        }
                    }
                }
            }
        }

        if (idCampoVazio == 0) {
            executaJogadaFacilMatrix();
        }
    }

    private boolean possuiCampoVazio() {
        for (int l = 0; l < camposGrid.length; l++) {
            for (int c = 0; c < camposGrid[l].length; c++) {
                CampoGrid campo = camposGrid[l][c];
                if (!campo.isMarcado())
                    return true;
            }
        }

        return false;
    }
}
