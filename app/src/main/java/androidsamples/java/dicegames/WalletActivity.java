package androidsamples.java.dicegames;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
    private static final String TAG="WalletActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wallet);

    mTxtBalance = findViewById(R.id.txt_balance);
    mBtnDie = findViewById(R.id.btn_die);
    mTxtSingleSixes = findViewById(R.id.txt_single_sixes);
    mTxtTotalRolls = findViewById(R.id.txt_total_rolls);
    mTxtDoubleSixes = findViewById(R.id.txt_double_sixes);
    mTxtDoubleOthers = findViewById(R.id.txt_double_others);
    mTxtPrevRoll = findViewById(R.id.txt_prev_roll);

    mWalletVM = new ViewModelProvider(this).get(WalletViewModel.class);
    updateUI();

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