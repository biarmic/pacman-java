import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Blinky extends Ghost {
	public Blinky(Screen screen, Maze maze, Pacman pacman) {
		super(screen,13,11,maze,pacman);
		canMove = true;
		try {
			ghostIcon = new ImageIcon(ImageIO.read(getClass().getResource("images/blinky.png")));
			frightenedIcon = new ImageIcon(ImageIO.read(getClass().getResource("images/frightened_ghost.png")));
			fleeIcon = new ImageIcon(ImageIO.read(getClass().getResource("images/flee_ghost.png")));
			setImage(ghostIcon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < 5; i++)
			scatterPath.add(new Position(5-i,21));
		for(int i = 0; i < 5; i++)
			scatterPath.add(new Position(1,22+i));
		for(int i = 0; i < 4; i++)
			scatterPath.add(new Position(2+i,26));
		for(int i = 0; i < 4; i++)
			scatterPath.add(new Position(5,25-i));
		scatterDefault.row = 1;
		scatterDefault.column = 26;
	}
	void findPath() {
		if(!fleeing)
			path = findShortestPath(pacman.getRow(),pacman.getColumn());
	}
}
