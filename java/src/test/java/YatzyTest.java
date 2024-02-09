import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class YatzyTest {


    @Nested
    class Chance_uses_cases {
        @Test
        public void all_dices_are_summed() {
            assertEquals(15, Yatzy.chance(2, 3, 4, 5, 1));
            assertEquals(16, Yatzy.chance(3, 3, 4, 5, 1));
        }
    }

    @Nested
    class Yatzy_uses_cases {
        @Test
        public void yatzy_scores_50() {
            assertEquals(50, Yatzy.yatzy(4, 4, 4, 4, 4));
            assertEquals(50, Yatzy.yatzy(6, 6, 6, 6, 6));
            assertEquals(0, Yatzy.yatzy(6, 6, 6, 6, 3));
        }
    }


    @Nested
    class Single_values_uses_cases {
        @Test
        public void ones() {
            assertEquals(1, Yatzy.ones(1, 2, 3, 4, 5));
            assertEquals(2, Yatzy.ones(1, 2, 1, 4, 5));
            assertEquals(0, Yatzy.ones(6, 2, 2, 4, 5));
            assertEquals(4, Yatzy.ones(1, 2, 1, 1, 1));
        }

        @Test
        public void twos() {
            assertEquals(4, Yatzy.twos(1, 2, 3, 2, 6));
            assertEquals(10, Yatzy.twos(2, 2, 2, 2, 2));
        }

        @Test
        public void threes() {
            assertEquals(6, Yatzy.threes(1, 2, 3, 2, 3));
            assertEquals(12, Yatzy.threes(2, 3, 3, 3, 3));
        }

        @Test
        public void fours() {
            assertEquals(12, new Yatzy(4, 4, 4, 5, 5).fours());
            assertEquals(8, new Yatzy(4, 4, 5, 5, 5).fours());
            assertEquals(4, new Yatzy(4, 5, 5, 5, 5).fours());
        }

        @Test
        public void fives() {
            assertEquals(10, new Yatzy(4, 4, 4, 5, 5).fives());
            assertEquals(15, new Yatzy(4, 4, 5, 5, 5).fives());
            assertEquals(20, new Yatzy(4, 5, 5, 5, 5).fives());
        }

        @Test
        public void sixes() {
            assertEquals(0, new Yatzy(4, 4, 4, 5, 5).sixes());
            assertEquals(6, new Yatzy(4, 4, 6, 5, 5).sixes());
            assertEquals(18, new Yatzy(6, 5, 6, 6, 5).sixes());
        }
    }

    @Nested
    class Same_of_a_kind_uses_cases {
        @Test
        public void two_of_them() {
            assertEquals(6, Yatzy.score_pair(3, 4, 3, 5, 6));
            assertEquals(10, Yatzy.score_pair(5, 3, 3, 3, 5));
            assertEquals(12, Yatzy.score_pair(5, 3, 6, 6, 5));
        }


        @Test
        public void three_of_them() {
            assertEquals(9, Yatzy.three_of_a_kind(3, 3, 3, 4, 5));
            assertEquals(15, Yatzy.three_of_a_kind(5, 3, 5, 4, 5));
            assertEquals(9, Yatzy.three_of_a_kind(3, 3, 3, 3, 5));
            assertEquals(9, Yatzy.three_of_a_kind(3, 3, 3, 3, 3));
        }

        @Test
        public void four_of_them() {
            assertEquals(12, Yatzy.four_of_a_kind(3, 3, 3, 3, 5));
            assertEquals(20, Yatzy.four_of_a_kind(5, 5, 5, 4, 5));

        }

        @Nested
        class Combinations_uses_cases {
            @Test
            public void double_pair() {
                assertEquals(16, Yatzy.two_pair(3, 3, 5, 4, 5));
                assertEquals(16, Yatzy.two_pair(3, 3, 5, 5, 5));
            }

            @Test
            public void full_house() {
                assertEquals(18, Yatzy.fullHouse(6, 2, 2, 2, 6));
                assertEquals(0, Yatzy.fullHouse(2, 3, 4, 5, 6));
            }
        }
    }


    @Nested
    class Straight_uses_cases {
        @Test
        public void small_straight() {
            assertEquals(15, Yatzy.smallStraight(1, 2, 3, 4, 5));
            assertEquals(15, Yatzy.smallStraight(2, 3, 4, 5, 1));
            assertEquals(0, Yatzy.smallStraight(1, 2, 2, 4, 5));
        }

        @Test
        public void large_straight() {
            assertEquals(20, Yatzy.largeStraight(6, 2, 3, 4, 5));
            assertEquals(20, Yatzy.largeStraight(2, 3, 4, 5, 6));
            assertEquals(0, Yatzy.largeStraight(1, 2, 2, 4, 5));
        }
    }


}
