package com.example.lenovo.eats.Utility;


import android.os.AsyncTask;
import android.widget.TextView;

public class WTFWaitForReplyAsyncTask extends AsyncTask<Void,Void,Void> {

    TextView title;

    WTFWaitForReplyAsyncTask(TextView txt){
        title = txt;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void...params){

        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
    }

}
