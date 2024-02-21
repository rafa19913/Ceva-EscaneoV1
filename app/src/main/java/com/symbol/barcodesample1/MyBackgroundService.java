package com.symbol.barcodesample1;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.os.Handler;


public class MyBackgroundService extends Service{
    private static final String TAG = "LogService";
    private Handler handler;
    private Runnable logTask;




    // Define una cadena de acción para el broadcast
    public static final String ACTION_APP_STATE_CHANGED = "com.symbol.barcodesample1.APP_STATE_CHANGED";


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
        logTask = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Log enviado desde el servicio en segundo plano.");



                // Programa la próxima ejecución después de 5 segundos
                handler.postDelayed(this, 5 * 1000);
            }
        };



    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Registra el BroadcastReceiver para recibir cambios de estado de la aplicación
        registerReceiver(appStateReceiver, new IntentFilter(ACTION_APP_STATE_CHANGED));

        // Inicia el servicio y programa la tarea
        handler.post(logTask);
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        // Elimina el BroadcastReceiver cuando la actividad se destruye
        unregisterReceiver(appStateReceiver);
        super.onDestroy();
    }

    private final BroadcastReceiver appStateReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            // Aquí puedes realizar acciones en respuesta al cambio de estado
            if (intent.getAction() != null && intent.getAction().equals(MyBackgroundService.ACTION_APP_STATE_CHANGED)) {
                appStateChanged();
            }
        }
    };

    // Método que se llama cuando hay un cambio en el estado de la aplicación
    public static void appStateChanged() {
        // Realiza acciones específicas en respuesta al cambio de estado
        // Por ejemplo, puedes realizar acciones cuando la aplicación pasa a segundo plano
        // ...
    }
    private void sendAppStateChangeBroadcast(String action) {
        Intent intent = new Intent(ACTION_APP_STATE_CHANGED);
        intent.putExtra("ACTION_TO_PERFORM", action);
        sendBroadcast(intent);
    }


}
