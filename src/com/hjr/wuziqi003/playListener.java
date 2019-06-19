package com.hjr.wuziqi003;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.hjr.wuziqi003.ChessPosition;

public class playListener implements MouseListener {
	int x,y;
	UI ui;
	Graphics g;
	//��ʼ����
	int count = 0;
	//���̴�С
	public static int size = 15;
	//��ս��ʽ
	public int battleType=0;
	//��ʷ����
	public static ArrayList<ChessPosition> historyList = new ArrayList<ChessPosition>();
	int listSize;//��ʷ��������
	//��������
	public static int [][] chess = new int [size][size];
	//����Ȩֵ����
	public static int [][] weightArray = new int [size][size];
	//Ȩֵ��
	public static HashMap<String,Integer> map = new HashMap<>();
	ChessPosition nextPosition;//�洢��һλ��Ӧ�����Ķ�
	static {
		map.put("", 7);
		map.put("B", 35);
		map.put("BB", 800);
		map.put("BBB", 15000);
		map.put("BBBB", 800000);
		map.put("W", 15);
		map.put("WW", 400);
		map.put("WWW", 1800);
		map.put("WWWW", 100000);
		map.put("Polluted", 0);
		
	}
	public playListener(UI ui) {
		// TODO Auto-generated constructor stub
		this.ui = ui;
		this.g = ui.getGraphics();
	}

	public void mouseClicked(MouseEvent e){
		
	}

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    public  void mousePressed(MouseEvent e){
//		 for (int[] row : chess) {
//	         for (int each : row) {
//	             System.out.print(each + "\t");
//	         }
//	         System.out.println();
//	     }
    	//�ж������������ϵ�λ��
    	x = e.getX();
		y = e.getY();
		int x0=(x-25)/50;
		int y0= (y-25)/50;
		System.out.println(x+" "+y+" "+x0+" "+y0);
		//�ж��Ƿ���������
		if(x0<15&y0<15) {
			//�ж��Ƿ���������
			if(chess[x0][y0]==0){
				//�ж�������ɫ
				//�˻���սģʽ
				if(battleType==0) {
					if(count%2 == 0){
						chess [x0][y0] = 1;
						g.setColor(Color.BLACK);
						chessMove(x0, y0);
					}
						g.setColor(Color.WHITE);
						AIMove();
		//		        System.out.println("�ҳ���������:" + max);
		//		        System.out.println("��������[" + target_i + "][" + target_j + "]");
					}
				}
			//���˶�ս
				if(battleType==1) {
					//�ж�������ɫ
					if(count%2 == 0){
						chess [x0][y0] = 1;
						g.setColor(Color.BLACK);
					}else{
						chess [x0][y0] = -1;
						g.setColor(Color.WHITE);
					}
					chessMove(x0, y0);
				}
				//������ս����ɽ�ۻ�����
				if(battleType==2) {
					if(count==0) {
						g.setColor(Color.BLACK);
						Random r1 = new Random ();
						Random r2 = new Random ();
						int randomPosition1 = r1.nextInt(size-1);
						int randomPosition2 = r2.nextInt(size-1);
						chess[randomPosition1][randomPosition2]=1;
						chessMove(randomPosition1,randomPosition2);
					}
					while(true) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException error) {
							// TODO Auto-generated catch block
							error.printStackTrace();
						}
						AIMove();
						if(AIMove())
						break;
					}
				}
			}
	    }
    //������ز���
    public boolean chessMove(int x0,int y0) {
    	//����
		g.fillOval(30+50*x0, 30+50*y0, 40, 40);
		//��ArrayList��¼������ʷ
		historyList.add(new ChessPosition(x0,y0));
		count++;
		return isWin(x0,y0);
    }
    public boolean AIMove() {
    	//Ȩֵ����
		weighting();
		// ���ֵ������
		int max = -1;
        int target_i = -1;
        int target_j = -1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (weightArray[i][j] > max) {
                    max = weightArray[i][j];
                    target_i = i;
                    target_j = j;
                }
                System.out.print(weightArray[i][j] + "    ");
            }
            System.out.println();
        }
        if(weightArray[target_i][target_j]==-2) {
        	ui.PopUp("�Ծֽ���","ƽ�֣�");
        	return true;
        }
        //��������
        if(count%2 == 0){
        	chess [target_i][target_j] = 1;
			g.setColor(Color.BLACK);
		}else{
			chess [target_i][target_j] = -1;
			g.setColor(Color.WHITE);
		}
        return chessMove(target_i, target_j);
    }
    //ʤ���ж�
    public boolean isWin(int x, int y){
    	//�������Ӹ���
    	//����
    	int h = 0;
    	//����
    	int	s = 0;
    	//б��
    	int xie1 = 0;
    	int xie2 = 0;
    	for (int i=x+1;i<size;i++){
    		if(chess[i][y]== chess[x][y]){
    			h++;
    		}else{
    			break;
    		}
    	}
    	for(int i=x-1;i>-1;i--){
    		if(chess[i][y]== chess[x][y]){
    			h++;
    		}else{
    			break;
    		}
    	}
    	for (int i=y+1;i<size;i++) {
    		if(chess[x][i] == chess[x][y]) {
    			s++;
    		}else {
    			break;
    		}
    	}
    	for (int i=y-1;i>-1;i--) {
    		if(chess[x][i] == chess[x][y]) {
    			s++;
    		}else {
    			break;
    		}
    	}
    	for (int i=x+1,j=y+1;i<size&&j<size;i++,j++) {
    		if(chess[i][j] == chess[x][y]) {
    			xie1++;
    		}else {
    			break;
    		}
    	}
    	for (int i=x-1,j=y-1;i>-1&&j>-1;i--,j--) {
    		if(chess[i][j] == chess[x][y]) {
    			xie1++;
    		}else {
    			break;
    		}
    	}
    	for (int i=x+1,j=y-1;i<size&j>-1;i++,j--) {
    		if(chess[i][j] == chess[x][y]) {
    			xie2++;
    		}else {
    			break;
    		}
    	}
    	for (int i=x-1,j=y+1;i>-1&j<size;i--,j++) {
    		if(chess[i][j] == chess[x][y]) {
    			xie2++;
    		}else {
    			break;
    		}
    	}
    	if(h >= 4 || s >= 4 || xie1 >= 4 || xie2 >= 4){
    		System.out.println("�Ծֽ���");
    		if(count%2==0) {
    			ui.PopUp("�Ծֽ���","����Ӯ��");
    			System.out.println("����Ӯ��");
    		}else {
    			ui.PopUp("�Ծֽ���","����Ӯ��");
    			System.out.println("����Ӯ��");
    		}
    		return true;
    	}
    	return false;
    }
    //�ؿ�
    public void replay() {
    	chess=new int[size][size];
    	//�������
    	count=0;
    	//�ػ�
    	ui.repaint();
    	if(listSize>0) {
    		historyList = new ArrayList<ChessPosition>();
    	}
    }
    //����
    public void regret() {
    	listSize = historyList.size();
    	if(listSize>0) {
    		ChessPosition lastPosition = new ChessPosition();
    		//�Ƴ����һλ��ͬʱ�������һ����ʷ����
    		lastPosition = historyList.remove(listSize-1);
    		//��0
    		chess[lastPosition.xi][lastPosition.yi]=0;
    		count--;
    		ui.repaint();
    	}else {
    		System.out.println("����û�������أ�");
    	}
    }
    //�Ծֽ���
    public void defeat() {
    	if(count%2==0) {
			ui.PopUp("�Ծֽ���","����Ӯ��");
			System.out.println("����Ӯ��");
		}else {
			ui.PopUp("�Ծֽ���","����Ӯ��");
			System.out.println("����Ӯ��");
		}
    	count=0;
    }
    //����Ȩֵ
    private void weighting() {
    	for(int i=0;i<size;i++) {
    		for(int j=0;j<size;j++) {
    			int weight=0;
    			//�жϴ��ڼ��������飬���ز����ڵ�������
    			for(int m=4;m>-1;m--) {
    				//����
    				if(i-m>-1) {
    					//��ÿ�����̵����������б���
    					String weightkey=""; 
    	    			for(int a=i-m;a<i-m+5&&a<size;a++) {
    	    				if(chess[a][j]==1) {
    							weightkey += "B";
    						}
    						if(chess[a][j]==-1) {
    							weightkey += "W";
    						}
    	    			}
    	    			weight += getWeight(weightkey);
    				}
    				//����
    				if(j-m>-1) {
    					//��ÿ�����̵����������б���
    					String weightkey=""; 
    	    			for(int a=j-m;a<j-m+5&&a<size;a++) {
    	    				if(chess[i][a]==1) {
    							weightkey += "B";
    						}
    						if(chess[i][a]==-1) {
    							weightkey += "W";
    						}
    	    			}
    	    			weight += getWeight(weightkey);
    				}
    				//��б
    				if(i-m>-1&&j+m<15) {
    					//��ÿ�����̵����������б���
    					String weightkey=""; 
    	    			for(int a=i-m,b=j+m;a<i-m+5&&b>j+m-5&&a<size&&b>-1;a++,b--) {
    	    				if(chess[a][b]==1) {
    							weightkey += "B";
    						}
    						if(chess[a][b]==-1) {
    							weightkey += "W";
    						}
    	    			}
    	    			weight += getWeight(weightkey);
    				}
    				//��б
    				if(i-m>-1&&j-m>-1) {
    					//��ÿ�����̵����������б���
    					String weightkey=""; 
    	    			for(int a=i-m,b=j-m;a<i-m+5&&b<j-m+5&&a<size&&b<size;a++,b++) {
    	    				if(chess[a][b]==1) {
    							weightkey += "B";
    						}
    						if(chess[a][b]==-1) {
    							weightkey += "W";
    						}
    	    			}
    	    			weight += getWeight(weightkey);
    				}
    			}
    			weightArray[i][j] = weight;
    			if(chess[i][j]!=0) {
    				weightArray[i][j] = -2;
    			}
    		}
    	}
    }
    //�ж��Ƿ��������Ӷ���,���ض�Ӧ��Ȩֵ
    private int getWeight(String weightkey){
		if(weightkey.indexOf("B")!=-1&&weightkey.indexOf("W")!=-1) {	
			return map.get("Polluted");
		}
		else {
			//������Ϊ��ʱ�����
			return map.get(weightkey);
		}
    }
    /**
     * Invoked when a mouse button has been released on a component.
     */
    public void mouseReleased(MouseEvent e){
    	
    }

    /**
     * Invoked when the mouse enters a component.
     */
    public void mouseEntered(MouseEvent e){
    	
    }

    /**
     * Invoked when the mouse exits a component.
     */
    public void mouseExited(MouseEvent e){
    	
    }
}
