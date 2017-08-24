package pedrodacosta.desafio.gazeus.testegazeusapplication.util;

import android.util.Log;

/**
 * Created by Pedro Henrique on 20/08/2017.
 */

public class Util {

    public static Class getActivityByName(String name) {
        Class activityClass = null;

        try {
            activityClass = Class.forName(name);
        } catch (ClassNotFoundException e) {
            StringBuffer msgErro = new StringBuffer();
            msgErro.append("Classe <");
            msgErro.append(name);
            msgErro.append("> não encontrada.");
            Log.e("Util", msgErro.toString());
        }

        return activityClass;
    }
}
