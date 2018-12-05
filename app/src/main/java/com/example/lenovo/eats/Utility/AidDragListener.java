package com.example.lenovo.eats.Utility;

/**
 * Created by Sheri on 22-Nov-18.
 */

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.View;
import com.example.*;
import com.example.lenovo.eats.Activities.DineinChefInterface;
import com.example.lenovo.eats.Adapters.AidListAdapter;
import com.example.lenovo.eats.R;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class AidDragListener implements View.OnDragListener {

    private boolean isDropped = false;

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DROP:
                isDropped = true;
                int positionTarget = -1;
                View viewSource = (View) event.getLocalState();
                int viewId = v.getId();
                final int flItem = R.id.frame_layout_item;
                if(getTarget(viewId)!=null || viewId==flItem) {
                        RecyclerView target=getTarget(viewId);
                        if (target==null)
                            {target = (RecyclerView) v.getParent();
                            positionTarget = (int) v.getTag();}

                        if (viewSource != null) {
                            RecyclerView source = (RecyclerView)
                                                viewSource.getParent();

                            AidListAdapter
                                    adapterSource =(AidListAdapter)
                                    source.getAdapter();
                            int positionSource = (int)
                                    viewSource.getTag();
                            String list = adapterSource.getList()
                                            .get(positionSource);
                            List<String> listSource = adapterSource
                                            .getList();
                            listSource.remove(positionSource);
                            adapterSource.updateList(listSource);
                            adapterSource.notifyDataSetChanged();
                            AidListAdapter adapterTarget =
                                    (AidListAdapter)
                                    target.getAdapter();
                            List<String> customListTarget = adapterTarget
                                    .getList();
                            if (positionTarget > 0)
                                {customListTarget
                                        .add(positionTarget, list);}                               else {customListTarget.add(list);}
                            adapterTarget.updateList(customListTarget);
                            adapterTarget.notifyDataSetChanged();
                        }
                }
                break;
        }

        if (!isDropped && event.getLocalState() != null) {
            ((View) event.getLocalState()).setVisibility(View.VISIBLE);
        }
        return true;
    }
RecyclerView getTarget(int Id)
{
    for (int i = 0; i< DineinChefInterface.queueCount; i++)
    {
        if (Id== DineinChefInterface.queues[i].getId())
        {
            return DineinChefInterface.queues[i];
        }

    }
    return null;
}

}