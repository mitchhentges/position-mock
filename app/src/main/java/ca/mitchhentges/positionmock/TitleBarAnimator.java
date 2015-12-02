package ca.mitchhentges.positionmock;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

/**
 * @author cacti
 * @since 12/1/2015
 */
public class TitleBarAnimator {

    private final ActionBarActivity activity;

    public TitleBarAnimator(ActionBarActivity activity) {
        this.activity = activity;
    }

    public void animateTitleBar() {
        // Initial colors of each system bar.
        final int toolbarColor = activity.getResources().getColor(R.color.toolbar_color);
        final int toolbarToColor = activity.getResources().getColor(R.color.toolbar_to_color);

        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float position = animation.getAnimatedFraction();

                if (position > 0.5) {
                    position = Math.abs(position - 1);
                }

                int blended = blendColors(toolbarColor, toolbarToColor, position * 2);
                ColorDrawable background = new ColorDrawable(blended);
                activity.getSupportActionBar().setBackgroundDrawable(background);
            }
        });
        anim.setDuration(500).start();
    }

    private int blendColors(int from, int to, float ratio) {
        final float inverseRatio = 1f - ratio;

        final float r = Color.red(to) * ratio + Color.red(from) * inverseRatio;
        final float g = Color.green(to) * ratio + Color.green(from) * inverseRatio;
        final float b = Color.blue(to) * ratio + Color.blue(from) * inverseRatio;

        return Color.rgb((int) r, (int) g, (int) b);
    }
}
