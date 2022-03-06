package de.paettyb.processingTest;

import static de.paettyb.processingTest.Sudoku.*;

public final class Move {
    public final int x,y,val;
    public Move(int x,int y,int val) {
        this.x = x;
        this.y = y;
        this.val = val;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Move move = (Move) o;
        
        if (x != move.x) return false;
        if (y != move.y) return false;
        return val == move.val;
    }
    
    @Override
    public String toString() {
        return "Move{" +
                "x=" + x +
                ", y=" + y +
                ", val=" + val +
                '}';
    }
}

class MoveBook{
    int lastMoveIndex = -1;
    Move[] moves;
    MoveBook(){
        moves = new Move[n*m];
    }
}