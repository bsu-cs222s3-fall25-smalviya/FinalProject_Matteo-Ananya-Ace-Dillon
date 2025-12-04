package cs.edu.bsu;

import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HorseRaceLogicTest {

    @BeforeEach
    void resetAll() throws Exception {
        HorseRaceLogic.resetRaceState();
        setStaticInt(HorseRaceLogic.class, "currentBet", 0);
        setStaticInt(HorseRaceLogic.class, "chosenHorseIndex", -1);
        setStaticInt(CoinBalance.class, "gameBalance", 0);
    }

    private static void setStaticInt(Class<?> cls, String field, int value) throws Exception {
        Field f = cls.getDeclaredField(field);
        f.setAccessible(true);
        f.setInt(null, value);
    }

    private static int getStaticInt(Class<?> cls, String field) throws Exception {
        Field f = cls.getDeclaredField(field);
        f.setAccessible(true);
        return f.getInt(null);
    }

    @SuppressWarnings("unchecked")
    private static List<Integer> getFinishedOrder() throws Exception {
        Field f = HorseRaceLogic.class.getDeclaredField("finishedOrder");
        f.setAccessible(true);
        return (List<Integer>) f.get(null);
    }

    private static void setFinishedOrder(List<Integer> order) throws Exception {
        Field f = HorseRaceLogic.class.getDeclaredField("finishedOrder");
        f.setAccessible(true);
        List<Integer> target = (List<Integer>) f.get(null);
        target.clear();
        target.addAll(order);
    }

    private static void setFinishedFlags(int numHorses) throws Exception {
        Field f = HorseRaceLogic.class.getDeclaredField("finished");
        f.setAccessible(true);
        boolean[] arr = (boolean[]) f.get(null);
        Arrays.fill(arr, false);
        for (int i = 0; i < numHorses; i++) arr[i] = true;
    }

    private static void setPlayerPlace(int place) throws Exception {
        setStaticInt(HorseRaceLogic.class, "playerPlace", place);
    }

    private static int getCoinsWon() throws Exception {
        return getStaticInt(HorseRaceLogic.class, "coinsWon");
    }

    private static void invokeApplyPayout() throws Exception {
        Method m = HorseRaceLogic.class.getDeclaredMethod("applyPayout");
        m.setAccessible(true);
        m.invoke(null);
    }

    @Test
    void numHorsesAndFinishDistance_areStable() {
        assertEquals(5, HorseRaceLogic.getNumHorses());
        assertEquals(600.0, HorseRaceLogic.getFinishDistance(), 1e-9);
    }

    @Test
    void getPositions_returnsCopyNotLiveView() throws Exception {
        Field posField = HorseRaceLogic.class.getDeclaredField("positions");
        posField.setAccessible(true);
        posField.set(null, new double[]{1, 2, 3, 4, 5});

        double[] snap1 = HorseRaceLogic.getPositions();
        assertArrayEquals(new double[]{1, 2, 3, 4, 5}, snap1, 1e-9);

        snap1[0] = 999;

        double[] snap2 = HorseRaceLogic.getPositions();
        assertEquals(1.0, snap2[0], 1e-9);
    }

    @Test
    void payout_firstPlace_creditsStakeTimesThree_andUpdatesBalance() throws Exception {
        HorseRaceLogic.setBet(100);
        HorseRaceLogic.setChosenHorse(2);
        setFinishedFlags(HorseRaceLogic.getNumHorses());
        setFinishedOrder(List.of(2, 0, 1, 3, 4));
        setPlayerPlace(1);

        invokeApplyPayout();

        assertEquals(300, getCoinsWon());
        assertEquals(300, CoinBalance.getGameBalance());
    }

    @Test
    void payout_secondPlace_creditsStakeTimesTwo_andUpdatesBalance() throws Exception {
        HorseRaceLogic.setBet(150);
        HorseRaceLogic.setChosenHorse(1);

        setFinishedFlags(HorseRaceLogic.getNumHorses());
        setFinishedOrder(List.of(0, 1, 2, 3, 4));
        setPlayerPlace(2);

        invokeApplyPayout();

        assertEquals(300, getCoinsWon()); // 2.0x
        assertEquals(300, CoinBalance.getGameBalance());
    }

    @Test
    void payout_thirdPlace_creditsStakeTimesOnePointFive_roundsProperly() throws Exception {
        HorseRaceLogic.setBet(101); // odd amount to verify rounding
        HorseRaceLogic.setChosenHorse(4);

        setFinishedFlags(HorseRaceLogic.getNumHorses());
        setFinishedOrder(List.of(0, 2, 4, 1, 3));
        setPlayerPlace(3);
        invokeApplyPayout();
        assertEquals(152, getCoinsWon());
        assertEquals(152, CoinBalance.getGameBalance());
    }

    @Test
    void payout_outsideTopThree_yieldsZero_andNoBalanceChange() throws Exception {
        HorseRaceLogic.setBet(200);
        HorseRaceLogic.setChosenHorse(0);

        setFinishedFlags(HorseRaceLogic.getNumHorses());
        setFinishedOrder(List.of(1, 2, 3, 0, 4));
        setPlayerPlace(4);

        invokeApplyPayout();

        assertEquals(0, getCoinsWon());
        assertEquals(0, CoinBalance.getGameBalance());
    }

    @Test
    void payout_ignoresWhenNoBetPlaced() throws Exception {
        HorseRaceLogic.setBet(0);
        HorseRaceLogic.setChosenHorse(3);

        setFinishedFlags(HorseRaceLogic.getNumHorses());
        setFinishedOrder(List.of(3, 1, 2, 0, 4));
        setPlayerPlace(1);

        invokeApplyPayout();

        assertEquals(0, getCoinsWon());
        assertEquals(0, CoinBalance.getGameBalance());
    }

    @Test
    void resultMessage_winIncludesCoinsAndSuffix() throws Exception {
        setStaticInt(HorseRaceLogic.class, "coinsWon", 250);
        setPlayerPlace(1);

        String msg = HorseRaceLogic.resultMessage();
        assertTrue(msg.contains("finished 1st"));
        assertTrue(msg.contains("You won 250 MAAD Coins!"));
    }

    @Test
    void resultMessage_lossIncludesStakeAndSuffix() throws Exception {
        HorseRaceLogic.setBet(120);
        setPlayerPlace(5);

        String msg = HorseRaceLogic.resultMessage();
        assertTrue(msg.contains("finished 5th"));
        assertTrue(msg.contains("You lost 120 MAAD Coins."));
    }

    @Test
    void resultMessage_beforePlacementIsGeneric() throws Exception {
        setPlayerPlace(-1);
        String msg = HorseRaceLogic.resultMessage();
        assertEquals("Race finished.", msg);
    }

    @Test
    void finishOrderString_matchesCurrentFormat_withIndexPlusSuffix() throws Exception {
        setFinishedOrder(new ArrayList<>(List.of(0, 2, 4))); // Horse #1, #3, #5

        String s = HorseRaceLogic.finishOrderString();
        String[] lines = s.split("\\R");
        assertEquals(3, lines.length);

        assertEquals("11st: Horse 1", lines[0]);
        assertEquals("22nd: Horse 3", lines[1]);
        assertEquals("33rd: Horse 5", lines[2]);
    }

    @Test
    void fullRace_completesAndSetsPlaceAndBalanceConsistently() {
        HorseRaceLogic.setBet(100);
        HorseRaceLogic.setChosenHorse(0);

        boolean finished = false;
        for (int i = 0; i < 10_000; i++) {
            if (HorseRaceLogic.stepRace()) {
                finished = true;
                break;
            }
        }
        assertTrue(finished, "Race should finish in reasonable steps.");

        int place;
        try {
            place = getStaticInt(HorseRaceLogic.class, "playerPlace");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertTrue(place >= 1 && place <= HorseRaceLogic.getNumHorses(), "Place must be within [1, numHorses]");

        int coins;
        try {
            coins = getCoinsWon();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        long bal = CoinBalance.getGameBalance();

        if (place <= 3) {
            assertTrue(coins > 0);
            assertEquals(coins, bal);
        } else {
            assertEquals(0, coins);
            assertEquals(0, bal);
        }
    }
}
