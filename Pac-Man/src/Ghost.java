import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Ghost extends JLabel {
	private Screen screen;
	static Maze maze;
	static Pacman pacman;
	ImageIcon ghostIcon;
	static ImageIcon frightenedIcon;
	static ImageIcon fleeIcon;
	private ImageIcon alive;
	int row;
	int column;
	static boolean chasing = false;
	static boolean frightened = false;
	boolean fleeing = false;
	ArrayList<Position> path;
	ArrayList<Position> scatterPath = new ArrayList<Position>();
	boolean canMove;
	Position scatterDefault = new Position();
	private Direction frightDirection;
	public Ghost(Screen screen, int row, int column) {
		this.screen = screen;
		this.row = row;
		this.column = column;
		maze = screen.getMaze();
	}
	public Ghost(Screen screen, int row, int column, Maze maze, Pacman pacman) {
		this.screen = screen;
		this.row = row;
		this.column = column;
		Ghost.pacman = pacman;
		Ghost.maze = maze;
	}
	void setImage(ImageIcon icon) {
		alive = icon;
		setIcon(alive);
		setBounds(column*30,row*30,30,30);
		screen.add(this,JLayeredPane.MODAL_LAYER);
	}
	void setPosition(int row, int column) {
		Tile prev = maze.getLayer2()[row][column];
		maze.getLayer2()[this.row][this.column] = null;
		maze.getLayer2()[row][column] = Tile.Ghost;
		this.row = row;
		this.column = column;
		setLocation(column*30,row*30);
		if(prev==Tile.Pacman && !frightened && !fleeing)
			screen.lifeLost();
		else if(prev==Tile.Pacman && !fleeing)
			flee();
	}
	class Position {
		int row;
		int column;
		int distance;
		ArrayList<Position> path = new ArrayList<Position>();
		Position(int row, int column, int distance) {
			this.row = row;
			this.column = column;
			this.distance = distance;
			path.add(this);
		}
		Position(int row, int column) {
			this.row = row;
			this.column = column;
		}
		Position() {
		}
		void setPath(ArrayList<Position> path){
			this.path = new ArrayList<Position>(path);
			this.path.add(this);
		}
	}
	ArrayList<Position> findShortestPath(int destRow, int destColumn) {
		ArrayList<Position> shortestPath = new ArrayList<Position>();
		int[] possibleRow = new int[] {-1,0,0,1};
		int[] possibleColumn = new int[] {0,-1,1,0};
		int sourceRow = row;
		int sourceColumn = column;
		boolean[][] isVisited = new boolean[31][28];
		Queue<Position> pos = new ArrayDeque<>();
		isVisited[row][column] = true;
		pos.add(new Position(sourceRow,sourceColumn,0));
		int minDistance = Integer.MAX_VALUE;
		while (!pos.isEmpty()) {
			Position current = pos.poll();
			sourceRow = current.row;
			sourceColumn = current.column;
			int currentDistance = current.distance;
			if (sourceRow==destRow && sourceColumn==destColumn && current.distance<minDistance) {
				minDistance = current.distance;
				shortestPath = current.path;
			}else {
				for (int k = 0; k < 4; k++) {
					if (maze.isValidMove(sourceRow+possibleRow[k],sourceColumn+possibleColumn[k],isVisited)) {
						isVisited[sourceRow+possibleRow[k]][sourceColumn+possibleColumn[k]] = true;
						Position add = new Position(sourceRow+possibleRow[k],sourceColumn+possibleColumn[k],currentDistance+1);
						add.setPath(current.path);
						pos.add(add);
					}
				}
			}
		}
		return shortestPath;
	}
	public void go() {
		if(fleeing) {
			followPath(path);
			if(path.size()<=1) {
				setIcon(ghostIcon);
				fleeing = false;
				frightened = false;
			}
		}else if(frightened) {
			if(canMove) {
				if(frightDirection!=null && possibleDirections()<3 && maze.isValidMove(row + (frightDirection==Direction.Up ? -1 : frightDirection==Direction.Down ? 1 : 0),column + (frightDirection==Direction.Left ? -1 : frightDirection==Direction.Right ? 1 : 0)))
					setPosition(row + (frightDirection==Direction.Up ? -1 : frightDirection==Direction.Down ? 1 : 0),column + (frightDirection==Direction.Left ? -1 : frightDirection==Direction.Right ? 1 : 0));
				else {
					boolean canMove = false;
					while(!canMove) {
						frightDirection = Direction.random();
						if(maze.isValidMove(row + (frightDirection==Direction.Up ? -1 : frightDirection==Direction.Down ? 1 : 0),column + (frightDirection==Direction.Left ? -1 : frightDirection==Direction.Right ? 1 : 0)))
							canMove = true;
					}
					setPosition(row + (frightDirection==Direction.Up ? -1 : frightDirection==Direction.Down ? 1 : 0),column + (frightDirection==Direction.Left ? -1 : frightDirection==Direction.Right ? 1 : 0));
				}
			}
		}else if(!chasing) {
			if(canMove) {
				for(int i = 0; i < scatterPath.size(); i++) {
					if(scatterPath.get(i).row==row && scatterPath.get(i).column==column) {
						setPosition(scatterPath.get((i+1)%scatterPath.size()).row,scatterPath.get((i+1)%scatterPath.size()).column);
						return;
					}
				}
				followPath(findShortestPath(scatterDefault.row,scatterDefault.column));
			}
		}else {
			if(canMove)
				followPath(path);
		}
	}
	private void followPath(ArrayList<Position> path) {
		if(path!=null && path.size()>1) {
			setPosition(path.get(1).row,path.get(1).column);
			path.remove(1);
		}
	}
	private int possibleDirections() {
		int directionCount = 0;
		if(maze.isValidMove(row-1,column))
			directionCount++;
		if(maze.isValidMove(row+1,column))
			directionCount++;
		if(maze.isValidMove(row,column-1))
			directionCount++;
		if(maze.isValidMove(row,column+1))
			directionCount++;
		return directionCount;
	}
	public void frighten() {
		if(!fleeing)
			setIcon(frightenedIcon);
	}
	public void unfrighten() {
		if(!fleeing)
			setIcon(ghostIcon);
	}
	public void flee() {
		fleeing = true;
		screen.eatGhost();
		setIcon(fleeIcon);
		path = findShortestPath(14,14);
	}
}
