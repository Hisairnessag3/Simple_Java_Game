import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Random;

public class Starthere extends Applet implements Runnable, KeyListener {
	
	
	private Image i;
	private Graphics doubleG;
	Ball b, b2;
	Platform p[] = new Platform[7];
	Item item[] = new Item[3];
	private int score;
	double cityX = 0;
	double cityDx = .3;
	URL url;
	Image city;
	
	

	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public void init() {
		setSize(800, 600);
		addKeyListener(this);
		try{
			url = getDocumentBase();
		}catch (Exception e) {
			
		}
		city = getImage(url, "images/building.png");
	}
	
	@Override
	public void start() {
		score = 0;
	for (int i = 0; i < p.length; i++){
		Random r = new Random();
		p[i] = new Platform(getWidth() + 200 * i, getHeight() - 40 - r.nextInt(400));	
	}
	
	for (int i = 0; i < item.length; i++){
		Random r = new Random();
		switch (r.nextInt(5)){
		case 0:
			item[i] = new GravUp(getWidth() + 2000  * i);
			break;
		case 1:
			item[i] = new GravDown(getWidth()  + 2000  * i);
			break;
		case 2:
			item[i] = new AgilUp(getWidth()   + 2000  * i);
			break;
		case 3:
			item[i] = new AgilDown(getWidth()   + 2000  * i);
			break;
		case 4: 
			item[i] = new ScorePlus(getWidth()   + 2000  * i, this);
		break;
		}
		
	}
		
	
	b = new Ball();
	b2 = new Ball(250, 250);
	Thread thread = new Thread(this);
		thread.start();
	
	}
		
		
	

	@Override
public void run() {
		// thread information
		while (true) {
			
			if(cityX > getWidth() * -1){
				cityX -= cityDx;	
			}else{
				cityX=0;
			}
			
			
			score++;
		
		Random r = new Random();
			
		for (int i = 0; i < item.length; i++){
			if (item[i].isCreateNew()){
				item[i] = null;
				switch (r.nextInt(5)){
				case 0:
					item[i] = new GravUp(getWidth() + 10 * r.nextInt(500));
					break;
				case 1:
					item[i] = new GravDown(getWidth() + 10 * r.nextInt(500));
					break;
				case 2:
					item[i] = new AgilUp(getWidth()  + 10 * r.nextInt(500));
					break;
				case 3:
					item[i] = new AgilDown(getWidth()  + 10 * r.nextInt(500));
					break;
				case 4: 
					item[i] = new ScorePlus(getWidth()  + 10 * r.nextInt(500), this);
				break;
				}
				item[i].setCreateNew(false);
			}
		}
		
		b.update(this);
			
			for(int i = 0; i< p.length; i++){
				p[i].update(this, b);
				
			}

			for(int i = 0; i< item.length; i++){
				item[i].update(this, b);
				
			}
			
				
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
	@Override
	public void stop() {
		
	}
	
	@Override
	public void destroy() {
		
		
	}
	
	@Override
	public void update(Graphics g) {
		if(i==null){
			i = createImage(this.getSize().width, this.getSize().height);
			doubleG = i.getGraphics();
		}
		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, this.getSize().width, this.getSize().height);
		
		doubleG.setColor(getForeground());

		paint(doubleG);
		g.drawImage(i, 0, 0, this);
	
	
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(15,77,147));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(city,(int) cityX, 0, this);
		g.drawImage(city,(int) cityX + getWidth(), 0, this);

		
		b.paint(g);
				for(int i = 0; i< p.length; i++){
			p[i].paint(g);
			}

				for(int i = 0; i< item.length; i++){
					item[i].paint(g);
					}
		String s = Integer.toString(score);
		Font font = new Font("Serif", Font.BOLD, 32);
		g.setColor(Color.BLACK);
		g.drawString(s, getWidth()-150+1, 50+1);
		g.setColor(Color.MAGENTA);
		g.drawString(s, getWidth()-150, 50);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT:
			b.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			b.moveRight();
			break;
		
		
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}
