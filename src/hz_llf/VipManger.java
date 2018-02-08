package hz_llf;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.acl.Group;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.sql.*;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.View;

import com.mysql.jdbc.PreparedStatement;

public class VipManger extends Amanger {
		//会员管理系统
		private JButton vipSectionButton;//选择按钮
		private JLabel  totalviewLable;	//总览标签
		private JLabel  addVipLable;	//会员添加标签
		private JLabel  searchAndDeleteLable; //会员查找与删除标签
		private JLabel  analyzeLabel;//喜好分析
		private Connecter dBconncetion;//数据库连接类
		private final int mode=ActionChangeService.MODE_VIP;//该模式的编号
		private JPanel	detPanle;//工作面板
		private JPanel	basePanle;//工作根面板
		private boolean isWorking;//是否开始
		private JLabel lastone;//上一个点击的label
		private boolean inThisMode;//是否在
		private AddSet addSet;
		

		public VipManger(JPanel basepanel,Connecter dBconncetion) {
			// TODO Auto-generated constructor stub
			this.dBconncetion=dBconncetion;//connceter
			this.basePanle=basepanel;
			this.detPanle=new JPanel();
			vipSectionButton=new JButton("会员管理");
			totalviewLable=new JLabel("会员总览");
			totalviewLable.addMouseListener(new LabelListener());
			
			addVipLable=new JLabel("添加会员");
			addVipLable.addMouseListener(new LabelListener());
			
			searchAndDeleteLable=new JLabel("会员查找/删除");
			searchAndDeleteLable.addMouseListener(new LabelListener());
			
			analyzeLabel=new JLabel("喜好分析");
			analyzeLabel.addMouseListener(new LabelListener());
			
			
			//``````initState
			isWorking=false;
			lastone=null;//上一个为空
			inThisMode=false;
			super.setFirstCount(true);
			
			
			
			
		
		}
		
		
		@Override
		public int getModeState(){
			return mode;//重载
			
		}
		@Override
		public JLabel getFirstLable() {
			//第一个选项
			return totalviewLable;
		};
		@Override
		public JLabel[] getJlabelList() {
			return new JLabel[]{
				addVipLable,
			//	analyzeLabel,
				searchAndDeleteLable,
				totalviewLable,
				
			};
			
	
			
			
			
		};
		@Override
		public JButton getButton() {
			return vipSectionButton;
			
			
		};
		public boolean isInThisMode() {
			return inThisMode;
		}


		public void setInThisMode(boolean inThisMode) {
			this.inThisMode = inThisMode;
		}

		
		public JButton getVipSectionButton() {
			return vipSectionButton;
		}
		public JLabel getTotalviewLable() {
		
			
			return totalviewLable;
		}
		public JLabel getAddVipLable() {
			return addVipLable;
		}
		public JLabel getSearchAndDeleteLable() {
			return searchAndDeleteLable;
		}
		public JLabel getAnalyzeLabel() {
			return analyzeLabel;
		}
		public Connecter getdBconncetion() {
			return dBconncetion;
		}
		public int getMode() {
			return mode;
		}
		public JPanel getDetPanle() {
			return detPanle;
		}
		public boolean isWorking() {
			return isWorking;
		}
		
		public void setLastone(JLabel lastone) {
			this.lastone = lastone;
		}


		public JLabel getLastone() {
			return lastone;
		}
		@Override
		public void actionAsSelected(JLabel thisone) {
			if(isWorking==true || lastone==thisone || thisone==null)
				{
				basePanle.removeAll();//基本面板都移除
				basePanle.add(detPanle);//
				basePanle.validate();
				basePanle.repaint();
				return;}//必须没有开始工作且上一个不是本身,只进行简单放上去
		      isWorking=true;//开始工作
		      if(thisone==totalviewLable){  //`````````````````总览模式
		    	  //选择了总览模式
		    	  thisone.setForeground(Color.BLUE);
		    	  basePanle.removeAll();//基本面板都移除
		    	  detPanle.removeAll();//该工作面板移除
		    	  //````````````````积分板
		    	  JPanel scorePane=new JPanel();
		    	  JScrollPane scorePanel_s=new JScrollPane();//表格放上面
		    	 
		    	  Connection connecter= dBconncetion.getCon();
		    	 
		    	  JLabel scoreJlabel=new JLabel();//
		    	  java.sql.PreparedStatement s=null;
		    	  ResultSet r=null;
		    	  ResultSetMetaData tableheader=null;
		    	  Vector<String> tableheaderNameVec=new Vector<String>();
		    	  Vector<Vector<String>> tableDateRowVec=new Vector<>();
		    	  try {
		    		 // System.out.println(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_score));
					  s=  dBconncetion.getCon().prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_score));
					  r=s.executeQuery();
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
					 JTable j=new JTable(dtmod);//表格完成
					
					 scorePanel_s.setViewportView(j);//设置卷缩面板为table的设置
					 
					 scoreJlabel.setText("积分榜");
					 scorePane.setLayout(new BorderLayout());
					 scorePane.add(scoreJlabel,BorderLayout.NORTH);
					 scorePane.add(scorePanel_s, BorderLayout.CENTER);
					 
					 if(!r.isClosed())r.close();
					 if(!s.isClosed())s.close();//关闭他们
					 
					  
					  
					  
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "sql语句生成失败,错误代码:"+e.getErrorCode()+"\n详情:"+e.getMessage());	
					e.printStackTrace();
					return;
					
				}
		    	 
		     	
		    	  
		    	  
		    	  
		    	  
		    	  
		    	  //``````积分面板结束
		    	  //``````本月生日会员
		    	  JPanel birthdayPanel=new JPanel();
		    	  
		    	  
		    	  String baseyears="2010";
		    	  tableDateRowVec=new Vector<>();//肯定要重新申请啊
		    	  
		    	  tableheaderNameVec=new Vector<String>();
		    	
		    	  try {
		    		  //	System.out.println(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_birthday));
				        s=connecter.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_birthday));
				        Calendar c=Calendar.getInstance();
				        String mintime=baseyears+"-"+(c.get(Calendar.MONTH)+1)+"-"+"01";//1号
				        String maxtime=baseyears+"-"+(c.get(Calendar.MONTH)+1)+"-"+"31";//31号	        
				        s.setString(1, mintime);
				        s.setString(2, maxtime);		
				        //System.out.println(s);
				        r=s.executeQuery();
				        tableheader=r.getMetaData();
						  int tableColumNum= tableheader.getColumnCount();
						  tableDateRowVec.clear();
						  tableheaderNameVec.clear();
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
						 JTable j=new JTable(dtmod);//表格完成
						 JScrollPane birthdaTablePanel=new JScrollPane(j);
						 JLabel j_bir=new JLabel("当月生日会员表");
						 birthdayPanel.setLayout(new BorderLayout());
						 birthdayPanel.add(j_bir, BorderLayout.NORTH);
						 birthdayPanel.add(birthdaTablePanel, BorderLayout.CENTER);
						 if(!r.isClosed())r.close();
						 if(!s.isClosed())s.close();//关闭他们
						 
				        
				        
				        
				        
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "sql语句生成失败,错误代码:"+e.getErrorCode()+"\n详情:"+e.getMessage());					
					e.printStackTrace();
					return;
				}
		    	  
		    	  
		    	  //``````````````总数面板
		    	  JPanel j_total=new JPanel();
		    	  j_total.setLayout(new GridLayout(1, 2));
		    	  try {
					s=connecter.prepareStatement("select count(*) from "+ Connecter.TABLENAME_VIP+" ; ");
					
					r=s.executeQuery();
					r.next();
					JLabel j_allvips=new JLabel( "<html>"+ "<br />会员总数:"+r.getString(1)+"<br />"+ "</html>");//只有一列
					if(!r.isClosed())r.close();
					if(!s.isClosed())s.close();//关闭他们
					s=connecter.prepareStatement("select sum(score) from "+ Connecter.TABLENAME_VIP+" ; ");
					r=s.executeQuery();
					r.next();
					JLabel j_AllScore=new JLabel( "<html>"+"<br />积分总数:"+r.getString(1)+"<br />"+ "</html>");//只有一列
					if(!r.isClosed())r.close();
					if(!s.isClosed())s.close();//关闭他们
					j_total.add(j_allvips);
					j_total.add(j_AllScore);
					j_total.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK), "会员总览"));
					 if(!r.isClosed())r.close();
					 if(!s.isClosed())s.close();//关闭他们
					//```````
					
					
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "sql语句生成失败.\n错误代码:"+e.getErrorCode()+"\n详情:"+e.getMessage());					
					e.printStackTrace();
					return;
				}
		    	  //```````````````
		    	  detPanle.setLayout(new BorderLayout());
		    	  JPanel jp=new JPanel();
		    	  jp.setLayout(new GridLayout(1, 2));
		    	  jp.add(scorePane);
		    	  jp.add(birthdayPanel);
		    	  detPanle.add(jp, BorderLayout.CENTER);
		    	  detPanle.add(j_total, BorderLayout.NORTH);
		    	  detPanle.validate();
		    	  detPanle.repaint();
		    	  
		    	  //`````````````````
		    	  
		    	  basePanle.setLayout(new BorderLayout());
		    	  basePanle.add(detPanle,BorderLayout.CENTER);//
		    	  basePanle.validate();
		    	 
		    	 if(lastone!=null) lastone.setForeground(Color.BLACK);
		    	  lastone=thisone;
		    	  isWorking=false;
		    	  return;
		      }else if(thisone==addVipLable){//``---------加会员-------------------
		    	  thisone.setForeground(Color.BLUE);
		    	  basePanle.removeAll();//基本面板都移除
		    	  detPanle.removeAll();//该工作面板移除
		    	  //添加面板``````````````````````
		    	   final JPanel j_addPanel=new JPanel();
		    	   j_addPanel.removeAll();
		    	   addSet =new AddSet();
		    	   JButton j_getNewNum= (JButton)addSet.getObjByTxt("会员编号");
		    	   j_getNewNum.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						isWorking=true;
					//	JButton thisbutton=(JButton)e.getSource();
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
							insertIntoDb(j_addPanel);
						isWorking=false;
					}
				});
		    	
		    	   
 
 
		    	  detPanle.setLayout(null);
		    	  detPanle.add(j_addPanel);
		    	  j_addPanel.setSize(600,200);
		    	  j_addPanel.setLocation(new Point(0, 0));
		    	  detPanle.add(j_addButton);
		    	  j_addButton.setLocation(480,250);
		    	  j_addButton.setSize(100, 40);
		    	  
		    	  basePanle.setLayout(new BorderLayout());
		    	 
		    	  basePanle.add(detPanle,BorderLayout.CENTER);//
		    	  basePanle.validate();
		    	  basePanle.repaint();
		    	  
		    	  if(lastone!=null) lastone.setForeground(Color.BLACK);
		    	  lastone=thisone;
		    	  isWorking=false;
		    	  return;
		      }else if (thisone==searchAndDeleteLable) {//``````````````````查找`````````````````````
		    	  thisone.setForeground(Color.BLUE);
		    	  basePanle.removeAll();//基本面板都移除
		    	  detPanle.removeAll();//该工作面板移除
		    	  //```````查找
		    	  final JPanel jp_search=new JPanel();
		    	  final JScrollPane jp_table=new JScrollPane();//表的
		    	  final JPanel allComponetPanel=new JPanel();//都放这里算了
		    	  final JPanel jp_detialPanel=new JPanel();
		    	  final JScrollPane jp_tableDetail=new JScrollPane();//消费表的
		    	  JLabel jl_wayTosearch=new JLabel("查找方式:");
		    	  final CheckboxGroup checkboxGroup=new CheckboxGroup();
		    	  final Map<String, Checkbox> boxmap=new HashMap<String,Checkbox>();
		    	  boxmap.put("会员编号", new Checkbox("会员编号",checkboxGroup,true));
		    	  boxmap.put("电话", new Checkbox("电话", checkboxGroup,false));
		    	  boxmap.put("姓名", new Checkbox("姓名", checkboxGroup,false));
		    	  boxmap.put("生日", new Checkbox("生日", checkboxGroup,false));
		    	  boxmap.put("加入日期", new Checkbox("加入日期", checkboxGroup,false));
		    	  boxmap.put("积分", new Checkbox("积分", checkboxGroup,false));
		    	  JLabel jl_title=new JLabel("输入:");
		    	   final  JTextField jt=new JTextField();
		    	  JButton jb_search=new JButton("查询");
		    	  jb_search.addActionListener(new ActionListener() {				
					@Override
					public void actionPerformed(ActionEvent e) {
						amongSearch(jp_table,checkboxGroup,boxmap,jt);//画个表格
						
					}
				});
		    	 
		    	  
		    	  
		    	  
		    	  //下来为右边消费面板
		    	  JLabel jl_idlabel=new JLabel("会员编号");
		    	  final JTextField jt_idfield=new JTextField();
		    	  JLabel jl_statr=new JLabel("开始日期:");
		    	  final JTextField jt_start=new JTextField(); 
		    	  JLabel jl_end=new JLabel("结束日期:");
		    	  final JTextField jt_end=new JTextField();
		    	  JButton jb_detSearch=new JButton("查询");
		    	  JButton jb_export=new JButton("导出Exl");
	  	  
		    	  jb_detSearch.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						detailSearch(jp_tableDetail, jt_idfield, jt_start, jt_end);
						
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
		    	  jp_search.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "查找会员"));
		    	  jp_search.validate();
		    	  jp_search.repaint();
		    	  
		    	  jp_detialPanel.setLayout(new BorderLayout());
		    	  JPanel jp_det_north=new JPanel();
		    	  jp_det_north.setLayout(new GridLayout(3, 1));
		    	  JPanel jp_det_north_r1r1=new JPanel();
		    	  jp_det_north_r1r1.setLayout(new GridLayout(1, 2));
		    	  jp_det_north_r1r1.add(jl_idlabel);
		    	  jp_det_north_r1r1.add(jt_idfield);
		    	  JPanel jp_det_north_r2c1=new JPanel();
		    	  jp_det_north_r2c1.setLayout(new GridLayout(1, 4));
		    	  jp_det_north_r2c1.add(jl_statr);
		    	  jp_det_north_r2c1.add(jt_start);
		    	  jp_det_north_r2c1.add(jl_end);
		    	  jp_det_north_r2c1.add(jt_end);
		    	  JPanel jp_det_north_r3c1=new JPanel();
		    	  jp_det_north_r3c1.setLayout(new GridLayout(1, 2));
		    	  jp_det_north_r3c1.add(jb_detSearch);
		    	  jp_det_north_r3c1.add(jb_export);
		    	  
		    	  jp_det_north.add(jp_det_north_r1r1);
		    	  jp_det_north.add(jp_det_north_r2c1);
		    	  jp_det_north.add(jp_det_north_r3c1);
		    	  
		    	  jp_det_north.validate();
		    	  jp_det_north.repaint();
		    	  
		    	  jp_detialPanel.add(jp_det_north,BorderLayout.NORTH);
		    	  jp_detialPanel.add(jp_tableDetail, BorderLayout.CENTER);
		    	  
		    	  jp_detialPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "会员账单明细查询"));
		    	  jp_detialPanel.validate();
		    	  jp_detialPanel.repaint();
		    	  allComponetPanel.setLayout(new GridLayout(1, 2));
		    	  allComponetPanel.add(jp_search);
		    	  allComponetPanel.add(jp_detialPanel);
		    	  allComponetPanel.validate();
		    	  allComponetPanel.repaint();
		    	  
		    	  
		    	  
		    	  
		    	  
		    	  
		    	//``````````````
		    	  detPanle.setLayout(new BorderLayout());
		    	  detPanle.add(allComponetPanel, BorderLayout.CENTER);
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
			else if (thisone==analyzeLabel) {
				thisone.setForeground(Color.BLUE);
				basePanle.removeAll();//基本面板都移除
		    	detPanle.removeAll();//该工作面板移除
		    	//``````````````````````````
		    		
		    	
		    	  
		    	  
		    	//``````````````````````````
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
		public String getChineseName(String str) {
			if(str.equalsIgnoreCase("id"))return new String("会员编号");
			if(str.equalsIgnoreCase("phone"))return new String("电话");
			if(str.equalsIgnoreCase("fname"))return new String("姓名");
			if(str.equalsIgnoreCase("birthday"))return new String("生日");
			if(str.equalsIgnoreCase("score"))return new String("积分");
			if(str.equalsIgnoreCase("datejoin"))return new String("加入日期");
			
			if(str.equalsIgnoreCase("goodsid"))return new String("商品编号");
			if(str.equalsIgnoreCase("goodsname"))return new String("商品名称");
			if(str.equalsIgnoreCase("lining"))return new String("商品面料");
			if(str.equalsIgnoreCase("style"))return new String("商品类型");
			if(str.equalsIgnoreCase("color"))return new String("颜色");
			if(str.equalsIgnoreCase("price"))return new String("价格");
			if(str.equalsIgnoreCase("size"))return new String("大小");
			if(str.equalsIgnoreCase("amount"))return new String("数量");
			if(str.equalsIgnoreCase("buydate"))return new String("购买日期");
			if(str.equalsIgnoreCase("totalmoney"))return new String("总金额");
			
			
			
			if(str.equalsIgnoreCase("商品编号"))return new String("goodsid");
			if(str.equalsIgnoreCase("商品名称"))return new String("goodsname");
			if(str.equalsIgnoreCase("商品面料"))return new String("lining");
			if(str.equalsIgnoreCase("商品类型"))return new String("style");
			if(str.equalsIgnoreCase("颜色"))return new String("color");
			if(str.equalsIgnoreCase("价格"))return new String("price");
			if(str.equalsIgnoreCase("大小"))return new String("size");
			if(str.equalsIgnoreCase("数量"))return new String("amount");
			
			
			if(str.equalsIgnoreCase("会员编号"))return new String("id");
			if(str.equalsIgnoreCase("电话"))return new String("phone");
			if(str.equalsIgnoreCase("姓名"))return new String("fname");
			if(str.equalsIgnoreCase("生日"))return new String("birthday");
			if(str.equalsIgnoreCase("积分"))return new String("score");
			if(str.equalsIgnoreCase("加入日期"))return new String("datejoin");		
			if(str.equalsIgnoreCase("购买日期"))return new String("buydate");
			
			
			
			
			return null;			
		}
		

		public void insertIntoDb(JPanel j_addPanel) {
			final JPanel j_addPanel1=j_addPanel;
			Vector<String> dateVec=new Vector<String>();
			Vector<String> columVec=new Vector<String>();
		    Object[] olist=	addSet.jObjlist;
		    JLabel[] jlist= addSet.jlist;
		    for(int i=0;i<jlist.length;i++){
		    	Object obj= addSet.getObjByTxt(jlist[i].getText());
		    	if(obj.getClass()==JButton.class){//没有获取
		    		  JOptionPane.showMessageDialog(null, "加入错误:未获取会员编号，无法加入");
		    		  return;
		    		
		    	}else {
		    		if(jlist[i].getText().equalsIgnoreCase("生日")){
		    			//是生日
		    			
		    			String orignstring=((JTextField)obj).getText();
		    			//System.out.println(orignstring);
		    			int index=orignstring.indexOf(".");
		    			if(index==-1){
		    				
		    				JOptionPane.showMessageDialog(null, "加入错误:生日请用半角\".\"号分割");
		    				return;
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
		    int columNameCount=1;
		    int dateValueCount=1;//从1和7开始
		    //列和数据都有了
		   // System.out.println(dateVec);
		  //  System.out.println(columVec);
		    try {
				java.sql.PreparedStatement ps=dBconncetion.getCon().prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_insert));
				 for(int i=0;i<columVec.size();i++){
				    	if(getChineseName(columVec.elementAt(i)).equalsIgnoreCase("会员编号") || getChineseName(columVec.elementAt(i)).equalsIgnoreCase("积分")){
				    		 //是会员编号和积分
				    		ps.setInt(dateValueCount+i, Integer.parseInt(dateVec.elementAt(i)) );//设置整数值
				    		//ps.setString(columNameCount+i,columVec.elementAt(i));//设置字段名	   		
				    	}
				    	else if(getChineseName(columVec.elementAt(i)).equalsIgnoreCase("生日") || getChineseName(columVec.elementAt(i)).equalsIgnoreCase("加入日期")){
							//System.out.println( "index:"+((dateValueCount+i)+" "+java.sql.Date.valueOf(dateVec.elementAt(i))));
				    		ps.setDate(dateValueCount+i, java.sql.Date.valueOf(dateVec.elementAt(i)));
				    	//	ps.setString(columNameCount+i,columVec.elementAt(i));//设置字段名
				    		
				    		
						}
				    	else {
				    		ps.setString(dateValueCount+i,dateVec. elementAt(i));//设置字符型值
				    		//ps.setString(columNameCount+i,columVec.elementAt(i));//设置字段名
				    		
						}		    	
				    }
				 //设置完成
				 
				 if( ps.executeUpdate()==1){
					 //加入了，重新绘制
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
								JTextField  jtfield=new JTextField(newId+"");
								jtfield.setEditable(false);//不可编辑
								j_addPanel1.removeAll();
								addSet.changeObjByTxt("会员编号",jtfield);//新的组件
								addSet.addTOPanel(j_addPanel1);
								j_addPanel1.setBorder(new LineBorder(Color.black));
								j_addPanel1.validate();
								j_addPanel1.repaint();
						
							} catch (SQLException e1) {
								JOptionPane.showMessageDialog(null, "sql语句失败.\n错误代码:"+e1.getErrorCode()+"\n详情:"+e1.getMessage());											
								e1.printStackTrace();
								return;
							}
							
							
							
							
							isWorking=false;
						}
					});
			    	   addSet.addTOPanel(j_addPanel);//加入进去	   
			    	   j_addPanel.setBorder(new LineBorder(Color.black));
			    	   j_addPanel.validate();
			    	   j_addPanel.repaint();
	 
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
		
		public void amongSearch(JScrollPane jp,CheckboxGroup cgroup,Map<String, Checkbox> map,JTextField jTextField) {
			final JScrollPane jp_searchDetail=jp;
			final CheckboxGroup checkGroup=cgroup;
			final Map<String, Checkbox> checlMap=map;
			final JTextField jt=jTextField;
			if( checkGroup.getSelectedCheckbox()==null){
				JOptionPane.showMessageDialog(null, "错误:未选择查询方式");
				return;
			}
			if(jt.getText().equalsIgnoreCase("") || jt.getText()==null){
				JOptionPane.showMessageDialog(null, "错误:请填写需要查询的编号或电话，姓名，日期等信息\n注:日期需要用半角.分开，如:8.23");
				return;
			}
			String checkboxString=cgroup.getSelectedCheckbox().getLabel();//得到字符串
			java.sql.PreparedStatement s=null;
	    	  ResultSet r=null;
	    	  ResultSetMetaData tableheader=null;
	    	  Vector<String> tableheaderNameVec=new Vector<String>();
	    	  Vector<Vector<String>> tableDateRowVec=new Vector<>();
	    	  Connection con=dBconncetion.getCon();
			  final JTable jTable=creatSearchTableByType(jt, checkboxString, s, r, tableheader, tableheaderNameVec, tableDateRowVec, con);
 			  if(jTable==null){return ;}	
 			
 			  jp_searchDetail.setViewportView(jTable);
 			  jp_searchDetail.setBorder(new LineBorder(Color.black));
 			  jp.validate();
 			  jp.repaint();
			
		}
		
		public void detailSearch(JScrollPane jp,JTextField jt_id,JTextField jt_start, JTextField jt_end ) {
		
			if(jt_id.getText()==null || jt_start.getText()==null || jt_end.getText()==null || jt_id.getText().equalsIgnoreCase("") || jt_start.getText().equalsIgnoreCase("") || jt_end.getText().equalsIgnoreCase("") ){
				JOptionPane.showMessageDialog(null, "错误:请会员编号");
				return;
				
				
			}
			 java.sql.PreparedStatement s=null;
	    	  ResultSet r=null;
	    	  ResultSetMetaData tableheader=null;
	    	  Vector<String> tableheaderNameVec=new Vector<String>();
	    	  Vector<Vector<String>> tableDateRowVec=new Vector<>();
	    	  Connection con=dBconncetion.getCon();
	    	  final JTable jTable=creatDetialTable(jt_id, jt_start, jt_end, s, r, tableheader, tableheaderNameVec, tableDateRowVec, con);
	    	  if(jTable==null)return;
	    	  int pricecount=0;
	    	  int col=-1;
	    	  // System.out.println(tableheaderNameVec);
	    	  for(int i=0;i<tableheaderNameVec.size();i++){
	    		  System.out.println(tableheaderNameVec.elementAt(i));
	    		  if(getChineseName(tableheaderNameVec.elementAt(i)).equalsIgnoreCase("price")){
	    		  col=i;
	    		  break;
	    		  					}
	    		  }
	    	//  System.out.println(col);
	    	  if(col!=-1){
	    		  pricecount=0;
 	    		  for(Vector<String > vrow: tableDateRowVec){
	    			  pricecount+= Integer.parseInt(vrow.elementAt(col));
	    			  
	    			  
	    		  }
	    		 
	    	  }
//	    	  jp_new.setLayout(new BorderLayout());
//	    	  jp_new.add(jTable,BorderLayout.CENTER);
//	    	  jp_new.add(jl_allscore,BorderLayout.SOUTH);
//	    	  jp_new.validate();
//	    	  jp_new.repaint();
//			
	    	  jp.setViewportView(jTable);
	    	  jp.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "总消费额:"+pricecount+"元"));
	    	  jp.validate();
	    	  jp.repaint();
			
			
		}
		public JTable creatSearchTableByType(JTextField jt ,String checkboxString  , java.sql.PreparedStatement s, ResultSet r,ResultSetMetaData tableheader,Vector<String> tableheaderNameVec,Vector<Vector<String>> tableDateRowVec,Connection con) {
			 JTable jtable=null;
			try {
				
				if(checkboxString.equalsIgnoreCase(getChineseName("id"))){
					//按id查询
					if(jt.getText().equalsIgnoreCase("*")){
						//如果是星号
						s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_selectAllVip));
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
					}else{
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
					}
					
				}else if (checkboxString.equalsIgnoreCase(getChineseName("phone"))) {
					
					s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_searchby_phone));
//					s.setString(1, "%"+jt.getText());
					//	s.setString(2, jt.getText()+"%");
						s.setString(1,  "%"+jt.getText()+ "%");
						//s.setString(4, jt.getText());
					
					r=s.executeQuery();//查询到
					  tableheader=r.getMetaData();
					  int tableColumNum= tableheader.getColumnCount();
					  if(tableColumNum<=0)return null;
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
					  if(tableColumNum<=0)return null;
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
					  if(tableColumNum<=0)return null;
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
		
		public JTable creatDetialTable(JTextField jt_id,JTextField jt_start, JTextField jt_end ,java.sql.PreparedStatement s, ResultSet r,ResultSetMetaData tableheader,Vector<String> tableheaderNameVec,Vector<Vector<String>> tableDateRowVec,Connection con) {
			        JTable jtable=null;
				try {
					String dateStart=toDateString(jt_start.getText(), 2);
					String dateEnd=toDateString(jt_end.getText(), 2);
					int id=Integer.parseInt(jt_id.getText());
				//	System.out.println(id+" "+dateStart+" "+dateEnd+" " +dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_searchOneMsg));
						s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_vip_searchOneMsg));
						s.setInt(1, id);
						s.setDate(2, java.sql.Date.valueOf(dateStart));
						s.setDate(3, java.sql.Date.valueOf(dateEnd));
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

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "sql语句失败,请填写正确会员编号.\n错误代码:"+e1.getErrorCode()+"\n详情:"+e1.getMessage());											
					e1.printStackTrace();
					return null;
				}
				catch (NumberFormatException e1){
					JOptionPane.showMessageDialog(null, "错误:请输入正确数字.\n错误代码:"+"\n详情:"+e1.getMessage());											
					e1.printStackTrace();
					return null;
				}
				catch (IllegalArgumentException e1){
					JOptionPane.showMessageDialog(null, "错误:请检测日期输入格式.\n错误代码:"+"\n详情:"+e1.getMessage());											
					e1.printStackTrace();
					return null;
				}	
				catch (Exception e1){
					JOptionPane.showMessageDialog(null, "错误:请检测输入格式.\n错误代码:"+"\n详情:"+e1.getMessage());											
					e1.printStackTrace();
					return null;
				}
			
			
			
			
			
			
			
			
			
			
			
			
		}
		
		public String toDateString(String str,int mode) {
				try {
					if(mode==1){//生日
					
					return new String(Connecter.VIP_BIRTHDAYBASEYEAR+"-"+str.substring(0,str.indexOf("."))+"-"+str.substring(str.indexOf(".")+".".length()));
					
					
				}else {
					
					return new String(str.substring(0,str.indexOf("."))+"-"+str.substring(str.indexOf(".")+".".length(),str.indexOf(".", str.indexOf(".")+".".length()))+"-"+str.substring(str.indexOf(".",str.indexOf(".", str.indexOf(".")+".".length()))+".".length()));
					
				}
			} catch (Exception e) {
				// TODO: handle exception
				return null;
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
	

}
