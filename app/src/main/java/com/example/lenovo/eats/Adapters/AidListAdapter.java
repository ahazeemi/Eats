package com.example.lenovo.eats.Adapters;

/**
 * Created by Sheri on 22-Nov-18.
 */

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.eats.Activities.DineinChefInterface;
import com.example.lenovo.eats.R;
import com.example.lenovo.eats.Utility.AidDragListener;
import com.example.lenovo.eats.Utility.itemFactory;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import android.content.*;
@SuppressLint("NewApi")
public class AidListAdapter
        extends RecyclerView.Adapter<AidListAdapter.ListViewHolder>
        implements View.OnClickListener,
                   View.OnLongClickListener
{

    private List<String> list;
    private boolean suppress;
    private  int queueid;
    public View.OnClickListener onClickListener;
    public AidListAdapter(List<String> list) {
        this.list = list;
    }
    public AidListAdapter(List<String> list,int position) {
        this.list = list;
        queueid=position;

    }

    public AidListAdapter(List<String> list,boolean suppress) {
        this.list = list;
        this.suppress=suppress;
        for (int i=0;i<20;i++)
        {
            DineinChefInterface.queues[i].setOnDragListener(new AidDragListener());
        }
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType)
    {   View view = LayoutInflater.from(parent.getContext())
                    .inflate(viewType, parent, false);
        return new ListViewHolder(view);}
    @Override
    public int getItemViewType(int position) {
        if (position==0 && !suppress) return R.layout.list_head;
        else return R.layout.list_row;}
    @Override
    public void onBindViewHolder(ListViewHolder holder, int position)
    {   holder.text.setText(list.get(position).split(",")[0]);
        holder.frameLayout.setTag(position);
        if (onClickListener!=null)
            holder.frameLayout.setOnClickListener(onClickListener);
        else
            holder.frameLayout.setOnClickListener(this);
        if (!suppress) {
            holder.frameLayout.setOnLongClickListener(this);

            holder.frameLayout.setOnDragListener(new AidDragListener());
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<String> getList() {
        return list;
    }

    public void updateList(List<String> list) {
        this.list = list;
    }

    @Override
    public boolean onLongClick(View v) {
        if ((int)v.getTag()!=0)         //Dont move the head of Queue
                {ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View
                                                  .DragShadowBuilder(v);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, shadowBuilder, v, 0);}
                else {v.startDrag(data, shadowBuilder, v, 0);}
                return true;}
        else
                return false;
    }

    @Override
    public void onClick(final View view) {
        if ((int)view.getTag()>0) {  // Make Head of Queue Non Clickable
            DialogInterface.OnClickListener dialogClickListener = new               DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_NEUTRAL:
                            int index1=(int)view.getTag();
                            Intent  Eats=new Intent("Eats action");
                            String item1=DineinChefInterface.data.get(queueid).get(index1);
                            itemFactory it1=new itemFactory
                                    (item1);
                            Eats
                                    .putExtra("orderID"
                                            ,it1.getOrderID());
                            Eats
                                    .putExtra
                                            ("type",
                                                    "chef");
                            DineinChefInterface.Instance
                                    .startActivityForResult
                                            (Eats,11);
                            break;
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            int index=(int)view.getTag();
                            String item=DineinChefInterface.data.get(queueid).get(index);
                            if (DineinChefInterface.Instance.completed(item))
                            {
                                itemFactory ifact=new itemFactory(item);
                                Toast.makeText(DineinChefInterface.Instance, "Completed!", Toast.LENGTH_SHORT).show();
                                DineinChefInterface.delieveredCount++;
                                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                FirebaseDatabase.getInstance().getReference("Order/"+ifact.getOrderID()+"/order_completed").setValue(true);
                            }

                            DineinChefInterface.data.get(queueid).remove(index);
                            DineinChefInterface.queues[queueid]
                                    .getAdapter()
                                    .notifyDataSetChanged();
                            DineinChefInterface.Instance.getMore();

                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;

                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder
                                                (DineinChefInterface.THIS);
            builder.setTitle(list.get((int)view.getTag()))
                    .setMessage("Item prepared?")
                    .setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener)
                    .setNeutralButton("Report Issue",dialogClickListener)
                    .show();
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        FrameLayout frameLayout;

        ListViewHolder(View itemView) {
            super(itemView);
            //DON'T CHANGE HERE, MAKE CHANGE TO NEW LAYOUTS
            text=itemView.findViewById(R.id.text);
            frameLayout=itemView.findViewById(R.id.frame_layout_item);}
    }
}