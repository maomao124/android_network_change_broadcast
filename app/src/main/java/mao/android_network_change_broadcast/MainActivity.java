package mao.android_network_change_broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    /**
     * 标签
     */
    private static final String TAG = "MainActivity";
    private TextView textView;
    private NetworkReceiver networkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.TextView);


    }


    private class NetworkReceiver extends BroadcastReceiver
    {

        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent == null)
            {
                return;
            }
            NetworkInfo networkInfo = intent.getParcelableExtra("networkInfo");
            String s = "网络类型：" + networkInfo.getTypeName() + ",网络子类型：" + networkInfo.getSubtypeName()
                    + ",网络状态：" + networkInfo.getState().toString();
            Log.d(TAG, "onReceive: " + s);
            textView.setText(textView.getText() +"\n\n"+ s);
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        networkReceiver = new NetworkReceiver();
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkReceiver, intentFilter);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        unregisterReceiver(networkReceiver);
    }
}