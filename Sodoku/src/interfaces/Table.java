package interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Table extends JPanel 
{
	private JPanel parent ;
	private JTextField[][] GMap ;
	private int[][] LMap ;
	private Vector<Cookie> queue = null ;
	private int cellW = 40 , cellH = 30 ;
	public Table(JPanel parent)
	{
		this.parent = parent ;
		setSize(360 , 270) ;
		setLocation(5 , 5) ;
		setLayout(null) ;
		setBorder(BorderFactory.createEtchedBorder(1)) ;
		LMap = new int[9][9] ;
		GMap = new JTextField[9][9] ;
		for(int i = 0 ; i < 9 ; i++)
			for(int j = 0 ; j < 9 ; j ++)
			{
				GMap[i][j] = createTableCell(i , j) ;
				LMap[i][j] = 0 ;
			}
		this.parent.add(this) ;
	}
	private JTextField createTableCell(final int i , final int j)
	{
		final JTextField tf = new JTextField() 
		{
			public void processKeyEvent(KeyEvent arg)
			{
				if((getText().equals("") && getText().length() < 1 && arg.getKeyChar() > '0' && arg.getKeyChar() <= '9') || (arg.getKeyCode() == 8))
				{
					super.processKeyEvent(arg) ;
					if(getText().equals(""))
						setBackground(Color.white) ;
				}
			}
		};
		tf.setSize(cellW , cellH) ;
		tf.setLocation(i * cellW , j * cellH) ;
		tf.setBorder(BorderFactory.createEtchedBorder(1)) ;
		tf.setFont(new Font(MainFrame.fontName , 20 , 20)) ;
		tf.setText("") ;
		tf.addFocusListener(new FocusAdapter()
		{
			public void focusLost(FocusEvent arg)
			{
				if(!check(i , j , (tf.getText().equals("") ? 0 : Integer.parseInt(tf.getText()))))
					tf.setBackground(Color.red);
				else
					tf.setBackground(Color.white) ;
			}
		}) ;
		add(tf) ;
		return tf ;
	}
	private boolean check(final int i , final int j , int data )
	{
		LMap[i][j] = data ;
		if(queue != null)
			queue.add(new Cookie(i , j , data)) ;
		if(data == 0) return true ;
		for(int col = 0 ; col < 9 ; col ++)
			if(col != j && LMap[i][col] == LMap[i][j])
				return false ;
		for(int row = 0 ; row < 9 ; row ++)
			if(row != i && LMap[row][j] == LMap[i][j])
				return false ;
		int starti = (i / 3) * 3;
		int startj = (j / 3) * 3 ;
		for(int ti = starti ; ti < starti + 3 ; ti ++)
			for(int tj = startj ; tj < startj + 3 ; tj ++)
				if(ti != i && tj != j && LMap[ti][tj] == LMap[i][j])
					return false ;
		return true ;
	}
	private boolean solveRec() 
	{
		int starti = 0, startj = 0 ;
		boolean founded = false ;
		for(starti = 0 ; starti < 9 ; starti ++)
		{
			for(startj = 0 ; startj < 9 ; startj ++)
				if(LMap[starti][startj] == 0)
				{
					founded = true ;
					break ;
				}
			if(founded)
				break ;
		}
		if(!founded)
			return true ;
		int data = 0 ;
		do
		{
			data ++ ;
			while(data <= 9 && !check(starti ,startj , data))
				data ++ ;
			if(data > 9)
			{
				LMap[starti][startj] = 0 ;
				return false ;
			}
		}while(!solveRec()) ;
		return true ;
	}
	private void printLMap()
	{
		for(int[] p : LMap)
		{
			System.out.println();
			for(int i : p)
				System.out.print( " " + i );
		}
	}
	public void solve(final int delay)
	{
		System.out.println(delay);
		queue = new Vector<Cookie>() ;
		solveRec() ;
		printLMap() ;
		Thread t = new Thread()
		{
			public void run()
			{
				System.out.println("\nSolved with " + queue.size() + " moves!") ;
				for(Cookie c : queue)
				{
					GMap[c.i][c.j].setText(c.data+"") ;
					try {
						sleep(delay) ;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for(int i = 0 ; i < 9 ; i++)
					for(int j = 0 ; j < 9 ; j++)
						GMap[i][j].setText(LMap[i][j] + "") ;
			}
		} ;
		t.start() ;
	}
	public void paint(Graphics g)
	{
		super.paint(g) ;
		g.setColor(Color.blue) ;
		g.drawLine(3 * cellW - 1 , 0 , 3 * cellW - 1, getHeight()) ;
		g.drawLine(6 * cellW - 1 , 0 , 6 * cellW - 1, getHeight()) ;
		g.drawLine(0 , 3 * cellH - 1 , getWidth(), 3 * cellH - 1) ;
		g.drawLine(0 , 6 * cellH - 1 , getWidth(), 6 * cellH - 1) ;
	}
}