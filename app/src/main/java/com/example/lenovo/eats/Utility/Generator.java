package com.example.lenovo.eats.Utility;

/**
 * Created by Sheri on 03-Dec-18.
 */
import com.example.lenovo.eats.Activities.DineinChefInterface;
import com.example.lenovo.eats.ClassModel.Dine_In_orderProxy;
import com.example.lenovo.eats.ClassModel.MenuItemView;
import com.example.lenovo.eats.ClassModel.OrderMenuItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.support.annotation.NonNull;

import  java.util.*;
public class Generator {
private  static Generator inst;
public
        static Generator getInstance()
{if (inst==null)
{
    inst=new Generator();
    inst.getOrders();

}
    return inst;
}

    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

    static String names[]={
            "Mutton",
            "Korma",
            "Karahi",
            "Champ",
            "Platter",
            "Mango Shake",
            "Bryani",
            "Shami Chawal",
            "Siri Paye",
            "Nehari"
    };
    static String time[]={
            "20",
            "10",
            "25",
            "35",
            "30",
            "5",
            "15",
            "10",
            "10",
            "12"
    };
  static List<Dine_In_orderProxy> getOrder()
  {
      int count=1;
List<Dine_In_orderProxy> ret=new ArrayList<>();
      for (int i=0;i<count;i++)
      {int items=(int) (Math.random()*4)+1;

      List<String> itemL=new ArrayList<>();

      for (int j=0;j<items;j++)

          {
              int random=(int)(Math.random()*9);
              String item=names[random]+","+(i+j)+","+time[random]+","+i;
              itemL.add(item);
          }
          ret.add(new Dine_In_orderProxy(""+i,itemL));
      }

    return  ret;

  }
    void getOrders()
    {
        DatabaseReference databaseReference=firebaseDatabase.getReference("Order");
        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            Dine_In_orderProxy order=    update     (dataSnapshot.getKey());

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


    Dine_In_orderProxy update(final String orderId)
    {
        final Dine_In_orderProxy order=new Dine_In_orderProxy();
        order.orderID=orderId;

DatabaseReference numItems=firebaseDatabase.getReference("Order/"+orderId+"/number_of_items");
numItems.addValueEventListener(new ValueEventListener() {
    @Override// NUMBER OF ITEMS
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
   long number=(long)dataSnapshot.getValue();

  final int count=(int) number;
   DatabaseReference databaseReference=firebaseDatabase.getReference("Order/"+orderId+"/order_items");
        databaseReference.addChildEventListener(new ChildEventListener() {
int obtained=0;
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                   FetchMenuItem(dataSnapshot.getKey(),
                           dataSnapshot.getValue(OrderMenuItem.class),order,count);



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
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

   return order;
    }

    void FetchMenuItem(String key, final OrderMenuItem item, final Dine_In_orderProxy order, final int count)
    {
        final int quantity = item.getQuantity();
        DatabaseReference databaseReference=firebaseDatabase.getReference("MenuItem");
        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MenuItemView menuItem = dataSnapshot
                        .getValue(MenuItemView.class);
                if(menuItem!=null) {
                    menuItem.setMenuItemId(dataSnapshot.getKey());
                    menuItem.setQuantityOrdered(quantity);

                    String item = menuItem.getName() + " " + quantity + ","
                            + menuItem.getMenuItemId()
                            + ","
                            + (int) (Math.random() * 10 + 5)
                            + ","
                            + order.orderID;
                    order.items = new ArrayList<>();
                    order.items.add(item);
                    if (order.items.size() == count) {
                        DineinChefInterface.Instance.waitingOrders.add(order);
                        DineinChefInterface.Instance.backStack.addAll(order.items);
                        DineinChefInterface.Instance.getMore();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
