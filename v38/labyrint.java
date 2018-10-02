package v38;

import java.io.File;

import v37.MapSolver;
import javafx.application.Application;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class labyrint extends Application {

	static int i = 0;
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		Group root = new Group();
		
		//Kartans blocks lägger sig i denna
		Map map = MapInterpreter.interpretMap(new File("map4.txt"));
		
		//MapSolverns cirklar/path lägger sig i denna.
		Group path = new Group();
		
		root.getChildren().addAll(map, path);
		
		Scene scene = new Scene(root, map.getWidth(), map.getHeight());

		primaryStage.setScene(scene);
		primaryStage.show();
		
		MapSolver mapSolver = new MapSolver();		
		
		long t1 = System.currentTimeMillis();
		if(mapSolver.solveMap(map)) {
			System.out.println("Time taken: " + (System.currentTimeMillis() - t1) + "ms");
			System.out.println("Steps taken: " + mapSolver.getSteps());
			
			//Texten blockerar en liten del av kartan, men kan enkelt stängas av.
			Text text = new Text(map.getWidth()/2, map.getHeight()-12, "Steps taken: 0");
			text.setFill(Color.BLUEVIOLET);
			text.setFont(new Font(map.getWidth()/30));
			path.getChildren().add(text);
			
			//Timeline kanske inte alltid fungerar lika bra som en AnimationTimer
			//Men med en Timeline så kan man enkelt öka animationens hastighet, som är bra för större kartor.
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
				if(i >= mapSolver.getPath().size() - 1) {
					try {
						this.stop();
						return;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	
				Circle circle = mapSolver.getPath().get(i);
			
				path.getChildren().add(circle);

				text.setText("Steps taken: " + i);
				text.toFront();
				
				i++;
			}));
				
			timeline.setCycleCount(Animation.INDEFINITE);
			timeline.setRate(10); //Ändra på denna för att öka/sänka hastigheten på animationen.
			timeline.play();
			
		}
	}

	public static void main(String[] args) {
		launch();
	}

}
