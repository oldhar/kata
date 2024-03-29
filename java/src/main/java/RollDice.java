import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RollDice {
    public static int YATZY_SCORE = 50;
    public static int NOT_MATCHING_COMBINATION_SCORE = 0;

    protected int[] dices;

    public RollDice(Dice dice1, Dice dice2, Dice dice3, Dice dice4, Dice dice5) {
        this.dices = new int[]{
                dice1.getValue(),
                dice2.getValue(),
                dice3.getValue(),
                dice4.getValue(),
                dice5.getValue()
        };
    }

    protected RollDice(int... dices) {
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
        if (findSameOfAKind(dices, 5) != NOT_MATCHING_COMBINATION_SCORE) {
            return YATZY_SCORE;
        } else {
            return NOT_MATCHING_COMBINATION_SCORE;
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
        return findSameOfAKind(dices, 2);
    }

    public int threeOfAKind() {
        return findSameOfAKind(dices, 3);
    }

    public int fourOfAKind() {
        return findSameOfAKind(dices, 4);
    }

    private int findSameOfAKind(int[] dices, int nbOfDices) {
        Optional<Integer> biggestDiceValue = findBiggestDiceValue(dices, nbOfDices);

        if (biggestDiceValue.isPresent()) {
            return calculateDices(nbOfDices, biggestDiceValue.get());
        } else {
            return NOT_MATCHING_COMBINATION_SCORE;
        }
    }

    private int calculateDices(int nbOfDices, int biggestDiceValue) {
        return Long.valueOf(nbOfDices * biggestDiceValue)
                .intValue();
    }

    private static Optional<Integer> findBiggestDiceValue(int[] dices, int nbOfDices) {
        Optional<Integer> biggestDiceValue = countDicesPerValue(dices)
                .entrySet().stream()
                .filter(entry -> entry.getValue() >= nbOfDices)
                .map(Entry::getKey)
                .max(Comparator.naturalOrder());
        return biggestDiceValue;
    }

    private static Map<Integer, Long> countDicesPerValue(int[] dices) {
        return Arrays.stream(dices)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public int twoPairs() {
        Optional<Integer> biggestDiceValue = findBiggestDiceValue(dices, 2);
        if (biggestDiceValue.isPresent()) {
            List<Integer> dicesWithoutFirstPair = removeNbOfDicesForValue(dices, biggestDiceValue.get(), 2);
            Optional<Integer> secondPairBiggestValue = findBiggestDiceValue(dicesWithoutFirstPair.stream().mapToInt(i -> i).toArray(), 2);
            /* TODO: The 2nd condition matchs spec but to be discussed */
            if (secondPairBiggestValue.isPresent() && secondPairBiggestValue.get() != biggestDiceValue.get()) {
                return calculateDices(2, biggestDiceValue.get()) + calculateDices(2, secondPairBiggestValue.get());
            } else {
                return NOT_MATCHING_COMBINATION_SCORE;
            }
        } else {
            return NOT_MATCHING_COMBINATION_SCORE;
        }
    }

    private List<Integer> removeNbOfDicesForValue(int[] dices, Integer diceValueToRemove, int nbOfDices) {
        List<Integer> dicesWithoutFirstPair = Arrays.stream(dices).mapToObj(dice -> Integer.valueOf(dice)).collect(Collectors.toList());
        for (int i = 0; i < nbOfDices; i++) {
            dicesWithoutFirstPair.remove(diceValueToRemove);
        }
        return dicesWithoutFirstPair;
    }


    public int fullHouse() {
        Optional<Integer> biggestDiceValue = findBiggestDiceValue(dices, 3);
        if (biggestDiceValue.isPresent()) {
            List<Integer> dicesWithoutFirstPair = removeNbOfDicesForValue(dices, biggestDiceValue.get(), 3);
            Optional<Integer> secondPairBiggestValue = findBiggestDiceValue(dicesWithoutFirstPair.stream().mapToInt(i -> i).toArray(), 2);
            /* TODO: The 2nd condition matchs spec but to be discussed */
            if (secondPairBiggestValue.isPresent() && secondPairBiggestValue.get()!= biggestDiceValue.get()) {
                return calculateDices(3, biggestDiceValue.get()) + calculateDices(2, secondPairBiggestValue.get());
            } else {
                return NOT_MATCHING_COMBINATION_SCORE;
            }
        } else {
            return NOT_MATCHING_COMBINATION_SCORE;
        }
    }

    public int smallStraight() {
        Predicate<int[]> smallStraightPredicate = (dices) -> dices[0] == 1;
        return calculateStraight(dices, smallStraightPredicate);
    }

    public int largeStraight() {
        Predicate<int[]> largeStraightPredicate = (dices) -> dices[4] == 6;
        return calculateStraight(dices, largeStraightPredicate);
    }

    private int calculateStraight(int[] dices, Predicate<int[]> straightPredicate) {
        boolean existsOnlyOneValuePerDice = existsOnlyOneValuePerDice(dices);
        Arrays.sort(dices);
        boolean diceStraightConstraintIsVerified = straightPredicate.test(dices);
        if (existsOnlyOneValuePerDice && diceStraightConstraintIsVerified) {
            return Arrays.stream(dices).sum();
        } else {
            return NOT_MATCHING_COMBINATION_SCORE;
        }
    }

    private boolean existsOnlyOneValuePerDice(int[] dices) {
        return countDicesPerValue(dices).values().stream().allMatch(val -> val == 1);
    }


}



