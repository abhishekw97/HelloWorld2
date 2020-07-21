package com.example.helloworld;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OdataList extends ArrayAdapter<Odata>{

    private Activity context;
    private List<Odata> odataList;

    public OdataList(Activity context,List<Odata> odatalist){
        super(context,R.layout.list_layout,odatalist);
        this.context=context;
        this.odataList=odatalist;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.list_layout,null,true);

        TextView textViewName=(TextView)listViewItem.findViewById(R.id.textViewName);
        TextView textViewAddress=(TextView)listViewItem.findViewById(R.id.textViewAddress);
        TextView textViewContact=(TextView)listViewItem.findViewById(R.id.textViewContact);
        TextView textViewRequirement=(TextView)listViewItem.findViewById(R.id.textViewRequirement);

        Odata odata=odataList.get(position);
        textViewName.setText(odata.getoName());
        textViewAddress.setText(odata.getoAddress());
        textViewContact.setText(odata.getoContact());
        textViewRequirement.setText(odata.getoRequirements());

        return listViewItem;
    }
}
