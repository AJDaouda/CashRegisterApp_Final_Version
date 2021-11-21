package Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cashregisterapp.Model.Product;
import com.example.cashregisterapp.Model.PurchaseHistory;
import com.example.cashregisterapp.R;

import java.util.ArrayList;

public class ManagerActivity extends AppCompatActivity{

    //External class objects declaration
    ArrayList<PurchaseHistory> mAHistoryList;
    ArrayList<PurchaseHistory> mAHistoryListdup;
    ArrayList<Product> mAProdctList;

    //Layout Widgets declaration
    Button history, restock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        //Finding layout widgets by id and connecting them to the activity_manager.xml
        history = (Button) findViewById(R.id.history_btn);
        restock = (Button) findViewById(R.id.restock_btn);

        //Receiving data from "MainActivity" activity
        Intent fromMain = getIntent();
        if(!(this.getIntent().getExtras().getParcelableArrayList("listOfHistory")==null)){

            mAHistoryList = this.getIntent().getExtras().getParcelableArrayList("listOfHistory");
            System.out.println("My ManagerActivity History is: \n"+ mAHistoryList);}
        else if(!(this.getIntent().getExtras().getParcelableArrayList("ListOfProd")==null)){
            mAProdctList = this.getIntent().getExtras().getParcelableArrayList("ListOfProd");
            System.out.println("My ManagerActivity ListOfProd is: \n"+ mAProdctList.toString());} }


    //Required actions when the "ManagerActivity" buttons are clicked
    public void btnClicked(View v){
        if (v.equals(history)) {
            mAHistoryListdup =  mAHistoryList;
            System.out.println("My duplicate history is:"+mAHistoryListdup);

            //Sending history data to the "HistoryReportActivity" activity
            Intent toHistoryReportActivity = new Intent(this, HistoryReportActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("listOfHistory",mAHistoryListdup);
            toHistoryReportActivity.putExtras(bundle);
            startActivity(toHistoryReportActivity);
            System.out.println("My toHistoryReportActivity History is: \n"+ mAHistoryListdup);
            Toast.makeText(this, "History clicked", Toast.LENGTH_SHORT).show();}

        else if (v.equals(restock)) {
            //Sending product list data to the "HistoryReportActivity" activity
            Intent toRestockActivity = new Intent(this, RestockActivity.class);
            startActivity(toRestockActivity);
            Toast.makeText(this, "Restock clicked", Toast.LENGTH_SHORT).show();
        }
    }
}