package com.sportsquizpuzzle.puzzle;

public class Piece {
    private int position;
    private int id;
    private int targetHolderId;
    private int targetImgId;

    public Piece(int position, int id, int targetHolderId, int targetImgId) {
        this.position = position;
        this.id = id;
        this.targetHolderId = targetHolderId;
        this.targetImgId = targetImgId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTargetHolderId() {
        return targetHolderId;
    }

    public void setTargetHolderId(int targetHolderId) {
        this.targetHolderId = targetHolderId;
    }

    public int getTargetImgId() {
        return targetImgId;
    }

    public void setTargetImgId(int targetImgId) {
        this.targetImgId = targetImgId;
    }
}
