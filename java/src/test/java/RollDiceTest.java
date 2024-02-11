import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class RollDiceTest {

    @Nested
    class General_roll_dice_uses_cases {
        @Test
        public void five_dices_rolled(){
            new RollDice(1,1,1,1,1);
        }

        @Test
        public void error_when_not_five_dices_rolled(){
            String message = assertThrows(IllegalArgumentException.class, () -> {
                new RollDice(1, 1, 1, 1);
            }).getMessage();

            assertEquals("five_dices_not_provided", message);
        }

        @Test
        public void error_when_at_least_one_dice_value_is_not(){
            String message = assertThrows(IllegalArgumentException.class, () -> {
                new RollDice(1, 1, 1, 1, 7);
            }).getMessage();

            assertEquals("dice_value_out_of_bounds", message);
        }

        @Test
        public void public_usage_with_forced_dice_values(){
            assertEquals(15, new RollDice(
                    Dice.fromValue(1),
                    Dice.fromValue(2),
                    Dice.fromValue(3),
                    Dice.fromValue(4),
                    Dice.fromValue(5)).smallStraight());
        }
    }


    @Nested
    class Chance_uses_cases {
        @ParameterizedTest
        @CsvSource({
                "15, 2; 3; 4; 5; 1",
                "16, 3; 3; 4; 5; 1"
        })
        public void all_dices_are_summed(int expectedScore, @ConvertWith(DiceConverter.class) int[] dices) {
             assertEquals(expectedScore, new RollDice(dices).chance());
        }
    }

    @Nested
    class Yatzy_uses_cases {
        @ParameterizedTest
        @CsvSource({
                "4; 4; 4; 4; 4",
                "6; 6; 6; 6; 6"
        })
        public void yatzy_scores_50_if_all_values_equals(@ConvertWith(DiceConverter.class) int[] dices) {
            assertEquals(RollDice.YATZY_SCORE, new RollDice(dices).yatzy());
        }

        @Test
        public void yatzy_scores_0_if_not_all_values_equals() {
            assertEquals(0, new RollDice(6, 6, 6, 6, 3).yatzy());
        }
    }


    @Nested
    class Single_values_uses_cases {
        @ParameterizedTest
        @CsvSource({
                "1, 1; 2; 3; 4; 5",
                "2, 1; 2; 1; 4; 5",
                "0, 6; 2; 2; 4; 5",
                "4, 1; 2; 1; 1; 1"
        })
        public void ones(int expectedScore, @ConvertWith(DiceConverter.class) int[] dices) {
            assertEquals(expectedScore, new RollDice(dices).ones());
        }

        @ParameterizedTest
        @CsvSource({
                "4, 1; 2; 3; 2; 6",
                "10, 2; 2; 2; 2; 2",
        })
        public void twos(int expectedScore, @ConvertWith(DiceConverter.class) int[] dices) {
            assertEquals(expectedScore, new RollDice(dices).twos());
        }


        @ParameterizedTest
        @CsvSource({
                "6, 1; 2; 3; 2; 3",
                "12, 2; 3; 3; 3; 3",
        })
        public void threes(int expectedScore, @ConvertWith(DiceConverter.class) int[] dices) {
            assertEquals(expectedScore, new RollDice(dices).threes());
        }

        @ParameterizedTest
        @CsvSource({
                "12, 4; 4; 4; 5; 5",
                "8, 4; 4; 5; 5; 5",
                "4, 4; 5; 5; 5; 5",
        })
        public void fours(int expectedScore, @ConvertWith(DiceConverter.class) int[] dices) {
            assertEquals(expectedScore, new RollDice(dices).fours());
        }

        @ParameterizedTest
        @CsvSource({
                "10, 4; 4; 4; 5; 5",
                "15, 4; 4; 5; 5; 5",
                "20, 4; 5; 5; 5; 5",
        })
        public void fives(int expectedScore, @ConvertWith(DiceConverter.class) int[] dices) {
            assertEquals(expectedScore, new RollDice(dices).fives());
        }

        @ParameterizedTest
        @CsvSource({
                "0, 4; 4; 4; 5; 5",
                "6, 4; 4; 6; 5; 5",
                "18, 6; 5; 6; 6; 5",
        })
        public void sixes(int expectedScore, @ConvertWith(DiceConverter.class) int[] dices) {
            assertEquals(expectedScore, new RollDice(dices).sixes());
        }
    }

    @Nested
    class Same_of_a_kind_uses_cases {
        @ParameterizedTest
        @CsvSource({
                "6, 3; 4; 3; 5; 6",
                "10, 5; 3; 3; 3; 5",
                "12, 5; 3; 6; 6; 5",
        })
        public void pair(int expectedScore, @ConvertWith(DiceConverter.class) int[] dices) {
            assertEquals(expectedScore, new RollDice(dices).pair());
        }


        @ParameterizedTest
        @CsvSource({
                "9, 3; 3; 3; 4; 5",
                "15, 5; 3; 5; 4; 5",
                "9, 3; 3; 3; 3; 5",
                "9, 3; 3; 3; 3; 3",
        })
        public void triple(int expectedScore, @ConvertWith(DiceConverter.class) int[] dices) {
            assertEquals(expectedScore, new RollDice(dices).threeOfAKind());
        }

        @ParameterizedTest
        @CsvSource({
                "12, 3; 3; 3; 3; 5",
                "20, 5; 5; 5; 4; 5",
        })
        public void quadruple(int expectedScore, @ConvertWith(DiceConverter.class) int[] dices) {
            assertEquals(expectedScore, new RollDice(dices).fourOfAKind());
        }

        @Nested
        class Combinations_uses_cases {
            @ParameterizedTest
            @CsvSource({
                    "16, 3; 3; 5; 4; 5",
                    "16, 3; 3; 5; 5; 5",
            })
            public void double_pair(int expectedScore, @ConvertWith(DiceConverter.class) int[] dices) {
                assertEquals(expectedScore, new RollDice(dices).twoPairs());
            }

            @ParameterizedTest
            @CsvSource({
                    "18, 6; 2; 2; 2; 6",
                    "0, 2; 3; 4; 5; 6",
            })
            public void full_house(int expectedScore, @ConvertWith(DiceConverter.class) int[] dices) {
                assertEquals(expectedScore, new RollDice(dices).fullHouse());
            }
        }
    }


    @Nested
    class Straight_uses_cases {
        @ParameterizedTest
        @CsvSource({
                "15, 1; 2; 3; 4; 5",
                "15, 2; 3; 4; 5; 1",
                "0, 1; 2; 2; 4; 5",
        })
        public void small_straight(int expectedScore, @ConvertWith(DiceConverter.class) int[] dices) {
            assertEquals(expectedScore, new RollDice(dices).smallStraight());
        }

        @ParameterizedTest
        @CsvSource({
                "20, 6; 2; 3; 4; 5",
                "20, 2; 3; 4; 5; 6",
                "0, 1; 2; 2; 4; 5",
        })
        public void large_straight(int expectedScore, @ConvertWith(DiceConverter.class) int[] dices) {
            assertEquals(expectedScore, new RollDice(dices).largeStraight());
        }
    }


}
