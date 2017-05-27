package source.main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Ha28");

        BorderPane borderPane = new BorderPane();
        HBox toolbarBox = new HBox();
        toolbarBox.setPadding(new Insets(15, 12, 15, 12));
        toolbarBox.setSpacing(10);
        toolbarBox.setStyle("-fx-background-color: #336699;");

        VBox navigationBox = new VBox();
        navigationBox.setPadding(new Insets(15, 12, 15, 12));
        //navigationBox.setStyle("-fx-background-color: #FF1111;");


        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<String,Number> lineChart = new LineChart<String, Number>(xAxis,yAxis);

        xAxis.setLabel("Monat");
        lineChart.setTitle("Kredit-Monitoring Ha28");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Kredit");

        series1.getData().add(new XYChart.Data("Jul 15", 230000));
        series1.getData().add(new XYChart.Data("Dez 15", 228000));
        series1.getData().add(new XYChart.Data("Dez 16", 213000));
        series1.getData().add(new XYChart.Data("Apr 17", 199000));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Bausparer 2");
        series2.getData().add(new XYChart.Data("Jul 15", 0));
        series2.getData().add(new XYChart.Data("Dez 15", 500));
        series2.getData().add(new XYChart.Data("Dez 16", 3000));
        series2.getData().add(new XYChart.Data("Apr 17", 9800));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Bausparer inkl. Kredit");
        series3.getData().add(new XYChart.Data("Jul 15", 0));
        series3.getData().add(new XYChart.Data("Dez 15", getBausparerMitKredit(500)));
        series3.getData().add(new XYChart.Data("Dez 16", getBausparerMitKredit(3000)));
        series3.getData().add(new XYChart.Data("Apr 17", getBausparerMitKredit(9800)));

        Pane centerPane = new Pane();
        centerPane.getChildren().addAll(lineChart);

        Label label = new Label(); //String.valueOf(getDifferenzGuthabenKredit(199000, getBausparerMitKredit(9800))));
        label.setText("HALLO HBOX!");
        label.setPrefSize(100, 20);
        toolbarBox.getChildren().addAll(label);

        Button scene1 = new Button();
        scene1.setText("LineChart");
        scene1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                borderPane.setCenter(centerPane);
            }
        });
        navigationBox.getChildren().add(scene1);

        borderPane.setTop(toolbarBox);
        borderPane.setLeft(navigationBox);
        //borderPane.setCenter(centerPane);

        Scene scene  = new Scene(borderPane, 800, 600);
        lineChart.getData().addAll(series1, series2, series3);

        stage.setScene(scene);
        stage.show();

    }

    private double getBausparerMitKredit(double guthaben) {
        return guthaben/0.4;
    }

    private double getDifferenzGuthabenKredit(double Kredit, double Guthaben) {
        return Kredit - Guthaben;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
