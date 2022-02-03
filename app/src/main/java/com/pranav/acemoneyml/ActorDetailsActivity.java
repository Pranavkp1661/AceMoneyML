package com.pranav.acemoneyml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;

public class ActorDetailsActivity extends AppCompatActivity {
    Context context;
    Intent intent;
    ImageView imvActorImage;
    TextView tvActorName;
    TextView tvActorBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_actor_details);
        initActivity();
        intent = getIntent();
        ActorsModel actorsModel = new ActorsModel();
        if (intent.getParcelableExtra("Details") != null) {
            actorsModel = intent.getParcelableExtra("Details");
        }
        tvActorName.setText(actorsModel.getActorName());
        tvActorBio.setText(actorsModel.getDescription());
        Glide.with(context).load(actorsModel.getImgUrl()).into(imvActorImage);
    }

    private void initActivity() {
        imvActorImage = findViewById(R.id.imvActorImage);
        tvActorName = findViewById(R.id.tvActorName);
        tvActorBio = findViewById(R.id.tvActorBio);
    }
}