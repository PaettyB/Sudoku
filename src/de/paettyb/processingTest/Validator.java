package de.paettyb.processingTest;

import java.util.ArrayList;

import static processing.core.PApplet.println;
import static de.paettyb.processingTest.Sudoku.*;

public class Validator {
    public static ArrayList<Move> getAllValidMoves(int[][] grid) {
        ArrayList<Move> valids = new ArrayList<Move>();
        for (int i=0; i < n; i++) {
            for (int j=0; j < m; j++) {
                if (grid[i][j] == 0) {
                    
                    for (int k = 1; k <= 9; k++) {
                        if (isValid(grid, i, j, k)) {
                            valids.add(new Move(i, j, k));
                        }
                    }
                }
            }
        }
        
        return valids;
    }
    
    
    public static void removeInvalidMoves(int[][] grid, ArrayList<Move> moves){
        for(int i = moves.size()-1; i >= 0 ; i--) {
            Move m = moves.get(i);
            if(!isValid(grid, m.x,m.y,m.val)){
                moves.remove(i);
            }
        }
    }
    
    public static boolean isValidInBox(int[][] grid, int x, int y, int val) {
        int xOffset = (x / 3) * 3;
        int yOffset = (y / 3) * 3;
        
        for (int i = xOffset; i < xOffset + 3; i++) {
            for (int j = yOffset; j < yOffset + 3; j++) {
                if (grid[i][j] == val) return false;
            }
        }
        return true;
    }
    
    public static boolean isValidInRow (int[][] grid, int y, int val) {
        for (int i= 0; i < n; i++) {
            if (grid[i][y] == val) return false;
        }
        return true;
    }
    
    public static boolean isValidInCol (int[][] grid, int x, int val) {
        for (int j= 0; j < m; j++) {
            if (grid[x][j] == val) return false;
        }
        return true;
    }
    
    public static boolean isValid(int[][] grid, int x, int y, int val) {
        if (grid[x][y] == 0 && isValidInCol(grid, x, val) && isValidInRow(grid, y, val) && isValidInBox(grid, x, y, val)) return true;
        return false;
    }
    
    
    public static boolean isGridComplete(int[][] grid){
        for (int i=0; i < n; i++) {
            for (int j=0; j < m; j++) {
                if(grid[i][j] == 0) return false;
            }
        }
        return true;
    }
    
}
