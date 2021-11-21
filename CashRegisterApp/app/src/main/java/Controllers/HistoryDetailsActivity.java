package Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cashregisterapp.Model.PurchaseHistory;
import com.example.cashregisterapp.R;

public class HistoryDetailsActivity extends AppCompatActivity {

    //Layout Widgets declaration
    TextView historyDetails;
    String selectedHistoryDetails;
    //String electedHistorydup;

    //Declaring a "PurchaseHistory" object
    PurchaseHistory selectedHistory;
    //PurchaseHistory selectedHistorydup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);

        //Receiving data from "HistoryReportActivity" activity
        historyDetails = (TextView)findViewById(R.id.hDetailstextView);
        Intent fromHistoryReport = getIntent();

        //Assigning the data from "HistoryReportActivity" activity to "selectedHistory"
        selectedHistory = fromHistoryReport.getParcelableExtra("historyDetails");

        //Showing the history details in the "HistoryDetailsActivity" layout
        selectedHistoryDetails = selectedHistory.toString();
        historyDetails.setText(selectedHistoryDetails);
    }
}