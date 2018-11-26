package com.lambdaschool.threads;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button mButton;
    TextView encodedText;
    EditText shiftText;
    ProgressBar progress;

    public static class MyParams {
        int shift;
        String text;

        MyParams(int shift, String text) {
            this.shift = shift;
            this.text = text;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = findViewById(R.id.progress);
        encodedText = findViewById(R.id.encoded_text);
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strText = encodedText.getText().toString();
                shiftText = findViewById(R.id.editText);
                int shiftSteps = Integer.parseInt(shiftText.getText().toString());
                MyParams myParams = new MyParams(shiftSteps, strText);
                Log.d("THREADS", shiftSteps + " & " + strText);
                MyTask aTask = new MyTask();
                aTask.execute(myParams);


            }
        });
    }

    public static String shiftCypher(String text, int shiftSteps) {
        if (text != null) {
            String newText  = "";
            for (int i = 0; i < text.length(); ++i) {
                char unicode = text.charAt(i);
                if ((unicode >= 'A' && unicode <= 'Z') || ((unicode >= 'a' && unicode <= 'z'))) {
                    for (int n = 0; n < shiftSteps; ++n) {
                        ++unicode;
                        if (unicode > 'Z' && unicode < 'a') {
                            unicode = 'A';
                        }
                        if (unicode > 'z') {
                            unicode = 'a';
                        }
                    }
                }
                newText += unicode;
            }
            return newText;
        } else {
            return "";
        }
    }



    public class MyTask extends AsyncTask<MyParams, Integer, String> {


        @Override
        protected String doInBackground(MyParams... myParams) {
            int shift = myParams[0].shift;
            String text = myParams[0].text;
            Log.d("DoInBackground", shift + " & " + text);
            String result = shiftCypher(text, shift);
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.setVisibility(View.GONE);
            encodedText.setText(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
