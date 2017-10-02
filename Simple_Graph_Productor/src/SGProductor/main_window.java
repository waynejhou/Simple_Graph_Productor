package SGProductor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;



public class main_window extends JFrame{//�D����GUI
	private static main_window window ;//�ŧi�D����
	//�ŧi�W��ﶵ�C
	private JMenuBar main_menu = new JMenuBar() ; 
	private JMenu menu_file = new JMenu("�ɮ�") ;
	private JMenuItem menu_open = new JMenuItem("�}���ɮ�") ;
	private JMenuItem menu_save = new JMenuItem("�x�s�ɮ�") ;
	private JMenuItem menu_setsize = new JMenuItem( "�վ�j�p" ) ;
	
	//�ŧi���O
	private JPanel panel_all = new JPanel() ;//�`���O
	private Graph_Panel paint_panel = new Graph_Panel() ;//�e�ϭ��O
	private JPanel panel_button_west = new JPanel() ;//������s���O
	private JPanel panel_button_east = new JPanel() ;//�k����s���O
	private JPanel panel_button_south = new JPanel() ;//�U����s���O
	
	//�ŧi���s
	private JButton button_pencil = new JButton( "����" ) ;
	private JButton button_straight = new JButton( "���u" ) ;
	private JButton button_circle = new JButton( "���" ) ;
	private JButton button_color = new JButton( "�C��" ) ;
	private JButton button_bg_color = new JButton( "�I��" ) ;
	private JButton button_clear = new JButton( "�M��" ) ;
	private JButton button_rect = new JButton( "�x��" ) ;
	private JButton button_tri = new JButton( "�T����" ) ;
	private JButton button_heart = new JButton( "�R��" ) ;
	private JButton button_fill = new JButton( "��" ) ;
	//private JButton button_pre = new JButton( "�W�@�B" ) ;
	//private JButton button_for = new JButton( "�U�@�B" ) ;
	
	private JSlider slider_line_size = new JSlider( 1 , 50 , 5 ) ;//�ŧi�즲��
	
	private JTextField textfield_line_size = new JTextField( slider_line_size.getValue()+"�@�@�@�@") ;//�u���ʲӭ�
	private JTextField textfield_color= new JTextField( ""  ) ;//�Ϯ��C��
	private JTextField textfield_bg_color= new JTextField( "" ) ;//�I���C��
	
	private color_window window_color ;//�վ��C��ε���
	private color_window window_bg_color ;//�վ�I���C��ε���
	private resize_window window_resize = new resize_window() ;//�վ�j�p�ε���
	
	private JFileChooser choose_file = new JFileChooser() ;//����ɮ׵���
	
	private console_thread console = new console_thread() ;//�D���x�����

	main_window(){//�غc�l
		
		//�x�s�Ϥ��\��
		menu_save.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stu
				save() ;
			}
		});
		
		//�}�ҹϤ��\��
		menu_open.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				open() ;
			}
		});
		//�վ�e���j�p�\��
		menu_setsize.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				window_resize.setLocation( getLocation() );
				window_resize.setVisible(true);
			}
		});
		
		//�Q������ �_��\��
		/*button_pre.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				paint_panel.undo();
			}
		});*/
		
		//�����񺡥\��
		button_fill.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				paint_panel.graph.toggle_fill();
				if( Graph_Object.filled )
				{
					button_fill.setText( "�L��" );
				}
				else
				{
					button_fill.setText( "��" );
				}
			}
		});
		
		//�e�ϥ\���
		//{
		//�Ҧ����s����;
		//�����\��;
		//���s��w;
		//}
		button_pencil.addActionListener( new ActionListener(){//����
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				free_all_button() ;
				paint_panel.set_tool( 1 );
				button_pencil.setEnabled( false );
			}
		});
		button_circle.addActionListener( new ActionListener(){//���
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				free_all_button() ;
				paint_panel.set_tool( 2 );
				button_circle.setEnabled( false );
			}
		});
		button_rect.addActionListener( new ActionListener(){//�x��
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				free_all_button() ;
				paint_panel.set_tool( 3 );
				button_rect.setEnabled( false );
			}
		});
		button_tri.addActionListener( new ActionListener(){//�T����
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				free_all_button() ;
				paint_panel.set_tool( 4 );
				button_tri.setEnabled( false );
			}
		});
		button_heart.addActionListener( new ActionListener(){//�R��
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				free_all_button() ;
				paint_panel.set_tool( 5 );
				button_heart.setEnabled( false );
			}
		});
		button_straight.addActionListener( new ActionListener(){//���u
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				free_all_button() ;
				paint_panel.set_tool( 6 );
				button_straight.setEnabled( false );
			}
		});
		
		//�M���\��
		button_clear.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				paint_panel.clear();
			}
		});
		
		//�C����s
		button_color.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				window_color.setLocation( button_color.getLocationOnScreen() );
				window_color.setVisible( true );
			}
		});
		button_bg_color.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				window_bg_color.setLocation( button_bg_color.getLocationOnScreen() );
				window_bg_color.setVisible( true );
			}
		});
		
		//�վ�u���ʲө즲���\��
		slider_line_size.addChangeListener( new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent ce) {
				// TODO Auto-generated method stub
				paint_panel.graph.set_line_size(slider_line_size.getValue()) ;
				textfield_line_size.setText( slider_line_size.getValue() + "" );
			}
		});
		
		//�վ��C��ε��� ��l��
		window_color = new color_window( paint_panel.graph , textfield_color , 1 ) ;
		window_bg_color = new color_window( paint_panel.graph , textfield_bg_color , 0 ) ;
		
		//��r����l�]�w �T��s��
		textfield_line_size.setEditable( false );
		textfield_color.setEditable( false );
		textfield_bg_color.setEditable( false );
		
		//�]�w��l�C��
		textfield_color.setBackground( Color.black );//��l�C�⬰��
		textfield_line_size.setBackground( Color.white );//��l�u���ʲӭȭI������
		textfield_bg_color.setBackground( Color.white );//��l�I������
		
		//�ɮ׹L�o
		choose_file.setFileFilter(new FileNameExtensionFilter("Image(*.jpg ; *.jpeg ; *.png)", "jpg", "jpeg","png" ));
		
		//���O�ƪ�
		panel_button_west.setLayout( new BoxLayout(panel_button_west, BoxLayout.Y_AXIS) );
		panel_button_west.setBorder( BorderFactory.createBevelBorder(BevelBorder.LOWERED) );
		panel_button_west.add(button_pencil);
		panel_button_west.add(button_straight);
		panel_button_west.add(button_circle);
		panel_button_west.add(button_clear);
		panel_button_west.add(button_rect);
		panel_button_west.add(button_tri);
		panel_button_west.add(button_heart);
		panel_button_east.setBorder( BorderFactory.createBevelBorder(BevelBorder.LOWERED) );
		panel_button_east.add(button_color) ;
		panel_button_east.add(textfield_color) ;
		panel_button_east.add(button_bg_color) ;
		panel_button_east.add(textfield_bg_color) ;
		panel_button_east.setLayout( new BoxLayout(panel_button_east, BoxLayout.Y_AXIS) );
		panel_button_south.setBorder( BorderFactory.createBevelBorder(BevelBorder.LOWERED) );
		panel_button_south.add(button_fill) ;
		panel_button_south.add(button_clear) ;
		panel_button_south.add(slider_line_size);
		panel_button_south.add(textfield_line_size);
		//panel_button_south.add(button_pre) ;
		paint_panel.setBorder( BorderFactory.createBevelBorder(BevelBorder.LOWERED) );
		panel_all.setLayout( new BorderLayout() );
		panel_all.add(paint_panel,BorderLayout.CENTER) ;
		panel_all.add(panel_button_west,BorderLayout.WEST);
		panel_all.add(panel_button_east,BorderLayout.EAST) ;
		panel_all.add(panel_button_south,BorderLayout.SOUTH) ;
		
		//�ﶵ�C�ƪ�
		menu_file.add(menu_open);
		menu_file.add(menu_save);
		menu_file.add(menu_setsize);
		main_menu.add(menu_file) ;
		
		//�D�����]�w
		this.add(panel_all);
		this.setJMenuBar( main_menu );
		this.setTitle( "Simple Graph Productor" ) ;
		this.setSize( 1280 , 720 );
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);//���}�ʧ@
		this.setVisible( true );
		paint_panel.draw();//��s�e��
    	console.start() ;//�ҰʥD���x
		
	}

	public static void main(String[] args) {//�{���i�J�I
		// TODO Auto-generated method stub
		window = new main_window() ;//�D������l��
	}
	private void free_all_button(){//�Ѱ���w�����s
		button_heart.setEnabled( true ) ;
		button_pencil.setEnabled( true );
		button_circle.setEnabled( true );
		button_clear.setEnabled( true );
		button_rect.setEnabled( true );
		button_tri.setEnabled( true );
		button_straight.setEnabled( true );
	}
	private void save(){//�x�s
		int returnVal = choose_file.showSaveDialog( menu_save  ); //�}��"�x�s"����
        if (returnVal == JFileChooser.APPROVE_OPTION) {//�������������s�p�G�O"�x�s"
        	try {//�s�J�ҿ�ܪ���Ƨ�
	            ImageIO.write(paint_panel.get_buffered_image() , "png", new File(choose_file.getSelectedFile()+".png" ));
	        } catch (Exception e) {
	            //e.printStackTrace();
	        }
            //This is where a real application would open the file.
        } else {
        }
	}
	private void open(){//�}��
		int returnVal = choose_file.showOpenDialog( menu_open );//�}��"�}��"����
        if (returnVal == JFileChooser.APPROVE_OPTION) {//�������������s�p�G�O"�}��"
        	
        	//Ū���ɮרåᵹ Graph_Object
        	paint_panel.graph.set_img(choose_file.getSelectedFile());
        	
        	//�վ�e���j�p
        	paint_panel.set_buffered_image(new BufferedImage( (int)paint_panel.graph.get_img_size().getWidth() , (int)paint_panel.graph.get_img_size().getHeight(), BufferedImage.TYPE_INT_BGR) );
        	
        	paint_panel.draw_staff(-2) ;//��s�e��
        } else {
        }
	}
	//�C�����
	class color_window extends JFrame{
		//�C��������ŧi
		private JPanel panel = new JPanel( ) ;
		private JColorChooser choose_color = new JColorChooser() ;
		private JButton button_close = new JButton( "����" ) ;
		private JButton button_lucky = new JButton( "�n���" ) ;
		private JButton button_cancel = new JButton( "����" ) ;
		
		private Color chosen_color ;//�Q�諸�C��
		
		color_window( Graph_Object graph , JTextField text , int order ){//�غc�l
			choose_color.setBorder(BorderFactory.createTitledBorder("�զ�L"));//�~�ظ˹�
			//����C��
			choose_color.getSelectionModel().addChangeListener( new ChangeListener(){
				@Override
				public void stateChanged(ChangeEvent e) {
					// TODO Auto-generated method stub
					if( order == 1 )
					{
						chosen_color = choose_color.getColor() ;
					}
					else if( order == 0 )
					{
						chosen_color = new Color( choose_color.getColor().getRed() , choose_color.getColor().getGreen() , choose_color.getColor().getBlue() ) ;

					}
				}
			});
			button_close.addActionListener( new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if( order == 0 )
					{
						graph.set_background_Color(chosen_color);
						text.setBackground( chosen_color );
						paint_panel.clear();
					}
					else if( order == 1 )
					{
						graph.set_Graph_Color(chosen_color);
						text.setBackground( chosen_color );
					}
					setVisible( false );
				}
			});
			button_lucky.addActionListener( new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					chosen_color = Random_color();
					if( order == 0 )
					{
						graph.set_background_Color(chosen_color);
						text.setBackground( chosen_color );
						paint_panel.clear();
					}
					else if( order == 1 )
					{
						graph.set_Graph_Color(chosen_color);
						text.setBackground( chosen_color );
					}
					setVisible( false );
				}
			});
			button_cancel.addActionListener( new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					setVisible( false );
				}
			});
			//���O�ƪ�
			panel.add( choose_color,BorderLayout.PAGE_START ) ;
			panel.add( button_close,BorderLayout.PAGE_END ) ;
			panel.add( button_lucky,BorderLayout.PAGE_END  ) ;
			panel.add( button_cancel,BorderLayout.PAGE_END ) ;
			//�����]�w
			this.setUndecorated( true );
			this.add(panel);
			this.setTitle( "Colors" );
			this.setSize( 600 , 400 );
			
		}
		private Color Random_color() {//�H���C��
			int random_R = (int)(Math.random()*254) ;
			int random_G = (int)(Math.random()*254) ;
			int random_B = (int)(Math.random()*254) ;
			return new Color( random_R , random_G , random_B );
		}

	}
	//�վ�j�p����
	class resize_window extends JFrame{
		private JPanel panel = new JPanel( ) ;
		private JLabel Label_width = new JLabel( "�e: " ) ;
		private JLabel Label_height = new JLabel( "��: " ) ;
		private JTextField TextF_width = new JTextField( "" ) ;
		private JTextField TextF_height = new JTextField( "" ) ;
		private JButton button_close = new JButton( "����" ) ;
		private JButton button_cancel = new JButton( "����" ) ;
		resize_window(){
			button_close.addActionListener( new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int w = paint_panel.get_buffered_image().getWidth() ;
					int h = paint_panel.get_buffered_image().getHeight() ;
					try{
						w = Integer.valueOf(TextF_width.getText()) ;
						h = Integer.valueOf(TextF_height.getText());
						paint_panel.set_buffered_image(new BufferedImage( w , h , BufferedImage.TYPE_INT_BGR) );
						
					}catch( Exception ex )
					{
						w = paint_panel.get_buffered_image().getWidth() ;
						h = paint_panel.get_buffered_image().getHeight();
						paint_panel.set_buffered_image(new BufferedImage( w , h , BufferedImage.TYPE_INT_BGR) );
					}
					paint_panel.clear();
					setVisible( false );
				}
			});
			button_cancel.addActionListener( new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					setVisible( false );
				}
			});
			panel.add( Label_width,BorderLayout.PAGE_START ) ;
			panel.add( TextF_width,BorderLayout.PAGE_START ) ;
			panel.add( Label_height,BorderLayout.PAGE_START ) ;
			panel.add( TextF_height,BorderLayout.PAGE_START ) ;
			panel.add( button_close,BorderLayout.PAGE_END ) ;
			panel.add( button_cancel,BorderLayout.PAGE_END ) ;
			TextF_width.setPreferredSize( new Dimension( 150 , 25 ));
			TextF_height.setPreferredSize( new Dimension( 150 , 25 ));
			this.setUndecorated( true );
			this.add( panel ) ;
			this.setTitle( "Resize" );
			this.setSize( 200 , 125 );
		}
		
	}
	//�D���x�����
	class console_thread extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Scanner input = new Scanner(System.in) ;
			System.out.println( "�o�̬O�D���x" );
			String command ;
			while( true )
			{
				System.out.println( "" );
				System.out.printf( ">>" );
				command = input.nextLine() ;//��J���O
				String[] command_split = command.split( " " , 2 ) ;//���Ϋ��O
				//�P�_���O
				try{
					if( command_split[0].equals( "clear" ) )
					{
						free_all_button() ;
						paint_panel.set_tool( -1 );
						paint_panel.update( paint_panel.getGraphics() );
					}
					else if( command_split[0].equals( "toggle_fill" ) )
					{
						paint_panel.graph.toggle_fill();
						if( Graph_Object.filled )
						{
							button_fill.setText( "�L��" );
						}
						else
						{
							button_fill.setText( "��" );
						}
					}
					else if( command_split[0].equals( "save" ) )
					{
						save() ;
					}
					else if( command_split[0].equals( "open" ) )
					{
						open() ;
					}
					else if( command_split[0].equals( "set_size" ) )
					{
						try{
							String[] command_size = command_split[1].split( " " , 2 ) ;
							paint_panel.set_buffered_image(new BufferedImage( Integer.valueOf(command_size[0]) , Integer.valueOf(command_size[1]) , BufferedImage.TYPE_INT_BGR) );
							paint_panel.clear();
						}
						catch( Exception ex )
						{
							System.out.println( "" );
							System.out.println( "���~�ƭ�" ) ;
							System.out.println( "��J help ���o���T�ƭȧΦ�" ) ;
						}
					}
					else if( command_split[0].equals( "set_line_size" ) )
					{
						paint_panel.graph.set_line_size(Integer.valueOf(command_split[1])) ;
						textfield_line_size.setText( Integer.valueOf(command_split[1]) + "" );
					}
					else if( command_split[0].equals( "set_stroke_cap" ) )
					{
						paint_panel.graph.set_stroke_cap( command_split[1] ) ;
					}
					else if( command_split[0].equals( "set_stroke_join" ) )
					{
						paint_panel.graph.set_stroke_join( command_split[1] ) ;
					}
					else if( command_split[0].equals( "set_color" ) )
					{
						paint_panel.graph.set_Graph_Color( command_split[1] , textfield_color ) ;
						paint_panel.update( paint_panel.getGraphics() );
					}
					else if( command_split[0].equals( "set_colorRGB" ) )
					{
						try{
							String[] command_color = command_split[1].split( " " , 3 ) ;
							paint_panel.graph.set_Graph_Color( new Color( Integer.valueOf(command_color[0]) , Integer.valueOf(command_color[1]) , Integer.valueOf(command_color[2]) ));
							textfield_color.setBackground(new Color( Integer.valueOf(command_color[0]) , Integer.valueOf(command_color[1]) , Integer.valueOf(command_color[2]) ));
						}
						catch( Exception ex )
						{
							System.out.println( "" );
							System.out.println( "���~�ƭ�" ) ;
							System.out.println( "��J help ���o���T�ƭȧΦ�" ) ;
						}
					}
					else if( command_split[0].equals( "set_colorRGBA" ) )
					{
						try{
							String[] command_color = command_split[1].split( " " , 4 ) ;
							paint_panel.graph.set_Graph_Color( new Color( Integer.valueOf(command_color[0]) , Integer.valueOf(command_color[1]) , Integer.valueOf(command_color[2]) , Integer.valueOf(command_color[3] ) ));
							textfield_color.setBackground(new Color( Integer.valueOf(command_color[0]) , Integer.valueOf(command_color[1]) , Integer.valueOf(command_color[2]) , Integer.valueOf(command_color[3] ) ));
						}
						catch( Exception ex )
						{
							System.out.println( "" );
							System.out.println( "���~�ƭ�" ) ;
							System.out.println( "��J help ���o���T�ƭȧΦ�" ) ;
						}
					}
					else if( command_split[0].equals( "set_bgcolor" ) )
					{
						paint_panel.graph.set_background_Color( command_split[1] , textfield_bg_color ) ;
						free_all_button() ;
						paint_panel.set_tool( -1 );
						paint_panel.update( paint_panel.getGraphics() );
	
					}
					else if( command_split[0].equals( "set_bgcolorRGB" ) )
					{
						try{
							String[] command_color = command_split[1].split( " " , 3 ) ;
							paint_panel.graph.set_background_Color( new Color( Integer.valueOf(command_color[0]) , Integer.valueOf(command_color[1]) , Integer.valueOf(command_color[2]) ));
							textfield_bg_color.setBackground(new Color( Integer.valueOf(command_color[0]) , Integer.valueOf(command_color[1]) , Integer.valueOf(command_color[2]) ));
						}
						catch( Exception ex )
						{
							System.out.println( "" );
							System.out.println( "���~�ƭ�" ) ;
							System.out.println( "��J help ���o���T�ƭȧΦ�" ) ;
						}
					}
					else if( command_split[0].equals( "set_bgcolorRGBA" ) )
					{
						try{
							String[] command_color = command_split[1].split( " " , 4 ) ;
							paint_panel.graph.set_background_Color( new Color( Integer.valueOf(command_color[0]) , Integer.valueOf(command_color[1]) , Integer.valueOf(command_color[2]) , Integer.valueOf(command_color[3] ) ));
							textfield_bg_color.setBackground(new Color( Integer.valueOf(command_color[0]) , Integer.valueOf(command_color[1]) , Integer.valueOf(command_color[2]) , Integer.valueOf(command_color[3] ) ));
						}
						catch( Exception ex )
						{
							System.out.println( "" );
							System.out.println( "���~�ƭ�" ) ;
							System.out.println( "��J help ���o���T�ƭȧΦ�" ) ;
						}
					}
					else if( command_split[0].equals( "paint_line" ) )
					{
						try{
							String[] command_point = command_split[1].split( " " , 4 ) ;
							paint_panel.set_mouse_p_i_and_f( new Point( Integer.valueOf(command_point[0]) , Integer.valueOf(command_point[1]) ) , new Point( Integer.valueOf(command_point[2]) , Integer.valueOf(command_point[3]) ) );
							paint_panel.draw_staff(6) ;
							paint_panel.update( paint_panel.getGraphics() );
						}
						catch( Exception ex )
						{
							System.out.println( "" );
							System.out.println( "���~�ƭ�" ) ;
							System.out.println( "��J help ���o���T�ƭȧΦ�" ) ;
						}
					}
					else if( command_split[0].equals( "paint_circle" ) )
					{
						try{
							String[] command_point = command_split[1].split( " " , 4 ) ;
							paint_panel.set_mouse_p_i_and_f( new Point( Integer.valueOf(command_point[0]) , Integer.valueOf(command_point[1]) ) , new Point( Integer.valueOf(command_point[2]) , Integer.valueOf(command_point[3]) ) );
							paint_panel.draw_staff(2) ;
							paint_panel.update( paint_panel.getGraphics() );
						}
						catch( Exception ex )
						{
							System.out.println( "" );
							System.out.println( "���~�ƭ�" ) ;
							System.out.println( "��J help ���o���T�ƭȧΦ�" ) ;
						}
					}
					else if( command_split[0].equals( "paint_rect" ) )
					{
						try{
							String[] command_point = command_split[1].split( " " , 4 ) ;
							paint_panel.set_mouse_p_i_and_f( new Point( Integer.valueOf(command_point[0]) , Integer.valueOf(command_point[1]) ) , new Point( Integer.valueOf(command_point[2]) , Integer.valueOf(command_point[3]) ) );
							paint_panel.draw_staff(3) ;
							paint_panel.update( paint_panel.getGraphics() );
						}
						catch( Exception ex )
						{
							System.out.println( "" );
							System.out.println( "���~�ƭ�" ) ;
							System.out.println( "��J help ���o���T�ƭȧΦ�" ) ;
						}
					}
					else if( command_split[0].equals( "paint_tri" ) )
					{
						try{
							String[] command_point = command_split[1].split( " " , 4 ) ;
							paint_panel.set_mouse_p_i_and_f( new Point( Integer.valueOf(command_point[0]) , Integer.valueOf(command_point[1]) ) , new Point( Integer.valueOf(command_point[2]) , Integer.valueOf(command_point[3]) ) );
							paint_panel.draw_staff(4) ;
							paint_panel.update( paint_panel.getGraphics() );
						}
						catch( Exception ex )
						{
							System.out.println( "" );
							System.out.println( "���~�ƭ�" ) ;
							System.out.println( "��J help ���o���T�ƭȧΦ�" ) ;
						}
					}
					else if( command_split[0].equals( "paint_heart" ) )
					{
						try{
							String[] command_point = command_split[1].split( " " , 4 ) ;
							paint_panel.set_mouse_p_i_and_f( new Point( Integer.valueOf(command_point[0]) , Integer.valueOf(command_point[1]) ) , new Point( Integer.valueOf(command_point[2]) , Integer.valueOf(command_point[3]) ) );
							paint_panel.draw_staff(5) ;
							paint_panel.update( paint_panel.getGraphics() );
						}
						catch( Exception ex )
						{
							System.out.println( "" );
							System.out.println( "���~�ƭ�" ) ;
							System.out.println( "��J help ���o���T�ƭȧΦ�" ) ;
						}
					}
					else if( command_split[0].equals( "get_info" ) )
					{
						System.out.println( "�e���j�p�G\t"+ paint_panel.get_buffered_image().getWidth()+"*"+paint_panel.get_buffered_image().getHeight() ) ;
						System.out.println( "�u���ʲӡG\t"+ paint_panel.graph.get_line_size() ) ;
						System.out.println( "�C��G\t\t"+ paint_panel.graph.get_color() ) ;
						System.out.println( "�I���C��G\t"+ paint_panel.graph.get_bgcolor() ) ;
						System.out.println( "�����G\t\t"+ paint_panel.graph.get_stroke() ) ;
						System.out.println( "�񺡡G\t\t"+ paint_panel.graph.get_filled() ) ;
					}
					else if( command_split[0].equals( "quit" ) )
					{
						System.out.println( "Bye Bye!" );
						System.exit( 0 );
					}
					else if ( command_split[0].equals( "help" ) )
					{
						System.out.println( "" );
						System.out.println( "���O�p�U: " ) ;
						System.out.println( "\tclear"+"\t\t\t\t\t�M���@��" ) ;
						System.out.println( "\ttoggle_fill"+"\t\t\t\t������" ) ;
						System.out.println( "\tsave"+"\t\t\t\t\t�}���x�s�ɮ׵���" ) ;
						System.out.println( "\topen"+"\t\t\t\t\t�}�Ҷ}���ɮ׵���" ) ;
						System.out.println( "\tset_size [width] [height]"+"\t\t�վ�j�p" ) ;
						System.out.println( "\tset_line_size [num]"+"\t\t\t�վ�u���ʲ�" ) ;
						System.out.println( "\tset_stroke_cap [�u�����˹��W]"+"\t\t�վ�u�����˹�" ) ;
						System.out.println( "\t\t�u�����˹��W: butt , round , square" ) ;
						System.out.println( "\tset_stroke_join [�u�����X�˹��W]"+"\t�վ�u�����X�˹�" ) ;
						System.out.println( "\t\t�u�����X�˹��W: bevel , miter , round" ) ;
						System.out.println( "\tset_color [�C��W]"+"\t\t\t�վ��C��" ) ;
						System.out.println( "\t\t�C��W: Random , white , black , blue , cyan" ) ;
						System.out.println( "\t\t\t     darkGray , gray , green , lightGray , magenta" ) ;
						System.out.println( "\t\t\t     orange , pink , red , yellow. " ) ;
						System.out.println( "\tset_color_RGB [num] [num] [num]"+"\t\t�վ��C���" ) ;
						System.out.println( "\tset_color_RGBA [num] [num] [num] [num]"+"\t�վ��C��z����" ) ;
						System.out.println( "\t\t�C��ƭȽd�򬰥��w�� 0~255 " ) ;
						System.out.println( "\tset_bgcolor [�C��W]"+"\t\t\t�վ�I���C��" ) ;
						System.out.println( "\t\t�C��W: Random , white , black , blue , cyan" ) ;
						System.out.println( "\t\t\t     darkGray , gray , green , lightGray , magenta" ) ;
						System.out.println( "\t\t\t     orange , pink , red , yellow. " ) ;
						System.out.println( "\tset_bgcolor_RGB [num] [num] [num]"+"\t�վ�I���C���" ) ;
						System.out.println( "\tset_bgcolor_RGBA [num] [num] [num] [num]"+"�վ�I���C��z����" ) ;
						System.out.println( "\t\t�C��ƭȽd�򬰥��w�� 0~255 " ) ;
						System.out.println( "\tpaint_line [x0] [y0] [x1] [y1]"+"\t\t�e���u�q(x0,y0)��(x1,y1)" ) ;
						System.out.println( "\tpaint_circle [x0] [y0] [x1] [y1]"+"\t�e��αq(x0,y0)��(x1,y1)" ) ;
						System.out.println( "\tpaint_rect [x0] [y0] [x1] [y1]"+"\t\t�e�x�αq(x0,y0)��(x1,y1)" ) ;
						System.out.println( "\tpaint_tri [x0] [y0] [x1] [y1]"+"\t\t�e�T���αq(x0,y0)��(x1,y1)" ) ;
						System.out.println( "\tpaint_heart [x0] [y0] [x1] [y1]"+"\t\t�e�R�߱q(x0,y0)��(x1,y1)" ) ;
						System.out.println( "\tget_info"+"\t\t\t\t���o�{�b�]�w��" ) ;
						System.out.println( "\tquit"+"\t\t\t\t\t�����{��" ) ;
						System.out.println( "\thelp"+"\t\t\t\t\t�����O" ) ;
						System.out.println( "ĵ�i! �Ҧ��ƭȤ��o�O�B�I��" ) ;
					}
					else
					{
						System.out.println( "" );
						System.out.println( "���O���~" ) ;
						System.out.println( "��J help ���o���T���O" ) ;
					}
				}
				catch( Exception ex )
				{
					System.out.println( "" );
					System.out.println( "���O���~" ) ;
					System.out.println( "��J help ���o���T���O" ) ;
				}
			}
		}

	}
}