package com.example.henrybender.testbillsplit;

import java.util.ArrayList;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class ContactsAdapter extends ArrayAdapter<Contact> implements Filterable {
    private ArrayList<Contact> mOriginalValues;
    private ArrayList<Contact> mDisplayedValues;

    LayoutInflater inflater;
    public ContactsAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
        this.mOriginalValues = contacts;
        this.mDisplayedValues = contacts;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDisplayedValues.size();
    }



    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        ConstraintLayout llcontainer;
        TextView name,number;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.contact_item_list, null);
            holder.llcontainer = (ConstraintLayout) convertView.findViewById(R.id.llContainer);
            holder.name = (TextView) convertView.findViewById(R.id.nameContact);
            holder.number = (TextView) convertView.findViewById(R.id.phone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(mDisplayedValues.get(position).name);
        if (mDisplayedValues.get(position).numbers.size() > 0 && mDisplayedValues.get(position).numbers.get(0) != null) {
            holder.number.setText(mDisplayedValues.get(position).numbers.get(0).number + "");
        }
        holder.llcontainer.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.i("It works", "yay");
            }
        });

        return convertView;
    }
    //    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item
//        Contact contact = (Contact) getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        View view = convertView;
//        if (view == null) {
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            view = inflater.inflate(R.layout.contact_item_list, parent, false);
//        }
//        // Populate the data into the template view using the data object
//        TextView tvName = (TextView) view.findViewById(R.id.name);
//        TextView tvPhone = (TextView) view.findViewById(R.id.phone);
//        tvName.setText(contact.name);
//        tvPhone.setText("");
//
//        if (contact.numbers.size() > 0 && contact.numbers.get(0) != null) {
//            tvPhone.setText(contact.numbers.get(0).number);
//        }
//        return view;
//    }
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mDisplayedValues = (ArrayList<Contact>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Contact> FilteredArrList = new ArrayList<Contact>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Contact>(mDisplayedValues); // saves the original data in mOriginalValues
                }
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).name;
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Contact(mOriginalValues.get(i).name, mOriginalValues.get(i).name));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }


}
