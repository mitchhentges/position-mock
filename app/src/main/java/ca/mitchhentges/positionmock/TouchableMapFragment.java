package ca.mitchhentges.positionmock;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.MapFragment;

/**
 * @author cacti
 * @since 12/2/2015
 */
public class TouchableMapFragment extends MapFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        TouchableWrapper touchableWrapper = new TouchableWrapper((PositionMockActivity) getActivity());
        touchableWrapper.addView(view);

        return touchableWrapper;
    }
}
