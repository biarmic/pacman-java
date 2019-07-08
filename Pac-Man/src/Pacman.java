import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Pacman extends JLabel{
	private Screen screen;
	private Maze maze;
	private static ImageIcon right;
	private static ImageIcon left;
	private static ImageIcon up;
	private static ImageIcon down;
	private int row = 23;
	private int column = 13;
	private Direction intended = Direction.Left;
	private Direction direction = Direction.Left;
	public Pacman(Screen screen, Maze maze) {
		this.screen = screen;
		this.maze = maze;
		try {
			right = new ImageIcon(ImageIO.read(getClass().getResource("images/pacman_right.png")));
			left = new ImageIcon(ImageIO.read(getClass().getResource("images/pacman_left.png")));
			up = new ImageIcon(ImageIO.read(getClass().getResource("images/pacman_up.png")));
			down = new ImageIcon(ImageIO.read(getClass().getResource("images/pacman_down.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setIcon(left);
		setBounds(column*30,row*30,30,30);
		screen.add(this,JLayeredPane.POPUP_LAYER);
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public Direction getDirection() {
		return direction;
	}
	public ImageIcon getImageIcon() {
		return right;
	}
	private void setPosition(int row, int column) {
		Tile prev = maze.getLayer2()[row][column];
		maze.getLayer2()[this.row][this.column] = null;
		maze.getLayer2()[row][column] = Tile.Pacman;
		this.row = row;
		this.column = column;
		setLocation(column*30,row*30);
		if(prev==Tile.Ghost && !Ghost.frightened && !screen.isFleeing())
			screen.lifeLost();
		else if(prev==Tile.Ghost && !screen.isFleeing())
			screen.flee();
	}
	public void setDirection(Direction intended) {
		this.intended = intended;
	}
	private void changeIcon() {
		setIcon(direction==Direction.Up ? up : direction==Direction.Down ? down : direction==Direction.Left ? left : right);
	}
	public void go() {
		System.out.println("moving");
		if(maze.whatIsThere(row,column)==Tile.Teleport && column==0 && intended!=Direction.Right && direction==Direction.Left)
			setPosition(row,27);
		else if(maze.whatIsThere(row,column)==Tile.Teleport && column==27 && intended!=Direction.Left && direction==Direction.Right)
			setPosition(row,0);
		Tile destination1 = maze.whatIsThere(row + (intended==Direction.Up ? -1 : intended==Direction.Down ? 1 : 0),column + (intended==Direction.Left ? -1 : intended==Direction.Right ? 1 : 0));
		Tile destination2 = maze.whatIsThere(row + (direction==Direction.Up ? -1 : direction==Direction.Down ? 1 : 0),column + (direction==Direction.Left ? -1 : direction==Direction.Right ? 1 : 0));
		if(destination1!=Tile.Wall && destination1!=Tile.Door) {
			direction = intended;
			setPosition(row + (intended==Direction.Up ? -1 : intended==Direction.Down ? 1 : 0),column + (intended==Direction.Left ? -1 : intended==Direction.Right ? 1 : 0));
			changeIcon();
			if(destination1==Tile.Food)
				screen.eatFood(row,column);
			else if(destination1==Tile.BigFood)
				screen.eatBigFood(row,column);
		}else if(destination2!=Tile.Wall && destination2!=Tile.Door) {
			setPosition(row + (direction==Direction.Up ? -1 : direction==Direction.Down ? 1 : 0),column + (direction==Direction.Left ? -1 : direction==Direction.Right ? 1 : 0));
			if(destination2==Tile.Food)
				screen.eatFood(row,column);
			else if(destination2==Tile.BigFood)
				screen.eatBigFood(row,column);
		}
	}
}
