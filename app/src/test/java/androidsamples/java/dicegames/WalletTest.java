package androidsamples.java.dicegames;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
public class WalletTest {

  private WalletViewModel viewModel;

  @Before
  public void setUp() {
    viewModel = new WalletViewModel();
  }

  @Test
  public void initialBalanceIsZero() {
    assertEquals(0, viewModel.balance());
  }

  @Test
  public void rollingSixIncreasesBalanceByFive() {
    // Mock the die to always return 6
    viewModel.mDie = new Die6() {
      @Override
      public void roll() {
        // Do nothing
      }

      @Override
      public int value() {
        return 6;
      }
    };

    viewModel.rollDie();
    assertEquals(5, viewModel.balance());
  }

  @Test
  public void rollingDoubleSixIncreasesBalanceByTen() {
    // Mock the die to always return 6
    viewModel.mDie = new Die6() {
      @Override
      public void roll() {
        // Do nothing
      }

      @Override
      public int value() {
        return 6;
      }
    };

    viewModel.rollDie();
    viewModel.rollDie();
    assertEquals(15, viewModel.balance());
  }

  @Test
  public void rollingDoubleNonSixDecreasesBalanceByFive() {
    // Mock the die to always return 3
    viewModel.mDie = new Die6() {
      @Override
      public void roll() {
        // Do nothing
      }

      @Override
      public int value() {
        return 3;
      }
    };

    viewModel.rollDie();
    viewModel.rollDie();
    assertEquals(-5, viewModel.balance());
  }

  @Test
  public void statisticsAreCorrectlyUpdated() {
    // Mock the die to return a sequence: 6, 6, 3, 3, 6
    viewModel.mDie = new Die6() {
      private final int[] sequence = {6, 6, 3, 3, 6};
      private int index = 0;

      @Override
      public void roll() {
        index = (index + 1) % sequence.length;
      }

      @Override
      public int value() {
        return sequence[index];
      }
    };

    for (int i = 0; i < 5; i++) {
      viewModel.rollDie();
    }

    assertEquals(5, viewModel.totalRolls());
    assertEquals(2, viewModel.singleSixes());
    assertEquals(1, viewModel.doubleSixes());
    assertEquals(1, viewModel.doubleOthers());
    assertEquals(6, viewModel.previousRoll());
    assertEquals(15, viewModel.balance());
  }

  @Test
  public void statisticsAreCorrectlyUpdated2(){
    //sequence : {2, 4, 3, 3, 6, 6}
    viewModel.mDie = new Die() {
      private final int[] sequence = {2,4,3,3,6,6};
      private int index = 0;

      @Override
      public void roll() {
        index = (index+1)% sequence.length;
      }

      @Override
      public int value() {
        return sequence[index];
      }
    };

    for(int i = 0; i<6; i++){
      viewModel.rollDie();
    }

    assertEquals(6, viewModel.totalRolls());
    assertEquals(1, viewModel.singleSixes());
    assertEquals(1, viewModel.doubleSixes());
    assertEquals(1, viewModel.doubleOthers());
    assertEquals(6, viewModel.previousRoll());
    assertEquals(10, viewModel.balance());
  }
}