package com.sportsquizpuzzle.puzzle;

import com.sportsquizpuzzle.R;

public class Levels {
    public static final Level LEVEL_1 = getLevel1();
    public static final Level LEVEL_2 = null;

    public static Level getLevel(int level) {
        if (level == 2) {
            return LEVEL_2;
        }
        return LEVEL_1;
    }

    private static Level getLevel1() {
        Piece p1 = new Piece(1, R.id.lvl1_piece1, R.id.lvl1_target_holder1, R.id.lvl1_target_img1);
        Piece p2 = new Piece(2, R.id.lvl1_piece2, R.id.lvl1_target_holder2, R.id.lvl1_target_img2);

        return new Level("Level-1", 1, new Piece[]{p1, p2}, "soccer",
                R.layout.t_level_1, R.layout.p_level_1);
    }
}
