package com.example.lenovo.eats.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.lenovo.eats.ClassModel.Movie;
import com.example.lenovo.eats.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Suleman on 1/6/2018.
 */


public class newMoviesAdapter1 extends RecyclerView.Adapter<newMoviesAdapter1.MyViewHolder> implements Filterable
{


    private DatabaseReference mRootRef;


    private Context context;
    private List<Movie> contactList;
    private List<Movie> contactListFiltered;
    private MoviesAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone, sc;
        public ImageView thumbnail;
        public Spinner spnStatus;

        public Button submit;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.title);
            phone = view.findViewById(R.id.genre);
            spnStatus = view.findViewById(R.id.Answer);
            submit = view.findViewById(R.id.btnChangeTableStatus);
            sc= view.findViewById(R.id.SC);
            mRootRef  = FirebaseDatabase.getInstance().getReference().getRoot();
            //thumbnail = view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public newMoviesAdapter1(Context context, List<Movie> contactList, MoviesAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row1, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Movie contact = contactListFiltered.get(position);
        holder.name.setText("Table : " + contact.getTitle());
        int status = Integer.parseInt(contact.getGenre());
        holder.sc.setText("Seating Capacity: " + contact.getCapacity());

    //    if()
        if(status == 0)
            holder.phone.setText("Status : Free");
        else  if (status == 1)
            holder.phone.setText("Status : Occupied");
        else if(status == 2)
            holder.phone.setText("Status : Booked");
        else
            holder.phone.setText("Status : Unknown");


        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String choice = holder.spnStatus.getSelectedItem().toString();
                int choiceInt = 0;
                if(choice.equals("Free"))
                    choiceInt = 0;
                else if(choice.equals("Occupied"))
                    choiceInt = 1;
                else if(choice.equals("Booked"))
                    choiceInt = 2;
                changeStatus(choiceInt, position);
                contactListFiltered.get(position).setGenre(Integer.toString(choiceInt));

                notifyDataSetChanged();
                //Toast.makeText(context, "On Click Called", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });


        //holder.btnStatus.setText("Change Status");
        //Glide.with(context)
        //      .load(contact.getImage())
        //    .apply(RequestOptions.circleCropTransform())
        //   .into(holder.thumbnail);
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


    private void changeStatus(int c, int position) {

        HashMap<String, Object> result = new HashMap<>();

        result.put("status", c);
        final Movie contact = contactListFiltered.get(position);
        String key = contact.getKey();
        //holder.name.setText("Table : " + contact.getTitle());
        //FirebaseDatabase.getInstance().getReference()
               mRootRef.child("DiningTable").child(key).updateChildren(result);
        //Toast.makeText(context, contact.getKey(), Toast.LENGTH_SHORT).show();



    /*    //FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //final DatabaseReference reference = firebaseDatabase.getReference();
        Query query = mRootRef.child("hall").child("tables").orderByChild("id").equalTo("1");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                String path = "/" + dataSnapshot.getKey() + "/" + key;
                HashMap<String, Object> result = new HashMap<>();
                result.put("Status", "2");
                mRootRef.child(path).updateChildren(result);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Logger.error(TAG, ">>> Error:" + "find onCancelled:" + databaseError);

            }
        });
*/


        /*String curr_user_ref = "test/";
        //String chat_user_ref = "messages/" + chatUser.getChatId() + "/" + user;

        DatabaseReference user_message_push = mRootRef.child("hall").child("hall")
                .push();

        String push_id = user_message_push.getKey();
        // curr=df.format(c.getTime());

        Map messageMap = new HashMap();
        messageMap.put("message", "test");
        messageMap.put("type", "text");
        messageMap.put("time", "5:00");
        messageMap.put("from", "mystery");


        Map messageUserMap = new HashMap();
        messageUserMap.put(curr_user_ref + "/" + push_id, messageMap);
        //messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);
        //text.setText("");


        mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.d("Chat_log", databaseError.getMessage().toString());
                }
            }
        });*/


               /* Map chatInfoMap = new HashMap();
                chatInfoMap.put("name", chatUser.getName());
                chatInfoMap.put("time", "5:00");
                chatInfoMap.put("lastmessage", message);

                Map chatUserMap = new HashMap();
                chatUserMap.put("ChatTab/" + user + "/" + chatUser.getChatId(), chatInfoMap);
                chatUserMap.put("ChatTab/" + chatUser.getChatId() + "/" + user, chatInfoMap);

                mRootRef.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Log.d("Chat_log", databaseError.getMessage().toString());
                        }
                    }
                });*/

        // mRootRef.child("ChatTab").child("qUaq5cLEBCRFik5dtnfN6qaQdCo1").keepSynced(true);
//kaam na kre to ise chala k dekhna
                /*
            mRootRef.child("ChatTab").child(user).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
*/

        //}
        //}
    }



}

