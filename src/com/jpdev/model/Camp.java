package com.jpdev.model;

import com.jpdev.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;

public class Camp {

    private final int row;
    private final int column;

    private boolean opened = false;
    private boolean marked = false;
    private boolean mined = false;

    private final List<Camp> neighborhood = new ArrayList<>();

    Camp(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    boolean isMarked() {
        return marked;
    }

    void toggleMarked() {
        if (!opened) {
            this.marked = !marked;
        }
    }

    boolean isMined() {
        return mined;
    }

    void setMined(boolean mined) {
        this.mined = mined;
    }

    boolean open() {

        if (!this.opened && !this.marked) {
            this.opened = true;

            if (this.mined) {
                throw new ExplosionException();
            }

            if (verifyNeighborhood()) {
                this.neighborhood.forEach(Camp::open);
            }
            return true;

        } else {
           return false;
        }
    }

    boolean addNeighbor(Camp neighbor) {

        boolean differentRow = this.row != neighbor.row;
        boolean differentColumn = this.column != neighbor.column;
        boolean diagonal = differentColumn && differentRow;

        int deltaRow = Math.abs(this.row - neighbor.row);
        int deltaColumn = Math.abs(this.column - neighbor.column);
        int deltaTotal = deltaRow + deltaColumn;

        if (deltaTotal == 1 && !diagonal) {
            this.neighborhood.add(neighbor);
            return true;
        } else if (deltaTotal == 2 && diagonal) {
            this.neighborhood.add(neighbor);
            return true;
        } else {
            return false;
        }

    }

    boolean verifyNeighborhood() {
        return this.neighborhood.stream().noneMatch(Camp::isMined);
    }

    long verifyNeighborhoodMinesCount() {
        return this.neighborhood.stream().filter(Camp::isMined).count();
    }

    void putMine() {
        this.mined = true;
    }

    boolean checkGoalDone() {
        if (this.opened && !this.mined) {
            return true;
        } else if (this.marked) {
            return true;
        } else {
            return false;
        }
    }

    void restart() {
        this.marked = false;
        this.opened = false;
        this.mined = false;
    }

    public String toString() {
        if (this.marked) {
            return "x";
        } else if (this.opened && this.mined) {
            return "*";
        } else if (this.opened && verifyNeighborhoodMinesCount() > 0) {
            return Long.toString(verifyNeighborhoodMinesCount());
        } else if (this.opened) {
            return " ";
        } else {
            return "?";
        }
    }


}
