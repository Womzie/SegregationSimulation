public class SegSim {
    public static void main(String[] args) {
        int size = 30;


        int numE = (int) ((Math.pow(size,2) ) * 0.1);
        int numR = (int) ((Math.pow(size,2) - numE) * 0.5);
        int numB = (int) ( (Math.pow(size,2) - numE) * 0.5);

        double similar = 0.5;

        StdOut.println(numR + " " + numB + " " + numE);
        StdDraw.enableDoubleBuffering();
        int[][] cells = new int[size][size]; // 1 is red, 2 is blue, 0 is empty
        int[][] satisfied = new int[size][size]; // -1 if not satisfied, 0 if empty, 1 if satisfied

        initCells(cells, numR, numB);
        draw(size,cells,satisfied, similar);

        while(StdDraw.isMousePressed()){
            // after the mouse is clicked start the swapping
        }
        // run the swap



        }



    private static boolean areCellsSatisfied(int[][] cells, int[][] satisfied, double similar, int x, int y) {
        double countG = 0;
        double countB = 0;
        for (int x1 = x - 1; x1 <= x + 1; ++x1) {
            for (int y1 = y - 1; y1 <= y + 1; ++y1) {
                if (x1 >= 0 && x1 < cells.length &&
                        y1 >= 0 && y1 < cells.length &&
                        (x1 != x || y1 != y)) {
                    if (cells[x1][y1] == cells[x][y]) ++countG;
                    else if(cells[x1][y1] != 0){
                        countB ++;
                    }
                }
            }
        }
        double check = countG / (countG + countB);


        if(check >= similar){
            satisfied[x][y] = 1;
            return true;
        }
        else {
            satisfied[x][y] = -1;
            return false;
        }


        }




    public static void draw(int size, int[][] cells, int[][] satisfied, double similar){
        StdDraw.setScale(-0.5,size-0.5);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {


                if(cells[x][y] == 1){
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.filledSquare(x,y,0.5);
                    StdDraw.setPenColor(StdDraw.BLACK);
                }
                else if(cells[x][y] == 2){
                    StdDraw.setPenColor(StdDraw.BLUE);
                    StdDraw.filledSquare(x,y,0.5);
                    StdDraw.setPenColor(StdDraw.BLACK);
                }
                else if(cells[x][y] == 0){
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledSquare(x,y,0.5);
                    StdDraw.setPenColor(StdDraw.BLACK);
                }
                if(!areCellsSatisfied(cells, satisfied,similar, x, y) && cells[x][y] != 0){
                    StdDraw.text(x,y,":("); // just hand to see who isnt satisfied
                }

                StdDraw.square(x,y,0.5);
            }

        }
        StdDraw.show();
    }

    public static void initCells(int[][] cells, int numR, int numB){
        for (int i = 0; i < numR; ++i) {
            int x = StdRandom.uniform(cells.length);
            int y = StdRandom.uniform(cells[0].length);
            if (cells[x][y] > 0) {
                --i;
            }
            else {
                cells[x][y] = 1;
            }
        }


        for (int i = 0; i < numB; ++i) {
            int x = StdRandom.uniform(cells.length);
            int y = StdRandom.uniform(cells[0].length);
            if (cells[x][y] > 0) {
                --i;
            }
            else {
                cells[x][y] = 2;
            }
        }

    }

}
