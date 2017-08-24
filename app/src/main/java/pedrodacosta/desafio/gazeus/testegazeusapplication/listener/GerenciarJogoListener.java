package pedrodacosta.desafio.gazeus.testegazeusapplication.listener;

/**
 * Created by Pedro Henrique on 23/08/17.
 */

public interface GerenciarJogoListener {
    public int marcarCampo(int idCampoView, int idImageDrawable);
    public boolean isVitoria(int idImageDrawable);
    public void finalizarJogo(String msgFinal);
    public void trocarVezJogador(boolean vezJogador);
}
