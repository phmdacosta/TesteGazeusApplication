package pedrodacosta.desafio.gazeus.testegazeusapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Pedro Henrique on 23/08/17.
 */

public class FimActivity extends AppCompatActivity {

    public static final String TEXTO_FINAL = "textofinal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fim);

        TextView textoFim = (TextView) findViewById(R.id.texto_fim);

        if (getIntent() != null) {
            textoFim.setText(getIntent().getStringExtra(TEXTO_FINAL));
        }
    }

    public void sair(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
