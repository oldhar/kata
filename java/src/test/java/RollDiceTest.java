import org.junit.jupiter.api.*;

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
        @Test
        public void all_dices_are_summed() {
            assertEquals(15, RollDice.chance(2, 3, 4, 5, 1));
            assertEquals(16, RollDice.chance(3, 3, 4, 5, 1));
        }
    }

    @Nested
    class Yatzy_uses_cases {
        @Test
        public void yatzy_scores_50() {
            assertEquals(50, RollDice.yatzy(4, 4, 4, 4, 4));
            assertEquals(50, RollDice.yatzy(6, 6, 6, 6, 6));
            assertEquals(0, RollDice.yatzy(6, 6, 6, 6, 3));
        }
    }


    @Nested
    class Single_values_uses_cases {
        @Test
        public void ones() {
            assertEquals(1, RollDice.ones(1, 2, 3, 4, 5));
            assertEquals(2, RollDice.ones(1, 2, 1, 4, 5));
            assertEquals(0, RollDice.ones(6, 2, 2, 4, 5));
            assertEquals(4, RollDice.ones(1, 2, 1, 1, 1));
        }

        @Test
        public void twos() {
            assertEquals(4, RollDice.twos(1, 2, 3, 2, 6));
            assertEquals(10, RollDice.twos(2, 2, 2, 2, 2));
        }

        @Test
        public void threes() {
            assertEquals(6, RollDice.threes(1, 2, 3, 2, 3));
            assertEquals(12, RollDice.threes(2, 3, 3, 3, 3));
        }

        @Test
        public void fours() {
            assertEquals(12, new RollDice(4, 4, 4, 5, 5).fours());
            assertEquals(8, new RollDice(4, 4, 5, 5, 5).fours());
            assertEquals(4, new RollDice(4, 5, 5, 5, 5).fours());
        }

        @Test
        public void fives() {
            assertEquals(10, new RollDice(4, 4, 4, 5, 5).fives());
            assertEquals(15, new RollDice(4, 4, 5, 5, 5).fives());
            assertEquals(20, new RollDice(4, 5, 5, 5, 5).fives());
        }

        @Test
        public void sixes() {
            assertEquals(0, new RollDice(4, 4, 4, 5, 5).sixes());
            assertEquals(6, new RollDice(4, 4, 6, 5, 5).sixes());
            assertEquals(18, new RollDice(6, 5, 6, 6, 5).sixes());
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
