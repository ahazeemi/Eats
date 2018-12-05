package com.example.lenovo.eats.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.lenovo.eats.ClassModel.Movie;
import com.example.lenovo.eats.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Suleman on 1/6/2018.
 */


public class newMoviesAdapter3 extends RecyclerView.Adapter<newMoviesAdapter3.MyViewHolder> implements Filterable
{

    private DatabaseReference mRootRef;
    private Context context;
    private List<Movie> contactList;
    private List<Movie> contactListFiltered;
    private List tablets;
    private List firsts;
    private MoviesAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone, bill, status;
        public ImageView thumbnail;
        public Button submit;
        public Spinner spnStatus;
        ArrayAdapter<String> adapter;// = new ArrayAdapter<String>(context,
                //android.R.layout.simple_spinner_item, tablets);

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);




        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.title);
            spnStatus = view.findViewById(R.id.Answer);

            adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, tablets);


            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnStatus.setAdapter(adapter);


            spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    firsts.set( getAdapterPosition() ,spnStatus.getSelectedItem().toString());

                    // your code here
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });


            mRootRef  = FirebaseDatabase.getInstance().getReference().getRoot();

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public newMoviesAdapter3(Context context, List<Movie> contactList, MoviesAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.moview_list_row2, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Movie contact = contactListFiltered.get(position);
        holder.name.setText("Table : " + contact.getTitle());


    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<Movie> filteredList = new ArrayList<>();
                    for (Movie row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getGenre().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<Movie>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface MoviesAdapterListener {
        void onContactSelected(Movie contact);
    }

    public void setTablets(List tabletsPassed)
    {
        List new1 = new ArrayList();
        new1.add("1");


        tablets = new1;
        //tablets = tabletsPassed;
    }

    public void setFirstTablets(List s)
    {
        firsts = s;
    }
    private void changeBillStatus(int position) {

        HashMap<String, Object> result = new HashMap<>();

        result.put("status", 1);
        final Movie contact = contactListFiltered.get(position);
        String key = contact.getOrderKey();
        //holder.name.setText("Table : " + contact.getTitle());
        //FirebaseDatabase.getInstance().getReference()
        mRootRef.child("hall").child("orders").child(key).updateChildren(result);
        //Toast.makeText(context, contact.getKey(), Toast.LENGTH_SHORT).show();


    }


    public boolean isClashWithTablets()
    {

        for(int i=0; i<firsts.size() ;i++)
        {
            for(int j=0; j<firsts.size() ;j++)
            {
                if(i != j && firsts.get(i) == firsts.get(j))
                    return true;
            }
        }
        return false;
    }


    public void setTabletsAndTables()
    {


        for(int j=0; j<firsts.size() ;j++)
        {
            HashMap<String, Object> result1 = new HashMap<>();
            result1.put("assigned_table", firsts.get(j));
            mRootRef.child("Employee").child(contactListFiltered.get(j).getOrderKey()).updateChildren(result1);



            //title
        }


    }




}


