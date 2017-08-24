package pedrodacosta.desafio.gazeus.testegazeusapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pedrodacosta.desafio.gazeus.testegazeusapplication.adapter.ListMenuAdapter;
import pedrodacosta.desafio.gazeus.testegazeusapplication.model.ItemMenu;

/**
 * Created by Pedro Henrique on 20/08/2017.
 */

public class MainActivity extends AppCompatActivity {

    private ListView listViewMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ItemMenu> listItemMenu = carregarItensMenu();
        ListMenuAdapter adapter = new ListMenuAdapter(this, listItemMenu);
        listViewMenu = (ListView) findViewById(R.id.lista_menu);
        listViewMenu.setAdapter(adapter);
    }

    private List<ItemMenu> carregarItensMenu() {
        List<ItemMenu> itensMenu = new ArrayList<>();

        ItemMenu itemMenu1 = new ItemMenu();
        itemMenu1.setId(1);
        itemMenu1.setTitulo("Novo Jogo");
        itemMenu1.setActivityAlvo("pedrodacosta.desafio.gazeus.testegazeusapplication.EscolhaMarcadorActivity");
        itensMenu.add(itemMenu1);

        ItemMenu itemMenu2 = new ItemMenu();
        itemMenu2.setId(1);
        itemMenu2.setTitulo("Sair");
        itemMenu2.setActivityAlvo("finish");
        itensMenu.add(itemMenu2);

        return itensMenu;
    }
}
