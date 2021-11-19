package com.example.cashregisterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cashregisterapp.Model.MyApp;
import com.example.cashregisterapp.Model.Product;

import java.util.ArrayList;

public class RestockActivity extends AppCompatActivity {

    ArrayList<Product> newlistOfProd = new ArrayList<>();
    ListViewAdapter restockCustomAdapter;
    EditText qntInput;
    Button ok, cancel;
    ListView productList;

    int selectedIndex;
    int userQnt;
    Product selectedProd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);
        newlistOfProd = ((MyApp) getApplication()).getManager().getListOfProd();

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

    public void btnClicked(View v) {
        if (v==cancel){
            finish();
            Toast.makeText(this,"No changes was made \n to the list of products",Toast.LENGTH_SHORT).show();
        }
        else {
            //qntInput.setText("");
            userQnt = Integer.parseInt(qntInput.getText().toString());
            selectedProd.setProdQnt(selectedProd.getProdQnt() +userQnt) ;
            restockCustomAdapter.notifyDataSetChanged();
            Toast.makeText(this,"The new quantity of " + selectedProd.getProdName()+" \n is "+ selectedProd.getProdQnt(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList("allToDos",allTodos);

        //outState.putParcelableArrayList("allproductList",newlistOfProd);
    }

}