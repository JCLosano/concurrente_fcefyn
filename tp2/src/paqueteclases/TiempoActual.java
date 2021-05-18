package paqueteclases;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TiempoActual {
	
	public TiempoActual(){
	}
	
    public String getTiempo() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
        return sdf.format(cal.getTime());
    }
}