/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ahmetcelik;

/**
 *
 * @author ahmet-celik
 */
public class MazeGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Maze maze = new Maze(50);
       // System.out.println(maze);
        maze.generateMaze();
        System.out.println(maze);
    }
}
