package com.example.cashregisterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cashregisterapp.Model.PurchaseHistory;

public class ManagerActivity extends AppCompatActivity {

    Button history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        history = (Button) findViewById(R.id.btn_history);
       /* if (getIntent().hasExtra(HistoryList)){
            // PurchaseHistory shopperPH = getIntent().getExtras().getParcelable("HistoryList");
            // System.out.println(shopperPH.toString());
        }*/
    }

    public void historyClicked(View v){
    Intent toPurchaseReport = new Intent(this, HistoryReportActivity.class);
    startActivity(toPurchaseReport);}

    public void restockClicked(View v){
        Intent toRestockActivity = new Intent(this, RestockActivity.class);
        startActivity(toRestockActivity);}

}