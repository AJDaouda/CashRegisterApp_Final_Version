package com.example.cashregisterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cashregisterapp.Model.PurchaseHistory;

public class HistoryDetailsActivity extends AppCompatActivity {
    TextView historyDetails;
    String selectedHistoryDetails;
    //String electedHistorydup;

    PurchaseHistory selectedHistory;
    //PurchaseHistory selectedHistorydup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        historyDetails = (TextView)findViewById(R.id.hDetailstextView);
        Intent fromHistoryReport = getIntent();


            selectedHistory = fromHistoryReport.getParcelableExtra("historyDetails");
            //selectedHistory = this.getIntent().getParcelableExtra("historyDetails");
        selectedHistoryDetails = selectedHistory.toString();
        historyDetails.setText(selectedHistoryDetails);
    }
}