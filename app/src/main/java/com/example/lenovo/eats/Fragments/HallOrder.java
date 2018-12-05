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


import com.example.lenovo.eats.Activities.HallLoginActivity;
import com.example.lenovo.eats.Adapters.newMoviesAdapter1;
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
import com.example.lenovo.eats.R;

import static java.lang.Integer.parseInt;

/**
 * Created by Suleman on 12/31/2017.
 */

public class HallOrder extends Fragment implements newMoviesAdapter1.MoviesAdapterListener {


    private DatabaseReference mRootRef;
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private newMoviesAdapter1 moAdapter;
    FloatingActionButton myFab;
    private String queryy = "";
    private SearchView searchView;
    EditText titleET;
    EditText genreET;
    Button ask, submit;



    public HallOrder(){
        mRootRef  = FirebaseDatabase.getInstance().getReference().getRoot();
    }
    public void onCreate(Bundle a){
        super.onCreate(a);
        setHasOptionsMenu(true);
    }



    private void prepareMovieData() {
        Movie movie = new Movie("Pain in Elbow", "Doctors, I am having too much pain in my elbow after i fell down.", "12/09/2015");
        movieList.add(movie);

        movie = new Movie("Endless Fever", "Plz tell me what to do ? I am suffering from fever for last 2 months,, plz help", "03/09/2016");
        movieList.add(movie);

        movie = new Movie("Healthy Diet", "Plz suggest some healthy diet plan for an aged person. I am also suffering from diabetes", "03/09/2015");
        movieList.add(movie);

        movie = new Movie("Shaun the Sheep", "Animation", "2015");
        movieList.add(movie);

        movie = new Movie("The Martian", "Science Fiction & Fantasy", "2015");
        movieList.add(movie);

        movie = new Movie("Mission: Impossible Rogue Nation", "Action", "2015");
        movieList.add(movie);

        movie = new Movie("Up", "Animation", "2009");
        movieList.add(movie);

        movie = new Movie("Star Trek", "Science Fiction", "2009");
        movieList.add(movie);

        movie = new Movie("The LEGO HallMovie", "Animation", "2014");
        movieList.add(movie);

        movie = new Movie("Iron Man", "Action & Adventure", "2008");
        movieList.add(movie);

        movie = new Movie("Aliens", "Science Fiction", "1986");
        movieList.add(movie);

        movie = new Movie("Chicken Run", "Animation", "2000");
        movieList.add(movie);

        movie = new Movie("Back to the Future", "Science Fiction", "1985");
        movieList.add(movie);

        movie = new Movie("Raiders of the Lost Ark", "Action & Adventure", "1981");
        movieList.add(movie);

        movie = new Movie("Goldfinger", "Action & Adventure", "1965");
        movieList.add(movie);

        movie = new Movie("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        movieList.add(movie);

        moAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // suleman
        //mRootRef=FirebaseDatabase.getInstance().getReference();
        View view = inflater.inflate(R.layout.fragment_wall_hall, null, false);

        genreET = (EditText) view.findViewById(R.id.rating_rating_bar);
        titleET = (EditText) view.findViewById(R.id.etTitle);
        //ask = (Button) view.findViewById(R.id.ask);
        submit = (Button) view.findViewById(R.id.btn_ok);
        final LinearLayout askingLayout  = (LinearLayout) view.findViewById(R.id.confirm);


        /*ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askingLayout.setVisibility(View.VISIBLE);

            }
        });*/

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askingLayout.setVisibility(View.INVISIBLE);

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
                }
            }
        });


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        //RecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        moAdapter = new newMoviesAdapter1(getActivity(),movieList, this);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(moAdapter);


        myFab = (FloatingActionButton) view.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), HallLoginActivity.class));
                getActivity().finish();
            }
        });

        loadMessage();

        return view;
    }






    private void toggleSelection(int position) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main, menu);

    }

    public boolean onOptionsItemSelected(MenuItem item){


        return super.onOptionsItemSelected(item);
    }


    private void loadMessage() {

        FirebaseDatabase.getInstance().getReference("DiningTable").addChildEventListener(new ChildEventListener() {

            //String parent =

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Movie message= new Movie();
                message.setTitle(dataSnapshot.child("table_no").getValue().toString()+"");
                message.setGenre(dataSnapshot.child("status").getValue().toString()+"");
                message.setYear("2000");
                //long x = (long)dataSnapshot.child("seating_capacity").getValue();

                //int sc = x.intValue();
                message.setCapacity(parseInt(dataSnapshot.child("seating_capacity").getValue().toString()));
                message.setKey(dataSnapshot.getKey());
                movieList.add(message);
                moAdapter.notifyDataSetChanged();

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

    }





    @Override
    public void onContactSelected(Movie contact) {

    }
}
