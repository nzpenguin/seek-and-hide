
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
    public static int PrintingBoardSize = coreloop.BOARDSIZE * 2;
    public static boolean gapinboard = true;
    public static boolean needresponce = true;
    static int player1board[][] = new int[PrintingBoardSize][PrintingBoardSize];
    static int boardgap = PrintingBoardSize - 4;
    static boolean GameRunning = true;
    static int playonemin = 0;
    static int playonemax = BOARDSIZE;
    static int playtwoXmin = BOARDSIZE;
    static int playtwoYmin = 0;
    static int playtwoXmax = PrintingBoardSize;
    static int playtwoYmax = BOARDSIZE + 1;
    static double numberofspotsforboard = Math.floor((BOARDSIZE * BOARDSIZE) * 0.31);
    static boolean acceptedinput = true;
    public static void main(String[] args){
        //double gameendchecker = numberofspotsforboard;
        double spotsleftforplayer1 = numberofspotsforboard;
        double spotsleftforplayer2 = numberofspotsforboard;
        boolean playerseekingside = true;
        boolean player1turn = true;
        int spotsdone = 0;
        boolean playerwin = false;
        boolean AIwin = false;
        while(spotsdone <= numberofspotsforboard && playerseekingside == true){//randomizes the seeking part of the board
            boardfillingone(playonemin, playonemax);
            spotsdone++;
            if (spotsdone == numberofspotsforboard){
                playerseekingside = false;
            }
        }
        
        spotsdone = 0;
        while(spotsdone <= numberofspotsforboard && playerseekingside == false){//should be randomizing the locations of the hiding part of the board, but there is a bug and i can't find it
            boardfillingtwo(playtwoXmin,playtwoYmin, playtwoXmax, playtwoYmax);
            spotsdone++;
            if (spotsdone == numberofspotsforboard){
                playerseekingside = true;
            }
        }
        
       // player1board[PrintingBoardSize -1][PrintingBoardSize -1] = 5;
        Scanner keyboard = new Scanner(System.in);
        while (GameRunning == true){//game running start
            printboard();
            String s1; // string needed for imput from scanner
            int xAxis = 0;
            int yAxis = 0;
            boolean intworking1 = false;
            boolean intworking2 = false;
            //boolean acceptedinput = true;
            while(needresponce == true && player1turn == true){
                s1 = keyboard.nextLine();//string being filled from input
                String inputverable[] = s1.split(",");//splitting the input
                if (inputverable.length != 2){
                    acceptedinput = false;
                }
                if (acceptedinput == true){
                    intworking1 = isInt(inputverable[1]);
                    intworking2 = isInt(inputverable[0]);
                    xAxis = getrow(inputverable[0], inputverable[1], intworking1, intworking2);
                    yAxis = getcol(inputverable[0], inputverable[1]);
                    if (xAxis == PrintingBoardSize +1 || yAxis == PrintingBoardSize + 1){
                        acceptedinput = false;
                    }else {
                        acceptedinput = true;
                    }
                }
                
                //System.out.println(yAxis +","+ xAxis);
                needresponce = false;
            }//end of whileloop    
            if (acceptedinput == true){
                if (player1board[xAxis][yAxis] == 0){//searched but not found
                player1board[xAxis][yAxis] = 2;
                player1turn = false;
                }else if (player1board[xAxis][yAxis] == 1 && playeroneturn == true){//found
                    player1board[xAxis][yAxis] = 3;
                    spotsleftforplayer1--;
                    player1turn = false;
                }else{
                    System.out.println("try again");
                }
            }else {
                System.out.println("try again");
            }
            //Ai code, causes the code to check if the player has done somethnig wrong to go off over and over again
            
            while (player1turn == false){
                int randomnumberX = 0;
                int randomnumberY = 0;
                randomnumberX = (int)Math.floor(Math.random()*(playtwoXmax-playtwoXmin)+playtwoXmin);
                randomnumberY = (int)Math.floor(Math.random()*(playtwoYmax-playtwoYmin)+playtwoYmin);
                if (player1board[randomnumberY][randomnumberX] == 0){//miss
                    player1board[randomnumberY][randomnumberX] = 2;
                    player1turn = true;
                } else if (player1board[randomnumberY][randomnumberX] == 1){//hit
                    player1board[randomnumberY][randomnumberX] = 3;
                    player1turn = true;
                    spotsleftforplayer2--;
                }
            }
            player1turn = true;
            printboard(); //prints out board
            needresponce = true;
            if (spotsleftforplayer1 == 0){
                GameRunning = false;
                playerwin = true;
            }
            if (spotsleftforplayer2 == 0){
                GameRunning = false;
                AIwin = true;
            }
        }//end of GameRunning
        keyboard.close();
        if (playerwin == true){
            System.out.println("well done player, you have won");
        } else if (AIwin == true){
            System.out.println("saddly the AI won, better luck next time");
        }else{
            System.out.println("how did you manage this one?");
        }
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
   static int getcol (String coord1, String coord2){//this turns the string into a char
        char col = coord1.charAt(0);
        if (col >= 'a' && col <= 'a' + (BOARDSIZE - 1)){
            return col - 'a';
        }else { col = coord2.charAt(0);
            if (col >='a' && col <= 'a' + (BOARDSIZE - 1)) {
                return col - 'a';
            }else {
                return  PrintingBoardSize +1;
            }
        }//end of if statment
    }//end of getcol
    
    static int getrow (String coord1, String coord2, boolean working1, boolean working2){//this turns the string into a int
        int row;
        if(working1 == true && working2 == false){
            row = Integer.parseInt(coord2);
        }else if (working1 == false && working2 == true) {
            row = Integer.parseInt(coord1);
        }else {
            return PrintingBoardSize +1;
        }
        
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
        System.out.println("Hello, this is a game of hide and seek,");
        System.out.println("The board labbled hide is where your people are hiding in");
        System.out.println("The board labbled seek is where you are trying to find people in");
        System.out.println("There are " + numberofspotsforboard + " hiding in the seek board at the start of the game, good luck");
        System.out.println();
        String gap;
        gap = " ";
        System.out.print("seek");
        System.out.print("         ");
        for(double i=0; i <= boardgap;){
            System.out.print(gap);
            i++;
        }

        System.out.println("hide");
        System.out.print("   ");
        letterline = 'a';
        for(int y=0;y<coreloop.BOARDSIZE;y++){
            System.out.print(letterline + gap);
            letterline++;
        }
        //prints out letter line on right side
        letterline = 'a';
        System.out.print("        ");
        for(int y=0;y<coreloop.BOARDSIZE;y++){
            System.out.print(letterline + gap);
            letterline++;
        }
        System.out.println();
        for(int y=0; y<BOARDSIZE;y++) {
            if ( y < 10){
                System.out.print(" " + y + gap);
            }else {
                System.out.print(y + gap);
            }
            for(int x=0;x<PrintingBoardSize;x++){
                if(x >= coreloop.BOARDSIZE && gapinboard == true){ //gap between boards
                    if ( y < 10){
                        System.out.print("      " + y + gap);
                    }else {
                        System.out.print("     " + y + gap);
                    }
                    gapinboard = false;
                }
                
                if (player1board[y][x] == 0){
                    System.out.print("  ");
                }else if(x <= BOARDSIZE && y <= BOARDSIZE && player1board[y][x] == 1){
                    System.out.print("  ");
                }else{
                    System.out.print(player1board[y][x] + gap);
                }
                
                //System.out.print(player1board[y][x] + gap);
            }
            System.out.println();
            gapinboard = true;
        }//end of for loop
        System.out.println("1 == something");
        System.out.println("2 == nothing there");
        System.out.println("3 == found something");
        System.out.println("Please enter location input as letter,number");
        if (acceptedinput == false){
            System.out.println("the last input you did was incorrect, please try again");
        }
    }//end of printboard

    static void clearboard(){//this clears the screen
        System.out.println("\033[H");
        System.out.println("\033[2J");
        System.out.println("\033[H");
    }//end clearboard

    static void boardfillingone(int min, int max){// this fills the "seek" side of the board
        boolean randomnumberneeded = true;
        int randomnumberX = 0;
        int randomnumberY = 0;
        while (randomnumberneeded == true){
            randomnumberX = (int)Math.floor(Math.random()*(max-min)+min);
            randomnumberY = (int)Math.floor(Math.random()*(max-min)+min);
            if (player1board[randomnumberX][randomnumberY] == 0){
                randomnumberneeded = false;
                player1board[randomnumberX][randomnumberY] = 1;
            }
        }
    }//end of boardfillingone
    static void boardfillingtwo(int Xmin,int Ymin, int Xmax, int Ymax){//this fills the "hide" side of the board
        int randomnumberX = 0;
        int randomnumberY = 0;
        boolean randomnumberneeded = true;
        while (randomnumberneeded == true){
            randomnumberX = (int)Math.floor(Math.random()*(Xmax-Xmin)+Xmin);
            randomnumberY = (int)Math.floor(Math.random()*(Ymax-Ymin)+Ymin);
            if (player1board[randomnumberY][randomnumberX] == 0){
                randomnumberneeded = false;
                player1board[randomnumberY][randomnumberX] = 1;
            }
        }
    }//end boardfilligtwo
}//class end
