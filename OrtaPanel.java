import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class OrtaPanel extends JPanel {
	
	
	  private static final long serialVersionUID = 1L;
	  private  int size;   
	  private int nbTiles;  
	  private int dimension; 
	// Karelerin arka plan rengi
	  private static final Color FOREGROUND_COLOR = new Color(129, 25, 210); 
    // Kutularýn rastgele yerleþebilmesi için kullanýlan random sýnýfý
	  private static final Random RANDOM = new Random();  
	// Kaç kare olacaðýný tuttuðumuz dizi
	  private int[] tiles;  
	// Ekranda kaç kare olacaðýný tuttuðumuz deðiþken
	  private int tileSize; 
	// Boþ karenin yerini tuttuðumuz deðiþken
	  private int blankPos; 
	// Kutularýn kenarlardan ne kadar olacaðýný tuttuðumuz deðiþken
	  private int margin; 
	// Arayüz büyüklüðü
	  private int gridSize; 
	// Oyun durumunu tuttuðumuz deðiþken
	  private boolean gameOver; 
	//Týklama sayýsýný tuttuðumuz deðiþken
	  protected int tiksayýsý=0;
	  
	  
	 
	//Oyunun oynandýðý panelin tasarýmlarý
	public OrtaPanel(int size, int dim, int mar) {
			
		
			this.size = size;  
		    dimension = dim; 
		    margin = mar; 
		    
		    nbTiles = size * size - 1; 
		    tiles = new int[size * size];
		    
		    
		    gridSize = (dim - 2 * margin);  
		    tileSize = gridSize / size;  
		    
		    //Kutularýn tasarýmý
		    setPreferredSize(new Dimension(dimension, dimension ));
		    setBackground(Color.gray);
		    setForeground(FOREGROUND_COLOR);
		    setFont(new Font("SansSerif", Font.BOLD, 60));
		    
		    gameOver = true;
		    //Kutularýn boþ alana kaydýrýlmasý için yazýlan kodlar
		    addMouseListener(new MouseAdapter() {
		        @Override
		        public void mousePressed(MouseEvent e) {
		        	
		          if (gameOver) {
		        	
		            newGame();
		            
		          } else {
		        	// Týklandýðýnda boþ alana kayma
		            int ex = e.getX() - margin;
		            int ey = e.getY() - margin;
		            
		            
		            if (ex < 0 || ex > gridSize  || ey < 0  || ey > gridSize)
		            return; 
		            int c1 = ex / tileSize;
		            int r1 = ey / tileSize;
		            int c2 = blankPos % size;
		            int r2 = blankPos / size;
		            int clickPos = r1 * size + c1;
		            int dir = 0;

		            if (c1 == c2  &&  Math.abs(r1 - r2) > 0)
		              dir = (r1 - r2) > 0 ? size : -size;
		            else if (r1 == r2 && Math.abs(c1 - c2) > 0)
		              dir = (c1 - c2) > 0 ? 1 : -1;
		              
		            if (dir != 0) {
		              
		              do {
		                int newBlankPos = blankPos + dir;
		                tiles[blankPos] = tiles[newBlankPos];
		                blankPos = newBlankPos;
		              } while(blankPos != clickPos);
		              
		              tiles[blankPos] = 0;
		            }
		            //Her týklama skor olarak ekrana yazdýrýlýr
		            tiksayýsý++;
			          Dokuz16Yirmibes.lblskor.setText(""+tiksayýsý);
			          
			          
			          // Oyunun bitiþ durumunu kontrol ediyoruz.
			         gameOver = isSolved();
			        	          
		          }
          
		       // Oyun ekraný tekrar tasarlanýyor.
		          repaint();
		        }
		      });	    
		    newGame();	
	}
	
	
		private void newGame() {
		    do {
		    	Dokuz16Yirmibes.tiksayisi=0;
		    	// Oyun resetlenir
		      reset();
		   // Kutularýn yerleri karýþtýrýlýr
		      shuffle(); 
		   // oyun çözülene kadar devam et.
		    } while(!isSolvable()); 
		    
		    gameOver = false;
		  }
		
		private void reset() {
			
		    for (int i = 0; i < tiles.length; i++) {
		      tiles[i] = (i + 1) % tiles.length;
		    }
    
		    blankPos = tiles.length - 1;
		  }
		
		private void shuffle() {
		    
		    int n = nbTiles;
		    
		    while (n > 1) {
		      int r = RANDOM.nextInt(n--);
		      int tmp = tiles[r];
		      tiles[r] = tiles[n];
		      tiles[n] = tmp;
		    }
		  }
		
		private boolean isSolvable() {
		    int countInversions = 0;
		    
		    for (int i = 0; i < nbTiles; i++) {
		      for (int j = 0; j < i; j++) {
		        if (tiles[j] > tiles[i])
		          countInversions++;
		      }
		    }
		    
		    return countInversions % 2 == 0;
		  }
		
		private boolean isSolved() {
			//Boþ hücre kendi yerinde deðilse çözülemedi döner.
		    if (tiles[tiles.length - 1] != 0) 
		      return false;
		    
		    for (int i = nbTiles - 1; i >= 0; i--) {
		      if (tiles[i] != i + 1)
		    	  
		        return false;      
		    }
		   
		    Dokuz16Yirmibes.timer.stop();
		   
		    return true;
		  }
		
		private void drawGrid(Graphics2D g) {
		    for (int i = 0; i < tiles.length; i++) {
		    //1D olan boyutu 2D yapýyoruz.
		      int r = i / size;
		      int c = i % size;
		   // Kullanýcý arayüzü oluþturuluyor
		      int x = margin + c * tileSize;
		      int y = margin + r * tileSize;
		      
		      
		      if(tiles[i] == 0) {
		        if (gameOver) {
		          g.setColor(FOREGROUND_COLOR);
		          drawCenteredString(g, "\u2713 ", x, y);
		          
		        }
		        
		        continue;
		      }	
		      //kutularýn görsel tasarýmý
		      g.setColor(getForeground());
		      g.fillRoundRect(x, y, tileSize, tileSize, 75, 75);
		      g.setColor(Color.BLACK);
		      g.drawRoundRect(x, y, tileSize, tileSize, 75, 75);
		      g.setColor(Color.WHITE);
		      drawCenteredString(g, String.valueOf(tiles[i]), x , y);
		    }
		  }
		
		private void drawTikSayi(Graphics2D g) {
		    if (gameOver) {
		      g.setFont(getFont().deriveFont(Font.BOLD, 18));
		      g.setColor(FOREGROUND_COLOR);
		      String s ="Yeniden Baþla";
		      g.drawString(s, (getWidth() - g.getFontMetrics().stringWidth(s)) / 2, getHeight() - margin);
		    }
		  }
		
		private void drawStartMessage(Graphics2D g) {
		    if (gameOver) {
		      g.setFont(getFont().deriveFont(Font.BOLD, 18));
		      g.setColor(FOREGROUND_COLOR);
		      String s = "Yeniden Baþla";
		      g.drawString(s, (getWidth() - g.getFontMetrics().stringWidth(s)) / 2,  getHeight() - margin);
		    }
		    drawTikSayi(g);
		  }
		

		private void drawCenteredString(Graphics2D g, String s, int x, int y) {
		    
		    FontMetrics fm = g.getFontMetrics();
		    int asc = fm.getAscent();
		    int desc = fm.getDescent();
		    g.drawString(s  ,  x + (tileSize - fm.stringWidth(s)) / 2,  y + (asc + (tileSize - (asc + desc)) / 2));
		  }
		
		
		@Override
		  protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    Graphics2D g2D = (Graphics2D) g;
		    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    drawGrid(g2D);
		  
		  }
}
