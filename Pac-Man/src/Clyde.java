import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Clyde extends Ghost {
	public Clyde (Screen screen) {
		super(screen,15,16);
		canMove = false;
		try {
			ghostIcon = new ImageIcon(ImageIO.read(getClass().getResource("images/clyde.png")));
			setImage(ghostIcon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < 4; i++)
			scatterPath.add(new Position(23,9-i));
		for(int i = 0; i < 3; i++)
			scatterPath.add(new Position(24+i,6));
		for(int i = 0; i < 5; i++)
			scatterPath.add(new Position(26,5-i));
		for(int i = 0; i < 3; i++)
			scatterPath.add(new Position(27+i,1));
		for(int i = 0; i < 11; i++)
			scatterPath.add(new Position(29,2+i));
		for(int i = 0; i < 3; i++)
			scatterPath.add(new Position(28-i,12));
		for(int i = 0; i < 3; i++)
			scatterPath.add(new Position(26,11-i));
		for(int i = 0; i < 2; i++)
			scatterPath.add(new Position(25-i,9));
		scatterDefault.row = 29;
		scatterDefault.column = 1;
	}
	void findPath() {
		if(!fleeing) {
			double distance = Math.sqrt(Math.pow(row-pacman.getRow(),2)+Math.pow(column-pacman.getColumn(),2));
			if(distance>8) {
				path = findShortestPath(pacman.getRow(),pacman.getColumn());
			}else {
				path = findShortestPath(29,1);
			}
		}
	}
}
