import java.awt.*;
import java.awt.event.*;
//import java.awt.image.*;
import java.awt.Color;
import java.awt.Graphics;
//import java.util.*;
public class Tic_tac_toe extends Frame
{
	class EndGameDialog extends Dialog
	{
		private Button OK;
		public EndGameDialog(Frame parent,String title, boolean mode,int w, int h)
		{
			super(parent,title,mode);
			addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					dispose();
				}
			});
			setLayout(null);
			setSize(w, h);
			setLocation(parent.getWidth()/2-w/2, parent.getHeight()/2-h/2);
			OK = new Button("OK");
			OK.setSize(80, 30);
			OK.setLocation(w/2-40, h-40);
			OK.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
			 	{
					dispose();
			 	}
			});
			add(OK);
			show();
		}
	}
	enum celltype{Empty, Cross, Zero};
	public class cell
	{
		celltype type;
		public cell(){}
	}
	public cell ret_pos[];
	public cell pos[];
	public boolean is_line(celltype z)
	{
		if((pos[0].type == z && pos[1].type == z && pos[2].type == z) ||
			(pos[3].type == z && pos[4].type == z && pos[5].type == z) ||
			(pos[6].type == z && pos[7].type == z && pos[8].type == z) ||
			(pos[0].type == z && pos[3].type == z && pos[6].type == z) ||
			(pos[1].type == z && pos[4].type == z && pos[7].type == z) ||
			(pos[2].type == z && pos[5].type == z && pos[8].type == z) ||
			(pos[0].type == z && pos[4].type == z && pos[8].type == z) ||
			(pos[6].type == z && pos[4].type == z && pos[2].type == z))
			return true;
		else return false;
	}
	public boolean is_game_over()
	{
		if(!(is_line(celltype.Cross) || is_line(celltype.Zero)))
		{
			for(cell i : pos)
				if(i.type == celltype.Empty) return false;
		}
		return true;
	}
	public int Search(celltype curtype, int alpha, int beta, int depth)
	{
		celltype oponent;
		int tmp;
		boolean is_move = false;
		if(curtype == celltype.Cross)oponent = celltype.Zero;
		else oponent = celltype.Cross;
		if(is_line(oponent))return -1;
		for(int i=0; i<9; i++)
		{
			if(pos[i].type == celltype.Empty)
			{
				is_move = true;
				pos[i].type = curtype;
				tmp = -Search(oponent, -beta, -alpha, depth+1);
				if(tmp>alpha)
				{
					alpha=tmp;
					if(depth == 0)
						for(int k=0;k<9;k++)
							ret_pos[k].type = pos[k].type;
				}
				pos[i].type = celltype.Empty;
				if(alpha>=beta)break;
			}
		}
		if(is_move)return alpha;
		else return 0;
		
	}
	public boolean is_your_turn;
	public Tic_tac_toe(String FrameName)
	{
		super(FrameName);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();
				System.exit(0);
			}
		});
		
		is_your_turn = true;
		pos = new cell[9];
		ret_pos = new cell[9];
		for(int i=0; i<9; i++)
		{
			pos[i] = new cell();
			pos[i].type = celltype.Empty;
			ret_pos[i] = new cell();
			ret_pos[i].type = celltype.Empty;
		}
		
		setLayout(null);
		setSize(300, 330);
		setLocation(0, 0);
		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(is_your_turn)
				{
					for(int i=0; i<3; i++)
						for(int j=0; j<3; j++)
						{
							if(e.getX()>=i*100 && e.getX()<i*100 + 100 && e.getY()>=j*100 + 30 && e.getY()<j*100 + 130 && pos[j+i*3].type == celltype.Empty)
							{
								is_your_turn = false;
								pos[j+i*3].type = celltype.Cross;
								repaint();
								if(is_game_over()) new EndGameDialog(getFrames()[0],"ÃÐÓ ÇÀÂÅÐØÅÍÎ!",true,200,100);
								else 
								{
									Search(celltype.Zero,-2,2,0);
									for(int k=0;k<9;k++)
										pos[k].type = ret_pos[k].type;
									repaint();
									if(is_game_over()) new EndGameDialog(getFrames()[0],"ÃÐÓ ÇÀÂÅÐØÅÍÎ!",true,200,100);
									else is_your_turn = true;
								}
								break;
							}
						}
				}
			}
		});
		show();
		
	}
	public boolean isDoubleBuffered(){return true;}
    public static void main(String args[])
	{
		new Tic_tac_toe("Tic-tac-toe");
	}
	public void paint(Graphics g)
	{
		g.setColor(Color.black);
		g.drawLine(100,30,100,330);
		g.drawLine(200,30,200,330);
		g.drawLine(0,130,300,130);
		g.drawLine(0,230,300,230);
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j++)
			{
				if(pos[j + i*3].type == celltype.Cross)
				{
					g.drawLine(i*100 + 25, j*100 + 30 + 25, i*100 + 75, j*100 + 30 + 75);
					g.drawLine(i*100 + 25, j*100 + 30 + 75, i*100 + 75, j*100 + 30 + 25);
				}
				else if(pos[j + i*3].type == celltype.Zero)
				{
					g.drawOval(i*100 + 25, j*100 + 30 + 25, 50, 50);
				}
			}
		if((pos[0].type == celltype.Cross && pos[1].type == celltype.Cross && pos[2].type == celltype.Cross) ||
			(pos[0].type == celltype.Zero && pos[1].type == celltype.Zero && pos[2].type == celltype.Zero))
				g.drawLine(50, 20 + 30, 50, 280 + 30);
				
		if((pos[3].type == celltype.Cross && pos[4].type == celltype.Cross && pos[5].type == celltype.Cross) ||
			(pos[3].type == celltype.Zero && pos[4].type == celltype.Zero && pos[5].type == celltype.Zero))
				g.drawLine(150, 20 + 30, 150, 280 + 30);
				
		if((pos[6].type == celltype.Cross && pos[7].type == celltype.Cross && pos[8].type == celltype.Cross) ||
			(pos[6].type == celltype.Zero && pos[7].type == celltype.Zero && pos[8].type == celltype.Zero))
				g.drawLine(250, 20 + 30, 250, 280 + 30);
				
		if((pos[0].type == celltype.Cross && pos[3].type == celltype.Cross && pos[6].type == celltype.Cross) ||
			(pos[0].type == celltype.Zero && pos[3].type == celltype.Zero && pos[6].type == celltype.Zero))
				g.drawLine(20, 50 + 30, 280, 50 + 30);
				
		if((pos[1].type == celltype.Cross && pos[4].type == celltype.Cross && pos[7].type == celltype.Cross) ||
			(pos[1].type == celltype.Zero && pos[4].type == celltype.Zero && pos[7].type == celltype.Zero))
				g.drawLine(20, 150 + 30, 280, 150 + 30);
				
		if((pos[2].type == celltype.Cross && pos[5].type == celltype.Cross && pos[8].type == celltype.Cross) ||
			(pos[2].type == celltype.Zero && pos[5].type == celltype.Zero && pos[8].type == celltype.Zero))
				g.drawLine(20, 250 + 30, 280, 250 + 30);
				
		if((pos[0].type == celltype.Cross && pos[4].type == celltype.Cross && pos[8].type == celltype.Cross) ||
			(pos[0].type == celltype.Zero && pos[4].type == celltype.Zero && pos[8].type == celltype.Zero))
				g.drawLine(20, 20 + 30, 280, 280 + 30);
				
		if((pos[6].type == celltype.Cross && pos[4].type == celltype.Cross && pos[2].type == celltype.Cross)||
			(pos[6].type == celltype.Zero && pos[4].type == celltype.Zero && pos[2].type == celltype.Zero))
				g.drawLine(20, 280 + 30, 280, 20 + 30);

	}
}
