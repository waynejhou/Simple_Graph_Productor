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
	private int tool_change = -1 ;//�\�����
	private int tool_change_pre = -1 ;//�\����� 
	//-3���Q������ �_��\��
	//-2���e�Ϥ��\��
	//-1���M���\��
	//0	���S�ƥ\��
	//1	������\��
	//2	����Υ\��
	//3	���x�Υ\��
	//4	���T���Υ\��
	//5	���R�ߥ\��
	//6	�����u�\��
	private int mouse_x_i ;//�ƹ��y��x�b ��l��
	private int mouse_y_i ;//�ƹ��y��y�b ��l��
	private int mouse_x_f ;//�ƹ��y��x�b ������
	private int mouse_y_f ;//�ƹ��y��y�b ������
	private Point mouse_p_i = new Point() ;//�ƹ��y��  ��l��
	private Point mouse_p_f = new Point() ;//�ƹ��y�� ������
	private Point mouse_p_t = new Point() ;//�ƹ��y�� �Ȧs��
	private boolean i_and_f = false ;//���� ��l�M����
	private boolean first_dot = true ;//�O�_�Ĥ@�����U�ƹ�
	private boolean is_pre = false ;//�O�_�O�w����(��ɷƹ��ɡA��}�e���ϧ�)
	private Image img_cursor_pancil ;//�ƹ��C�йϮ�
	private Cursor pencil_cursor ;//�ƹ��C��
	Graph_Panel(){
		try {//Ū���ƹ��C�йϮ�
			img_cursor_pancil = ImageIO.read( new File( "pancle_cursor.png" ));
		} catch (IOException e) {
			 //TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//��l�]�w�ۭq���
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
			public void mousePressed(MouseEvent me) {//���U�h�� �����ƹ���l�Ȯy��
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
			public void mouseReleased(MouseEvent me) {//��}�� �����ƹ������Ȯy��
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
					draw();//��s�e��
					update(getGraphics());
					//System.out.println( "( " + mouse_p_f.x + " , " + mouse_p_f.y + " )");
				}
			}
		});
		//�ƹ��즲�ƥ�
		addMouseMotionListener( new MouseMotionListener(){
			@Override
			public void mouseDragged(MouseEvent mme) {//����즲�ɡA�|���_�椬���o�ƹ��y�е���l�ȩM������
				// TODO Auto-generated method stub	  //�C�����o�����Ȯ�ø��s�e���@��
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
				{//����즲�ɡA�|���_�椬���o�ƹ��y�е������ȩM�Ȧs��
				 //�C�����o�����Ȯ�ø��s�e���@��
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
			public void mouseMoved(MouseEvent mme) {//��Цb�̵e���ɤ~����
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
		g.drawImage( bufimg , 5 , 5 , this ) ;//��̵e���e�b��e��
		if( is_pre == true )//�e�w���Ϧb��e��
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
		else if( tool_change == 2 )//�̫�N��l�ȩM�����Ȯy�еe�Ϯצb�̵e���W
		{						   //���P�즲��V�� �y�Ъ��e�� �����P�����e�p��k
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
		if( tool_change == 2 )//�N������s���Ȧs�ȩM�����Ȯy�ХH�e�W�ϮסA�b�R�h�A�H�˥X�w���ĪG
		{					  //���P�즲��V�� �y�Ъ��e�� �����P��k
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
	
	//�����\��[��
	public void set_tool( int change )
	{
		tool_change = change ;
	}
	
	//�M���e���[��
	public void clear()
	{
		tool_change_pre = tool_change ;
		tool_change = -1 ;
		draw() ;
		tool_change = tool_change_pre ;
	}
	
	//�e�ϲ[��
	public void draw_staff( int change ) {
		tool_change_pre = tool_change ;
		tool_change = change ;
		draw() ;
		tool_change = tool_change_pre ;
	}
	//�Q������ �_��\��
	/*public void undo() {
		tool_change_pre = tool_change ;
		tool_change = -3 ;
		draw() ;
		tool_change = tool_change_pre ;
	}*/
	
	//���o"�̵e��"�[��
	public BufferedImage get_buffered_image()
	{
		return bufimg ;
	}
	
	//�]�w"�̵e��"�j�p�[��
	public void set_buffered_image( BufferedImage change )
	{
		bufimg = change ;
	}
	
	//�]�w�ƹ��y�в[��
	public void set_mouse_p_i_and_f( Point i , Point f )
	{
		mouse_p_i = i ;
		mouse_p_f = f ;
	}

}
