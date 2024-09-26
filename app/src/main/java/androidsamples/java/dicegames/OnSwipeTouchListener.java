package androidsamples.java.dicegames;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeTouchListener implements View.OnTouchListener {
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private final OnTwoFingerSwipeListener listener;
    private float initialX1, initialY1;
    private float initialX2, initialY2;
    private float initialX3, initialY3;
    private boolean swipeInProgress = false; // State variable

    public OnSwipeTouchListener(OnTwoFingerSwipeListener listener) {
        this.listener = listener;
        Log.d("OnSwipeTouchListener", "Reached Constructor");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("OnSwipeTouchListener", "Pointer Count: " + event.getPointerCount());

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() >= 2) {
                    initialX1 = event.getX(0);
                    initialY1 = event.getY(0);
                    initialX2 = event.getX(1);
                    initialY2 = event.getY(1);

                    if (event.getPointerCount() == 3) {
                        initialX3 = event.getX(2);
                        initialY3 = event.getY(2);
                        Log.d("OnSwipeTouchListener", "Pointer down: 3 fingers detected!");
                    } else {
                        Log.d("OnSwipeTouchListener", "Pointer down: 2 fingers detected!");
                    }
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 2) {
                    Log.d("OnSwipeTouchListener", "2 fingers moving detected!");

                    float deltaX1 = event.getX(0) - initialX1;
                    float deltaY1 = event.getY(0) - initialY1;
                    float deltaX2 = event.getX(1) - initialX2;
                    float deltaY2 = event.getY(1) - initialY2;

                    float avgDeltaX = (deltaX1 + deltaX2) / 2;
                    float avgDeltaY = (deltaY1 + deltaY2) / 2;

                    if (!swipeInProgress) {
                        if (Math.abs(avgDeltaX) > SWIPE_THRESHOLD && Math.abs(avgDeltaY) < SWIPE_THRESHOLD) {
                            swipeInProgress = true; // Set swipe in progress
                            if (avgDeltaX > 0) {
                                listener.onTwoFingerSwipeRight(); // Two-finger swipe right
                            } else {
                                listener.onTwoFingerSwipeLeft(); // Two-finger swipe left
                            }
                            return true;
                        }
                    }
                } else if (event.getPointerCount() == 3) {
                    Log.d("OnSwipeTouchListener", "3 fingers moving detected!");

                    float deltaX1 = event.getX(0) - initialX1;
                    float deltaY1 = event.getY(0) - initialY1;
                    float deltaX2 = event.getX(1) - initialX2;
                    float deltaY2 = event.getY(1) - initialY2;
                    float deltaX3 = event.getX(2) - initialX3;
                    float deltaY3 = event.getY(2) - initialY3;

                    float avgDeltaX = (deltaX1 + deltaX2 + deltaX3) / 3;
                    float avgDeltaY = (deltaY1 + deltaY2 + deltaY3) / 3;

                    if (!swipeInProgress) {
                        if (Math.abs(avgDeltaX) > SWIPE_THRESHOLD && Math.abs(avgDeltaY) < SWIPE_THRESHOLD) {
                            swipeInProgress = true; // Set swipe in progress
                            if (avgDeltaX > 0) {
                                listener.onThreeFingerSwipeRight(); // Three-finger swipe right
                            } else {
                                listener.onThreeFingerSwipeLeft(); // Three-finger swipe left
                            }
                            return true;
                        }
                    }
                }
                return true;

            case MotionEvent.ACTION_POINTER_UP:
                Log.d("OnSwipeTouchListener", "Pointer up detected!");
                swipeInProgress = false; // Reset swipe progress state
                return true;

            case MotionEvent.ACTION_UP:
                initialX1 = initialY1 = initialX2 = initialY2 = 0;
                initialX3 = initialY3 = 0; // Reset the third finger positions
                swipeInProgress = false; // Reset the swipe in progress state
                return true;
        }

        Log.d("OnSwipeTouchListener", "No 2 or 3 fingers detected!");
        return false;
    }
}
