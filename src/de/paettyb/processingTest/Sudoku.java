package de.paettyb.processingTest;

import processing.core.PApplet;

import java.util.Random;

import static de.paettyb.processingTest.Solver.*;

public class Sudoku extends PApplet {
    
    static int n = 9, m = 9;
    static int gridSize;
    public static Random random = new Random(6969);
    
    int[][] fixedNumbers;
    int[][] variableNumbers;
    int[][] grid;
    
    float fontSize;
    
    boolean selected= false;
    int selectedX, selectedY;
    
    public void settings() {
        size(900, 900);
        randomSeed(6969);
        
        gridSize = width / n;
        
        fontSize = gridSize * 0.75f;
        fixedNumbers = new int[n][m];
        variableNumbers = new int[n][m];
        grid = new int[n][m];
        
        //for (int i=0; i < n; i++) {
        //  for (int j=0; j < m; j++) {
        //    int n = floor(random(5));
        //    fixedNumbers[i][j] = n;
        //    grid[i][j] = n;
        //  }
        //}
        
        fillGrid(grid);
        println("Emptied: " + Solver.emptyGrid(grid));
//        grid[6][2] = 0;
//        grid[8][2] = 0;
//        grid[6][4] = 0;
//        grid[8][4] = 0;
//        Solver.removeNumberFromGrid(grid, 1);
//        Solver.removeNumberFromGrid(grid, 2);
        //        grid[4] = new int[9];
        //grid[3][4] = 0;
        //grid[5][4] = 0;
        //grid[3][3] = 0;
        //grid[5][5] = 0;
        //grid[3][5] = 0;
        //grid[5][3] = 0;
//        grid[3] = new int[9];
//        grid[4] = new int[9];
        
//        println("Found unique sulution: " + findUniqueSolution(grid, Validator.getAllValidMoves(grid)));
        copyGrid(grid, fixedNumbers);
        
        
    }
    
    public void draw () {
        textSize(fontSize);
        textAlign(CENTER, CENTER);
        if (keyPressed) {
            if (key == '1' || key == '2' || key == '3' || key == '4' || key == '5' || key == '6' || key == '7' || key == '8' || key == '9') {
                if (selected && fixedNumbers[selectedX][selectedY] == 0) {
                    variableNumbers[selectedX][selectedY] = key-48;
                    grid[selectedX][selectedY] = key-48;
                }
            }
        }
        
        background (0);
        strokeWeight(0.1f);
        for (int i=0; i < n; i++) {
            for (int j=0; j < m; j++) {
                noFill();
                stroke(255);
                rect(i*gridSize, j*gridSize, gridSize, gridSize);
                if (selectedX == i && selectedY == j && selected) {
                    fill(207, 115, 0);
                    rect(i*gridSize+1, j*gridSize+1, gridSize-2, gridSize-2);
                }
                
                if (fixedNumbers[i][j] > 0) {
                    fill(10);
                    rect(i*gridSize+1, j*gridSize+1, gridSize-2, gridSize-2);
                    fill(200);
                    text(""+fixedNumbers[i][j], (int)(i * gridSize + gridSize / 2), (int)(j * gridSize +(0.5* textAscent())));
                } else if (variableNumbers[i][j] > 0) {
                    fill(255);
                    text(""+variableNumbers[i][j], (int)(i * gridSize + gridSize / 2), (int)(j * gridSize +(0.5* textAscent())));
                }
            }
        }
        
        strokeWeight(4);
        fill(255);
        line(0,3 * gridSize, width, 3 * gridSize);
        line(0,6 * gridSize, width, 6 * gridSize);
        
        line(3 * gridSize, 0, 3 * gridSize, height);
        line(6 * gridSize, 0, 6 * gridSize, height);
    }
    
    public void mousePressed() {
        
        int gridX = mouseX / gridSize;
        int gridY = mouseY / gridSize;
        if (fixedNumbers[gridX][gridY] > 0) {
            selected = false;
            return;
        }
        if (selectedX == gridX && selectedY == gridY) {
            selected = !selected;
        } else {
            selected = true;
            selectedX = gridX;
            selectedY = gridY;
        }
    }
    
    public void keyPressed() {
        if (keyCode == 8 || key == '0') {
            if (selected) {
                variableNumbers[selectedX][selectedY] = 0;
                grid[selectedX][selectedY] = 0;
            }
        }
    }
    
    public static void main(String[] args) {
	    PApplet.main("de.paettyb.processingTest.Sudoku");
    }
}
