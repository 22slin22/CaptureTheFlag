package Server;

public class ServerMain implements Runnable{
	
	private boolean running;
	private boolean playing = false;
	
	
	private Thread thread;
	private ServerGameState serverGameState;
	
	
	public ServerMain(Server server) {
		this.serverGameState = new ServerGameState(server);
	}
	
	
	public synchronized void start() {
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
		
		serverGameState.reset();
	}
	
	public synchronized void stop() {
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
		
		running = true;
		while(running) {
			timeNow = System.nanoTime();
			deltaTime += (timeNow - timeLastTick) / timePerTick;
			timeLastTick = timeNow;
			
			while(deltaTime >= 1) {
				if(playing)
					serverGameState.tick();
				
				deltaTime--;
			}
		}
	}
	
	
	public ServerGameState getServerGameState() {
		return serverGameState;
	}
	
	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

}
