package com.example.saber.animationdemo;

import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //使用LayoutAnimation为ListView每个item设置出场动画
        lv = (ListView) findViewById(R.id.lv);

        Button button = (Button) findViewById(R.id.button1);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation_test);
        button.startAnimation(animation);

        Button btnFrameAnimation = (Button) findViewById(R.id.button2);
        btnFrameAnimation.setBackgroundResource(R.drawable.frame_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) btnFrameAnimation.getBackground();
        animationDrawable.start();

        List<String> datas = new ArrayList<>();
        for(int i=0;i<30;i++){
            datas.add(i+"");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas);
        lv.setAdapter(arrayAdapter);



        //使用属性动画
        //y向下平移
        final Button btnObjectTransition = (Button) findViewById(R.id.btn_object_transition);
        btnObjectTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(btnObjectTransition,"translationY",btnObjectTransition.getHeight());
                objectAnimator.start();
            }
        });

        //改变背景色
        final Button btnObjectColor = (Button) findViewById(R.id.btn_object_color);
        btnObjectColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueAnimator valueAnimator = ObjectAnimator.ofInt(btnObjectColor,"backgroundColor",0xFFFF8080,0xFF8080FF);
                valueAnimator.setDuration(3000);
                valueAnimator.setEvaluator(new ArgbEvaluator());//设置时间估值器
                valueAnimator.setRepeatCount(ValueAnimator.INFINITE);//无限大
                valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
                valueAnimator.start();
            }
        });

        //AnimationSet
        final Button btnAnimationSet = (Button) findViewById(R.id.btn_object_set);
        btnAnimationSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AnimationSet animationSet = new AnimationSet(true);


            }
        });

        Button btnAnimationSetXml = (Button) findViewById(R.id.btn_object_set_xml);
        btnAnimationSetXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // AnimationSet set = (AnimationSet) AnimatorInflater.loadAnimator(getApplicationContext(),R.animator.property_animator);

            }
        });


        /**
         * 解决动画属性abc无效果，
         * 1.对象必须提供setabc和getabc方法（如果这条不满足，程序直接crash）
         * 2.必须通过某种方法反应出来，比如ui变化（如果这条不满足，动画无效果，但不会crash）
         */
        final Button btnChange = (Button) findViewById(R.id.btn_change);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                //解决方法1：给对象加上get,set方法，如果有权限的话

                //解决方法2：用一个类来包装原始对象，间接提供get，set方法
//                ViewWrapper viewWrapper = new ViewWrapper(btnChange);
//                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(viewWrapper,"height",500);
//                objectAnimator.start();

                //解决方法3：采用ValueAnimation,监听动画过程，自己实现属性的改变。
                ValueAnimator valueAnimator = ValueAnimator.ofInt(0,100);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    //创建一个IntEvaluator对象，方便下面估值的时候使用
                    private IntEvaluator mEvaluator = new IntEvaluator();
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        //获得当前动画进度值，0-100之间
                        int currentValue = (int) animation.getAnimatedValue();

                        //获取当前进度占整个动画过程的比例，浮点型0-1之间
                        float fraction = animation.getAnimatedFraction();
                        //直接调用整型估值器，通过比例计算出高度，然后再设给button
                        btnChange.getLayoutParams().height = mEvaluator.evaluate(fraction,btnChange.getHeight(),500);
                        btnChange.requestLayout();
                    }
                });

                valueAnimator.start();

            }
        });



    }

    //包装Button的包装类
    private static class ViewWrapper{
       private View mTarget;
        public ViewWrapper(View target){
            mTarget = target;
        }

        //重写set方法
        public int getHeight(){
            return mTarget.getLayoutParams().height;
        }

        //重写set方法
        public void setHeight(int height){
            mTarget.getLayoutParams().height = height;
            mTarget.requestLayout();
        }
    }
}
