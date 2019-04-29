package wuziqi0002;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import com.hr.wuziqi.ChessPosition;

public class playListener implements MouseListener {
	int x,y;
	UI ui;
	Graphics g;
	//初始计数
	int count = 0;
	//棋盘大小
	public static int size = 15;
	//对战方式
	public int battleType=0;
	//历史数据
	public static ArrayList<ChessPosition> historyList = new ArrayList<ChessPosition>();
	int listSize;//历史数据数量
	//棋子数组
	public static int [][] chess = new int [size][size];
	//棋子权值数组
	public static int [][] weightArray = new int [size][size];
	//权值表
	public static HashMap<String,Integer> map = new HashMap<>();
	ChessPosition nextPosition;//存储下一位置应下在哪儿
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
    	//判断棋子在棋盘上的位置
    	x = e.getX();
		y = e.getY();
		int x0=(x-25)/50;
		int y0= (y-25)/50;
		System.out.println(x+" "+y+" "+x0+" "+y0);
		//判断是否在棋盘内
		if(x0<15&y0<15) {
			//判断是否已有棋子
			if(chess[x0][y0]==0){
				//判断棋子颜色
				//人机对战模式
				if(battleType==0) {
					if(count%2 == 0){
						chess [x0][y0] = 1;
						g.setColor(Color.BLACK);
						chessMove(x0, y0);
					}
						g.setColor(Color.WHITE);
						AIMove();
		//		        System.out.println("找出来最大的是:" + max);
		//		        System.out.println("其坐标是[" + target_i + "][" + target_j + "]");
					}
				}
			//人人对战
				if(battleType==1) {
					//判断棋子颜色
					if(count%2 == 0){
						chess [x0][y0] = 1;
						g.setColor(Color.BLACK);
					}else{
						chess [x0][y0] = -1;
						g.setColor(Color.WHITE);
					}
					chessMove(x0, y0);
				}
				//机机对战（坐山观虎斗）
				if(battleType==2) {
					if(count==0) {
						//首次下棋盘中央
						g.setColor(Color.BLACK);
						chess[(size-1)/2][(size-1)/2]=1;
						chessMove((size-1)/2,(size-1)/2);
					}
					while(true) {
						AIMove();
						if(AIMove())
						break;
					}
				}
			}
	    }
    //落子相关操作
    public boolean chessMove(int x0,int y0) {
    	//落子
		g.fillOval(30+50*x0, 30+50*y0, 40, 40);
		//用ArrayList记录落子历史
		historyList.add(new ChessPosition(x0,y0));
		count++;
		return isWin(x0,y0);
    }
    public boolean AIMove() {
    	//权值计算
		weighting();
		// 最大值的坐标
		int max = 0;
        int target_i = -1;
        int target_j = -1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (weightArray[i][j] > max) {
                    max = weightArray[i][j];
                    target_i = i;
                    target_j = j;
                }
            }
        }
        //机器落子
        if(count%2 == 0){
        	chess [target_i][target_j] = 1;
			g.setColor(Color.BLACK);
		}else{
			chess [target_i][target_j] = -1;
			g.setColor(Color.WHITE);
		}
        return chessMove(target_i, target_j);
    }
    //胜负判断
    public boolean isWin(int x, int y){
    	//连续棋子个数
    	//横向
    	int h = 0;
    	//竖向
    	int	s = 0;
    	//斜向
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
    		System.out.println("对局结束");
    		if(count%2==0) {
    			ui.PopUp("对局结束","白棋赢！");
    			System.out.println("白棋赢！");
    		}else {
    			ui.PopUp("对局结束","黑棋赢！");
    			System.out.println("黑棋赢！");
    		}
    		return true;
    	}
    	return false;
    }
    //重开
    public void replay() {
    	chess=new int[size][size];
    	//清零计数
    	count=0;
    	//重绘
    	ui.repaint();
    	if(listSize>0) {
    		historyList = new ArrayList<ChessPosition>();
    	}
    }
    //悔棋
    public void regret() {
    	listSize = historyList.size();
    	if(listSize>0) {
    		ChessPosition lastPosition = new ChessPosition();
    		//移除最后一位，同时返回最后一个历史对象
    		lastPosition = historyList.remove(listSize-1);
    		//置0
    		chess[lastPosition.xi][lastPosition.yi]=0;
    		count--;
    		ui.repaint();
    	}else {
    		System.out.println("现在没法悔棋呢？");
    	}
    }
    //对局结束
    public void defeat() {
    	if(count%2==0) {
			ui.PopUp("对局结束","黑棋赢！");
			System.out.println("黑棋赢！");
		}else {
			ui.PopUp("对局结束","白棋赢！");
			System.out.println("白棋赢！");
		}
    	count=0;
    }
    //计算权值
    private void weighting() {
    	for(int i=0;i<size;i++) {
    		for(int j=0;j<size;j++) {
    			int weight=0;
    			//对每个棋盘点的五子组进行遍历
    			//横向五元组(最多五个)
    			for(int n=0;n<5;n++) {
    				//判断左侧和右侧是否存在五元组，限制五元组范围
    				if(i-n>-1&&i+n<15) {
    					String weightkey=""; 
    					//对五元组进行遍历
    					for(int m=0;m<5&&i-n+m>-1&&i-n+m<15;m++) {
    						if(chess[i-n+m][j]==1) {
    							weightkey += "B";
    						}
    						if(chess[i-n+m][j]==-1) {
    							weightkey += "W";
    						}
    					}
    					weight += getWeight(weightkey);
    					System.out.println(weightkey);
    				}else {
    					continue;
    				}
    			}
    			//纵向五元组(最多五个)
    			for(int n=0;n<5;n++) {
    				//判断左侧和右侧是否存在五元组
    				if(j-n>-1&&j+n<15) {
    					String weightkey=""; 
    					//对五元组进行遍历
    					for(int m=0;m<5&&j-n+m<15&&j-n+m>-1;m++) {
    						if(chess[i][j-n+m]==1) {
    							weightkey += "B";
    						}
    						if(chess[i][j-n+m]==-1) {
    							weightkey += "W";
    						}
    					}
    					weight += getWeight(weightkey);
    					
    				}else {
    					continue;
    				}
    			}
    			//正斜五元组(最多五个)
    			for(int n=0;n<5;n++) {
    				//判断左侧和右侧是否存在五元组
    				if(i-n>-1&&i+n<15) {
    					String weightkey=""; 
    					//对五元组进行遍历
    					for(int m=0;m<5&&j-n+m<15&&j-n+m>-1&&i-n+m>-1&&i-n+m<15;m++) {
    						if(chess[i-n+m][j-n+m]==1) {
    							weightkey += "B";
    						}
    						if(chess[i-n+m][j-n+m]==-1) {
    							weightkey += "W";
    						}
    					}
    					weight += getWeight(weightkey);
    					
    				}else {
    					continue;
    				}
    			}
    			//反斜五元组(最多五个)
    			for(int n=0;n<5;n++) {
    				//判断左侧和右侧是否存在五元组
    				if(i-n>-1&&i+n<15) {
    					String weightkey=""; 
    					//对五元组进行遍历
    					for(int m=0;m<5&&i-n+m<15&&i-n+m>-1&&j+n-m>-1&&j+n-m<15;m++) {
    						if(chess[i-n+m][j+n-m]==1) {
    							weightkey += "B";
    						}
    						if(chess[i-n+m][j+n-m]==-1) {
    							weightkey += "W";
    						}
    					}
    					weight += getWeight(weightkey);
    					
    				}else {
    					continue;
    				}
    			}
    			weightArray[i][j] = weight;
    			if(chess[i][j]!=0) {
    				weightArray[i][j] = 0;
    			}
    		}
    	}
    }
    //判断是否两种棋子都含,返回对应的权值
    private int getWeight(String weightkey){
		if(weightkey.indexOf("B")!=-1&&weightkey.indexOf("W")!=-1) {	
			return map.get("Polluted");
		}
		else {
			//包括了为空时的情况
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
