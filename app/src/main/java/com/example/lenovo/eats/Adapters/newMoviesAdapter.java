package com.example.lenovo.eats.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.lenovo.eats.Activities.OrderItems;
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


public class newMoviesAdapter extends RecyclerView.Adapter<newMoviesAdapter.MyViewHolder> implements Filterable
{

    private DatabaseReference mRootRef;
    private Context context;
    private List<Movie> contactList;
    private List<Movie> contactListFiltered;
    private MoviesAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone, bill, status;
        public ImageView thumbnail;
        public Button submit,special_order;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.title);
            phone = view.findViewById(R.id.genre);
            bill = view.findViewById(R.id.bill);
            status = view.findViewById(R.id.status);
            special_order = view.findViewById(R.id.special);
            //thumbnail = view.findViewById(R.id.thumbnail);
            submit = view.findViewById(R.id.Answer);
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


    public newMoviesAdapter(Context context, List<Movie> contactList, MoviesAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Movie contact = contactListFiltered.get(position);
        holder.name.setText("Order : " + contact.getTitle());
        holder.phone.setText("Table : " + contact.getGenre());
        holder.bill.setText("Bill : " + contact.getBill());
        holder.status.setText("Status : "+ contact.getStatus());


        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                changeBillStatus(position);
                contact.setStatus("1");
                notifyDataSetChanged();
                //Toast.makeText(context, "On Click Called", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        holder.special_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               Intent intent = new Intent(context,OrderItems.class);
               intent.putExtra("orderId",contact.getTitle());
                intent.putExtra("type","customer");
               context.startActivity(intent);
                //Toast.makeText(context, "On Click Called", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });


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


    private void changeBillStatus(int position) {

        HashMap<String, Object> result = new HashMap<>();

        result.put("bill_status", 1);
        final Movie contact = contactListFiltered.get(position);
        String key = contact.getOrderKey();
        //holder.name.setText("Table : " + contact.getTitle());
        //FirebaseDatabase.getInstance().getReference()
        mRootRef.child("Order").child(key).updateChildren(result);
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

