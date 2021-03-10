package com.ics.hunar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ics.hunar.Constant;
import com.ics.hunar.R;
import com.ics.hunar.helper.ApiClient;
import com.ics.hunar.helper.Utils;
import com.ics.hunar.model.Splash_Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash_Activity extends AppCompatActivity {

    ImageView imageView;
    TextView tvtag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        tvtag = findViewById(R.id.tvtag);
        imageView = findViewById(R.id.img_logo);
        callapi();
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/Kollektif-Bold.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
    }


//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));    }

    private void callapi() {

        ApiClient.getApiService().get_splash(Constant.accessKeyValue,"1").enqueue(new Callback<Splash_Response>() {
            @Override
            public void onResponse(Call<Splash_Response> call, Response<Splash_Response> response) {

                Utils.retro_call_info(""+response.raw().request().url(),""+new Gson().toJson(response.body()));
                if (response.isSuccessful()){
                    if (response.body().error.equals("false")){
                        try {
                            Glide.with(Splash_Activity.this).load(response.body().data.get(0).message).
                                    placeholder(R.drawable.hunar_logo_new).into(imageView);
                            tvtag.setText(response.body().data.get(2).message);
                            delaym();
                        }catch (Exception ex) {
                            ex.printStackTrace();
                            delaym();
                        }
                    }else{
                        delaym();
                    }
                }

            }

            @Override
            public void onFailure(Call<Splash_Response> call, Throwable t) {
                Toast.makeText(Splash_Activity.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                delaym();
            }
        });
    }

    public void delaym(){
        new Handler().postDelayed(this::gonext, 3000);
    }

    public void gonext(){
        Intent in = new Intent(Splash_Activity.this, LoginActivity.class);
        startActivity(in);
        finish();
    }

}
