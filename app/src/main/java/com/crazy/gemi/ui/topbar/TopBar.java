package com.crazy.gemi.ui.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.crazy.gemi.R;

public class TopBar extends RelativeLayout {

    private Drawable draw_left;
    private Drawable draw_right;

    public TopBar(Context context) {
        this(context, null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        // 系统提供了 TypedArray 来获取自定义的属性集
        TypedArray typedArray = null;

        try {
            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TopBar,defStyleAttr,-1);

            draw_left = typedArray.getDrawable(R.styleable.TopBar_topbar_left_icon);
            draw_right = typedArray.getDrawable(R.styleable.TopBar_topbar_right_icon);
        } finally {
            // 获取完所有的属性值后要回收资源
            typedArray.recycle();
        }

        View view = View.inflate(getContext(), R.layout.topbar_layout, this);

        ImageView imgLeft = (ImageView)view.findViewById(R.id.topbar_left_img);
        ImageView imgRight = (ImageView)view.findViewById(R.id.topbar_right_img);

        imgLeft.setImageDrawable(draw_left);
        imgRight.setImageDrawable(draw_right);

//        // 如果不用布局文件的方法，当然还要实例化 ImageView
//        // 如 ImageView imgLeft = new ImageView(content);
//        // 为组件设置相应的布局元素(左边)
//        LayoutParams leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
//        // 添加到 ViewGroup
//        addView(imgLeft, leftParams);
//
//        // 为组件设置相应的布局元素（右边）
//        LayoutParams rightParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
//        // 添加到 ViewGroup
//        addView(imgRight,rightParams);
    }
}
