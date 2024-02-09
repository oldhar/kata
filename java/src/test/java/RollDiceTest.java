import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

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
        @Test
        public void two_of_them() {
            assertEquals(6, RollDice.score_pair(3, 4, 3, 5, 6));
            assertEquals(10, RollDice.score_pair(5, 3, 3, 3, 5));
            assertEquals(12, RollDice.score_pair(5, 3, 6, 6, 5));
        }


        @Test
        public void three_of_them() {
            assertEquals(9, RollDice.three_of_a_kind(3, 3, 3, 4, 5));
            assertEquals(15, RollDice.three_of_a_kind(5, 3, 5, 4, 5));
            assertEquals(9, RollDice.three_of_a_kind(3, 3, 3, 3, 5));
            assertEquals(9, RollDice.three_of_a_kind(3, 3, 3, 3, 3));
        }

        @Test
        public void four_of_them() {
            assertEquals(12, RollDice.four_of_a_kind(3, 3, 3, 3, 5));
            assertEquals(20, RollDice.four_of_a_kind(5, 5, 5, 4, 5));

        }

        @Nested
        class Combinations_uses_cases {
            @Test
            public void double_pair() {
                assertEquals(16, RollDice.two_pair(3, 3, 5, 4, 5));
                assertEquals(16, RollDice.two_pair(3, 3, 5, 5, 5));
            }

            @Test
            public void full_house() {
                assertEquals(18, RollDice.fullHouse(6, 2, 2, 2, 6));
                assertEquals(0, RollDice.fullHouse(2, 3, 4, 5, 6));
            }
        }
    }


    @Nested
    class Straight_uses_cases {
        @Test
        public void small_straight() {
            assertEquals(15, RollDice.smallStraight(1, 2, 3, 4, 5));
            assertEquals(15, RollDice.smallStraight(2, 3, 4, 5, 1));
            assertEquals(0, RollDice.smallStraight(1, 2, 2, 4, 5));
        }

        @Test
        public void large_straight() {
            assertEquals(20, RollDice.largeStraight(6, 2, 3, 4, 5));
            assertEquals(20, RollDice.largeStraight(2, 3, 4, 5, 6));
            assertEquals(0, RollDice.largeStraight(1, 2, 2, 4, 5));
        }
    }


}
