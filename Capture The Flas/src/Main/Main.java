package Main;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import Display.Display;
import Input.KeyManager;
import Input.MouseManager;

public class Main implements Runnable{
	
	private boolean running;
	public final static int WINDOW_WIDTH = 1080;
	public final static int WINDOW_HEIGHT = 720;
	
	private static int WIDTH, HEIGHT;
	
	private Display display;
	private Game game;
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	
	public Main() {
		display = new Display(WINDOW_WIDTH, WINDOW_HEIGHT);
		display.getCanvas().createBufferStrategy(3);
		
		keyManager = new KeyManager();
		display.getCanvas().addKeyListener(keyManager);
		mouseManager = new MouseManager();
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		
		game = new Game(keyManager, mouseManager);
		
		
		try {
			thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WIDTH = display.getCanvas().getWidth();
		HEIGHT = display.getCanvas().getHeight();
		
		System.out.println(WIDTH);
		System.out.println(HEIGHT);
		
		start();
	}
	
	public synchronized void start() {
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop() {
		if(!running)
			return;
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	public void run() {
		int fps = 60;
		float timePerTick = 1000000000 / fps;
		long timeLastTick = System.nanoTime();
		long timeNow;
		double deltaTime = 0;
		
		long timeTicks = System.currentTimeMillis();
		int ticks = 0;
		
		running = true;
		while(running) {
			timeNow = System.nanoTime();
			deltaTime += (timeNow - timeLastTick) / timePerTick;
			timeLastTick = timeNow;
			
			while(deltaTime >= 1) {
				tick();
				render();
				
				deltaTime--;
				ticks++;
			}
			
			if(System.currentTimeMillis() - timeTicks >= 1000) {
				//System.out.println("FPS: " + ticks);
				ticks = 0;
				timeTicks = System.currentTimeMillis();
			}
		}
	}
	
	public void tick() {
		game.tick();
	}
	
	public void render() {
		bs = display.getCanvas().getBufferStrategy();
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		game.render(g);
		
		bs.show();
		g.dispose();
	}
	
	public static int getWidth() {
		return WIDTH;
	}
	
	public static int getHeight() {
		return HEIGHT;
	}

}