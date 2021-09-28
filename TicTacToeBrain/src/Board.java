public class Board {
    private int[][] realBoard;
    private boolean moveCheck;

    public Board (){
        this.realBoard = new int[3][3];

    }

    //Note StdDraw already uses Swing for Java

    public void setUpRealBoard(){
        for (int i = 0; i<realBoard.length; i ++){ //LENGTH OF ROWS
            for (int z = 0; z<realBoard[0].length; z++){ //arrayname[x], references the array being stored within the array, so this is column length
                                                            //Really you could fit any value instead of x and still get the length of columns
                realBoard[i][z] = 0;
                //Indicates the starter board, where no value has been placed
                //realBoard will only have a value of 0,1,2
            }
        }
    }


    public void printBoard(){
        int squareNumber = 0;
        for (int i = 0; i<realBoard.length; i++){
            //squareNumber++;
            for (int z = 0; z<realBoard[0].length; z++){
                squareNumber++;

                if (realBoard[i][z] == 1){
                    System.out.print("X");
                }
                else if (realBoard[i][z] == 2){
                    System.out.print("O");
                }
                else {
                    System.out.print(squareNumber);
                }

            }
            System.out.println(" ");
            //System.out.println("-----------------");
        }
        System.out.println(" ");
    }

    public void changeBoard(int sqNum, int realValue){
        //System.out.println(sqNum);
        boolean moveOk = true;
        int squareNumber = 0;
        for (int i = 0; i<realBoard.length; i++){
            //squareNumber++;
            for (int z = 0; z<realBoard[0].length; z++){
                moveOk = moveCheck(i,z,sqNum);
                //System.out.println(moveOk);
                squareNumber++;
                if ((squareNumber == sqNum)&&(moveOk)){
                    realBoard[i][z] = realValue;
                    this.moveCheck = true;
                }
                else if (((squareNumber == sqNum)&&(moveOk == false))){
                    //moveCheck(sqNum);
                    this.moveCheck = false;

                }
            }
            //System.out.println("-----------------");
        }
    }

    public void forceChangeBoard(int sqNum, int realValue){
        //Should only be used in tests within the Simulation class
        /*When going from the simulation class methods, we know that we will already be in a  valid range
            because int[] freeSpaces controls the spaces we test on our board.
            We know that we need to reset a space to 0 after we're done evaluating it, but normal changeBoard's
            call to private moveCheck, intereferes with this.
         */

        //System.out.println(sqNum);
        //boolean moveOk = true;
        int squareNumber = 0;
        for (int i = 0; i<realBoard.length; i++){
            //squareNumber++;
            for (int z = 0; z<realBoard[0].length; z++){
                //moveOk = moveCheck(i,z,sqNum);
                //System.out.println(moveOk);
                squareNumber++;

                if (squareNumber == sqNum){
                    realBoard[i][z] = realValue;
                    this.moveCheck = true;
                }

            }
            //System.out.println("-----------------");
        }
    }


    private boolean moveCheck(int i, int z,int sqNum){ //Makes sure the move is within the rules before the realBoard gets changed and printed
        Boolean bool = true;
        Boolean validRange = true;
        Boolean notAlreadyUsed = true;

        if (realBoard[i][z] == 0){
            notAlreadyUsed = true;
        }
        else {
            //System.out.println("Sorry this space has already been used... Try again, loser boy!"); //Couldnt get this to print properly. maybe later
            notAlreadyUsed = false;
        }


        if ((sqNum<=9)&&(sqNum>=1)){
            validRange = true;
        }
        else{
            validRange = false;
            //System.out.println("Sorry this number is too large, and doesn't fit on the board... Try again, stinky head!"); //Couldnt get this to print properly. maybe later
        }
        //System.out.println(bool);

        //System.out.println("ValidRange: "+validRange);

        if (validRange && notAlreadyUsed){
            bool = true;
        }
        else{
            bool = false;
        }

        return bool;
    }

    public boolean getMoveCheck(){
        //System.out.println("MoveCheck: "+this.moveCheck);
        return this.moveCheck;
    }

    /*
    public boolean moveCheck(int sqNum){ //Kind of redundant but oh well. The first moveCheck is private and only works within the changeBoard function
                                            //This one is public and is meant to manipulate the while loop in Game, in case the move is illegal.
        if ((sqNum>9)||(sqNum<1)){
            System.out.println("Sorry this number is too large, and doesn't fit on the board... Try again, stinky head!");
            bool = false;

        }
        else{
            for (int i = 0; i < realBoard[0].length; i++) {
                //squareNumber++;
                for (int z = 0; z < realBoard[1].length; z++) {
                    if (realBoard[i][z] == 0) {
                        bool = true;
                    } else {
                        bool = false;
                    }
                }

                //System.out.println("-----------------");
            }
        }
        //System.out.println(bool);





        return bool;
    }
*/


    public int[][] getRealBoard() {
        return realBoard;
    }

    public int[] getFreeSpaces(){

        int squareNumber = 0;
        int freeSpacesTotal = 0;
        for (int i = 0; i<realBoard.length; i++){
            for (int z = 0; z<realBoard[0].length; z++){
                squareNumber++;

                if (realBoard[i][z] == 0) {
                    freeSpacesTotal++;
                }
            }

        }

        squareNumber = 0;
        int[] freeSpaces = new int[freeSpacesTotal];
        int freeSpacesIndex = 0;

        for (int i = 0; i<realBoard.length; i++){
            for (int z = 0; z<realBoard[0].length; z++){
                squareNumber++;

                if (realBoard[i][z] == 0) {
                    freeSpaces[freeSpacesIndex]=squareNumber;
                    freeSpacesIndex++;
                }
            }

        }

        return freeSpaces;
    }

    public int checkWinner(){ // add a draw scenario
        int[][] b = returnAsLines();
        int count1 = 0;
        int count2 = 0;
        int val = 0;

        int count = 0;
        for (int i = 0; i<realBoard.length; i++){
            for (int j = 0; j<realBoard[0].length; j++){
                if (realBoard[i][j] == 1 || realBoard[i][j] == 2){
                    count++;
                }
            }
        }

        if (count != 9) { //If the count of total spaces occupied reaches nine before someone wins then that means its a draw

            //Note: This iterates down the rows of each column, rather than through the columns of one row
            for (int i = 0; i < b[0].length; i++) {
                count1 = 0;
                count2 = 0;
                for (int z = 0; z < b.length; z++) {
                    if (b[z][i] == 1) {
                        count1++;
                    } else if (b[z][i] == 2) {
                        count2++;
                    } else {

                    }

                }
                if (count1 == 3) {
                    val = 1;
                    break;
                }
                if (count2 == 3) {
                    val = 2;
                    break;
                }
            }
        }
        else {
            val = 3;
        }
        return val;
    }

    public int[][] returnAsLines() {
        int[][] lineBoard = new int[3][8];
        int lineBoardColumn = 0;


        for (int i = 0; i < realBoard.length; i++) { //iterating different columns                //Should add all of the columns to line board
            for (int z = 0; z < realBoard[0].length; z++) { //iterating different rows

                lineBoard[z][lineBoardColumn] = realBoard[z][i];
                //Having i, as the argument for rows allows returnAsLines to scale up, if we ever make a larger board
                //in this case i should max at 3 in this for loop

            }
            lineBoardColumn++;
        }
        //Rows are being printed correctly
        for (int i = 0; i < realBoard[0].length; i++) { //This adds all of the rows to line board
            for (int z = 0; z < realBoard.length; z++) {

                lineBoard[z][lineBoardColumn] = realBoard[i][z];
                //Having i, as the argument for rows allows returnAsLines to scale up, if we ever make a larger board
                //in this case i should max at 3 in this for loop

            }
            lineBoardColumn++;
        }
        /* Diagonal splitters are below*/
        int diagonalCounter = 0;
        //lineBoardColumn++;
        for (int i = 0; i < realBoard.length; i++) { //iterating different columns
            for (int z = 0; z < realBoard[0].length; z++) { //iterating different rows
                if (i == z) {
                    lineBoard[diagonalCounter][lineBoardColumn] = realBoard[i][z];
                    //Having i, as the argument for rows allows returnAsLines to scale up, if we ever make a larger board
                    //in this case i should max at 3 in this for loop
                    diagonalCounter++;
                }
            }
        }
        diagonalCounter = 0;
        lineBoardColumn++;
        //System.out.println(lineBoardColumn);
        for (int i = 0; i < realBoard.length; i++) {
            for (int z = 0; z < realBoard[0].length; z++) {
                if (z == (realBoard.length-i-1)) {

                    lineBoard[i][lineBoardColumn] = realBoard[i][z];
                    //Having i, as the argument for rows allows returnAsLines to scale up, if we ever make a larger board
                    //in this case i should max at 3 in this for loop
                    diagonalCounter++;

                }
            }
        }

        return lineBoard;
    }

    public void setRealBoard(int[][] realBoard) {
        this.realBoard = realBoard;
    }
}
