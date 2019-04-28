package wuziqi0002;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class UI extends JPanel{
	/**
	 * 
	 */
	Graphics g;
	//边框宽度
	public int border;
	//棋盘宽高
	public int width;
	public int height;
	//棋格大小
	public int cubeSize;
	private static final long serialVersionUID = 1L;
	public void showUI(){
		//棋盘大小
		JFrame jf = new JFrame();
		jf.setSize(1000, 850);
		jf.setTitle("五子棋");
		border = 50 ;
		cubeSize = 50;
//		setBackground(new Color(238,238,238));
		jf.setDefaultCloseOperation(3);;//设置关闭按钮功能
		jf.setLayout(new BorderLayout());//设置为框架布局
		
		//分别设置各组件大小
		Dimension left = new Dimension(850,850);
		Dimension right = new Dimension(200,800);
		Dimension btnSize = new Dimension(150,50);//按钮大小
		
		//左侧界面实现
		this.setPreferredSize(left);
//		width = leftJP.getWidth();
//		height = leftJP.getHeight();
//		System.out.println(width+" "+height);
		this.setBackground(new Color(249,214,91));
		jf.add(this,BorderLayout.CENTER);
		
		//右侧界面实现
		JPanel rightJP = new JPanel();
		rightJP.setPreferredSize(right);
//		rightJP.setBackground(Color.WHITE);
		jf.add(rightJP,BorderLayout.EAST);
		rightJP.setLayout(new FlowLayout());//右侧控制区采用流式布局
		
		//添加按钮
		String [] buttonName = {"新游戏","悔棋","认输"};
		JButton[] button = new JButton[3];
		for(int i=0;i<buttonName.length;i++) {
			button[i] = new JButton(buttonName[i]);
			button[i].setPreferredSize(btnSize);
			rightJP.add(button[i]);
		}
		
		//设置选项按钮
		String[] boxname= {"人机对战","人人对战","菜机互啄"};
		JComboBox<String> box=new JComboBox<>(boxname);
		box.setPreferredSize(btnSize);
		rightJP.add(box);
				
		jf.setVisible(true);
		//下棋区添加监听器对象
		playListener p = new playListener(this);
		this.addMouseListener(p);
		
		//按钮添加监听器对象
		ButtonListener BL = new ButtonListener(p,box);
		for(int i=0;i<buttonName.length;i++) {
			button[i].addActionListener(BL);
		}
		box.addActionListener(BL);
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
		for(int i=0;i<playListener.size;i++){
			//绘制棋盘
			g.drawLine(border, border + i * cubeSize, 800 - border, border + i*cubeSize);
			g.drawLine(border + i*cubeSize, border , border + i*cubeSize, 800 - border);
		}
		//保证最小化棋盘不会被清零
		int[][] l = playListener.chess;
		for (int i=0;i<l.length;i++) {
			for(int j=0;j<l.length;j++) {
				if(l[i][j]==1) {
					g.setColor(Color.BLACK);
					g.fillOval(30+50*i, 30+50*j, 40, 40);
				}if(l[i][j]==-1) {
					g.setColor(Color.WHITE);
					g.fillOval(30+50*i, 30+50*j, 40, 40);
				}
			}
		}
	}
	public void PopUp(String title,String message) {
//		JOptionPane jo=new JOptionPane();
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
//		jo.showMessageDialog(null, result, top, JOptionPane.PLAIN_MESSAGE);
	}
	public static void main(String args[]){
		UI u = new UI();
		u.showUI();
		
	}
}
