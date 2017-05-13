package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TestSimilarityInUI extends Application{
		
	 private TextField t1 = new TextField();
	 private TextField t2 = new TextField();
	 private TextField t3 = new TextField();
	 	
	 
	 public void start(Stage primaryStage){
		 
		 GridPane g = new GridPane();
		 
		 g.add(new Label("The first primitive:"),0,0);
	     g.add(t1,1,0);
	     g.add(new Label("The second primitive:"),0,1);
	     g.add(t2,1,1);
	     g.add(new Label("The similarity of the two primitives:"), 0, 2);
	     g.add(t3, 1, 2);	     
	 
		 Button b1 = new Button("Get the similarity");
		 b1.setOnAction(e -> {
			 String word1 = t1.getText();
			 String word2 = t2.getText();
			 double simP = WordSimilarity.simPrimitive(word1,word2);
			 t3.setText(simP + ""); 	                               
		 });
		 g.add(b1,1,3);				
		 		 
		 
		 Scene scene = new Scene(g,500,120);
	        primaryStage.setScene(scene);
	        primaryStage.setTitle("Word Similarity");
	        primaryStage.show();
	 
	 }
	 public static void main(String[] args){
         Application.launch(args);
       }  
	
}
