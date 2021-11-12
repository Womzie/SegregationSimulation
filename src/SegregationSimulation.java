

// By Anders and Tucker


public class SegregationSimulation {

    public static void main(String[] args) {
        double similarity = .5;  //satisfaction threshold
        double redBlue = .5;  //percentage of red squares compared to blue squares
        double empty = .2;  //percentage of empty squares
        int size = 30;  //number of squares to a side of the grid

        boolean finished = false;

        int numEmpty = (int) (Math.pow(size, 2) * empty); //determines number of empty cells

        //setting grids
        int[][] cells = new int[size][size]; // 1 is red, 2 is blue, 0 is empty
        int[][] satisfied = new int[size][size]; // -1 if not satisfied, 0 if empty, 1 if satisfied

        //prepping drawing stuff
        StdDraw.setScale(-0.5, size - 0.5);
        StdDraw.enableDoubleBuffering();

        initCells(cells, numEmpty, redBlue, size);
        draw(size, cells, satisfied, similarity);

        while (!finished) {
            int redCount = 0; int blueCount = 0; // resets the counts to 0
            satisfactionMatrix(cells, satisfied, similarity); // sets satisfaction matrix
            if (finished(satisfied, cells)) finished = true; // tests if any cells are unsatisfied
            //unsatisfiedCount(cells, satisfied, redCount, blueCount);  // takes count for redistribution
            for(int x = 0; x < cells.length; x++) {
                for(int y = 0; y < cells[0].length; y++) {
                    if(satisfied[x][y] == -1){
                        if (cells[x][y] == 1) redCount++;
                        if (cells[x][y] == 2) blueCount++;
                    }
                }
            }
            moveCells(satisfied, cells, numEmpty, redCount, blueCount); // reshuffles unsatisfied cells randomly, using counts to remain accurate

            draw(size, cells, satisfied, similarity); // redraws the grid
        }
        StdOut.println("Done!");
    }



    public static boolean finished(int[][] satisfied, int[][] cells) {
        boolean finished = true;
        for(int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length; y++) {
                if (satisfied[x][y] == -1) {
                    finished = false;
                    break;
                }
            }
        }
        return finished;
    }




    public static void satisfactionMatrix(int[][] cells, int[][] satisfied, double similarity) {
        //finds if cells are satisfied or not, sets satisfaction matrix
        for(int x = 0; x < cells.length; x++) {
            for(int y = 0; y < cells[0].length; y++) {
                areCellsSatisfied(cells, satisfied, similarity, x, y);
                if(cells[x][y] == 0) satisfied[x][y] = 0;
            }
        }
    }

    public static void moveCells(int[][] satisfied, int[][] cells, int numEmpty, int redCount, int blueCount) {
        for (int i = 0; i < redCount; ++i) {
            int x = StdRandom.uniform(cells.length);
            int y = StdRandom.uniform(cells[0].length);
            if (satisfied[x][y] > 0) {
                --i;
            }
            else {
                cells[x][y] = 1;
                satisfied[x][y] = 1;
            }
        }
        for (int i = 0; i < blueCount; ++i) {
            int x = StdRandom.uniform(cells.length);
            int y = StdRandom.uniform(cells[0].length);
            if (satisfied[x][y] > 0) {
                --i;
            }
            else {
                cells[x][y] = 2;
                satisfied[x][y] = 1;
            }
        }
        for (int i = 0; i < numEmpty; ++i) {
            int x = StdRandom.uniform(cells.length);
            int y = StdRandom.uniform(cells[0].length);
            if (satisfied[x][y] > 0) {
                --i;
            }
            else {
                cells[x][y] = 0;
                satisfied[x][y] = 1;
            }
        }
    }


    public static boolean areCellsSatisfied(int[][] cells, int[][] satisfied, double similarity, int x, int y) {

        double countS = 0; //satisfactory cell counter
        double countU = 0; //unsatisfactory cell counter

        for (int x1 = x - 1; x1 <= x + 1; ++x1) {
            for (int y1 = y - 1; y1 <= y + 1; ++y1) {
                if (x1 >= 0 && x1 < cells.length && y1 >= 0 && y1 < cells.length && (x1 != x || y1 != y)) {
                    if (cells[x1][y1] == cells[x][y]) ++countS;
                    else if(cells[x1][y1] != 0) {
                        countU++;
                    }
                }
            }
        }

        double check = countS / (countS + countU);

        if(check >= similarity){
            satisfied[x][y] = 1;
            return true;
        }
        else {
            satisfied[x][y] = -1;
            return false;
        }
    }


    public static void draw(int size, int[][] cells, int[][] satisfied, double similarity){
        StdDraw.clear();

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {

                if(cells[x][y] == 1) {
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.filledSquare(x, y,0.5);
                }
                else if(cells[x][y] == 2) {
                    StdDraw.setPenColor(StdDraw.BLUE);
                    StdDraw.filledSquare(x, y,0.5);
                }
                else if(cells[x][y] == 0) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledSquare(x,y,0.5);
                }

                StdDraw.setPenColor(StdDraw.BLACK);

             if(!areCellsSatisfied(cells, satisfied, similarity, x, y) && cells[x][y] != 0) { //comment out in a minute
                    StdDraw.text(x,y,":("); // see who isn't satisfied
                }
                StdDraw.square(x,y,0.5);
            }
        }
        StdDraw.show();
    }

    //initCells places red and blue cells in the minefield randomly... does not deal with satisfaction at all
    public static void initCells(int[][] cells, int numEmpty, double redBlue, int size){
        int numRed  = (int) ((Math.pow(size,2) - numEmpty) * redBlue); //determines number of red cells
        int numBlue = (int) ((Math.pow(size,2) - numEmpty) * (1-redBlue)); //determines number of blue cells

        for (int i = 0; i < numRed; ++i) {
            int x = StdRandom.uniform(cells.length);
            int y = StdRandom.uniform(cells[0].length);
            if (cells[x][y] > 0) {
                --i;
            }
            else {
                cells[x][y] = 1;
            }
        }

        for (int i = 0; i < numBlue; ++i) {
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
