package pedrodacosta.desafio.gazeus.testegazeusapplication.model;

/**
 * Created by Pedro Henrique on 23/08/17.
 */

public class CampoGrid {

    private int idView;
    private boolean marcado;
    private int idDrawable;

    public CampoGrid(int idView) {
        this.idView = idView;
        this.marcado = false;
    }

    public void setIdView(int idView) {
        this.idView = idView;
    }

    public int getIdView() {
        return idView;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void setIdDrawable(int idDrawable) {
        this.idDrawable = idDrawable;
    }

    public int getIdDrawable() {
        return idDrawable;
    }
}
