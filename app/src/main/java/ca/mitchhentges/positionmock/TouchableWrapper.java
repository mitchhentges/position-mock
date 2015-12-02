package ca.mitchhentges.positionmock;

import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * @author cacti
 * @since 12/2/2015
 */
public class TouchableWrapper extends FrameLayout {

    private final PositionMockActivity activity;

    public TouchableWrapper(PositionMockActivity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if ((action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN
                && event.getPointerCount() == 3) {
            activity.applyMockLocation();
        }
        return super.onInterceptTouchEvent(event);
    }
}
