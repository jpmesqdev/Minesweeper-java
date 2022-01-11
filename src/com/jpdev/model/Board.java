package com.jpdev.model;

import com.jpdev.exception.ExitException;
import com.jpdev.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int rows;
    private final int columns;
    private final int mineCount;

    private List<Camp> camps = new ArrayList<>();

    public Board(int rows, int columns, int mineCount) {
        this.rows = rows;
        this.columns = columns;
        this.mineCount = mineCount;

        generateCamps();
        associateNeighborhood();
        sortMines();
    }

    public void openCamp(int row, int column) {
        try {
            this.camps.stream()
                    .filter(camp -> camp.getRow() == row && camp.getColumn() == column)
                    .findFirst()
                    .ifPresent(Camp::open);
        } catch (ExplosionException e) {
            this.camps.forEach(camp -> camp.setOpened(true));
            throw e;
        }

    }

    public void markCamp (int row, int column) {
        this.camps.stream()
                .filter(camp -> camp.getRow() == row &&
                        camp.getColumn() == column)
                .findFirst()
                .ifPresent(Camp::toggleMarked);
    }

    void generateCamps() {
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                this.camps.add(new Camp(row, column));
            }
        }
    }

    void associateNeighborhood() {
        for (Camp camp1: this.camps) {
            for (Camp camp2: this.camps) {
                camp1.addNeighbor(camp2);
            }
        }
    }

    void sortMines() {
        long sortedMines = 0;

        do {
            int randomNumber = (int) (Math.random() * this.camps.size());
            this.camps.get(randomNumber).putMine();
            sortedMines = this.camps.stream().filter(Camp::isMined).count();
        } while (sortedMines < this.mineCount);
    }


    public void restartGame() {
        this.camps.forEach(Camp::restart);
        sortMines();
    }

    public boolean checkGoalDone() {
        return this.camps.stream().allMatch(Camp::checkGoalDone);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("  ");
        for (int column = 0; column < this.columns; column++) {
            sb.append(" ");
            sb.append(column);
            sb.append(" ");
        }

        sb.append("\n");

        int i = 0;

        for (int row = 0; row < this.rows; row++) {
            sb.append(" ");
            sb.append(row);

            for (int column = 0; column < this.columns; column++) {
                sb.append(" ");
                sb.append(camps.get(i).toString());
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }


        return sb.toString();
    }
}
