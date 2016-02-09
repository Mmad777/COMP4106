package application;
import javafx.application.Application;
import javafx.stage.Stage;
 
public class MainApplication extends Application {
 
    public static void main(String[] args) {
        launch(args);
    }
     
    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("COMP4106 A1");
        
        MainController controller = new MainController();
    	primaryStage.setScene(controller.getView());
    	
    	primaryStage.show();
    	
    }
}