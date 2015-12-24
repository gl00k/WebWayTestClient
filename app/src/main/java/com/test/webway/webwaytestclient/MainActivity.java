package com.test.webway.webwaytestclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
public class MainActivity extends AppCompatActivity {

    private ListView mList;
    private ArrayList<String> arrayList;
    private MyCustomAdapter mAdapter;
    private Client mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList = new ArrayList<>();

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button send = (Button)findViewById(R.id.send_button);

        mList = (ListView)findViewById(R.id.list);
        mAdapter = new MyCustomAdapter(this, arrayList);
        mList.setAdapter(mAdapter);

        new connectTask().execute("");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = editText.getText().toString();
                if (mClient != null) {
                    mClient.sendMessage(message);
                }
                mAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
    }

    public class connectTask extends AsyncTask<String,String,Client> {

        @Override
        protected Client doInBackground(String... message) {

            mClient = new Client(new Client.OnMessageReceived() {
                @Override
                public void messageReceived(String message) {
                    publishProgress(message);
                }
            });
            mClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            arrayList.add(values[0]);
            mAdapter.notifyDataSetChanged();
        }
    }

}
