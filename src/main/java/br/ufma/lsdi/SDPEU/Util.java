package br.ufma.lsdi.SDPEU;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import static android.content.Context.WIFI_SERVICE;
import static android.os.SystemClock.sleep;

/**
 * Created by makleyston on 11/11/17.
 */

public class Util {

    public static Util instance = null;
    private Context context = null;
    private int seqConsole = 1;

    //IP'of smartphone ip[0]
    final String[] ip = new String[1];

    private Util() {}

    public static Util getInstance(Context context) {
        if(instance == null)
            instance = new Util();
        if(context != null)
            instance.setContext(context);
        return instance;
    }

    private void setContext(Context context){
        this.context = context;
    }

    /**
     * Get IP of smartphone
     * @param context
     * @return String - IP
     */
    public String getIP(Context context) {
        try {
            @SuppressLint("WifiManagerLeak") WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            return String.format(Locale.getDefault(), "%d.%d.%d.%d",
                    (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                    (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    public void addMsgConsole(final TextView txt, final String msg, Handler handler){
        if(handler != null) {
            final Runnable r = new Runnable() {
                public void run() {
                    txt.setText(txt.getText().toString() + "\n" + (seqConsole++) + "# " + msg);
                }
            };
            handler.post(r);
        }else {
            txt.setText(txt.getText().toString() + "\n" + (seqConsole++) + "# " + msg);
        }
    }

    public void wifi(final Handler handler, final TextView txt_idaddress, final Button btn_select_master) {
        final SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.app_name), Context.MODE_PRIVATE);
        final String[] ip_master = new String[1];
        Thread wifi = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            ip_master[0] = sharedPref.getString("ip_master", "");
                            ip[0] = getIP(context).trim();
                            //0.0.0.0
                            if (!ip[0].equals("0.0.0.0")) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (ip_master[0] == "") {
                                            txt_idaddress.setText(ip[0]);
                                            //btn_select_master.setText(context.getResources().getString(R.string.lbl_select_master));
                                        }else {
                                            txt_idaddress.setText(ip_master[0]);
                                            //btn_select_master.setText(context.getResources().getString(R.string.lbl_select_master_cancel));
                                        }
                                    }
                                });
                            } else {
                                if (!txt_idaddress.getText().toString().trim().equals("0.0.0.0"))
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            txt_idaddress.setText("0.0.0.0");
                                        }
                                    });
                            }
                            sleep(1000);
                        }
                    }
                }
        );
        wifi.start();
    }

    //Ok
    public String formattedIP(String ip) {
        String str = null;
        if (ip == null)
            return str;
        if (ip.trim().length() == 0)
            return str;
        if ((ip.split("[.]").length == 1) && (ip.length() == 12)) {
            String p1 = ip.substring(0, 2);
            String p2 = ip.substring(3, 5);
            String p3 = ip.substring(6, 8);
            String p4 = ip.substring(9, 11);
            str = p1 + "." + p2 + "." + p3 + "." + p4;
            return str;
        }
        if ((ip.split("[.]").length < 4))
            return str;
        return ip;
    }

}
