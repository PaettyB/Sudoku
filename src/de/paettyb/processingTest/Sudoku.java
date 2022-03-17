package de.paettyb.processingTest;

import processing.core.PApplet;
import processing.core.PVector;

import java.security.Key;
import java.util.Random;

import static de.paettyb.processingTest.Solver.*;

public class Sudoku extends PApplet {
    
    static int n = 9, m = 9;
    static int gridSize;
    static int miniGrid;
    public static Random random = new Random(6969);
    
    int[][] fixedNumbers;
    int[][] variableNumbers;
    int[][] grid;
    
    boolean[][][] pencilmarks;
    
    float fontSize;
    
    boolean selected= false;
    int selectedX, selectedY;
    
    public void settings() {
        size(900, 900);
        randomSeed(6969);
        
        gridSize = width / n;
        miniGrid = gridSize / 3;
        
        fontSize = gridSize * 0.75f;
        fixedNumbers = new int[n][m];
        variableNumbers = new int[n][m];
        grid = new int[n][m];
        pencilmarks = new boolean[n][m][9];
        
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
                if (selected && selectedX == i && selectedY == j) {
                    fill(207, 115, 0);
                    rect(i*gridSize+1, j*gridSize+1, gridSize-2, gridSize-2);
                    if(variableNumbers[i][j] == 0 && mouseHoverSelected()){
                        int hoverIndex = getPencilHoverIndex();
                        fill(0);
                        textSize(20);
                        textAlign(CENTER,CENTER);
                        PVector hoverCoords = miniIndexToCoords(hoverIndex);
                        text(""+(hoverIndex+1), i * gridSize + hoverCoords.x, j * gridSize + hoverCoords.y);
                    }
                }
                textSize(fontSize);
                textAlign(CENTER,CENTER);
                if (fixedNumbers[i][j] > 0) {
                    fill(10);
                    rect(i*gridSize+1, j*gridSize+1, gridSize-2, gridSize-2);
                    fill(200);
                    text(""+fixedNumbers[i][j], (int)(i * gridSize + gridSize / 2), (int)(j * gridSize +(0.5* textAscent())));
                } else if (variableNumbers[i][j] > 0) {
                    fill(255);
                    text(""+variableNumbers[i][j], (int)(i * gridSize + gridSize / 2), (int)(j * gridSize +(0.5* textAscent())));
                } else{
                    if(!(selected && selectedX == i && selectedY == j))
                        fill(150);
                    else
                        fill(0);
                    textSize(20);
                    textAlign(CENTER,CENTER);
                    for(int p = 0 ; p < 9; p++){
                        if(pencilmarks[i][j][p]){
                            PVector hoverCoords = miniIndexToCoords(p);
                            text(""+(p+1), i * gridSize + hoverCoords.x, j * gridSize + hoverCoords.y);
                        }
                    }
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
    
    public PVector miniIndexToCoords(int index){
        PVector coords = switch (index) {
            case 0 -> new PVector(0,0);
            case 1 -> new PVector(miniGrid,0);
            case 2 -> new PVector(miniGrid * 2,0);
            case 3 -> new PVector(0,miniGrid);
            case 4 -> new PVector(miniGrid,miniGrid);
            case 5 -> new PVector(miniGrid * 2,miniGrid);
            case 6 -> new PVector(0,miniGrid * 2);
            case 7 -> new PVector(miniGrid,miniGrid * 2);
            case 8 -> new PVector(miniGrid * 2,miniGrid * 2);
            default -> new PVector(-1,-1);
        };
        return coords.add(miniGrid * 0.5f, miniGrid * 0.5f - 3);
        
    }
    
    
    public void mousePressed() {
        
        int gridX = mouseX / gridSize;
        int gridY = mouseY / gridSize;
        if (fixedNumbers[gridX][gridY] > 0) {
            selected = false;
            return;
        }
        if(variableNumbers[gridX][gridY] == 0 && mouseHoverSelected()){
            int hoverIndex = getPencilHoverIndex();
            pencilmarks[gridX][gridY][hoverIndex] = !pencilmarks[gridX][gridY][hoverIndex];
            return;
        }
        selected = true;
        selectedX = gridX;
        selectedY = gridY;

    }
    
    private boolean mouseHoverSelected(){
        if(!selected) return false;
        int xStart = selectedX * gridSize;
        int yStart = selectedY * gridSize;
        return (mouseX >= xStart && mouseX < xStart + gridSize && mouseY >= yStart && mouseY < yStart+gridSize);
    }
    
    private int getPencilHoverIndex(){
        int xStart = selectedX * gridSize;
        int yStart = selectedY * gridSize;
        int dx = mouseX - xStart;
        int dy = mouseY - yStart;
        
        if(dx < miniGrid && dy < miniGrid) return 0;
        else if(dx <= miniGrid * 2 && dy <= miniGrid) return 1;
        else if(dx <= miniGrid * 3 && dy <= miniGrid) return 2;
        else if(dx <= miniGrid && dy <= miniGrid * 2) return 3;
        else if(dx <= miniGrid * 2 && dy <= miniGrid * 2) return 4;
        else if(dx <= miniGrid * 3 && dy <= miniGrid * 2) return 5;
        else if(dx <= miniGrid  && dy <= miniGrid * 3) return 6;
        else if(dx <= miniGrid * 2 && dy <= miniGrid * 3) return 7;
        else if(dx <= miniGrid * 3 && dy <= miniGrid * 3) return 8;
        else throw new RuntimeException("Invalid miniGrid location: " + dx + ", " + dy);
    }
    
    
    
    public void keyPressed() {
        if (keyCode == 8 || key == '0' || keyCode == DELETE) {
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
