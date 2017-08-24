package pedrodacosta.desafio.gazeus.testegazeusapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import pedrodacosta.desafio.gazeus.testegazeusapplication.R;
import pedrodacosta.desafio.gazeus.testegazeusapplication.model.ItemMenu;
import pedrodacosta.desafio.gazeus.testegazeusapplication.util.Util;

/**
 * Created by Pedro Henrique on 20/08/2017.
 */

public class ListMenuAdapter extends BaseAdapter {

    private Context context;
    private Activity activity;
    private List<ItemMenu> listItensMenu;

    public ListMenuAdapter(Activity activity, List<ItemMenu> listItensMenu) {
        this.activity = activity;
        this.listItensMenu = listItensMenu;
    }

    @Override
    public int getCount() {
        return listItensMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return listItensMenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listItensMenu.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemMenu itemMenu = (ItemMenu) getItem(position);

        View view;
        if (convertView == null) {
            view = activity.getLayoutInflater().inflate(R.layout.item_menu, parent, false);
        } else {
            view = convertView;
        }

        ViewHolder holder = new ViewHolder(view);

        if (itemMenu.getImagemDrawable() != null) {
            holder.imagemMenu.setImageDrawable(itemMenu.getImagemDrawable());
            holder.imagemMenu.setVisibility(View.VISIBLE);
        }

        holder.tituloMenu.setText(itemMenu.getTitulo());

        if (itemMenu.getSubtitulo() != null && !itemMenu.getSubtitulo().isEmpty()) {
            holder.subtituloMenu.setText(itemMenu.getSubtitulo());
            holder.subtituloMenu.setVisibility(View.VISIBLE);
        }

        if ("finish".equals(itemMenu.getActivityAlvo())) {
            holder.layoutItemMenu.setOnClickListener(new OnClickSairListener());
        } else {
            OnClickStarActivityListener listener = new OnClickStarActivityListener();
            listener.setActivity(itemMenu.getActivityAlvo());
            holder.layoutItemMenu.setOnClickListener(listener);
        }

        return view;
    }

    private class ViewHolder {
        private LinearLayout layoutItemMenu;
        private ImageView imagemMenu;
        private TextView tituloMenu;
        private TextView subtituloMenu;

        private ViewHolder(View view) {
            this.layoutItemMenu = (LinearLayout) view.findViewById(R.id.layout_item_menu);
            this.imagemMenu = (ImageView) view.findViewById(R.id.imagem_item_menu);
            this.tituloMenu = (TextView) view.findViewById(R.id.titulo_item_menu);
            this.subtituloMenu = (TextView) view.findViewById(R.id.subtitulo_item_menu);
        }
    }

    private class OnClickSairListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            activity.finish();
        }
    }

    private class OnClickStarActivityListener implements View.OnClickListener {
        private String activityName;

        @Override
        public void onClick(View v) {
            Class activityClass = Util.getActivityByName(activityName);
            if (activityClass != null) {
                Intent intent = new Intent(activity, activityClass);
                activity.startActivity(intent);
            }
        }

        public void setActivity(String activityName) {
            this.activityName = activityName;
        }
    }
}
