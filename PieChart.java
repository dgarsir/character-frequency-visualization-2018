import java.util.HashMap;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class PieChart {

    //private variables
    private HistogramLetters data;
    private double radius = 300;

    //constructor
    public PieChart(HistogramLetters histogramletters) {

        data = histogramletters;

    }

    //public methods
    public void draw(GraphicsContext gc, int n_occurrences) {

        Color[] colors = {Color.RED, Color.BLACK, Color.BLUE, Color.YELLOW, 
                          Color.PINK, Color.GREEN, Color.GAINSBORO, Color.WHEAT, 
                          Color.PALEVIOLETRED, Color.AQUAMARINE, Color.POWDERBLUE, 
                          Color.PEACHPUFF, Color.THISTLE, Color.CYAN, Color.SILVER,
                          Color.SANDYBROWN, Color.DEEPPINK, Color.CHARTREUSE, 
                          Color.MEDIUMSLATEBLUE, Color.KHAKI, Color.CHOCOLATE, 
                          Color.CRIMSON, Color.OLIVEDRAB, Color.STEELBLUE, Color.ORANGE,
                          Color.PURPLE, Color.TEAL, Color.HONEYDEW, Color.DIMGRAY, 
                          Color.SADDLEBROWN, Color.DARKSLATEGRAY, Color.GOLDENROD, 
                          Color.INDIGO, Color.GHOSTWHITE, Color.LIME,
                          Color.HOTPINK, Color.LEMONCHIFFON, Color.PLUM};

        //important local variables
        double originX = gc.getCanvas().getWidth()/2;
        double originY = gc.getCanvas().getHeight()/2;
        double sumFrequencies = data.getSumFrequencies();
        double remainingFrequency = 1;
        double start_angle = 0;
        double unfilled_angle = 360;
        double text_box_radius_offset = radius + 50;
        double text_box_X;
        double text_box_Y;
        int colors_index = 0;
        String text_box_content;
        HashMap<Character, Double> maxHashMap = data.getMaxHashMap(n_occurrences);
        Font font = new Font("helvetica", 12);

        //set font
        gc.setFont(font);
        gc.setTextAlign(TextAlignment.CENTER);

        //iterate over hashmap entries
        for (Map.Entry<Character, Double> entry : maxHashMap.entrySet()) {

            //calculate inner angle of pie slice.
            double inner_angle = entry.getValue() / sumFrequencies * 360;

            //set color, draw slice
            gc.setFill(colors[colors_index]);
            gc.fillArc(originX - radius, originY - radius,
                    radius*2, radius*2, start_angle, inner_angle, ArcType.ROUND);

            //draw text_box, "dynamically"
            if ((start_angle > 45 && start_angle < 135) ||
                    (start_angle > 225 && start_angle < 315)) {
                text_box_X = originX + (text_box_radius_offset-12) * 
                             Math.cos(Math.toRadians((start_angle) + (inner_angle/2)));
                text_box_Y = originY - (text_box_radius_offset-12) * 
                             Math.sin(Math.toRadians((start_angle) + (inner_angle/2)));
            }
            else {
                text_box_X = originX + text_box_radius_offset * 
                             Math.cos(Math.toRadians((start_angle) + (inner_angle / 2)));
                text_box_Y = originY - text_box_radius_offset * 
                             Math.sin(Math.toRadians((start_angle) + (inner_angle / 2)));
            }

            text_box_content = entry.getKey() + ", " + 
                               String.format("%.4g%n", entry.getValue()/sumFrequencies);
            gc.strokeText(text_box_content, text_box_X, text_box_Y);

            //adjust variables
            remainingFrequency -= entry.getValue()/sumFrequencies;
            start_angle += inner_angle;
            unfilled_angle -= inner_angle;
            colors_index++;
        }

        //"All other letters" handling
        if (n_occurrences <= 27) {

            double custom_offset = text_box_radius_offset;

            gc.setFill(colors[colors_index]);
            gc.fillArc(originX - radius, originY - radius,
                    radius*2, radius*2, start_angle, unfilled_angle, ArcType.ROUND);

            if (n_occurrences > 12)
                custom_offset+=20;
            else if (n_occurrences > 9 || n_occurrences < 3)
                custom_offset+=15;

            text_box_X = originX + custom_offset * 
                         Math.cos(Math.toRadians((start_angle) + (360-start_angle) / 2));
            text_box_Y = originY - custom_offset * 
                         Math.sin(Math.toRadians((start_angle) + (360-start_angle) / 2));

            gc.strokeText("All other letters, \n" + 
                   String.format("%.4g%n", remainingFrequency), text_box_X, text_box_Y);
        }

        //create text showing W, N, and S meaning
        gc.setTextAlign(TextAlignment.RIGHT);
        gc.strokeText("W: whitespace instance\n N: numeral instance\nS: symbol instance",
                gc.getCanvas().getWidth()-8, gc.getCanvas().getHeight()-35);
    }
}
