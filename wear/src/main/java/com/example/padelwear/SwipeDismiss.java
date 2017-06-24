package com.example.padelwear;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.SwipeDismissFrameLayout;

/**
 * Created by el70n on 24/06/2017.
 */

public class SwipeDismiss extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_dismiss);
        SwipeDismissFrameLayout root = (SwipeDismissFrameLayout) findViewById(R.id.swipe_dismiss_root);
        root.addCallback(new SwipeDismissFrameLayout.Callback() {
            @Override
            public void onDismissed(SwipeDismissFrameLayout layout) {
                SwipeDismiss.this.finish();
            }
        });
    }
}
