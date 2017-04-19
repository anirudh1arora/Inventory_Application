package com.anirudh.android.invent_app;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anirudh.android.invent_app.data.DbHelper;
import com.anirudh.android.invent_app.data.InventContract.InventoryEntry;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private   DbHelper mDbHelper;

    private Uri mCurrentInventUri;

    private boolean mPetHasChanged = false;


    private static final int Invent_Loader=0;

    InventCursorAdapter mCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // Find the ListView which will be populated with the pet data
        ListView ListView = (ListView) findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);
        ListView.setEmptyView(emptyView);

        mCursorAdapter = new InventCursorAdapter(this,null);
        ListView.setAdapter(mCursorAdapter);



        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent =new Intent(MainActivity.this,EditorActivity.class);

                Uri currentUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI,id);

                intent.setData(currentUri);

                startActivity(intent);
            }
        });


        getSupportLoaderManager().initLoader(Invent_Loader,null,this);

    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mPetHasChanged = true;
            return false;
        }
    };

    public void onClickSell(View view) {

        Intent intent = new Intent(MainActivity.this, MainActivity.class);

        Uri currentProductUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);

        intent.setData(currentProductUri);

        mCurrentInventUri = intent.getData();


        TextView mStockText;
        mStockText = (TextView) findViewById(R.id.summary);

        int stock = Integer.parseInt(mStockText.getText().toString());

        ContentValues values = new ContentValues();
        if(stock>0){
stock--;
    String stockString = String.valueOf(stock);
mStockText.setText(stockString);

            values.put(InventoryEntry.COLUMN_INVENTORY_QUANTITY, stockString);

            int rowsAffected = getContentResolver().update(mCurrentInventUri, values, null, null);

            String rowsAffected1 = String.valueOf(rowsAffected);

            Toast.makeText(MainActivity.this,rowsAffected1,Toast.LENGTH_LONG).show();

        }
else
{
    Toast.makeText(MainActivity.this,getString(R.string.InstockException),Toast.LENGTH_LONG).show();
}
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:

                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_INVENTORY_NAME,
                InventoryEntry.COLUMN_INVENTORY_PRICE,
                InventoryEntry.COLUMN_INVENTORY_EMAIL,
                InventoryEntry.COLUMN_INVENTORY_IMAGE,
                InventoryEntry.COLUMN_INVENTORY_QUANTITY,
                InventoryEntry.COLUMN_INVENTORY_SOLDSTOCK
        };

        return new CursorLoader(this,InventoryEntry.CONTENT_URI,projection,null,null,null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
