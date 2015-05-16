package interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

public class MainFrame extends JFrame 
{
	public static String fontName = "MV Boli" ;
	public static Font font = new Font(fontName , 15 , 15) ;
	private Table table ;
	private int delayTime = 100 ;
	public MainFrame()
	{
		setSize(410 , 450) ;
		setTitle("AI") ;
		setIconImage(new ImageIcon("H:/Pictures/abstract-waves-1680x1050.jpg").getImage()) ;
		setLocation(100 , 100) ;
		setLayout(null) ;
		setDefaultCloseOperation(EXIT_ON_CLOSE) ;
		for(LookAndFeelInfo inf : UIManager.getInstalledLookAndFeels())
			if("Nimbus".equals(inf.getName()))
				try
				{
					UIManager.setLookAndFeel(inf.getClassName()) ;
				}
				catch (ClassNotFoundException e) {e.printStackTrace();}
				catch (InstantiationException e) {e.printStackTrace();}
				catch (IllegalAccessException e) {e.printStackTrace();}
				catch (UnsupportedLookAndFeelException e) {e.printStackTrace();}
		JLabel l = new JLabel("Please enter given elements and press Solve") ;
		l.setSize(370 , 35) ;
		l.setLocation(10 , 10) ;
		l.setBackground(Color.gray) ;
		l.setBorder(BorderFactory.createEtchedBorder(1)) ;
		l.setFont(font) ;
		getContentPane().add(l) ;
		JPanel panel = new JPanel() ;
		panel.setSize(370 , 280) ;
		panel.setLocation(l.getX() , l.getY() + l.getHeight() + 20) ;
		panel.setBorder(BorderFactory.createRaisedBevelBorder()) ;
		panel.setLayout(null) ;
		getContentPane().add(panel) ;
		table = new Table(panel) ;
		JButton solve = new JButton("Solve") ;
		solve.setFont(font) ;
		solve.setSize(80 ,35) ;
		solve.setLocation(l.getX() + 30 , panel.getHeight() + panel.getY() + 10) ;
		solve.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg)
			{
				table.solve(delayTime) ;
			}
		}) ;
		getContentPane().add(solve) ;
		final JLabel delay = new JLabel("Delay") ;
		delay.setSize(60 , 30) ;
		delay.setLocation(solve.getX() + solve.getWidth() + 20 , solve.getY() + 2) ;
		delay.setFont(new Font(fontName , 20 , 20)) ;
		getContentPane().add(delay) ;
		final JTextField tf = new JTextField() ;
		tf.setSize(60 , 30) ;
		tf.setLocation(delay.getX() + delay.getWidth() , delay.getY()) ;
		tf.setBorder(BorderFactory.createEtchedBorder(1)) ;
		tf.setFont(delay.getFont()) ;
		tf.setText("100") ;
		tf.addFocusListener(new FocusAdapter()
		{
			public void focusLost(FocusEvent arg)
			{
				delayTime = Integer.parseInt(tf.getText()) ;
			}
		}) ;
		getContentPane().add(tf) ;
		JButton exit = new JButton("Exit") ;
		exit.setFont(font) ;
		exit.setSize(80 ,35) ;
		exit.setLocation(panel.getX() + panel.getWidth() - 30 - 80 , solve.getY()) ;
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg)
			{
				System.exit(0) ;
			}
		}) ;
		getContentPane().add(exit) ;
		setVisible(true) ;
	}
	public static void main(String[] arg)
	{
		new MainFrame() ;
	}
}
