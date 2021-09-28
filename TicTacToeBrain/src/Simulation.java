public class Simulation {
        private Board board;
        private Board simBoard;
        private Move[] possibleMoves;
        private int[] freeSpaces;
        private Visualizer viz;
        int frameTime;

        public Simulation(Board board, Visualizer viz, int frameTime){ //Must accept the visualizer instance used in game such that the process can be seen.
            this.board = board;
            //this.possibleMoves = board.getFreeSpaces();

            this.freeSpaces = board.getFreeSpaces();
            this.possibleMoves = new Move[freeSpaces.length];

            this.frameTime = frameTime;
            this.simBoard = new Board(); //Sets up a new object which is our simBoard

            this.simBoard.setUpRealBoard();
            /*
                Using a for loop to copy the contents for board onto the separate simBoard object
                its important to do this, because using normal getters and setters, simBoard will end up
                referring to the same object in memory as board. Which means all of our tests will appear
                on the actual game board, and we don't want that.
             */
            int squareNumber =0 ;
            for(int i = 0; i<board.getRealBoard().length; i++){
                for(int z = 0; z<board.getRealBoard()[0].length; z++){
                    squareNumber++;
                    this.simBoard.changeBoard(squareNumber,board.getRealBoard()[i][z]);
                }
            }
                this.viz = viz;     //otherwise we don't need to instantiate it

        }

        public int getOptimalMove(){

            for (int i=0; i<freeSpaces.length; i++){
                Move evaluatedMove;
                evaluatedMove = evaluator(freeSpaces[i]);
                possibleMoves[i] = evaluatedMove;
            }

            int highestEval = Integer.MIN_VALUE; //Lowest possible evaluation is like -4 or something
            Move optimalMove = new Move();
            for (int i=0; i<possibleMoves.length; i++){
                int currentEval = possibleMoves[i].getEvaluation();



                if (currentEval>highestEval){
                    highestEval = currentEval;
                    optimalMove.setEvaluation(possibleMoves[i].getEvaluation());
                    optimalMove.setSpace(possibleMoves[i].getSpace());
                }
            }
            if (viz.getAlgoMode()){
                viz.drawGameState(board);
                StdDraw.pause(2000); //Shows the end result of calculations, then clears
                viz.clearMoveScores();
                viz.drawGameState(board);
            }


            return optimalMove.getSpace();
        }

        private Move evaluator(int testSpace){

            simBoard.changeBoard(testSpace,2); //Right now I'm going to fix the AI to be player2

            int[][] lines = simBoard.returnAsLines();
            int score = 0;

            boolean multipleTwos = false; //because we constantly add a two for testing purposes
            boolean aggro = false;

            int count2 = 0;
            for (int i=0; i<simBoard.getRealBoard().length; i++){

                for (int z=0; z<simBoard.getRealBoard()[0].length; z++){
                    if (simBoard.getRealBoard()[i][z] == 2){
                        count2 ++;
                    }
                }
            }
            if (count2>1){
                multipleTwos = true;
            }

            if(simBoard.getFreeSpaces().length<=2){
                aggro = true;
            }


            for (int i =0; i<lines[0].length; i++){ //Iterate columns on the outside, rows on the inside, so for each column, we check the 3 rows in that column.
                int deltaScore = 0;
                int ones = 0;
                int twos = 0;
                int zeros = 0;



                for (int z = 0; z<lines.length; z++){ //We want to check by column, not by row
                    switch(lines[z][i]){
                        case 1:
                            ones++;
                            break;
                        case 2:
                            twos++;
                            break;
                        default:
                            zeros++;
                            break;
                    }
                }


                /*HEURISTIC ALGORITHM*/

//                The algorithm is still a bit dumb when it comes to winning
//                        but it can draw every time.
                if (((ones == 0) && (twos == 3))&&(multipleTwos)&&(aggro)){
                    deltaScore = 10;
                }
                else if ((ones == 1) && (twos == 0)){
                    deltaScore = -1;
                }
                else if ((ones == 2) && (twos == 0)){
                    deltaScore = -3;
                }
                else if ((ones == 0) && (twos == 1)){
                    deltaScore = 1;
                }
                else if (((ones == 0) && (twos == 2))&&(multipleTwos)){
                    deltaScore = 1;
                }
                else if (((ones == 0) && (twos == 3))&&(multipleTwos)){
                    deltaScore = 3;
                }

                else{
                    deltaScore = 0;
                }

                score += deltaScore;

                if (viz.getAlgoMode()){
                    viz.drawGameState(simBoard);
                    viz.highlight(i, ""+deltaScore, ""+score);
                    StdDraw.show();
                    StdDraw.pause(frameTime);
                    StdDraw.clear();
                }



                //Insert visualization for summing the score together to produce a total evaluation for this move
            }

            simBoard.printBoard();
            simBoard.forceChangeBoard(testSpace,0); //Changing the test space back to zero, for the next method call to work properly


            Move evaluatedMove = new Move(testSpace, score);

            if (viz.getAlgoMode()){
                viz.addMoveScore(evaluatedMove);
            }


            return evaluatedMove;
        }
}
