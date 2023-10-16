import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Launches a GUI that asks the user to enter inputs for the number of rows,
 * columns, and the maze itself.
 * @author John Seibert
 */
public class MazeInput extends Application {

    private TextField rowsField;
    private TextField colsField;
    private TextField mazeField;

    private Scene s;

    private Button submitButton;

    /**
     * Loads text fields for the user to enter details into.
     * @param primaryStage the primary stage for this application onto
     * which the application scene can be set; The primary stage will
     * be embedded in the browser if the application was launched as
     * an applet. Applications may create other stages, if needed,
     * but they will not be primary stages and will not be
     * embedded in the browser. (anything after semicolon is boilerplate)
     */
    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        Label rowsLabel = new Label("Number of rows:");
        Label colsLabel = new Label("Number of columns:");
        Label mazeLabel = new Label("Maze (B for entrance, " +
                "E for exit, 0 for passage, 1 for wall):");
        Label sampleLabel = new Label("Sample 5x5 maze: " +
                "\n 11111B00011010E1000111111");
        Label warningLabel = new Label("WARNING: Enter " +
                "a maze with a small number of solutions. \n"
        + "This will help you avoid crashing the program.");
        Label authorLabel = new Label("Created with " +
                "love by: \n John Seibert");

        rowsField = new TextField();
        colsField = new TextField();
        mazeField = new TextField();

        submitButton = new Button("Submit");
        submitButton.setOnAction(e -> submitMaze());

        pane.add(rowsLabel, 0, 0);
        pane.add(rowsField, 1, 0);
        pane.add(colsLabel, 0, 1);
        pane.add(colsField, 1, 1);
        pane.add(mazeLabel, 0, 2);
        pane.add(mazeField, 1, 2);
        pane.add(sampleLabel, 0, 3);
        pane.add(warningLabel, 0, 4);
        pane.add(authorLabel, 1, 4);
        pane.add(submitButton, 1, 3);

        Scene scene = new Scene(pane, 500, 200);
        s = scene;
        primaryStage.setTitle("Maze Input");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Submits the maze details for use in the MazeSolver class.
     */
    private void submitMaze() {
        try {
            int rows = Integer.parseInt(rowsField.getText());
            int cols = Integer.parseInt(colsField.getText());
            String mazeString = mazeField.getText();

            if (rows <= 0 || cols <= 0 || mazeString.length() != rows * cols)
            {
                throw new Exception("Invalid maze details");
            }

            char[][] maze = new char[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    maze[i][j] = mazeString.charAt(i * cols + j);
                }
            }

            Stage stage = new Stage();
            MazeSolver solver = new MazeSolver(maze, s);
            stage.setTitle("Maze Solver");
            stage.setScene(new Scene(solver));
            solver.mazeSetup();
            stage.show();

            Stage currentStage = (Stage) submitButton.getScene().getWindow();
            currentStage.close();

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Launches the application for JavaFX.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}