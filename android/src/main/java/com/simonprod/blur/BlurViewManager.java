package com.simonprod.blur;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import android.view.View;
import android.widget.ImageView;

public class BlurViewManager extends SimpleViewManager<BlurView> {

    private static final String REACT_CLASS = "BlurView";
    private static ThemedReactContext context;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public BlurView createViewInstance(ThemedReactContext ctx) {
        context = ctx;

        BlurView blurView = new BlurView(ctx);
        blurView.setScaleType(ImageView.ScaleType.CENTER);
        return blurView;
    }

    @ReactProp(name = "parentId")
    public void setParentId(BlurView view, int id) {
        if (context != null && context.getCurrentActivity() != null) {
            View findView = context.getCurrentActivity().findViewById(id);

            if (findView != null) {
                view.setViewParent(findView);
            }
        }
    }

    @ReactProp(name = "wrapperId")
    public void setWrapperId(BlurView view, int id) {
        if (context != null && context.getCurrentActivity() != null) {
            View findView = context.getCurrentActivity().findViewById(id);

            if (findView != null) {
                view.setViewRef(findView);
            }
        }
    }

}
