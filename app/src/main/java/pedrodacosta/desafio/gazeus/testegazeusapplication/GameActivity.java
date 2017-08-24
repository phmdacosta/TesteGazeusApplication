package pedrodacosta.desafio.gazeus.testegazeusapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import pedrodacosta.desafio.gazeus.testegazeusapplication.listener.JogadaIATask;
import pedrodacosta.desafio.gazeus.testegazeusapplication.listener.GerenciarJogoListener;
import pedrodacosta.desafio.gazeus.testegazeusapplication.model.CampoGrid;
import pedrodacosta.desafio.gazeus.testegazeusapplication.util.ConstantesJV;

/**
 * Created by Pedro Henrique on 20/08/2017.
 */

public class GameActivity extends AppCompatActivity implements GerenciarJogoListener {

    private TextView cabecalhoGame;
    private int marcadorJogador;
    private JogadaIATask jogadaIATask;
    private GerenciarJogoListener gerenciarJogoListener;
    private CampoGrid[][] camposGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (getIntent() != null) {
            marcadorJogador = getIntent().getIntExtra(EscolhaMarcadorActivity.ESCOLHA_MARCADOR, 0);
        }

        cabecalhoGame = (TextView) findViewById(R.id.cabecalho_game);
        trocarVezJogador(true);
        gerenciarJogoListener = this;

        criarMatrizJogo();
        prepararJogo();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public int marcarCampo(int idCampoView, int idImageDrawable) {
        try {
            RelativeLayout espacoMarcador = (RelativeLayout) findViewById(idCampoView);
            ImageView imageView = (ImageView) espacoMarcador.getChildAt(0);
            imageView.setImageResource(idImageDrawable);
            imageView.setVisibility(View.VISIBLE);

            for (int l = 0; l < camposGrid.length; l++) {
                for (int c = 0; c < camposGrid[l].length; c++) {
                    CampoGrid campoGrid = camposGrid[l][c];
                    if (campoGrid.getIdView() == idCampoView) {
                        campoGrid.setMarcado(true);
                        campoGrid.setIdDrawable(idImageDrawable);
                    }
                }
            }
        } catch (Exception e) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean isVitoria(int idImageDrawable) {
        int xCampoMarcadoLinha = -1;
        int yCampoMarcadoLinha = -1;
        int xCampoMarcadoColuna = -1;
        int yCampoMarcadoColuna = -1;
        int xCampoMarcadoDiagonal = -1;
        int yCampoMarcadoDiagonal = -1;
        int xCampoMarcadoDiagonalInvers = -1;
        int yCampoMarcadoDiagonalInvers = -1;
        int countMarcadosLinha = 0;
        int countMarcadosColuna = 0;
        int countMarcadosDiagonal = 0;
        int countMarcadosDiagonalInvers = 0;

        for (int l = 0; l < camposGrid.length; l++) {
            for (int c = 0; c < camposGrid[l].length; c++) {
                CampoGrid campoGrid = camposGrid[l][c];

                if (campoGrid.isMarcado() && campoGrid.getIdDrawable() == idImageDrawable) {
                    if (countMarcadosLinha == 0) {
                        countMarcadosLinha++;
                        xCampoMarcadoLinha = l;
                        yCampoMarcadoLinha = c;
                    }

                    if (countMarcadosColuna == 0) {
                        countMarcadosColuna++;
                        xCampoMarcadoColuna = l;
                        yCampoMarcadoColuna = c;
                    }

                    if (l == xCampoMarcadoLinha && c != yCampoMarcadoLinha) {
                        countMarcadosLinha++;
                        if (countMarcadosLinha == 3) {
                            return true;
                        }
                        xCampoMarcadoLinha = l;
                        yCampoMarcadoLinha = c;
                    }
                    if (c == yCampoMarcadoColuna && l != xCampoMarcadoColuna) {
                        countMarcadosColuna++;
                        if (countMarcadosColuna == 3) {
                            return true;
                        }
                        xCampoMarcadoColuna = l;
                        yCampoMarcadoColuna = c;
                    }
                    if (l == c && l != xCampoMarcadoDiagonal) {
                        countMarcadosDiagonal++;
                        if (countMarcadosDiagonal == 3) {
                            return true;
                        }
                        xCampoMarcadoDiagonal = l;
                        yCampoMarcadoDiagonal = c;
                    }
                    if ((l + c == 2) && l != xCampoMarcadoDiagonalInvers) {
                        countMarcadosDiagonalInvers++;
                        if (countMarcadosDiagonalInvers == 3) {
                            return true;
                        }
                        xCampoMarcadoDiagonalInvers = l;
                        yCampoMarcadoDiagonalInvers = c;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void finalizarJogo(String msgFinal) {
        Intent intent = new Intent(this, FimActivity.class);
        intent.putExtra(FimActivity.TEXTO_FINAL, msgFinal);
        startActivity(intent);
    }

    @Override
    public void trocarVezJogador(boolean vezJogador) {
        if (vezJogador) {
            cabecalhoGame.setText("VEZ DO JOGADOR 1");
        } else {
            cabecalhoGame.setText("VEZ DO COMP");
        }
    }

    public void sair(View v) {
        finish();
    }

    private void prepararJogo() {
        for (int l = 0; l < camposGrid.length; l++) {
            for (int c = 0; c < camposGrid[l].length; c++) {
                CampoGrid campoGrid = camposGrid[l][c];
                RelativeLayout view = (RelativeLayout) findViewById(campoGrid.getIdView());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (jogadaIATask == null || !jogadaIATask.isExecutando()) {
                            RelativeLayout espacoMarcador = (RelativeLayout) v;
                            ImageView imageView = (ImageView) espacoMarcador.getChildAt(0);

                            int drawableJogador;
                            int drawableComp;

                            if (marcadorJogador == EscolhaMarcadorActivity.X) {
                                drawableJogador = R.drawable.check_x;
                                drawableComp = R.drawable.check_circle;
                            } else {
                                drawableJogador = R.drawable.check_circle;
                                drawableComp = R.drawable.check_x;
                            }

                            marcarCampo(espacoMarcador.getId(), drawableJogador);
                            if (isVitoria(drawableJogador)) {
                                finalizarJogo("Jogador 1 ganhou!");
                            } else {
                                trocarVezJogador(false);
                                jogadaIATask = new JogadaIATask(gerenciarJogoListener, camposGrid, drawableComp, ConstantesJV.MEDIO);
                                jogadaIATask.execute();
                            }
                        }
                    }
                });
            }
        }
    }

    private void criarMatrizJogo() {
        camposGrid = new CampoGrid[3][3];

        CampoGrid campoGridSupEsqr = new CampoGrid(R.id.marca_super_esqr);
        camposGrid[0][0] = campoGridSupEsqr;
        CampoGrid campoGridSupCent = new CampoGrid(R.id.marca_super_centro);
        camposGrid[0][1] = campoGridSupCent;
        CampoGrid campoGridSupDir = new CampoGrid(R.id.marca_super_diret);
        camposGrid[0][2] = campoGridSupDir;
        CampoGrid campoGridCentEsqr = new CampoGrid(R.id.marca_centro_esqr);
        camposGrid[1][0] = campoGridCentEsqr;
        CampoGrid campoGridCentCent = new CampoGrid(R.id.marca_centro_centro);
        camposGrid[1][1] = campoGridCentCent;
        CampoGrid campoGridCentDir = new CampoGrid(R.id.marca_centro_diret);
        camposGrid[1][2] = campoGridCentDir;
        CampoGrid campoGridInfEsqr = new CampoGrid(R.id.marca_infer_esqr);
        camposGrid[2][0] = campoGridInfEsqr;
        CampoGrid campoGridInfCent = new CampoGrid(R.id.marca_infer_centro);
        camposGrid[2][1] = campoGridInfCent;
        CampoGrid campoGridInfDir = new CampoGrid(R.id.marca_infer_diret);
        camposGrid[2][2] = campoGridInfDir;
    }
}
