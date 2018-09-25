import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.canvas.GraphicsContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class DrawChartController {

    @FXML
    private Canvas canvas;

    @FXML
    private TextField myTextField;

    @FXML
    void drawChartButtonPressed(ActionEvent event) {

        //get graphicscontext
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //clear canvas
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        //collect info from GUI text field
        int n_occurrences = Integer.parseInt(myTextField.getText());
        if (n_occurrences > 28) {
            System.err.println("Invalid input.  Application terminated.");
            System.exit(0);
        }

        //get file
        File f = new File("/Users/School/Desktop/CSC221/Emma.txt");
        FileReader fr = null;

        try {
            fr = new FileReader(f);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }

        BufferedReader br = new BufferedReader(fr);

        //create list of frequencies
        HistogramLetters histogramletters = new HistogramLetters();
        histogramletters.setFrequencies(br);

        //create and draw pie chart
        PieChart piechart = new PieChart(histogramletters);
        piechart.draw(gc, n_occurrences);
    }
}