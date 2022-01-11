package com.jpdev;

import com.jpdev.model.Board;
import com.jpdev.view.BoardConsole;

public class Main {

    public static void main(String[] args) {
        Board board = new Board(6, 6, 6 );
        new BoardConsole(board);
    }
}
