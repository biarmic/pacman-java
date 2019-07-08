import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Inky extends Ghost {
	private Blinky blinky;
	public Inky (Screen screen, Blinky blinky) {
		super(screen,15,11);
		this.blinky = blinky;
		canMove = false;
		try {
			ghostIcon = new ImageIcon(ImageIO.read(getClass().getResource("images/inky.png")));
			setImage(ghostIcon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < 4; i++)
			scatterPath.add(new Position(23,18+i));
		for(int i = 0; i < 3; i++)
			scatterPath.add(new Position(24+i,21));
		for(int i = 0; i < 5; i++)
			scatterPath.add(new Position(26,22+i));
		for(int i = 0; i < 3; i++)
			scatterPath.add(new Position(27+i,26));
		for(int i = 0; i < 11; i++)
			scatterPath.add(new Position(29,25-i));
		for(int i = 0; i < 3; i++)
			scatterPath.add(new Position(28-i,15));
		for(int i = 0; i < 3; i++)
			scatterPath.add(new Position(26,16+i));
		for(int i = 0; i < 2; i++)
			scatterPath.add(new Position(25-i,18));
		scatterDefault.row = 29;
		scatterDefault.column = 26;
	}
	void findPath() {
		if(!fleeing) {
			int frontRow = 0;
			int frontColumn = 0;
			if(pacman.getDirection()==Direction.Up) {
				frontRow = pacman.getRow()-2;
				frontColumn = pacman.getColumn();
			}else if(pacman.getDirection()==Direction.Down) {
				frontRow = pacman.getRow()+2;
				frontColumn = pacman.getColumn();
			}else if(pacman.getDirection()==Direction.Left) {
				frontRow = pacman.getRow();
				frontColumn = pacman.getColumn()-2;
			}else if(pacman.getDirection()==Direction.Right) {
				frontRow = pacman.getRow();
				frontColumn = pacman.getColumn()+2;
			}
			int vectorRow = frontRow + (frontRow - blinky.row);
			int vectorColumn = frontColumn + (frontColumn - blinky.column);
			for(int i = 0; i < 31; i++) {
				for(int j = -1; j < 2; j++) {
					for(int k = -1; k < 2; k++) {
						if(maze.isValidMove(vectorRow+i*j,vectorColumn+i*k)) {
							path = findShortestPath(vectorRow+i*j,vectorColumn+i*k);
							return;
						}
					}
				}
			}
		}
	}
}
