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
//        ArrayList<Move> succcessfulMoves = getAllValidMoves(grid);
        if(validMoves.size() == 0) {
            if(isGridComplete(grid)) return true;
            return false;
        }
        
        Move uniqueMove = null;
        Move currentMove;
        
        while(!validMoves.isEmpty()){
            currentMove = validMoves.remove(0);
            grid[currentMove.x][currentMove.y] = currentMove.val;
            //After we found one move that leads to a unique solution, we check the other possible moves *ON THE SAME CELL*
            
            boolean subsequentSolutionUnique = findUniqueSolution(grid, new ArrayList<>(validMoves));
            grid[currentMove.x][currentMove.y] = 0;
            if(subsequentSolutionUnique){
                if(uniqueMove == null) {
                    uniqueMove = currentMove;
                } else {
                    if(uniqueMove.x == currentMove.x && uniqueMove.y == currentMove.y){
                        return false;
                    }
                }
            }
        }
        
        return (uniqueMove != null);
    }
    
    public static int emptyGrid(int[][] grid){
        int removed = 0;
        for (int i = 0; i < 30; i++) {
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
