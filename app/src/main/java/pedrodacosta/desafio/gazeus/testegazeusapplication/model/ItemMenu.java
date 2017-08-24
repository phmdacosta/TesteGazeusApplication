package pedrodacosta.desafio.gazeus.testegazeusapplication.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Pedro Henrique on 20/08/2017.
 */

public class ItemMenu {

    private long id;
    private Drawable imagemDrawable;
    private String titulo;
    private String subtitulo;
    private String activityAlvo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Drawable getImagemDrawable() {
        return imagemDrawable;
    }

    public void setImagemDrawable(Drawable imagemDrawable) {
        this.imagemDrawable = imagemDrawable;
    }

    public String getActivityAlvo() {
        return activityAlvo;
    }

    public void setActivityAlvo(String activityAlvo) {
        this.activityAlvo = activityAlvo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
