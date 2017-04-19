package com.anirudh.android.invent_app.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Anirudh on 3/28/2017.
 */

public final class InventContract {

    private InventContract() {
    }
    public static final String CONTENT_AUTHORITY = "com.anirudh.android.invent";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_INVENTS = "invent";
    public static final class InventoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTS);


        public static final String
                CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTS;

        /**
         */
        public static final String
                CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTS;


        public final static String TABLE_NAME = "invent";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_INVENTORY_NAME ="name";

        public final static String COLUMN_INVENTORY_PRICE ="price";

        public final static String COLUMN_INVENTORY_QUANTITY ="quantity";

        public final static String COLUMN_INVENTORY_SOLDSTOCK ="soldstock";

        public final static String COLUMN_INVENTORY_EMAIL ="email";

        public final static String COLUMN_INVENTORY_IMAGE ="image";



    }
}