import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Solves a maze, a grid of rows and columns, and paints each cell
 * a different color depending on where along the maze we are.
 * @author John Seibert
 */
public class MazeSolver extends GridPane {

    private final char[][] maze;

    private final int rows;
    private final int cols;

    private int entranceRow;
    private int entranceCol;
    private int exitRow;
    private int exitCol;

    private final int colIndex = 12;
    private int rowIndex;

    private int mazeCount;

    private static final int cellSize = 20;

    private final Scene originalScene;

    /**
     * Initializes the maze solver by setting up the input scene for
     * redirection as well as the rows and columns.
     * @param maze character array of maze inputted
     * @param originalScene input scene
     */
    public MazeSolver(char[][] maze, Scene originalScene) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
        this.originalScene = originalScene;
    }

    /**
     * Sets up the back button, the entrance, and the exit.
     */
    public void mazeSetup() {
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        Button back = new Button("Back");
        back.setOnAction(e -> goBack(this));
        buttonBox.getChildren().add(back);
        this.add(buttonBox, 0, 0);

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                if (maze[i][j] == 'B' || maze[i][j] == 'b')
                {
                    entranceRow = i;
                    entranceCol = j;
                } else if (maze[i][j] == 'E' || maze[i][j] == 'e')
                {
                    exitRow = i;
                    exitCol = j;
                }
            }
        }
        solveMaze(entranceRow, entranceCol);
    }

    /**
     * Finds paths using recursive backtracking.
     * @param row index of the row, so the n-1th row
     * @param col index of the column, or the n-1th column
     * @return if the maze can still be solved
     */
    public boolean solveMaze(int row, int col)
    {
        maze[entranceRow][entranceCol] = 'b';
        if (row >= 0 && row < rows && col >= 0 && col < cols && maze[row][col] != '1')
        {
            if (row == exitRow && col == exitCol)
            {
                maze[row][col] = 'E';
                if (mazeCount % colIndex == 0)
                {
                    rowIndex++;
                }
                displaySolution();
                mazeCount++;
                return true;
            }

            if (maze[row][col] != 'P')
            {
                maze[row][col] = 'P';

                boolean up = solveMaze(row - 1, col);
                boolean down = solveMaze(row + 1, col);
                boolean left = solveMaze(row, col - 1);
                boolean right = solveMaze(row, col + 1);

                maze[row][col] = '0';

                return up || down || left || right;
            }
        }
        return false;
    }

    /**
     * Formats a painted grid of entrance, exit, passage, and wall blocks
     * when a solution is possible.
     */
    public void displaySolution()
    {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                Rectangle rectangle = new Rectangle(cellSize, cellSize);
                switch (maze[i][j]) {
                    case '1':
                        rectangle.setFill(Color.BLACK);
                        break;
                    case 'P':
                    case 'p':
                        rectangle.setFill(Color.RED);
                        break;
                    case 'B':
                    case 'b':
                        rectangle.setFill(Color.BLUE);
                        break;
                    case 'E':
                    case 'e':
                        rectangle.setFill(Color.GREEN);
                        break;
                    default:
                        rectangle.setFill(Color.WHITE);
                        break;
                }
                gridPane.add(rectangle, j, i);
            }
        }
        this.add(gridPane, mazeCount % colIndex, rowIndex + 1);
        this.setHgap(2);
        this.setVgap(2);
    }

    /**
     * Redirects the user back to the input screen upon activation.
     * @param gp the input screen
     */
    public void goBack(GridPane gp)
    {
        Stage stage = (Stage) gp.getScene().getWindow();
        stage.setTitle("Maze Input");
        stage.setScene(originalScene);
    }
}