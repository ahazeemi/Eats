package com.example.lenovo.eats.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lenovo.eats.ClassModel.TDBPair;
import com.example.lenovo.eats.R;;

import java.util.ArrayList;

public class TDBTwoFieldAdapter extends ArrayAdapter
{
    private int Header = 0, Data = 1, Footer = 2;
    private ArrayList<TDBPair> data;
    private boolean showFooter = false;

    public TDBTwoFieldAdapter(@NonNull Context context, int resource, ArrayList<TDBPair> data)
    {
        super(context, resource);
        this.data = data;
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position == data.size() - 1 && showFooter)
        {
            return Footer;
        }
        else if(position > 0)
        {
            return Data;
        }
        return Header;
    }

    @Override
    public int getViewTypeCount()
    {
        return 3;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        int itemType = getItemViewType(position);

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(itemType == Header)
            {
                convertView = inflater.inflate(R.layout.tdb_two_field_header, parent, false);
            }
            else if(itemType == Data)
            {
                if(position % 2 == 0)
                {
                    convertView = inflater.inflate(R.layout.tdb_two_field_body_even, parent, false);
                }
                else
                {
                    convertView = inflater.inflate(R.layout.tdb_two_field_body_odd, parent, false);
                }
            }
            else
            {
                convertView = inflater.inflate(R.layout.tdb_two_field_footer, parent, false);
            }
        }

        ((TextView)convertView.findViewById(R.id.leftTv)).setText(data.get(position).getFirst());
        ((TextView)convertView.findViewById(R.id.rightTv)).setText(data.get(position).getSecond());

        return convertView;
    }

    public boolean isShowFooter()
    {
        return showFooter;
    }

    public void setShowFooter(boolean showFooter)
    {
        this.showFooter = showFooter;
    }
}
