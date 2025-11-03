package cs.edu.bsu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HorseRaceTest {

    private void resetAll() {
        CoinBalance.balance = 0;
        HorseRaceLogic.setBet(0);
        HorseRaceLogic.setChosenHorse(0);
        HorseRaceLogic.resetRaceState();
    }

    @Test
    void testReset() {
        resetAll();

        CoinBalance.balance = 999;
        HorseRaceLogic.positions[0] = 123.45;
        HorseRaceLogic.finished[0] = true;
        HorseRaceLogic.finishedOrder.add(2);
        HorseRaceLogic.coinsWon = 50;
        HorseRaceLogic.playerPlace = 3;
        HorseRaceLogic.resetRaceState();

        for (double pos : HorseRaceLogic.positions) {
            assertEquals(0.0, pos, 0.0001);
        }
        for (boolean f : HorseRaceLogic.finished) {
            assertFalse(f);
        }
        assertTrue(HorseRaceLogic.finishedOrder.isEmpty());
        assertEquals(0, HorseRaceLogic.coinsWon);
        assertEquals(-1, HorseRaceLogic.playerPlace);
    }

    @Test
    void testPastFinish() {
        resetAll();
        for (int i = 0; i < HorseRaceLogic.getNumHorses(); i++) {
            HorseRaceLogic.positions[i] = HorseRaceLogic.getFinishDistance() + 5;
        }

        boolean finished = HorseRaceLogic.stepRace();
        assertTrue(finished, "Race should finish in this step");
        for (boolean f : HorseRaceLogic.finished) {
            assertTrue(f);
        }
        assertEquals(HorseRaceLogic.getNumHorses(), HorseRaceLogic.finishedOrder.size());
    }

    @Test
    void testFirstGets3xBet() {
        resetAll();
        CoinBalance.balance = 1000;
        int bet = 200;

        HorseRaceLogic.setBet(bet);
        HorseRaceLogic.setChosenHorse(0);

        CoinBalance.balance -= bet;
        for (int i = 0; i < HorseRaceLogic.getNumHorses(); i++) {
            HorseRaceLogic.positions[i] = HorseRaceLogic.getFinishDistance() + 10;
        }
        boolean finished = HorseRaceLogic.stepRace();
        assertTrue(finished);

        assertEquals(1400, CoinBalance.balance);
        assertEquals(600, HorseRaceLogic.coinsWon);

        String msg = HorseRaceLogic.resultMessage();
        assertTrue(msg.contains("1st"));
        assertTrue(msg.contains("600"));
    }

    @Test
    void testSecondGets2xBet() {
        resetAll();

        CoinBalance.balance = 1000;
        int bet = 200;

        HorseRaceLogic.setBet(bet);
        HorseRaceLogic.setChosenHorse(1);

        CoinBalance.balance -= bet;

        for (int i = 0; i < HorseRaceLogic.getNumHorses(); i++) {
            HorseRaceLogic.positions[i] = HorseRaceLogic.getFinishDistance() + 10;
        }

        boolean finished = HorseRaceLogic.stepRace();
        assertTrue(finished);

        assertEquals(1200, CoinBalance.balance);
        assertEquals(400, HorseRaceLogic.coinsWon);

        String msg = HorseRaceLogic.resultMessage();
        assertTrue(msg.contains("2nd"));
        assertTrue(msg.contains("400"));
    }

    @Test
    void testThirdGets1HalfxBet() {
        resetAll();

        CoinBalance.balance = 1000;
        int bet = 200;
        HorseRaceLogic.setBet(bet);
        HorseRaceLogic.setChosenHorse(2);
        CoinBalance.balance -= bet;
        for (int i = 0; i < HorseRaceLogic.getNumHorses(); i++) {
            HorseRaceLogic.positions[i] = HorseRaceLogic.getFinishDistance() + 10;
        }
        boolean finished = HorseRaceLogic.stepRace();
        assertTrue(finished);
        assertEquals(1100, CoinBalance.balance);
        assertEquals(300, HorseRaceLogic.coinsWon);

        String msg = HorseRaceLogic.resultMessage();
        assertTrue(msg.contains("3rd"));
        assertTrue(msg.contains("300"));
    }

    @Test
    void testAllLosing() {
        resetAll();

        CoinBalance.balance = 1000;
        int bet = 200;

        HorseRaceLogic.setBet(bet);
        HorseRaceLogic.setChosenHorse(3);

        CoinBalance.balance -= bet;

        for (int i = 0; i < HorseRaceLogic.getNumHorses(); i++) {
            HorseRaceLogic.positions[i] = HorseRaceLogic.getFinishDistance() + 10;
        }
        boolean finished = HorseRaceLogic.stepRace();
        assertTrue(finished);
        assertEquals(800, CoinBalance.balance);
        assertEquals(0, HorseRaceLogic.coinsWon);
        String msg = HorseRaceLogic.resultMessage();
        assertTrue(msg.contains("lost") || msg.contains("lost " + bet));
    }

    @Test
    void testIfUpdatedBalanceMatchBalance() {
        resetAll();
        CoinBalance.balance = 4242;
        long result = HorseRaceLogic.getUpdatedBalance();
        assertEquals(4242, result);
    }

    @Test
    void testForAllPlaces() {
        resetAll();
        for (int i = 0; i < HorseRaceLogic.getNumHorses(); i++) {
            HorseRaceLogic.positions[i] = HorseRaceLogic.getFinishDistance() + 10;
        }
        HorseRaceLogic.setBet(0);
        HorseRaceLogic.setChosenHorse(0);

        boolean finished = HorseRaceLogic.stepRace();
        assertTrue(finished);
        String order = HorseRaceLogic.finishOrderString();
        assertTrue(order.contains("1st: Horse 1"));
        assertTrue(order.contains("2nd: Horse 2"));
        assertTrue(order.contains("3rd: Horse 3"));
        assertTrue(order.contains("4th: Horse 4"));
        assertTrue(order.contains("5th: Horse 5"));
    }

    @Test
    void testMessageForFirstAndCoins() {
        resetAll();
        CoinBalance.balance = 1000;
        int bet = 100;
        HorseRaceLogic.setBet(bet);
        HorseRaceLogic.setChosenHorse(0);
        CoinBalance.balance -= bet;

        for (int i = 0; i < HorseRaceLogic.getNumHorses(); i++) {
            HorseRaceLogic.positions[i] = HorseRaceLogic.getFinishDistance() + 10;
        }
        HorseRaceLogic.stepRace();
        String msg = HorseRaceLogic.resultMessage();
        assertTrue(msg.contains("1st"));
        assertTrue(msg.contains("300"));
    }

    @Test
    void testMessageForThirdAndCoins() {
        resetAll();
        CoinBalance.balance = 500;
        int bet = 100;
        HorseRaceLogic.setBet(bet);
        HorseRaceLogic.setChosenHorse(2);
        CoinBalance.balance -= bet;
        for (int i = 0; i < HorseRaceLogic.getNumHorses(); i++) {
            HorseRaceLogic.positions[i] = HorseRaceLogic.getFinishDistance() + 10;
        }
        HorseRaceLogic.stepRace();
        String msg = HorseRaceLogic.resultMessage();
        assertTrue(msg.contains("3rd"));
        assertTrue(msg.contains("150"));
    }

    @Test
    void testMessageForLoss() {
        resetAll();
        CoinBalance.balance = 500;
        int bet = 50;
        HorseRaceLogic.setBet(bet);
        HorseRaceLogic.setChosenHorse(4);
        CoinBalance.balance -= bet;

        for (int i = 0; i < HorseRaceLogic.getNumHorses(); i++) {
            HorseRaceLogic.positions[i] = HorseRaceLogic.getFinishDistance() + 10;
        }
        HorseRaceLogic.stepRace();
        String msg = HorseRaceLogic.resultMessage();
        assertTrue(msg.contains("lost"));
    }

    @Test
    void testNoPayoutWhenBetIsZero() {
        resetAll();
        CoinBalance.balance = 1000;
        int bet = 0;
        HorseRaceLogic.setBet(bet);
        HorseRaceLogic.setChosenHorse(0);
        CoinBalance.balance -= bet;

        for (int i = 0; i < HorseRaceLogic.getNumHorses(); i++) {
            HorseRaceLogic.positions[i] = HorseRaceLogic.getFinishDistance() + 10;
        }
        HorseRaceLogic.stepRace();
        assertEquals(1000, CoinBalance.balance);
        assertEquals(0, HorseRaceLogic.coinsWon);
    }
    @Test
    void testResultMessageBeforeAnyRace() {
        // no race run yet: playerPlace should be -1
        resetAll();
        CoinBalance.balance = 1000;

        String msg = HorseRaceLogic.resultMessage();
        assertEquals("Race finished.", msg);
    }

    @Test
    void testUnfinishWhenPositionsBelowFinish() {
        resetAll();

        CoinBalance.balance = 1000;
        int bet = 200;

        HorseRaceLogic.setBet(bet);
        HorseRaceLogic.setChosenHorse(0);
        CoinBalance.balance -= bet; // 800

        for (int i = 0; i < HorseRaceLogic.getNumHorses(); i++) {
            HorseRaceLogic.positions[i] = HorseRaceLogic.getFinishDistance() / 2.0;
        }

        boolean finished = HorseRaceLogic.stepRace();
        assertFalse(finished, "Race should not finish when horses are below finish line");
        assertTrue(HorseRaceLogic.finishedOrder.isEmpty());
        assertEquals(800, CoinBalance.balance);
    }

    @Test
    void testStepRaceSkipsAlreadyFinishedHorse() {
        resetAll();

        HorseRaceLogic.positions[0] = HorseRaceLogic.getFinishDistance();
        HorseRaceLogic.finished[0] = true;
        double before = HorseRaceLogic.positions[0];

        for (int i = 1; i < HorseRaceLogic.getNumHorses(); i++) {
            HorseRaceLogic.positions[i] = 10.0;
        }
        HorseRaceLogic.setBet(0);
        HorseRaceLogic.setChosenHorse(0);
        HorseRaceLogic.stepRace();
        assertEquals(before, HorseRaceLogic.positions[0], 0.0001);
    }

}
