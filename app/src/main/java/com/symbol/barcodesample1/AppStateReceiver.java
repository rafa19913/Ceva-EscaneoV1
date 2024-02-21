package com.symbol.barcodesample1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppStateReceiver extends BroadcastReceiver{

    // Define una interfaz para notificar cambios de estado
    public interface AppStateChangeListener {
        void onAppStateChange();
    }

    private AppStateChangeListener listener;

    public AppStateReceiver(AppStateChangeListener listener) {
        this.listener = listener;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // Notifica al listener cuando se recibe el broadcast
        if (listener != null) {
            listener.onAppStateChange();
        }
    }


}
