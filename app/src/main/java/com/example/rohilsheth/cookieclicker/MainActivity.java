package com.example.rohilsheth.cookieclicker;

import android.graphics.Color;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    TextView grandmas,factories, cps;
    ImageView image, grandma, factory;
    ConstraintLayout constraintLayout;
    TextView points;
    AtomicInteger pointCount = new AtomicInteger(0);
    int pc = 0;
    boolean runGrandma = true;
    int numberOfGrandmas = 0;
    int numberOfFactories = 0;
    int grandmaSubtract = 50;
    int factorySubtract = 100;
    ScaleAnimation grandmaStartAnimation = new ScaleAnimation(0.5f,1.0f,0.5f,1.0f, Animation.RELATIVE_TO_SELF,.5f,Animation.RELATIVE_TO_SELF,0.5f);
    ScaleAnimation factoryStartAnimation = new ScaleAnimation(0.5f,1.0f,0.5f,1.0f, Animation.RELATIVE_TO_SELF,.5f,Animation.RELATIVE_TO_SELF,0.5f);
    ScaleAnimation grandmaEnd = new ScaleAnimation(1.0f,.01f,1.0f,.01f, Animation.RELATIVE_TO_SELF,.5f,Animation.RELATIVE_TO_SELF,0.5f);
    ScaleAnimation factoryEnd = new ScaleAnimation(1.0f,.01f,1.0f,.01f, Animation.RELATIVE_TO_SELF,.5f,Animation.RELATIVE_TO_SELF,0.5f);
    public static final String POINT_KEY = "savedpoint";
    public static final String GR = "grr";
    public static final String FA = "faa";
    public static final String GS = "gss";
    public static final String FS = "fss";
    public static final String ee = "eee";
    int layoutId = 0;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        pc = pointCount.intValue();
        outState.putInt(POINT_KEY,pc);
        outState.putInt(GR,numberOfGrandmas);
        outState.putInt(FA,numberOfFactories);
        outState.putInt(GS,grandmaSubtract);
        outState.putInt(FS,factorySubtract);
        layoutId = constraintLayout.getId();
        outState.putInt(ee, layoutId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = (ConstraintLayout)findViewById(R.id.Layout);

        image = (ImageView) findViewById(R.id.imageView);
        factory = findViewById(R.id.factoryID);
        points = findViewById(R.id.ID_Points);
        grandma = findViewById(R.id.imageView2);
        grandmas = findViewById(R.id.textView2);
        cps = findViewById(R.id.textView4);
        grandmas.setText("");
        factories = findViewById(R.id.textView);
        factories.setText("");
        if(savedInstanceState!=null) {
            pointCount = new AtomicInteger(savedInstanceState.getInt(POINT_KEY));
            numberOfGrandmas = savedInstanceState.getInt(GR);
            numberOfFactories = savedInstanceState.getInt(FA);
            grandmaSubtract = savedInstanceState.getInt(GS);
            factorySubtract = savedInstanceState.getInt(FS);
            grandmas.setText(numberOfGrandmas+" grandmas");
            factories.setText(numberOfFactories+" factories");
            cps.setText(numberOfGrandmas+(numberOfFactories*2)+" CPS");
            for(int i=0; i<numberOfGrandmas;i++){
                layoutGrandma();
            }
            for(int i=0; i<numberOfFactories;i++){
                layoutFactory();
            }
            layoutId = savedInstanceState.getInt(ee);
            constraintLayout.setId(layoutId);
            setContentView(constraintLayout);
        }
        final ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,1.25f,1.0f,1.25f, Animation.RELATIVE_TO_SELF,.5f,Animation.RELATIVE_TO_SELF,0.5f);
        grandmaStartAnimation.setDuration(200);
        factoryStartAnimation.setDuration(200);
        grandmaEnd.setDuration(200);
        factoryEnd.setDuration(200);
        scaleAnimation.setDuration(150);
        grandma.setVisibility(View.INVISIBLE);
        factory.setVisibility(View.INVISIBLE);
        new PassiveUpgrades().start();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(scaleAnimation);
                pointCount.incrementAndGet();
                addOne();
                points.setText("Cookies: "+pointCount+"");
                checkUpgrades();

            }
        });
        checkUpgrades();
        grandma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOfGrandmas+=1;
                grandmas.setText(numberOfGrandmas+" grandmas");
                cps.setText(numberOfGrandmas+(numberOfFactories*2)+" CPS");
                createGrandma();
                checkUpgrades();
            }
        });
        factory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOfFactories+=1;
                factories.setText(numberOfFactories+" factories");
                cps.setText(numberOfGrandmas+(numberOfFactories*2)+" CPS");
                createFactory();
                checkUpgrades();
            }
        });
    }

    public void addOne(){
        final TextView textViewinCode;
        textViewinCode=new TextView(this);
        textViewinCode.setId(View.generateViewId());
        textViewinCode.setText("+1");
        textViewinCode.setTextColor(Color.WHITE);
        textViewinCode.setTextSize(15);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
        textViewinCode.setLayoutParams(params);
        constraintLayout.addView(textViewinCode);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(textViewinCode.getId(),ConstraintSet.BOTTOM,image.getId(),ConstraintSet.BOTTOM);
        constraintSet.connect(textViewinCode.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP);
        constraintSet.connect(textViewinCode.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT);
        constraintSet.connect(textViewinCode.getId(),ConstraintSet.RIGHT,constraintLayout.getId(),ConstraintSet.RIGHT);
        constraintSet.setVerticalBias(textViewinCode.getId(),.45f);
        constraintSet.setHorizontalBias(textViewinCode.getId(),(float)(Math.random()*0.2+0.4));
        constraintSet.applyTo(constraintLayout);
        TranslateAnimation animation = new TranslateAnimation(0,0,50,-300);
        animation.setDuration(800);
        textViewinCode.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                constraintLayout.removeView(textViewinCode);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    public void createGrandma(){
        pointCount.addAndGet(-grandmaSubtract);
        grandmaSubtract+=8;
        layoutGrandma();
    }
    public void layoutGrandma(){
        points.setText("Cookies: "+pointCount+"");
        ImageView grandmaImage;
        grandmaImage=new ImageView(this);
        grandmaImage.setId(View.generateViewId());
        grandmaImage.setImageResource(R.drawable.grandmaa);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
        params.height = 150;
        params.width = 150;
        grandmaImage.setLayoutParams(params);
        constraintLayout.addView(grandmaImage);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(grandmaImage.getId(),ConstraintSet.TOP,image.getId(),ConstraintSet.BOTTOM);
        constraintSet.connect(grandmaImage.getId(),ConstraintSet.BOTTOM,constraintLayout.getId(),ConstraintSet.BOTTOM);
        constraintSet.connect(grandmaImage.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT);
        constraintSet.connect(grandmaImage.getId(),ConstraintSet.RIGHT,constraintLayout.getId(),ConstraintSet.RIGHT);
        constraintSet.setVerticalBias(grandmaImage.getId(),(float)(Math.random()*0.5));
        constraintSet.setHorizontalBias(grandmaImage.getId(),(float)(Math.random()*0.5));
        constraintSet.applyTo(constraintLayout);
        grandmaImage.startAnimation(grandmaStartAnimation);
    }
    public void createFactory(){
        pointCount.addAndGet(-factorySubtract);
        factorySubtract+=10;
        layoutFactory();
    }
    public void layoutFactory(){
        points.setText("Cookies: "+pointCount+"");
        ImageView factoryImage;
        factoryImage=new ImageView(this);
        factoryImage.setId(View.generateViewId());
        factoryImage.setImageResource(R.drawable.factory);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
        params.height = 150;
        params.width = 150;
        factoryImage.setLayoutParams(params);
        constraintLayout.addView(factoryImage);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(factoryImage.getId(),ConstraintSet.TOP,image.getId(),ConstraintSet.BOTTOM);
        constraintSet.connect(factoryImage.getId(),ConstraintSet.BOTTOM,constraintLayout.getId(),ConstraintSet.BOTTOM);
        constraintSet.connect(factoryImage.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT);
        constraintSet.connect(factoryImage.getId(),ConstraintSet.RIGHT,constraintLayout.getId(),ConstraintSet.RIGHT);
        constraintSet.setVerticalBias(factoryImage.getId(),(float)(Math.random()*0.5));
        constraintSet.setHorizontalBias(factoryImage.getId(),(float)(Math.random()*0.5+0.5));
        constraintSet.applyTo(constraintLayout);
        factoryImage.startAnimation(factoryStartAnimation);
    }
    public void checkUpgrades(){
        if (pointCount.get()>=grandmaSubtract) {
            grandma.setVisibility(View.VISIBLE);
        }
        if (pointCount.get()<=grandmaSubtract) {
            grandma.setVisibility(View.INVISIBLE);
        }
        if (pointCount.get()<=factorySubtract) {
            factory.setVisibility(View.INVISIBLE);
        }
        if (pointCount.get()>=factorySubtract) {
            factory.setVisibility(View.VISIBLE);
        }
        if (pointCount.get()==grandmaSubtract) {
            grandma.startAnimation(grandmaStartAnimation);
            grandma.setVisibility(View.VISIBLE);

        }
        if(pointCount.get()==factorySubtract) {
            factory.startAnimation(factoryStartAnimation);
            factory.setVisibility(View.VISIBLE);
        }
    }
    public class PassiveUpgrades extends Thread{
        public void run(){
            while (runGrandma) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                pointCount.addAndGet(numberOfGrandmas);
                pointCount.addAndGet(numberOfFactories*2);
                points.post(new Runnable() {
                    @Override
                    public void run() {
                        points.setText("Cookies: "+pointCount+"");
                    }
                });
                if (pointCount.get()>=grandmaSubtract) {
                    grandma.post(new Runnable() {
                        @Override
                        public void run() {
                            grandma.setVisibility(View.VISIBLE);
                        }
                    });

                }
                if (pointCount.get()<grandmaSubtract) {
                    grandma.post(new Runnable() {
                        @Override
                        public void run() {
                            grandma.setVisibility(View.INVISIBLE);

                        }
                    });
                }
                if (pointCount.get()<=factorySubtract) {
                    factory.post(new Runnable() {
                        @Override
                        public void run() {
                            factory.setVisibility(View.INVISIBLE);

                        }
                    });
                }
                if (pointCount.get()>=factorySubtract) {
                    factory.post(new Runnable() {
                        @Override
                        public void run() {
                            factory.setVisibility(View.VISIBLE);

                        }
                    });
                }

            }
        }
    }

}

