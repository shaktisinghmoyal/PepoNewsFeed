package com.pepo.news.data.networking.datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;
import android.util.Log;

import com.pepo.news.data.entity.NewsFeedEntity;
import com.pepo.news.data.entity.mapper.EntityToDataMapper;
import com.pepo.news.data.entity.mapper.RSSToEntityMapper;
import com.pepo.news.domain.model.NewsFeed;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by shakti on 5/7/2017.
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
                + KEY_LINK + " TEXT" + ")";
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
        SQLiteDatabase db = this.getWritableDatabase();
        for (NewsFeedEntity newsFeedEntity : newsFeedEntityList) {
            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, newsFeedEntity.getTitle());
            values.put(KEY_LINK, newsFeedEntity.getLink());

            db.insert(TABLE_NEWS_FEED, null, values);
        }
        db.close(); // Closing database connection

        if (shouldBroadCast) {
            Intent intent = new Intent();
            ArrayList<NewsFeed> newsFeeds = (ArrayList) entityToDataMapper
                    .transFromNewsFeedEntity(
                            newsFeedEntityList);
            intent.putParcelableArrayListExtra("news_feeds", newsFeeds);
            intent.setAction("com.pepo.news.NEWS_UPDATED");
            context.sendBroadcast(intent);
        }
    }

    // Getting single contact
    NewsFeedEntity getNewsFeed(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NEWS_FEED, new String[]{KEY_ID,
                        KEY_TITLE, KEY_LINK}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        NewsFeedEntity contact = new NewsFeedEntity(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), null);
        // return contact
        return contact;
    }


    public List<NewsFeedEntity> getAllNewsFeeds() {
        List<NewsFeedEntity> newsFeedEntityList = new ArrayList<NewsFeedEntity>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NEWS_FEED;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NewsFeedEntity newsFeedEntity = new NewsFeedEntity();
                newsFeedEntity.setId(Integer.parseInt(cursor.getString(0)));
                newsFeedEntity.setTitle(cursor.getString(1));
                newsFeedEntity.setLink(cursor.getString(2));

                newsFeedEntityList.add(newsFeedEntity);
            } while (cursor.moveToNext());
        }


        return newsFeedEntityList;
    }


    public int updateNewsFeed(NewsFeedEntity newsFeedEntity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, newsFeedEntity.getTitle());
        values.put(KEY_LINK, newsFeedEntity.getLink());

        // updating row
        return db.update(TABLE_NEWS_FEED, values, KEY_ID + " = ?",
                new String[]{String.valueOf(newsFeedEntity.getId())});
    }


    public void deleteNewsFeed(NewsFeedEntity newsFeedEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NEWS_FEED, KEY_ID + " = ?",
                new String[]{String.valueOf(newsFeedEntity.getId())});
        db.close();
    }


    public void deleteAllNewsFeeds() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NEWS_FEED, null, null);
        db.close();
    }

    public int getNewsFeedCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NEWS_FEED;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
