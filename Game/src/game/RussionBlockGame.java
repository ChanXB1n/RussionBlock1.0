package game;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.math.*;

	public class RussionBlockGame {
		private int aa=0;
		private int ic=0;
		private final int sp_width=10;                               //游戏界面宽格
		private final int sp_height=20;                              //游戏界面高格
		private final int types[][][]={                              //游戏方块
				{{-1,0},{0,0},{1,0},{2,0}},                          //长条
				{{0,-1},{0,0},{0,1},{0,2}},
				{{-1,0},{0,0},{1,0},{1,1}},                          //直角（右）
				{{0,1},{0,0},{0,-1},{1,-1}},
				{{1,0},{0,0},{-1,0},{-1,-1}},
				{{0,-1},{0,0},{0,1},{-1,1}},
				{{-1,0},{0,0},{0,1},{1,0}},                          //直角（中）
				{{0,1},{0,0},{1,0},{0,-1}},
				{{1,0},{0,0},{0,-1},{-1,0}},
				{{0,-1},{0,0},{-1,0},{0,1}},
				{{-1,1},{-1,0},{0,0},{1,0}},                         //直接（左）
				{{1,1},{0,1},{0,0},{0,-1}},
				{{1,-1},{1,0},{0,0},{-1,0}},
				{{-1,-1},{0,-1},{0,0},{0,1}},
				{{0,-1},{0,0},{1,0},{1,1}},
				{{-1,0},{0,0},{0,-1},{1,-1}},
				{{0,1},{0,0},{1,0},{1,-1}},
				{{1,0},{0,0},{0,-1},{-1,-1}},
				{{0,0},{0,1},{1,0},{1,1}}                            //正方形
    		};   
		private int[][] block_box=new int[4][2];                     //四个方块坐标
		private int[][] block_box_tt=new int[4][2];
		private int block_x=0,block_y=0;                             //游戏方块在游戏界面中的坐标
		private int block_type=0;                                    //方块类别
		private int[][] game_space=new int[20][10];                  //空间数据
		private int movetype=0;
		private int score=0;
		private int line=0;
    
    public RussionBlockGame(){
    	clearspace();
    	makenewblock();
    }
    
    public void clearspace(){                                  //初始化空间数据
    	for(int i=0;i<sp_height;i++)
    		for(int j=0;j<sp_width;j++)
    			game_space[i][j]=0;
    }
    
    public void makenewblock(){                                   //随机出现方块
        aa=(int)(Math.random()*100%7+1);
        ic=aa*10+1;
    	switch(aa){
    	case 1:
    		block_type=0;
    		break;
    	case 2:
    		block_type=2;
    		break;
    	case 3:
    		block_type=6;
    		break;
    	case 4:
    		block_type=10;
    		break;
    	case 5:
    		block_type=14;
    		break;
    	case 6:
    		block_type=16;
    		break;
    	case 7:
    		block_type=18;
    		break;
    	}
    	block_x=1; 
    	block_y=sp_width/2;
    	for(int i=0;i<4;i++){
    		block_box[i][0]=block_x-types[block_type][i][1];
    		block_box[i][1]=block_y+types[block_type][i][0];
    	}
    }
    
    public void movedown(){
    	block_x++;
    	for(int i=0;i<4;i++)
    	{
    		block_box[i][0]=block_x-types[block_type][i][1];
    	}
    	movetype=1;
    }
    
    public void moveleft(){
    	block_y--;
    	for(int i=0;i<4;i++)
    	{
    		block_box[i][1]=block_y+types[block_type][i][0];
    	}
    	movetype=2;
    }
    
    public void moveright(){
    	block_y++;
    	for(int i=0;i<4;i++)
    	{
    		block_box[i][1]=block_y+types[block_type][i][0];
    	}
    	movetype=3;
    }
    
    public void turnright(){
    	int[][] block_box_temp=new int[4][2];
    	int ic_temp=ic;
    	int block_type_temp=block_type;
    	int id=ic%10;   
    	for(int i=0;i<4;i++){
    		block_box_temp[i][0]=block_box[i][0];
    		block_box_temp[i][1]=block_box[i][1];
    	}
    	if(aa==7)
    		return;
    	else if(aa==1||aa==5||aa==6){
    		if(id==2){
    			block_type--;
    			ic--;
    		}
    		else{
    		    block_type++;
    		    ic++;
    		}
    	}
    	else{
    		if(id==4){
    			block_type=block_type-3;
    			ic=ic-3;
    		}
    		else{
    			block_type++;
    			ic++;
    		}
    	}
    	for(int i=0;i<4;i++){
    		block_box[i][0]=block_x-types[block_type][i][1];
    		block_box[i][1]=block_y+types[block_type][i][0];
    	}
    	if(Iscanmoveto()==false){
    		ic=ic_temp;
    		block_type=block_type_temp;
    		for(int i=0;i<4;i++)
        	{
        		block_box[i][0]=block_box_temp[i][0];
        		block_box[i][1]=block_box_temp[i][1];
        	}
    	}
    }
    
    public void moveback(){
    	if(movetype==1){
    		block_x--;
        	for(int i=0;i<4;i++){
        		block_box[i][0]=block_x-types[block_type][i][1];
        	}
    	}
    	else if(movetype==2){
    		block_y++;
        	for(int m=0;m<4;m++){
        		block_box[m][1]=block_y+types[block_type][m][0];
        	}
    	}
    	else if(movetype==3){
    		block_y--;
        	for(int n=0;n<4;n++){
        		block_box[n][1]=block_y+types[block_type][n][0];
        	}
    	}
    }
    
    public boolean Iscanmoveto(){
    	for(int i=0;i<4;i++){
    		if(block_box[i][0]<0||block_box[i][0]>19){
    			moveback();
    			return false;
    		}
    		else if(block_box[i][1]<0||block_box[i][1]>9){
    			moveback();
    			return false;
    		}
    		else if(game_space[block_box[i][0]][block_box[i][1]]==1){
    			moveback();
    			return false;
    		}
    	}
    	return true;
    }
    	
    public boolean Ishitbottom(){
    	for(int i=0;i<4;i++){
    	    if(block_box[i][0]+1>19){
    	    	for(int m=0;m<4;m++){
    	            game_space[block_box[m][0]][block_box[m][1]]=1;
    	            block_box_tt[m][0]=block_box[m][0];
    	            block_box_tt[m][1]=block_box[m][1];
    	            block_box[m][0]=0;
    	            block_box[m][1]=0;
    	    	}
    	    	return true;
    	    }    	    
    	}
    	for(int i=0;i<4;i++){
    		if(game_space[block_box[i][0]+1][block_box[i][1]]==1){
    	    	for(int m=0;m<4;m++){
    	    		game_space[block_box[m][0]][block_box[m][1]]=1;
    	            block_box_tt[m][0]=block_box[m][0];
    	            block_box_tt[m][1]=block_box[m][1];
    	            block_box[m][0]=0;
    	            block_box[m][1]=0;
    	    	}
    	    	return true;
    	    }
    	}
    	return false;
    }
    
    public void CheckAndCutLine(){
    	int a[]={block_box_tt[0][0],block_box_tt[1][0],block_box_tt[2][0],block_box_tt[3][0]};
    	int b[]={30,30,30,30};
    	int temp=0;
    	int temp1=0;
    	int count=0;										//同时有多少个块消除
    	int ss=0;
    	for(int i=0;i<4;i++){
    		for(int j=0;j<10;j++){
    			if(game_space[a[i]][j]==1)
    			temp++;	
    		}
    		if(temp==10){
    			for(int m=0;m<4;m++)
    				if(b[m]==a[i]){
    				    break;
    				}
    				else
    					ss++;
    			if(ss==4){
    				b[count]=a[i];
			        count++;
			        score=score+count*100;
    			}
    		}
    		temp=0;
    		ss=0;
    	}
		line+=count;
    	for(int i=0;i<3;i++)
    		for(int j=i+1;j<4;j++){
    			if(b[i]>b[j]){
    			    temp1=b[i];
    			    b[i]=b[j];
    			    b[j]=temp1;
    			}
    		}
    	for(int n=0;n<4;n++){
    		if(b[n]==30)
    			break;
    		else{
    			for(int aa=b[n]-1;aa>=0;aa--){
    				for(int bb=0;bb<10;bb++){
    					game_space[aa+1][bb]=game_space[aa][bb];
    				}
    			}
    			for(int cc=0;cc<10;cc++)
    				game_space[0][cc]=0;
    		}
    	}
    }
    
    public boolean IsGameOver(){
    	boolean flag=false;
    	for(int i=0;i<sp_width;i++){
    		if(game_space[0][i]==1){
    			flag=true;
    			break;
    		}  		
    	}
    	if(flag==true){
    		IsOver();
    	}
    	return flag;
    }
    
    public void sure(){
    	for(int i=0;i<4;i++)
		    game_space[block_box[i][0]][block_box[i][1]]=1;
    }
    
    public void notsure(){
    	for(int i=0;i<4;i++)
		    game_space[block_box[i][0]][block_box[i][1]]=0;
    }
   
    public boolean judge(int i,int j){
    	if(game_space[i][j]==1)
    		return true;
    	else
    		return false;
    }
    
    public int getScore(){
    	return score;
    }
    
    public int getLine(){
    	return line;
    }
    public void IsOver(){
    	clearspace();
		score=0;
		line=0;
    }
    public int getLevel(){
    	int level=1;
    	if((Win.score>1000)&&(Win.score<=2000)){
			level=2;
		}
		if((Win.score>2000)&&(Win.score<=3500)){
			level=3;
		}
		if((Win.score>3500)&&(Win.score<=5000)){
			level=4;
		}
		if(Win.score>5000){
			level=5;
		}
		return level;
    }
}