package androidsamples.java.dicegames;

import android.util.Log;
import android.widget.Spinner;

import androidx.lifecycle.ViewModel;

import java.util.Arrays;

public class DropDownViewModel extends ViewModel {
    private final int GESTURE_SIZE = 3;
    private final int NUMOFSTATES = 3;
    private final int COLOR_SIZE = 4;
    private static final String TAG="SettingsViewModel";
    private int spinnerState = 0;
    private int gestureIndex = -1;
    private int actionIndex = -1;

    private int[] gesture_action;
    private int backgroundColorIndex = -1;
    private int dieColorIndex = -1;


    public DropDownViewModel(){
        gesture_action = new int[GESTURE_SIZE];
        Arrays.fill(gesture_action, -1);
    }

    public int getAction(int gesture){
        return gesture_action[gesture];
    }

    public void changeBackgroundColorIndex(int inc){
        backgroundColorIndex = (backgroundColorIndex + inc + COLOR_SIZE) % COLOR_SIZE;
    }

    public void changeDieColorIndex(int inc){
        dieColorIndex = (dieColorIndex + inc + COLOR_SIZE) % COLOR_SIZE;
    }

    public int getBackgroundColorIndex(){
        return backgroundColorIndex;
    }

    public int getDieColorIndex(){
        return dieColorIndex;
    }

    public int getSpinnerState(){
        return spinnerState;
    }

    public void changeSpinnerState(){
        spinnerState = (spinnerState + 1)%NUMOFSTATES;
    }

    public void setIndex(int val){
        if(spinnerState == 1){
            gestureIndex = val;
        }
        else if(spinnerState == 2){
            actionIndex = val;
            if(gestureIndex > 0){
                gesture_action[gestureIndex-1] = actionIndex-1;
            }
            Log.d(TAG, gestureIndex + " " + actionIndex + "=========" + gesture_action[gestureIndex-1]);
        }
    }

    public void resetState(){
        spinnerState = 0;
        gestureIndex = -1;
        actionIndex = -1;
    }
}
