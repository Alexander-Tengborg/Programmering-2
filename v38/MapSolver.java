package v38;

import v37.map.Map;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import v37.blocks.Block;
import v37.blocks.GoalBlock;
import v37.blocks.ClosedBlock;

//L�ttare och tydligare att anv�nda f�ljande ist�llet f�r att ha olika siffror som representerar olika h�ll
enum Dir {
	UP,
	RIGHT,
	DOWN,
	LEFT,
	NONE
}

public class MapSolver {
	
	Map map;
	
	boolean[][] beenHere;
	
	ArrayList<Circle> path;
	
	int stepsTaken = 0;
	
	public ArrayList<Circle> getPath() {
		return path;
	}
	
	public int getSteps() {
		return stepsTaken;
	}
	
	public boolean solveMap(Map map) {
		this.map = map;
		int x = map.getStartX();
		int y = map.getStartY();
		
		beenHere = new boolean[(int) map.getBlocks().get(0).size()][(int) map.getBlocks().size()];
		
		path = new ArrayList<Circle>();
		
		return solve(x, y, Dir.NONE);
	}
	
	private boolean solve(int x, int y, Dir dir) {
		switch(dir) {
			case UP:
				y -= 1;
				break;
				
			case RIGHT:
				x += 1;
				break;
				
			case DOWN:
				y += 1;
				break;
				
			case LEFT:
				x -= 1;
				break;
			default:
				break;
		}
		
		Block block = map.getBlock(x, y);
		
		stepsTaken++;
		
		
		if(block == null) return false;
		
		//Kanske inte det mest effektiva s�ttet, men detta g�r s� att jag enkelt kan ha olika f�rger p� cirklarna.

		Circle circle;
		
		if(block instanceof ClosedBlock) {
			//Cirklarna som �r p� en v�gg �r gr�na
			circle = new Circle(x*Block.SIZE + Block.SIZE/2, y*Block.SIZE + Block.SIZE/2, Block.SIZE/2, Color.GREEN);
			path.add(circle);
			return false;
		} else if(beenHere[x][y] == true) {
			//Cirklarna d�r den har varit fler �n en g�ng p� �r gula
			circle = new Circle(x*Block.SIZE + Block.SIZE/2, y*Block.SIZE + Block.SIZE/2, Block.SIZE/2, Color.YELLOW);
			path.add(circle);
			return false;
		} else {
			//Cirklarna d�r den bara har varit en g�ng �r bl�a
			circle = new Circle(x*Block.SIZE + Block.SIZE/2, y*Block.SIZE + Block.SIZE/2, Block.SIZE/2, Color.BLUE);
			path.add(circle);
		}
		
		beenHere[x][y] = true;
		
		if(block instanceof GoalBlock) {
			return true;
		}
		
		if(solve(x, y, Dir.RIGHT) || solve(x, y, Dir.DOWN) || solve(x, y, Dir.LEFT) || solve(x, y, Dir.UP)) {
			return true;
		}
		return false;
	}
}
