public class Player {
    private Board board;
    private char character;
    private int realValue;

    public Player(Board board, char character, int realValue){
        this.board = board;
        this.character = character;
        this.realValue = realValue;


    }

     public void move(int sqNum){
        board.changeBoard(sqNum,this.realValue);
        //board.checkMove();
     }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public int getRealValue() {
        return realValue;
    }

    public void setRealValue(int realValue) {
        this.realValue = realValue;
    }
}
