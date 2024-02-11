import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class RollDice {
    public static int YATZY_SCORE = 50;

    protected int[] dices;

    public RollDice(int... dices) {
        this.dices = dices;
        if (dices.length != 5) {
            throw new IllegalArgumentException("five_dices_not_provided");
        }
        if (Arrays.stream(dices).filter(diceValue -> diceValue < 1 || diceValue > 6).findFirst().isPresent()) {
            throw new IllegalArgumentException("dice_value_out_of_bounds");
        }
    }

    public int chance() {
        return Arrays.stream(dices).sum();
    }

    public int yatzy() {
        if (Arrays.stream(dices).allMatch(value -> value == dices[0])) {
            return YATZY_SCORE;
        } else {
            return 0;
        }
    }

    public int ones() {
        return allOfASingleValue(1);
    }

    public int twos() {
        return allOfASingleValue(2);
    }

    private int allOfASingleValue(int kindValue) {
        return Arrays.stream(dices).filter(value -> value == kindValue).sum();
    }


    public int threes() {
        return allOfASingleValue(3);
    }

    public int fours() {
        return allOfASingleValue(4);
    }

    public int fives() {
        return allOfASingleValue(5);
    }

    public int sixes() {
        return allOfASingleValue(6);
    }

    public int pair() {
        return findSameOfAKind(dices,2);
    }

    public int threeOfAKind() {
        return findSameOfAKind(dices,3);
    }

    public int fourOfAKind() {
        return findSameOfAKind(dices,4);
    }

    private int findSameOfAKind(int[] dices, int nbOfDices) {
        Optional<Integer> biggestDiceValue = Arrays.stream(dices)
                .mapToObj(value -> new SimpleEntry<Integer, Integer>(value, 1))
                .collect(Collectors.groupingBy(SimpleEntry::getKey, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() >= nbOfDices)
                .map(Entry::getKey)
                .max(Comparator.naturalOrder());


        if(biggestDiceValue.isPresent()){
            return Long.valueOf(nbOfDices * biggestDiceValue.get())
                    .intValue();
        } else {
            return 0;
        }
    }

    public static int two_pairs(int d1, int d2, int d3, int d4, int d5) {
        int[] counts = new int[6];
        counts[d1 - 1]++;
        counts[d2 - 1]++;
        counts[d3 - 1]++;
        counts[d4 - 1]++;
        counts[d5 - 1]++;
        int n = 0;
        int score = 0;
        for (int i = 0; i < 6; i += 1)
            if (counts[6 - i - 1] >= 2) {
                n++;
                score += (6 - i);
            }
        if (n == 2)
            return score * 2;
        else
            return 0;
    }

    public static int smallStraight(int d1, int d2, int d3, int d4, int d5) {
        int[] tallies;
        tallies = new int[6];
        tallies[d1 - 1] += 1;
        tallies[d2 - 1] += 1;
        tallies[d3 - 1] += 1;
        tallies[d4 - 1] += 1;
        tallies[d5 - 1] += 1;
        if (tallies[0] == 1 &&
                tallies[1] == 1 &&
                tallies[2] == 1 &&
                tallies[3] == 1 &&
                tallies[4] == 1)
            return 15;
        return 0;
    }

    public static int largeStraight(int d1, int d2, int d3, int d4, int d5) {
        int[] tallies;
        tallies = new int[6];
        tallies[d1 - 1] += 1;
        tallies[d2 - 1] += 1;
        tallies[d3 - 1] += 1;
        tallies[d4 - 1] += 1;
        tallies[d5 - 1] += 1;
        if (tallies[1] == 1 &&
                tallies[2] == 1 &&
                tallies[3] == 1 &&
                tallies[4] == 1
                && tallies[5] == 1)
            return 20;
        return 0;
    }

    public static int fullHouse(int d1, int d2, int d3, int d4, int d5) {
        int[] tallies;
        boolean _2 = false;
        int i;
        int _2_at = 0;
        boolean _3 = false;
        int _3_at = 0;


        tallies = new int[6];
        tallies[d1 - 1] += 1;
        tallies[d2 - 1] += 1;
        tallies[d3 - 1] += 1;
        tallies[d4 - 1] += 1;
        tallies[d5 - 1] += 1;

        for (i = 0; i != 6; i += 1)
            if (tallies[i] == 2) {
                _2 = true;
                _2_at = i + 1;
            }

        for (i = 0; i != 6; i += 1)
            if (tallies[i] == 3) {
                _3 = true;
                _3_at = i + 1;
            }

        if (_2 && _3)
            return _2_at * 2 + _3_at * 3;
        else
            return 0;
    }
}



