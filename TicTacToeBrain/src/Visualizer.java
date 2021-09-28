import java.awt.*;
import java.util.ArrayList;

public class Visualizer {

    private double border; //So that the visualization can be scaleable
    private boolean algoMode;
    private ArrayList<Move> moveScores; //A necessary workaround to put movescores up on the screen as the program works its way through
        //Arraylist because there will never be a fixed value for the amount of moves evaluated.

    public Visualizer(Boolean algoMode) {
        StdDraw.enableDoubleBuffering();
        this.algoMode = algoMode;
        this.moveScores = new ArrayList<Move>();

        if (algoMode) {
            border = 0.3; //For now we'll set the border to a constant dependent on a boolean since really there will only be two modes

            StdDraw.setXscale(0 - border, 1 + border);
            StdDraw.setYscale(0 - border, 1 + border);
        } else {
            border = 0;
        }
        drawBoard();
    }

    public void setAlgoMode(Boolean algoMode){
        this.algoMode = algoMode;

        //Changing algoMode must also change the border variable
        if (this.algoMode){
            border = 0.3; //For now we'll set the border to a constant dependent on a boolean since really there will only be two modes

            StdDraw.setXscale(0 - border, 1 + border);
            StdDraw.setYscale(0 - border, 1 + border);
            StdDraw.clear();
            drawBoard();
        }
        else{
            border = 0;
            StdDraw.setXscale(0, 1);
            StdDraw.setYscale(0 , 1);
            StdDraw.clear();
            drawBoard();
        }

    }

    public boolean getAlgoMode(){ //literally the same method as isAlgoMode()
        return this.algoMode;
    }

    public boolean isAlgoMode(){
        return this.algoMode;
    }

    private void drawX(double x1, double y1, double x2, double y2) { //endpoints for one of the lines
        StdDraw.setPenColor(Color.GREEN);
        StdDraw.line(x1, y1, x2, y2);
        StdDraw.line(x1, y2, x2, y1);
        StdDraw.show();
    }

    private void drawO(double x, double y) {
        StdDraw.setPenColor(Color.RED);
        double radius = 0.3333333333 / 2; //Not the best idea to hard code this, will look wonky if the grid is ever smaller than the canvas

        StdDraw.circle(x + radius, y - radius, radius);
    }

    public void drawGameState(Board realBoard) {
        StdDraw.clear();
        drawBoard(); //First draws the board background

        int[][] b = realBoard.getRealBoard();

        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {

                if (b[i][j] == 1) { //Board coordinates are flipped from array so extra math is needed

//                    System.out.println("i = "+i +" j = "+j);
                    drawX((j / 3.0), 1 - i / 3.0, ((j + 1) / 3.0), 1 - (i + 1) / 3.0);

                } else if (b[i][j] == 2) {
                    drawO(j / 3.0, 1 - i / 3.0); //x1 y1, top left corner
                } else {

                }
            }
        }

        if (algoMode){
            drawMoveScores(); //This way each time the gamestate is redrawn move scores will be displayed
            //now just figure out away to make them be only displayed during the bot's turn.
        }

        StdDraw.show();
    }

    public void highlight(int boardAsLinesIndex) { //Will circle the line in interest

        if (algoMode) {
        /*must use return as lines here somehow
        diagonals will have different standard draw parameters than verticals and horizontals
         */

            double width = 0.25;
            double radius = width / 2;

            double startTheta = 0;
            double endTheta = 0;
            //for the arc on the opposite end, just switch the start and end thetas, it'll make the opposite arc

            double x1 = 0;
            double y1 = 0;
            double x2 = 0;
            double y2 = 0;

            StdDraw.setPenColor(Color.BLACK);
            if (0 <= boardAsLinesIndex && boardAsLinesIndex <= 2) { //For highlighting columns
                startTheta = 180;
                endTheta = 0;
                y1 = 0.0;
                y2 = 1;
                switch (boardAsLinesIndex) {
                    case 0:
                        x1 = (1.0 / 3) - (1.0 / 6);

                        break;
                    case 1:

                        x1 = (2.0 / 3) - (1.0 / 6);

                        break;
                    case 2:

                        x1 = (1.0) - (1.0 / 6);

                        break;
                    default:
                        break;
                }
                x2 = x1;


                StdDraw.line(x1 - radius, y1, x1 - radius, y2);
                StdDraw.line(x1 + radius, y1, x1 + radius, y2);

            } else if (3 <= boardAsLinesIndex && boardAsLinesIndex <= 5) { //For highlighting rows


                startTheta = 90;
                endTheta = -90;
                x1 = 0.0;
                x2 = 1.0;
                switch (boardAsLinesIndex) {
                    case 3:
                        y1 = (1.0 / 3) - (1.0 / 6);

                        break;
                    case 4:
                        y1 = (2.0 / 3) - (1.0 / 6);
                        break;
                    case 5:
                        y1 = 1.0 - (1.0 / 6);
                    default:
                        break;
                }
                y2 = y1;


                StdDraw.line(x1, y1 - radius, x2, y1 - radius);
                StdDraw.line(x1, y1 + radius, x2, y1 + radius);

            } else { //diagonals are at indices 6 and 7

                double adjRadius = radius / Math.sqrt(2); //Need to use trig since we're at a 45 degree angle
                startTheta = (45);
                endTheta = 225;
                x1 = 0;
                x2 = 1;
                y1 = 1;
                y2 = 0; //These will be longer than the ones for columns and rows


                if (boardAsLinesIndex == 6) {

                    StdDraw.line(x1 + adjRadius, y1 + adjRadius, x2 + adjRadius, y2 + adjRadius);
                    StdDraw.line(x1 - adjRadius, y1 - adjRadius, x2 - adjRadius, y2 - adjRadius);
                } else if (boardAsLinesIndex == 7) { //Reverse the line of index 6 in the board as lines array

                    x1 = 0;
                    x2 = 1;
                    y1 = 0;
                    y2 = 1;
                    startTheta += 90; //90 degree rotation
                    endTheta += 90;

                    StdDraw.line(x1 - adjRadius, y1 + adjRadius, x2 - adjRadius, y2 + adjRadius);
                    StdDraw.line(x1 + adjRadius, y1 - adjRadius, x2 + adjRadius, y2 - adjRadius);
                }

                StdDraw.show();

            }

            StdDraw.arc(x1, y1, radius, startTheta, endTheta);
            StdDraw.arc(x2, y2, radius, endTheta, startTheta);

            StdDraw.show();
        }
    }

    public void highlight(int boardAsLinesIndex, String lineText, String sumText) { //Overloaded to also include a display of an individual line's score.
        if (algoMode) {
            double width = 0.25;
            double radius = width / 2;

            double textMargin = 0.1;

            double startTheta = 0;
            double endTheta = 0;

            double x1 = 0;
            double y1 = 0;
            double x2 = 0;
            double y2 = 0;

            StdDraw.setPenColor(Color.BLACK);
            if (0 <= boardAsLinesIndex && boardAsLinesIndex <= 2) {
                startTheta = 180;
                endTheta = 0;
                y1 = 0.0;
                y2 = 1;
                switch (boardAsLinesIndex) {
                    case 0:
                        x1 = (1.0 / 3) - (1.0 / 6);

                        break;
                    case 1:

                        x1 = (2.0 / 3) - (1.0 / 6);

                        break;
                    case 2:

                        x1 = (1.0) - (1.0 / 6);

                        break;
                    default:
                        break;
                }
                x2 = x1;


                StdDraw.line(x1 - radius, y1, x1 - radius, y2);
                StdDraw.line(x1 + radius, y1, x1 + radius, y2);

                StdDraw.text(x1, y2 + textMargin + radius, lineText);

            } else if (3 <= boardAsLinesIndex && boardAsLinesIndex <= 5) {
                startTheta = 90;
                endTheta = -90;
                x1 = 0.0;
                x2 = 1.0;
                switch (boardAsLinesIndex) {
                    case 3:
                        y1 = 1.0 - (1.0 / 6);

                        break;
                    case 4:
                        y1 = (2.0 / 3) - (1.0 / 6);
                        break;
                    case 5:
                        y1 = (1.0 / 3) - (1.0 / 6);
                    default:
                        break;
                }
                y2 = y1;


                StdDraw.line(x1, y1 - radius, x2, y1 - radius);
                StdDraw.line(x1, y1 + radius, x2, y1 + radius);
                StdDraw.text(x2 + textMargin + radius, y1, lineText);

            } else {

                double adjRadius = radius / Math.sqrt(2);
                startTheta = (45);
                endTheta = 225;
                x1 = 0;
                x2 = 1;
                y1 = 1;
                y2 = 0;
                if (boardAsLinesIndex == 6) {

                    StdDraw.line(x1 + adjRadius, y1 + adjRadius, x2 + adjRadius, y2 + adjRadius);
                    StdDraw.line(x1 - adjRadius, y1 - adjRadius, x2 - adjRadius, y2 - adjRadius);
                    StdDraw.text(x2 + adjRadius + (textMargin), y2 - adjRadius - (textMargin), lineText);
                } else if (boardAsLinesIndex == 7) {

                    x1 = 0;
                    x2 = 1;
                    y1 = 0;
                    y2 = 1;
                    startTheta += 90;
                    endTheta += 90;

                    StdDraw.line(x1 - adjRadius, y1 + adjRadius, x2 - adjRadius, y2 + adjRadius);
                    StdDraw.line(x1 + adjRadius, y1 - adjRadius, x2 + adjRadius, y2 - adjRadius);
                    StdDraw.text(x2 + adjRadius + (textMargin), y2 + adjRadius + (textMargin), lineText);
                }

                StdDraw.show();

            }

            StdDraw.arc(x1, y1, radius, startTheta, endTheta);
            StdDraw.arc(x2, y2, radius, endTheta, startTheta);

            StdDraw.text(0, -2 * textMargin, "Running Sum: " + sumText);


            StdDraw.show();
        }
    }

    private void drawBoard() {
        StdDraw.setPenColor(Color.black);
        StdDraw.line(1, 1 / 3.0, 0, 1 / 3.0);
        StdDraw.line(1, 2 / 3.0, 0, 2 / 3.0);

        StdDraw.line(1 / 3.0, 1, 1 / 3.0, 0);
        StdDraw.line(2 / 3.0, 1, 2 / 3.0, 0);

        StdDraw.show();
    }

    public void addMoveScore(Move evaluatedMove){ //Will be used to add to update move scores live as the program runs
        if (algoMode){

            moveScores.add(evaluatedMove);
        }
    }

    private void drawMoveScores(){
        if (algoMode) {
            int yBlock = 1;
            int xBlock = 1;

            for (Move m : moveScores) {
                int n = m.getSpace();

                double x = 1.0 - 1.0 / 6; //Calculating the exact position
                double y = 1.0 - 1.0 / 6; //Starting in the top left hand corner

                if (1 <= n && n <= 3) { //Needed because of how I coded the spaces
                    y = 1.0 - 1.0 / 6;
                } else if (3 < n && n <= 6) {
                    y -= 1.0 / 3;
                } else if (6 < n && n <= 9) {
                    y -= 2.0 / 3;
                } else {
                }

                if (n % 3 == 0) { //last column
                    x = 1.0 - 1.0 / 6;
                } else if (n % 3 == 1) { //first column
                    x -= 2.0 / 3;
                } else if (n % 3 == 2) { //second column
                    x -= 1.0 / 3;
                } else {

                }

                StdDraw.setPenColor(Color.RED);
                StdDraw.text(x, y, m.getEvaluation() + "");
                StdDraw.show();
            }
        }
    }

    public void clearMoveScores(){
        moveScores = new ArrayList<Move>();
    }

    public int detectClick() { //calculates and returns the numbered square in which the mouse was pressed
        int value = 0;

        while (StdDraw.isMousePressed() == false) {

        }
        double mouseX = 0.0;
        double mouseY = 0.0;
        double arrayY = 0.0;
        if (StdDraw.isMousePressed() && ((0.0 < StdDraw.mouseX() && StdDraw.mouseX() <= 1.0)
                && (0.0 < StdDraw.mouseY() && StdDraw.mouseY() <= 1.0))) {
            mouseX = StdDraw.mouseX();
            mouseY = StdDraw.mouseY();

            //multiply X by 3 then round down and add 1;
            mouseX *= 3;

            arrayY = 1 - mouseY;
            arrayY *= 3;

            int yModifier = 0;
            if (0.0 < arrayY && arrayY <= 1.0) { //Needed because of how I coded the spaces
                yModifier = 1;
            } else if (1.0 < arrayY && arrayY <= 2.0) {
                yModifier = 4;
            } else if (2.0 < arrayY && arrayY <= 3.0) {
                yModifier = 7;
            } else {

            }
            value = (int) mouseX + yModifier;
            return value;
        } else {
            return -1; //return value for not clicking on the board
        }


    }


}
