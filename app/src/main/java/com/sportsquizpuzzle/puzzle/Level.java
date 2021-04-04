package com.sportsquizpuzzle.puzzle;

public class Level {
    private String name;
    private int levelNumber;
    private Piece [] pieces;
    private String objectName;

    private int targetLayoutId;
    private int piecesLayoutId;

    public Level(String name, int levelNumber, Piece[] pieces, String objectName, int targetLayoutId, int piecesLayoutId) {
        this.name = name;
        this.levelNumber = levelNumber;
        this.pieces = pieces;
        this.objectName = objectName;
        this.targetLayoutId = targetLayoutId;
        this.piecesLayoutId = piecesLayoutId;
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

    public int getTargetLayoutId() {
        return targetLayoutId;
    }

    public void setTargetLayoutId(int targetLayoutId) {
        this.targetLayoutId = targetLayoutId;
    }

    public int getPiecesLayoutId() {
        return piecesLayoutId;
    }

    public void setPiecesLayoutId(int piecesLayoutId) {
        this.piecesLayoutId = piecesLayoutId;
    }
}
