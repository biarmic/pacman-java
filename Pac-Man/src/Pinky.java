import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Pinky extends Ghost {
	public Pinky (Screen screen) {
		super(screen,13,16);
		canMove = true;
		try {
			ghostIcon = new ImageIcon(ImageIO.read(getClass().getResource("images/pinky.png")));
			setImage(ghostIcon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < 5; i++)
			scatterPath.add(new Position(5-i,6));
		for(int i = 0; i < 5; i++)
			scatterPath.add(new Position(1,5-i));
		for(int i = 0; i < 4; i++)
			scatterPath.add(new Position(2+i,1));
		for(int i = 0; i < 4; i++)
			scatterPath.add(new Position(5,2+i));
		scatterDefault.row = 1;
		scatterDefault.column = 1;
	}
	void findPath() {
		if(!fleeing) {
			if(pacman.getDirection()==Direction.Up && maze.isValidMove(pacman.getRow()-2,pacman.getColumn())) 
				path = findShortestPath(pacman.getRow()-2,pacman.getColumn());
			else if(pacman.getDirection()==Direction.Down && maze.isValidMove(pacman.getRow()+2,pacman.getColumn()))
				path = findShortestPath(pacman.getRow()+2,pacman.getColumn());
			else if(pacman.getDirection()==Direction.Left && maze.isValidMove(pacman.getRow(),pacman.getColumn()-2))
				path = findShortestPath(pacman.getRow(),pacman.getColumn()-2);
			else if(pacman.getDirection()==Direction.Right && maze.isValidMove(pacman.getRow(),pacman.getColumn()+2))
				path = findShortestPath(pacman.getRow(),pacman.getColumn()+2);
			else if(maze.isValidMove(pacman.getRow()-2,pacman.getColumn()))
				path = findShortestPath(pacman.getRow()-2,pacman.getColumn());
			else if(maze.isValidMove(pacman.getRow()+2,pacman.getColumn()))
				path = findShortestPath(pacman.getRow()+2,pacman.getColumn());
			else if(maze.isValidMove(pacman.getRow(),pacman.getColumn()-2))
				path = findShortestPath(pacman.getRow(),pacman.getColumn()-2);
			else if(maze.isValidMove(pacman.getRow(),pacman.getColumn()+2))
				path = findShortestPath(pacman.getRow(),pacman.getColumn()+2);
		}
	}
}
