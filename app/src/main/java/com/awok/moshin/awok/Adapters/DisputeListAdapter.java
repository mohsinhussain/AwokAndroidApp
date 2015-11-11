package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.awok.moshin.awok.Models.DisputeChildModel;
import com.awok.moshin.awok.Models.DisputeExpandableListModel;
import com.awok.moshin.awok.R;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by shon on 11/5/2015.
 */
public class DisputeListAdapter extends BaseExpandableListAdapter {





    private Activity context;
    private Map<String, List<String>> laptopCollections;
    private List<String> laptops;
    private List<DisputeExpandableListModel> data;

    public DisputeListAdapter(Activity context, List<DisputeExpandableListModel> data        ) {
        this.context = context;
        this.laptopCollections = laptopCollections;
        this.laptops = laptops;
        this.data=data;
    }

    public Object getChild(int groupPosition, int childPosition) {
        //return laptopCollections.get(laptops.get(groupPosition)).get(childPosition);
        ArrayList<DisputeChildModel> chList = data.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        //final String laptop = (String) getChild(groupPosition, childPosition);
        DisputeChildModel child = (DisputeChildModel) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dispute_child_lay, null);
        }





        TextView initiator = (TextView) convertView.findViewById(R.id.initiator);
        TextView refund = (TextView) convertView.findViewById(R.id.refund);
        TextView returnProduct = (TextView) convertView.findViewById(R.id.returnProduct);
        TextView action = (TextView) convertView.findViewById(R.id.action);
        TextView reason = (TextView) convertView.findViewById(R.id.reason);
        TextView received = (TextView) convertView.findViewById(R.id.received);

       // ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
       /* delete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to remove?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                List<String> child =
                                        laptopCollections.get(laptops.get(groupPosition));
                                child.remove(childPosition);
                                notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });*/

        initiator.setText(child.getInitiator());
        returnProduct.setText(child.getShipGoods());
        received.setText(child.getIsReceived());
        refund.setText(child.getRefundAmount());
        action.setText(child.getAction());
        reason.setText(child.getNote());







        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        //return laptopCollections.get(laptops.get(groupPosition)).size();
        ArrayList<DisputeChildModel> chList = data.get(groupPosition).getItems();
        return chList.size();
    }

    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }
    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }
    public int getGroupCount() {
        return data.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
//        String laptopName = (String) getGroup(groupPosition);
        DisputeExpandableListModel group = (DisputeExpandableListModel) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.dispute_expandable_header,
                    null);
        }
        TextView dateGroup = (TextView) convertView.findViewById(R.id.dateGroup);





        TextView textGroup = (TextView) convertView.findViewById(R.id.initiatorGroup);


       // item.setTypeface(null, Typeface.BOLD);
       // item.setText(laptopName);
        dateGroup.setText(group.getDate());
        textGroup.setText(group.getInitiator());
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
