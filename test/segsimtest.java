import static org.junit.jupiter.api.Assertions.*;

class segsimtest {

    @org.junit.jupiter.api.Test
            void satisfied() {
                int numE = 4;
                int numR = 3;
                int numB = 2;
                int[][] satisfied = new int[3][3];
                int[][] cells = new int[3][3];
                cells[0][1] = 1;
                cells[1][1] = 1;
                cells[1][2] = 1;
                cells[2][1] = 2;
                cells[2][2] = 2;

                assertEquals(true, SegregationSimulation.areCellsSatisfied(cells, satisfied, 0.5, 1,1));
                assertEquals(false, SegregationSimulation.areCellsSatisfied(cells, satisfied, 0.5, 2,1));

                assertFalse(SegregationSimulation.finished(satisfied, cells));

            }


}