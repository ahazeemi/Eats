package com.example.lenovo.eats.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.lenovo.eats.ClassModel.PiedPiperMenuEntry;
import com.example.lenovo.eats.R;;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PiedPiperAddMenuItemActivity extends AppCompatActivity {

    EditText et_name_dish;
    EditText et_prep_time;
    EditText et_sales_price;

    EditText et_ing_1;
    EditText et_ing_2;
    EditText et_ing_3;
    EditText et_ing_4;
    EditText et_ing_5;

    Spinner  sp_type;
    Spinner  sp_ing_1;
    Spinner  sp_ing_2;
    Spinner  sp_ing_3;
    Spinner  sp_ing_4;
    Spinner  sp_ing_5;


    Button btn_add;


    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public String TAG = "Test!";


    HashMap<Integer,String> inventory_spinner_map = new HashMap<Integer, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piedpiper_activity_add_menu_item);

        et_name_dish = findViewById( R.id.et_name_dish);
        et_prep_time = findViewById( R.id.et_prep_time);
        et_sales_price = findViewById( R.id.et_sales_price);

        et_ing_1 = findViewById( R.id.et_ing_1 );
        et_ing_2 = findViewById( R.id.et_ing_2 );
        et_ing_3 = findViewById( R.id.et_ing_3 );
        et_ing_4 = findViewById( R.id.et_ing_4 );
        et_ing_5 = findViewById( R.id.et_ing_5 );

        sp_type = findViewById( R.id.sp_type);

        sp_ing_1 = findViewById( R.id.sp_ing_1);
        sp_ing_2 = findViewById( R.id.sp_ing_2);
        sp_ing_3 = findViewById( R.id.sp_ing_3);
        sp_ing_4 = findViewById( R.id.sp_ing_4);
        sp_ing_5 = findViewById( R.id.sp_ing_5);



        btn_add = findViewById( R.id.btn_add );

        initializeTypeSpinner();
        initializeIngredientsSpinners();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, Integer> ingredients;
                boolean should_continue = true;
                try
                {
                    ingredients = readIngredients();
                }
                catch ( Exception e )
                {
                    Toast.makeText(PiedPiperAddMenuItemActivity.this, "Please enter atleast one ingredient!", Toast.LENGTH_LONG).show();

                    should_continue = false;
                }

                if ( should_continue )
                {
                    try
                    {
                        String name = et_name_dish.getText().toString();
                        String prep_time = et_prep_time.getText().toString();
                        float sale_price = Float.parseFloat( et_sales_price.getText().toString() );
                        String type = sp_type.getSelectedItem().toString();


                        if ( name.equals( "" ) || prep_time.equals( "" ) )
                        {
                            throw new Exception();
                        }

                        ingredients = readIngredients();

                        PiedPiperMenuEntry new_item = new PiedPiperMenuEntry();

                        new_item.ingredients = ingredients;
                        new_item.name = name;
                        new_item.preparation_time = prep_time;
                        new_item.sale_price = sale_price;
                        new_item.type = type;

                        final DatabaseReference ref = database.getReference("MenuItem");

//                        ref.push().setValue( new_item );

                        String key = ref.push().getKey();

                        ref.child( key ).setValue( new_item );

                        //key.jpeg

                        Toast.makeText(PiedPiperAddMenuItemActivity.this, "Menu Item added successfully!", Toast.LENGTH_LONG).show();

                        refreshFields();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(PiedPiperAddMenuItemActivity.this, "Check entered data. Error!", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    private void refreshFields() {
        et_name_dish.setText("");
        et_prep_time.setText("");
        et_sales_price.setText("");
        sp_type.setSelection(0);
        sp_ing_1.setSelection(0);
        sp_ing_2.setSelection(0);
        sp_ing_3.setSelection(0);
        sp_ing_4.setSelection(0);
        sp_ing_5.setSelection(0);
    }


    private HashMap<String, Integer> readIngredients() throws Exception
    {
//        ArrayList<String> ingredients = new ArrayList<>();
        HashMap<String, Integer> ingredients = new HashMap<String, Integer>( );
        String ing_1 = sp_ing_1.getSelectedItem().toString();
        if ( ing_1.equals( "None" ) )
        {
            throw new Exception();
        }
        else
        {
            int quant = Integer.parseInt( et_ing_1.getText().toString() );
            ingredients.put( inventory_spinner_map.get( sp_ing_1.getSelectedItemPosition() ), quant );
        }




        String ing_2 = sp_ing_2.getSelectedItem().toString();
        if ( !ing_2.equals( "None" ) )
        {
            int quant = Integer.parseInt( et_ing_2.getText().toString() );
            ingredients.put(  inventory_spinner_map.get( sp_ing_2.getSelectedItemPosition() ), quant );
        }


        String ing_3 = sp_ing_3.getSelectedItem().toString();
        if ( !ing_3.equals( "None" ) )
        {
            int quant = Integer.parseInt( et_ing_3.getText().toString() );
            ingredients.put(inventory_spinner_map.get( sp_ing_3.getSelectedItemPosition() ), quant );
        }


        String ing_4 = sp_ing_4.getSelectedItem().toString();
        if ( ing_4.equals( "None" ) )
        {

            int quant = Integer.parseInt( et_ing_4.getText().toString() );
            ingredients.put(  inventory_spinner_map.get( sp_ing_4.getSelectedItemPosition() ), quant );
        }


        String ing_5 = sp_ing_5.getSelectedItem().toString();
        if ( !ing_5.equals( "None" ) )
        {
            int quant = Integer.parseInt( et_ing_5.getText().toString() );
            ingredients.put(  inventory_spinner_map.get( sp_ing_5.getSelectedItemPosition() ), quant );
        }


        return ingredients;
    }

    private void initializeIngredientsSpinners()
    {
        final List<String> inventory_item_names = new ArrayList<>();
        final DatabaseReference ref = database.getReference("Inventory");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override


            public void onDataChange(DataSnapshot dataSnapshot) {
                inventory_item_names.add( "None" );

                int i = 1;
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    String key = data.getKey().toString();
                    String name = data.child( "name" ).getValue().toString();
                    inventory_item_names.add( name );
                    inventory_spinner_map.put( i, key );

                    i++;
                }

                ArrayAdapter<String> ingredients_adapter = new ArrayAdapter<String>(PiedPiperAddMenuItemActivity.this, android.R.layout.simple_spinner_item, inventory_item_names);
                ingredients_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_ing_1.setAdapter(ingredients_adapter);
                sp_ing_2.setAdapter(ingredients_adapter);
                sp_ing_3.setAdapter(ingredients_adapter);
                sp_ing_4.setAdapter(ingredients_adapter);
                sp_ing_5.setAdapter(ingredients_adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void initializeTypeSpinner()
    {
        final List<String> types = new ArrayList<>();
        types.add( "Main Course" );
        types.add( "Chinese Course" );
        types.add( "Turkish Course" );

        ArrayAdapter<String> types_adapter = new ArrayAdapter<String>(PiedPiperAddMenuItemActivity.this, android.R.layout.simple_spinner_item, types);
        types_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_type.setAdapter(types_adapter);
    }
}
