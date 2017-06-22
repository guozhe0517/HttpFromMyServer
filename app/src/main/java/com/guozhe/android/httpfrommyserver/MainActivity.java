package com.guozhe.android.httpfrommyserver;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        networkTask("http://192.168.11.255:8080/aaa.jsp");

    }
    public void networkTask(String url){
        new AsyncTask<String,Void,String>(){

            @Override
            protected String doInBackground(String... params) {
                String result = getData(params[0]);

                return result;
            }
            @Override
            protected void onPostExecute(String r) {
                super.onPostExecute(r);
                Log.e("RESULT" , "결솨" +r);
            }
        }.execute(url);
    }

    public String getData(String url){
        StringBuilder result = new StringBuilder();
        try {
            //1.요청처리
            URL serverUrl = new URL(url);
            //주소에 해당하는 서버의 socket 을 연결
            HttpURLConnection con =(HttpURLConnection)serverUrl.openConnection();
            //OutputStream으로 데이터를 요청
            con.setRequestMethod("GET");

            //2. 응답처리
            //응답의 유효성 검사
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                //줄단위로 데이터를 읽기 위해서 버퍼에 담느다
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String temp = "";
               while ((temp = br.readLine()) != null){
                   result.append(temp+"\n");
               }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return result.toString();

    }
}
