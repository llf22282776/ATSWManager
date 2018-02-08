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
		private Vector<Amanger> alist;//����ģ���б�
		private ActionChangeService actChanger;
		private DBconncetion dBconncetion;
		
		
		public ATmanger() {
			// TODO Auto-generated constructor stub
			serPanel=new JPanel();
			
			detPanel=new JPanel();
			quickpanel=new JPanel();
			
			alist=new Vector<Amanger>();
			mainFrame=new JFrame("����ʫޱ�������");
			SwingUtilities.updateComponentTreeUI(mainFrame);
			//````````��Ϣ����``````````````````
			infoFrame=new JFrame();
			SwingUtilities.updateComponentTreeUI(infoFrame);//�ô��ڼ���Ƥ��
			
			Container co=infoFrame.getContentPane();//�õ�����Panel
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
			infolabel.setText("�������ݿ�");
			String url="jdbc:mysql://localhost:3306/aiteshiwei?useSSL=false";
			String name="webspider";
			String pwd ="webspider";
			dBconncetion=new DBconncetion(url, name, pwd);
			if(!dBconncetion.canConnect()){
				JOptionPane.showMessageDialog(null, "���ݿ�����ʧ�ܣ���ȷ�����ݿ�����Ƿ���");
				System.exit(1);
				
			};
			
			infolabel.setText("���ػ�Աģ��");//
			
			alist.add(new VipManger(detPanel, dBconncetion.getOneConnecter(ActionChangeService.MODE_VIP)));
			
			
			infolabel.setText("��������ģ��");//
			
			alist.add(new SellManger(detPanel, dBconncetion.getOneConnecter(ActionChangeService.MODE_SELL)));
			infolabel.setText("���ػ������ģ��");//
			alist.add(new GoodsManger(detPanel, dBconncetion.getOneConnecter(ActionChangeService.MODE_GOODS)));
			infolabel.setText("������Ӧģ��");//
			actChanger=new ActionChangeService(serPanel, detPanel, alist, dBconncetion,mainFrame);
			
			
			
			
			
			
			infolabel.setText("�������");
			
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
					int result=JOptionPane.showConfirmDialog(null, "��ȷ���Ƿ��˳�");
					
					if(result==JOptionPane.YES_OPTION){
						//��Щ���رղ���
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
			//��Ӵ��ڼ���
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
			actChanger.initOnce();//ִ��һ��
			mainFrame.setVisible(true);
			
			
			
			
			//`````
			
			
			
			
			
			
			
		}
	
		public static void main(String[] args) {
			
//			try {
//				UIManager.setLookAndFeel("swing.addon.plaf.threeD.ThreeDLookAndFeel");
//			} catch (ClassNotFoundException e) {
//				JOptionPane.showMessageDialog(null, "Ƥ��������ʧ��:�Ҳ�����Ƥ����");
//				
//				e.printStackTrace();
//				return;
//			} catch (InstantiationException e) {
//				JOptionPane.showMessageDialog(null, "Ƥ��������ʧ�ܣ���ʼ��ʧ��");
//				e.printStackTrace();
//				return;
//			} catch (IllegalAccessException e) {
//				JOptionPane.showMessageDialog(null, "Ƥ��������ʧ�ܣ��Ƿ�����");
//				e.printStackTrace();
//				return;
//			} catch (UnsupportedLookAndFeelException e) {
//				// TODO Auto-generated catch block
//				JOptionPane.showMessageDialog(null, "Ƥ��������ʧ�ܣ���֧�ָ�Ƥ����");
//				e.printStackTrace();
//				return;
//			}
		new ATmanger();
			
			
			
		}
	
	
	
	
	
	
}
