/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ahmetcelik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author ahmet-celik
 */
public class Maze {

    private static final int WALL_DOWN = 1, WALL_RIGHT = 2, CELL_VISITED = 4;
    private static final int UP = 0, LEFT = 1, DOWN = 2, RIGHT = 3;
    private Random random;
    private int[][] maze;
    private int length;

    public Maze(int length) {
        this.random = new Random(42);
        this.length = length + 2;
        this.maze = new int[length + 2][length + 2];
        initMaze();
    }
    /**
     * Init maze with walls.
     */
    private void initMaze() {
        for (int i = 1; i < this.length - 1; i++) {
            maze[0][i] = (Maze.WALL_DOWN | Maze.CELL_VISITED);
            maze[i][0] = (Maze.WALL_RIGHT | Maze.CELL_VISITED);
            maze[this.length - 1][i] = Maze.CELL_VISITED;
            maze[i][this.length - 1] = Maze.CELL_VISITED;
            for (int j = 1; j < this.length - 1; j++) {
                maze[i][j] = (Maze.WALL_DOWN | Maze.WALL_RIGHT);
            }
        }
    }
    /**
     * Generates random walk
     */
    public void generateMaze() {
        int[] start = new int[]{1, 1};
        markVisited(start);
        randomWalk(start);
    }
    /**
     * Checks if cell in this direction if visited
     * @param start
     * @param direction
     * @return 
     */
    private boolean checkCell(int[] start, int direction) {
        int[] end = Arrays.copyOf(start, start.length);
        switch (direction) {
            case Maze.UP:
                --end[0];
                break;
            case Maze.RIGHT:
                ++end[1];
                break;
            case Maze.DOWN:
                ++end[0];
                break;
            case Maze.LEFT:
                --end[1];
                break;
        }
        return (maze[end[0]][end[1]] & Maze.CELL_VISITED) > 0;
    }
    /**
     * Mark given cell as visited
     * @param position Cell
     */
    private void markVisited(int[] position) {
        maze[position[0]][position[1]] |= Maze.CELL_VISITED;
    }
    /**
     * Remove wall between start and end cells
     * @param start Position
     * @param direction Walking direction
     * @return New position
     */
    private int[] breakWall(int[] start, int direction) {
        int[] end = Arrays.copyOf(start, start.length);
        switch (direction) {
            case Maze.UP:
                --end[0];
                maze[end[0]][end[1]] &= ~Maze.WALL_DOWN;
                break;
            case Maze.RIGHT:
                ++end[1];
                maze[start[0]][start[1]] &= ~Maze.WALL_RIGHT;
                break;
            case Maze.DOWN:
                ++end[0];
                maze[start[0]][start[1]] &= ~Maze.WALL_DOWN;
                break;
            case Maze.LEFT:
                --end[1];
                maze[end[0]][end[1]] &= ~Maze.WALL_RIGHT;
                break;
        }
        return end;
    }
    /**
     * Prepare a list of unvisited neighbors
     * @param current Position in the maze
     * @return list of unvisited cells' directions
     */
    private ArrayList<Integer> getPossiblePositions(int[] current) {
        ArrayList<Integer> positionsAvailable = new ArrayList<>();
        if (!checkCell(current, Maze.UP)) {
            positionsAvailable.add(Maze.UP);
        }
        if (!checkCell(current, Maze.RIGHT)) {
            positionsAvailable.add(Maze.RIGHT);
        }
        if (!checkCell(current, Maze.DOWN)) {
            positionsAvailable.add(Maze.DOWN);
        }
        if (!checkCell(current, Maze.LEFT)) {
            positionsAvailable.add(Maze.LEFT);
        }
        return positionsAvailable;
    }
    
    /**
     * Random depth-first search walking
     * @param current Position in the maze
     */
    private void randomWalk(int[] current) {
        ArrayList<Integer> positionsAvailable = getPossiblePositions(current);
        while (!positionsAvailable.isEmpty()) {
            int direction = positionsAvailable.get(random.nextInt(positionsAvailable.size()));
            int[] newPosition = breakWall(current, direction);
            markVisited(newPosition);
            randomWalk(newPosition);
            positionsAvailable = getPossiblePositions(current);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1; j++) {
                int cell = maze[i][j];
                if ((Maze.WALL_DOWN & cell) > 0) {
                    sb.append('_');
                } else {
                    sb.append(' ');
                }
                if ((Maze.WALL_RIGHT & cell) > 0) {
                    sb.append('|');
                } else {
                    sb.append('.');
                }
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
