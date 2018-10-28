import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable , KeyListener{
	
	private static final long serialVersionUID = 1L;
	
	
	private static final int WIDTH = 500, HEIGHT = 500;
	
	private Thread thread;
	
	private boolean running;
	
	private boolean right = true, left = false, up = false, down = false;
	
	private SnekBod s;
	private ArrayList<SnekBod> snake;
	
	private Fruit f;
	private ArrayList<Fruit> fruit;
	
	private Random r;
	
	private int xCoor = 10, yCoor = 10, size = 5;
	private int ticks=0;
		public Panel() {
			setFocusable(true);
			
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
			addKeyListener(this);
			
			snake = new ArrayList<SnekBod>();
			fruit = new ArrayList<Fruit>();
			
			r = new Random();
			
			start();
			
		}
	
		public void start() {
			running = true;
			thread = new Thread(this);
			thread.start();
				
		}
		public void stop() {
			running=false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		public void tick() {
			if(snake.size() == 0) {
				s = new SnekBod(xCoor, yCoor, 10);
				snake.add(s);
			}
			ticks++;
			if(ticks > 250000) {
				if(right) xCoor++;
				if(left) xCoor--;
				if(up) yCoor--;
				if(down) yCoor++;
				
				ticks = 0;
				
				s = new SnekBod(xCoor, yCoor, 10);
				snake.add(s);
				
				if(snake.size() > size) {
					snake.remove(0);
				}
				
 			}
			
			if(fruit.size()==0) {
				int xCoor = r.nextInt(49);
				int yCoor = r.nextInt(49);
				
				f = new Fruit(xCoor, yCoor, 10);
				fruit.add(f);
			}
			
			for(int i = 0; i< fruit.size() ; i++) {
				if(xCoor == fruit.get(i).getxCoor() && yCoor == fruit.get(i).getyCoor()) {
					size++;
					fruit.remove(i);
					i++;
				}
			}
			// when it hits himself
			for(int i = 0; i< snake.size() ; i++) {
				if(xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
					if(i != snake.size()- 1) {
						System.out.println("You have been defeated by a feeble snek");
						stop();
					}
				}
			}
			
			//when he hits the walls
			if(xCoor < 0 || xCoor > 49 || yCoor < 0 || yCoor > 49) {
				System.out.println("You have been defeated by a feeble snek");
				stop();
			}
			
		}
		public void paint(Graphics g) {
			g.clearRect(0, 0, WIDTH, HEIGHT);
			
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);
				
			for (int i=0; i< WIDTH/10; i++) {
				g.drawLine(i*10, 0, i*10, HEIGHT);
			}
			for (int i=0; i< HEIGHT/10; i++) {
				g.drawLine(0, i*10 , HEIGHT, i*10);
			}
			for (int i=0; i< snake.size(); i++) {
				snake.get(i).draw(g);
			}
			for (int i=0; i< fruit.size(); i++) {
				fruit.get(i).draw(g);
			}
			
		}

		@Override
		public void run() {
			while(running) {
				tick();
				repaint();
			}
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_RIGHT && !left ) {
				right = true;
				up = false;
				down = false;			
			}
			
			if(key == KeyEvent.VK_LEFT && !right ) {
				left = true;
				up = false;
				down = false;			
			}
			
			if(key == KeyEvent.VK_UP && !down ) {
				up = true;
				right = false;
				left = false;			
			}
			if(key == KeyEvent.VK_DOWN && !up ) {
				down = true;
				right = false;
				left = false;			
			}
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
}
