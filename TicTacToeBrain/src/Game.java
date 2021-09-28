import java.util.Scanner;


public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    public static int turn;

    private Visualizer viz;


    public Game(){

        this.board = new Board();
        this.player1 = new Player(board,'x',1);
        this.player2 = new Player(board,'o',2);
        this.turn = 1;

        viz = new Visualizer(true);

    }

    public void newGame(){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Player 1, will be represented by the "+player1.getCharacter()+" character.");
        System.out.println("Player 2, will be represented by the "+player2.getCharacter()+" character.");

        System.out.println("Show brute force algorithm? 'y' or 'n'.");

        String algoResponse = scanner.nextLine();
        boolean showAlgo = false;

        if (algoResponse.equals("y")){
            showAlgo = true;
        }

        viz.setAlgoMode(showAlgo);

        int time = 0;
        if (showAlgo){
            System.out.println("Enter the time for each frame to pass, in seconds.");


            String speedResponse = scanner.nextLine();
            time = (int)(Double.parseDouble(speedResponse)*1000);

        }

        scanner.close();
        playGame(time);
    }

    private void playGame(int frameTime){

        //Scanner scanner = new Scanner(System.in);
        board.setUpRealBoard();
        int numEntered;
        boolean game = true;
        boolean passMoveTest = true;
        Player first = firstMove();
        Player current = first;
        System.out.println("Player "+current.getRealValue()+" is going first!");
        board.printBoard();
        while (game){

            System.out.println("You are currently on turn #"+turn+".");
            System.out.println("Player "+current.getRealValue()+"'s turn. Please enter the number square you would like to change.");

            if (current.getRealValue() == 2){
                Simulation sim = new Simulation(board, viz, frameTime);

                numEntered = sim.getOptimalMove();
                System.out.println(" ");
                System.out.println("Brain says " +numEntered);
                //current.move(numEntered);
            }
            else {
                //On the player's turn, having a numEntered of -1 means they aren't clicking on the board
                numEntered = -1;

                while (numEntered == -1){
                    numEntered = viz.detectClick();
                }
                viz.clearMoveScores(); // on the players turn we ignore move scores;


            }

            //if the player is p2, run a sim.
            current.move(numEntered);

            passMoveTest = board.getMoveCheck();

            if (passMoveTest) {
                turn++;
                if ((turn % 2) == 0) {
                    current = this.player2;
                } else {
                    current = this.player1;
                }
            }
            StdDraw.clear();
            board.printBoard();
            viz.drawGameState(board);
            StdDraw.pause(500);



            if (board.checkWinner() == 1){
                System.out.println("Player wins!!!");
                game = false;
            }
            else if (board.checkWinner() ==2){
                System.out.println("Computer wins!!!");
                game = false;
            }
            else if (board.checkWinner() ==3){
                System.out.println("Draw!!!");
                game = false;
            }
        }

    }

    private Player firstMove(){ //Maybe get this to work randomized later

        return player1;
    }

}
