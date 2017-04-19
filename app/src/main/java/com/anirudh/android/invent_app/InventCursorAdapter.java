package com.anirudh.android.invent_app;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.anirudh.android.invent_app.data.InventContract;


public class InventCursorAdapter extends CursorAdapter {

    Button sellProduct;
    TextView summaryTextView;
    TextView soldTextView;
    public InventCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
// Find fields to populate in inflated template
        final Context ctext = context;

        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        summaryTextView   = (TextView) view.findViewById(R.id.summary);
        TextView priceTextView = (TextView) view.findViewById(R.id.list_price_stock);
        soldTextView  = (TextView) view.findViewById(R.id.list_sold_stock);
        TextView emailTextView = (TextView) view.findViewById(R.id.list_email);

        // Extract properties from cursor
        int nameColoumnIndex = cursor.getColumnIndex(InventContract.InventoryEntry.COLUMN_INVENTORY_NAME);
        int priceColoumnIndex = cursor.getColumnIndex(InventContract.InventoryEntry.COLUMN_INVENTORY_PRICE);
        int stockColoumnIndex = cursor.getColumnIndex(InventContract.InventoryEntry.COLUMN_INVENTORY_QUANTITY);
        int soldColoumnIndex = cursor.getColumnIndex(InventContract.InventoryEntry.COLUMN_INVENTORY_SOLDSTOCK);
        int emailColoumnIndex = cursor.getColumnIndex(InventContract.InventoryEntry.COLUMN_INVENTORY_EMAIL);
        // Populate fields with extracted properties
        String inventName = cursor.getString(nameColoumnIndex);

        String priceColoumn = cursor.getString(priceColoumnIndex);

        String stockColoumn = cursor.getString(stockColoumnIndex);

        String soldstockColoumn = cursor.getString(soldColoumnIndex);

        String emailColoumn = cursor.getString(emailColoumnIndex);

        nameTextView.setText(inventName);
        priceTextView.setText(priceColoumn);
        summaryTextView.setText(stockColoumn);
        soldTextView.setText(soldstockColoumn);
        emailTextView.setText(emailColoumn);

        sellProduct = (Button) view.findViewById(R.id.list_sell_btn);
        final int id = cursor.getInt(cursor.getColumnIndexOrThrow(InventContract.InventoryEntry._ID));
        final Integer quantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventContract.InventoryEntry.COLUMN_INVENTORY_QUANTITY));
        final Integer sold = cursor.getInt(cursor.getColumnIndexOrThrow(InventContract.InventoryEntry.COLUMN_INVENTORY_SOLDSTOCK));


        sellProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
   onClickSell(ctext, id, quantity,sold);
            }

        });
    }


    public void onClickSell(Context context, int id, int quantity,int sold) {

        Uri currentProductUri = ContentUris.withAppendedId(InventContract.InventoryEntry.CONTENT_URI, id);

        if(quantity>0)
        {
            quantity--;
            sold++;
        }
        ContentValues values = new ContentValues();
        values.put(InventContract.InventoryEntry.COLUMN_INVENTORY_QUANTITY, quantity);
        values.put(InventContract.InventoryEntry.COLUMN_INVENTORY_SOLDSTOCK, sold);

        int rowsAffected = context.getContentResolver().update(currentProductUri, values,null, null);
        if(rowsAffected !=0){
            summaryTextView.setText(Integer.toString(quantity));
            soldTextView.setText(Integer.toString(sold));

        }


    }
}