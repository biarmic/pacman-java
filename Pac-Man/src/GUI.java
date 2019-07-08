import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.KeyStroke;

public class GUI extends JFrame {
	private static GUI gui;
	private Screen screen;
	private Pacman pacman;
	private ArrayList<JLabel> lives = new ArrayList<JLabel>();
	private Timer timer;
	private TimerTask pacmanTask;
	private TimerTask ghostTask;
	private TimerTask scatterTask;
	private TimerTask chaseTask;
	private TimerTask frightenedTask;
	int score = 0;
	JLabel scoreLabel = new JLabel(""+score);
	JLabel gameOver = new JLabel();
	public GUI() {
		super("Pac-Man");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(996,959);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int)(screenSize.width-996)/2,(int)(screenSize.height-959)/2);
		setResizable(false);
		scoreLabel.setBounds(860,70,120,30);
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setFont(new Font("Arial",Font.BOLD,25));
		prepareGame(true);
		setVisible(true);
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "pressUp");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "pressUp");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "pressDown");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "pressDown");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "pressLeft");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "pressLeft");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "pressRight");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "pressRight");
		getRootPane().getActionMap().put("pressUp", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				pacman.setDirection(Direction.Up);
			}
		});
		getRootPane().getActionMap().put("pressDown", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				pacman.setDirection(Direction.Down);
			}
		});
		getRootPane().getActionMap().put("pressLeft", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				pacman.setDirection(Direction.Left);
			}
		});
		getRootPane().getActionMap().put("pressRight", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				pacman.setDirection(Direction.Right);
			}
		});
		for(int i = 0; i < 2; i++) {
			JLabel life = new JLabel();
			life.setIcon(pacman.getImageIcon());
			life.setBounds(870+60*i,150,30,30);
			screen.add(life,JLayeredPane.PALETTE_LAYER);
			lives.add(life);
		}
		try {
			gameOver.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("images/game_over.png"))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		gameOver.setBounds(180,390,480,90);
		restartTimer();
	}
	private void prepareGame(boolean nextLevel) {
		if(nextLevel)
			screen = new Screen(this,null);
		else {
			Tile[][] prev = screen.getMaze().getLayer1();
			screen = new Screen(this,prev);
		}
		add(screen);
		pacman = screen.getPacman();
		for(JLabel life : lives)
			screen.add(life,JLayeredPane.PALETTE_LAYER);
		screen.add(scoreLabel,JLayeredPane.PALETTE_LAYER);
		validate();
	}
	public void restartTimer() {
		if(timer!=null)
			timer.cancel();
		timer = new Timer();
		pacmanTask = new TimerTask() {
			@Override
			public void run() {
				screen.moveObjects();
			}
		};
		ghostTask = new TimerTask() {
			@Override
			public void run() {
				screen.findPath();
			}
		};
		scatterTask = new TimerTask() {
			@Override
			public void run() {
				screen.scatterMode();
			}
		};
		chaseTask = new TimerTask() {
			@Override
			public void run() {
				screen.chaseMode();
			}
		};
		timer.schedule(pacmanTask,1000,200);
		timer.schedule(ghostTask,900,200);
		timer.schedule(scatterTask,1000,27000);
		timer.schedule(chaseTask,8000,27000);
	}
	public void unfrightenGhosts() {
		if(Ghost.frightened)
			frightenedTask.cancel();
		frightenedTask = new TimerTask() {
			@Override
			public void run() {
				screen.unfrighten();
			}
		};
		timer.schedule(frightenedTask,10000);
	}
	public void eatFood(boolean isBigFood) {
		if(isBigFood)
			score += 50;
		else
			score += 10;
		scoreLabel.setText(""+score);
	}
	public void eatGhost() {
		score += 200;
		scoreLabel.setText(""+score);
	}
	public void lifeLost() {
		if(lives.size()>0) {
			try {
				Thread.sleep(1000);
				lives.get(0).setIcon(null);
				lives.remove(0);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			remove(screen);
			prepareGame(false);
			restartTimer();
		}else
			gameOver();
	}
	public void nextLevel() {
		try {
			Thread.sleep(1000);
			remove(screen);
			prepareGame(true);
			restartTimer();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void gameOver() {
		screen.add(gameOver,JLayeredPane.DRAG_LAYER);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setVisible(false);
		dispose();
		gui = new GUI();
	}
	public static void main(String[] args) {
		GUI gui = new GUI();
	}
}
