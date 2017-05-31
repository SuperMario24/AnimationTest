# AnimationTest

1.Android动画：(1)View动画  (2)帧动画 (3)属性动画

2.View动画作用的是View影像，而不是View本身，支持四种动画效果：
(1)平移动画 TranslateAnimation
(2)缩放动画 ScaleAnimation
(3)旋转动画 RotateAnimation
(4)透明度动画 AlphaAnimation

3.<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="300"
    android:interpolator="@android:anim/accelerate_interpolator"  系统定义的插值器，线性匀速，加速等
    android:shareInterpolator="true" 子元素是否共享插值器
    >

    <alpha
        android:fromAlpha="float"
        android:toAlpha="float"
        />

    <scale
        android:fromXScale="float"
        android:toXScale="float"
        android:fromYScale="float"
        android:toYScale="float"
        android:pivotX="float"
        android:pivotY="float"
        ></scale>

    <translate
        android:fromXDelta="float"
        android:toXDelta="float"
        android:fromYDelta="float"
        android:toYDelta="float"/>

    <rotate
        android:fromDegrees="float"
        android:toDegrees="float"
        android:pivotX="float"
        android:pivotY="float"

        <set
            ..
            />
</set>

4.Animation 可以 setAnimationListener给View动画添加过程监听。

5.overridePendingTransition 必须位于startActivity或finish的后面调用，否则不起作用.

6.属性动画默认帧率10ms/帧。

7.属性动画比较常用的几个动画类：ValueAnimation,ObjectAnimation,AnimationSet

8.<set>标签的android:ordering="together" 属性对应together和sequentially 同时和按顺序播放，默认为together

9.repeatCount---动画循环次数，默认为0，-1为无限循环。


10.实现非匀速动画的重要手段：Interpolator(插值器),TypeEvaluator（估值器）:


