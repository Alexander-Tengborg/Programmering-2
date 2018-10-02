package v38;

import v37.map.Map;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import v37.blocks.Block;
import v37.blocks.GoalBlock;
import v37.blocks.ClosedBlock;

//Lättare och tydligare att använda följande istället för att ha olika siffror som representerar olika håll
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
		
		//Kanske inte det mest effektiva sättet, men detta gör så att jag enkelt kan ha olika färger på cirklarna.

		Circle circle;
		
		if(block instanceof ClosedBlock) {
			//Cirklarna som är på en vägg är gröna
			circle = new Circle(x*Block.SIZE + Block.SIZE/2, y*Block.SIZE + Block.SIZE/2, Block.SIZE/2, Color.GREEN);
			path.add(circle);
			return false;
		} else if(beenHere[x][y] == true) {
			//Cirklarna där den har varit fler än en gång på är gula
			circle = new Circle(x*Block.SIZE + Block.SIZE/2, y*Block.SIZE + Block.SIZE/2, Block.SIZE/2, Color.YELLOW);
			path.add(circle);
			return false;
		} else {
			//Cirklarna där den bara har varit en gång är blåa
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
