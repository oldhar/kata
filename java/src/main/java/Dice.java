import java.util.Arrays;
import java.util.Optional;

public enum Dice {

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6);

    private int diceValue;

    Dice(int diceValue) {
        this.diceValue = diceValue;
    }

    public static Dice fromValue(int diceValue) {
        return Arrays.stream(Dice.values())
                .filter(dice -> dice.getValue() == diceValue)
                .findFirst()
                .orElseThrow(()->{throw new IllegalArgumentException("dice_value_out_of_bounds");});
    }

    public int getValue() {
        return diceValue;
    }
}
