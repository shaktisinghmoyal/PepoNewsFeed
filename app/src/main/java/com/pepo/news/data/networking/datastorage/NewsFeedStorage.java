package com.pepo.news.data.networking.datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pepo.news.R;
import com.pepo.news.data.entity.NewsFeedEntity;
import com.pepo.news.data.entity.mapper.EntityToDataMapper;
import com.pepo.news.domain.model.NewsFeed;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * this class is responsible for creating sqlit data base and CRUD operation with database
 */

public class NewsFeedStorage extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "PEPO_NEWS_FEED";

    // Contacts table name
    private static final String TABLE_NEWS_FEED = "news_feed";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_LINK = "link";
    private static final String KEY_IMAGE_LINK = "imageLink";
    private static final String KEY_IS_READ = "read";
    private Context context;
    private EntityToDataMapper entityToDataMapper;

    @Inject
    public NewsFeedStorage(Context context, EntityToDataMapper entityToDataMapper) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.entityToDataMapper = entityToDataMapper;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NEWS_FEED + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_LINK + " TEXT," + KEY_IMAGE_LINK + " TEXT," + KEY_IS_READ + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS_FEED);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    public void addAllNewsFeed(List<NewsFeedEntity> newsFeedEntityList, Boolean shouldBroadCast) {
        String titleForFirstNewsFromDB = getFirstNewsFeedTitle();
        boolean isFirstNewsFeedFromServer = true, isFirstItemFromDBMatched = false;
        int idForDBNews = 1;
        if (titleForFirstNewsFromDB != null) {
            for (NewsFeedEntity newsFeedEntity : newsFeedEntityList) {

                if (!isFirstItemFromDBMatched) {
                    isFirstItemFromDBMatched = newsFeedEntity.getTitle().equals(titleForFirstNewsFromDB);
                }
                if (isFirstItemFromDBMatched) {

                    if (!isFirstNewsFeedFromServer) {
                        newsFeedEntity.setIsRead(checkIfThisNewsFeedRead(idForDBNews));
                        idForDBNews++;
                    } else {
                        break;
                    }
                }

                isFirstNewsFeedFromServer = false;
            }
        }
        if (titleForFirstNewsFromDB == null || !isFirstNewsFeedFromServer) {
            deleteAllNewsFeeds();
            SQLiteDatabase db = this.getWritableDatabase();
            for (NewsFeedEntity newsFeedEntity : newsFeedEntityList) {
                ContentValues values = new ContentValues();
                values.put(KEY_TITLE, newsFeedEntity.getTitle());
                values.put(KEY_LINK, newsFeedEntity.getLink());
                values.put(KEY_IMAGE_LINK, newsFeedEntity.getImageLink());
                values.put(KEY_IS_READ, newsFeedEntity.getIsRead() == true ? 1 : 0);

                db.insert(TABLE_NEWS_FEED, null, values);
            }
            db.close(); // Closing database connection
        }

        Intent intent = new Intent();
        ArrayList<NewsFeed> newsFeeds = (ArrayList) getAllNewsFeeds();
        intent.putParcelableArrayListExtra(context.getString(R.string.news_feeds),newsFeeds);
        intent.setAction(context.getString(R.string.custom_intent_news_update));
        context.sendBroadcast(intent);
    }

    private boolean checkIfThisNewsFeedRead(int position) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NEWS_FEED, new String[]{KEY_ID,
                        KEY_TITLE, KEY_LINK, KEY_IMAGE_LINK, KEY_IS_READ}, KEY_ID + "=?",
                new String[]{String.valueOf(position)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor.getInt(4) != 0;
    }

    private String getFirstNewsFeedTitle() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NEWS_FEED, null);

        if (cursor.getCount() == 0) {
            return null;
        }

        cursor = db.query(TABLE_NEWS_FEED, new String[]{KEY_ID,
                        KEY_TITLE, KEY_LINK, KEY_IMAGE_LINK, KEY_IS_READ}, KEY_ID + "=?",
                new String[]{String.valueOf(1)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor.getString(1);

    }


    public List<NewsFeed> getAllNewsFeeds() {
        List<NewsFeed> newsFeedList = new ArrayList<NewsFeed>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NEWS_FEED;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NewsFeed newsFeed = new NewsFeed();
                newsFeed.setTitle(cursor.getString(1));
                newsFeed.setLink(cursor.getString(2));
                newsFeed.setImageLink(cursor.getString(3));
                newsFeed.setIsRead(cursor.getInt(4) != 0);
                newsFeedList.add(newsFeed);
            } while (cursor.moveToNext());
        }


        return newsFeedList;
    }


    public int updateNewsFeedReadInfo(int position) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IS_READ, 1);
        // updating row
        return db.update(TABLE_NEWS_FEED, values, KEY_ID + " = ?",
                new String[]{String.valueOf(position + 1)});
    }


    public void deleteAllNewsFeeds() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NEWS_FEED, null, null);
        db.close();
    }

}
