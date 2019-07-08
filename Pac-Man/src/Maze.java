
public class Maze {
	private Tile[][] layer1 = new Tile[31][28];
	private Tile[][] layer2 = new Tile[31][28];
	public Maze() {
		for(int i = 0; i < 28; i++) {
			layer1[0][i] = Tile.Wall;
			layer1[1][i] = (i==0 || i==13 || i==14 || i==27) ? Tile.Wall : Tile.Food;
			layer1[2][i] = (i==1 || i==6 || i==12 || i==15 || i==21 || i==26) ? Tile.Food : Tile.Wall;
			layer1[3][i] = (i==6 || i==12 || i==15 || i==21) ? Tile.Food : (i==1 || i==26) ? Tile.BigFood : Tile.Wall;
			layer1[4][i] = (i==1 || i==6 || i==12 || i==15 || i==21 || i==26) ? Tile.Food : Tile.Wall;
			layer1[5][i] = (i==0 || i==27) ? Tile.Wall : Tile.Food;
			layer1[6][i] = (i==1 || i==6 || i==9 || i==18 || i==21 || i==26) ? Tile.Food : Tile.Wall;
			layer1[7][i] = (i==1 || i==6 || i==9 || i==18 || i==21 || i==26) ? Tile.Food : Tile.Wall;
			layer1[8][i] = (i==0 || i==7 || i==8 || i==13 || i==14 || i==19 || i==20 ||i==27) ? Tile.Wall : Tile.Food;
			layer1[9][i] = (i==6 || i==21) ? Tile.Food : (i==12 || i==15) ? null : Tile.Wall;
			layer1[10][i] = (i==6 || i==21) ? Tile.Food : (i==12 || i==15) ? null : Tile.Wall;
			layer1[11][i] = (i==6 || i==21) ? Tile.Food : (i>=9 && i<=18) ? null : Tile.Wall;
			layer1[12][i] = (i==6 || i==21) ? Tile.Food : (i==9 || i==18) ? null : (i==13 || i==14) ? Tile.Door : Tile.Wall;
			layer1[13][i] = (i==6 || i==21) ? Tile.Food : (i==10 || i==17) ? Tile.Wall : (i>=9 && i<=18) ? null : Tile.Wall;
			layer1[14][i] = (i==6 || i==21) ? Tile.Food : (i==10 || i==17) ? Tile.Wall : (i==0 || i==27) ? Tile.Teleport : null;
			layer1[15][i] = (i==6 || i==21) ? Tile.Food : (i==10 || i==17) ? Tile.Wall : (i>=9 && i<=18) ? null : Tile.Wall;
			layer1[16][i] = (i==6 || i==21) ? Tile.Food : (i==9 || i==18) ? null : Tile.Wall;
			layer1[17][i] = (i==6 || i==21) ? Tile.Food : (i>=9 && i<=18) ? null : Tile.Wall;
			layer1[18][i] = (i==6 || i==21) ? Tile.Food : (i==9 || i==18) ? null : Tile.Wall;
			layer1[19][i] = (i==6 || i==21) ? Tile.Food : (i==9 || i==18) ? null : Tile.Wall;
			layer1[20][i] = (i==0 || i==13 || i==14 || i==27) ? Tile.Wall : Tile.Food;
			layer1[21][i] = (i==1 || i==6 || i==12 || i==15 || i==21 || i==26) ? Tile.Food : Tile.Wall;
			layer1[22][i] = (i==1 || i==6 || i==12 || i==15 || i==21 || i==26) ? Tile.Food : Tile.Wall;
			layer1[23][i] = (i==0 || i==4 || i==5 || i==22 || i==23 || i==27) ? Tile.Wall : (i==1 || i==26) ? Tile.BigFood : i==13 ? null : Tile.Food;
			layer1[24][i] = (i==3 || i==6 || i==9 || i==18 || i==21 || i==24) ? Tile.Food : Tile.Wall;
			layer1[25][i] = (i==3 || i==6 || i==9 || i==18 || i==21 || i==24) ? Tile.Food : Tile.Wall;
			layer1[26][i] = (i==0 || i==7 || i==8 || i==13 || i==14 || i==19 || i==20 || i==27) ? Tile.Wall : Tile.Food;
			layer1[27][i] = (i==1 || i==12 || i==15 || i==26) ? Tile.Food : Tile.Wall;
			layer1[28][i] = (i==1 || i==12 || i==15 || i==26) ? Tile.Food : Tile.Wall;
			layer1[29][i] = (i==0 || i==27) ? Tile.Wall : Tile.Food;
			layer1[30][i] = Tile.Wall;
		}
		layer2[13][11] = Tile.Ghost;
		layer2[13][16] = Tile.Ghost;
		layer2[15][11] = Tile.Ghost;
		layer2[15][16] = Tile.Ghost;
		layer2[23][13] = Tile.Pacman;
	}
	public Tile[][] getLayer1() {
		return layer1;
	}
	public Tile[][] getLayer2() {
		return layer2;
	}
	public void setLayer1(Tile[][] layer) {
		layer1 = layer;
	}
	public Tile whatIsThere(int row, int column) {
		return layer1[row][column];
	}
	public boolean isValidMove(int row, int column, boolean[][] isVisited) {
		return row<=30 && row>=0 && column<=27 && column>=0 && layer1[row][column]!=Tile.Wall && !isVisited[row][column];
	}
	public boolean isValidMove(int row, int column) {
		return row<=30 && row>=0 && column<=27 && column>=0 && layer1[row][column]!=Tile.Wall;
	}
}
