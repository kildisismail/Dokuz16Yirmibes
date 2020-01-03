import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;

public class Dokuz16Yirmibes extends JFrame {
	static Dokuz16Yirmibes frame;
	private JButton btnKolay;
	private JButton btnOrta;
	private JButton btnZor;
	private JPanel ust;
	private JPanel alt;
	private OrtaPanel orta;

	private JLabel zaman;
	private JLabel skor;
	private Component horizontalStrut;
	private JLabel lbltime;
	protected static JLabel lblskor;
	public static int satýrsayýsý = 2;
	static Timer timer;
	int zamantime = 0;
	static int tiksayisi = 0;

	///////////////////////////////////////////////////////
	public int boyut;
	private int size; //Oyun büyüklüðü
	private int nbTiles; // Kaç tane kare olacak
	private int dimension; // Kullanýcý arayüz büyüklüðü
	 
	private int tileSize; // Ekranda kaç kare olacaðýný tuttuðumuz deðiþken
	private int blankPos; // Boþ karenin yerini tuttuðumuz deðiþken
	private int margin; // Kutularýn kenarlardan ne kadar olacaðýný tuttuðumuz deðiþken
	private int gridSize; // Arayüz büyüklüðü
	private boolean gameOver; // Oyun durumunu tuttuðumuz deðiþken

	private ActionListener timerlistener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			zamantime++;
			lbltime.setText("" + zamantime);

		}
	};

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Dokuz16Yirmibes();
					frame.setVisible(true);
					frame.setTitle("Dokuz16Yirmibeþ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	 
	public Dokuz16Yirmibes() {

		timer = new Timer(1000, timerlistener);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 650);
		setResizable(false);
		setLocationRelativeTo(null);

		ust = new JPanel();
		FlowLayout flowLayout = (FlowLayout) ust.getLayout();
		flowLayout.setVgap(2);
		getContentPane().add(ust, BorderLayout.NORTH);

		btnKolay = new JButton("Kolay");
		btnKolay.setActionCommand("Kolay");
		btnKolay.setPreferredSize(new Dimension(70, 30));
		btnKolay.addActionListener(btnlistener);
		ust.add(btnKolay);

		btnOrta = new JButton("Orta");
		btnOrta.setActionCommand("Orta");
		btnOrta.setPreferredSize(new Dimension(70, 30));
		btnOrta.addActionListener(btnlistener);
		ust.add(btnOrta);

		btnZor = new JButton("Zor");
		btnZor.setActionCommand("Zor");
		btnZor.setPreferredSize(new Dimension(70, 30));
		btnZor.addActionListener(btnlistener);
		ust.add(btnZor);

		orta = new OrtaPanel(satýrsayýsý, 600, 20);

		

		alt = new JPanel();
		getContentPane().add(alt, BorderLayout.SOUTH);
		alt.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		zaman = new JLabel("Zaman: ");
		zaman.setPreferredSize(new Dimension(70, 15));
		zaman.setFont(new Font("Tahoma", Font.BOLD, 14));
		alt.add(zaman);

		lbltime = new JLabel("0");
		lbltime.setPreferredSize(new Dimension(70, 15));
		lbltime.setFont(new Font("Tahoma", Font.BOLD, 14));
		alt.add(lbltime);

		horizontalStrut = Box.createHorizontalStrut(50);
		alt.add(horizontalStrut);

		skor = new JLabel("Skor:");
		skor.setPreferredSize(new Dimension(70, 15));
		skor.setFont(new Font("Tahoma", Font.BOLD, 14));
		alt.add(skor);

		lblskor = new JLabel("0");
		lblskor.setPreferredSize(new Dimension(70, 15));
		lblskor.setFont(new Font("Tahoma", Font.BOLD, 14));
		alt.add(lblskor);
	}

	private ActionListener btnlistener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == btnKolay.getActionCommand()) {
				if (timer.isRunning()) {
					zamantime = 0;
					lbltime.setText("0");
					lblskor.setText("0");
					timer.restart();
				} else {
					timer.start();
				}
				satýrsayýsý = 3;
				frame.remove(orta);
				orta = new OrtaPanel(satýrsayýsý, 600, 30);
				getContentPane().add(orta);
				pack();
				timer.start();

			} else if (e.getActionCommand() == btnOrta.getActionCommand()) {
				if (timer.isRunning()) {
					zamantime = 0;
					lbltime.setText("0");
					lblskor.setText("0");
					timer.restart();
				} else {
					timer.start();
				}
				zamantime = 0;
				satýrsayýsý = 4;
				frame.remove(orta);
				orta = new OrtaPanel(satýrsayýsý, 600, 30);
				getContentPane().add(orta);
				pack();
				timer.start();

			} else if (e.getActionCommand() == btnZor.getActionCommand()) {
				if (timer.isRunning()) {
					zamantime = 0;
					lbltime.setText("0");
					timer.restart();
				} else {
					timer.start();
				}
				satýrsayýsý = 5;
				zamantime = 0;
				frame.remove(orta);
				orta = new OrtaPanel(satýrsayýsý, 600, 30);

				getContentPane().add(orta);
				pack();
				timer.start();
			}

		}
	};

}
