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

	private JButton sellSectionButton;//选择按钮
	private JLabel  totalviewLable;	//收入浏览标签
	private JLabel  getMoneyLable;	//收银标签
	private AddSet  addSet;
	private Connecter dBconncetion;//数据库连接类
	private final int mode=ActionChangeService.MODE_SELL;//该模式的编号
	private JPanel	detPanle;//工作面板
	private JPanel	basePanle;//工作根面板
	private boolean isWorking;//是否开始
	private JLabel lastone;//上一个点击的label
	public SellManger(JPanel basepanel,Connecter dbconnect) {
		basePanle=basepanel;
		detPanle=new JPanel();
		isWorking=false;
		lastone=null;
		dBconncetion=dbconnect;
		sellSectionButton=new JButton("收银管理");
		totalviewLable=new JLabel("收入总览");
		totalviewLable.addMouseListener(new LabelListener());
		getMoneyLable=new JLabel("收银界面");
		getMoneyLable.addMouseListener(new LabelListener());	
		super.setFirstCount(true);//是第一次
	}
	
	@Override
	public int  getModeState () {
		return mode;
		
	}
	@Override
	public void  actionAsSelected(JLabel thisone) {
		if(isWorking==true || lastone==thisone || thisone==null)
		{
		basePanle.removeAll();//基本面板都移除
		basePanle.add(detPanle);//
		basePanle.validate();
		basePanle.repaint();
		return;}//必须没有开始工作且上一个不是本身,只进行简单放上去
        isWorking=true;//开始工作
//```````````````````````````````````
        if(thisone==totalviewLable){//```````````````````````总览模式````````````````
        	  thisone.setForeground(Color.BLUE);
	    	  basePanle.removeAll();//基本面板都移除
	    	  detPanle.removeAll();//该工作面板移除
	    	  //```````````````````````````
	    	 final  JPanel allCompone=new JPanel();//所有组件放的位置
	    	 final  JPanel jp_north_total=new JPanel();//总流水
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
        	
        }else if (thisone==getMoneyLable) {//`````````````````收银模式`````````````````````````````
        	  thisone.setForeground(Color.BLUE);
	    	  basePanle.removeAll();//基本面板都移除
	    	  detPanle.removeAll();//该工作面板移除
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
			final JLabel jLabel_start=new JLabel("起始时间");
			final JLabel jLabel_end=new JLabel("结束时间:");
			final JButton jb_search=new JButton("查询");
			final JButton jb_export=new JButton("导出");
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
						JOptionPane.showMessageDialog(null, "错误:sql错误.\n错误代码:"+"\n详情:"+e1.getMessage());											
						e1.printStackTrace();
						return ;
					}
					
					catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "错误:请检测输入格式.\n错误代码:"+"\n详情:"+e1.getMessage());											
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
			jp_west_search.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black),"流水查询"));
			
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
	  	  JLabel jLabel_allmoney=new JLabel("总消费金额:");
	  	  JLabel jLabel_allVipMoney=new JLabel("会员总消费金额:");
	  	  JLabel jLabel_allnormalMoney=new JLabel("散客总消费金额:");
	  	  JLabel jLabel_totalClothes=new JLabel("总流水商品数额:");
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
					
					jLabel_allnormalMoney.setText(jLabel_allnormalMoney.getText()+(totalmoney-totalvipMoney)+"元");
					jLabel_allVipMoney.setText(jLabel_allVipMoney.getText()+totalvipMoney+"元");
					jLabel_allmoney.setText(jLabel_allmoney.getText()+totalmoney+"元");
					jLabel_totalClothes.setText(jLabel_totalClothes.getText()+totalClothes+"件");
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
				
				jp_north.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black),"总流水"));
				jp_north.validate();
				jp_north.repaint();
				
				
				
				
				
				if(!r.isClosed())r.close();
				if(!s.isClosed())s.close();
				
			} catch (SQLException e1) {
			
				JOptionPane.showMessageDialog(null, "sql语句失败,请填写正确会员编号.\n错误代码:"+e1.getErrorCode()+"\n详情:"+e1.getMessage());											
				e1.printStackTrace();
				return;
	
			}
		
		
		
		
		
		
	}
	public void totalViewCenterPaint(JPanel jp,JScrollPane jp_table){
		final JPanel   jp_today=jp;
		 PreparedStatement s1;
	
	  	  ResultSet r=null;
	  	  Connection con=dBconncetion.getCon();
	  	  JLabel jLabel_allmoney=new JLabel(      "今日消费金额            : ");
	  	  JLabel jLabel_allVipMoney=new JLabel(   "今日会员总消费金额  : ");
	  	  JLabel jLabel_allnormalMoney=new JLabel("今日散客总消费金额  : ");
	  	  JLabel jLabel_totalClothes=new JLabel(  "今日流水商品数量	 : ");
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
					jLabel_allnormalMoney.setText(jLabel_allnormalMoney.getText()+(totalmoney-totalvipMoney)+"元");
					jLabel_allVipMoney.setText(jLabel_allVipMoney.getText()+totalvipMoney+"元");
					jLabel_allmoney.setText(jLabel_allmoney.getText()+totalmoney+"元");
					jLabel_totalClothes.setText(jLabel_totalClothes.getText()+totalClothes+"件");
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
				
				jp_north.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black),"今日流水"));
				
				
				jp_today.setLayout(new BorderLayout());
				jp_today.add(jp_north,BorderLayout.NORTH);
				jp_today.add(jp_table, BorderLayout.CENTER);
				jp_today.validate();
				jp_today.repaint();
	
				
			} catch (SQLException e1) {
			
				JOptionPane.showMessageDialog(null, "sql语句失败,请填写正确会员编号.\n错误代码:"+e1.getErrorCode()+"\n详情:"+e1.getMessage());											
				e1.printStackTrace();
				return;
	
			}
			 catch (Exception e1) {
					
					JOptionPane.showMessageDialog(null, "错误:请正确输入"+"\n详情:"+e1.getMessage());											
					e1.printStackTrace();
					return;
		
				}
		
		
		
		
		
		
		
		
	}
	public JTable	creatJtable(String sql, Statement s,java.sql.PreparedStatement s1, ResultSet r,ResultSetMetaData tableheader,Vector<String> tableheaderNameVec,Vector<Vector<String>> tableDateRowVec,Connection con) {
		try {
			
			JTable jtable=null;
			if(sql==null){
				//预定义的语句
				
				r=s1.executeQuery();
				tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				//  System.out.println(r);
				  if(tableColumNum<=0)return null;
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  //建立表头vec
				  while(r.next()){
					  //有下一行
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						  if(tableheaderNameVec.elementAt(j-1).equalsIgnoreCase("生日")){
							  v.add(r.getString(j).substring(r.getString(j).indexOf("-")+"-".length()));
							  continue;
							  
						  }
						  v.add(r.getString(j));//添加进去							  
					  }
					  tableDateRowVec.add(v);
				  				}
				  DefaultTableModel dtmod=new DefaultTableModel(tableDateRowVec, tableheaderNameVec);
				  jtable=new JTable(dtmod);//表格完成	
				  return jtable;

								}
			else if(sql!=null){
				r=s.executeQuery(sql);
				tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				//  System.out.println(r);
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  //建立表头vec
				  while(r.next()){
					  //有下一行
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						  if(tableheaderNameVec.elementAt(j-1).equalsIgnoreCase("生日")){
							  v.add(r.getString(j).substring(r.getString(j).indexOf("-")+"-".length()));
							  continue;
							  
						  }
						  v.add(r.getString(j));//添加进去							  
					  }
					  tableDateRowVec.add(v);
				  				}
				  DefaultTableModel dtmod=new DefaultTableModel(tableDateRowVec, tableheaderNameVec);
				  jtable=new JTable(dtmod);//表格完成	
				  return jtable;

				
				
				
			}
			else  return null;
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "sql语句失败,请填写正确会员编号.\n错误代码:"+e1.getErrorCode()+"\n详情:"+e1.getMessage());											
			e1.printStackTrace();
			return null;
			
			
			
			
			
		}
		catch (Exception e1) {
			
			JOptionPane.showMessageDialog(null, "错误:请检测输入格式.\n错误代码:"+"\n详情:"+e1.getMessage());											
			e1.printStackTrace();
			return null;

		}
		
		
		
		
		
	}
	
	public void totalViewEastPaint(JPanel jp,JScrollPane jp_table){
		final JPanel j_thismonth=jp;
		 PreparedStatement s1;
			
			  	  ResultSet r=null;
			  	  Connection con=dBconncetion.getCon();
			  	  JLabel jLabel_allmoney=new JLabel(      "本月消费金额                 :");
			  	  JLabel jLabel_allVipMoney=new JLabel(   "本月会员总消费金额       :");
			  	  JLabel jLabel_allnormalMoney=new JLabel("本月散客总消费金额       :");
			  	  JLabel jLabel_totalClothes=new JLabel(  "本月流水商品数量	    :");
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
			  	  c.set(Calendar.MILLISECOND, 0);//下月减一天为本月最后一天
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
							
							jLabel_allnormalMoney.setText(jLabel_allnormalMoney.getText()+(totalmoney-totalvipMoney)+"元");
							jLabel_allVipMoney.setText(jLabel_allVipMoney.getText()+totalvipMoney+"元");
							jLabel_allmoney.setText(jLabel_allmoney.getText()+totalmoney+"元");
							jLabel_totalClothes.setText(jLabel_totalClothes.getText()+totalClothes+"件");
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
						
						jp_north.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black),"本月流水"));
						 j_thismonth.setLayout(new BorderLayout());
						 j_thismonth.add(jp_north,BorderLayout.NORTH);
						 j_thismonth.add(jp_table, BorderLayout.CENTER);
						 j_thismonth.validate();
						 j_thismonth.repaint();
			
						
					} catch (SQLException e1) {
					
						JOptionPane.showMessageDialog(null, "sql语句失败,请填写正确会员编号.\n错误代码:"+e1.getErrorCode()+"\n详情:"+e1.getMessage());											
						e1.printStackTrace();
						return;
			
					}
				
				
		
		
		
		
		
		
		
		
	}
	public void  getMoneyPanel_Paint(JPanel jp_north1) {
		final JPanel jp_getMoney=jp_north1;
		final JButton jb_fianlButton=new JButton("结算");
		final JScrollPane js_tableDetialGoods=new JScrollPane();
	//	final JLabel jl_grap=new JLabel(" ");
		final JLabel jl_vipidTitle=new JLabel("会员电话:");
		final JTextField jt_vipid=new JTextField();
		final JButton jb_searchVip=new JButton("查找");
		final JButton jb_addNewVip=new JButton("新添");
	//	final JLabel jl_grap1=new JLabel(" ");
	//``````````````````````````````````````````````

		final Vector<Integer> vidVec=new Vector<Integer>();
		vidVec.clear();
		//vidVec.setSize(1);
		final JLabel jL_vipID=new JLabel("编号:");
		final JLabel jl_phone=new JLabel("电话:");
		final JLabel jl_name=new JLabel("姓名:");
		
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
					tableDateRowVec.removeElementAt(jtable_detial.getSelectedRow());//从行向量里一并删除
					
					
					
					
				}
				
			
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
				
			}
		});
		
		
		
		final JButton jb_startScanf=new JButton("开始扫描");
		final JButton jb_delete=new JButton("删除");
		final JButton jb_resetButton=new JButton("重置");
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
		
				if(tableDateRowVec.size()!=0 )jtable_detial.setRowSelectionInterval(tableDateRowVec.size()-1, tableDateRowVec.size()-1);//最后一行
				
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
							 jl_name.setText("姓名:");
							 jl_phone.setText("电话:");
							 jL_vipID.setText("编号:");
							 jl_name.setText(jl_name.getText()+r.getString(1));
							 jl_phone.setText(jl_phone.getText()+r.getString(2));
							 jL_vipID.setText(jL_vipID.getText()+r.getInt(3));
							 jp_getMoney.validate();
							 jp_getMoney.repaint();
							 vidVec.clear();
							 vidVec.addElement(new Integer(r.getInt(3)));
						 
						 }
						 else {
							
							 jl_name.setText("姓名:");
							 jl_phone.setText("电话:");
							 jL_vipID.setText("编号:");
							 jp_getMoney.validate();
							 jp_getMoney.repaint();
							 vidVec.clear();
							 
							 
						}
						 
						 
						
						
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "sql语句失败,请填写正确会员编号.\n错误代码:"+e1.getErrorCode()+"\n详情:"+e1.getMessage());											
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
		    	  final JScrollPane jp_table=new JScrollPane();//表的
		    	  JLabel jl_wayTosearch=new JLabel("查找方式:");
		    	  final CheckboxGroup checkboxGroup=new CheckboxGroup();
		    	  final Map<String, Checkbox> boxmap=new HashMap<String,Checkbox>();
		    	  boxmap.put("会员编号", new Checkbox("会员编号",checkboxGroup,true));
		    	  boxmap.put("电话", new Checkbox("电话", checkboxGroup,false));
		    	  boxmap.put("姓名", new Checkbox("姓名", checkboxGroup,false));
		    	  boxmap.put("生日", new Checkbox("生日", checkboxGroup,false));
		    	  boxmap.put("加入日期", new Checkbox("加入日期", checkboxGroup,false));
		    	  boxmap.put("积分", new Checkbox("积分", checkboxGroup,false));
		    	  final JFrame addFrame=new JFrame("查找会员");
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
							if(jt.getSelectedRow()==-1){//没有选
								int  state=JOptionPane.showConfirmDialog(null, "未选择会员,是否返回选择");
								if(state==JOptionPane.CANCEL_OPTION || state==JOptionPane.OK_OPTION){
								      return;

								}else {
									vidVec.clear();
									((JFrame)e.getSource()).dispose();//没有添加
								}
								
							}else {
								vidVec.clear();
								vidVec.addElement(new Integer(Integer.parseInt((String)jt.getValueAt(jt.getSelectedRow(), 1))));
								((JFrame)e.getSource()).dispose();
							}
							
							
						}else {
							int  state=JOptionPane.showConfirmDialog(null, "未选择会员,是否返回选择");
							if(state==JOptionPane.CANCEL_OPTION || state==JOptionPane.OK_OPTION){
								//返回继续
								return;
							}
							else {
								vidVec.clear();
								((JFrame)e.getSource()).dispose();//没有添加
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
		    	
		    	  JLabel jl_title=new JLabel("输入:");
		    	   final  JTextField jt=new JTextField();
		    	   final JButton jb_ok=new JButton("确定");
		    	   
		    	  jb_ok.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if(jTableVec.size()!=0){
							//System.out.println(jTableVec);
							JTable jt=jTableVec.get(0);
							if(jt.getSelectedRow()==-1){//没有选
								int  state=JOptionPane.showConfirmDialog(null, "未选择会员,是否返回选择");
								if(state==JOptionPane.YES_OPTION || state==JOptionPane.CANCEL_OPTION){
								      return;

								}else {
									vidVec.clear();
									addFrame.dispose();//没有添加
									jl_name.setText("姓名:");
									 jl_phone.setText("电话:");
									 jL_vipID.setText("编号:");
									 jp_getMoney.validate();
									 jp_getMoney.repaint();
									return;
								}
								
							}else {
								vidVec.clear();
								vidVec.addElement(new Integer(Integer.parseInt((String)jt.getValueAt(jt.getSelectedRow(), 0))));//ID,电话，姓名
								jl_name.setText("姓名:");
								 jl_phone.setText("电话:");
								 jL_vipID.setText("编号:");
								jL_vipID.setText(jL_vipID.getText()+vidVec.elementAt(0));
								jl_name.setText(jl_name.getText()+jt.getValueAt(jt.getSelectedRow(),2 ));
								jl_phone.setText(jl_phone.getText()+jt.getValueAt(jt.getSelectedRow(),1 ));
								addFrame.dispose();
							}
							
							
						}else {
							int  state=JOptionPane.showConfirmDialog(null, "未选择会员,是否返回选择");
							if(state==JOptionPane.YES_OPTION || state==JOptionPane.CANCEL_OPTION){
								//返回继续
								return;
							}
							else {
								vidVec.clear();
								addFrame.dispose();//没有添加
								jl_name.setText("姓名:");
								 jl_phone.setText("电话:");
								 jL_vipID.setText("编号:");
								 jp_getMoney.validate();
								 jp_getMoney.repaint();
								return;
							}
							
						}
						
						
						
					}
				});
		    	  JButton jb_search=new JButton("查询");
		    	  jb_search.addActionListener(new ActionListener() {				
					@Override
					public void actionPerformed(ActionEvent e) {
					   JTable jtab=	amongSearch(jp_table,checkboxGroup,boxmap,jt);//画个表格
					   jTableVec.clear();
					   jTableVec.addElement(jtab);
					}
				});
		    	 
		    	  //````加面板
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
		    	  jp_search.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "查找会员"));
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
					vidVec.addElement(new Integer(1000));//散客编号
					
				}
		
				jt_scanf.setVisible(true);
				jt_scanf.requestFocus();
				
			}
		});
		
		jb_addNewVip.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				   final JPanel j_addPanel=new JPanel();
				   final JFrame jf=new JFrame("添加会员");
				   jt_vipid.setText("");
		    	   j_addPanel.removeAll();
		    	   addSet =new AddSet();
		    	   JButton j_getNewNum= (JButton)addSet.getObjByTxt("会员编号");
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
							int newId= r.getInt(1);//只有1列
							newId++;//新的会员编号
							JTextField  jtfield=new JTextField();
							jtfield.setEditable(false);//不可编辑
							jtfield.setText(newId+"");
							j_addPanel.removeAll();
							addSet.changeObjByTxt("会员编号",jtfield);//新的组件
							addSet.addTOPanel(j_addPanel);
							j_addPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "添加会员"));
							j_addPanel.validate();
							j_addPanel.repaint();
					
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, "sql语句失败.\n错误代码:"+e1.getErrorCode()+"\n详情:"+e1.getMessage());											
							e1.printStackTrace();
							return;
						}
						
						
						
						
						isWorking=false;
					}
				});
		    	   addSet.addTOPanel(j_addPanel);//加入进去
		    	   
		    	   j_addPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "添加会员"));
		    	   j_addPanel.validate();
		    	   j_addPanel.repaint();
		    	   JButton j_addButton=new JButton("加入");
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
				if(vidVec.size()!=1 || tableDateRowVec.size()<=0)return;//无法结算
				final JFrame countframe=new JFrame("结算明细");
				countframe.setSize(400, 400);
				countframe.setLocation(400, 100);
				
				
				JPanel jp=new JPanel(new GridLayout(6, 1));
				jp.add(new JLabel("会员编号:"+vidVec.get(0)));
				jp.add(new JLabel("    件数:"+tableDateRowVec.size()));
				 int payMoney=0;
				for(int i=0;i<tableDateRowVec.size();i++){payMoney+=Integer.parseInt(tableDateRowVec.get(i).get(6));}//第七个是价格
				final int payMoneyAll=payMoney;
				jp.add(new JLabel("    总计:"+payMoney));
				JPanel jpR3=new JPanel(new GridLayout(1, 2));
				jpR3.add(new JLabel("实收:"));
				final JTextField jt=new JTextField();
				jpR3.add(jt);
				jp.add(jpR3);//新面板
//				JPanel jpR4=new JPanel(new GridLayout(1, 2));
//				jpR4.add(new JLabel("优惠:"));
//				final JTextField jt1=new JTextField();
//				jpR4.add(jt1);
//				jp.add(jpR4);
				JButton okButton=new JButton("确认并打票");
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try{	
							int  actalMoney=Integer.parseInt(jt.getText());
							System.out.println("actalmoney"+jt.getText());
							SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
							
								
								int vipid=vidVec.get(0);
								for(int i=0;i<tableDateRowVec.size();i++){
									//记录帐
									Connection con=dBconncetion.getCon();
									PreparedStatement s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_sell_insert));
									s.setString(2,tableDateRowVec.get(i).get(0));
									s.setInt(1, vipid);
									
									s.setTimestamp(3, Timestamp.valueOf(df.format(new Date())));
									s.executeUpdate();
									
									if(!s.isClosed())s.close();
									
									
									if(!s.isClosed())s.close();
									
									//商品数量件数一
									s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_sell_reduceGoodsNum));
									s.setString(1, tableDateRowVec.get(i).get(0));
									s.executeUpdate();
									if(!s.isClosed())s.close();
									
								}
								
							//```````````````````更新一次消费细节表
							PreparedStatement s=dBconncetion.getCon().prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_OnceDetial_insert));
							s.setInt(1, vipid);//加入价格
							s.setInt(2, actalMoney);//
							s.setTimestamp(3, Timestamp.valueOf(df.format(new Date())));
							s.executeUpdate();
							if(!s.isClosed())s.close();
							//更新记录，然后加积分
							s=dBconncetion.getCon().prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_sell_addScore));
							s.setInt(1, actalMoney);//加入价格
							s.setInt(2, vipid);
							s.executeUpdate();
							if(!s.isClosed())s.close();
							countframe.dispose();//销毁窗口
							//JOptionPane.showMessageDialog(null, "已结账");
							//打印小票```````````````````````````````
							ticketPrint.clearBodyAndCustom();
							JTable jt= tableVec.elementAt(0);
							Statement sqls=dBconncetion.getCon().createStatement();
							ResultSet r1 = sqls.executeQuery("select fname,id,score from " + Connecter.TABLENAME_VIP + " where id= "+(int)vidVec.elementAt(0)+";");
							if(r1.next()){
								
								ticketPrint.customdetial.addElement(r1.getString(1)); //姓名
								ticketPrint.customdetial.addElement(r1.getInt(2)+"");//编号
								ticketPrint.customdetial.addElement(r1.getInt(3)+"");//积分
								ticketPrint.customdetial.addElement(payMoneyAll+"");//总钱数
								ticketPrint.customdetial.addElement(actalMoney+"");//实收钱数
								ticketPrint.customdetial.addElement(""+(jt.getRowCount()));//总件数
							}
							
							
							for(int i=0;i<jt.getRowCount();i++){
								Vector<String> v=new Vector<>();
								for(int j=0;j<jt.getColumnCount();j++){
									if(jt.getColumnName(j).equalsIgnoreCase(getChineseName("goodsid")) || jt.getColumnName(j).equalsIgnoreCase(getChineseName("goodsname")) || jt.getColumnName(j).equalsIgnoreCase(getChineseName("price")) ){
										//商品名字和编号
								
										v.addElement((String)jt.getValueAt(i, j));
									       }
									}
								v.addElement(1+"");//这就是第四个了
								boolean has=false;
								for(Vector<String> vt:ticketPrint.body){
									if(vt.elementAt(0).equalsIgnoreCase(v.elementAt(0))){
										//之前就有
										String am=vt.elementAt(3);//第四个是数量
										am=""+(1+Integer.parseInt(am));
										vt.removeElementAt(3);//移除第四个
										vt.addElement(am);//加进去
										has=true;
										break;
									}	
	
								}
								if(has==false)ticketPrint.body.addElement(v);//加入新的
								
							}
							
							ticketPrint.printTickets();
							
							
							
							//`````````````````````````````````````
							
							JOptionPane.showMessageDialog(null, "已结账");
							//````````重置
							
							
							lastone=null;
							actionAsSelected(getMoneyLable);
							

					}catch(SQLException e1){
								
								JOptionPane.showMessageDialog(null, "sql语句失败.\n错误代码:"+e1.getErrorCode()+"\n详情:"+e1.getMessage());											
								e1.printStackTrace();
								return;
	
							}
						catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "请正确输入实收钱数"+"\n详情:"+e1.getMessage());											
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
		//加入面板
		
	
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
		//````第一行
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
		//```````第二行```````````````````
		//`````
		JPanel jp_getMoney_r3=new JPanel(new GridLayout(1, 3));
		jp_getMoney_r3.add(new JLabel("扫描框:"));
		jp_getMoney_r3.add(jt_scanf);
	
		jp_getMoney_r3.add(new JLabel(" "));
		doc.addDocumentListener(new DocListener(jt_scanf, tableheaderNameVec, tableDateRowVec, js_tableDetialGoods, vidVec, tableVec,jp_getMoney_r3));
		//.``````第三行
		
		
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
		js_tableDetialGoods.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black),"商品明细"));
		jp_getMoney.add(js_tableDetialGoods, BorderLayout.CENTER);
		jp_getMoney.add(jp_south, BorderLayout.SOUTH);
		jp_getMoney.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black),"收银界面"));
		jp_getMoney.validate();
		jp_getMoney.repaint();
		
		
	}
	
	public JTable amongSearch(JScrollPane jp,CheckboxGroup cgroup,Map<String, Checkbox> map,JTextField jTextField) {
		final JScrollPane jp_searchDetail=jp;
		final CheckboxGroup checkGroup=cgroup;
		
		final JTextField jt=jTextField;
		if( checkGroup.getSelectedCheckbox()==null){
			JOptionPane.showMessageDialog(null, "错误:未选择查询方式");
			return null;
		}
		if(jt.getText().equalsIgnoreCase("") || jt.getText()==null){
			JOptionPane.showMessageDialog(null, "错误:请填写需要查询的编号或电话，姓名，日期等信息\n注:日期需要用半角.分开，如:8.23");
			return null;
		}
		String checkboxString=cgroup.getSelectedCheckbox().getLabel();//得到字符串
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
				r=s.executeQuery();//查询到
				  tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				  if(tableColumNum<=0)return null;
				//  System.out.println(r);
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  //建立表头vec
				  while(r.next()){
					  //有下一行
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						  if(tableheaderNameVec.elementAt(j-1).equalsIgnoreCase("生日")){
							  v.add(r.getString(j).substring(r.getString(j).indexOf("-")+"-".length()));
							  continue;
							  
						  }
						  v.add(r.getString(j));//添加进去							  
					  }
					  tableDateRowVec.add(v);
				  }
				  DefaultTableModel dtmod=new DefaultTableModel(tableDateRowVec, tableheaderNameVec);
				  jtable=new JTable(dtmod);//表格完成
				  return jtable;
				
			}else if (checkboxString.equalsIgnoreCase(getChineseName("phone"))) {
				
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_searchby_phone));
//				s.setString(1, "%"+jt.getText());
				//	s.setString(2, jt.getText()+"%");
					s.setString(1,  "%"+jt.getText()+ "%");
					//s.setString(4, jt.getText());
				
				r=s.executeQuery();//查询到
				  tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				 // System.out.println(r);
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  //建立表头vec
				  while(r.next()){
					  //有下一行
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						  if(tableheaderNameVec.elementAt(j-1).equalsIgnoreCase("生日")){
							  v.add(r.getString(j).substring(r.getString(j).indexOf("-")+"-".length()));
							  continue;
							  
						  }
						  v.add(r.getString(j));//添加进去							  
					  }
					  tableDateRowVec.add(v);
				  }
				  DefaultTableModel dtmod=new DefaultTableModel(tableDateRowVec, tableheaderNameVec);
				  if(!r.isClosed())r.close();
					 if(!s.isClosed())s.close();//关闭他们
				  jtable=new JTable(dtmod);//表格完成
				  return jtable;
				
				
				
			}else if (checkboxString.equalsIgnoreCase(getChineseName("fname"))) {
				System.out.println(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_searchby_name));
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_searchby_name));
			//	s.setString(1, "%"+jt.getText());
			//	s.setString(2, jt.getText()+"%");
				s.setString(1,  "%"+jt.getText()+ "%");
				//s.setString(4, jt.getText());
				r=s.executeQuery();//查询到
			    tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				 // System.out.println(r);
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  //建立表头vec
				  while(r.next()){
					  //有下一行
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						  if(tableheaderNameVec.elementAt(j-1).equalsIgnoreCase("生日")){
							  v.add(r.getString(j).substring(r.getString(j).indexOf("-")+"-".length()));
							  continue;
							  
						  }
						  v.add(r.getString(j));//添加进去							  
					  }
					  tableDateRowVec.add(v);
				  }
				  DefaultTableModel dtmod=new DefaultTableModel(tableDateRowVec, tableheaderNameVec);
				  if(!r.isClosed())r.close();
					 if(!s.isClosed())s.close();//关闭他们
				  jtable=new JTable(dtmod);//表格完成
				  return jtable;
				
				
				
			}else if (checkboxString.equalsIgnoreCase(getChineseName("birthday"))) {	
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_searchby_birthday));
				s.setDate(1, java.sql.Date.valueOf(toDateString(jt.getText(), 1)));
				r=s.executeQuery();//查询到
				  tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				 // System.out.println(r);
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  //建立表头vec
				  while(r.next()){
					  //有下一行
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						  if(tableheaderNameVec.elementAt(j-1).equalsIgnoreCase("生日")){
							  v.add(r.getString(j).substring(r.getString(j).indexOf("-")+"-".length()));
							  continue;
							  
						  }
						  v.add(r.getString(j));//添加进去							  
					  }
					  tableDateRowVec.add(v);
				  }
				  DefaultTableModel dtmod=new DefaultTableModel(tableDateRowVec, tableheaderNameVec);
				  jtable=new JTable(dtmod);//表格完成	
				  return jtable;
			}else if (checkboxString.equalsIgnoreCase(getChineseName("datejoin"))) {	
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_searchby_datejoin));
				System.out.println(toDateString(jt.getText(), 2));
				s.setDate(1, java.sql.Date.valueOf(toDateString(jt.getText(), 2)));
				r=s.executeQuery();//查询到
				  tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				//  System.out.println(r);
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  //建立表头vec
				  while(r.next()){
					  //有下一行
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						  if(tableheaderNameVec.elementAt(j-1).equalsIgnoreCase("生日")){
							  v.add(r.getString(j).substring(r.getString(j).indexOf("-")+"-".length()));
							  continue;
							  
						  }
						  v.add(r.getString(j));//添加进去							  
					  }
					  tableDateRowVec.add(v);
				  }
				  DefaultTableModel dtmod=new DefaultTableModel(tableDateRowVec, tableheaderNameVec);
				  if(!r.isClosed())r.close();
				  if(!s.isClosed())s.close();//关闭他们
				  jtable=new JTable(dtmod);//表格完成	
				  return jtable;
			}
			else if (checkboxString.equalsIgnoreCase(getChineseName("score"))) {	
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_searchby_score));
				s.setInt(1, Integer.parseInt(jt.getText()));
				r=s.executeQuery();//查询到
				  tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				//  System.out.println(r);
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  //建立表头vec
				  while(r.next()){
					  //有下一行
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						  if(tableheaderNameVec.elementAt(j-1).equalsIgnoreCase("生日")){
							  v.add(r.getString(j).substring(r.getString(j).indexOf("-")+"-".length()));
							  continue;
							  
						  }
						  v.add(r.getString(j));//添加进去							  
					  }
					  tableDateRowVec.add(v);
				  }
				  DefaultTableModel dtmod=new DefaultTableModel(tableDateRowVec, tableheaderNameVec);
				  if(!r.isClosed())r.close();
				  if(!s.isClosed())s.close();//关闭他们
				  jtable=new JTable(dtmod);//表格完成	
				  return jtable;
			}
			else {
				  if(!r.isClosed())r.close();
				  if(!s.isClosed())s.close();//关闭他们
				return null;
			}
			
			
			
			
			
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "sql语句失败.\n错误代码:"+e1.getErrorCode()+"\n详情:"+e1.getMessage());											
			e1.printStackTrace();
			return null;
		}
		catch (NumberFormatException e1){
			JOptionPane.showMessageDialog(null, "错误:请输入正确数字.\n错误代码:"+"\n详情:"+e1.getMessage());											
			e1.printStackTrace();
			return null;
		}
		catch (IllegalArgumentException e1){
			JOptionPane.showMessageDialog(null, "错误:请检测输入格式.\n错误代码:"+"\n详情:"+e1.getMessage());											
			e1.printStackTrace();
			return null;
		}	
		catch (Exception e1){
			JOptionPane.showMessageDialog(null, "错误:请检测输入格式.\n错误代码:"+"\n详情:"+e1.getMessage());											
			e1.printStackTrace();
			return null;
		}	
		
		
	}
	
	
	
	
	
	class LabelListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			actionAsSelected((JLabel)e.getSource());//进行反应
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
					new JLabel("会员编号"),
					new JLabel("生日"),
					new JLabel("电话"),
					new JLabel("姓名"),
					
			};
			
			jObjlist=new Object[]{
					new JButton("生成"),
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
			//加入面板
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
	    	if(obj.getClass()==JButton.class){//没有获取
	    		  JOptionPane.showMessageDialog(null, "加入错误:未获取会员编号，无法加入");
	    		  return ;
	    		
	    	}else {
	    		if(jlist[i].getText().equalsIgnoreCase("生日")){
	    			//是生日
	    			
	    			String orignstring=((JTextField)obj).getText();
	    			
	    			int index=orignstring.indexOf(".");
	    			if(index==-1){
	    				
	    				JOptionPane.showMessageDialog(null, "加入错误:生日请用半角\".\"号分割");
	    				return ;
	    			}
	    			String birthday="2010-"+orignstring.substring(0, index)+"-"+orignstring.substring(index+".".length());
	    			columVec.addElement(getChineseName(jlist[i].getText()));//列名字
	    			dateVec.addElement(birthday);
	    			continue;
	    			
	    		}
	    		columVec.addElement(getChineseName(jlist[i].getText()));//列名字
				dateVec.addElement(((JTextField)obj).getText());
	    				    		
			}
	    	
	    }
	    SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd");
	    String joindate= df.format(new Date());//加入日期
	    //System.out.println(joindate);
	    columVec.addElement(getChineseName("加入日期"));//datejoin
	    dateVec.addElement(joindate);
	    columVec.addElement(getChineseName("积分"));
	    dateVec.addElement("0");
	   
	    int dateValueCount=1;
	    int vipidIndex=-1;
	    String phone=null;
	    String name=null;
	    try {
			java.sql.PreparedStatement ps=dBconncetion.getCon().prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_insert));
			 for(int i=0;i<columVec.size();i++){
			    	if(getChineseName(columVec.elementAt(i)).equalsIgnoreCase("会员编号") || getChineseName(columVec.elementAt(i)).equalsIgnoreCase("积分")){
			    		
			    		ps.setInt(dateValueCount+i, Integer.parseInt(dateVec.elementAt(i)) );//设置整数值
			    		if(getChineseName(columVec.elementAt(i)).equalsIgnoreCase("会员编号")) {
			    			vipidIndex=i;

			    		}
			    	}
			    	else if(getChineseName(columVec.elementAt(i)).equalsIgnoreCase("生日") || getChineseName(columVec.elementAt(i)).equalsIgnoreCase("加入日期")){
						
			    		ps.setDate(dateValueCount+i, java.sql.Date.valueOf(dateVec.elementAt(i)));
			    	
			    		
			    		
					}
			    	else {
			    		
			    		if(getChineseName(columVec.elementAt(i)).equalsIgnoreCase("电话") )phone=dateVec.elementAt(i);
			    		if(getChineseName(columVec.elementAt(i)).equalsIgnoreCase("姓名") )name=dateVec.elementAt(i);
			    		ps.setString(dateValueCount+i,dateVec. elementAt(i));//设置字符型值
			    		
			    		
					}		    	
			    }
			 //设置完成
			 
			 if( ps.executeUpdate()==1){
				 //加入了，关闭
				 vipVec.clear();
				 vipVec.addElement( Integer.parseInt(dateVec.elementAt(vipidIndex))); 
				 jl_name.setText("姓名:");
				 jl_phone.setText("电话:");
				 jL_vipID.setText("编号:");
				 jL_vipID.setText(jL_vipID.getText()+vipVec.elementAt(0));
					jl_name.setText(jl_name.getText()+name);
					jl_phone.setText(jl_phone.getText()+phone);
				 
				 
				 jf.dispose();
				 
				
			 }
			 else {
				JOptionPane.showMessageDialog(null, "错误：加入失败，请确认编号和电话已获取");
				return;
			}
	    
	    
	    
	    } catch (SQLException e1) {
			// TODO Auto-generated catch block
	    	JOptionPane.showMessageDialog(null, "sql语句生成失败.\n错误代码:"+e1.getErrorCode()+"\n详情:"+e1.getMessage());											
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
				
				
				int strsign=10;//回车
				if(jt_scanf.getText().indexOf(strsign)!=-1){
					//出现了\r\n
				    String goodsid=	jt_scanf.getText().substring(0, jt_scanf.getText().indexOf(strsign));//找到商品id
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
					tableVec.add(jt_goodsdetial);//新表
					jt_goodsdetial.setCellSelectionEnabled(false);
					jt_goodsdetial.setRowSelectionAllowed(true);
					jt_goodsdetial.setRowSelectionInterval(tableDateRowVec.size()-1, tableDateRowVec.size()-1);//最后一行
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
					jp_getMoney_r3.add(new JLabel("扫描框:"));
					
					
					jp_getMoney_r3.add(jnew);
					
					jp_getMoney_r3.add(new JLabel(" "));
					jp_getMoney_r3.validate();
					jp_getMoney_r3.repaint();
					jnew.setVisible(true);
					jnew.requestFocus();
					
				}
				
				
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "sql语句生成失败\n错误代码:"+e1.getErrorCode()+"\n详情:"+e1.getMessage());											
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

		public Vector<String> header;//表头店面描述信息
		public  Vector<Vector<String>> body;   //商品信息主体
		public Vector<String> customdetial;//客户信息，最多3行，提示信息+id
		int maxPoint=165;//做多这么多的点
		
		
		public Printer() {

		body=new Vector<>();//内容主题		
		header=new Vector<>();
		customdetial=new Vector<>();
		header.add("-----------------------");
		header.add("欢迎光临艾特诗薇");
		header.add("·······················");
		//表头还应该有的信息
		
		
		
			
			
		}
		public void clearBodyAndCustom() {
			body.clear();
			customdetial.clear();
		}
		
		@Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
			// 
			if(body.size()<=0 || customdetial.size()<=0)return 0; //没有记录
			
			
			// Component c = null;  
		        // 转换成Graphics2D 拿到画笔  
		       Graphics2D g2 = (Graphics2D) graphics;  
		        // 设置打印颜色为黑色  
		        g2.setColor(Color.black);  
		  
		        // 打印起点坐标  
		        double x = pageFormat.getImageableX();  
		        double y = pageFormat.getImageableY();  
		        // 虚线  
		        float[] dash1 = { 4.0f };  
		     
		        g2.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, dash1, 0.0f)); 
		        SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd HH:mm");
		        Font titlefont=new Font("宋体", Font.BOLD, 14);//20大小
		        g2.setFont(titlefont);
		        float height=titlefont.getSize2D();
		        
		        
		        g2.drawString("*欢迎光临沫莉花开*", (float)x, (float)y+height);
		        float lineY=2*height; //下一行描绘起点
		        
		        Font font2=new Font("宋体", Font.PLAIN, 12);//12大小
		        g2.setFont(font2);
		        float fontHeight=font2.getSize2D();
		        g2.drawString("===============", (float)x,(float) y+lineY);
		        lineY+=fontHeight;//更新偏移
		        g2.drawString("会员姓名:"+customdetial.elementAt(0), (float)x, lineY+(float)y);//x,y为起始点
		        lineY+=fontHeight;//更新偏移
		        g2.drawString("会员编号:"+customdetial.elementAt(1), (float)x, lineY+(float)y);//x,y为起始点
		        lineY+=fontHeight;//更新偏移
		        g2.drawString("会员积分:"+customdetial.elementAt(2), (float)x, lineY+(float)y);//x,y为起始点
		        lineY+=fontHeight;//更新偏移
		        g2.drawString("时间:"+df.format(new Date()), (float)x, lineY+(float)y);//x,y为起始点
		        lineY+=fontHeight;//更新偏移
		        g2.drawString("===============", (float)x,(float) y+lineY);
		        lineY+=fontHeight;//更新偏移
		        
		        g2.drawString("商品", (float)x,(float) y+lineY);
		        g2.drawString("价格", (float)x+(float)maxPoint/3,(float) y+lineY);
		        g2.drawString("数量", (float)x+(float)maxPoint*2/3,(float) y+lineY);
		        
		        lineY+=fontHeight;//更新偏移
		        
		        
		        g2.drawString("===============", (float)x,(float) y+lineY);
		        font2=new Font("宋体", Font.PLAIN, 10);//12大小
		        g2.setFont(font2);
		        fontHeight=font2.getSize2D();
		        lineY+=fontHeight;//更新偏移
		        //`````````` ``````````开始绘制具体信息，等下解决
		        for(Vector<String> v:body){
		        	    g2.drawString(v.elementAt(1), (float)x,(float) y+lineY);
				        g2.drawString(v.elementAt(2), (float)x+(float)maxPoint/3,(float) y+lineY);
				        g2.drawString(v.elementAt(3), (float)x+(float)maxPoint*2/3,(float) y+lineY);
				        lineY+=fontHeight;//更新偏移

		        }
		        
		        //细节绘制完成
		        //`````````````````````总信息``````````````````
		        
		        g2.drawString("===============", (float)x,(float) y+lineY);
		        font2=new Font("宋体", Font.PLAIN, 15);
		        fontHeight=font2.getSize2D();
		        g2.setFont(font2);//更新字体
		        lineY+=fontHeight;//更新偏移
		        g2.drawString("总件数:"+customdetial.elementAt(5), (float)x,(float) y+lineY);
		        lineY+=fontHeight;//更新偏移
		        g2.drawString("总计:"+customdetial.elementAt(3), (float)x,(float) y+lineY);
		        lineY+=fontHeight;//更新偏移
		        g2.drawString("实收:"+customdetial.elementAt(4), (float)x,(float) y+lineY);
		        lineY+=fontHeight;//更新偏移
		        if((Integer.parseInt(customdetial.elementAt(4))-Integer.parseInt(customdetial.elementAt(3)))<0){
		        	//优惠
		        	  g2.drawString("优惠:"+(Integer.parseInt(customdetial.elementAt(3))-Integer.parseInt(customdetial.elementAt(4))), (float)x,(float) y+lineY);
				        lineY+=fontHeight;//更新偏移
		        	
		        }
		        
		        
		       else {
		    	   g2.drawString("优惠:"+(Integer.parseInt(customdetial.elementAt(4))-Integer.parseInt(customdetial.elementAt(3))), (float)x,(float) y+lineY);
			        lineY+=fontHeight;//更新偏移
				
		       	}
		        
		        g2.drawString("===============", (float)x,(float) y+lineY);
		        font2=new Font("宋体", Font.BOLD, 20);
		        fontHeight=font2.getSize2D();
		        g2.setFont(font2);//更新字体
		        lineY+=fontHeight;//更新偏移
		        
		        g2.drawString("**谢谢惠顾!**", (float)x,(float) y+lineY);
		        
		        
		        return 0;
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
		
		public void printTickets(){
			try {
				Book book=new Book();
				Paper paper=new Paper();
				paper.setSize(maxPoint, maxPoint*3+10*(body.size()+1));//长度是变动的
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
				JOptionPane.showMessageDialog(null, "错误：打印错误\n详情:"+e.getMessage());
				return;
			}
			
			
			
			
			
			
			
		}
		
		
		
		
		
		
	}
	
	
	
	
}


