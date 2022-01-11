package com.jpdev.view;

import com.jpdev.exception.ExitException;
import com.jpdev.exception.ExplosionException;
import com.jpdev.model.Board;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class BoardConsole {
    private Board board;
    private Scanner input = new Scanner(System.in);

    public BoardConsole(Board board) {
        this.board = board;
        startGame();
    }

    private void startGame() {
        try {
            boolean continuePlaying = true;

            while (continuePlaying) {
                gameLoop();
                System.out.println("Play again? y/N");
                String response = input.nextLine();

                if ("n".equalsIgnoreCase(response)) {
                    continuePlaying = false;
                } else {
                    this.board.restartGame();
                }

            }
        } catch (ExitException e) {
            System.out.println("Bye!!!");
        } finally {
            input.close();
        }
    }

    private void gameLoop() {
        try {

            while (!this.board.checkGoalDone()) {
                System.out.println(this.board);

                String typed = catchTypedValue("Type (x, y): ");

                Iterator<Integer> xy = Arrays.stream(typed.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();

                typed = catchTypedValue("1 - Open || 2 - (Un)Mark");

                if ("1".equals(typed)) {
                    this.board.openCamp(xy.next(), xy.next());
                } else if ("2".equals(typed)) {
                    this.board.markCamp(xy.next(), xy.next());
                }
            }

            System.out.println(this.board);
            System.out.println("You win!");
        } catch (ExplosionException e) {
            System.out.println(this.board);
            System.out.println("You lose!");
        }
    }

    private String catchTypedValue(String text) {
        System.out.println(text);
        String typed = input.nextLine();

        if ("exit".equalsIgnoreCase(typed)) {
            throw new ExitException();
        }

        return typed;
    }
}
