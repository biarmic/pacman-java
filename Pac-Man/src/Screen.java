import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Screen extends JLayeredPane {
	private static GUI gui;
	private static ImageIcon food;
	private static ImageIcon bigFood;
	private Maze maze = new Maze();
	private ArrayList<JLabel> foods = new ArrayList<JLabel>();
	private ArrayList<JLabel> bigFoods = new ArrayList<JLabel>();
	private Pacman pacman = new Pacman(this,maze);
	private Blinky blinky = new Blinky(this,maze,pacman);
	private Pinky pinky = new Pinky(this);
	private Inky inky = new Inky(this,blinky);
	private Clyde clyde = new Clyde(this);
	private int dotsEaten = 0;
	public Screen(GUI gui, Tile[][] prev) {
		Screen.gui = gui;
		JLabel background = new JLabel();
		try {
			background.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("images/maze.png"))));
			food = new ImageIcon(ImageIO.read(getClass().getResource("images/food.png")));
			bigFood = new ImageIcon(ImageIO.read(getClass().getResource("images/big_food.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		background.setBounds(0,0,990,930);
		Tile[][] tiles;
		if(prev==null)
			tiles = maze.getLayer1();
		else {
			tiles = prev;
			maze.setLayer1(tiles);
		}
		for(int i = 0; i < 31; i++) {
			for(int j = 0; j < 28; j++) {
				if(tiles[i][j]==Tile.Food) {
					JLabel a = new JLabel();
					a.setIcon(food);
					a.setBounds(j*30,i*30,30,30);
					foods.add(a);
					add(a,JLayeredPane.PALETTE_LAYER);
				}else if(tiles[i][j]==Tile.BigFood) {
					JLabel a = new JLabel();
					a.setIcon(bigFood);
					a.setBounds(j*30,i*30,30,30);
					bigFoods.add(a);
					add(a,JLayeredPane.PALETTE_LAYER);
				}
			}
		}
		add(background,JLayeredPane.DEFAULT_LAYER);
	}
	public Pacman getPacman() {
		return pacman;
	}
	public Maze getMaze() {
		return maze;
	}
	public void moveObjects() {
		if(dotsEaten>=30) {
			inky.canMove = true;
			if(dotsEaten>=80)
				clyde.canMove = true;
		}
		pacman.go();
		blinky.go();
		pinky.go();
		inky.go();
		clyde.go();
	}
	public void findPath() {
		blinky.findPath();
		pinky.findPath();
		inky.findPath();
		clyde.findPath();
	}
	public void scatterMode() {
		Ghost.chasing = false;
	}
	public void chaseMode() {
		Ghost.chasing = true;
	}
	public void unfrighten() {
		blinky.unfrighten();
		pinky.unfrighten();
		inky.unfrighten();
		clyde.unfrighten();
		Ghost.frightened = false;
	}
	private void frighten() {
		Ghost.frightened = true;
		blinky.frighten();
		pinky.frighten();
		inky.frighten();
		clyde.frighten();
	}
	public void eatFood(int row, int column) {
		dotsEaten++;
		gui.eatFood(false);
		for(JLabel food : foods) {
			if(food.getX()/30==column && food.getY()/30==row) {
				maze.getLayer1()[row][column] = null;
				food.setIcon(null);
				foods.remove(food);
				return;
			}
		}
	}
	public void eatBigFood(int row, int column) {
		dotsEaten++;
		gui.eatFood(true);
		for(JLabel food : bigFoods) {
			if(food.getX()/30==column && food.getY()/30==row) {
				maze.getLayer1()[row][column] = null;
				food.setIcon(null);
				foods.remove(food);
				gui.unfrightenGhosts();
				frighten();
				return;
			}
		}
	}
	public void eatGhost() {
		gui.eatGhost();
	}
	public void lifeLost() {
		gui.lifeLost();
	}
	public void flee() {
		int row = pacman.getRow();
		int column = pacman.getColumn();
		if(blinky.row==row && blinky.column==column)
			blinky.flee();
		if(pinky.row==row && pinky.column==column)
			pinky.flee();
		if(inky.row==row && inky.column==column)
			inky.flee();
		if(clyde.row==row && clyde.column==column)
			clyde.flee();
	}
	public boolean isFleeing() {
		int row = pacman.getRow();
		int column = pacman.getColumn();
		if(blinky.row==row && blinky.column==column && blinky.fleeing)
			return true;
		if(pinky.row==row && pinky.column==column && pinky.fleeing)
			return true;
		if(inky.row==row && inky.column==column && inky.fleeing)
			return true;
		if(clyde.row==row && clyde.column==column && clyde.fleeing)
			return true;
		return false;
	}
}
