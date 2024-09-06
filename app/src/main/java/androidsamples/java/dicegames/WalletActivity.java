package androidsamples.java.dicegames;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class WalletActivity extends AppCompatActivity {
    private int mBalance;
    private int mSixes;
    private int mRolls;
    private int mDoubleSixes;
    private int mDoubleOthers;
    private int mPrev;
    private int mDieValue;
    private Die mDie;

    private static final String KEY_BALANCE = "KEY_BALANCE";
    private static final String KEY_SIXES = "KEY_SIXES";
    private static final String KEY_ROLLS = "KEY_ROLLS";
    private static final String KEY_DOUBLESIXES = "KEY_DOUBLESIXES";
    private static final String KEY_DOUBLEOTHERS = "KEY_DOUBLEOTHERS";
    private static final String KEY_PREV = "KEY_PREV";
    private static final String KEY_VALUE = "KEY_VALUE";
    private TextView mTxtBalance;
    private Button mBtnDie;
    private TextView mTxtSingleSixes;
    private TextView mTxtTotalRolls;
    private TextView mTxtDoubleSixes;
    private TextView mTxtDoubleOthers;
    private TextView mTxtPrevRoll;
    private static final String TAG="WalletActivity";
    private static final int WIN_VALUE = 6;
    private static final int INCREMENT = 5;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wallet);

    mDie = new Die6();
    mTxtBalance = findViewById(R.id.txt_balance);
    mBtnDie = findViewById(R.id.btn_die);
    mTxtSingleSixes = findViewById(R.id.txt_single_sixes);
    mTxtTotalRolls = findViewById(R.id.txt_total_rolls);
    mTxtDoubleSixes = findViewById(R.id.txt_double_sixes);
    mTxtDoubleOthers = findViewById(R.id.txt_double_others);
    mTxtPrevRoll = findViewById(R.id.txt_prev_roll);

      if(savedInstanceState != null){
          mBalance = savedInstanceState.getInt(KEY_BALANCE, 0);
          mSixes = savedInstanceState.getInt(KEY_SIXES, 0);
          mPrev = savedInstanceState.getInt(KEY_PREV, 0);
          mDoubleSixes = savedInstanceState.getInt(KEY_DOUBLESIXES, 0);
          mDoubleOthers = savedInstanceState.getInt(KEY_DOUBLEOTHERS, 0);
          mRolls = savedInstanceState.getInt(KEY_ROLLS, 0);
          mDieValue = savedInstanceState.getInt(KEY_VALUE, 0);

          updateUI();
      }

    mBtnDie.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mDie.roll();
        mDieValue = mDie.value();
        mRolls++; Log.d(TAG, "Die roll = " + mDieValue + " Rolls: " + mRolls);
        if(mDieValue == WIN_VALUE){
          mBalance += INCREMENT;
          if(mPrev == WIN_VALUE) {
              mBalance += INCREMENT;
              mDoubleSixes++;
          }
          mSixes++; Log.d(TAG, "New Sixes = " + mSixes);
          showToast();
        }
        else if(mPrev == mDieValue) {
            mBalance -= INCREMENT;
            mDoubleOthers++;
        }
        Log.d(TAG, "New Balance = " + mBalance);
        mPrev = mDieValue;
        updateUI();
      }
    });
  }

  private void updateUI(){
    mTxtBalance.setText(Integer.toString(mBalance));
    mBtnDie.setText(Integer.toString(mDieValue));
    mTxtSingleSixes.setText(Integer.toString(mSixes));
    mTxtTotalRolls.setText(Integer.toString(mRolls));
    mTxtDoubleOthers.setText(Integer.toString(mDoubleOthers));
    mTxtDoubleSixes.setText(Integer.toString(mDoubleSixes));
    mTxtPrevRoll.setText(Integer.toString(mPrev));

  }

  private void showToast(){
      CharSequence text = "Congratulations!";
      int duration = Toast.LENGTH_SHORT;
      Toast toast = Toast.makeText(this, text, duration);
      toast.show();
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState){
    super.onSaveInstanceState(outState);
    Log.d(TAG, "outSaveInstanceState");
    outState.putInt(KEY_BALANCE, mBalance);
    outState.putInt(KEY_PREV, mPrev);
    outState.putInt(KEY_DOUBLEOTHERS, mDoubleOthers);
    outState.putInt(KEY_DOUBLESIXES, mDoubleSixes);
    outState.putInt(KEY_ROLLS, mRolls);
    outState.putInt(KEY_VALUE, mDieValue);
    outState.putInt(KEY_SIXES, mSixes);
  }

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