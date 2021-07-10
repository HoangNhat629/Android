package com.example.module2_toeic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.module2_toeic.models.CategoryModel;
import com.example.module2_toeic.models.TopicModel;
import com.example.module2_toeic.models.WordModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseUtils {
    private static final String TAG = "DatabaseUtils";

    private final String TABLE_TOPIC = "tbl_topic";
    private final String TABLE_WORD = "tbl_word";

    private SQLiteDatabase sqLiteDatabase;
    private MyDatabase myDatabase;

    //Singleton
    private static DatabaseUtils databaseUtils;

    public DatabaseUtils(Context context) {
        myDatabase = new MyDatabase(context);
    }

    public static DatabaseUtils getInstance(Context context) {
        if (databaseUtils == null) {
            databaseUtils = new DatabaseUtils(context);
        }
        return databaseUtils;
    }

    public List<TopicModel> getListTopic() {
        sqLiteDatabase = myDatabase.getReadableDatabase();
        List<TopicModel> topicModels = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_TOPIC, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            //read data
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String category = cursor.getString(4);
            String color = cursor.getString(5);
            String lastTime = cursor.getString(6);

            TopicModel topicModel = new TopicModel(id, name, category, color, lastTime);
            topicModels.add(topicModel);
            //next
            cursor.moveToNext();
        }

        Log.d(TAG, "getListTopic: " + topicModels);

        return topicModels;
    }

    public List<CategoryModel> getListCategory(List<TopicModel> topicModels) {
        List<CategoryModel> categoryModelList = new ArrayList<>();
        for (int i = 0; i < topicModels.size(); i = i + 5) {
            CategoryModel categoryModel = new CategoryModel(
                    topicModels.get(i).getCategory(),
                    topicModels.get(i).getColor());
            categoryModelList.add(categoryModel);
        }
        Log.d(TAG, "getListCategory: " + categoryModelList);
        return categoryModelList;
    }

    public HashMap<String, List<TopicModel>> getHashMapTopic(List<TopicModel> topicModels,
                                                             List<CategoryModel> categoryModels) {
        HashMap<String, List<TopicModel>> hashMap = new HashMap<>();

        //add 10 list
        for (int i = 0; i < categoryModels.size(); i++) {
            //i = 9
            int positionTopic = i * 5; //45
            // == list.add(T)
            // hashmap.put(key - category name, value - list: 5 topic in category)
            hashMap.put(
                    categoryModels.get(i).getName(),
                    topicModels.subList(positionTopic, positionTopic + 5)); //45 -> 50
            Log.d(TAG, "getHashMapTopic: ----" + categoryModels.get(i).getName());
            Log.d(TAG, "getHashMapTopic: " + hashMap.get(categoryModels.get(i).getName()));
        }

        return hashMap;
    }

    public WordModel getRandomWord(int topicId, int preWordId) {
        sqLiteDatabase = myDatabase.getReadableDatabase();

        int level = 0;
        Cursor cursor;

        do {
            double random = Math.random() * 100;
            if (random <=5 ) level = 4; //5
            else if (random <= 15) level = 3; //5->15: 10
            else if (random <= 30) level = 2; //15->30: 15
            else if (random <= 55) level = 1; //30->55: 25
            else level = 0; //45

            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_WORD +
                    " WHERE topic_id =  " + topicId +
                    " AND LEVEL = " + level +
                    " AND id <> " + preWordId +
                    " ORDER BY RANDOM() LIMIT 1", null);
        } while (cursor.getCount() == 0);

        cursor.moveToFirst();
        int id = cursor.getInt(0);
        String origin = cursor.getString(1);
        String explanation = cursor.getString(2);
        String type = cursor.getString(3);
        String pronunciation = cursor.getString(4);
        String imageUrl = cursor.getString(5);
        String example = cursor.getString(6);
        String example_trans = cursor.getString(7);

        return new WordModel(id, origin, explanation, type, pronunciation, imageUrl,
                example, example_trans, topicId, level);
    }

    public void updateWordLevel(WordModel wordModel, boolean isKnown) {
        sqLiteDatabase = myDatabase.getWritableDatabase();

        int level = wordModel.getLevel();
        if (isKnown && level < 4) {
            level++;
        } else if (!isKnown && level > 0) {
            level--;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("level", level);
        sqLiteDatabase.update(TABLE_WORD, contentValues,
                "id = " + wordModel.getId(), null);
    }

    public int getNumberOfNewWordByTopicId(int topicId) {
        sqLiteDatabase = myDatabase.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_WORD + " WHERE level = 0 and topic_id = " + topicId, null);
        return cursor.getCount();
    }

    public int getNumberOfMasterByTopicId(int topicId) {
        sqLiteDatabase = myDatabase.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_WORD + " WHERE level = 4 and topic_id = " + topicId, null);
        return cursor.getCount();
    }

    public void updateLastTime(TopicModel topicModel, String lastTime) {
        sqLiteDatabase = myDatabase.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("last_time", lastTime);
        sqLiteDatabase.update(TABLE_TOPIC, contentValues,
                "id = " + topicModel.getId(), null);
    }

    public String getLastTimeByTopicId(int topicId) {
        sqLiteDatabase = myDatabase.getReadableDatabase();
        Cursor cursor = sqLiteDatabase
                .rawQuery("SELECT last_time FROM " + TABLE_TOPIC
                        + " WHERE id = " + topicId, null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }
}
