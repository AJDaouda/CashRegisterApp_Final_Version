package Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cashregisterapp.Model.MyApp;
import com.example.cashregisterapp.Model.PurchaseHistory;
import com.example.cashregisterapp.R;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    /* Objects created  before creating the manager app at the application level
    //static StoreManager mngObj = new StoreManager(); //To access the "StoreManager" class
    //static PurchaseHistory historyMngObj = new PurchaseHistory(); //To access the "PurchaseHistory" class
    */

    //External class objects declaration
    ListViewAdapter customAdapter;//To access the "ListViewAdapter" class

    //Creating a "PurchaseHistory" object
    PurchaseHistory history;

    //Creating an ArrayList to get the list of purchase histories as soon as the "buy" button is clicked
    ArrayList<PurchaseHistory> Historylist = new ArrayList<>();

    //Instance variables declaration
    String qntStr = "";//String to be displayed when the client enter a quantity for a specific item
    int userQnt; // User selected quantity
    int newProdQnt; // Updated product quantity after a purchase is made
    int selectedIndex; //Selected index in the listView
    double total; // total = price*quantity
    double nullTotal=0;

    //Layout Widgets declaration
    Button one, two, three, four, five, six, seven, eight, nine, zero;// digit pad
    Button clear, buy;//clear button
    TextView itemQntTV; //Displays client's quantity
    TextView purchasePrice; //Displays the total price of a purchase
    TextView itemSelectedTV;//Displays the selected item from the list of products
    ListView catalogue;//Displays the list of products that the store holds
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Finding layout widgets by id and connecting them to the activity_main.xml
        zero = (Button) findViewById(R.id.btn_0);
        one = (Button) findViewById(R.id.btn_1);
        two = (Button) findViewById(R.id.btn_2);
        three = (Button) findViewById(R.id.btn_3);
        four = (Button) findViewById(R.id.btn_4);
        five = (Button) findViewById(R.id.btn_5);
        six = (Button) findViewById(R.id.btn_6);
        seven = (Button) findViewById(R.id.btn_7);
        eight = (Button) findViewById(R.id.btn_8);
        nine = (Button) findViewById(R.id.btn_9);

        clear = (Button) findViewById(R.id.btn_Clear);
        buy = (Button) findViewById(R.id.BuyBtn);
        itemQntTV = (TextView) findViewById(R.id.qnt);
        purchasePrice= (TextView) findViewById(R.id.cost);
        itemSelectedTV = (TextView) findViewById(R.id.selectedprod);
        catalogue = (ListView) findViewById(R.id.listViewid);

        // Instantiating the "customAdapter" created above
        //customAdapter = new ListViewAdapter(mngObj.getListOfProd(),this);
        customAdapter = new ListViewAdapter(((MyApp) getApplication()).getManager().getListOfProd(),this);
        catalogue.setAdapter(customAdapter);// Setting "customAdapter" as the adapter to be used by the "catalogue" listview

        //Instantiating the "builder" created above
        builder = new AlertDialog.Builder(this);

        catalogue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex = position;
                itemSelectedTV.setText(((MyApp) getApplication()).getManager().getListOfProd().get(selectedIndex).getProdName());

            }
        });
    }

    //Calculates the amount due for a purchase
    private double calculateTotal(){
        userQnt = Integer.parseInt(itemQntTV.getText().toString());
        double price = ((MyApp) getApplication()).getManager().getListOfProd().get(selectedIndex).getProdPrice();
        total = userQnt*price;
        purchasePrice.setText(String.format("$%,.2f", total));
        System.out.println("Item:" + ((MyApp) getApplication()).getManager().getListOfProd().get(selectedIndex).getProdName()+
                "\n" + "Qnt: " + userQnt +
                "\n" + "Total: $ "+ total);
        return total; }

    //Decrease the inventory for each item when a purchase is completed
    private void updateInventoryQnt(){
        newProdQnt = ((MyApp) getApplication()).getManager().getListOfProd().get(selectedIndex).getProdQnt()-userQnt;
        ((MyApp) getApplication()).getManager().getListOfProd().get(selectedIndex).setProdQnt(newProdQnt);
        System.out.println(((MyApp) getApplication()).getManager().getListOfProd().get(selectedIndex).getProdQnt());
        System.out.println(newProdQnt); }

    //Increase the inventory for each item when a purchase is completed
    //public void cancelOrder(){}

    //Create a history for each sale
   private void createHistory(){
       history = new PurchaseHistory(((MyApp) getApplication()).getManager().getListOfProd().get(selectedIndex).getProdName(),
               userQnt, total,(new Date().toString())) ;
       Historylist.add(history);
       }

   //Claering all Textviews
    private void clearUI(){
        itemQntTV.setText(qntStr = "");
        purchasePrice.setText("");
        itemSelectedTV.setText("");
    }

    //Required actions when a button on the digit pad is clicked
    public void btnClicked(View v) {
        //When the client clicks on the "Clear" button
        if (v==clear){
            clearUI();
            System.out.println(itemQntTV.getText().toString()); }
        //When the client clicks on the digit pad
        else{
            String data = ((Button)v).getText().toString();
            qntStr+=data;
            itemQntTV.setText(qntStr);
            calculateTotal();} }

    //Required actions when the "BUY" button is clicked
    public void buyClicked(View v) {
        if (!((itemQntTV.getText().toString().isEmpty())||(itemSelectedTV.getText().toString().isEmpty()))){
            //int userSelectedQnt = Integer.parseInt(itemQntTV.getText().toString());

            if(!(((MyApp) getApplication()).getManager().checkInventory(((MyApp) getApplication()).getManager().getListOfProd().get(selectedIndex),userQnt))){
                Toast.makeText(this,"Not enough quantity in stock!!!",Toast.LENGTH_SHORT).show();
                itemQntTV.setText(qntStr="");
                purchasePrice.setText(String.format("$%,.2f", nullTotal));}
            else{
                newProdQnt = ((MyApp) getApplication()).getManager().getListOfProd().get(selectedIndex).getProdQnt()-userQnt;
                purchasePrice.setText(String.format("$%,.2f", calculateTotal()));
                showAlertBox();
                updateInventoryQnt();
                customAdapter.notifyDataSetChanged();
                clearUI();
                createHistory();
                /*history = new PurchaseHistory(((MyApp) getApplication()).getManager().getListOfProd().get(selectedIndex).getProdName(),
                        userQnt, total,(new Date().toString())) ;
                Historylist.add(history);*/
                System.out.println("My History is: \n"+ Historylist);
                Log.d("new item qnt", String.valueOf(newProdQnt));
            }}
        else {
            Toast.makeText(this,"All fields are required",Toast.LENGTH_SHORT).show(); } }


    //Shows a dialog box whe the shopper purchases an item
    private void showAlertBox(){
        builder.setTitle("Thank you for shopping with us");
        builder.setMessage("Your purchase is:" + "\n" + userQnt + " "+
                ((MyApp) getApplication()).getManager().getListOfProd().get(selectedIndex).getProdName()+ " "+
                "for the total price of "+ String.format("$%,.2f", calculateTotal()));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"OK clicked",Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Cancel clicked",Toast.LENGTH_SHORT).show();
                //Cancel Order
            }
        });
        AlertDialog alertDialog=builder.create();
        builder.show(); }

        //Required actions when the "MANAGER" button is clicked
    public void mngBtnClicked(View v) {
        //Sending data to the "ManagerActivity" activity
        Intent toMngActivity = new Intent(this, ManagerActivity.class);
        Bundle bundle = new Bundle();
        //Bundle bundle1 = new Bundle();
        //Bundle bundle2 = new Bundle();
        //System.out.println("My mainActivity History is: \n"+ Historylist);
        System.out.println("My product list is: \n"+ ((MyApp) getApplication()).getManager().getListOfProd().toString());
        bundle.putParcelableArrayList("listOfHistory",Historylist);
        bundle.putParcelableArrayList("ListOfProd",((MyApp) getApplication()).getManager().getListOfProd());
        //bundle1.putParcelableArrayList("listOfHistory",Historylist);
       // bundle2.putParcelableArrayList("ListOfProd",((MyApp) getApplication()).getManager().getListOfProd());
        toMngActivity.putExtras(bundle);
        //toMngActivity.putExtras(bundle1);
        //toMngActivity.putExtras(bundle2);
        startActivity(toMngActivity);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState); }

}