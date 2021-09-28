public class Move {
    private int space;
    private int evaluation;

    public Move(){
        this.space = 0;
        this.evaluation = 0;
    }

    public Move(int space, int evaluation){
        this.space = space;
        this.evaluation = evaluation;

    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }
}
