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



public class main_window extends JFrame{//主視窗GUI
	private static main_window window ;//宣告主視窗
	//宣告上方選項列
	private JMenuBar main_menu = new JMenuBar() ; 
	private JMenu menu_file = new JMenu("檔案") ;
	private JMenuItem menu_open = new JMenuItem("開啟檔案") ;
	private JMenuItem menu_save = new JMenuItem("儲存檔案") ;
	private JMenuItem menu_setsize = new JMenuItem( "調整大小" ) ;
	
	//宣告面板
	private JPanel panel_all = new JPanel() ;//總面板
	private Graph_Panel paint_panel = new Graph_Panel() ;//畫圖面板
	private JPanel panel_button_west = new JPanel() ;//左方按鈕面板
	private JPanel panel_button_east = new JPanel() ;//右方按鈕面板
	private JPanel panel_button_south = new JPanel() ;//下方按鈕面板
	
	//宣告按鈕
	private JButton button_pencil = new JButton( "筆刷" ) ;
	private JButton button_straight = new JButton( "直線" ) ;
	private JButton button_circle = new JButton( "圓形" ) ;
	private JButton button_color = new JButton( "顏色" ) ;
	private JButton button_bg_color = new JButton( "背景" ) ;
	private JButton button_clear = new JButton( "清除" ) ;
	private JButton button_rect = new JButton( "矩形" ) ;
	private JButton button_tri = new JButton( "三角形" ) ;
	private JButton button_heart = new JButton( "愛心" ) ;
	private JButton button_fill = new JButton( "填滿" ) ;
	//private JButton button_pre = new JButton( "上一步" ) ;
	//private JButton button_for = new JButton( "下一步" ) ;
	
	private JSlider slider_line_size = new JSlider( 1 , 50 , 5 ) ;//宣告拖曳條
	
	private JTextField textfield_line_size = new JTextField( slider_line_size.getValue()+"　　　　") ;//線條粗細值
	private JTextField textfield_color= new JTextField( ""  ) ;//圖案顏色
	private JTextField textfield_bg_color= new JTextField( "" ) ;//背景顏色
	
	private color_window window_color ;//調整顏色用視窗
	private color_window window_bg_color ;//調整背景顏色用視窗
	private resize_window window_resize = new resize_window() ;//調整大小用視窗
	
	private JFileChooser choose_file = new JFileChooser() ;//選擇檔案視窗
	
	private console_thread console = new console_thread() ;//主控台執行緒

	main_window(){//建構子
		
		//儲存圖片功能
		menu_save.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stu
				save() ;
			}
		});
		
		//開啟圖片功能
		menu_open.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				open() ;
			}
		});
		//調整畫布大小功能
		menu_setsize.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				window_resize.setLocation( getLocation() );
				window_resize.setVisible(true);
			}
		});
		
		//被取消的 復原功能
		/*button_pre.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				paint_panel.undo();
			}
		});*/
		
		//切換填滿功能
		button_fill.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				paint_panel.graph.toggle_fill();
				if( Graph_Object.filled )
				{
					button_fill.setText( "無填滿" );
				}
				else
				{
					button_fill.setText( "填滿" );
				}
			}
		});
		
		//畫圖功能們
		//{
		//所有按鈕解鎖;
		//切換功能;
		//按鈕鎖定;
		//}
		button_pencil.addActionListener( new ActionListener(){//筆刷
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				free_all_button() ;
				paint_panel.set_tool( 1 );
				button_pencil.setEnabled( false );
			}
		});
		button_circle.addActionListener( new ActionListener(){//圓形
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				free_all_button() ;
				paint_panel.set_tool( 2 );
				button_circle.setEnabled( false );
			}
		});
		button_rect.addActionListener( new ActionListener(){//矩形
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				free_all_button() ;
				paint_panel.set_tool( 3 );
				button_rect.setEnabled( false );
			}
		});
		button_tri.addActionListener( new ActionListener(){//三角形
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				free_all_button() ;
				paint_panel.set_tool( 4 );
				button_tri.setEnabled( false );
			}
		});
		button_heart.addActionListener( new ActionListener(){//愛心
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				free_all_button() ;
				paint_panel.set_tool( 5 );
				button_heart.setEnabled( false );
			}
		});
		button_straight.addActionListener( new ActionListener(){//直線
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				free_all_button() ;
				paint_panel.set_tool( 6 );
				button_straight.setEnabled( false );
			}
		});
		
		//清除功能
		button_clear.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				paint_panel.clear();
			}
		});
		
		//顏色按鈕
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
		
		//調整線條粗細拖曳條功能
		slider_line_size.addChangeListener( new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent ce) {
				// TODO Auto-generated method stub
				paint_panel.graph.set_line_size(slider_line_size.getValue()) ;
				textfield_line_size.setText( slider_line_size.getValue() + "" );
			}
		});
		
		//調整顏色用視窗 初始化
		window_color = new color_window( paint_panel.graph , textfield_color , 1 ) ;
		window_bg_color = new color_window( paint_panel.graph , textfield_bg_color , 0 ) ;
		
		//文字條初始設定 禁止編輯
		textfield_line_size.setEditable( false );
		textfield_color.setEditable( false );
		textfield_bg_color.setEditable( false );
		
		//設定初始顏色
		textfield_color.setBackground( Color.black );//初始顏色為黑
		textfield_line_size.setBackground( Color.white );//初始線條粗細值背景為白
		textfield_bg_color.setBackground( Color.white );//初始背景為白
		
		//檔案過濾
		choose_file.setFileFilter(new FileNameExtensionFilter("Image(*.jpg ; *.jpeg ; *.png)", "jpg", "jpeg","png" ));
		
		//面板排版
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
		
		//選項列排版
		menu_file.add(menu_open);
		menu_file.add(menu_save);
		menu_file.add(menu_setsize);
		main_menu.add(menu_file) ;
		
		//主視窗設定
		this.add(panel_all);
		this.setJMenuBar( main_menu );
		this.setTitle( "Simple Graph Productor" ) ;
		this.setSize( 1280 , 720 );
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);//離開動作
		this.setVisible( true );
		paint_panel.draw();//刷新畫布
    	console.start() ;//啟動主控台
		
	}

	public static void main(String[] args) {//程式進入點
		// TODO Auto-generated method stub
		window = new main_window() ;//主視窗初始化
	}
	private void free_all_button(){//解除鎖定的按鈕
		button_heart.setEnabled( true ) ;
		button_pencil.setEnabled( true );
		button_circle.setEnabled( true );
		button_clear.setEnabled( true );
		button_rect.setEnabled( true );
		button_tri.setEnabled( true );
		button_straight.setEnabled( true );
	}
	private void save(){//儲存
		int returnVal = choose_file.showSaveDialog( menu_save  ); //開啟"儲存"視窗
        if (returnVal == JFileChooser.APPROVE_OPTION) {//關閉視窗的按鈕如果是"儲存"
        	try {//存入所選擇的資料夾
	            ImageIO.write(paint_panel.get_buffered_image() , "png", new File(choose_file.getSelectedFile()+".png" ));
	        } catch (Exception e) {
	            //e.printStackTrace();
	        }
            //This is where a real application would open the file.
        } else {
        }
	}
	private void open(){//開啟
		int returnVal = choose_file.showOpenDialog( menu_open );//開啟"開啟"視窗
        if (returnVal == JFileChooser.APPROVE_OPTION) {//關閉視窗的按鈕如果是"開啟"
        	
        	//讀取檔案並丟給 Graph_Object
        	paint_panel.graph.set_img(choose_file.getSelectedFile());
        	
        	//調整畫布大小
        	paint_panel.set_buffered_image(new BufferedImage( (int)paint_panel.graph.get_img_size().getWidth() , (int)paint_panel.graph.get_img_size().getHeight(), BufferedImage.TYPE_INT_BGR) );
        	
        	paint_panel.draw_staff(-2) ;//更新畫布
        } else {
        }
	}
	//顏色視窗
	class color_window extends JFrame{
		//顏色視窗原件宣告
		private JPanel panel = new JPanel( ) ;
		private JColorChooser choose_color = new JColorChooser() ;
		private JButton button_close = new JButton( "完成" ) ;
		private JButton button_lucky = new JButton( "好手氣" ) ;
		private JButton button_cancel = new JButton( "取消" ) ;
		
		private Color chosen_color ;//被選的顏色
		
		color_window( Graph_Object graph , JTextField text , int order ){//建構子
			choose_color.setBorder(BorderFactory.createTitledBorder("調色盤"));//外框裝飾
			//選擇顏色
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
			//面板排版
			panel.add( choose_color,BorderLayout.PAGE_START ) ;
			panel.add( button_close,BorderLayout.PAGE_END ) ;
			panel.add( button_lucky,BorderLayout.PAGE_END  ) ;
			panel.add( button_cancel,BorderLayout.PAGE_END ) ;
			//視窗設定
			this.setUndecorated( true );
			this.add(panel);
			this.setTitle( "Colors" );
			this.setSize( 600 , 400 );
			
		}
		private Color Random_color() {//隨機顏色
			int random_R = (int)(Math.random()*254) ;
			int random_G = (int)(Math.random()*254) ;
			int random_B = (int)(Math.random()*254) ;
			return new Color( random_R , random_G , random_B );
		}

	}
	//調整大小視窗
	class resize_window extends JFrame{
		private JPanel panel = new JPanel( ) ;
		private JLabel Label_width = new JLabel( "寬: " ) ;
		private JLabel Label_height = new JLabel( "高: " ) ;
		private JTextField TextF_width = new JTextField( "" ) ;
		private JTextField TextF_height = new JTextField( "" ) ;
		private JButton button_close = new JButton( "完成" ) ;
		private JButton button_cancel = new JButton( "取消" ) ;
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
	//主控台執行緒
	class console_thread extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Scanner input = new Scanner(System.in) ;
			System.out.println( "這裡是主控台" );
			String command ;
			while( true )
			{
				System.out.println( "" );
				System.out.printf( ">>" );
				command = input.nextLine() ;//輸入指令
				String[] command_split = command.split( " " , 2 ) ;//分割指令
				//判斷指令
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
							button_fill.setText( "無填滿" );
						}
						else
						{
							button_fill.setText( "填滿" );
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
							System.out.println( "錯誤數值" ) ;
							System.out.println( "輸入 help 取得正確數值形式" ) ;
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
							System.out.println( "錯誤數值" ) ;
							System.out.println( "輸入 help 取得正確數值形式" ) ;
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
							System.out.println( "錯誤數值" ) ;
							System.out.println( "輸入 help 取得正確數值形式" ) ;
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
							System.out.println( "錯誤數值" ) ;
							System.out.println( "輸入 help 取得正確數值形式" ) ;
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
							System.out.println( "錯誤數值" ) ;
							System.out.println( "輸入 help 取得正確數值形式" ) ;
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
							System.out.println( "錯誤數值" ) ;
							System.out.println( "輸入 help 取得正確數值形式" ) ;
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
							System.out.println( "錯誤數值" ) ;
							System.out.println( "輸入 help 取得正確數值形式" ) ;
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
							System.out.println( "錯誤數值" ) ;
							System.out.println( "輸入 help 取得正確數值形式" ) ;
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
							System.out.println( "錯誤數值" ) ;
							System.out.println( "輸入 help 取得正確數值形式" ) ;
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
							System.out.println( "錯誤數值" ) ;
							System.out.println( "輸入 help 取得正確數值形式" ) ;
						}
					}
					else if( command_split[0].equals( "get_info" ) )
					{
						System.out.println( "畫布大小：\t"+ paint_panel.get_buffered_image().getWidth()+"*"+paint_panel.get_buffered_image().getHeight() ) ;
						System.out.println( "線條粗細：\t"+ paint_panel.graph.get_line_size() ) ;
						System.out.println( "顏色：\t\t"+ paint_panel.graph.get_color() ) ;
						System.out.println( "背景顏色：\t"+ paint_panel.graph.get_bgcolor() ) ;
						System.out.println( "筆劃：\t\t"+ paint_panel.graph.get_stroke() ) ;
						System.out.println( "填滿：\t\t"+ paint_panel.graph.get_filled() ) ;
					}
					else if( command_split[0].equals( "quit" ) )
					{
						System.out.println( "Bye Bye!" );
						System.exit( 0 );
					}
					else if ( command_split[0].equals( "help" ) )
					{
						System.out.println( "" );
						System.out.println( "指令如下: " ) ;
						System.out.println( "\tclear"+"\t\t\t\t\t清除一切" ) ;
						System.out.println( "\ttoggle_fill"+"\t\t\t\t切換填滿" ) ;
						System.out.println( "\tsave"+"\t\t\t\t\t開啟儲存檔案視窗" ) ;
						System.out.println( "\topen"+"\t\t\t\t\t開啟開啟檔案視窗" ) ;
						System.out.println( "\tset_size [width] [height]"+"\t\t調整大小" ) ;
						System.out.println( "\tset_line_size [num]"+"\t\t\t調整線條粗細" ) ;
						System.out.println( "\tset_stroke_cap [線條末裝飾名]"+"\t\t調整線條末裝飾" ) ;
						System.out.println( "\t\t線條末裝飾名: butt , round , square" ) ;
						System.out.println( "\tset_stroke_join [線條接合裝飾名]"+"\t調整線條接合裝飾" ) ;
						System.out.println( "\t\t線條接合裝飾名: bevel , miter , round" ) ;
						System.out.println( "\tset_color [顏色名]"+"\t\t\t調整顏色" ) ;
						System.out.println( "\t\t顏色名: Random , white , black , blue , cyan" ) ;
						System.out.println( "\t\t\t     darkGray , gray , green , lightGray , magenta" ) ;
						System.out.println( "\t\t\t     orange , pink , red , yellow. " ) ;
						System.out.println( "\tset_color_RGB [num] [num] [num]"+"\t\t調整顏色值" ) ;
						System.out.println( "\tset_color_RGBA [num] [num] [num] [num]"+"\t調整顏色透明值" ) ;
						System.out.println( "\t\t顏色數值範圍為必定為 0~255 " ) ;
						System.out.println( "\tset_bgcolor [顏色名]"+"\t\t\t調整背景顏色" ) ;
						System.out.println( "\t\t顏色名: Random , white , black , blue , cyan" ) ;
						System.out.println( "\t\t\t     darkGray , gray , green , lightGray , magenta" ) ;
						System.out.println( "\t\t\t     orange , pink , red , yellow. " ) ;
						System.out.println( "\tset_bgcolor_RGB [num] [num] [num]"+"\t調整背景顏色值" ) ;
						System.out.println( "\tset_bgcolor_RGBA [num] [num] [num] [num]"+"調整背景顏色透明值" ) ;
						System.out.println( "\t\t顏色數值範圍為必定為 0~255 " ) ;
						System.out.println( "\tpaint_line [x0] [y0] [x1] [y1]"+"\t\t畫直線從(x0,y0)到(x1,y1)" ) ;
						System.out.println( "\tpaint_circle [x0] [y0] [x1] [y1]"+"\t畫圓形從(x0,y0)到(x1,y1)" ) ;
						System.out.println( "\tpaint_rect [x0] [y0] [x1] [y1]"+"\t\t畫矩形從(x0,y0)到(x1,y1)" ) ;
						System.out.println( "\tpaint_tri [x0] [y0] [x1] [y1]"+"\t\t畫三角形從(x0,y0)到(x1,y1)" ) ;
						System.out.println( "\tpaint_heart [x0] [y0] [x1] [y1]"+"\t\t畫愛心從(x0,y0)到(x1,y1)" ) ;
						System.out.println( "\tget_info"+"\t\t\t\t取得現在設定值" ) ;
						System.out.println( "\tquit"+"\t\t\t\t\t關閉程式" ) ;
						System.out.println( "\thelp"+"\t\t\t\t\t本指令" ) ;
						System.out.println( "警告! 所有數值不得是浮點數" ) ;
					}
					else
					{
						System.out.println( "" );
						System.out.println( "指令錯誤" ) ;
						System.out.println( "輸入 help 取得正確指令" ) ;
					}
				}
				catch( Exception ex )
				{
					System.out.println( "" );
					System.out.println( "指令錯誤" ) ;
					System.out.println( "輸入 help 取得正確指令" ) ;
				}
			}
		}

	}
}