package androidsamples.java.dicegames;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

public class WalletActivity extends AppCompatActivity {
    private TextView mTxtBalance;
    private Button mBtnDie;
    private TextView mTxtSingleSixes;
    private TextView mTxtTotalRolls;
    private TextView mTxtDoubleSixes;
    private TextView mTxtDoubleOthers;
    private TextView mTxtPrevRoll;
    private WalletViewModel mWalletVM;
    private DropDownViewModel DDVM;
    private ImageView settings;
    private Spinner spinner;
    private static final String TAG="WalletActivity";
    private final int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
    private LinearLayout mainBody;
    private boolean isFirstSelectionGesture = true;
    private boolean isFirstSelectionAction = true;
    private OnTwoFingerSwipeListener swipeListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wallet);

    mainBody = findViewById(R.id.body);
    settings = findViewById(R.id.settings_button);
    spinner = findViewById(R.id.drop_down_menu);
    mTxtBalance = findViewById(R.id.txt_balance);
    mBtnDie = findViewById(R.id.btn_die);
    mTxtSingleSixes = findViewById(R.id.txt_single_sixes);
    mTxtTotalRolls = findViewById(R.id.txt_total_rolls);
    mTxtDoubleSixes = findViewById(R.id.txt_double_sixes);
    mTxtDoubleOthers = findViewById(R.id.txt_double_others);
    mTxtPrevRoll = findViewById(R.id.txt_prev_roll);



    mWalletVM = new ViewModelProvider(this).get(WalletViewModel.class);
    DDVM = new ViewModelProvider(this).get(DropDownViewModel.class);
    updateUI();

      swipeListener = new OnTwoFingerSwipeListener() {
          @Override
          public void onTwoFingerSwipeLeft() {
              Log.d(TAG, "Swiped Left---------------------------------------------");
              // If active then do something
              // If swipe is assigned to background color
              Log.d(TAG, ""+DDVM.getAction(0));
              swipe(0, 1);
          }

          @Override
          public void onTwoFingerSwipeRight() {
              // Handle right swipe
              // If swipe is assigned to background color
              Log.d(TAG, "Swiped Right---------------------------------------------");
              swipe(0, -1);
          }
          @Override
          public void onThreeFingerSwipeLeft(){
              swipe(1, 1);
          }
          @Override
          public void onThreeFingerSwipeRight(){
              swipe(1, -1);
          }

          public void swipe(int gesture, int inc){
              if(DDVM.getAction(gesture) == 0){
                  DDVM.changeBackgroundColorIndex(inc);
                  changeColor();
              }
              //If swipe is assigned to die color
              else if(DDVM.getAction(gesture) == 1){
                  Log.d(TAG, "Change Die color");
                  DDVM.changeDieColorIndex(inc);
                  changeColor();
              }
          }
      };

      mainBody.setOnTouchListener(new OnSwipeTouchListener(swipeListener));

      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
              if(DDVM.getSpinnerState() == 1 && isFirstSelectionGesture){
                  isFirstSelectionGesture = false;
                  return;
              }
              if(DDVM.getSpinnerState() == 2 && isFirstSelectionAction){
                  isFirstSelectionAction = false;
                  return;
              }
              DDVM.setIndex(position);
              DDVM.changeSpinnerState();
              Log.d(TAG, "State changed to: " + DDVM.getSpinnerState());
              displayDropDown();
          }
          @Override
          public void onNothingSelected(AdapterView<?> parent){

          }
      });

    settings.setOnClickListener(v -> {
        if(DDVM.getSpinnerState() != 0){
            spinner.setVisibility(View.GONE);
            DDVM.resetState();
            isFirstSelectionGesture = true;
            isFirstSelectionAction = true;
        }
        else{
            DDVM.changeSpinnerState();
            Log.d(TAG, "State is " + DDVM.getSpinnerState());
            displayDropDown();
        }
    });

//      if(savedInstanceState != null){
//          mBalance = savedInstanceState.getInt(KEY_BALANCE, 0);
//          mSixes = savedInstanceState.getInt(KEY_SIXES, 0);
//          mPrev = savedInstanceState.getInt(KEY_PREV, 0);
//          mDoubleSixes = savedInstanceState.getInt(KEY_DOUBLESIXES, 0);
//          mDoubleOthers = savedInstanceState.getInt(KEY_DOUBLEOTHERS, 0);
//          mRolls = savedInstanceState.getInt(KEY_ROLLS, 0);
//          mDieValue = savedInstanceState.getInt(KEY_VALUE, 0);
//
//          updateUI();
//      }

    mBtnDie.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mWalletVM.rollDie();
        updateUI();
        if(mWalletVM.dieValue() == mWalletVM.WIN_VALUE){
            showToast("Congratulations!");
        }
      }
    });
  }

  private void updateUI(){
    mTxtBalance.setText(Integer.toString(mWalletVM.balance()));
    mBtnDie.setText(Integer.toString(mWalletVM.dieValue()));
    mTxtSingleSixes.setText(Integer.toString(mWalletVM.singleSixes()));
    mTxtTotalRolls.setText(Integer.toString(mWalletVM.totalRolls()));
    mTxtDoubleOthers.setText(Integer.toString(mWalletVM.doubleOthers()));
    mTxtDoubleSixes.setText(Integer.toString(mWalletVM.doubleSixes()));
    mTxtPrevRoll.setText(Integer.toString(mWalletVM.previousRoll()));
    displayDropDown();
    changeColor();
  }

  private void changeColor(){
      if(DDVM.getBackgroundColorIndex() != -1)mainBody.setBackgroundColor(colors[DDVM.getBackgroundColorIndex()]);
      Log.d(TAG, "Die Color index " + DDVM.getDieColorIndex());
      if(DDVM.getDieColorIndex() != -1) mBtnDie.setBackgroundColor(colors[DDVM.getDieColorIndex()]);
  }

  private void displayDropDown(){
      int state = DDVM.getSpinnerState();
      if(state == 0){
          spinner.setVisibility(View.GONE);
          DDVM.resetState();
          isFirstSelectionGesture = true;
          isFirstSelectionAction = true;
          return;
      }
      Log.d(TAG, "Found the state of " + state);
      String[] items = getResources().getStringArray(state == 1 ? R.array.gestures : R.array.actions);
      if(items.length == 0) return;
      ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinner.setAdapter(adapter);
      spinner.setVisibility(View.VISIBLE);
  }

  private void showToast(CharSequence text){
      int duration = Toast.LENGTH_SHORT;
      Toast toast = Toast.makeText(this, text, duration);
      toast.show();
  }

  @Override
//  protected void onSaveInstanceState(@NonNull Bundle outState){
//    super.onSaveInstanceState(outState);
//    Log.d(TAG, "outSaveInstanceState");
//    outState.putInt(KEY_BALANCE, mBalance);
//    outState.putInt(KEY_PREV, mPrev);
//    outState.putInt(KEY_DOUBLEOTHERS, mDoubleOthers);
//    outState.putInt(KEY_DOUBLESIXES, mDoubleSixes);
//    outState.putInt(KEY_ROLLS, mRolls);
//    outState.putInt(KEY_VALUE, mDieValue);
//    outState.putInt(KEY_SIXES, mSixes);
//  }

  protected void onStart(){
      super.onStart();
      Log.d(TAG, "onStart");
  }

  protected void onResume(){
      super.onResume();
      Log.d(TAG, "onResume");
  }

  protected void onPause(){
      super.onPause();
      Log.d(TAG, "onPause");
  }

  protected void onStop(){
      super.onStop();
      Log.d(TAG, "onStop");
  }

  protected void onDestroy(){
      super.onDestroy();
      Log.d(TAG, "onDestroy");
  }

}