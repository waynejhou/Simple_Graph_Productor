package SGProductor;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Graph_Panel extends JPanel{

	public Graph_Object graph = new Graph_Object() ;
	private Dimension GPanel_size = new Dimension( 800 , 600 ) ;
	private BufferedImage bufimg = new BufferedImage( GPanel_size.width , GPanel_size.height , BufferedImage.TYPE_INT_BGR );
	//private BufferedImage undoimg ; 
	private int tool_change = -1 ;//功能切換
	private int tool_change_pre = -1 ;//功能切換 
	//-3為被取消的 復原功能
	//-2為畫圖片功能
	//-1為清除功能
	//0	為沒事功能
	//1	為筆刷功能
	//2	為圓形功能
	//3	為矩形功能
	//4	為三角形功能
	//5	為愛心功能
	//6	為直線功能
	private int mouse_x_i ;//滑鼠座標x軸 初始值
	private int mouse_y_i ;//滑鼠座標y軸 初始值
	private int mouse_x_f ;//滑鼠座標x軸 結束值
	private int mouse_y_f ;//滑鼠座標y軸 結束值
	private Point mouse_p_i = new Point() ;//滑鼠座標  初始值
	private Point mouse_p_f = new Point() ;//滑鼠座標 結束值
	private Point mouse_p_t = new Point() ;//滑鼠座標 暫存值
	private boolean i_and_f = false ;//切換 初始和結束
	private boolean first_dot = true ;//是否第一次按下滑鼠
	private boolean is_pre = false ;//是否是預覽時(拖時滑鼠時，放開前的圖形)
	private Image img_cursor_pancil ;//滑鼠遊標圖案
	private Cursor pencil_cursor ;//滑鼠遊標
	Graph_Panel(){
		try {//讀取滑鼠遊標圖案
			img_cursor_pancil = ImageIO.read( new File( "pancle_cursor.png" ));
		} catch (IOException e) {
			 //TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//初始設定自訂游標
		pencil_cursor = Toolkit.getDefaultToolkit().createCustomCursor( img_cursor_pancil , new Point(15,15) , "test") ;
		addMouseListener( new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent me) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent me) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent me) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mousePressed(MouseEvent me) {//按下去時 取的滑鼠初始值座標
				// TODO Auto-generated method stub
				if( tool_change == 2 || tool_change == 3 || tool_change == 4 || tool_change == 5 || tool_change == 6 )
				{
					is_pre = true ;
					mouse_p_i = me.getPoint() ;
					//System.out.println( "( " + mouse_p_i.x + " , " + mouse_p_i.y + " )");
				}
				//undoimg = bufimg ;
			}

			@Override
			public void mouseReleased(MouseEvent me) {//放開時 取的滑鼠結束值座標
				// TODO Auto-generated method stub
				if( tool_change == 1 )
				{
					first_dot = true ;
					i_and_f = false ;
				}
				else if( tool_change == 2 || tool_change == 3 || tool_change == 4 || tool_change == 5 || tool_change == 6 )
				{
					is_pre = false ;
					first_dot = true ;
					i_and_f = false ;
					mouse_p_f = me.getPoint() ;
					draw();//更新畫布
					update(getGraphics());
					//System.out.println( "( " + mouse_p_f.x + " , " + mouse_p_f.y + " )");
				}
			}
		});
		//滑鼠拖曳事件
		addMouseMotionListener( new MouseMotionListener(){
			@Override
			public void mouseDragged(MouseEvent mme) {//左鍵拖曳時，會不斷交互取得滑鼠座標給初始值和結束值
				// TODO Auto-generated method stub	  //每次取得結束值時繪更新畫布一次
				if( tool_change == -1 )
				{
				}
				else if( tool_change == 1 )
				{
					if( i_and_f == false )
					{
						if( first_dot )
						{
							mouse_x_i = mme.getX() ;
							mouse_y_i = mme.getY() ;
							first_dot = false ;
						}
						else
						{
							mouse_x_i = mouse_x_f ;
							mouse_y_i = mouse_y_f ;
						}
						i_and_f = true ;
					}
					else if( i_and_f == true )
					{
						mouse_x_f = mme.getX() ;
						mouse_y_f = mme.getY() ;
						draw();
						i_and_f = false ;
					}
					//System.out.println( "( " + mme.getX() + " , " + mme.getY() + " )");
				}
				else if( tool_change == 2 || tool_change == 3 || tool_change == 4 || tool_change == 5 || tool_change == 6 )
				{//左鍵拖曳時，會不斷交互取得滑鼠座標給結束值和暫存值
				 //每次取得結束值時繪更新畫布一次
					if( i_and_f == false )
					{
						if( first_dot )
						{
							mouse_p_t = mme.getPoint() ;
							first_dot = false ;
						}
						else
						{
							mouse_p_t = mouse_p_f ;
						}
						i_and_f = true ;
					}
					else if( i_and_f == true )
					{
						mouse_p_f = mme.getPoint() ;
						update( getGraphics() );
						i_and_f = false ;
					}
					//System.out.println( "( " + mme.getX() + " , " + mme.getY() + " )");
				}
			}
			@Override
			public void mouseMoved(MouseEvent mme) {//游標在裡畫布時才改變
				// TODO Auto-generated method stub
				if( mme.getX() >= 5 && mme.getX()<= bufimg.getWidth()+5 )
				{
					setCursor( pencil_cursor );
				}
				else
				{
					setCursor( new Cursor(Cursor.DEFAULT_CURSOR) );
				}
			}
		});
	}
	public void paint(Graphics g)
	{
		this.getRootPane().revalidate();
		g.setColor( new Color( 225 ,225 , 225 ) );
		g.fillRect(0, 0, 1920, 1080);
		g.drawImage( bufimg , 5 , 5 , this ) ;//把裡畫布畫在表畫布
		if( is_pre == true )//畫預覽圖在表畫布
		{
			preview_draw( (Graphics2D) g ) ;
		}
	}
	public void draw(){
		/*if( tool_change == -3 )
		{
			graph.paint_pre( (Graphics2D)bufimg.getGraphics() , undoimg );
		}*/
		if ( tool_change == -2 )
		{
			graph.paint_img( (Graphics2D)bufimg.getGraphics()  ) ;
			update(getGraphics());
		}
		else if( tool_change == -1 )
		{
			graph.paint_clear( bufimg.getGraphics() );
			update(getGraphics());
		}
		else if( tool_change == 1 )
		{
			graph.paint_pencil( (Graphics2D)bufimg.getGraphics() , mouse_x_i , mouse_y_i , mouse_x_f , mouse_y_f) ;
			update(getGraphics());
		}
		else if( tool_change == 2 )//最後將初始值和結束值座標畫圖案在裡畫布上
		{						   //不同拖曳方向的 座標長寬值 有不同的長寬計算法
			if( mouse_p_i.x > mouse_p_f.x && mouse_p_i.y > mouse_p_f.y)// | <-\ | 
			{
				graph.paint_circle( (Graphics2D)bufimg.getGraphics() , mouse_p_f.x , mouse_p_f.y , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ) ;
			}
			else if( mouse_p_i.x < mouse_p_f.x && mouse_p_i.y > mouse_p_f.y)// | /-> | 
			{
				graph.paint_circle( (Graphics2D)bufimg.getGraphics() , mouse_p_i.x , mouse_p_i.y-Math.abs(mouse_p_f.y - mouse_p_i.y) , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ) ;
			}
			else if( mouse_p_i.x > mouse_p_f.x && mouse_p_i.y < mouse_p_f.y)// | <-/ | 
			{
				graph.paint_circle( (Graphics2D)bufimg.getGraphics() , mouse_p_f.x , mouse_p_f.y-Math.abs(mouse_p_f.y - mouse_p_i.y) , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ) ;
			}
			else// | \-> | 
			{
				graph.paint_circle( (Graphics2D)bufimg.getGraphics() , mouse_p_i.x , mouse_p_i.y , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ) ;
			}
		}
		else if( tool_change == 3 )
		{
			if( mouse_p_i.x > mouse_p_f.x && mouse_p_i.y > mouse_p_f.y)// | <-\ | 
			{
				graph.paint_rect( (Graphics2D)bufimg.getGraphics() , mouse_p_f.x , mouse_p_f.y , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ) ;
			}
			else if( mouse_p_i.x < mouse_p_f.x && mouse_p_i.y > mouse_p_f.y)// | /-> | 
			{
				graph.paint_rect( (Graphics2D)bufimg.getGraphics() , mouse_p_i.x , mouse_p_i.y-Math.abs(mouse_p_f.y - mouse_p_i.y) , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ) ;
			}
			else if( mouse_p_i.x > mouse_p_f.x && mouse_p_i.y < mouse_p_f.y)// | <-/ | 
			{
				graph.paint_rect( (Graphics2D)bufimg.getGraphics() , mouse_p_f.x , mouse_p_f.y-Math.abs(mouse_p_f.y - mouse_p_i.y) , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ) ;
			}
			else// | \-> | 
			{
				graph.paint_rect( (Graphics2D)bufimg.getGraphics() , mouse_p_i.x , mouse_p_i.y , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ) ;
			}
		}
		else if( tool_change == 4 )
		{
			if( mouse_p_i.x > mouse_p_f.x && mouse_p_i.y > mouse_p_f.y)// | <-\ | 
			{
				graph.paint_tri( (Graphics2D)bufimg.getGraphics() , mouse_p_f.x , mouse_p_f.y , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) , false ) ;

			}
			else if( mouse_p_i.x < mouse_p_f.x && mouse_p_i.y > mouse_p_f.y)// | /-> | 
			{
				graph.paint_tri( (Graphics2D)bufimg.getGraphics() , mouse_p_i.x , mouse_p_i.y-Math.abs(mouse_p_f.y - mouse_p_i.y) , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) , false) ;
			}
			else if( mouse_p_i.x > mouse_p_f.x && mouse_p_i.y < mouse_p_f.y)// | <-/ | 
			{
				graph.paint_tri( (Graphics2D)bufimg.getGraphics() , mouse_p_f.x , mouse_p_f.y-Math.abs(mouse_p_f.y - mouse_p_i.y) , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ,true ) ;
			}
			else// | \-> | 
			{
				graph.paint_tri( (Graphics2D)bufimg.getGraphics() , mouse_p_i.x , mouse_p_i.y , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ,true ) ;
			}
		}
		else if( tool_change == 5 )
		{
			if( mouse_p_i.x > mouse_p_f.x && mouse_p_i.y > mouse_p_f.y)// | <-\ | 
			{
				graph.paint_heart( (Graphics2D)bufimg.getGraphics() , mouse_p_f.x , mouse_p_f.y , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) , false ) ;
			}
			else if( mouse_p_i.x < mouse_p_f.x && mouse_p_i.y > mouse_p_f.y)// | /-> | 
			{
				graph.paint_heart( (Graphics2D)bufimg.getGraphics() , mouse_p_i.x , mouse_p_i.y-Math.abs(mouse_p_f.y - mouse_p_i.y) , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) , false) ;
			}
			else if( mouse_p_i.x > mouse_p_f.x && mouse_p_i.y < mouse_p_f.y)// | <-/ | 
			{
				graph.paint_heart( (Graphics2D)bufimg.getGraphics() , mouse_p_f.x , mouse_p_f.y-Math.abs(mouse_p_f.y - mouse_p_i.y) , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ,true ) ;
			}
			else// | \-> | 
			{
				graph.paint_heart( (Graphics2D)bufimg.getGraphics() , mouse_p_i.x , mouse_p_i.y , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ,true ) ;
			}
		
		}
		else if( tool_change == 6 )
		{
			graph.paint_line( (Graphics2D)bufimg.getGraphics() , mouse_p_i.x , mouse_p_i.y , mouse_p_f.x ,  mouse_p_f.y ) ;
		}
		
	}
	public void preview_draw( Graphics2D g2_pre){
		if( tool_change == 2 )//將不停更新的暫存值和結束值座標以畫上圖案，在刪去，以弄出預覽效果
		{					  //不同拖曳方向的 座標長寬值 有不同算法
			if( mouse_p_i.x > mouse_p_f.x && mouse_p_i.y > mouse_p_f.y)// | <-\ | 
			{
				graph.clear_circle( g2_pre , mouse_p_t.x , mouse_p_t.y , Math.abs(mouse_p_t.x - mouse_p_i.x) ,  Math.abs(mouse_p_t.y - mouse_p_i.y) ) ;
				graph.paint_circle( g2_pre , mouse_p_f.x , mouse_p_f.y , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ) ;

			}
			else if( mouse_p_i.x < mouse_p_f.x && mouse_p_i.y > mouse_p_f.y)// | /-> | 
			{
				graph.clear_circle( g2_pre , mouse_p_i.x , mouse_p_i.y-Math.abs(mouse_p_t.y - mouse_p_i.y) , Math.abs(mouse_p_t.x - mouse_p_i.x) ,  Math.abs(mouse_p_t.y - mouse_p_i.y) ) ;
				graph.paint_circle( g2_pre , mouse_p_i.x , mouse_p_i.y-Math.abs(mouse_p_f.y - mouse_p_i.y) , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ) ;

			}
			else if( mouse_p_i.x > mouse_p_f.x && mouse_p_i.y < mouse_p_f.y)// | <-/ | 
			{
				graph.clear_circle( g2_pre , mouse_p_t.x , mouse_p_t.y-Math.abs(mouse_p_t.y - mouse_p_i.y) , Math.abs(mouse_p_t.x - mouse_p_i.x) ,  Math.abs(mouse_p_t.y - mouse_p_i.y) ) ;
				graph.paint_circle( g2_pre , mouse_p_f.x , mouse_p_f.y-Math.abs(mouse_p_f.y - mouse_p_i.y) , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ) ;

			}
			else// | \-> | 
			{
				graph.clear_circle( g2_pre , mouse_p_i.x , mouse_p_i.y , Math.abs(mouse_p_t.x - mouse_p_i.x) ,  Math.abs(mouse_p_t.y - mouse_p_i.y) ) ;
				graph.paint_circle( g2_pre , mouse_p_i.x , mouse_p_i.y , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ) ;

			}
		}
		else if( tool_change == 3 )
		{
			if( mouse_p_i.x > mouse_p_f.x && mouse_p_i.y > mouse_p_f.y)// | <-\ | 
			{
				graph.clear_rect( g2_pre , mouse_p_t.x , mouse_p_t.y , Math.abs(mouse_p_t.x - mouse_p_i.x) ,  Math.abs(mouse_p_t.y - mouse_p_i.y) ) ;
				graph.paint_rect( g2_pre , mouse_p_f.x , mouse_p_f.y , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ) ;

			}
			else if( mouse_p_i.x < mouse_p_f.x && mouse_p_i.y > mouse_p_f.y)// | /-> | 
			{
				graph.clear_rect( g2_pre , mouse_p_i.x , mouse_p_i.y-Math.abs(mouse_p_t.y - mouse_p_i.y) , Math.abs(mouse_p_t.x - mouse_p_i.x) ,  Math.abs(mouse_p_t.y - mouse_p_i.y) ) ;
				graph.paint_rect( g2_pre , mouse_p_i.x , mouse_p_i.y-Math.abs(mouse_p_f.y - mouse_p_i.y) , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ) ;

			}
			else if( mouse_p_i.x > mouse_p_f.x && mouse_p_i.y < mouse_p_f.y)// | <-/ | 
			{
				graph.clear_rect( g2_pre , mouse_p_t.x , mouse_p_t.y-Math.abs(mouse_p_t.y - mouse_p_i.y) , Math.abs(mouse_p_t.x - mouse_p_i.x) ,  Math.abs(mouse_p_t.y - mouse_p_i.y) ) ;
				graph.paint_rect( g2_pre , mouse_p_f.x , mouse_p_f.y-Math.abs(mouse_p_f.y - mouse_p_i.y) , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ) ;

			}
			else// | \-> | 
			{
				graph.clear_rect( g2_pre , mouse_p_i.x , mouse_p_i.y , Math.abs(mouse_p_t.x - mouse_p_i.x) ,  Math.abs(mouse_p_t.y - mouse_p_i.y) ) ;
				graph.paint_rect( g2_pre , mouse_p_i.x , mouse_p_i.y , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ) ;

			}
		}
		else if( tool_change == 4 )
		{
			if( mouse_p_i.x > mouse_p_f.x && mouse_p_i.y > mouse_p_f.y)// | <-\ | 
			{
				graph.clear_tri( g2_pre , mouse_p_t.x , mouse_p_t.y , Math.abs(mouse_p_t.x - mouse_p_i.x) ,  Math.abs(mouse_p_t.y - mouse_p_i.y) , false ) ;
				graph.paint_tri( g2_pre , mouse_p_f.x , mouse_p_f.y , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) , false ) ;

			}
			else if( mouse_p_i.x < mouse_p_f.x && mouse_p_i.y > mouse_p_f.y)// | /-> | 
			{
				graph.clear_tri( g2_pre , mouse_p_i.x , mouse_p_i.y-Math.abs(mouse_p_t.y - mouse_p_i.y) , Math.abs(mouse_p_t.x - mouse_p_i.x) ,  Math.abs(mouse_p_t.y - mouse_p_i.y) , false) ;
				graph.paint_tri( g2_pre , mouse_p_i.x , mouse_p_i.y-Math.abs(mouse_p_f.y - mouse_p_i.y) , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) , false) ;

			}
			else if( mouse_p_i.x > mouse_p_f.x && mouse_p_i.y < mouse_p_f.y)// | <-/ | 
			{
				graph.clear_tri( g2_pre , mouse_p_t.x , mouse_p_t.y-Math.abs(mouse_p_t.y - mouse_p_i.y) , Math.abs(mouse_p_t.x - mouse_p_i.x) ,  Math.abs(mouse_p_t.y - mouse_p_i.y) ,true ) ;
				graph.paint_tri( g2_pre , mouse_p_f.x , mouse_p_f.y-Math.abs(mouse_p_f.y - mouse_p_i.y) , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ,true  ) ;

			}
			else// | \-> | 
			{
				graph.clear_tri( g2_pre , mouse_p_i.x , mouse_p_i.y , Math.abs(mouse_p_t.x - mouse_p_i.x) ,  Math.abs(mouse_p_t.y - mouse_p_i.y) ,true ) ;
				graph.paint_tri( g2_pre , mouse_p_i.x , mouse_p_i.y , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ,true ) ;

			}
		}
		else if( tool_change == 5 )
		{
			if( mouse_p_i.x > mouse_p_f.x && mouse_p_i.y > mouse_p_f.y)// | <-\ | 
			{
				graph.clear_heart( g2_pre , mouse_p_t.x , mouse_p_t.y , Math.abs(mouse_p_t.x - mouse_p_i.x) ,  Math.abs(mouse_p_t.y - mouse_p_i.y) , false ) ;
				graph.paint_heart( g2_pre , mouse_p_f.x , mouse_p_f.y , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) , false ) ;

			}
			else if( mouse_p_i.x < mouse_p_f.x && mouse_p_i.y > mouse_p_f.y)// | /-> | 
			{
				graph.clear_heart( g2_pre , mouse_p_i.x , mouse_p_i.y-Math.abs(mouse_p_t.y - mouse_p_i.y) , Math.abs(mouse_p_t.x - mouse_p_i.x) ,  Math.abs(mouse_p_t.y - mouse_p_i.y) , false) ;
				graph.paint_heart( g2_pre , mouse_p_i.x , mouse_p_i.y-Math.abs(mouse_p_f.y - mouse_p_i.y) , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) , false) ;
			}
			else if( mouse_p_i.x > mouse_p_f.x && mouse_p_i.y < mouse_p_f.y)// | <-/ | 
			{
				graph.clear_heart( g2_pre , mouse_p_t.x , mouse_p_t.y-Math.abs(mouse_p_t.y - mouse_p_i.y) , Math.abs(mouse_p_t.x - mouse_p_i.x) ,  Math.abs(mouse_p_t.y - mouse_p_i.y) ,true ) ;
				graph.paint_heart( g2_pre , mouse_p_f.x , mouse_p_f.y-Math.abs(mouse_p_f.y - mouse_p_i.y) , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ,true  ) ;
			}
			else// | \-> | 
			{
				graph.clear_heart( g2_pre , mouse_p_i.x , mouse_p_i.y , Math.abs(mouse_p_t.x - mouse_p_i.x) ,  Math.abs(mouse_p_t.y - mouse_p_i.y) ,true ) ;
				graph.paint_heart( g2_pre , mouse_p_i.x , mouse_p_i.y , Math.abs(mouse_p_f.x - mouse_p_i.x) ,  Math.abs(mouse_p_f.y - mouse_p_i.y) ,true ) ;
			}
		}
		else if( tool_change == 6 )
		{
			graph.clear_line( g2_pre , mouse_p_i.x , mouse_p_i.y , mouse_p_t.x ,  mouse_p_t.y  ) ;
			graph.paint_line( g2_pre , mouse_p_i.x , mouse_p_i.y , mouse_p_f.x ,  mouse_p_f.y ) ;
		}
	}
	
	//切換功能涵式
	public void set_tool( int change )
	{
		tool_change = change ;
	}
	
	//清除畫布涵式
	public void clear()
	{
		tool_change_pre = tool_change ;
		tool_change = -1 ;
		draw() ;
		tool_change = tool_change_pre ;
	}
	
	//畫圖涵式
	public void draw_staff( int change ) {
		tool_change_pre = tool_change ;
		tool_change = change ;
		draw() ;
		tool_change = tool_change_pre ;
	}
	//被取消的 復原功能
	/*public void undo() {
		tool_change_pre = tool_change ;
		tool_change = -3 ;
		draw() ;
		tool_change = tool_change_pre ;
	}*/
	
	//取得"裡畫布"涵式
	public BufferedImage get_buffered_image()
	{
		return bufimg ;
	}
	
	//設定"裡畫布"大小涵式
	public void set_buffered_image( BufferedImage change )
	{
		bufimg = change ;
	}
	
	//設定滑鼠座標涵式
	public void set_mouse_p_i_and_f( Point i , Point f )
	{
		mouse_p_i = i ;
		mouse_p_f = f ;
	}

}
