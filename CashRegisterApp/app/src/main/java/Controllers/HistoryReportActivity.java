package Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cashregisterapp.Model.PurchaseHistory;
import com.example.cashregisterapp.R;

import java.util.ArrayList;

public class HistoryReportActivity extends AppCompatActivity implements HistoryListViewAdapter.ListClickListener {

    //Declaring a "myhistorylist" ArrayList
    ArrayList<PurchaseHistory> myhistorylist = new ArrayList<>();

    //Layout Widgets declaration
    RecyclerView historyRecyclerview;
    TextView historytextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_report);

        //Finding layout widgets by id and connecting them to the main activity
        historytextview = findViewById(R.id.historytextView);
        historyRecyclerview = findViewById(R.id.HistoryRecyclerView);

        historytextview.setVisibility(View.INVISIBLE);
        historyRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        //Receiving data from "ManagerActivity" activity
        Intent frommangerintent = getIntent();
        if(!(this.getIntent().getExtras().getParcelableArrayList("listOfHistory").isEmpty()))
        {
            myhistorylist = this.getIntent().getExtras().getParcelableArrayList("listOfHistory");
            //System.out.println(myhistorylist);
            HistoryListViewAdapter adapter = new HistoryListViewAdapter(this, myhistorylist);
            adapter.listener = this;
            historyRecyclerview.setAdapter(adapter);
        }
        else {System.out.println("Empty History");
            historytextview.setText("NO PURCHASE HISTORY");
            historytextview.setVisibility(View.VISIBLE);
            historyRecyclerview.setVisibility(View.INVISIBLE);
        }
        System.out.println("Printing History in HistoryActivity:");

    }

    //Required actions when an iten is selected from the list of purchase history
    @Override
    public void onHistorySelected(PurchaseHistory selectedHistory) {
        Intent toDetailActivity = new Intent(this, HistoryDetailsActivity.class);
        toDetailActivity.putExtra("historyDetails",selectedHistory);
        startActivity(toDetailActivity);
        System.out.println("My history details are: \n"+ selectedHistory.toString());
        Toast.makeText(this, "History details being sent", Toast.LENGTH_SHORT).show(); }
}
