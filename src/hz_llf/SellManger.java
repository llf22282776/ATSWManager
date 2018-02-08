package hz_llf;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;

import hz_llf.GoodsManger.DocListener;
import hz_llf.VipManger.AddSet;

public class SellManger extends Amanger{

	private JButton sellSectionButton;//ѡ��ť
	private JLabel  totalviewLable;	//���������ǩ
	private JLabel  getMoneyLable;	//������ǩ
	private AddSet  addSet;
	private Connecter dBconncetion;//���ݿ�������
	private final int mode=ActionChangeService.MODE_SELL;//��ģʽ�ı��
	private JPanel	detPanle;//�������
	private JPanel	basePanle;//���������
	private boolean isWorking;//�Ƿ�ʼ
	private JLabel lastone;//��һ�������label
	public SellManger(JPanel basepanel,Connecter dbconnect) {
		basePanle=basepanel;
		detPanle=new JPanel();
		isWorking=false;
		lastone=null;
		dBconncetion=dbconnect;
		sellSectionButton=new JButton("��������");
		totalviewLable=new JLabel("��������");
		totalviewLable.addMouseListener(new LabelListener());
		getMoneyLable=new JLabel("��������");
		getMoneyLable.addMouseListener(new LabelListener());	
		super.setFirstCount(true);//�ǵ�һ��
	}
	
	@Override
	public int  getModeState () {
		return mode;
		
	}
	@Override
	public void  actionAsSelected(JLabel thisone) {
		if(isWorking==true || lastone==thisone || thisone==null)
		{
		basePanle.removeAll();//������嶼�Ƴ�
		basePanle.add(detPanle);//
		basePanle.validate();
		basePanle.repaint();
		return;}//����û�п�ʼ��������һ�����Ǳ���,ֻ���м򵥷���ȥ
        isWorking=true;//��ʼ����
//```````````````````````````````````
        if(thisone==totalviewLable){//```````````````````````����ģʽ````````````````
        	  thisone.setForeground(Color.BLUE);
	    	  basePanle.removeAll();//������嶼�Ƴ�
	    	  detPanle.removeAll();//�ù�������Ƴ�
	    	  //```````````````````````````
	    	 final  JPanel allCompone=new JPanel();//��������ŵ�λ��
	    	 final  JPanel jp_north_total=new JPanel();//����ˮ
	    	 final 	JPanel jp_west_search=new JPanel();
	    	 final	JPanel jp_center_today=new JPanel();
	    	 final	JPanel	jp_east_month=new JPanel();
	    	 final JScrollPane jp_table_today=new JScrollPane();
	    	 final JScrollPane jp_table_month=new JScrollPane();
	    	 final JScrollPane jp_table_recorder=new JScrollPane();
	    	 totalViewNorthPaint(jp_north_total);
	    	 totalViewCenterPaint(jp_center_today, jp_table_today);
	    	 totalViewEastPaint(jp_east_month,jp_table_month);
	    	 westSearchRcorderPaing(jp_west_search,jp_table_recorder);
	    	 
	    	 allCompone.setLayout(new BorderLayout());
	    	 allCompone.add(jp_north_total,BorderLayout.NORTH);
	    	 JPanel jp_center=new JPanel(new GridLayout(1, 3));
	    	 jp_center.add(jp_west_search);
	    	 jp_center.add(jp_center_today);
	    	 jp_center.add(jp_east_month);
	    	 
	    	 
	       	 allCompone.add(jp_center, BorderLayout.CENTER);
	    	 detPanle.setLayout(new BorderLayout());
	    	 detPanle.add(allCompone, BorderLayout.CENTER);
  
        	 //````````````````````````````
	    	  detPanle.validate();
	    	  detPanle.repaint();
	    	  basePanle.setLayout(new BorderLayout());
	    	  basePanle.add(detPanle,BorderLayout.CENTER);//
	    	  basePanle.revalidate();
	    	  basePanle.repaint();		    	
	    	  if(lastone!=null) lastone.setForeground(Color.BLACK);
	    	  lastone=thisone;
	    	  isWorking=false;
	    	  return;
        	
        }else if (thisone==getMoneyLable) {//`````````````````����ģʽ`````````````````````````````
        	  thisone.setForeground(Color.BLUE);
	    	  basePanle.removeAll();//������嶼�Ƴ�
	    	  detPanle.removeAll();//�ù�������Ƴ�
	    	  //```````````````````````````
	    	  final JPanel jp_getMoney=new JPanel();
	    	  getMoneyPanel_Paint(jp_getMoney);
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  detPanle.setLayout(new BorderLayout());
	    	  detPanle.add(jp_getMoney, BorderLayout.CENTER);
	    	  
	    	  
	    	  //````````````````````````````
	    	  detPanle.validate();
	    	  detPanle.repaint();
	    	  basePanle.setLayout(new BorderLayout());
	    	  basePanle.add(detPanle,BorderLayout.CENTER);//
	    	  basePanle.revalidate();
	    	  basePanle.repaint();		    	
	    	  if(lastone!=null) lastone.setForeground(Color.BLACK);
	    	  lastone=thisone;
	    	  isWorking=false;
	    	  return;
		}
		
		
		
		
		
	}
	public void westSearchRcorderPaing(JPanel jp_west_search1, JScrollPane jp_table_recorder1) {
			final JPanel jp_west_search=jp_west_search1;
			final JScrollPane jp_table_recorder=jp_table_recorder1;
			final JTextField jt_start=new JTextField();
			final JTextField jt_end=new JTextField();;
			final JLabel jLabel_start=new JLabel("��ʼʱ��");
			final JLabel jLabel_end=new JLabel("����ʱ��:");
			final JButton jb_search=new JButton("��ѯ");
			final JButton jb_export=new JButton("����");
			//final  java.sql.PreparedStatement s=null;
	    	final  Connection con=dBconncetion.getCon();
	    //	final  ResultSetMetaData tableheader=null;
	    	final  Vector<String> tableheaderNameVec=new Vector<String>();
	    	final  Vector<Vector<String>> tableDateRowVec=new Vector<>();
			jb_search.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						java.sql.PreparedStatement s1=null;
						Statement s=null;
						ResultSetMetaData tableheader=null;
						String dateStart= toDateString(jt_start.getText(), 3);
						String dateEnd= toDateString(jt_end.getText(), 4);
						tableDateRowVec.clear();
						tableheaderNameVec.clear();
						System.out.println("ds:"+dateStart+" de:"+dateEnd);
						ResultSet r=null;
						s1=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_sell_todaycount));
						s1.setTimestamp(1, Timestamp.valueOf(dateStart));
						s1.setTimestamp(2, Timestamp.valueOf(dateEnd));
						JTable jTable=null;
						jTable=creatJtable(null, s, s1, r, tableheader, tableheaderNameVec, tableDateRowVec, con);
						if(jTable!=null)jp_table_recorder.setViewportView(jTable);	
						jp_table_recorder.validate();
						jp_table_recorder.repaint();

						
					}catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "����:sql����.\n�������:"+"\n����:"+e1.getMessage());											
						e1.printStackTrace();
						return ;
					}
					
					catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "����:���������ʽ.\n�������:"+"\n����:"+e1.getMessage());											
						e1.printStackTrace();
						return ;
					}
					
					
					
					
					
					
					
					
				}
			});
			
			JPanel jp_north=new JPanel(new GridLayout(3, 1));
			
			JPanel jp_north_r1=new JPanel(new GridLayout(1, 2));
			jp_north_r1.add(jLabel_start);
			jp_north_r1.add(jt_start);
			JPanel jp_north_r2=new JPanel(new GridLayout(1, 2));
			jp_north_r2.add(jLabel_end);
			jp_north_r2.add(jt_end);
			JPanel jp_north_r3=new JPanel(new GridLayout(1, 2));
			jp_north_r3.add(jb_search);
			jp_north_r3.add(jb_export);
			
			
			jp_north.add(jp_north_r1);
			jp_north.add(jp_north_r2);
			jp_north.add(jp_north_r3);
			jp_west_search.setLayout(new BorderLayout());
			jp_west_search.add(jp_north, BorderLayout.NORTH);
			jp_west_search.add(jp_table_recorder, BorderLayout.CENTER);
			jp_west_search.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black),"��ˮ��ѯ"));
			
			jp_west_search.validate();
			jp_west_search.repaint();
		
	}

	@Override
	public JLabel getFirstLable() {
		return totalviewLable;
	}
	@Override
	public JLabel[] getJlabelList() {
		return new JLabel[]{
		totalviewLable,
		getMoneyLable
		
		};
		
	}
	@Override
	public JButton getButton() {
		return sellSectionButton;
	
          }
	
	public void totalViewNorthPaint(JPanel jp){
		  final JPanel jp_north=jp;	
		  Statement s=null;
	  	  ResultSet r=null;
	  	  Connection con=dBconncetion.getCon();
	  	  JLabel jLabel_allmoney=new JLabel("�����ѽ��:");
	  	  JLabel jLabel_allVipMoney=new JLabel("��Ա�����ѽ��:");
	  	  JLabel jLabel_allnormalMoney=new JLabel("ɢ�������ѽ��:");
	  	  JLabel jLabel_totalClothes=new JLabel("����ˮ��Ʒ����:");
	  	  int totalmoney=-1;
	  	  int totalvipMoney=-1;	  
	  	  int totalClothes=-1;
			try {
				s=con.createStatement();
				r=s.executeQuery("select sum(actalget) totalmoney from "+ Connecter.TABLENAME_ONCEDETIAL+
								" ;"
				);
				if(r.next())totalmoney=r.getInt(1);   
				if(!r.isClosed())r.close();		
				r=s.executeQuery(" select sum(actalget) totalmoney from "+ Connecter.TABLENAME_ONCEDETIAL+
								" where vipid != 1000  ;"
						);
				
				if(r.next())totalvipMoney=r.getInt(1);
				if(!r.isClosed())r.close();
				r=s.executeQuery("select count(*)  from "+ Connecter.TABLENAME_SELL+" s;"
						
				);
				
				if(r.next())totalClothes=r.getInt(1);
				if(!r.isClosed())r.close();
				
				
				if(totalmoney!=-1 && totalvipMoney!=-1 && totalClothes!=-1){
					
					jLabel_allnormalMoney.setText(jLabel_allnormalMoney.getText()+(totalmoney-totalvipMoney)+"Ԫ");
					jLabel_allVipMoney.setText(jLabel_allVipMoney.getText()+totalvipMoney+"Ԫ");
					jLabel_allmoney.setText(jLabel_allmoney.getText()+totalmoney+"Ԫ");
					jLabel_totalClothes.setText(jLabel_totalClothes.getText()+totalClothes+"��");
				}
				
				jp_north.setLayout(new GridLayout(2	, 1));
				JPanel jPanel_r1=new JPanel(new GridLayout(1	, 2));
				jPanel_r1.add(jLabel_allmoney);
				jPanel_r1.add(jLabel_allVipMoney);
				
				JPanel jPanel_r2=new JPanel(new GridLayout(1	, 2));
				jPanel_r2.add(jLabel_allnormalMoney);
				jPanel_r2.add(jLabel_totalClothes);
				
				jp_north.add(jPanel_r1);
				jp_north.add(jPanel_r2);
				
				jp_north.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black),"����ˮ"));
				jp_north.validate();
				jp_north.repaint();
				
				
				
				
				
				if(!r.isClosed())r.close();
				if(!s.isClosed())s.close();
				
			} catch (SQLException e1) {
			
				JOptionPane.showMessageDialog(null, "sql���ʧ��,����д��ȷ��Ա���.\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
				e1.printStackTrace();
				return;
	
			}
		
		
		
		
		
		
	}
	public void totalViewCenterPaint(JPanel jp,JScrollPane jp_table){
		final JPanel   jp_today=jp;
		 PreparedStatement s1;
	
	  	  ResultSet r=null;
	  	  Connection con=dBconncetion.getCon();
	  	  JLabel jLabel_allmoney=new JLabel(      "�������ѽ��            : ");
	  	  JLabel jLabel_allVipMoney=new JLabel(   "���ջ�Ա�����ѽ��  : ");
	  	  JLabel jLabel_allnormalMoney=new JLabel("����ɢ�������ѽ��  : ");
	  	  JLabel jLabel_totalClothes=new JLabel(  "������ˮ��Ʒ����	 : ");
	  	  int totalmoney=-1;
	  	  int totalvipMoney=-1;	  
	  	  int totalClothes=-1;
	  	  SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	  	  Calendar c=Calendar.getInstance();
	  	  c.setTime(new Date());
	  
	  	  c.set(Calendar.HOUR_OF_DAY,0);
	  	  c.set(Calendar.MINUTE, 0);
	  	  c.set(Calendar.SECOND, 0);
	  	  c.set(Calendar.MILLISECOND, 0);
	  	  String basedate= df.format(c.getTime());
	  	  
	  	  String datestring=df.format(new Date());
	  	  System.out.println("today:"+basedate+" "+datestring );
			try {
				//s=con.createStatement();
				s1=con.prepareStatement(
						" select sum(actalget) totalmoney from "+Connecter.TABLENAME_ONCEDETIAL+
						" where buydate>= ? and buydate<= ? ;");
				s1.setTimestamp(1, Timestamp.valueOf(basedate));
				s1.setTimestamp(2, Timestamp.valueOf(datestring));
				
				r=s1.executeQuery();
				if(r.next())totalmoney=r.getInt(1);   
				if(!r.isClosed())r.close();		
				if(!s1.isClosed())s1.close();
				s1=con.prepareStatement(
						" select sum(actalget) totalmoney from "+Connecter.TABLENAME_ONCEDETIAL+
						" where buydate>= ? and buydate<= ? and vipid!=1000;"
								);
				s1.setTimestamp(1, Timestamp.valueOf(basedate));
				s1.setTimestamp(2, Timestamp.valueOf(datestring));
				r=s1.executeQuery();
				if(r.next())totalvipMoney=r.getInt(1);   
				if(!r.isClosed())r.close();		
				if(!s1.isClosed())s1.close();
				s1=con.prepareStatement(
						"select count(*)  from "+ Connecter.TABLENAME_SELL+" s where s.buydate>= ? and s.buydate<= ? ;"
						);

				s1.setTimestamp(1, Timestamp.valueOf(basedate));
				s1.setTimestamp(2, Timestamp.valueOf(datestring));
				r=s1.executeQuery();
				if(r.next())totalClothes=r.getInt(1);   
				if(!r.isClosed())r.close();		
				if(!s1.isClosed())s1.close();
				//System.out.println(totalmoney+" "+totalvipMoney+" "+totalClothes );
				if(totalmoney!=-1 && totalvipMoney!=-1 && totalClothes!=-1){
					//System.out.println(totalmoney+" "+totalvipMoney+" "+totalClothes );
					jLabel_allnormalMoney.setText(jLabel_allnormalMoney.getText()+(totalmoney-totalvipMoney)+"Ԫ");
					jLabel_allVipMoney.setText(jLabel_allVipMoney.getText()+totalvipMoney+"Ԫ");
					jLabel_allmoney.setText(jLabel_allmoney.getText()+totalmoney+"Ԫ");
					jLabel_totalClothes.setText(jLabel_totalClothes.getText()+totalClothes+"��");
				}
	
				if(!r.isClosed())r.close();
				if(!s1.isClosed())s1.close();
				java.sql.PreparedStatement s=null;
		    	  
		    	ResultSetMetaData tableheader=null;
		    	Vector<String> tableheaderNameVec=new Vector<String>();
		    	Vector<Vector<String>> tableDateRowVec=new Vector<>();
		    	s1=con.prepareStatement(dBconncetion.getSqlStatementMap().get( Connecter.sql_mode_sell_todaycount));

		    	s1.setTimestamp(1, Timestamp.valueOf(basedate));
				s1.setTimestamp(2, Timestamp.valueOf(datestring));
				tableDateRowVec.clear();
				tableheaderNameVec.clear();
		    	final JTable jTable=creatJtable(null, s, s1, r, tableheader, tableheaderNameVec, tableDateRowVec, con);
				if(jTable!=null){
						jp_table.setViewportView(jTable);

					
				}
				
				
				JPanel jp_north=new JPanel();
				jp_north.setLayout(new GridLayout(2	, 2));
				JPanel jPanel_r1=new JPanel(new GridLayout(1	,2));
				jPanel_r1.add(jLabel_allmoney);
				jPanel_r1.add(jLabel_allVipMoney);
				
				JPanel jPanel_r2=new JPanel(new GridLayout(1	, 2));
				jPanel_r2.add(jLabel_allnormalMoney);
				jPanel_r2.add(jLabel_totalClothes);
				
				jp_north.add(jPanel_r1);
				jp_north.add(jPanel_r2);
				
				jp_north.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black),"������ˮ"));
				
				
				jp_today.setLayout(new BorderLayout());
				jp_today.add(jp_north,BorderLayout.NORTH);
				jp_today.add(jp_table, BorderLayout.CENTER);
				jp_today.validate();
				jp_today.repaint();
	
				
			} catch (SQLException e1) {
			
				JOptionPane.showMessageDialog(null, "sql���ʧ��,����д��ȷ��Ա���.\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
				e1.printStackTrace();
				return;
	
			}
			 catch (Exception e1) {
					
					JOptionPane.showMessageDialog(null, "����:����ȷ����"+"\n����:"+e1.getMessage());											
					e1.printStackTrace();
					return;
		
				}
		
		
		
		
		
		
		
		
	}
	public JTable	creatJtable(String sql, Statement s,java.sql.PreparedStatement s1, ResultSet r,ResultSetMetaData tableheader,Vector<String> tableheaderNameVec,Vector<Vector<String>> tableDateRowVec,Connection con) {
		try {
			
			JTable jtable=null;
			if(sql==null){
				//Ԥ��������
				
				r=s1.executeQuery();
				tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				//  System.out.println(r);
				  if(tableColumNum<=0)return null;
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  //������ͷvec
				  while(r.next()){
					  //����һ��
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						  if(tableheaderNameVec.elementAt(j-1).equalsIgnoreCase("����")){
							  v.add(r.getString(j).substring(r.getString(j).indexOf("-")+"-".length()));
							  continue;
							  
						  }
						  v.add(r.getString(j));//��ӽ�ȥ							  
					  }
					  tableDateRowVec.add(v);
				  				}
				  DefaultTableModel dtmod=new DefaultTableModel(tableDateRowVec, tableheaderNameVec);
				  jtable=new JTable(dtmod);//������	
				  return jtable;

								}
			else if(sql!=null){
				r=s.executeQuery(sql);
				tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				//  System.out.println(r);
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  //������ͷvec
				  while(r.next()){
					  //����һ��
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						  if(tableheaderNameVec.elementAt(j-1).equalsIgnoreCase("����")){
							  v.add(r.getString(j).substring(r.getString(j).indexOf("-")+"-".length()));
							  continue;
							  
						  }
						  v.add(r.getString(j));//��ӽ�ȥ							  
					  }
					  tableDateRowVec.add(v);
				  				}
				  DefaultTableModel dtmod=new DefaultTableModel(tableDateRowVec, tableheaderNameVec);
				  jtable=new JTable(dtmod);//������	
				  return jtable;

				
				
				
			}
			else  return null;
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "sql���ʧ��,����д��ȷ��Ա���.\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
			e1.printStackTrace();
			return null;
			
			
			
			
			
		}
		catch (Exception e1) {
			
			JOptionPane.showMessageDialog(null, "����:���������ʽ.\n�������:"+"\n����:"+e1.getMessage());											
			e1.printStackTrace();
			return null;

		}
		
		
		
		
		
	}
	
	public void totalViewEastPaint(JPanel jp,JScrollPane jp_table){
		final JPanel j_thismonth=jp;
		 PreparedStatement s1;
			
			  	  ResultSet r=null;
			  	  Connection con=dBconncetion.getCon();
			  	  JLabel jLabel_allmoney=new JLabel(      "�������ѽ��                 :");
			  	  JLabel jLabel_allVipMoney=new JLabel(   "���»�Ա�����ѽ��       :");
			  	  JLabel jLabel_allnormalMoney=new JLabel("����ɢ�������ѽ��       :");
			  	  JLabel jLabel_totalClothes=new JLabel(  "������ˮ��Ʒ����	    :");
			  	  int totalmoney=-1;
			  	  int totalvipMoney=-1;	  
			  	  int totalClothes=-1;
			  	  SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			  	  Calendar c=Calendar.getInstance();
			  	  c.setTime(new Date());	  
			  	  c.set(Calendar.DAY_OF_MONTH,1);
			  	  c.set(Calendar.HOUR_OF_DAY,0);
			  	  c.set(Calendar.MINUTE, 0);
			  	  c.set(Calendar.SECOND, 0);
			  	  c.set(Calendar.MILLISECOND, 0);
			  	  String basedate= df.format( c.getTime());
			   	  c.setTime(new Date());
			   	  c.set(Calendar.MONTH, c.get(Calendar.MONTH)+1);
			   	  c.set(Calendar.DAY_OF_MONTH,1);
			   	  c.add(Calendar.DATE,-1);
			  	  c.set(Calendar.HOUR,23);
			  	  c.set(Calendar.MINUTE, 59);
			  	  c.set(Calendar.SECOND, 59);
			  	  c.set(Calendar.MILLISECOND, 0);//���¼�һ��Ϊ�������һ��
			  	  String datestring=df.format(c.getTime());
//			  	  String temp=basedate;
//			  	  basedate=datestring;
//			  	  datestring=temp;
			     System.out.println("mon:"+basedate+" "+datestring);
					try {
		
						s1=con.prepareStatement(
								"select sum(actalget) totalmoney from "+Connecter.TABLENAME_ONCEDETIAL+
								" where buydate>= ? and buydate<= ? ;");

						s1.setTimestamp(1, Timestamp.valueOf(basedate));
						s1.setTimestamp(2, Timestamp.valueOf(datestring));
						r=s1.executeQuery();
						if(r.next())totalmoney=r.getInt(1);   
						if(!r.isClosed())r.close();		
						if(!s1.isClosed())s1.close();
						s1=con.prepareStatement(
								"select sum(actalget) totalmoney from "+Connecter.TABLENAME_ONCEDETIAL+
								" where vipid!=1000 and buydate>= ? and buydate<= ? ;"
										);

						s1.setTimestamp(1, Timestamp.valueOf(basedate));
						s1.setTimestamp(2, Timestamp.valueOf(datestring));
						r=s1.executeQuery();
						if(r.next())totalvipMoney=r.getInt(1);   
						if(!r.isClosed())r.close();		
						if(!s1.isClosed())s1.close();
						s1=con.prepareStatement(
								"select count(*)  from "+ Connecter.TABLENAME_SELL+" s where s.buydate>= ? and s.buydate<= ? ;"
								);

						s1.setTimestamp(1, Timestamp.valueOf(basedate));
						s1.setTimestamp(2, Timestamp.valueOf(datestring));
						r=s1.executeQuery();
						if(r.next())totalClothes=r.getInt(1);   
						if(!r.isClosed())r.close();		
						if(!s1.isClosed())s1.close();
			
						if(totalmoney!=-1 && totalvipMoney!=-1 && totalClothes!=-1){
							
							jLabel_allnormalMoney.setText(jLabel_allnormalMoney.getText()+(totalmoney-totalvipMoney)+"Ԫ");
							jLabel_allVipMoney.setText(jLabel_allVipMoney.getText()+totalvipMoney+"Ԫ");
							jLabel_allmoney.setText(jLabel_allmoney.getText()+totalmoney+"Ԫ");
							jLabel_totalClothes.setText(jLabel_totalClothes.getText()+totalClothes+"��");
						}
			
						if(!r.isClosed())r.close();
						if(!s1.isClosed())s1.close();
						java.sql.PreparedStatement s=null;
				    	  
				    	  ResultSetMetaData tableheader=null;
				    	  Vector<String> tableheaderNameVec=new Vector<String>();
				    	  Vector<Vector<String>> tableDateRowVec=new Vector<>();
				    	s1=con.prepareStatement(dBconncetion.getSqlStatementMap().get( Connecter.sql_mode_sell_todaycount));

				    	s1.setTimestamp(1, Timestamp.valueOf(basedate));
						s1.setTimestamp(2, Timestamp.valueOf(datestring));
				    	final JTable jTable=creatJtable(null, s, s1, r, tableheader, tableheaderNameVec, tableDateRowVec, con);
						if(jTable!=null){
								jp_table.setViewportView(jTable);

							
						}
						
						
						JPanel jp_north=new JPanel();
						jp_north.setLayout(new GridLayout(2	, 2));
						JPanel jPanel_r1=new JPanel(new GridLayout(1	, 2));
						jPanel_r1.add(jLabel_allmoney);
						jPanel_r1.add(jLabel_allVipMoney);
						
						JPanel jPanel_r2=new JPanel(new GridLayout(1	,2));
						jPanel_r2.add(jLabel_allnormalMoney);
						jPanel_r2.add(jLabel_totalClothes);
						
						jp_north.add(jPanel_r1);
						jp_north.add(jPanel_r2);
						
						jp_north.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black),"������ˮ"));
						 j_thismonth.setLayout(new BorderLayout());
						 j_thismonth.add(jp_north,BorderLayout.NORTH);
						 j_thismonth.add(jp_table, BorderLayout.CENTER);
						 j_thismonth.validate();
						 j_thismonth.repaint();
			
						
					} catch (SQLException e1) {
					
						JOptionPane.showMessageDialog(null, "sql���ʧ��,����д��ȷ��Ա���.\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
						e1.printStackTrace();
						return;
			
					}
				
				
		
		
		
		
		
		
		
		
	}
	public void  getMoneyPanel_Paint(JPanel jp_north1) {
		final JPanel jp_getMoney=jp_north1;
		final JButton jb_fianlButton=new JButton("����");
		final JScrollPane js_tableDetialGoods=new JScrollPane();
	//	final JLabel jl_grap=new JLabel(" ");
		final JLabel jl_vipidTitle=new JLabel("��Ա�绰:");
		final JTextField jt_vipid=new JTextField();
		final JButton jb_searchVip=new JButton("����");
		final JButton jb_addNewVip=new JButton("����");
	//	final JLabel jl_grap1=new JLabel(" ");
	//``````````````````````````````````````````````

		final Vector<Integer> vidVec=new Vector<Integer>();
		vidVec.clear();
		//vidVec.setSize(1);
		final JLabel jL_vipID=new JLabel("���:");
		final JLabel jl_phone=new JLabel("�绰:");
		final JLabel jl_name=new JLabel("����:");
		
	//`````````````````````````````````````	
		final JTextArea jt_scanf=new JTextArea();
		jt_scanf.setEditable(true);
		jt_scanf.setVisible(false);
		final Vector<JTable> tableVec=new Vector<JTable>();
		//tableVec.setSize(1);
		tableVec.clear();
		final Document doc=jt_scanf.getDocument();
		final  Vector<String> tableheaderNameVec=new Vector<String>();
		final  Vector<Vector<String>> tableDateRowVec=new Vector<>();
		

		jt_scanf.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			
				
				
					
					
	
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println("why!!!!!"+e.getKeyCode()+" "+KeyEvent.VK_BACK_SPACE);
				if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
					System.out.println("delete");
					if(tableVec.size()==0 || tableDateRowVec.size()==0 || tableheaderNameVec.size()==0)return;
					JTable jtable_detial= tableVec.get(0);
					DefaultTableModel dmod=(DefaultTableModel)jtable_detial.getModel();//
					dmod.removeRow(jtable_detial.getSelectedRow());
					jtable_detial.validate();
					jtable_detial.repaint();
					tableDateRowVec.removeElementAt(jtable_detial.getSelectedRow());//����������һ��ɾ��
					
					
					
					
				}
				
			
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
				
			}
		});
		
		
		
		final JButton jb_startScanf=new JButton("��ʼɨ��");
		final JButton jb_delete=new JButton("ɾ��");
		final JButton jb_resetButton=new JButton("����");
		final Printer ticketPrint=new Printer();
	//`````````````````````````````````````	
		
		
		
	//	
		jb_delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tableVec.size()==0 || tableDateRowVec.size()==0 || tableheaderNameVec.size()==0)return;
				JTable jtable_detial= tableVec.get(0);
				DefaultTableModel dmod=(DefaultTableModel)jtable_detial.getModel();//
				//System.out.println("before:"+tableDateRowVec);
				
				 int index=jtable_detial.getSelectedRow();
				// System.out.println("index:"+index);
				dmod.removeRow(jtable_detial.getSelectedRow());
			//	System.out.println("after:"+tableDateRowVec);
		
				if(tableDateRowVec.size()!=0 )jtable_detial.setRowSelectionInterval(tableDateRowVec.size()-1, tableDateRowVec.size()-1);//���һ��
				
				jtable_detial.validate();
				jtable_detial.repaint();
				jt_scanf.setVisible(true);
				jt_scanf.requestFocus(true);
				
			}
		});
		jt_vipid.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				JTextField jt=(JTextField) e.getSource();
				// System.out.println(e.getKeyChar());
				 //e.getKeyCode()>= KeyEvent.VK_0 && e.getKeyCode()<= KeyEvent.VK_9 
				if(true ){
					try {
						
						String phonenum="%"+jt.getText()+e.getKeyChar()+"%";
						 Connection con=dBconncetion.getCon();
						 PreparedStatement s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_searchThereby_phone));
						 s.setString(1, phonenum);
						 ResultSet r=s.executeQuery();
						 if(r.next()){
							// System.out.println("get");
							 jl_name.setText("����:");
							 jl_phone.setText("�绰:");
							 jL_vipID.setText("���:");
							 jl_name.setText(jl_name.getText()+r.getString(1));
							 jl_phone.setText(jl_phone.getText()+r.getString(2));
							 jL_vipID.setText(jL_vipID.getText()+r.getInt(3));
							 jp_getMoney.validate();
							 jp_getMoney.repaint();
							 vidVec.clear();
							 vidVec.addElement(new Integer(r.getInt(3)));
						 
						 }
						 else {
							
							 jl_name.setText("����:");
							 jl_phone.setText("�绰:");
							 jL_vipID.setText("���:");
							 jp_getMoney.validate();
							 jp_getMoney.repaint();
							 vidVec.clear();
							 
							 
						}
						 
						 
						
						
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "sql���ʧ��,����д��ȷ��Ա���.\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
						e1.printStackTrace();
						return ;
					}
					catch (Exception e2) {
						
					}

			}
			
			}
		});
		
		jb_searchVip.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				  jt_vipid.setText("");
				  final JPanel jp_search=new JPanel();
		    	  final JScrollPane jp_table=new JScrollPane();//���
		    	  JLabel jl_wayTosearch=new JLabel("���ҷ�ʽ:");
		    	  final CheckboxGroup checkboxGroup=new CheckboxGroup();
		    	  final Map<String, Checkbox> boxmap=new HashMap<String,Checkbox>();
		    	  boxmap.put("��Ա���", new Checkbox("��Ա���",checkboxGroup,true));
		    	  boxmap.put("�绰", new Checkbox("�绰", checkboxGroup,false));
		    	  boxmap.put("����", new Checkbox("����", checkboxGroup,false));
		    	  boxmap.put("����", new Checkbox("����", checkboxGroup,false));
		    	  boxmap.put("��������", new Checkbox("��������", checkboxGroup,false));
		    	  boxmap.put("����", new Checkbox("����", checkboxGroup,false));
		    	  final JFrame addFrame=new JFrame("���һ�Ա");
		    	  final Vector<JTable> jTableVec=new Vector<JTable>();
		    	  jTableVec.clear();
		    	//  jTableVec.setSize(1);
		    	  addFrame.addFocusListener(new FocusListener() {
					
					@Override
					public void focusLost(FocusEvent e) {
						((JFrame)e.getSource()).requestFocus();
						
					}
					
					@Override
					public void focusGained(FocusEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
		    	//  addFrame.setAlwaysOnTop(true);
		    	  addFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		    	  addFrame.addWindowListener(new WindowListener() {
					
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
						if(jTableVec.size()!=0){
							JTable jt=jTableVec.get(0);
							if(jt.getSelectedRow()==-1){//û��ѡ
								int  state=JOptionPane.showConfirmDialog(null, "δѡ���Ա,�Ƿ񷵻�ѡ��");
								if(state==JOptionPane.CANCEL_OPTION || state==JOptionPane.OK_OPTION){
								      return;

								}else {
									vidVec.clear();
									((JFrame)e.getSource()).dispose();//û�����
								}
								
							}else {
								vidVec.clear();
								vidVec.addElement(new Integer(Integer.parseInt((String)jt.getValueAt(jt.getSelectedRow(), 1))));
								((JFrame)e.getSource()).dispose();
							}
							
							
						}else {
							int  state=JOptionPane.showConfirmDialog(null, "δѡ���Ա,�Ƿ񷵻�ѡ��");
							if(state==JOptionPane.CANCEL_OPTION || state==JOptionPane.OK_OPTION){
								//���ؼ���
								return;
							}
							else {
								vidVec.clear();
								((JFrame)e.getSource()).dispose();//û�����
							}
							
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
		    	  
		    	  addFrame.setSize(700, 400);
		    	  addFrame.setResizable(false);
		    	  addFrame.setLocation(300, 300); 
		    	
		    	  JLabel jl_title=new JLabel("����:");
		    	   final  JTextField jt=new JTextField();
		    	   final JButton jb_ok=new JButton("ȷ��");
		    	   
		    	  jb_ok.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if(jTableVec.size()!=0){
							//System.out.println(jTableVec);
							JTable jt=jTableVec.get(0);
							if(jt.getSelectedRow()==-1){//û��ѡ
								int  state=JOptionPane.showConfirmDialog(null, "δѡ���Ա,�Ƿ񷵻�ѡ��");
								if(state==JOptionPane.YES_OPTION || state==JOptionPane.CANCEL_OPTION){
								      return;

								}else {
									vidVec.clear();
									addFrame.dispose();//û�����
									jl_name.setText("����:");
									 jl_phone.setText("�绰:");
									 jL_vipID.setText("���:");
									 jp_getMoney.validate();
									 jp_getMoney.repaint();
									return;
								}
								
							}else {
								vidVec.clear();
								vidVec.addElement(new Integer(Integer.parseInt((String)jt.getValueAt(jt.getSelectedRow(), 0))));//ID,�绰������
								jl_name.setText("����:");
								 jl_phone.setText("�绰:");
								 jL_vipID.setText("���:");
								jL_vipID.setText(jL_vipID.getText()+vidVec.elementAt(0));
								jl_name.setText(jl_name.getText()+jt.getValueAt(jt.getSelectedRow(),2 ));
								jl_phone.setText(jl_phone.getText()+jt.getValueAt(jt.getSelectedRow(),1 ));
								addFrame.dispose();
							}
							
							
						}else {
							int  state=JOptionPane.showConfirmDialog(null, "δѡ���Ա,�Ƿ񷵻�ѡ��");
							if(state==JOptionPane.YES_OPTION || state==JOptionPane.CANCEL_OPTION){
								//���ؼ���
								return;
							}
							else {
								vidVec.clear();
								addFrame.dispose();//û�����
								jl_name.setText("����:");
								 jl_phone.setText("�绰:");
								 jL_vipID.setText("���:");
								 jp_getMoney.validate();
								 jp_getMoney.repaint();
								return;
							}
							
						}
						
						
						
					}
				});
		    	  JButton jb_search=new JButton("��ѯ");
		    	  jb_search.addActionListener(new ActionListener() {				
					@Override
					public void actionPerformed(ActionEvent e) {
					   JTable jtab=	amongSearch(jp_table,checkboxGroup,boxmap,jt);//�������
					   jTableVec.clear();
					   jTableVec.addElement(jtab);
					}
				});
		    	 
		    	  //````�����
		    	  jp_search.setLayout(new BorderLayout());
		    	  JPanel jp_search_north=new JPanel();
		    	  jp_search_north.setLayout(new GridLayout(2, 2));
		    	  jp_search_north.add(jl_wayTosearch);
		    	  JPanel jp_search_north_r1c2=new JPanel();
		    	  jp_search_north_r1c2.setLayout(new FlowLayout());
		    	  for(Checkbox cb:boxmap.values()){
		    		  jp_search_north_r1c2.add(cb);
		    		  
		    	  }
		    	  jp_search_north.add(jp_search_north_r1c2);
		    	  jp_search_north.add(jl_title);
		    	  JPanel jp_search_north_r2c2=new JPanel();
		    	  jp_search_north_r2c2.setLayout(new GridLayout(1, 2));
		    	  jp_search_north_r2c2.add(jt);
		    	  jp_search_north_r2c2.add(jb_search);
		    	  jp_search_north.add(jp_search_north_r2c2);
		    	  jp_search_north.validate();
		    	  jp_search_north.repaint();
		    	  jp_search.add(jp_search_north,BorderLayout.NORTH);
		    	  jp_search.add(jp_table, BorderLayout.CENTER);
		    	  jp_search.add(jb_ok, BorderLayout.SOUTH);
		    	  jp_search.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "���һ�Ա"));
		    	  jp_search.validate();
		    	  jp_search.repaint();
		    	  
		    	  addFrame.getContentPane().setLayout(new BorderLayout());
		    	  addFrame.getContentPane().add(jp_search, BorderLayout.CENTER);
		    	  addFrame.getContentPane().add(jb_ok, BorderLayout.SOUTH);
		    	  addFrame.getContentPane().validate();
		    	  addFrame.getContentPane().repaint();
		    	  addFrame.setVisible(true);
		    	  
			}
		});
		
		jb_startScanf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(vidVec.size()!=1){
					vidVec.clear();
					vidVec.addElement(new Integer(1000));//ɢ�ͱ��
					
				}
		
				jt_scanf.setVisible(true);
				jt_scanf.requestFocus();
				
			}
		});
		
		jb_addNewVip.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				   final JPanel j_addPanel=new JPanel();
				   final JFrame jf=new JFrame("��ӻ�Ա");
				   jt_vipid.setText("");
		    	   j_addPanel.removeAll();
		    	   addSet =new AddSet();
		    	   JButton j_getNewNum= (JButton)addSet.getObjByTxt("��Ա���");
		    	   j_getNewNum.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						isWorking=true;
						Connection conTodb=dBconncetion.getCon();
						Statement s;
						ResultSet  r;
						try {
							s = conTodb.createStatement();
							r=s.executeQuery(" select max(id) from "+ Connecter.TABLENAME_VIP );
							r.next();
							int newId= r.getInt(1);//ֻ��1��
							newId++;//�µĻ�Ա���
							JTextField  jtfield=new JTextField();
							jtfield.setEditable(false);//���ɱ༭
							jtfield.setText(newId+"");
							j_addPanel.removeAll();
							addSet.changeObjByTxt("��Ա���",jtfield);//�µ����
							addSet.addTOPanel(j_addPanel);
							j_addPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "��ӻ�Ա"));
							j_addPanel.validate();
							j_addPanel.repaint();
					
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, "sql���ʧ��.\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
							e1.printStackTrace();
							return;
						}
						
						
						
						
						isWorking=false;
					}
				});
		    	   addSet.addTOPanel(j_addPanel);//�����ȥ
		    	   
		    	   j_addPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "��ӻ�Ա"));
		    	   j_addPanel.validate();
		    	   j_addPanel.repaint();
		    	   JButton j_addButton=new JButton("����");
		    	   j_addButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						isWorking=true;
						insertIntoDb(j_addPanel,vidVec,jf,jl_name,jl_phone,jL_vipID);
						isWorking=false;
					}
				});
		    	   jf.getContentPane().setLayout(new BorderLayout());
		    	   jf.getContentPane().add(j_addPanel, BorderLayout.CENTER);
		    	   jf.getContentPane().add(j_addButton, BorderLayout.SOUTH);
		    	   jf.getContentPane().validate();
		    	   jf.getContentPane().repaint();
		    	   jf.setSize(400, 600);
		    	   jf.setLocation(600, 100);
		    	   jf.setVisible(true);
				
			}
		});
		
		jb_fianlButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(vidVec.size()!=1 || tableDateRowVec.size()<=0)return;//�޷�����
				final JFrame countframe=new JFrame("������ϸ");
				countframe.setSize(400, 400);
				countframe.setLocation(400, 100);
				
				
				JPanel jp=new JPanel(new GridLayout(6, 1));
				jp.add(new JLabel("��Ա���:"+vidVec.get(0)));
				jp.add(new JLabel("    ����:"+tableDateRowVec.size()));
				 int payMoney=0;
				for(int i=0;i<tableDateRowVec.size();i++){payMoney+=Integer.parseInt(tableDateRowVec.get(i).get(6));}//���߸��Ǽ۸�
				final int payMoneyAll=payMoney;
				jp.add(new JLabel("    �ܼ�:"+payMoney));
				JPanel jpR3=new JPanel(new GridLayout(1, 2));
				jpR3.add(new JLabel("ʵ��:"));
				final JTextField jt=new JTextField();
				jpR3.add(jt);
				jp.add(jpR3);//�����
//				JPanel jpR4=new JPanel(new GridLayout(1, 2));
//				jpR4.add(new JLabel("�Ż�:"));
//				final JTextField jt1=new JTextField();
//				jpR4.add(jt1);
//				jp.add(jpR4);
				JButton okButton=new JButton("ȷ�ϲ���Ʊ");
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try{	
							int  actalMoney=Integer.parseInt(jt.getText());
							System.out.println("actalmoney"+jt.getText());
							SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
							
								
								int vipid=vidVec.get(0);
								for(int i=0;i<tableDateRowVec.size();i++){
									//��¼��
									Connection con=dBconncetion.getCon();
									PreparedStatement s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_sell_insert));
									s.setString(2,tableDateRowVec.get(i).get(0));
									s.setInt(1, vipid);
									
									s.setTimestamp(3, Timestamp.valueOf(df.format(new Date())));
									s.executeUpdate();
									
									if(!s.isClosed())s.close();
									
									
									if(!s.isClosed())s.close();
									
									//��Ʒ��������һ
									s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_sell_reduceGoodsNum));
									s.setString(1, tableDateRowVec.get(i).get(0));
									s.executeUpdate();
									if(!s.isClosed())s.close();
									
								}
								
							//```````````````````����һ������ϸ�ڱ�
							PreparedStatement s=dBconncetion.getCon().prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_OnceDetial_insert));
							s.setInt(1, vipid);//����۸�
							s.setInt(2, actalMoney);//
							s.setTimestamp(3, Timestamp.valueOf(df.format(new Date())));
							s.executeUpdate();
							if(!s.isClosed())s.close();
							//���¼�¼��Ȼ��ӻ���
							s=dBconncetion.getCon().prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_sell_addScore));
							s.setInt(1, actalMoney);//����۸�
							s.setInt(2, vipid);
							s.executeUpdate();
							if(!s.isClosed())s.close();
							countframe.dispose();//���ٴ���
							//JOptionPane.showMessageDialog(null, "�ѽ���");
							//��ӡСƱ```````````````````````````````
							ticketPrint.clearBodyAndCustom();
							JTable jt= tableVec.elementAt(0);
							Statement sqls=dBconncetion.getCon().createStatement();
							ResultSet r1 = sqls.executeQuery("select fname,id,score from " + Connecter.TABLENAME_VIP + " where id= "+(int)vidVec.elementAt(0)+";");
							if(r1.next()){
								
								ticketPrint.customdetial.addElement(r1.getString(1)); //����
								ticketPrint.customdetial.addElement(r1.getInt(2)+"");//���
								ticketPrint.customdetial.addElement(r1.getInt(3)+"");//����
								ticketPrint.customdetial.addElement(payMoneyAll+"");//��Ǯ��
								ticketPrint.customdetial.addElement(actalMoney+"");//ʵ��Ǯ��
								ticketPrint.customdetial.addElement(""+(jt.getRowCount()));//�ܼ���
							}
							
							
							for(int i=0;i<jt.getRowCount();i++){
								Vector<String> v=new Vector<>();
								for(int j=0;j<jt.getColumnCount();j++){
									if(jt.getColumnName(j).equalsIgnoreCase(getChineseName("goodsid")) || jt.getColumnName(j).equalsIgnoreCase(getChineseName("goodsname")) || jt.getColumnName(j).equalsIgnoreCase(getChineseName("price")) ){
										//��Ʒ���ֺͱ��
								
										v.addElement((String)jt.getValueAt(i, j));
									       }
									}
								v.addElement(1+"");//����ǵ��ĸ���
								boolean has=false;
								for(Vector<String> vt:ticketPrint.body){
									if(vt.elementAt(0).equalsIgnoreCase(v.elementAt(0))){
										//֮ǰ����
										String am=vt.elementAt(3);//���ĸ�������
										am=""+(1+Integer.parseInt(am));
										vt.removeElementAt(3);//�Ƴ����ĸ�
										vt.addElement(am);//�ӽ�ȥ
										has=true;
										break;
									}	
	
								}
								if(has==false)ticketPrint.body.addElement(v);//�����µ�
								
							}
							
							ticketPrint.printTickets();
							
							
							
							//`````````````````````````````````````
							
							JOptionPane.showMessageDialog(null, "�ѽ���");
							//````````����
							
							
							lastone=null;
							actionAsSelected(getMoneyLable);
							

					}catch(SQLException e1){
								
								JOptionPane.showMessageDialog(null, "sql���ʧ��.\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
								e1.printStackTrace();
								return;
	
							}
						catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "����ȷ����ʵ��Ǯ��"+"\n����:"+e1.getMessage());											
							e1.printStackTrace();
							return;
						}
						

					}
				});
				jp.add(okButton);
				countframe.getContentPane().setLayout(new BorderLayout());
				countframe.getContentPane().add(jp, BorderLayout.CENTER);
				countframe.getContentPane().validate();
				countframe.getContentPane().repaint();
				countframe.setVisible(true);
				

				
				
				
				
			}
		});
		
		
		jb_resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isWorking==true)return;
				lastone=null;
				actionAsSelected(getMoneyLable);
				
				
			}
		});
		//�������
		
	
		JPanel jp_getMoney_north=new JPanel(new GridLayout(4, 1,0,3));
		
		//``````
		JPanel jp_getMoney_r1=new JPanel(new GridLayout(1, 3));
		JPanel jp_getMoney_r1_c1=new JPanel(new GridLayout(1, 2));
		jp_getMoney_r1_c1.add(new JLabel(" "));
		jp_getMoney_r1_c1.add(jl_vipidTitle);
		
	
		JPanel jp_getMoney_r1_c3=new JPanel(new GridLayout(1, 2));
		JPanel jp_getMoney_r1_c3_c1=new JPanel(new GridLayout(1, 2));
		jp_getMoney_r1_c3_c1.add(jb_searchVip);
		jp_getMoney_r1_c3_c1.add(jb_addNewVip);
		jp_getMoney_r1_c3.add(jp_getMoney_r1_c3_c1);
		jp_getMoney_r1_c3.add(new JLabel(" "));
		jp_getMoney_r1.add(jp_getMoney_r1_c1);
		jp_getMoney_r1.add(jt_vipid);
		jp_getMoney_r1.add(jp_getMoney_r1_c3);
		//````��һ��
		//`````````````````````````
		JPanel jp_getMoney_r2=new JPanel(new GridLayout(1, 3));
		JPanel jp_getMoney_r2_c1=new JPanel(new GridLayout(1, 2));
		jp_getMoney_r2_c1.add(new JLabel(" "));
		jp_getMoney_r2_c1.add(jL_vipID);
		JPanel jp_getMoney_r2_c2=new JPanel(new GridLayout(1, 2));
		jp_getMoney_r2_c2.add(new JLabel(" "));
		jp_getMoney_r2_c2.add(jl_name);
		JPanel jp_getMoney_r2_c3=new JPanel(new GridLayout(1, 2));
		jp_getMoney_r2_c3.add(new JLabel(" "));
		jp_getMoney_r2_c3.add(jl_phone);
		
		jp_getMoney_r2.add(jp_getMoney_r2_c1);
		jp_getMoney_r2.add(jp_getMoney_r2_c2);
		jp_getMoney_r2.add(jp_getMoney_r2_c3);
		jp_getMoney_r2.setBorder(new LineBorder(Color.black));
		//```````�ڶ���```````````````````
		//`````
		JPanel jp_getMoney_r3=new JPanel(new GridLayout(1, 3));
		jp_getMoney_r3.add(new JLabel("ɨ���:"));
		jp_getMoney_r3.add(jt_scanf);
	
		jp_getMoney_r3.add(new JLabel(" "));
		doc.addDocumentListener(new DocListener(jt_scanf, tableheaderNameVec, tableDateRowVec, js_tableDetialGoods, vidVec, tableVec,jp_getMoney_r3));
		//.``````������
		
		
		//``````
		JPanel jp_getMoney_r4=new JPanel(new GridLayout(1, 3));
		
		JPanel jp_getMoney_r4_c2=new JPanel(new GridLayout(1, 3));
		jp_getMoney_r4_c2.add(jb_startScanf);
		jp_getMoney_r4_c2.add(jb_delete);
		jp_getMoney_r4_c2.add(jb_resetButton);
		
		jp_getMoney_r4.add(new JLabel(" "));
		jp_getMoney_r4.add(jp_getMoney_r4_c2);
		jp_getMoney_r4.add(new JLabel(" "));
		
		//````````````````````````
		
		jp_getMoney_north.add(jp_getMoney_r1);
		jp_getMoney_north.add(jp_getMoney_r2);
		jp_getMoney_north.add(jp_getMoney_r3);
		jp_getMoney_north.add(jp_getMoney_r4);
		JPanel jp_south=new JPanel(new GridLayout(1, 3));
		jp_south.add(new JLabel(" "));
		jp_south.add(jb_fianlButton);
		jp_south.add(new JLabel(" "));
		
		jp_getMoney.setLayout(new BorderLayout());
		jp_getMoney.add(jp_getMoney_north, BorderLayout.NORTH);
		js_tableDetialGoods.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black),"��Ʒ��ϸ"));
		jp_getMoney.add(js_tableDetialGoods, BorderLayout.CENTER);
		jp_getMoney.add(jp_south, BorderLayout.SOUTH);
		jp_getMoney.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black),"��������"));
		jp_getMoney.validate();
		jp_getMoney.repaint();
		
		
	}
	
	public JTable amongSearch(JScrollPane jp,CheckboxGroup cgroup,Map<String, Checkbox> map,JTextField jTextField) {
		final JScrollPane jp_searchDetail=jp;
		final CheckboxGroup checkGroup=cgroup;
		
		final JTextField jt=jTextField;
		if( checkGroup.getSelectedCheckbox()==null){
			JOptionPane.showMessageDialog(null, "����:δѡ���ѯ��ʽ");
			return null;
		}
		if(jt.getText().equalsIgnoreCase("") || jt.getText()==null){
			JOptionPane.showMessageDialog(null, "����:����д��Ҫ��ѯ�ı�Ż�绰�����������ڵ���Ϣ\nע:������Ҫ�ð��.�ֿ�����:8.23");
			return null;
		}
		String checkboxString=cgroup.getSelectedCheckbox().getLabel();//�õ��ַ���
		java.sql.PreparedStatement s=null;
    	  ResultSet r=null;
    	  ResultSetMetaData tableheader=null;
    	  Vector<String> tableheaderNameVec=new Vector<String>();
    	  Vector<Vector<String>> tableDateRowVec=new Vector<>();
    	  Connection con=dBconncetion.getCon();
		  final JTable jTable=creatSearchTableByType(jt, checkboxString, s, r, tableheader, tableheaderNameVec, tableDateRowVec, con);
			  if(jTable==null){return null;}	
			
			  jp_searchDetail.setViewportView(jTable);
			  jp_searchDetail.setBorder(new LineBorder(Color.black));
			  jp.validate();
			  jp.repaint();
			  return jTable;
	}
	public JTable creatSearchTableByType(JTextField jt ,String checkboxString  , java.sql.PreparedStatement s, ResultSet r,ResultSetMetaData tableheader,Vector<String> tableheaderNameVec,Vector<Vector<String>> tableDateRowVec,Connection con) {
		 JTable jtable=null;
		try {
			
			if(checkboxString.equalsIgnoreCase(getChineseName("id"))){
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_searchby_id));
				s.setInt(1, Integer.parseInt(jt.getText()));
				r=s.executeQuery();//��ѯ��
				  tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				  if(tableColumNum<=0)return null;
				//  System.out.println(r);
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  //������ͷvec
				  while(r.next()){
					  //����һ��
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						  if(tableheaderNameVec.elementAt(j-1).equalsIgnoreCase("����")){
							  v.add(r.getString(j).substring(r.getString(j).indexOf("-")+"-".length()));
							  continue;
							  
						  }
						  v.add(r.getString(j));//��ӽ�ȥ							  
					  }
					  tableDateRowVec.add(v);
				  }
				  DefaultTableModel dtmod=new DefaultTableModel(tableDateRowVec, tableheaderNameVec);
				  jtable=new JTable(dtmod);//������
				  return jtable;
				
			}else if (checkboxString.equalsIgnoreCase(getChineseName("phone"))) {
				
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_searchby_phone));
//				s.setString(1, "%"+jt.getText());
				//	s.setString(2, jt.getText()+"%");
					s.setString(1,  "%"+jt.getText()+ "%");
					//s.setString(4, jt.getText());
				
				r=s.executeQuery();//��ѯ��
				  tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				 // System.out.println(r);
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  //������ͷvec
				  while(r.next()){
					  //����һ��
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						  if(tableheaderNameVec.elementAt(j-1).equalsIgnoreCase("����")){
							  v.add(r.getString(j).substring(r.getString(j).indexOf("-")+"-".length()));
							  continue;
							  
						  }
						  v.add(r.getString(j));//��ӽ�ȥ							  
					  }
					  tableDateRowVec.add(v);
				  }
				  DefaultTableModel dtmod=new DefaultTableModel(tableDateRowVec, tableheaderNameVec);
				  if(!r.isClosed())r.close();
					 if(!s.isClosed())s.close();//�ر�����
				  jtable=new JTable(dtmod);//������
				  return jtable;
				
				
				
			}else if (checkboxString.equalsIgnoreCase(getChineseName("fname"))) {
				System.out.println(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_searchby_name));
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_searchby_name));
			//	s.setString(1, "%"+jt.getText());
			//	s.setString(2, jt.getText()+"%");
				s.setString(1,  "%"+jt.getText()+ "%");
				//s.setString(4, jt.getText());
				r=s.executeQuery();//��ѯ��
			    tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				 // System.out.println(r);
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  //������ͷvec
				  while(r.next()){
					  //����һ��
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						  if(tableheaderNameVec.elementAt(j-1).equalsIgnoreCase("����")){
							  v.add(r.getString(j).substring(r.getString(j).indexOf("-")+"-".length()));
							  continue;
							  
						  }
						  v.add(r.getString(j));//��ӽ�ȥ							  
					  }
					  tableDateRowVec.add(v);
				  }
				  DefaultTableModel dtmod=new DefaultTableModel(tableDateRowVec, tableheaderNameVec);
				  if(!r.isClosed())r.close();
					 if(!s.isClosed())s.close();//�ر�����
				  jtable=new JTable(dtmod);//������
				  return jtable;
				
				
				
			}else if (checkboxString.equalsIgnoreCase(getChineseName("birthday"))) {	
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_searchby_birthday));
				s.setDate(1, java.sql.Date.valueOf(toDateString(jt.getText(), 1)));
				r=s.executeQuery();//��ѯ��
				  tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				 // System.out.println(r);
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  //������ͷvec
				  while(r.next()){
					  //����һ��
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						  if(tableheaderNameVec.elementAt(j-1).equalsIgnoreCase("����")){
							  v.add(r.getString(j).substring(r.getString(j).indexOf("-")+"-".length()));
							  continue;
							  
						  }
						  v.add(r.getString(j));//��ӽ�ȥ							  
					  }
					  tableDateRowVec.add(v);
				  }
				  DefaultTableModel dtmod=new DefaultTableModel(tableDateRowVec, tableheaderNameVec);
				  jtable=new JTable(dtmod);//������	
				  return jtable;
			}else if (checkboxString.equalsIgnoreCase(getChineseName("datejoin"))) {	
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_searchby_datejoin));
				System.out.println(toDateString(jt.getText(), 2));
				s.setDate(1, java.sql.Date.valueOf(toDateString(jt.getText(), 2)));
				r=s.executeQuery();//��ѯ��
				  tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				//  System.out.println(r);
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  //������ͷvec
				  while(r.next()){
					  //����һ��
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						  if(tableheaderNameVec.elementAt(j-1).equalsIgnoreCase("����")){
							  v.add(r.getString(j).substring(r.getString(j).indexOf("-")+"-".length()));
							  continue;
							  
						  }
						  v.add(r.getString(j));//��ӽ�ȥ							  
					  }
					  tableDateRowVec.add(v);
				  }
				  DefaultTableModel dtmod=new DefaultTableModel(tableDateRowVec, tableheaderNameVec);
				  if(!r.isClosed())r.close();
				  if(!s.isClosed())s.close();//�ر�����
				  jtable=new JTable(dtmod);//������	
				  return jtable;
			}
			else if (checkboxString.equalsIgnoreCase(getChineseName("score"))) {	
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_searchby_score));
				s.setInt(1, Integer.parseInt(jt.getText()));
				r=s.executeQuery();//��ѯ��
				  tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				//  System.out.println(r);
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  //������ͷvec
				  while(r.next()){
					  //����һ��
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						  if(tableheaderNameVec.elementAt(j-1).equalsIgnoreCase("����")){
							  v.add(r.getString(j).substring(r.getString(j).indexOf("-")+"-".length()));
							  continue;
							  
						  }
						  v.add(r.getString(j));//��ӽ�ȥ							  
					  }
					  tableDateRowVec.add(v);
				  }
				  DefaultTableModel dtmod=new DefaultTableModel(tableDateRowVec, tableheaderNameVec);
				  if(!r.isClosed())r.close();
				  if(!s.isClosed())s.close();//�ر�����
				  jtable=new JTable(dtmod);//������	
				  return jtable;
			}
			else {
				  if(!r.isClosed())r.close();
				  if(!s.isClosed())s.close();//�ر�����
				return null;
			}
			
			
			
			
			
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "sql���ʧ��.\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
			e1.printStackTrace();
			return null;
		}
		catch (NumberFormatException e1){
			JOptionPane.showMessageDialog(null, "����:��������ȷ����.\n�������:"+"\n����:"+e1.getMessage());											
			e1.printStackTrace();
			return null;
		}
		catch (IllegalArgumentException e1){
			JOptionPane.showMessageDialog(null, "����:���������ʽ.\n�������:"+"\n����:"+e1.getMessage());											
			e1.printStackTrace();
			return null;
		}	
		catch (Exception e1){
			JOptionPane.showMessageDialog(null, "����:���������ʽ.\n�������:"+"\n����:"+e1.getMessage());											
			e1.printStackTrace();
			return null;
		}	
		
		
	}
	
	
	
	
	
	class LabelListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			actionAsSelected((JLabel)e.getSource());//���з�Ӧ
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
		
	}
		
	class AddSet{
		public JLabel[] jlist;
	    public Object[] jObjlist;
		public AddSet() {
			// TODO Auto-generated constructor stub
			jlist=new JLabel[]{
					new JLabel("��Ա���"),
					new JLabel("����"),
					new JLabel("�绰"),
					new JLabel("����"),
					
			};
			
			jObjlist=new Object[]{
					new JButton("����"),
					new JTextField(),
					new JTextField(),
					new JTextField(),
					
					
			};
			
			
			
			
			
		}
		public Object getObjByTxt(String str) {			
			for(int i=0;i<jlist.length;i++){
				if(jlist[i].getText().equalsIgnoreCase(str)){
					
					return jObjlist[i];
				}				
			}
			
			return false;			
		}
		public void addTOPanel(JPanel jp) {
			//�������
			jp.removeAll();
			jp.setLayout(new GridLayout(jlist.length, 2));//
			for(int i=0;i<jlist.length;i++){
					jp.add((Component)jlist[i]);					
					jp.add((Component)jObjlist[i]);	
			}
			
			
			
			
			
			
			
			
		}
		public void changeObjByTxt(String str,Object obj) {
			
		
			for(int i=0;i<jlist.length;i++){
				if(jlist[i].getText().equalsIgnoreCase(str)){
					jObjlist[i]=obj;
					
					
				}
		}
		
		
		
		
	}
   }
	public void insertIntoDb(JPanel j_addPanel1,Vector<Integer> vipVec,JFrame jf,JLabel jl_name,JLabel jl_phone,JLabel   jL_vipID) {
		
		Vector<String> dateVec=new Vector<String>();
		Vector<String> columVec=new Vector<String>();
	   
	    JLabel[] jlist= addSet.jlist;
	    for(int i=0;i<jlist.length;i++){
	    	Object obj= addSet.getObjByTxt(jlist[i].getText());
	    	if(obj.getClass()==JButton.class){//û�л�ȡ
	    		  JOptionPane.showMessageDialog(null, "�������:δ��ȡ��Ա��ţ��޷�����");
	    		  return ;
	    		
	    	}else {
	    		if(jlist[i].getText().equalsIgnoreCase("����")){
	    			//������
	    			
	    			String orignstring=((JTextField)obj).getText();
	    			
	    			int index=orignstring.indexOf(".");
	    			if(index==-1){
	    				
	    				JOptionPane.showMessageDialog(null, "�������:�������ð��\".\"�ŷָ�");
	    				return ;
	    			}
	    			String birthday="2010-"+orignstring.substring(0, index)+"-"+orignstring.substring(index+".".length());
	    			columVec.addElement(getChineseName(jlist[i].getText()));//������
	    			dateVec.addElement(birthday);
	    			continue;
	    			
	    		}
	    		columVec.addElement(getChineseName(jlist[i].getText()));//������
				dateVec.addElement(((JTextField)obj).getText());
	    				    		
			}
	    	
	    }
	    SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd");
	    String joindate= df.format(new Date());//��������
	    //System.out.println(joindate);
	    columVec.addElement(getChineseName("��������"));//datejoin
	    dateVec.addElement(joindate);
	    columVec.addElement(getChineseName("����"));
	    dateVec.addElement("0");
	   
	    int dateValueCount=1;
	    int vipidIndex=-1;
	    String phone=null;
	    String name=null;
	    try {
			java.sql.PreparedStatement ps=dBconncetion.getCon().prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_insert));
			 for(int i=0;i<columVec.size();i++){
			    	if(getChineseName(columVec.elementAt(i)).equalsIgnoreCase("��Ա���") || getChineseName(columVec.elementAt(i)).equalsIgnoreCase("����")){
			    		
			    		ps.setInt(dateValueCount+i, Integer.parseInt(dateVec.elementAt(i)) );//��������ֵ
			    		if(getChineseName(columVec.elementAt(i)).equalsIgnoreCase("��Ա���")) {
			    			vipidIndex=i;

			    		}
			    	}
			    	else if(getChineseName(columVec.elementAt(i)).equalsIgnoreCase("����") || getChineseName(columVec.elementAt(i)).equalsIgnoreCase("��������")){
						
			    		ps.setDate(dateValueCount+i, java.sql.Date.valueOf(dateVec.elementAt(i)));
			    	
			    		
			    		
					}
			    	else {
			    		
			    		if(getChineseName(columVec.elementAt(i)).equalsIgnoreCase("�绰") )phone=dateVec.elementAt(i);
			    		if(getChineseName(columVec.elementAt(i)).equalsIgnoreCase("����") )name=dateVec.elementAt(i);
			    		ps.setString(dateValueCount+i,dateVec. elementAt(i));//�����ַ���ֵ
			    		
			    		
					}		    	
			    }
			 //�������
			 
			 if( ps.executeUpdate()==1){
				 //�����ˣ��ر�
				 vipVec.clear();
				 vipVec.addElement( Integer.parseInt(dateVec.elementAt(vipidIndex))); 
				 jl_name.setText("����:");
				 jl_phone.setText("�绰:");
				 jL_vipID.setText("���:");
				 jL_vipID.setText(jL_vipID.getText()+vipVec.elementAt(0));
					jl_name.setText(jl_name.getText()+name);
					jl_phone.setText(jl_phone.getText()+phone);
				 
				 
				 jf.dispose();
				 
				
			 }
			 else {
				JOptionPane.showMessageDialog(null, "���󣺼���ʧ�ܣ���ȷ�ϱ�ź͵绰�ѻ�ȡ");
				return;
			}
	    
	    
	    
	    } catch (SQLException e1) {
			// TODO Auto-generated catch block
	    	JOptionPane.showMessageDialog(null, "sql�������ʧ��.\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
			e1.printStackTrace();
			return;
		}		
		
	}
	
	class DocListener implements DocumentListener{
		public JTextArea jt_scanf;
		public Vector<String> tableheaderNameVec;
		public Vector<Vector<String>> tableDateRowVec;
		public JScrollPane js_tableDetialGoods;
		public Vector<Integer> vipId;
		public Vector<JTable> tableVec;
		public JPanel jp_getMoney_r3;
		public DocListener(JTextArea jt_scanf,Vector<String> tableheaderNameVec,Vector<Vector<String>> tableDateRowVec,JScrollPane js_tableDetialGoods,Vector<Integer> vipId,Vector<JTable> tableVec,JPanel jp_txtpanel) {
			this. jt_scanf=jt_scanf;
			this.tableheaderNameVec= tableheaderNameVec;
			this.js_tableDetialGoods=js_tableDetialGoods;
			this.tableDateRowVec=tableDateRowVec;
			this.vipId=vipId;
			this.tableVec=tableVec;
			this.jp_getMoney_r3=jp_txtpanel;
		}
		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			try {
				
				
				int strsign=10;//�س�
				if(jt_scanf.getText().indexOf(strsign)!=-1){
					//������\r\n
				    String goodsid=	jt_scanf.getText().substring(0, jt_scanf.getText().indexOf(strsign));//�ҵ���Ʒid
					Connection con=dBconncetion.getCon();
					Statement s=con.createStatement();
					String sqlstr="select * from "+ Connecter.TABLENAME_GOODS+ " where goodsid=" + goodsid+" ;" ;
					ResultSet r=null;
					ResultSetMetaData tableheader=null;
					tableheaderNameVec.clear();
					//tableDateRowVec.clear();
					JTable jt_goodsdetial=  creatJtable(sqlstr, s, null, r, tableheader, tableheaderNameVec, tableDateRowVec, con);
					if(jt_goodsdetial==null){
						
						
						return ;
					}
					tableVec.clear();
					tableVec.add(jt_goodsdetial);//�±�
					jt_goodsdetial.setCellSelectionEnabled(false);
					jt_goodsdetial.setRowSelectionAllowed(true);
					jt_goodsdetial.setRowSelectionInterval(tableDateRowVec.size()-1, tableDateRowVec.size()-1);//���һ��
					js_tableDetialGoods.setViewportView(jt_goodsdetial);
					js_tableDetialGoods.validate();
					js_tableDetialGoods.repaint();
					JTextArea  jnew=new JTextArea();
					jnew.setText("");
					Document doc= jnew.getDocument();
					doc.addDocumentListener(new DocListener(jnew, tableheaderNameVec, tableDateRowVec, js_tableDetialGoods, vipId, tableVec, jp_getMoney_r3));
				    // jp_getMoney_r3=new JPanel(new GridLayout(1, 3));
					jp_getMoney_r3.removeAll();
					jp_getMoney_r3.setLayout(new GridLayout(1, 3));
					jp_getMoney_r3.add(new JLabel("ɨ���:"));
					
					
					jp_getMoney_r3.add(jnew);
					
					jp_getMoney_r3.add(new JLabel(" "));
					jp_getMoney_r3.validate();
					jp_getMoney_r3.repaint();
					jnew.setVisible(true);
					jnew.requestFocus();
					
				}
				
				
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "sql�������ʧ��\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
				e1.printStackTrace();
				
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
		
		
		
	}
	
	@Override
	public void setlastOne(JLabel jl){
		lastone=jl;
		
	}
	@Override
    public JLabel getlastOne(){
		return lastone;
		
	}
	
	
	class Printer implements Printable{

		public Vector<String> header;//��ͷ����������Ϣ
		public  Vector<Vector<String>> body;   //��Ʒ��Ϣ����
		public Vector<String> customdetial;//�ͻ���Ϣ�����3�У���ʾ��Ϣ+id
		int maxPoint=165;//������ô��ĵ�
		
		
		public Printer() {

		body=new Vector<>();//��������		
		header=new Vector<>();
		customdetial=new Vector<>();
		header.add("-----------------------");
		header.add("��ӭ���ٰ���ʫޱ");
		header.add("����������������������������������������������");
		//��ͷ��Ӧ���е���Ϣ
		
		
		
			
			
		}
		public void clearBodyAndCustom() {
			body.clear();
			customdetial.clear();
		}
		
		@Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
			// 
			if(body.size()<=0 || customdetial.size()<=0)return 0; //û�м�¼
			
			
			// Component c = null;  
		        // ת����Graphics2D �õ�����  
		       Graphics2D g2 = (Graphics2D) graphics;  
		        // ���ô�ӡ��ɫΪ��ɫ  
		        g2.setColor(Color.black);  
		  
		        // ��ӡ�������  
		        double x = pageFormat.getImageableX();  
		        double y = pageFormat.getImageableY();  
		        // ����  
		        float[] dash1 = { 4.0f };  
		     
		        g2.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, dash1, 0.0f)); 
		        SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd HH:mm");
		        Font titlefont=new Font("����", Font.BOLD, 14);//20��С
		        g2.setFont(titlefont);
		        float height=titlefont.getSize2D();
		        
		        
		        g2.drawString("*��ӭ����ĭ�򻨿�*", (float)x, (float)y+height);
		        float lineY=2*height; //��һ��������
		        
		        Font font2=new Font("����", Font.PLAIN, 12);//12��С
		        g2.setFont(font2);
		        float fontHeight=font2.getSize2D();
		        g2.drawString("===============", (float)x,(float) y+lineY);
		        lineY+=fontHeight;//����ƫ��
		        g2.drawString("��Ա����:"+customdetial.elementAt(0), (float)x, lineY+(float)y);//x,yΪ��ʼ��
		        lineY+=fontHeight;//����ƫ��
		        g2.drawString("��Ա���:"+customdetial.elementAt(1), (float)x, lineY+(float)y);//x,yΪ��ʼ��
		        lineY+=fontHeight;//����ƫ��
		        g2.drawString("��Ա����:"+customdetial.elementAt(2), (float)x, lineY+(float)y);//x,yΪ��ʼ��
		        lineY+=fontHeight;//����ƫ��
		        g2.drawString("ʱ��:"+df.format(new Date()), (float)x, lineY+(float)y);//x,yΪ��ʼ��
		        lineY+=fontHeight;//����ƫ��
		        g2.drawString("===============", (float)x,(float) y+lineY);
		        lineY+=fontHeight;//����ƫ��
		        
		        g2.drawString("��Ʒ", (float)x,(float) y+lineY);
		        g2.drawString("�۸�", (float)x+(float)maxPoint/3,(float) y+lineY);
		        g2.drawString("����", (float)x+(float)maxPoint*2/3,(float) y+lineY);
		        
		        lineY+=fontHeight;//����ƫ��
		        
		        
		        g2.drawString("===============", (float)x,(float) y+lineY);
		        font2=new Font("����", Font.PLAIN, 10);//12��С
		        g2.setFont(font2);
		        fontHeight=font2.getSize2D();
		        lineY+=fontHeight;//����ƫ��
		        //`````````` ``````````��ʼ���ƾ�����Ϣ�����½��
		        for(Vector<String> v:body){
		        	    g2.drawString(v.elementAt(1), (float)x,(float) y+lineY);
				        g2.drawString(v.elementAt(2), (float)x+(float)maxPoint/3,(float) y+lineY);
				        g2.drawString(v.elementAt(3), (float)x+(float)maxPoint*2/3,(float) y+lineY);
				        lineY+=fontHeight;//����ƫ��

		        }
		        
		        //ϸ�ڻ������
		        //`````````````````````����Ϣ``````````````````
		        
		        g2.drawString("===============", (float)x,(float) y+lineY);
		        font2=new Font("����", Font.PLAIN, 15);
		        fontHeight=font2.getSize2D();
		        g2.setFont(font2);//��������
		        lineY+=fontHeight;//����ƫ��
		        g2.drawString("�ܼ���:"+customdetial.elementAt(5), (float)x,(float) y+lineY);
		        lineY+=fontHeight;//����ƫ��
		        g2.drawString("�ܼ�:"+customdetial.elementAt(3), (float)x,(float) y+lineY);
		        lineY+=fontHeight;//����ƫ��
		        g2.drawString("ʵ��:"+customdetial.elementAt(4), (float)x,(float) y+lineY);
		        lineY+=fontHeight;//����ƫ��
		        if((Integer.parseInt(customdetial.elementAt(4))-Integer.parseInt(customdetial.elementAt(3)))<0){
		        	//�Ż�
		        	  g2.drawString("�Ż�:"+(Integer.parseInt(customdetial.elementAt(3))-Integer.parseInt(customdetial.elementAt(4))), (float)x,(float) y+lineY);
				        lineY+=fontHeight;//����ƫ��
		        	
		        }
		        
		        
		       else {
		    	   g2.drawString("�Ż�:"+(Integer.parseInt(customdetial.elementAt(4))-Integer.parseInt(customdetial.elementAt(3))), (float)x,(float) y+lineY);
			        lineY+=fontHeight;//����ƫ��
				
		       	}
		        
		        g2.drawString("===============", (float)x,(float) y+lineY);
		        font2=new Font("����", Font.BOLD, 20);
		        fontHeight=font2.getSize2D();
		        g2.setFont(font2);//��������
		        lineY+=fontHeight;//����ƫ��
		        
		        g2.drawString("**лл�ݹ�!**", (float)x,(float) y+lineY);
		        
		        
		        return 0;
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
		
		public void printTickets(){
			try {
				Book book=new Book();
				Paper paper=new Paper();
				paper.setSize(maxPoint, maxPoint*3+10*(body.size()+1));//�����Ǳ䶯��
				paper.setImageableArea(0, 0, maxPoint, maxPoint*3+10*(body.size()+1));
				PageFormat pageFormat=new PageFormat();
				pageFormat.setOrientation(PageFormat.PORTRAIT);
				pageFormat.setPaper(paper);
				book.append(this, pageFormat);
				PrinterJob pjob=PrinterJob.getPrinterJob();
				pjob.setPageable(book);
				pjob.print();
				System.out.println("printer");
			} catch (PrinterException e) {
				JOptionPane.showMessageDialog(null, "���󣺴�ӡ����\n����:"+e.getMessage());
				return;
			}
			
			
			
			
			
			
			
		}
		
		
		
		
		
		
	}
	
	
	
	
}


