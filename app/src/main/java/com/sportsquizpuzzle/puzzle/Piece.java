package com.sportsquizpuzzle.puzzle;

public class Piece {
    private int position;
    private int drawableResource;

    public Piece(int position, int drawableResource) {
        this.position = position;
        this.drawableResource = drawableResource;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDrawableResource() {
        return drawableResource;
    }

    public void setDrawableResource(int drawableResource) {
        this.drawableResource = drawableResource;
    }
}
