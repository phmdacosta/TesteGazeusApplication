package pedrodacosta.desafio.gazeus.testegazeusapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Pedro Henrique on 23/08/17.
 */

public class EscolhaMarcadorActivity extends AppCompatActivity {

    public static final String ESCOLHA_MARCADOR = "marcador";
    public static final int O = 0;
    public static final int X = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha_marcador);
    }

    public void escolheuX(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(ESCOLHA_MARCADOR, X);
        startActivity(intent);
        finish();
    }

    public void escolheuO(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(ESCOLHA_MARCADOR, O);
        startActivity(intent);
        finish();
    }
}
