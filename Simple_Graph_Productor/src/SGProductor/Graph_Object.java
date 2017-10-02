package SGProductor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JTextField;


public class Graph_Object {//畫圖圖案涵式合集
	
	private int line_size = 5;//線條粗細值
	private Color Graph_Color = Color.black ;//顏色
	private Color background_color = Color.white ;//背景顏色
	
	//筆畫樣式( 線條粗細 , 線條末裝飾  , 線條接合裝飾 )
	private BasicStroke BStroke = new BasicStroke(line_size , BasicStroke.CAP_ROUND , BasicStroke.JOIN_MITER ) ;
	
	//是否填滿
	public static boolean filled = false ; 
	
	//要開啟的圖片
	private Image img  ;
	
	//切換填滿開關
	public void toggle_fill(){
		if( filled == false )
			filled = true;
		else if( filled == true )
			filled = false ;
		//System.out.println( filled ) ;
	}
	
	//取得線條粗細值
	public int get_line_size()
	{
		return line_size ;
	}
	
	//取得顏色值
	public String get_color()
	{
		return "[R: "+Graph_Color.getRed()+"] , [G: "+Graph_Color.getGreen()+"] , [B: "+Graph_Color.getBlue() +"], [Alpha: "+Graph_Color.getAlpha()+"]";
	}
	
	//取得背景顏色值
	public String get_bgcolor()
	{
		return "[R: "+background_color.getRed()+"] , [G: "+background_color.getGreen()+"] , [B: "+background_color.getBlue() +"], [Alpha: "+background_color.getAlpha()+"]";
	}
	
	//取得筆畫樣式值
	public String get_stroke(){
		String temp_cap = "";
		String temp_join = "";
		if( BStroke.getEndCap() == BStroke.CAP_BUTT)
		{
			temp_cap = "CAP_BUTT" ;
		}
		else if( BStroke.getEndCap() == BStroke.CAP_ROUND)
		{
			temp_cap = "CAP_ROUND" ;
		}
		else if( BStroke.getEndCap() == BStroke.CAP_SQUARE)
		{
			temp_cap = "CAP_SQUARE" ;
		}
		if( BStroke.getLineJoin() == BStroke.JOIN_BEVEL)
		{
			temp_join = "JOIN_BEVEL" ;
		}
		else if( BStroke.getLineJoin() == BStroke.JOIN_MITER)
		{
			temp_join = "JOIN_MITE" ;
		}
		else if( BStroke.getLineJoin() == BStroke.JOIN_ROUND)
		{
			temp_join = "JOIN_ROUND" ;
		}
		return "[cap: "+temp_cap+"]"+"[join: "+temp_join+"]" ;
	}
	
	//取得是否填滿
	public boolean get_filled(){
		return filled ;
	}
	
	//設定線條粗細值
	public void set_line_size( int change )
	{
		int temp_join = BStroke.getLineJoin() ;
		int temp_cap = BStroke.getEndCap() ;
		line_size = change ;
		BStroke = new BasicStroke(line_size , temp_cap , temp_join ) ;
	}
	
	//設定線條末裝飾值
	public void set_stroke_cap( String change )
	{
		int temp = BStroke.getLineJoin() ;
		if( change.equals( "butt" ) )
		{
			BStroke = new BasicStroke(line_size , BasicStroke.CAP_BUTT , temp ) ;
		}
		else if( change.equals( "round" ) )
		{
			BStroke = new BasicStroke(line_size , BasicStroke.CAP_ROUND , temp ) ;
		}
		else if( change.equals( "square" ) )
		{
			BStroke = new BasicStroke(line_size , BasicStroke.CAP_SQUARE  , temp ) ;
		}
	}
	
	//設定線條接合裝飾值
	public void set_stroke_join( String change )
	{
		int temp = BStroke.getEndCap() ;
		if( change.equals( "bevel" ) )
		{
			BStroke = new BasicStroke(line_size , temp , BasicStroke.JOIN_BEVEL ) ;
		}
		else if( change.equals( "miter" ) )
		{
			BStroke = new BasicStroke(line_size , temp , BasicStroke.JOIN_MITER ) ;
		}
		else if( change.equals( "round" ) )
		{
			BStroke = new BasicStroke(line_size , temp , BasicStroke.JOIN_ROUND ) ;
		}
	}
	
	//設定顏色(輸入數值)
	public void set_Graph_Color( Color change )
	{
		Graph_Color = change ;
	}
	
	//設定顏色(輸入顏色名)
	public void set_Graph_Color( String change , JTextField text )
	{
		if ( change.equals( "white" ) )
		{
			Graph_Color = Color.white ;
		}
		else if ( change.equals( "black" ) )
		{
			Graph_Color = Color.black ;
		}
		else if ( change.equals( "blue" ) )
		{
			Graph_Color = Color.blue ;
		}
		else if ( change.equals( "cyan" ) )
		{
			Graph_Color = Color.cyan;
		}
		else if ( change.equals( "darkGray" ) )
		{
			Graph_Color = Color.darkGray ;
		}
		else if ( change.equals( "gray" ) )
		{
			Graph_Color = Color.gray ;
		}
		else if ( change.equals( "green" ) )
		{
			Graph_Color = Color.green ;
		}
		else if ( change.equals( "lightGray" ) )
		{
			Graph_Color = Color.lightGray ;
		}
		else if ( change.equals( "magenta" ) )
		{
			Graph_Color = Color.magenta ;
		}
		else if ( change.equals( "orange" ) )
		{
			Graph_Color = Color.orange ;
		}
		else if ( change.equals( "pink" ) )
		{
			Graph_Color = Color.pink ;
		}
		else if ( change.equals( "red" ) )
		{
			Graph_Color = Color.red ;
		}
		else if ( change.equals( "yellow" ) )
		{
			Graph_Color = Color.yellow ;
		}
		text.setBackground(Graph_Color);
	}
	
	//設定背景顏色(輸入數值)
	public void set_background_Color( Color change )
	{
		background_color = change ;
	}
	
	//設定背景顏色(輸入顏色名)
	public void set_background_Color( String change , JTextField text )
	{
		if ( change.equals( "white" ) )
		{
			background_color = Color.white ;
		}
		else if ( change.equals( "black" ) )
		{
			background_color = Color.black ;
		}
		else if ( change.equals( "blue" ) )
		{
			background_color = Color.blue ;
		}
		else if ( change.equals( "cyan" ) )
		{
			background_color = Color.cyan;
		}
		else if ( change.equals( "darkGray" ) )
		{
			background_color = Color.darkGray ;
		}
		else if ( change.equals( "gray" ) )
		{
			background_color = Color.gray ;
		}
		else if ( change.equals( "green" ) )
		{
			background_color = Color.green ;
		}
		else if ( change.equals( "lightGray" ) )
		{
			background_color = Color.lightGray ;
		}
		else if ( change.equals( "magenta" ) )
		{
			background_color = Color.magenta ;
		}
		else if ( change.equals( "orange" ) )
		{
			background_color = Color.orange ;
		}
		else if ( change.equals( "pink" ) )
		{
			background_color = Color.pink ;
		}
		else if ( change.equals( "red" ) )
		{
			background_color = Color.red ;
		}
		else if ( change.equals( "yellow" ) )
		{
			background_color = Color.yellow ;
		}
		text.setBackground(background_color);
	}
	
	//載入圖片
	public void set_img( File file_img )
	{
		try {
			img = ImageIO.read( file_img ) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	//取得載入圖片的大小
	public Dimension get_img_size()
	{
		return new Dimension( img.getWidth(null) , img.getHeight(null) ) ;
	}
	
	//取得顏色
	public Color get_Graph_Color()
	{
		return Graph_Color ;
	}
	
	//畫圖片
	public void paint_img( Graphics2D g2 )
	{
		g2.drawImage(img, 0 , 0 , null) ;
	}
	
	//被取消的 復原功能
	public void paint_pre( Graphics2D g2 , Image img_pre )
	{
		g2.drawImage(img_pre , 0 , 0 , null) ;
	}
	
	//畫筆刷
	public void paint_pencil( Graphics2D g2 , int mouse_x_i , int mouse_y_i , int mouse_x_f , int mouse_y_f )
	{
		g2.setColor( Graph_Color );
		g2.setStroke( BStroke );
		g2.drawLine(mouse_x_i, mouse_y_i, mouse_x_f, mouse_y_f);
	}
	
	//清除
	public void paint_clear( Graphics g )
	{
		g.setColor( background_color);
		g.fillRect(0, 0, 9999, 9999);
	}
	
	//畫圓形
	public void paint_circle( Graphics2D g2 , int mouse_x , int mouse_y , int h , int w )
	{
		if( filled == false )
		{
			g2.setColor( Graph_Color );
			g2.setStroke( BStroke );
			g2.drawOval(mouse_x, mouse_y, h , w );
		}
		else if( filled == true )
		{
			g2.setColor( Graph_Color );
			g2.setStroke( BStroke );
			g2.fillOval(mouse_x, mouse_y, h , w );
		}
	}
	
	//清除圓形
	public void clear_circle( Graphics2D g2 , int mouse_x , int mouse_y , int h , int w )
	{
		if( filled == false )
		{
			g2.setColor( background_color );
			g2.setStroke( BStroke );
			g2.drawOval(mouse_x, mouse_y, h , w );
			//System.out.println( filled ) ;
		}
		else if( filled == true )
		{
			g2.setColor( background_color );
			g2.setStroke( BStroke );
			g2.fillOval(mouse_x, mouse_y, h , w );
		}
	}
	
	//畫矩形
	public void paint_rect( Graphics2D g2 , int mouse_x , int mouse_y , int h , int w )
	{
		if( filled == false )
		{
			g2.setColor( Graph_Color );
			g2.setStroke( BStroke  ) ;
			g2.drawRect(mouse_x, mouse_y, h , w );
		}
		else if( filled == true )
		{
			g2.setColor( Graph_Color );
			g2.setStroke( BStroke  ) ;
			g2.fillRect(mouse_x, mouse_y, h , w );
		}
	}
	
	//清除矩形
	public void clear_rect( Graphics2D g2 , int mouse_x , int mouse_y , int h , int w )
	{
		if( filled == false )
		{
			g2.setColor( background_color );
			g2.setStroke( BStroke  ) ;
			g2.drawRect(mouse_x, mouse_y, h , w );
		}
		else if( filled == true )
		{
			g2.setColor( background_color );
			g2.setStroke( BStroke  ) ;
			g2.fillRect(mouse_x, mouse_y, h , w );
		}
	}
	
	//畫三角形
	public void paint_tri( Graphics2D g2 , int mouse_x , int mouse_y , int h , int w , boolean is_upsidedown )
	{
		if( is_upsidedown == true )
		{
			int[] tri_x = { (mouse_x+h/2) , (mouse_x) , (mouse_x+h) } ;
			int[] tri_y = { (mouse_y) , (mouse_y+w) , (mouse_y+w) } ;
			if( filled == false )
			{
				g2.setColor( Graph_Color );
				g2.setStroke( BStroke  ) ;
				g2.drawPolygon( tri_x , tri_y , 3 );
			}
			else if( filled == true )
			{
				g2.setColor( Graph_Color );
				g2.setStroke( BStroke  ) ;
				g2.fillPolygon( tri_x , tri_y , 3 );
			}
		}
		else if( is_upsidedown == false )
		{
			int[] tri_x = { (mouse_x) , (mouse_x+h) , (mouse_x+h/2) } ;
			int[] tri_y = { (mouse_y) , (mouse_y) , (mouse_y+w) } ;
			if( filled == false )
			{
				g2.setColor( Graph_Color );
				g2.setStroke( BStroke  ) ;
				g2.drawPolygon( tri_x , tri_y , 3 );
			}
			else if( filled == true )
			{
				g2.setColor( Graph_Color );
				g2.setStroke( BStroke  ) ;
				g2.fillPolygon( tri_x , tri_y , 3 );
			}
		}
	}
	
	//清除三角形
	public void clear_tri( Graphics2D g2 , int mouse_x , int mouse_y , int h , int w , boolean is_upsidedown )
	{
		if( is_upsidedown == true )
		{
			int[] tri_x = { (mouse_x+h/2) , (mouse_x) , (mouse_x+h) } ;
			int[] tri_y = { (mouse_y) , (mouse_y+w) , (mouse_y+w) } ;
			if( filled == false )
			{
				g2.setColor( background_color );
				g2.setStroke( BStroke  ) ;
				g2.drawPolygon( tri_x , tri_y , 3 );
			}
			else if( filled == true )
			{
				g2.setColor( background_color );
				g2.setStroke( BStroke  ) ;
				g2.fillPolygon( tri_x , tri_y , 3 );
			}
		}
		else if( is_upsidedown == false )
		{
			int[] tri_x = { (mouse_x) , (mouse_x+h) , (mouse_x+h/2) } ;
			int[] tri_y = { (mouse_y) , (mouse_y) , (mouse_y+w) } ;
			if( filled == false )
			{
				g2.setColor( background_color );
				g2.setStroke( BStroke  ) ;
				g2.drawPolygon( tri_x , tri_y , 3 );
			}
			else if( filled == true )
			{
				g2.setColor( background_color );
				g2.setStroke( BStroke  ) ;
				g2.fillPolygon( tri_x , tri_y , 3 );
			}
		}
	}
	
	//畫愛心
	public void paint_heart( Graphics2D g2 , int mouse_x , int mouse_y , int h , int w , boolean is_upsidedown )
	{
		if( is_upsidedown == true )
		{
			if( filled == false )
			{
				g2.setColor( Graph_Color );
				g2.setStroke( BStroke  ) ;
				g2.drawArc(mouse_x, mouse_y , h/2 , w/2 , 0 ,  180 );
				g2.drawArc(mouse_x+h/2, mouse_y , h/2 , w/2 , 0 ,  180 );
				g2.drawArc(mouse_x , mouse_y-w/2, h+h/4 , w/2*3 , 180 ,  79 );
				g2.drawArc(mouse_x-h/4 , mouse_y-w/2 , h+h/4 , w/2*3 , 0 ,  -79 );
			}
			else if( filled == true )
			{
				g2.setColor( Graph_Color );
				g2.setStroke( BStroke  ) ;
				g2.fillArc(mouse_x, mouse_y , h/2 , w/2 , 0 ,  180 );
				g2.fillArc(mouse_x+h/2, mouse_y , h/2 , w/2 , 0 ,  180 );
				g2.fillArc(mouse_x , mouse_y-w/2, h+h/4 , w/2*3 , 180 ,  79 );
				g2.fillArc(mouse_x-h/4 , mouse_y-w/2 , h+h/4 , w/2*3 , 0 ,  -79 );
			}
		}
		else if( is_upsidedown == false )
		{
			if( filled == false )
			{
				g2.setColor( Graph_Color );
				g2.setStroke( BStroke  ) ;
				g2.drawArc(mouse_x , mouse_y+w/2 , h/2 , w/2 , 180 ,  180 );
				g2.drawArc(mouse_x+h/2, mouse_y+w/2 , h/2 , w/2 , 180 ,  180 );
				g2.drawArc(mouse_x , mouse_y , h+h/4 , w/2*3 , 180 ,  -79 );
				g2.drawArc(mouse_x-h/4 , mouse_y , h+h/4 , w/2*3 , 0 ,  79 );
			}
			else if( filled == true )
			{
				g2.setColor( Graph_Color );
				g2.setStroke( BStroke  ) ;
				g2.fillArc(mouse_x, mouse_y+w/2 , h/2 , w/2 , 180 ,  180 );
				g2.fillArc(mouse_x+h/2, mouse_y+w/2 , h/2 , w/2 , 180 ,  180 );
				g2.fillArc(mouse_x , mouse_y , h+h/4 , w/2*3 , 180 ,  -79 );
				g2.fillArc(mouse_x-h/4 , mouse_y , h+h/4 , w/2*3 , 0 ,  79 );
			}
		}
	}
	
	//清除愛心
	public void clear_heart( Graphics2D g2 , int mouse_x , int mouse_y , int h , int w , boolean is_upsidedown )
	{
		if( is_upsidedown == true )
		{
			if( filled == false )
			{
				g2.setColor( background_color );
				g2.setStroke( BStroke  ) ;
				g2.drawArc(mouse_x, mouse_y , h/2 , w/2 , 0 ,  180 );
				g2.drawArc(mouse_x+h/2, mouse_y , h/2 , w/2 , 0 ,  180 );
				g2.drawArc(mouse_x , mouse_y-w/2, h+h/4 , w/2*3 , 180 ,  79 );
				g2.drawArc(mouse_x-h/4 , mouse_y-w/2 , h+h/4 , w/2*3 , 0 ,  -79 );
			}
			else if( filled == true )
			{
				g2.setColor( background_color);
				g2.setStroke( BStroke  ) ;
				g2.fillArc(mouse_x, mouse_y , h/2 , w/2 , 0 ,  180 );
				g2.fillArc(mouse_x+h/2, mouse_y , h/2 , w/2 , 0 ,  180 );
				g2.fillArc(mouse_x , mouse_y-w/2, h+h/4 , w/2*3 , 180 ,  79 );
				g2.fillArc(mouse_x-h/4 , mouse_y-w/2 , h+h/4 , w/2*3 , 0 ,  -79 );
			}
		}
		else if( is_upsidedown == false )
		{
			if( filled == false )
			{
				g2.setColor( background_color );
				g2.setStroke( BStroke  ) ;
				g2.drawArc(mouse_x, mouse_y+w/2 , h/2 , w/2 , 180 ,  180 );
				g2.drawArc(mouse_x+h/2, mouse_y+w/2 , h/2 , w/2 , 180 ,  180 );
				g2.drawArc(mouse_x , mouse_y , h+h/4 , w/2*3 , 180 ,  -79 );
				g2.drawArc(mouse_x-h/4 , mouse_y , h+h/4 , w/2*3 , 0 ,  79 );
			}
			else if( filled == true )
			{
				g2.setColor( background_color );
				g2.setStroke( BStroke  ) ;
				g2.fillArc(mouse_x, mouse_y+w/2 , h/2 , w/2 , 180 ,  180 );
				g2.fillArc(mouse_x+h/2, mouse_y+w/2 , h/2 , w/2 , 180 ,  180 );
				g2.fillArc(mouse_x , mouse_y , h+h/4 , w/2*3 , 180 ,  -79 );
				g2.fillArc(mouse_x-h/4 , mouse_y , h+h/4 , w/2*3 , 0 ,  79 );
			}
		}
	}
	
	//畫直線
	public void paint_line( Graphics2D g2 , int mouse_x1 , int mouse_y1 , int mouse_x2 , int mouse_y2 )
	{
		g2.setColor( Graph_Color );
		g2.setStroke( BStroke  ) ;
		g2.drawLine(mouse_x1, mouse_y1, mouse_x2, mouse_y2) ;
	}
	
	//清除直線
	public void clear_line( Graphics2D g2 , int mouse_x1 , int mouse_y1 , int mouse_x2 , int mouse_y2 )
	{
		g2.setColor( background_color );
		g2.setStroke( BStroke  ) ;
		g2.drawLine(mouse_x1, mouse_y1, mouse_x2, mouse_y2) ;
	}

}
