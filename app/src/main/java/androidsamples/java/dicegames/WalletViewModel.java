package androidsamples.java.dicegames;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class WalletViewModel extends ViewModel {
  private static final String TAG="WalletViewModel";
  public static final int WIN_VALUE = 6;
  public static final int INCREMENT = 5;

  private int mBalance;
  private int mSixes;
  private int mRolls;
  private int mDoubleSixes;
  private int mDoubleOthers;
  private int mPrev;
  Die mDie;

//  private static final String KEY_BALANCE = "KEY_BALANCE";
//  private static final String KEY_SIXES = "KEY_SIXES";
//  private static final String KEY_ROLLS = "KEY_ROLLS";
//  private static final String KEY_DOUBLESIXES = "KEY_DOUBLESIXES";
//  private static final String KEY_DOUBLEOTHERS = "KEY_DOUBLEOTHERS";
//  private static final String KEY_PREV = "KEY_PREV";
  /**
   * The no argument constructor.
   */
  public WalletViewModel() {
    // TODO implement method
    mBalance = 0;
    mSixes = 0;
    mRolls = 0;
    mDoubleSixes = 0;
    mDoubleOthers = 0;
    mPrev = 0;
    mDie = new Die6();
  }

  /**
   * Reports the current balance.
   *
   */
  public int balance() {
    // TODO implement method
    return mBalance;
  }

  /**
   * Rolls the {@link Die} in the wallet and implements the changes accordingly.
   */
  public void rollDie() {
    // TODO implement method
    mDie.roll();
    mRolls++; Log.d(TAG, "Die roll = " + mDie.value() + " Rolls: " + mRolls);
    if(mDie.value() == WIN_VALUE){
      mBalance += INCREMENT;
      if(mPrev == WIN_VALUE) {
        mBalance += INCREMENT;
        mDoubleSixes++;
      }
      mSixes++; Log.d(TAG, "New Sixes = " + mSixes);
    }
    else if(mPrev == mDie.value()) {
      mBalance -= INCREMENT;
      mDoubleOthers++;
    }
    Log.d(TAG, "New Balance = " + mBalance);
    mPrev = mDie.value();
  }
  /**
   * Reports the current value of the {@link Die}.
   *
   */
  public int dieValue() {
    // TODO implement method
    return mDie.value();
  }

  /**
   * Reports the number of single (or first) sixes rolled so far.
   *
   */
  public int singleSixes() {
    // TODO implement method
    return mSixes;
  }

  /**
   * Reports the total number of dice rolls so far.
   *
   */
  public int totalRolls() {
    // TODO implement method
    return mRolls;
  }

  /**
   * Reports the number of times two sixes were rolled in a row.
   *
   */
  public int doubleSixes() {
    // TODO implement method
    return mDoubleSixes;
  }

  /**
   * Reports the number of times any other number was rolled twice in a row.
   *
   */
  public int doubleOthers() {
    // TODO implement method
    return mDoubleOthers;
  }

  /**
   * Reports the value of the die on the previous roll.
   *
   */
  public int previousRoll() {
    // TODO implement method
    return mPrev;
  }
}
