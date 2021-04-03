package com.sportsquizpuzzle.puzzle;

public class Level {
    private String name;
    private int levelNumber;
    private Piece [] pieces;
    private String objectName;

    public Level(String name, int levelNumber, Piece[] pieces, String objectName) {
        this.name = name;
        this.levelNumber = levelNumber;
        this.pieces = pieces;
        this.objectName = objectName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public Piece[] getPieces() {
        return pieces;
    }

    public void setPieces(Piece[] pieces) {
        this.pieces = pieces;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
}
