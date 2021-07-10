package com.example.module2_toeic.activities;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.module2_toeic.R;
import com.example.module2_toeic.database.DatabaseUtils;
import com.example.module2_toeic.models.TopicModel;
import com.example.module2_toeic.models.WordModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudyActivity extends AppCompatActivity {
    private static final String TAG = "StudyActivity";

    public static String KEY_TOPIC = "key_topic";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_name_topic)
    TextView tvNameTopic;
    @BindView(R.id.tv_origin)
    TextView tvOrigin;
    @BindView(R.id.tv_pronun)
    TextView tvPronun;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.iv_word)
    ImageView ivWord;
    @BindView(R.id.tv_example)
    TextView tvExample;
    @BindView(R.id.tv_example_trans)
    TextView tvExampleTrans;
    @BindView(R.id.tv_didnt_know)
    TextView tvDidntKnow;
    @BindView(R.id.tv_knew)
    TextView tvKnew;
    @BindView(R.id.cl_detail_part)
    ConstraintLayout clDetailPart;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.cl_full)
    ConstraintLayout clFull;
    @BindView(R.id.cv_word)
    CardView cvWord;
    @BindView(R.id.rl_background)
    RelativeLayout rlBackground;

    private int preWordId = -1;
    private WordModel wordModel;
    private AnimatorSet animatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        ButterKnife.bind(this);

        loadData();
    }

    private void nextWord(boolean isKnown) {
        setupAnimation(R.animator.animation_move_to_left);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                DatabaseUtils.getInstance(StudyActivity.this)
                        .updateWordLevel(wordModel, isKnown);
                loadData();
                clFull.setLayoutTransition(null);
                changeContent(true);
                setupAnimation(R.animator.animation_move_from_right);
            }
        });
    }

    private void setupAnimation(int animatorId) {
        animatorSet = (AnimatorSet) AnimatorInflater
                .loadAnimator(StudyActivity.this, animatorId);
        animatorSet.setTarget(cvWord);
        animatorSet.start();
    }

    private void loadData() {
        TopicModel topicModel = (TopicModel) getIntent().getSerializableExtra(KEY_TOPIC);

        tvNameTopic.setText(topicModel.getName());
        rlBackground.setBackgroundColor(Color.parseColor(topicModel.getColor()));

        wordModel = DatabaseUtils.getInstance(this).getRandomWord(topicModel.getId(), preWordId);
        preWordId = wordModel.getId();

        tvOrigin.setText(wordModel.getOrigin());
        tvPronun.setText(wordModel.getPronunciation());
        tvType.setText(wordModel.getType());
        tvExplain.setText(wordModel.getExplanation());
        tvExample.setText(wordModel.getExample());
        tvExampleTrans.setText(wordModel.getExample_trans());
        Glide.with(this).load(wordModel.getImageUrl()).into(ivWord);

        switch (wordModel.getLevel()) {
            case 0:
                tvLevel.setText("New word");
                break;
            case 1:
            case 2:
            case 3:
                tvLevel.setText("Review");
                break;
            case 4:
                tvLevel.setText("Master");
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_details, R.id.tv_didnt_know, R.id.tv_knew})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_details:
                clFull.setLayoutTransition(new LayoutTransition());
                changeContent(false);
                break;
            case R.id.tv_didnt_know:
                nextWord(false);
                break;
            case R.id.tv_knew:
                nextWord(true);
                break;
        }
    }

    private void changeContent(boolean isExpanded) {
        if (isExpanded) {
            clDetailPart.setVisibility(View.GONE);
            tvDetails.setVisibility(View.VISIBLE);
        } else {
            clDetailPart.setVisibility(View.VISIBLE);
            tvDetails.setVisibility(View.GONE);
        }
    }
}
