package minesweeper.view;


import java.util.Collection;
import java.util.Scanner;

import backtracker.Backtracker;
import minesweeper.model.GameState;
import minesweeper.model.Location;
import minesweeper.model.MinesweepBack;
import minesweeper.model.Minesweeper;
import minesweeper.model.MinesweeperException;

public class CLI_Minesweeper {
    public static void main(String[] args) throws MinesweeperException{
            Boolean quit = true;
            int rows;
            int cols;
            int mineCount;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter how many rows, columns, and mines you would like the board to have\n"
            + "(enter like: <rows> <cols> <mines>):");
            String[] start = scanner.nextLine().split(" ");
            rows = Integer.parseInt(start[0]);
            cols = Integer.parseInt(start[1]);
            mineCount = Integer.parseInt(start[2]);
            Minesweeper minesweeper = new Minesweeper(rows, cols, mineCount);
            System.out.println("Please enter a command:\n");
            help();
            while((minesweeper.getGameState() != GameState.LOST || minesweeper.getGameState() != GameState.WON) && quit == true){
                System.out.println("Minesweeper Board:");
                System.out.println(minesweeper);
                String[] choice = scanner.nextLine().split(" ");
                switch (choice[0]) {
                    case "help":
                        help();
                        break;
                    case "pick":
                        try {
                            minesweeper.makeSelction(new Location(Integer.parseInt(choice[1]), Integer.parseInt(choice[2])));
                            System.out.println("\nTotal Moves: " + minesweeper.getMoveCount());
                            break;
                        } 
                        catch (Exception e) {
                            System.out.println("INVALID MOVE\nPlease enter a valid move:");
                            break;
                        }
                    case "hint":
                        Collection<Location> possible = minesweeper.getPossibleSelections();
                            System.out.println("A possible move is: (" + possible.iterator().next() + ")");
                            break;
                    case "reset":
                        minesweeper = new Minesweeper(rows, cols, mineCount);
                        break;
                    case "quit":
                        quit = false;
                        System.out.println("\nBYE BYE");
                        break;
                    case "solve":
                        MinesweepBack findSum = new MinesweepBack(minesweeper);
                        Backtracker solver = new Backtracker(false);
                        solver.solve(findSum);
                        System.out.println(findSum);
                        quit=false;
                        break;
                        
                }
                if(minesweeper.getGameState() == GameState.LOST){
                    System.out.println("\nYOU LOSE!!!! (OMAE WA MOU SHINDEIRU)");
                    break;
                }
                if(minesweeper.getGameState() == GameState.WON){
                    System.out.println("\nYOU WIN!!!!!!!");
                    break;
                }
            }
            System.out.println("Here was the solution for the board: ");
            System.out.println(minesweeper.printSolution());
            scanner.close();
        }

    private static void help(){
        System.out.println("\nHere are the commands:\n"
        + "help: displays the commands\n"
        + "pick <row> <col>: makes a move, if invalid it will prompt you again\n"
        + "hint: displays a valid selection"
        + "quit: quits the game\n"
        + "solve: solves the board\n");
    }
}


