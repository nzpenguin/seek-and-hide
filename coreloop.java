
/**
 * Write a description of class coreloop here.
 *
 * @author (Gabe)
 * @version 15/06/2022)
 */
import java.util.Scanner;//the scanner
import java.io.File; // file stuffs
import java.io.IOException;
import java.io.FileWriter;// to write the files
public class coreloop
{

    // instance variables - replace the example below with your own
    public static int BOARDSIZE = 11;
    public static boolean playeroneturn = true;
    public static char letterline = 'a';
    public static int PrintingBoardSize = coreloop.BOARDSIZE *2;
    static int boardsizeonelarger = BOARDSIZE + 1;// this verable is for printing the board starting at one
    public static boolean gapinboard = true;
    public static boolean needresponce = true;
    static int player1board[][] = new int[PrintingBoardSize][PrintingBoardSize];
    static int boardgap = PrintingBoardSize - 4;
    static boolean GameRunning = true;
    public static void main(String[] args){
        player1board[1][12] = 1;
        player1board[1][13] = 1;
        player1board[1][14] = 1;
        player1board[1][15] = 1;
        player1board[4][12] = 1;
        player1board[5][12] = 1;
        player1board[6][12] = 1;
        player1board[7][12] = 1;
        while (GameRunning == true){//game running start
            printboard(); //prints out board
            Scanner keyboard = new Scanner(System.in);
            String s1; // string needed for imput from scanner
            int xAxis = 0;
            int yAxis = 0;
            while(needresponce == true){
                s1 = keyboard.nextLine();//string being filled from input
                String inputverable[] = s1.split(",");//splitting the input
                boolean intworking = isInt(inputverable[1]);
                xAxis = getrow(inputverable[0], inputverable[1], intworking);
                yAxis = getcol(inputverable[0], inputverable[1]);
                //System.out.println(yAxis +","+ xAxis);
                needresponce = false;
            }//end of whileloop    
            //keyboard.close();
            if (player1board[xAxis][yAxis] == 0){
            player1board[xAxis][yAxis] = 2;
            }else if (player1board[xAxis][xAxis] == 1){
                player1board[xAxis][yAxis] = 3;
            }else{
                System.out.println("try again");
            }

            printboard(); //prints out board
            needresponce = true;
        }//end of GameRunning
    }//end of main

//checks if the thing being passed in is a int
   static public boolean isInt (String possibleInteger){
        int pos = 0;
    while(pos < possibleInteger.length()){
        char c = possibleInteger.charAt(pos);
        if(c >= '0' && c <= '9'){
            pos++;
        }else {
            return false;
        }
    }//end of if statment
    return true;
   }//end of isInt

//gets the colem
   static int getcol (String coord1, String coord2){
        char col = coord1.charAt(0);
        if (col >= 'a' && col <= 'a' + coreloop.BOARDSIZE){
            return col - 'a';
        }else { col = coord2.charAt(0);
            if (col >='a' && col <= 'a' + coreloop.BOARDSIZE) {
                return col - 'a';
            }else {return -1;}
        }//end of if statment
    }//end of getcol
    
    static int getrow (String coord1, String coord2, boolean working){
        int row;
        if(working == true){
            row = Integer.parseInt(coord2);
        }else {
            row = Integer.parseInt(coord1);
        }//end of if statment
        
        if (row >= 0 && row <= 10 + BOARDSIZE){
            return row;
        }else {row = Integer.parseInt(coord1);
            if (row >=0 && row <= BOARDSIZE) {
                return row -1 ;
            }else {return -1;}
        }//end of if statement
    }//end of getrow

    static void printboard(){//printsboard
        clearboard();
        String gap;
        gap = " ";
        System.out.print("seek");
        System.out.print("         ");
        for(double i=0; i <= boardgap;){
            System.out.print(gap);
            i++;
        }

        System.out.println("hide");
        System.out.print("  ");
        letterline = 'a';
        for(int y=0;y<coreloop.BOARDSIZE;y++){
            System.out.print(letterline + " ");
            letterline++;
        }
        //prints out letter line on right side
        letterline = 'a';
        System.out.print("        ");
        for(int y=0;y<coreloop.BOARDSIZE;y++){
            System.out.print(letterline + " ");
            letterline++;
        }
        System.out.println();
        for(int x=1; x<boardsizeonelarger;x++) {
            System.out.print(x + " ");
            for(int y=0;y<PrintingBoardSize;y++){
                if(y >= coreloop.BOARDSIZE && gapinboard == true){ //gap between boards
                    System.out.print("      " + x + " ");
                    gapinboard = false;
                }
                System.out.print(player1board[x][y]+" ");

            }
            System.out.println();
            gapinboard = true;
        }//end of for loop
    }//end of printboard

    static void clearboard(){//this clears the screen
    System.out.print("\033[H");
    System.out.print("\033[2J");
    System.out.println("\033[H");
  }//end clearboard
}//class end
