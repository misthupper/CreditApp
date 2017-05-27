import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.sql.*;

/**
 * Created by joern on 29.04.17.
 */
public class CreditApp extends Application {

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    public void start(Stage stage) throws Exception {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        stage.setTitle("CreditApp");

        //create form to enter new loan data
        GridPane pane = new GridPane();


        Scene scene = new Scene(pane, 400, 500);

        final Label dateLabel = new Label("Datum: ");
        final TextField dateField = new TextField();

        final Label loanCountLabel = new Label("Restschuld: ");
        final TextField loanCountField = new TextField();

        Button addToDatabase = new Button("Speichern in der Datenbank");
        addToDatabase.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, new EventHandler<javafx.scene.input.MouseEvent>() {
            public void handle(javafx.scene.input.MouseEvent event) {
                writeNewLoanCount(dateField.getText(), loanCountField.getText());
            }
        });

        pane.add(dateLabel, 1, 1);
        pane.add(dateField, 2, 1);

        pane.add(loanCountLabel, 1, 2);
        pane.add(loanCountField, 2, 2);

        pane.add(addToDatabase, 3, 3);

        stage.setScene(scene);
        stage.show();

        getDataFromSQLite();
    }

    private void writeNewLoanCount(String date, String loanCount) {
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.execute("insert into hauskredit values('" + date  + "', " + loanCount + ")");
            //statement.executeUpdate("drop table if exists person");
            //statement.executeUpdate("create table person (id integer, name string)");
            //statement.executeUpdate("insert into person values(1, 'leo')");
            //statement.executeUpdate("insert into person values(2, 'yui')");
            //ResultSet rs = statement.executeQuery("select * from hauskredit");
            /*while(rs.next())
            {
                // read the result set
                System.out.println("date = " + rs.getString("date"));
                System.out.println("loan = " + rs.getInt("loan"));
            }
            */
        }
        catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }
    }

    private void getDataFromSQLite() {
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            //statement.executeUpdate("drop table if exists person");
            //statement.executeUpdate("create table person (id integer, name string)");
            //statement.executeUpdate("insert into person values(1, 'leo')");
            //statement.executeUpdate("insert into person values(2, 'yui')");
            ResultSet rs = statement.executeQuery("select * from hauskredit");
            while(rs.next())
            {
                // read the result set
                System.out.println("date = " + rs.getString("date"));
                System.out.println("loan = " + rs.getInt("loan"));
            }
        }
        catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }
    }
}
