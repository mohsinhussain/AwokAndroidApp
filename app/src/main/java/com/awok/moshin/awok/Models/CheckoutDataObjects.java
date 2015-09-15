package com.awok.moshin.awok.Models;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.awok.moshin.awok.R;

/**
 * Created by shon on 9/14/2015.
 */
public class CheckoutDataObjects {
    private int selected;
    private ArrayAdapter<CharSequence> adapter;

    public CheckoutDataObjects(Context parent) {
        adapter = ArrayAdapter.createFromResource(parent, R.array.object_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public ArrayAdapter<CharSequence> getAdapter() {
        return adapter;
    }

    public String getText() {
        return (String) adapter.getItem(selected);
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

}
