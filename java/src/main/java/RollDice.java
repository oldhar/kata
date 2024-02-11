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
        if (findSameOfAKind(dices, 5)!=0) {
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

    private int allOfASingleValue(int kindValue) {
        return Arrays.stream(dices).filter(value -> value == kindValue).sum();
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
        Optional<Integer> biggestDiceValue = findBiggestDiceValue(dices, nbOfDices);


        if(biggestDiceValue.isPresent()){
            return calculateDices(nbOfDices, biggestDiceValue.get());
        } else {
            return 0;
        }
    }

    private int calculateDices(int nbOfDices, int biggestDiceValue) {
        return Long.valueOf(nbOfDices * biggestDiceValue)
                .intValue();
    }

    private static Optional<Integer> findBiggestDiceValue(int[] dices, int nbOfDices) {
        Optional<Integer> biggestDiceValue = Arrays.stream(dices)
                .mapToObj(value -> new SimpleEntry<Integer, Integer>(value, 1))
                .collect(Collectors.groupingBy(SimpleEntry::getKey, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() >= nbOfDices)
                .map(Entry::getKey)
                .max(Comparator.naturalOrder());
        return biggestDiceValue;
    }

    public int twoPairs() {
        Optional<Integer> biggestDiceValue = findBiggestDiceValue(dices, 2);
        if (biggestDiceValue.isPresent()){
            List<Integer> dicesWithoutFirstPair = removeNbOfDicesForValue(biggestDiceValue.get(), 2);
            Optional<Integer> secondPairBiggestValue = findBiggestDiceValue(dicesWithoutFirstPair.stream().mapToInt(i -> i).toArray(), 2);
            if(secondPairBiggestValue.isPresent()){
                return calculateDices(2, biggestDiceValue.get()) + calculateDices(2, secondPairBiggestValue.get());
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    private List<Integer> removeNbOfDicesForValue(Integer biggestDiceValue, int nbOfDices) {
        List<Integer> dicesWithoutFirstPair = Arrays.stream(dices).mapToObj(dice -> Integer.valueOf(dice)).collect(Collectors.toList());
        for(int i = 0; i< nbOfDices; i++){
            dicesWithoutFirstPair.remove(biggestDiceValue);
        }
        return dicesWithoutFirstPair;
    }


    public int fullHouse() {
        Optional<Integer> biggestDiceValue = findBiggestDiceValue(dices, 3);
        if (biggestDiceValue.isPresent()){
            List<Integer> dicesWithoutFirstPair = removeNbOfDicesForValue(biggestDiceValue.get(), 3);
            Optional<Integer> secondPairBiggestValue = findBiggestDiceValue(dicesWithoutFirstPair.stream().mapToInt(i -> i).toArray(), 2);
            if(secondPairBiggestValue.isPresent()){
                return calculateDices(3, biggestDiceValue.get()) + calculateDices(2, secondPairBiggestValue.get());
            } else {
                return 0;
            }
        } else {
            return 0;
        }
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


}



