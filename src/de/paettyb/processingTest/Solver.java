package de.paettyb.processingTest;

import processing.data.IntList;
import static de.paettyb.processingTest.Sudoku.*;
import static de.paettyb.processingTest.Validator.*;

import java.util.ArrayList;

public class Solver {
    
    public static boolean fillGrid(int[][] grid) {
        int i=0, j=0;
        OuterLoop:
        for (i=0; i < n; i++) {
            for (j=0; j < m; j++) {
                if (grid[i][j] == 0) {
                    break OuterLoop;
                }
            }
        }
        if (i >= n) return true;
        
        
        IntList attempted = new IntList();
        while (attempted.size() < 9) {
            int val = (Sudoku.random.nextInt(9))+1;
            if (attempted.hasValue(val)) continue;
            attempted.push(val);
            
            
            if (isValid(grid,i, j, val)) {
                grid[i][j] = val;
                if (fillGrid(grid)) {
                    return true;
                } else {
                    grid[i][j] = 0;
                }
            }
        }
        return false;
    }


//TODO: CHECK IF GRID IS COMPLETE AFTER MAKING MOVE
    
    public static boolean findUniqueSolution(int[][] grid, ArrayList<Move> validMoves) {
        
        Validator.removeInvalidMoves(grid,validMoves);
        if(validMoves.size() == 0) {
            if(isGridComplete(grid)) return true;
            return false;
        }
        
        Move currentMove;
        
        while(!validMoves.isEmpty()){
            currentMove = validMoves.remove(0);
            grid[currentMove.x][currentMove.y] = currentMove.val;
            boolean subsequentSolutionUnique = findUniqueSolution(grid, new ArrayList<>(validMoves));
            grid[currentMove.x][currentMove.y] = 0;
            if(subsequentSolutionUnique) {
                // Now we only check the remaining moves, which are on the same cell then we return
                for (Move m : validMoves) {
                    if (m.x == currentMove.x && m.y == currentMove.y) {
                        grid[m.x][m.y] = m.val;
                        boolean otherSolution = findUniqueSolution(grid, new ArrayList<>(validMoves));
                        grid[m.x][m.y] = 0;
                        if (otherSolution) return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    public static int emptyGrid(int[][] grid){
        int removed = 0;
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(9);
            int y = random.nextInt(9);
            if(grid[x][y] == 0) continue;
            int removedNumber = grid[x][y];
            grid[x][y] = 0;
            println("removing " + x + ", " + y);
            if(!findUniqueSolution(grid, getAllValidMoves(grid))){
                grid[x][y] = removedNumber;
            } else {
                removed++;
            }
        }
        return removed;
    }
    
    
    
    public static void copyGrid(int[][] src, int[][] dest) {
        for (int i=0; i < n; i++) {
            for (int j=0; j < m; j++) {
                dest[i][j] = src[i][j];
            }
        }
    }
    
    public static void removeNumberFromGrid(int[][] grid, int number){
        for (int i=0; i < n; i++) {
            for (int j=0; j < m; j++) {
                if(grid[i][j] == number) grid[i][j] = 0;
            }
        }
    }
    
}
