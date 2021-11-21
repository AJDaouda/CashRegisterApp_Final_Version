package Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cashregisterapp.Model.MyApp;
import com.example.cashregisterapp.Model.Product;
import com.example.cashregisterapp.R;

import java.util.ArrayList;

public class RestockActivity extends AppCompatActivity {

    //External class objects declaration
    ArrayList<Product> newlistOfProd = new ArrayList<>();

    ListViewAdapter restockCustomAdapter;

    //Layout Widgets declaration
    EditText qntInput;
    Button ok, cancel;
    ListView productList;

    //Instance variables declaration
    int selectedIndex;
    int userQnt;
    Product selectedProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);

        newlistOfProd = ((MyApp) getApplication()).getManager().getListOfProd();

        //Finding layout widgets by id and connecting them to the activity_restock.xml
        qntInput = (EditText) findViewById(R.id.editTextNumber);
        ok = (Button) findViewById(R.id.OK_Btn);
        cancel = (Button) findViewById(R.id.Restock_Cancel_Btn);
        productList = (ListView) findViewById(R.id.Restock_ListView);

        restockCustomAdapter = new ListViewAdapter(newlistOfProd,this);
        productList.setAdapter(restockCustomAdapter);

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex = position;
                selectedProd = newlistOfProd.get(selectedIndex);
            }
        });
    }

    //Required actions when the "RestockActivity" buttons are clicked
    public void btnClicked(View v) {
        if (v==cancel){
            Intent toMainActvity = new Intent(this,MainActivity.class);
            startActivity(toMainActvity);
            finish();
            Toast.makeText(this,"No changes was made \n to the list of products",Toast.LENGTH_SHORT).show();
        }
        else {
            if(qntInput.getText().toString().isEmpty()){
                Toast.makeText(this,"All fields are required",Toast.LENGTH_SHORT).show(); }
            else {
            //qntInput.setText("");
            userQnt = Integer.parseInt(qntInput.getText().toString());
            selectedProd.setProdQnt(selectedProd.getProdQnt() +userQnt) ;
            restockCustomAdapter.notifyDataSetChanged();
            Toast.makeText(this,"The new quantity of " + selectedProd.getProdName()+" \n is "+ selectedProd.getProdQnt(),Toast.LENGTH_SHORT).show();}
            qntInput.setText("");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState); }
}