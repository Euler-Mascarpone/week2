package exercises;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Arrays;

import static java.lang.Math.round;
import static java.lang.Math.sqrt;
import static java.lang.System.exit;
import static java.lang.System.out;

/*
 * Program for Conway's game of life.
 * See https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
 *
 * This is a graphical program using JavaFX to draw on the screen.
 * There's a bit of "drawing" code to make this happen (far below).
 * You don't need to implement (or understand) any of it.
 * NOTE: To run tests must uncomment in init() method, see comment
 *
 * Use smallest step development + functional decomposition!
 *
 * See:
 * - UseEnum
 * - BasicJavaFX (don't need to understand, just if you're curious)
 */
public class Ex4GameOfLife extends Application {

    final Random rand = new Random();

    // Enum type for state of Cells
    enum Cell {
        DEAD, ALIVE;
    }

    // This is the *only* accepted modifiable instance variable in program except ...
    Cell[][] world;

    @Override
    public void init() {
        //test();        // <--------------- Uncomment to test!
        int nLocations = 10000;
        double distribution = 0.15;   // % of locations holding a Cell


        world = worldCreator(nLocations, distribution);
    }


    // Implement this method (using functional decomposition)
    // Every involved method should be tested, see below, method test()
    // This method is automatically called by a JavaFX timer (don't need to call it)


    void update() {
        int dim = world.length;
        Cell[][] worldTemp = new Cell[dim][dim]; //Best to make a separate copy;
        for (int i = 0; i < dim; i++) { //Looping through rows
            for (int j = 0; j < dim; j++) { //And columns
                Cell tempCell = world[i][j];
                int k = cellCounter(i, j, world); //Number of alive neighbouring cells
                if (tempCell == Cell.ALIVE) {
                    if (k < 2) {
                        worldTemp[i][j] = Cell.DEAD;
                    } else if(k == 2 || k==3 ) {
                        worldTemp[i][j] = Cell.ALIVE;
                    }else{ //k > 3, overpopulation
                        worldTemp[i][j] = Cell.DEAD;
                    }
                }
                if(tempCell == Cell.DEAD && k==3){ //Reproduction
                    worldTemp[i][j] = Cell.ALIVE;
                }
            }
        }
        world = worldTemp;
    }

    // -------- Write methods below this --------------

    // TODO

    int cellCounter(int i, int j, Cell[][] world) { //Counts the number of neighbouring live cells
        int sum = 0;
        int size = world.length;
        for (int iTemp = i - 1; iTemp <= i + 1; iTemp++) { //Variable already initialized
            for (int jTemp = j - 1; jTemp <= j + 1; jTemp++) {
                if (isValidLocation(size, iTemp, jTemp) && (iTemp != i || jTemp != j) && world[iTemp][jTemp] == Cell.ALIVE) { //If it isn't out of bounds and not the cell in question and alive
                    sum++;
                }
            }
        }
        return sum;
    }

    Cell[][] worldCreator(int size, double dist) {
        int alive = (int) Math.round(size * dist); //Number of alive cells
        int dimension = (int) Math.round(sqrt(size)); //Dimensions
        return getRand(alive, dimension); //Creates the world
    }

    Cell[][] getRand(int n, int dim) {
        Cell[][] arr = new Cell[dim][dim]; //Empty array of size x size numbers
        int totSize = dim * dim;
        int randNumb;
        for (int i = 0; i < n; i++) { //Assume n first elements are alive, they will now be shuffled
            randNumb = rand.nextInt(totSize - i); //Better shuffling algorithm than in ex1, takes one element at a time.
            int[] iCoords = indexMat(i, dim);
            int[] randCoords = indexMat(randNumb + i, dim); //Corresponding row and column in matrix, we add i because we do not want to pick one of the elements we've already shuffled
            if (randNumb + i < n) { //then both are alive because first n are alive, remember that we can't pick something already shuffled
                arr[iCoords[0]][iCoords[1]] = Cell.ALIVE; //Actually makes them alive
                arr[randCoords[0]][randCoords[1]] = Cell.ALIVE;
            } else if (arr[randCoords[0]][randCoords[1]] == null) { //Then we picked something outside, which has not been shuffled before i.e is dead
                arr[iCoords[0]][iCoords[1]] = Cell.DEAD;
                arr[randCoords[0]][randCoords[1]] = Cell.ALIVE; //Switches the cells
            } else { //Then we've encountered something outside which is either dead or alive
                arr[iCoords[0]][iCoords[1]] = arr[randCoords[0]][randCoords[1]];
                arr[randCoords[0]][randCoords[1]] = Cell.ALIVE; //Switches places
            }
        }
        Cell k; //We'll need this later
        for (int i = n; i < dim * dim; i++) { //Time to loop the rest
            randNumb = rand.nextInt(totSize - i); //Basically the same code
            int[] iCoords = indexMat(i, dim);
            int[] randCoords = indexMat(randNumb + i, dim);
            if (arr[randCoords[0]][randCoords[1]] == null && arr[iCoords[0]][iCoords[1]] == null) { //i.e they are dead
                arr[randCoords[0]][randCoords[1]] = Cell.DEAD;
                arr[iCoords[0]][iCoords[1]] = Cell.DEAD; //They were dead all along
            } else if (arr[randCoords[0]][randCoords[1]] == null) { //Then iCoord element has to be not null
                k = arr[iCoords[0]][iCoords[1]]; //Saves for later
                arr[iCoords[0]][iCoords[1]] = Cell.DEAD;
                arr[randCoords[0]][randCoords[1]] = k; //Switches places
            } else if (arr[iCoords[0]][iCoords[1]] == null) {
                k = arr[randCoords[0]][randCoords[1]]; //Saves for later
                arr[randCoords[0]][randCoords[1]] = Cell.DEAD;
                arr[iCoords[0]][iCoords[1]] = k; //Switches places
            } else { //Both are not null so just switch
                k = arr[iCoords[0]][iCoords[1]]; //Saves for later
                arr[iCoords[0]][iCoords[1]] = arr[randCoords[0]][randCoords[1]];
                arr[randCoords[0]][randCoords[1]] = k; //Switches places
            }
        }
        return arr;

    }

    int[] indexMat(int k, int dim) { //Converts an index from an array to an index in a 2d-array(square), left to right
        int[] index = {k / dim, k % dim}; //Row and column
        return index;
    }


    // Check if inside world
    boolean isValidLocation(int size, int row, int col) {
        return 0 <= row && row < size && 0 <= col && col < size;
    }

    void plot(Cell[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            out.println(Arrays.toString(matrix[row]));
        }
    }

    // ---------- Testing -----------------
    // Here you run your tests i.e. call your logic methods
    // to see that they really work
    void test() {
        // Hard coded test world
        Cell[][] testWorld = {
                {Cell.ALIVE, Cell.ALIVE, Cell.DEAD},
                {Cell.ALIVE, Cell.DEAD, Cell.DEAD},
                {Cell.DEAD, Cell.DEAD, Cell.ALIVE},
        };
        int size = testWorld.length;
        out.println(isValidLocation(size, 0, 0));  // All should be true
        out.println(!isValidLocation(size, 0, 3));


        exit(0);
    }

    // -------- Below is JavaFX stuff, nothing to do --------------

    void render() {
        gc.clearRect(0, 0, width, height);
        int size = world.length;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int x = 3 * col + 50;
                int y = 3 * row + 50;
                renderCell(x, y, world[row][col]);
            }
        }
    }

    void renderCell(int x, int y, Cell cell) {
        if (cell == Cell.ALIVE) {
            gc.setFill(Color.RED);
        } else {
            gc.setFill(Color.WHITE);
        }
        gc.fillOval(x, y, 3, 3);
    }

    final int width = 400;   // Size of window
    final int height = 400;

    GraphicsContext gc;

    // Must have public before more later.
    @Override
    public void start(Stage primaryStage) throws Exception {

        // JavaFX stuff
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        root.getChildren().addAll(canvas);
        gc = canvas.getGraphicsContext2D();

        // Create a timer
        AnimationTimer timer = new AnimationTimer() {

            long timeLastUpdate;

            // This method called by FX at a certain rate, parameter is the current time
            public void handle(long now) {
                if (now - timeLastUpdate > 300_000_000) {
                    update();
                    render();
                    timeLastUpdate = now;
                }
            }
        };
        // Create a scene and connect to the stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game of Life");
        primaryStage.show();
        timer.start();  // Start simulation
    }

    public static void main(String[] args) {
        launch(args);   // Launch JavaFX
    }
}