package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    TextView txtShow;
    Button btnCheck;
    EditText editText;
    private String url="";
    private String port ="";
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("https://socket-io-chat.now.sh/");
        } catch (URISyntaxException e) {}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSocket.connect();
        mSocket.on("login", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.e("ABC", args[0].toString());
               // Toast.makeText(MainActivity.this,"Login:" + args[0].toString(),Toast.LENGTH_SHORT).show();
            }
        });
        mSocket.emit("add user","Dang Vi");
        mSocket.on("new message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.e("ABC", args[0].toString().trim());
            }
        });
    }
    public void sendMessage(View view){
        EditText edtMessage = findViewById(R.id.edtText);
        String message = edtMessage.getText().toString().trim();
        if (!message.isEmpty()){
            mSocket.emit("new message",message);
            edtMessage.setText("");
        } else edtMessage.setError("Please enter text..");
    }
}
