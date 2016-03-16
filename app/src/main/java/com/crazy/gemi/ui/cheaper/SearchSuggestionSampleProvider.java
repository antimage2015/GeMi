package com.crazy.gemi.ui.cheaper;

import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.CancellationSignal;

import com.crazy.gemi.R;

/**
 *  该类用于记录最近的搜索，并将其存入数据库
 */
public class SearchSuggestionSampleProvider
        extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY="com.crazy.gemi.ui.cheaper.SearchSuggestionSampleProvider";
    public final static int MODE=DATABASE_MODE_QUERIES;

    public SearchSuggestionSampleProvider(){
        super();
        setupSuggestions(AUTHORITY, MODE);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
        String[] items = null;
        Cursor cursor = super.query(uri, projection, selection, selectionArgs, sortOrder);
        int arrayLength = cursor.getCount();
        if (arrayLength != 0) {
            items = new String[arrayLength + 1];
            cursor.moveToFirst();
            int i = 0;
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int number = cursor.getColumnIndex("suggest_intent_query");
                items[i] = cursor.getString(number);
                i++;
            }
        //    items[i] = "清空历史记录";
            items[i] = getContext().getString(R.string.clear_search_keyword);
        }else {
            return null;
        }

        // suggest_format 等字段不能够改变
        String[] columns = new String[]{"suggest_format", "suggest_text_1", "suggest_intent_query", "_id"};
        // ContentProvider对外共享数据的时候,如果没有数据库，需要对外共享数据时则使用 MatrixCursor
        MatrixCursor stringCursor = new MatrixCursor(columns);
        String row[] = new String[4];
        int i = 0;
        for (CharSequence item : items) {
            row[0] = "" + 0;
            row[1] = item.toString();
            row[2] = item.toString();
            row[3] = "" + (++i);
            stringCursor.addRow(row);
        }
        return stringCursor;
    }
}
