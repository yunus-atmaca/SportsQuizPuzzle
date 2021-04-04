package com.sportsquizpuzzle.puzzle;

import com.sportsquizpuzzle.R;

public class Levels {

    public static final String [] names = new String[] {
            "Soccer",
            "Basketball",
            "Volleyball",
            "Tennis",
            "Golf",
            "Bowling",
            "Pool",
            "Hockey"
    };

    public static Level getLevel(int level) {
        if (level == 2) {
            return getLevel2();
        } else if (level == 3) {
            return getLevel3();
        } else if (level == 4) {
            return getLevel4();
        } else if (level == 5) {
            return getLevel5();
        } else if(level == 6){
            //return getLevel5();
        }else if(level == 7){
            //return getLevel5();
        }else if(level == 8){
            //return getLevel5();
        }
        return getLevel1();
    }

    private static Level getLevel1() {
        Piece p1 = new Piece(1, R.id.lvl1_piece1, R.id.lvl1_target_holder1, R.id.lvl1_target_img1);
        Piece p2 = new Piece(2, R.id.lvl1_piece2, R.id.lvl1_target_holder2, R.id.lvl1_target_img2);

        return new Level("Level-1", 1, new Piece[]{p1, p2}, "Soccer",
                R.layout.t_level_1, R.layout.p_level_1);
    }

    private static Level getLevel2() {
        Piece p1 = new Piece(1, R.id.lvl2_piece1, R.id.lvl2_target_holder1, R.id.lvl2_target_img1);
        Piece p2 = new Piece(2, R.id.lvl2_piece2, R.id.lvl2_target_holder2, R.id.lvl2_target_img2);

        return new Level("Level-2", 2, new Piece[]{p1, p2}, "Basketball",
                R.layout.t_level_2, R.layout.p_level_2);
    }

    private static Level getLevel3() {
        Piece p1 = new Piece(1, R.id.lvl3_piece1, R.id.lvl3_target_holder1, R.id.lvl3_target_img1);
        Piece p2 = new Piece(2, R.id.lvl3_piece2, R.id.lvl3_target_holder2, R.id.lvl3_target_img2);
        Piece p3 = new Piece(3, R.id.lvl3_piece3, R.id.lvl3_target_holder3, R.id.lvl3_target_img3);
        Piece p4 = new Piece(4, R.id.lvl3_piece4, R.id.lvl3_target_holder4, R.id.lvl3_target_img4);

        return new Level("Level-3", 3, new Piece[]{p1, p2, p3, p4}, "Volleyball",
                R.layout.t_level_3, R.layout.p_level_3);
    }

    private static Level getLevel4() {
        Piece p1 = new Piece(1, R.id.lvl4_piece1, R.id.lvl4_target_holder1, R.id.lvl4_target_img1);
        Piece p2 = new Piece(2, R.id.lvl4_piece2, R.id.lvl4_target_holder2, R.id.lvl4_target_img2);
        Piece p3 = new Piece(3, R.id.lvl4_piece3, R.id.lvl4_target_holder3, R.id.lvl4_target_img3);
        Piece p4 = new Piece(4, R.id.lvl4_piece4, R.id.lvl4_target_holder4, R.id.lvl4_target_img4);

        return new Level("Level-4", 4, new Piece[]{p1, p2, p3, p4}, "Tennis",
                R.layout.t_level_4, R.layout.p_level_4);
    }

    private static Level getLevel5() {
        Piece p1 = new Piece(1, R.id.lvl5_piece1, R.id.lvl5_target_holder1, R.id.lvl5_target_img1);
        Piece p2 = new Piece(2, R.id.lvl5_piece2, R.id.lvl5_target_holder2, R.id.lvl5_target_img2);
        Piece p3 = new Piece(3, R.id.lvl5_piece3, R.id.lvl5_target_holder3, R.id.lvl5_target_img3);
        Piece p4 = new Piece(4, R.id.lvl5_piece4, R.id.lvl5_target_holder4, R.id.lvl5_target_img4);

        return new Level("Level-5", 5, new Piece[]{p1, p2, p3, p4}, "Golf",
                R.layout.t_level_5, R.layout.p_level_5);
    }
}
