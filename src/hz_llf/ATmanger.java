package hz_llf;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ATmanger {

		private JFrame mainFrame;
		private	JFrame infoFrame;
		private JPanel serPanel;
		private JPanel detPanel;
		private JPanel quickpanel;
		private Vector<Amanger> alist;//管理模块列表
		private ActionChangeService actChanger;
		private DBconncetion dBconncetion;
		
		
		public ATmanger() {
			// TODO Auto-generated constructor stub
			serPanel=new JPanel();
			
			detPanel=new JPanel();
			quickpanel=new JPanel();
			
			alist=new Vector<Amanger>();
			mainFrame=new JFrame("艾特诗薇管理软件");
			SwingUtilities.updateComponentTreeUI(mainFrame);
			//````````消息窗口``````````````````
			infoFrame=new JFrame();
			SwingUtilities.updateComponentTreeUI(infoFrame);//该窗口加载皮肤
			
			Container co=infoFrame.getContentPane();//得到基本Panel
			co.add(new JLabel(new ImageIcon("source/startimg.jpg")), BorderLayout.CENTER);
			ImageIcon img=new ImageIcon("source/startimg.jpg");
			infoFrame.setSize(img.getIconWidth(),img.getIconHeight()+30);
			JLabel infolabel=new JLabel("");
			co.add(infolabel,BorderLayout.SOUTH);
			Dimension size1=Toolkit.getDefaultToolkit().getScreenSize();
			System.out.println(size1);
			infoFrame.setLocation(size1.width/3, size1.height/6);
			
			infoFrame.setUndecorated(true);
			infoFrame.setResizable(false);
			infoFrame.setVisible(true);
			infolabel.setText("连接数据库");
			String url="jdbc:mysql://localhost:3306/aiteshiwei?useSSL=false";
			String name="webspider";
			String pwd ="webspider";
			dBconncetion=new DBconncetion(url, name, pwd);
			if(!dBconncetion.canConnect()){
				JOptionPane.showMessageDialog(null, "数据库连接失败，请确认数据库服务是否开启");
				System.exit(1);
				
			};
			
			infolabel.setText("加载会员模块");//
			
			alist.add(new VipManger(detPanel, dBconncetion.getOneConnecter(ActionChangeService.MODE_VIP)));
			
			
			infolabel.setText("加载收银模块");//
			
			alist.add(new SellManger(detPanel, dBconncetion.getOneConnecter(ActionChangeService.MODE_SELL)));
			infolabel.setText("加载货物管理模块");//
			alist.add(new GoodsManger(detPanel, dBconncetion.getOneConnecter(ActionChangeService.MODE_GOODS)));
			infolabel.setText("加载响应模块");//
			actChanger=new ActionChangeService(serPanel, detPanel, alist, dBconncetion,mainFrame);
			
			
			
			
			
			
			infolabel.setText("加载完成");
			
			infoFrame.dispose();
			//`````````````````````````````````````````
		
			Dimension size=Toolkit.getDefaultToolkit().getScreenSize();
		
			mainFrame.setLocationRelativeTo(null);
			mainFrame.setLocation(0, 0);
			mainFrame.setSize(size);
			mainFrame.setResizable(false);
		
			mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//
			mainFrame.addWindowListener(new WindowListener() {
				
				@Override
				public void windowOpened(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowIconified(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowDeiconified(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowDeactivated(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowClosing(WindowEvent e) {
					// TODO Auto-generated method stub
					int result=JOptionPane.showConfirmDialog(null, "请确认是否退出");
					
					if(result==JOptionPane.YES_OPTION){
						//做些流关闭操作
						try {
							dBconncetion.relaseAllConnector();
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
						System.exit(0);
						
					}
					else{
						
						
						return;
						
						
					}
					
					
					
				}
				
				@Override
				public void windowClosed(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowActivated(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			//添加窗口监听
			mainFrame.add(serPanel,BorderLayout.WEST);
			mainFrame.add(detPanel,BorderLayout.CENTER);
			mainFrame.addComponentListener(new ComponentListener() {
				
				@Override
				public void componentShown(ComponentEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void componentResized(ComponentEvent e) {
					
					
				}
				
				@Override
				public void componentMoved(ComponentEvent e) {
					mainFrame.setLocationRelativeTo(null);
					
				}
				
				@Override
				public void componentHidden(ComponentEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			actChanger.initOnce();//执行一次
			mainFrame.setVisible(true);
			
			
			
			
			//`````
			
			
			
			
			
			
			
		}
	
		public static void main(String[] args) {
			
//			try {
//				UIManager.setLookAndFeel("swing.addon.plaf.threeD.ThreeDLookAndFeel");
//			} catch (ClassNotFoundException e) {
//				JOptionPane.showMessageDialog(null, "皮肤包加载失败:找不到该皮肤包");
//				
//				e.printStackTrace();
//				return;
//			} catch (InstantiationException e) {
//				JOptionPane.showMessageDialog(null, "皮肤包加载失败：初始化失败");
//				e.printStackTrace();
//				return;
//			} catch (IllegalAccessException e) {
//				JOptionPane.showMessageDialog(null, "皮肤包加载失败：非法访问");
//				e.printStackTrace();
//				return;
//			} catch (UnsupportedLookAndFeelException e) {
//				// TODO Auto-generated catch block
//				JOptionPane.showMessageDialog(null, "皮肤包加载失败：不支持该皮肤包");
//				e.printStackTrace();
//				return;
//			}
		new ATmanger();
			
			
			
		}
	
	
	
	
	
	
}
