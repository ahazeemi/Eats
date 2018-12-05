package com.example.lenovo.eats.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.eats.ClassModel.HallContact;
import com.example.lenovo.eats.R;

import java.util.List;


public class HallContactAdapter extends SelectableAdapter<HallContactAdapter.ViewHolder> {

    private List<HallContact> mArrayList;
    private Context mContext;
    private ViewHolder.ClickListener clickListener;
    public HallContactAdapter(Context context, List<HallContact> arrayList, ViewHolder.ClickListener clickListener) {
        this.mArrayList = arrayList;
        this.mContext = context;
        this.clickListener = clickListener;

    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {

        View itemLayoutView;

        itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_contact, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView,clickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.tvName.setText(mArrayList.get(position).getName());
       // viewHolder.tvNumber.setText(mArrayList.get(position).getNumber());
        viewHolder.userPhoto.setImageResource(mArrayList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public HallContact getValue(int position)
    {
        return mArrayList.get(position);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener,View.OnLongClickListener  {

        public TextView tvName;
        public TextView tvNumber;
        public ImageView userPhoto;
        private ClickListener listener;
        //private final View selectedOverlay;

        public ViewHolder(View itemLayoutView,ClickListener listener) {
            super(itemLayoutView);

            this.listener = listener;
            tvName = (TextView) itemLayoutView.findViewById(R.id.tv_user_name);
           // tvNumber= (TextView) itemLayoutView.findViewById(R.id.tv_userNumber);
            userPhoto = (ImageView) itemLayoutView.findViewById(R.id.iv_user_photo);

            itemLayoutView.setOnClickListener(this);

            itemLayoutView.setOnLongClickListener (this);
        }



        @Override
        public void onClick(View v) {


            if (listener != null) {
                listener.onItemClicked(getAdapterPosition ());



            }
        }

        @Override
        public boolean onLongClick (View view) {
            if (listener != null) {
                return listener.onItemLongClicked(getAdapterPosition ());
            }
            return false;
        }



        public interface ClickListener {
            public void onItemClicked(int position);

            public boolean onItemLongClicked(int position);

            boolean onCreateOptionsMenu(Menu menu);
            public void onBackPressed();
        }
    }
}
