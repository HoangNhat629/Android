package com.example.module2_toeic.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.module2_toeic.R;
import com.example.module2_toeic.activities.StudyActivity;
import com.example.module2_toeic.database.DatabaseUtils;
import com.example.module2_toeic.models.CategoryModel;
import com.example.module2_toeic.models.TopicModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ToeicExpandableListAdapter extends BaseExpandableListAdapter {

    List<CategoryModel> categoryModels;
    HashMap<String, List<TopicModel>> topicModelHashMap;

    public ToeicExpandableListAdapter(List<CategoryModel> categoryModels, HashMap<String, List<TopicModel>> topicModelHashMap) {
        this.categoryModels = categoryModels;
        this.topicModelHashMap = topicModelHashMap;
    }

    @Override
    public int getGroupCount() {
        return categoryModels.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return topicModelHashMap.get(categoryModels
                .get(groupPosition).getName()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categoryModels.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return topicModelHashMap.get(categoryModels.get(groupPosition).getName()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_category, parent, false);

        CategoryModel categoryModel = (CategoryModel) getGroup(groupPosition);

        TextView tvCategory = convertView.findViewById(R.id.tv_category);
        TextView tvTopics = convertView.findViewById(R.id.tv_topics);
        CardView cvCategory = convertView.findViewById(R.id.cv_category);

        cvCategory.setCardBackgroundColor(Color.parseColor(categoryModel.getColor()));
        tvCategory.setText(categoryModel.getName());

        String topicNames = "";
        for (int i = 0; i < 5; i++) {
            topicNames += topicModelHashMap
                    .get(categoryModel.getName()).get(i).getName();
            if (i != 4) {
                topicNames += ", ";
            }
        }
        tvTopics.setText(topicNames);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_topic, parent, false);

        final TopicModel topicModel = (TopicModel) getChild(groupPosition, childPosition);

        TextView tvName = convertView.findViewById(R.id.tv_topic);
        ProgressBar pbTopic = convertView.findViewById(R.id.pb_topic);
        TextView tvLastTime = convertView.findViewById(R.id.tv_last_time);

        tvName.setText(topicModel.getName());
        String lastTime = DatabaseUtils.getInstance(
                parent.getContext()).getLastTimeByTopicId(topicModel.getId());
        if (lastTime == null) {
            tvLastTime.setText("Never learned before");
        } else {
            tvLastTime.setText(lastTime);
        }

        pbTopic.setMax(12);
        pbTopic.setProgress(DatabaseUtils.getInstance(parent.getContext())
                .getNumberOfMasterByTopicId(topicModel.getId()));
        pbTopic.setSecondaryProgress(12 - DatabaseUtils.getInstance(parent.getContext())
                .getNumberOfNewWordByTopicId(topicModel.getId()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String lastTime = simpleDateFormat.format(Calendar.getInstance().getTime());
                DatabaseUtils.getInstance(parent.getContext()).updateLastTime(topicModel, lastTime);

                Intent intent = new Intent(parent.getContext(), StudyActivity.class);
                intent.putExtra(StudyActivity.KEY_TOPIC, topicModel);
                parent.getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
