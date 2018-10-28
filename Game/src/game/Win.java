package game;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

	public class Win {
		public static void main(String[] args) {
			GameWin frame=new GameWin();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		}	
		public static boolean isplaying=false;
		public static int score=0;
		public static int level=1;
		public static int line=0;
		public static boolean isDown;
		public static boolean btStop=true;
		public static boolean end=true;
	}

	class GameWin extends JFrame{
		public static final int Default_X=650;
		public static final int Default_Y=630;
		static left jpy2=new left();           //��ߵ����
		static right jpy3=new right();			//�ұߵ����
		static JSplitPane jpy1=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jpy2,jpy3);  //�����ڷָ���������
		
		public GameWin(){
			this.setFocusable(true);
			getContentPane().requestFocus();
			this.setAlwaysOnTop(true);
			setTitle("super russionBlock");
			setBounds(350,90,Default_X,Default_Y);
			setResizable(false);
			add(jpy1);
			jpy1.setDividerLocation(430);
			jpy1.setDividerSize(4);
			addKeyListener(jpy2);
			Thread t=new Thread(jpy2);			//��thread�����ഴ���̣߳�ͨ��Thread��������Runnableʵ�ֵĶ��߳�
			t.start();
		}			
	}

	class right extends JPanel {  								//�ұߵ����
		private JTextField[] jtfArray={new JTextField("ͬʱ����1�У�100��"),new JTextField("ͬʱ����2�У�300��"),
									   new JTextField("ͬʱ����3�У�600��"),new JTextField("ͬʱ����4�У�1000��"),
									   new JTextField("   0~1000���Ѷ�1"),new JTextField("1001~2000���Ѷ�2"),
									   new JTextField("2001~3500���Ѷ�3"),new JTextField("3501~5000���Ѷ�4"),
									   new JTextField("5001~    ���Ѷ�5")};
		private JButton[] jbArray={new JButton("����ģʽ(����)"),new JButton("���ģʽ(����)"),
								   new JButton("��ͣ��Ϸ"), new JButton("������Ϸ"),
								   new JButton("�˳���Ϸ"),};
		public right(){
			initialframe();						
			initialListener();	
		}
		
		public void initialframe(){
			int spacejtf=30;
			int spacejb=350;
			setLayout(null);
			for(int i=0;i<jtfArray.length;i++){
				add(jtfArray[i]);
				jtfArray[i].setBounds(10,spacejtf,200,30);
				jtfArray[i].setFocusable(false);
				jtfArray[i].setEditable(false);
				jtfArray[i].setBorder(null);
				jtfArray[i].setFont(new Font("����", Font.BOLD, 17));
				if(i<4){
					spacejtf+=10;
				}
				spacejtf+=30;
			}

			for(int i=0;i<jbArray.length;i++){
				add(jbArray[i]);
				jbArray[i].setBounds(50,spacejb,120,35);
				jbArray[i].setFocusable(false);
				spacejb+=50;
			}		
		}

		public void initialListener(){
			buttonListener();
		}
		void buttonListener(){
			for(int i=0;i<5;i++){
				jbArray[i].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(e.getSource()==jbArray[0]){						//��e.getSource()�������ĸ�button��Ӧ��
							Win.isplaying=true;
						}
						else if(e.getSource()==jbArray[1]){
							Win.isplaying=true;
							Win.level=6;
						}
						else if(e.getSource()==jbArray[2]){
							Win.isplaying=false;
						}
						else if(e.getSource()==jbArray[3]){
							Win.isplaying=false;
							Win.btStop=false;
							Win.level=1;
							Win.line=0;
						}
						else if(e.getSource()==jbArray[4]){
							System.exit(0);		
						}
					}
				});
			}
		}
		
	}

	class left extends JComponent implements KeyListener,Runnable{  //ͨ��Thread��������Runnableʵ�ֵĶ��߳�github
		private RussionBlockGame game;
		public static final int boxSize=30;
		public left(){
			game=new RussionBlockGame();
		}
	
		public void paintComponent(Graphics g){
			Graphics2D g2=(Graphics2D)g;
			super.paintComponent(g);
			double width=300,height=600;
			Win.score=game.getScore();
			Win.line=game.getLine();
			if(Win.level!=6){
				Win.level=game.getLevel();
			}		
			Rectangle2D rect=new Rectangle2D.Double(0,0,width,height);
			g2.setColor(Color.black);
			g2.draw(rect);
			g2.setColor(Color.black);
			g2.fill(rect);
			g2.setColor(Color.black);
			g2.draw(rect);
			for(int i=0;i<20;i++)
				for(int j=0;j<10;j++){
					if(game.judge(i,j)==true){
						Rectangle2D rect3=new Rectangle2D.Double(j*boxSize,i*boxSize,boxSize,boxSize);
						g2.setColor(Color.black);
						g2.draw(rect3);
						switch(Win.level){
						case 1:
							g2.setColor(Color.yellow);
							break;
						case 2:
							g2.setColor(Color.cyan);
							break;
						case 3:
							g2.setColor(Color.magenta);
							break;
						case 4:
							g2.setColor(Color.orange);
							break;
						case 5:
							g2.setColor(Color.green);
							break;
						case 6:
							g2.setColor(Color.red);
							break;
						}					
						g2.fill(rect3);
						g2.setColor(Color.black);
						g2.draw(rect3);
					}
				}
			g.setFont(new Font("����", Font.BOLD, 24));
			g2.drawString("��Ϸ�Ѷ�",320,100);
			if(Win.level!=6){
				g2.drawString(""+Win.level,362,130);
			}
			else{
				g2.drawString("Crazy",340,130);
			}
			g2.drawString("��������",320,180);
			g2.drawString(""+Win.line,362,210);
			g2.drawString("��Ϸ����",320,260);
			if(Win.score==0){
				g2.drawString(""+Win.score,363,290);
			}
			if((Win.score<1000)&&(Win.score>0)){
				g2.drawString(""+Win.score,349,290);
			}
			if((Win.score>=1000)&&(Win.score<10000)){
				g2.drawString(""+Win.score,345,290);
			}
			if(Win.score>=10000){
				g2.drawString(""+Win.score,337,290);
			}
			g2.drawString("�ƶ���",333,380);
			g2.drawString("������",333,410);
			g2.drawString("���μ�",333,450);
			g2.drawString("��",358,480);
			g2.drawString("�����",333,520);
			g2.drawString("space",340,550);
			game.notsure();
		}
	
		public void keyTyped(KeyEvent e) {}
	
		public void keyPressed(KeyEvent e) {
			if(Win.isplaying==false)
				return;
			switch(e.getKeyCode()){
			case KeyEvent.VK_LEFT:                                       //LEFT
				game.moveleft();
				movejudge();
				break;	
			case KeyEvent.VK_UP:                                         //UP
				game.turnright();
				movejudge();
				break;                                   
			case KeyEvent.VK_RIGHT:                                      //RIGHT
				game.moveright();
				movejudge();
				break;
			case KeyEvent.VK_DOWN:                                       //DOWN
				game.movedown();
				movejudge();
				break;
			case KeyEvent.VK_SPACE:										 //DownTillCanNotMove
				while(game.Iscanmoveto()==true){
					game.movedown();
				}
				movejudge();
				break;
			case KeyEvent.VK_C:										//test����ר����գ�������������
				game.clearspace();
			}
		}

	public void keyReleased(KeyEvent e) {}	
	
		public void movejudge(){
			if(game.Iscanmoveto()==true){
				game.sure();
				repaint();
			}
			else if(game.Ishitbottom()==true){
				game.CheckAndCutLine();
				game.makenewblock();
				repaint();
				if(game.IsGameOver()==true){
					Win.isplaying=false;
					JOptionPane.showMessageDialog(GameWin.jpy1, "��Ϸ������", "֪ͨ", JOptionPane.ERROR_MESSAGE);
				}		
			}
			if(Win.btStop==false){
				game.IsOver();
			}
		}

		public void run(){
			try{ 
				while(Win.end==true){
					while(Win.isplaying){
						switch(Win.level){
						case 1:
							Thread.sleep(500);
							break;
						case 2:
							Thread.sleep(400);
							break;
						case 3:
							Thread.sleep(350);
							break;
						case 4:
							Thread.sleep(300);
							break;
						case 5:
							Thread.sleep(250);
							break;
						case 6:
							Thread.sleep(100);
							break;
						}											
			    	    game.movedown();
		    		    movejudge();
						}
					Thread.sleep(300);
					if(Win.btStop==false){
						game.IsOver();
						game.makenewblock();
						repaint();
						Win.btStop=true;
					}
				}		
//				if(Win.end==false){
//					System.out.print("1");
//					System.exit(0);
//				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}	

}

