package com.example.lenovo.eats.Fragments;


import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.eats.Activities.HallLoginActivity;
import com.example.lenovo.eats.R;


import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;



import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


import com.example.lenovo.eats.Activities.hallManagerOrderBill;
import com.example.lenovo.eats.Adapters.newMoviesAdapter2;
import com.example.lenovo.eats.ClassModel.Movie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;




import android.*;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suleman on 12/31/2017.
 */

public class HallAssignTablets extends Fragment implements newMoviesAdapter2.MoviesAdapterListener{


    private DatabaseReference mRootRef;
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private newMoviesAdapter2 moAdapter;
    FloatingActionButton myFab;
    private String queryy = "";
    private SearchView searchView;
    EditText titleET;
    EditText genreET;
    Button ask, submit;

    List tablet_nos = new ArrayList();
    List first_tablet = new ArrayList();


    public HallAssignTablets(){
        mRootRef  = FirebaseDatabase.getInstance().getReference().getRoot();
    }
    public void onCreate(Bundle a){
        super.onCreate(a);
        setHasOptionsMenu(true);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // suleman
        //mRootRef=FirebaseDatabase.getInstance().getReference();
        View view = inflater.inflate(R.layout.fragment_assign, null, false);

        genreET = (EditText) view.findViewById(R.id.rating_rating_bar);
        titleET = (EditText) view.findViewById(R.id.etTitle);
        //ask = (Button) view.findViewById(R.id.ask);
        submit = (Button) view.findViewById(R.id.button7);
        final LinearLayout askingLayout  = (LinearLayout) view.findViewById(R.id.confirm);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!moAdapter.isClashWithTablets())
                {

                    moAdapter.setTabletsAndTables();
                    /*Intent intent = new Intent(getActivity(), HallassignTablesWaiters.class);
                    startActivity(intent);
                    getActivity().finish();*/

                    Intent intent = new Intent(getActivity(), hallManagerOrderBill.class);
                    startActivity(intent);
                    getActivity().finish();


                }
                else
                {
                    Toast.makeText(getContext(), "Clashes between tables and tablets!", Toast.LENGTH_SHORT).show();
                }





                /*askingLayout.setVisibility(View.INVISIBLE);

                String titleString = titleET.getText().toString()+"";
                String genreString = genreET.getText().toString()+"";
                titleET.setText("");
                genreET.setText("");


                if(!(titleString.equals("") || genreString.equals("")))
                {
                    //

                    String curr_user_ref = "posts";
                    //String chat_user_ref = "messages/" + chatUser.getChatId() + "/" + user;

                    DatabaseReference user_message_push = mRootRef.child("posts").push();

                    String push_id = user_message_push.getKey();
                    // curr=df.format(c.getTime());

                    Map messageMap = new HashMap();
                    messageMap.put("title", titleString);
                    messageMap.put("genre", genreString);


                    Map messageUserMap = new HashMap();
                    messageUserMap.put(curr_user_ref + "/" + push_id, messageMap);


                    mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Log.d("Chat_log", databaseError.getMessage().toString());
                            }
                        }
                    });
                }*/
            }
        });


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        //RecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        moAdapter = new newMoviesAdapter2(getActivity(),movieList, this);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(moAdapter);


//        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        /*mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new HallContactAdapter(getContext(),data,this);
        mRecyclerView.setAdapter (mAdapter);
*/



        myFab = (FloatingActionButton) view.findViewById(R.id.fab);
        myFab.setVisibility(View.INVISIBLE);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), HallLoginActivity.class));
                getActivity().finish();
            }
        });


        //
        loadMessage();

        return view;
    }






    private void toggleSelection(int position) {
        //mAdapter.toggleSelection (position);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main, menu);


    }

    public boolean onOptionsItemSelected(MenuItem item){
        /*if(item.getItemId() == R.id.add_contact){
            Intent intent = new Intent("com.example.awais.test.SetupPhoneNumber");
            startActivity(intent);
        }*/

        return super.onOptionsItemSelected(item);
    }









    private void loadMessage() {

        //Log.e("ID",""+chatUser.getChatId());
        // mRootRef.child("messages").child("qUaq5cLEBCRFik5dtnfN6qaQdCo1").keepSynced(true);

        FirebaseDatabase.getInstance().getReference("Tablet").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //HallMovie message= new HallMovie();
                //message =    (ChatData)(dataSnapshot.getValue(ChatData.class));

                //ChatData newmessage = ChatData();
                //dataSnapshot.child("message").getValue().toString();

                //Log.e("text1",dataSnapshot.child("message").getValue().toString()+"");
                //Log.e("text1",message.getText()+"?");
                //message.setTitle(dataSnapshot.child("table_no").getValue().toString()+"");
                tablet_nos.add(dataSnapshot.child("tablet_no").getValue().toString());
                first_tablet.add(tablet_nos.get(0));


//                movieList.add(message);
                // Toast.makeText(getBaseContext(),""+message.getText(),Toast.LENGTH_SHORT).show();
                moAdapter.setTablets(tablet_nos);
                moAdapter.setFirstTablets(first_tablet);
                moAdapter.notifyDataSetChanged();
                // Toast.makeText(getBaseContext(),"Gud",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        FirebaseDatabase.getInstance().getReference("DiningTable").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Movie message= new Movie();
                //message =    (ChatData)(dataSnapshot.getValue(ChatData.class));

                //ChatData newmessage = ChatData();
                //dataSnapshot.child("message").getValue().toString();

                //Log.e("text1",dataSnapshot.child("message").getValue().toString()+"");
                //Log.e("text1",message.getText()+"?");
                message.setTitle(dataSnapshot.child("table_no").getValue().toString()+"");
                //message.setGenre(dataSnapshot.child("Table").getValue().toString()+"");
                //message.setStatus(dataSnapshot.child("status").getValue().toString()+"");
                //message.setBill(dataSnapshot.child("Total").getValue().toString()+"");
                message.setOrderKey(dataSnapshot.getKey());


                //message.setYear("2000");

                //message.setTime(dataSnapshot.child("time").getValue().toString()+"");
                //message.setType(dataSnapshot.child("type").getValue().toString()+"");

                movieList.add(message);
                // Toast.makeText(getBaseContext(),""+message.getText(),Toast.LENGTH_SHORT).show();

                moAdapter.notifyDataSetChanged();
                // Toast.makeText(getBaseContext(),"Gud",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        /*
        mRootRef.child("posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               HallMovie message= new HallMovie();
                //message =    (ChatData)(dataSnapshot.getValue(ChatData.class));

                //ChatData newmessage = ChatData();
                //dataSnapshot.child("message").getValue().toString();

                //Log.e("text1",dataSnapshot.child("message").getValue().toString()+"");
                //Log.e("text1",message.getText()+"?");
                message.setTitle(dataSnapshot.child("title").getValue().toString()+"");
                message.setGenre(dataSnapshot.child("genre").getValue().toString()+"");
                message.setYear("2000");

                //message.setTime(dataSnapshot.child("time").getValue().toString()+"");
                //message.setType(dataSnapshot.child("type").getValue().toString()+"");

                movieList.add(message);
                // Toast.makeText(getBaseContext(),""+message.getText(),Toast.LENGTH_SHORT).show();

                //moAdapter.notifyDataSetChanged();
                // Toast.makeText(getBaseContext(),"Gud",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
               HallMovie message=   new HallMovie();// dataSnapshot.getValue(ChatData.class);

                message.setTitle(dataSnapshot.child("title").getValue().toString()+"");
                message.setGenre(dataSnapshot.child("genre").getValue().toString()+"");

                //message.setTime(dataSnapshot.child("time").getValue().toString()+"");
                //message.setType(dataSnapshot.child("type").getValue().toString()+"");
                message.setYear("2000");
                //Log.e("text2",message.getText()+"");
                movieList.add(message);
                // Toast.makeText(getBaseContext(),""+message.getText(),Toast.LENGTH_SHORT).show();

//                moAdapter.notifyDataSetChanged();
                // Toast.makeText(getBaseContext(),"Gud",Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        //FirebaseDatabase.getInstance().getReference().keepSynced(true);
    }





    @Override
    public void onContactSelected(Movie contact) {

    }
}
