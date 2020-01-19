package com.simonprod.blur;

import com.facebook.react.uimanager.ThemedReactContext;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.view.View;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.RenderScript;
import android.renderscript.Allocation;
import android.renderscript.Type;
import android.renderscript.ScriptIntrinsicBlur;
import android.renderscript.Element;

public class BlurView extends ImageView {

    View viewParent;
    float radius = 25.0f;

    public BlurView(ThemedReactContext context) {
        this(context, null);
    }

    public BlurView(ThemedReactContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setViewParent(View viewRef) {
        viewParent = viewRef;
    }

    public void setViewRef(View viewRef) {
        Bitmap blurBitmap = getBitmapFromView(viewParent);
        blurBitmap = addBorderBitmap(blurBitmap, (int) (radius / 2.5));
        blurBitmap = addBlurBitmap(blurBitmap, radius, viewParent.getContext());

        setBackground(new BitmapDrawable(blurBitmap));
        viewRef.setVisibility(viewRef.GONE);
        viewRef.setEnabled(false);
    }

    public Bitmap getBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        v.draw(canvas);
        v.layout(
            v.getLeft(),
            v.getTop() - (int) (radius / 2.5),
            v.getRight(),
            v.getBottom()
        );
        return b;
    }

    private Bitmap addBorderBitmap(Bitmap bmp, int borderSize) {
        Bitmap bmpWithBorder = Bitmap.createBitmap(
            bmp.getWidth() + borderSize * 2,
            bmp.getHeight() + borderSize * 2,
            bmp.getConfig()
        );
        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.drawBitmap(bmp, borderSize, borderSize, null);
        return bmpWithBorder;
    }

    protected Bitmap addBlurBitmap(Bitmap bitmap, float radius, Context context) {
        RenderScript rs = RenderScript.create(context);

        Allocation allocation = Allocation.createFromBitmap(rs, bitmap);
        Type t = allocation.getType();

        Allocation blurredAllocation = Allocation.createTyped(rs, t);
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        blurScript.setRadius(radius);
        blurScript.setInput(allocation);
        blurScript.forEach(blurredAllocation);
        blurredAllocation.copyTo(bitmap);

        allocation.destroy();
        blurredAllocation.destroy();
        blurScript.destroy();
        rs.destroy();
        return bitmap;
    }

}
