package hz_llf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.concurrent.ForkJoinPool.ManagedBlocker;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class ActionChangeService {
	private JPanel serPanle;
	
	private JPanel nPanel;
	private JPanel cJPanel;
	private JPanel sPanel;
	private JPanel lPanel;
	private JPanel rPanel;
	private JButton curlButton;
	private DBconncetion dBconncetion;
	private Vector<Amanger> servicelist;
	private JPanel detPanel;
	private JFrame mainframe;
	public static final int MODE_NOTHINT=1;//��ģʽ
	public static final int MODE_VIP=2;//��Ա����
	public static final int MODE_GOODS=3;//�������
	public static final int MODE_COUNT=4;//ͳ��
	public static final int MODE_VIEWS=5;//�����
	public static final int MODE_SELL=6;//��������
	public boolean isswitch;
	private int lastMode;//�ϴ�״̬

	//������Ϊ�л�
	public ActionChangeService(JPanel servicePanle,JPanel detpanel,Vector<Amanger> servicelist,DBconncetion dBconncetion ,JFrame mainframe) {
		// TODO Auto-generated constructor stub
		this.serPanle=servicePanle;
		this.mainframe=mainframe;
		serPanle.setLayout(new BorderLayout());
		serPanle.setBorder(new LineBorder(Color.black));
		curlButton=new JButton(" ");
		nPanel=new JPanel();
		cJPanel=new JPanel();
		sPanel=new JPanel();
		lPanel=new JPanel();
		rPanel=new JPanel(); //���ڴ�����������
		detPanel=detpanel;
		lPanel.setLayout(new BorderLayout());
		lPanel.add(nPanel,BorderLayout.NORTH);
		lPanel.add(cJPanel, BorderLayout.CENTER);
		lPanel.add(sPanel, BorderLayout.SOUTH);
		lPanel.repaint();
		
		rPanel.setLayout(new BorderLayout());
		rPanel.add(curlButton);
		rPanel.repaint();
		
		serPanle.add(lPanel,BorderLayout.CENTER);
		serPanle.add(rPanel, BorderLayout.EAST);//��װ���
		serPanle.repaint();
		//������``````
		this.servicelist=servicelist;
		this.dBconncetion=dBconncetion;
		this.lastMode=MODE_NOTHINT;//ʲô������ģʽ
		this.isswitch=false;
		for(Amanger a:servicelist){
			a.getButton().addActionListener(new buttonListener(a));
			//��Ӽ�����
			
		}
		this.curlButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				switchCule();
				
			}
		});
		
		
	}
	
	
	public void switchCule () {
		if(isswitch==false){//û�о�
			mainframe.getContentPane().removeAll();
			
			serPanle.removeAll();
			serPanle.setLayout(new BorderLayout());
			serPanle.add(rPanel, BorderLayout.CENTER);
			serPanle.validate();
			serPanle.repaint();
			
			mainframe.getContentPane().setLayout(new BorderLayout());
			mainframe.getContentPane().add(serPanle,BorderLayout.WEST);
			mainframe.getContentPane().add(detPanel, BorderLayout.CENTER);
			mainframe.getContentPane().validate();
			mainframe.getContentPane().repaint();
		
			
			
			isswitch=true;
		}
		
		else {
			
			mainframe.getContentPane().removeAll();
			
			serPanle.removeAll();
			serPanle.setLayout(new BorderLayout());
			serPanle.add(lPanel,BorderLayout.CENTER);
			serPanle.add(rPanel, BorderLayout.EAST);
			serPanle.validate();
			serPanle.repaint();
			
			mainframe.getContentPane().setLayout(new BorderLayout());
			mainframe.getContentPane().add(serPanle,BorderLayout.WEST);
			mainframe.getContentPane().add(detPanel, BorderLayout.CENTER);
			mainframe.getContentPane().validate();
			mainframe.getContentPane().repaint();
		
			
			isswitch=false;
			
			
		}
		
		
		
	}
	public void initOnce() {
		//�ʼ����һ��	
		actPerForming(MODE_VIP);	
	}
	public void  actPerForming(int thismode) {
		if(thismode!=lastMode){
			//���ϴεĲ�һ��
			int i=0;
			Amanger a=null;
			for(i=0;i<servicelist.size();i++){
				
				a=servicelist.elementAt(i);
				System.out.println(thismode);
				if(a.getModeState()==thismode){
					if(a.isFirstCount()==true){
						//��һ��
						
						JLabel thisone=a.getFirstLable();//�õ�һ��
						a.actionAsSelected(thisone);						
						a.setFirstCount(false);
					}
					else {
						JLabel jl= a.getlastOne();
						a.setlastOne(null);
						a.actionAsSelected(jl);//��֮ǰ��������ȥ����
					}
					break;
				}
				
				
			}
			
			nPanel.removeAll();
	
			nPanel.setLayout(new GridLayout(i+1, 1));//���񲼾�
			for(int k=0;k<=i;k++){
				//��������
				nPanel.add(servicelist.elementAt(k).getButton());
				
			}
			nPanel.validate();
			nPanel.repaint();
			cJPanel.removeAll();
		
			JLabel[] jlablist=a.getJlabelList();
			cJPanel.setLayout(new GridLayout(jlablist.length,1 ));
			
			for(JLabel j:jlablist){
				//��������
				cJPanel.add(j);
				
			}
			cJPanel.validate();
			cJPanel.repaint();
			sPanel.removeAll();
		//	sPanel.setBorder(new LineBorder(Color.black));
			if(servicelist.size()<=1){
				sPanel.validate();
				sPanel.repaint();
				lPanel.removeAll();
				lPanel.setLayout(new BorderLayout());
				lPanel.add(nPanel, BorderLayout.NORTH);
				lPanel.add(cJPanel,BorderLayout.CENTER);
				lPanel.add(sPanel, BorderLayout.SOUTH);
				lPanel.validate();
				lPanel.repaint();
				
				
				serPanle.validate();
				serPanle.repaint();
				lastMode=thismode;
				return;
			}//ֻ��һ��
			sPanel.setLayout(new GridLayout(servicelist.size()-(i+1), 1));
			for(int m=i+1;m<servicelist.size();m++){
				sPanel.add(servicelist.elementAt(m).getButton());
	
			}//ʣ�µļӽ�ȥ
			
			sPanel.validate();
			sPanel.repaint();
			sPanel.validate();
			sPanel.repaint();
			lPanel.removeAll();
			lPanel.setLayout(new BorderLayout());
			lPanel.add(nPanel, BorderLayout.NORTH);
			lPanel.add(cJPanel,BorderLayout.CENTER);
			lPanel.add(sPanel, BorderLayout.SOUTH);
			lPanel.validate();
			lPanel.repaint();
			
			serPanle.validate();
			serPanle.repaint();
			lastMode=thismode;
			
			return;
		}
		
		
		
		
	}
	public JPanel getDetPanle() {
		return detPanel;
	}
	public void setDetPanle(JPanel detPanle) {
		this.detPanel = detPanle;
	}
	public DBconncetion getdBconncetion() {
		return dBconncetion;
	}
	public void setdBconncetion(DBconncetion dBconncetion) {
		this.dBconncetion = dBconncetion;
	}
	
	class buttonListener implements ActionListener{
		private Amanger a;
		public buttonListener(Amanger a) {
			// TODO Auto-generated constructor stub
			this.a=a;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			actPerForming(a.getModeState());//������Ӧ�Ĺ���ģ��
			
		}
		
		
		
		
	}
	
	
	
}
