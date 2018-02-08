package hz_llf;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;


import hz_llf.SellManger.AddSet;
import hz_llf.SellManger.DocListener;

public class GoodsManger extends Amanger{
	
	private JButton goodsSelectButton;//ѡ��ť
	private JLabel  insertGoodsLable;	//����¼��
	private JLabel  searchAndUpdateLable;	//��ѯ���޸�
	private JLabel  returnbackLable;	//��ѯ���޸�
	//private AddSet  addSet;
	private Connecter dBconncetion;//���ݿ�������
	private final int mode=ActionChangeService.MODE_GOODS;//��ģʽ�ı��
	private JPanel	detPanle;//�������
	private JPanel	basePanle;//���������
	private boolean isWorking;//�Ƿ�ʼ
	private JLabel lastone;//��һ�������label
	public GoodsManger(JPanel basepanel,Connecter dBconncetion) {
		basePanle=basepanel;
		this.dBconncetion=dBconncetion;
		detPanle=new JPanel();
		isWorking=false;
		insertGoodsLable=new JLabel("����¼��");
		goodsSelectButton=new JButton("�������");
		searchAndUpdateLable=new JLabel("�����ѯ�����");
		returnbackLable=new JLabel("�����˻�");
		lastone=null;
		searchAndUpdateLable.addMouseListener(new LabelListener());
		insertGoodsLable.addMouseListener(new LabelListener());
		returnbackLable.addMouseListener(new LabelListener());
		super.setFirstCount(true);
		
	
	}
	public void  addLabelsAndJtxtFields(Map<String,LabAndJtxt> map) {

		map.put( "��Ʒ���",new LabAndJtxt(new JTextField(),new JLabel("��Ʒ���"),new JTextArea()));
		map.put( "��Ʒ����",new LabAndJtxt(new JTextField(),new JLabel("��Ʒ����"),null));
		map.put( "��С",new LabAndJtxt(new JTextField(),new JLabel("��С"),null));
		map.put( "��Ʒ����",new LabAndJtxt(new JTextField(),new JLabel("��Ʒ����"),null));
		map.put( "��Ʒ����",new LabAndJtxt(new JTextField(),new JLabel("��Ʒ����"),null));
		map.put( "��ɫ",new LabAndJtxt(new JTextField(),new JLabel("��ɫ"),null));
		map.put( "�۸�",new LabAndJtxt(new JTextField(),new JLabel("�۸�"),null));
		map.put( "����",new LabAndJtxt(new JTextField(),new JLabel("����"),null));
		
	}
	public void insertPanelPaint(JPanel jp) {
		final JPanel jp_insert=jp;
		final Map<String, LabAndJtxt> map=new HashMap<>();
		addLabelsAndJtxtFields(map);//������Щ
		//�����¼�
		Document doc= map.get("��Ʒ���").jta.getDocument();
		doc.addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
			//	System.out.println("����:"+map.get("��Ʒ���").jt.getText());
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				  JTextArea jt=map.get("��Ʒ���").jta;
				 // byte[] blist=  jt.getText().getBytes();
				  int signstr=10;
				  
				   if(jt.getText().indexOf(signstr)!=-1){
					   //System.out.println("����:"+map.get("��Ʒ���").jta.getText());
					   String goodsid=jt.getText().substring(0, jt.getText().indexOf(signstr));//�źŴ���ǰ�Ķ���id
					   try {
						   Connection con=dBconncetion.getCon();
						   Statement s1=con.createStatement();
						   ResultSet r=s1.executeQuery("select * from "+Connecter.TABLENAME_GOODS+" where goodsid="+goodsid+";");
						   if(r.next()){
							   //�鵽��
							//   System.out.println( map.get( "��Ʒ���").jta.isEditable());
							  // map.get( "��Ʒ���").jta.setText(r.getString(1));
							   map.get( "��Ʒ���").jta.setEditable(false);
							   map.get( "��Ʒ���").jta.setBackground(Color.blue);
							   map.get( "��Ʒ���").state=1;//���е�
								map.get( "��Ʒ����").jt.setText(r.getString(2));
								map.get( "��Ʒ����").jt.setEditable(false);
								map.get( "��С").jt.setText(r.getString(3));
								map.get( "��С").jt.setEditable(true);
								map.get( "��С").jt.setText("");
								map.get( "��С").state=0;//δ�޸�
								//��С���Ա�
								Document  d1=	 map.get( "��С"    ).jt.getDocument();
								d1.addDocumentListener(new DocumentListener() {
								
								@Override
								public void removeUpdate(DocumentEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void insertUpdate(DocumentEvent e) {
									map.get( "��С"    ).state=1;
									
								}
								
								@Override
								public void changedUpdate(DocumentEvent e) {
									// TODO Auto-generated method stub
									
								}
							});
								
								map.get( "��Ʒ����").jt.setText(r.getString(4));
								map.get( "��Ʒ����").jt.setEditable(false);
								map.get( "��Ʒ����").jt.setText(r.getString(5));
								map.get( "��Ʒ����").jt.setEditable(false);
								map.get( "��ɫ"    ).jt.setText(r.getString(6));
								map.get( "��ɫ"    ).jt.setEditable(true);
								map.get( "��ɫ"    ).state=0;//δ�޸�
								map.get( "��ɫ"    ).jt.setText("");
								Document  d2=	 map.get( "��ɫ"    ).jt.getDocument();
								d2.addDocumentListener(new DocumentListener() {
								
								@Override
								public void removeUpdate(DocumentEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void insertUpdate(DocumentEvent e) {
									map.get( "��ɫ"    ).state=1;
									
								}
								
								@Override
								public void changedUpdate(DocumentEvent e) {
									// TODO Auto-generated method stub
									
								}
							});
								
								map.get( "�۸�"	   ).jt.setText(r.getString(7));
								map.get( "�۸�"	   ).jt.setEditable(false);
								map.get( "����"	   ).jt.setText(r.getString(8));
								map.get( "����"	   ).jt.setEditable(false);
							   //���ú�					   
						   }else {
							
							   map.get( "��Ʒ���").jta.setEditable(false);
							   map.get( "��Ʒ���").jta.setBackground(Color.blue);
							   map.get( "��Ʒ���").state=0;//����û��
						}
						   
	   
						
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "sql���ʧ��.\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
						e1.printStackTrace();
						
					}
					   
					  
					   return;
					   
				   }
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
					//System.out.println("");
			}
		});
		//��doc�¼�
		
		
		
		JButton jb_insert=new JButton("���");
		JButton jb_reset=new JButton("����");
		jb_insert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			 int state=	JOptionPane.showConfirmDialog(null, "�Ƿ����");
				if(state==JOptionPane.OK_OPTION){
					boolean hasNullitem=false;
					for(LabAndJtxt l  :map.values()){
					
						if(  l.jta==null){
							if(l.jt.getText()==null || l.jt.getText().equalsIgnoreCase("")){
							hasNullitem=true;
							break;
							}
							
						}else {
							if(l.jta.getText()==null || l.jta.getText().equalsIgnoreCase("")){
								//��Ų���Ϊ��
								hasNullitem=true;
								break;
							}
							
							
						}
					}
					if(hasNullitem==true){
						JOptionPane.showMessageDialog(null, "����:���ڿհ���");
						return;
					}
					else {
						 try {
							 int strsign=10;
							 if(map.get("��Ʒ���").state==1){
								 //ԭ�����У����¾���
								 
								 String goodsid= map.get("��Ʒ���").jta.getText().substring(0, map.get("��Ʒ���").jta.getText().indexOf(strsign));//�ҵ����
								 Connection con=dBconncetion.getCon();
								 Statement s1=con.createStatement();
								 String sql=" update  "+Connecter.TABLENAME_GOODS+ " set ";
								 if(map.get("��С").state==1){
									 sql=sql+getChineseName("��С")+"="+" CONCAT("+getChineseName("��С")+","+"',"+map.get("��С").jt.getText()+"') , ";
									 
								 }
								 
								 if(map.get("��ɫ").state==1){
									 sql=sql+getChineseName("��ɫ")+"="+" CONCAT("+getChineseName("��ɫ")+","+"',"+map.get("��ɫ").jt.getText()+"') , ";
									 }
								 sql=sql+"  amount=amount+1  where goodsid= "+goodsid+";";
								System.out.println(sql);
								 s1.execute(sql);
								 for(LabAndJtxt landj:map.values()){
									 landj.jt.setText("");
									 if(landj.jta!=null){
										 landj.jta.setText("");
										 landj.jta.setEditable(true);
										 landj.jta.setBackground(Color.WHITE);
									 }
									 landj.jt.setEditable(true);
									 if(landj.jl.getText().equalsIgnoreCase("��Ʒ���"))landj.state=-1;//��ʼ��
			 
								 }//��λ
								 
								 
							 }
							 
							 else {
								
							//ԭ��û�У���Ҫ�����
							 Connection con=dBconncetion.getCon();
							 PreparedStatement s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_goods_insert));
							 String goodsid= map.get("��Ʒ���").jta.getText().substring(0, map.get("��Ʒ���").jta.getText().indexOf(strsign));//�ҵ����
							 s.setString(1, goodsid);
							 s.setString(2, map.get(getChineseName("style")).jt.getText());
							 s.setString(3, map.get(getChineseName("size")).jt.getText());
							 s.setString(4, map.get(getChineseName("lining")).jt.getText());
							 s.setString(5, map.get(getChineseName("goodsname")).jt.getText());
							 s.setString(6, map.get(getChineseName("color")).jt.getText());
							 s.setInt(7, Integer.parseInt(map.get(getChineseName("price")).jt.getText()));
							 s.setInt(8, Integer.parseInt(map.get(getChineseName("amount")).jt.getText())); 
							 s.execute();//ִ��
							 for(LabAndJtxt landj:map.values()){
								 landj.jt.setText("");
								 if(landj.jta!=null){
									 landj.jta.setText("");
									 landj.jta.setEditable(true);
									 landj.jta.setBackground(Color.WHITE);
								 }
								 
								 landj.jt.setEditable(true);
								 if(landj.jl.getText().equalsIgnoreCase("��Ʒ���"))landj.state=-1;//��ʼ��
		 
							 }
							 
							 
							 
							 
							 }
							 
							 
							 
							 
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, "sql���ʧ��.\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
							e1.printStackTrace();
							return;
						}
						 catch (Exception e1){
							 JOptionPane.showMessageDialog(null, "��ʽ����,��������ȷ��ʽ"+"\n����:"+e1.getMessage());											
								e1.printStackTrace();
								return;
							 
							 
						 }
						
						
						
						
						
						
						
						
					}
					
					
					
				}
				else{return;}
				
				
			}
		});
		
		//��ȷ���¼�
		jb_reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				lastone=null;
				actionAsSelected(insertGoodsLable);
				
			}
		});
		
		///```````����
		
		JPanel j_north=new JPanel(new GridLayout(1, 3));
		
		JPanel j_north_c2=new JPanel(new GridLayout(8, 2));
		 j_north_c2.add( map.get(getChineseName("goodsid")).jl);
		 j_north_c2.add( map.get(getChineseName("goodsid")).jta);
		 j_north_c2.add( 	map.get(getChineseName("style")).jl);
		 j_north_c2.add( 	 map.get(getChineseName("style")).jt);
		 j_north_c2.add(  map.get(getChineseName("size")).jl);
		 j_north_c2.add( 	map.get(getChineseName("size")).jt);
		 j_north_c2.add( 	 map.get(getChineseName("lining")).jl);
		 j_north_c2.add( 	 map.get(getChineseName("lining")).jt);
		 j_north_c2.add( 	 map.get(getChineseName("goodsname")).jl);
		 j_north_c2.add(   map.get(getChineseName("goodsname")).jt);
		 j_north_c2.add( 	map.get(getChineseName("color")).jl);
		 j_north_c2.add( 	 map.get(getChineseName("color")).jt);
		 j_north_c2.add( 	map.get(getChineseName("price")).jl);
		 j_north_c2.add( map.get(getChineseName("price")).jt);
		 j_north_c2.add(  map.get(getChineseName("amount")).jl); 
		 j_north_c2.add(  map.get(getChineseName("amount")).jt); 
		j_north.add(new JPanel());
		j_north.add(j_north_c2);
		j_north.add(new JPanel());
		
		j_north.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "�������",TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.ABOVE_TOP));
	
		//``````�м�
		JPanel j_centre=new JPanel(new GridLayout(1, 3));
		JPanel j_centre_c2=new JPanel(new GridLayout(1, 3));
		j_centre_c2.add(new Label(" "));
		j_centre_c2.add(jb_insert);
		
		
		j_centre.add(new JPanel());
		j_centre.add(j_centre_c2);
		j_centre.add(new JPanel());
		//`````
		jp_insert.setLayout(new GridLayout(3, 3));//new JPanel(new Jlabel(" "))
		for(int i=0;i<4;i++) jp.add(new JPanel());
		jp_insert.add(j_north);
		for(int i=0;i<2;i++) jp.add(new JPanel());
		JPanel j_r3c2=new JPanel(new GridLayout(3, 3));
		j_r3c2.add(new JPanel());
		JPanel jp_r3c2_r1c2=new JPanel(new GridLayout(3, 2));
		//jp_r3c2_r1c2.add(new JPanel());
		jp_r3c2_r1c2.add(jb_insert);
		jp_r3c2_r1c2.add(jb_reset);
		for(int i=0;i<4;i++)jp_r3c2_r1c2.add(new JPanel());
		j_r3c2.add(jp_r3c2_r1c2);
		for(int i=0;i<7;i++)j_r3c2.add(new JPanel());
		jp_insert.add(j_r3c2);
		
		jp_insert.add(new JPanel());
		
		jp_insert.validate();
		jp_insert.repaint();
		
		
		
		
		
		
	}
	public void searchPabelPaint(JPanel jp) {
		 final JPanel allComponetPanel=jp;//������������
		  final JPanel jp_search=new JPanel();
    	  final JScrollPane jp_table=new JScrollPane();//���
    	  final Vector<JTable> tableVec_among=new Vector<>();
    	  final JPanel jp_detialPanel=new JPanel();//�ұ�ɨ���
    	  final JScrollPane jp_tableDetail=new JScrollPane();//���ѱ��
    	  JLabel jl_wayTosearch=new JLabel("���ҷ�ʽ:");
    	  final CheckboxGroup checkboxGroup=new CheckboxGroup();
    	  final Map<String, Checkbox> boxmap=new HashMap<String,Checkbox>();
    	  final JButton jb_edit=new JButton("�޸�ѡ����");
    	//  boxmap.put("��Ʒ���", new Checkbox("��Ʒ���",checkboxGroup,true));
    	  boxmap.put("��Ʒ����", new Checkbox("����", checkboxGroup,true));
    	  boxmap.put("��Ʒ����", new Checkbox("����", checkboxGroup,false));
    	  boxmap.put("��Ʒ����", new Checkbox("����", checkboxGroup,false));
    	 
    	  boxmap.put("��ɫ", new Checkbox("��ɫ", checkboxGroup,false));
    	  boxmap.put("�۸�", new Checkbox("�۸�", checkboxGroup,false));
    	  boxmap.put("��С", new Checkbox("��С", checkboxGroup,false));
    	  boxmap.put("����", new Checkbox("����", checkboxGroup,false));
    	  boxmap.put("������", new Checkbox("������", checkboxGroup,false));
    	  JLabel jl_title=new JLabel("����:");
    	   final  JTextField jt=new JTextField();
    	  JButton jb_search=new JButton("��ѯ");
    	  jb_search.addActionListener(new ActionListener() {				
			@Override
			public void actionPerformed(ActionEvent e) {
				amongSearch(jp_table,checkboxGroup,boxmap,jt,tableVec_among);//�������
				
			}
		});
    	  jb_edit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//�޸�
				if(tableVec_among.size()!=1 )return;
				if(tableVec_among.elementAt(0).getSelectedRow()==-1){
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�޸ĵ���");
					return;
				}
				//��Ҫ�޸ĵ���
				final JFrame jfedit=new JFrame();
				final JButton jb_update=new JButton("����");
				final JTable jt=tableVec_among.elementAt(0);
				String goodsid=(String)jt.getValueAt(jt.getSelectedRow(), 0);//��һ������
				Connection con=dBconncetion.getCon();
				try {
					Statement s=con.createStatement();
					ResultSet r= s.executeQuery(" select * from "+Connecter.TABLENAME_GOODS+" where goodsid="+goodsid);
					ResultSetMetaData colnums=r.getMetaData();
					if(!r.next()){return;}
					final JTextField[] jtlist=new JTextField[8];
					for(int i=0;i<8;i++)jtlist[i]=new JTextField();
					JLabel[]  jl_list=new JLabel[8];
					for(int i=0;i<8;i++) jl_list[i]=new JLabel("");
					for(int i=0;i<8;i++){
						if(i>=6) jtlist[i].setText(""+r.getInt(i+1));
						else{    jtlist[i].setText(r.getString(i+1));     }
						jl_list[i].setText(getChineseName(colnums.getColumnName(i+1)));	
						if(i==0)jtlist[i].setEditable(false);
					}
						jb_update.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							for(JTextField jt:jtlist){
								if(jt.getText().equalsIgnoreCase("") || jt.getText()==null){
									JOptionPane.showMessageDialog(null, "����:��������д");
									return;
								}
								
							}
							try {
								int price=Integer.parseInt(jtlist[6].getText());
								int amount=Integer.parseInt(jtlist[7].getText());
								Connection con=dBconncetion.getCon();
								PreparedStatement ps=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_Goods_update));
							    for(int i=1;i<=7;i++){ps.setString(i, jtlist[i].getText());}
								//���ã�
							    ps.setString(8, jtlist[0].getText());
								ps.executeUpdate();
								JOptionPane.showMessageDialog(null, "���³ɹ�");
								DefaultTableModel dfm=(DefaultTableModel )jt.getModel();
								dfm.removeRow(jt.getSelectedRow());
								jt.validate();
								jt.repaint();
								jfedit.dispose();
								
								
								
								
							} catch (SQLException e1) {
								
								JOptionPane.showMessageDialog(null, "sql���ʧ��,����д��ȷ��Ա���.\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
								e1.printStackTrace();
								return;
								
								
							} catch (Exception e1) {
								// TODO: handle exception
								JOptionPane.showMessageDialog(null, "����:����ȷ����"+"\n����:"+e1.getMessage());											
								e1.printStackTrace();
								return;
							}
							
							
							
							
						}
					});
			  JPanel jp_edit=new JPanel(new GridLayout(9, 2));			
				for(int i=0;i<8;i++){jp_edit.add(jl_list[i]);jp_edit.add(jtlist[i]);}
				jp_edit.add(jb_update);
				jp_edit.add(new JLabel(""));
				jfedit.setSize(400, 600);
				jfedit.setLocation(400, 200);
				jfedit.getContentPane().add(jp_edit, BorderLayout.CENTER);
				jfedit.setVisible(true);
					
					
				} catch (SQLException e1) {
					
					JOptionPane.showMessageDialog(null, "sql���ʧ��,����д��ȷ��Ա���.\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
					e1.printStackTrace();
					return ;
					
					
					
					
				}
				
				
				
				
				
				
				
			}
		});
    	  jp_search.setLayout(new BorderLayout());
    	  JPanel jp_search_north=new JPanel();
    	  jp_search_north.setLayout(new GridLayout(2, 1));
    	 // jp_search_north.add(jl_wayTosearch);
    	  JPanel jp_search_north_r1c2=new JPanel();
    	  jp_search_north_r1c2.setLayout(new FlowLayout());
    	  jp_search_north_r1c2.add(jl_wayTosearch);
    	  for(Checkbox cb:boxmap.values()){
    		  jp_search_north_r1c2.add(cb);
    		  
    	  }
    	  //`````````````````````````````�ұ�ɨ��
    	 final JTextArea jt_scanf=new JTextArea();
  		jt_scanf.setEditable(true);
  		jt_scanf.setVisible(true);
  		
  		final Vector<JTable> tableVec=new Vector<JTable>();
  		tableVec.clear();
  		final Document doc=jt_scanf.getDocument();
  		final  Vector<String> tableheaderNameVec=new Vector<String>();
  		final  Vector<Vector<String>> tableDateRowVec=new Vector<>();
  		final  JButton jb_scanf_edit=new JButton("�޸�ѡ����");
  		jb_scanf_edit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//�޸�
				if(tableVec.size()!=1 )return;
				if(tableVec.elementAt(0).getSelectedRow()==-1){
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�޸ĵ���");
					return;
				}
				//��Ҫ�޸ĵ���
				final JFrame jfedit=new JFrame();
				final JButton jb_update=new JButton("����");
				final JTable jt=tableVec.elementAt(0);
				String goodsid=(String)jt.getValueAt(jt.getSelectedRow(), 0);//��һ������
				Connection con=dBconncetion.getCon();
				try {
					Statement s=con.createStatement();
					ResultSet r= s.executeQuery(" select * from "+Connecter.TABLENAME_GOODS+" where goodsid="+goodsid);
					ResultSetMetaData colnums=r.getMetaData();
					if(!r.next()){return;}
					final JTextField[] jtlist=new JTextField[8];
					for(int i=0;i<8;i++)jtlist[i]=new JTextField();
					JLabel[]  jl_list=new JLabel[8];
					for(int i=0;i<8;i++) jl_list[i]=new JLabel("");
					for(int i=0;i<8;i++){
						if(i>=6) jtlist[i].setText(""+r.getInt(i+1));
						else{    jtlist[i].setText(r.getString(i+1));     }
						jl_list[i].setText(getChineseName(colnums.getColumnName(i+1)));	
						if(i==0)jtlist[i].setEditable(false);
					}
						jb_update.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							for(JTextField jt:jtlist){
								if(jt.getText().equalsIgnoreCase("") || jt.getText()==null){
									JOptionPane.showMessageDialog(null, "����:��������д");
									return;
								}
								
							}
							try {
								int price=Integer.parseInt(jtlist[6].getText());
								int amount=Integer.parseInt(jtlist[7].getText());
								Connection con=dBconncetion.getCon();
								PreparedStatement ps=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_Goods_update));
							    for(int i=1;i<=7;i++){ps.setString(i, jtlist[i].getText());}
								//���ã�
							    ps.setString(8, jtlist[0].getText());
								ps.executeUpdate();
								JOptionPane.showMessageDialog(null, "���³ɹ�");
								DefaultTableModel dfm=(DefaultTableModel )jt.getModel();
								dfm.removeRow(jt.getSelectedRow());
								jt.validate();
								jt.repaint();
								jfedit.dispose();

								
							} catch (SQLException e1) {
								
								JOptionPane.showMessageDialog(null, "sql���ʧ��,����д��ȷ��Ա���.\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
								e1.printStackTrace();
								return;
								
								
							} catch (Exception e1) {
								// TODO: handle exception
								JOptionPane.showMessageDialog(null, "����:����ȷ����"+"\n����:"+e1.getMessage());											
								e1.printStackTrace();
								return;
							}
							
							
							
							
						}
					});
			  JPanel jp_edit=new JPanel(new GridLayout(9, 2));			
				for(int i=0;i<8;i++){jp_edit.add(jl_list[i]);jp_edit.add(jtlist[i]);}
				jp_edit.add(jb_update);
				jp_edit.add(new JLabel(""));
				jfedit.setSize(400, 600);
				jfedit.setLocation(400, 200);
				jfedit.getContentPane().add(jp_edit, BorderLayout.CENTER);
				jfedit.setVisible(true);
					
					
				} catch (SQLException e1) {
					
					JOptionPane.showMessageDialog(null, "sql���ʧ��,����д��ȷ��Ա���.\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
					e1.printStackTrace();
					return ;
					
					
					
					
				}
				
				
				
			}
		});

  		jt_scanf.addKeyListener(new KeyListener() {
  			
  			@Override
  			public void keyTyped(KeyEvent e) {

  			}
  			
  			@Override
  			public void keyReleased(KeyEvent e) {
  			
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
  		

    	  
    	  //�����``````````````````
    	  jp_search_north.add(jp_search_north_r1c2);
    	  //jp_search_north.add(jl_title);
    	  JPanel jp_search_north_r2c2=new JPanel();
    	  jp_search_north_r2c2.setLayout(new GridLayout(1, 4));
    	  jp_search_north_r2c2.add(jl_title);
    	  jp_search_north_r2c2.add(jt);
    	  jp_search_north_r2c2.add(jb_search);
    	  jp_search_north_r2c2.add(jb_edit);
    	  jp_search_north.add(jp_search_north_r2c2);
    	  jp_search_north.validate();
    	  jp_search_north.repaint();
    	  jp_search.add(jp_search_north,BorderLayout.NORTH);
    	  jp_search.add(jp_table, BorderLayout.CENTER);
    	  jp_search.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "���һ���"));
    	  jp_search.validate();
    	  jp_search.repaint();
    	 //	`````�ұ�north 
    	  JPanel jp_getMoney_r3=new JPanel(new GridLayout(1, 3));
  		  jp_getMoney_r3.add(new JLabel("����ɨ���:"));
  		  jp_getMoney_r3.add(jt_scanf);
  		  jp_getMoney_r3.add(jb_scanf_edit);
  		  doc.addDocumentListener(new DocListener(jt_scanf, tableheaderNameVec, tableDateRowVec, jp_tableDetail, null, tableVec,jp_getMoney_r3,jb_scanf_edit));
    	  jp_getMoney_r3.setBorder(new LineBorder(Color.black));
    	  jp_getMoney_r3.validate();
    	  jp_getMoney_r3.repaint();
    	  jp_detialPanel.setLayout(new BorderLayout());
    	  jp_detialPanel.add(jp_getMoney_r3,BorderLayout.NORTH);
    	  jp_detialPanel.add(jp_tableDetail, BorderLayout.CENTER);
    	  jp_detialPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "ɨ���ѯ"));
    	  jp_detialPanel.validate();
    	  jp_detialPanel.repaint();
 
    	  //````````````````
    	  allComponetPanel.setLayout(new GridLayout(1, 2));
    	  allComponetPanel.add(jp_search);
    	  allComponetPanel.add(jp_detialPanel);
    	  allComponetPanel.validate();
    	  allComponetPanel.repaint();
	}
	
	public void returnBackGoodsPanelPaint(JPanel jp) {
		 final JTextArea jt_scanf=new JTextArea();
		 final JPanel jp_detialPanel=new JPanel();//�ұ�ɨ���
		 final JScrollPane jp_tableDetail=new JScrollPane();//���ѱ��
		 final JPanel allComponetPanel=jp;//������������
	  		jt_scanf.setEditable(true);
	  		jt_scanf.setVisible(true);
	  		
	  		final Vector<JTable> tableVec=new Vector<JTable>();
	  		tableVec.clear();
	  		final Document doc=jt_scanf.getDocument();
	  		final  Vector<String> tableheaderNameVec=new Vector<String>();
	  		final  Vector<Vector<String>> tableDateRowVec=new Vector<>();
	  		final  JButton jb_scanf_edit=new JButton("����ѡ����");
	  		jb_scanf_edit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//�޸�
					if(tableVec.size()!=1 )return;
					if(tableVec.elementAt(0).getSelectedRow()==-1){
						JOptionPane.showMessageDialog(null, "��ѡ��Ҫ���յĻ���");
						return;
					}
					//��Ҫ�޸ĵ���
					final JFrame jfedit=new JFrame();
					final JButton jb_update=new JButton("�˻�");
					final JTable jt=tableVec.elementAt(0);
					String goodsid=(String)jt.getValueAt(jt.getSelectedRow(), 0);//��һ������
					Connection con=dBconncetion.getCon();
					try {
						Statement s=con.createStatement();
						ResultSet r= s.executeQuery(" select * from "+Connecter.TABLENAME_GOODS+" where goodsid="+goodsid);
						ResultSetMetaData colnums=r.getMetaData();
						if(!r.next()){return;}
						final JTextField[] jtlist=new JTextField[8];
						for(int i=0;i<8;i++)jtlist[i]=new JTextField();
						JLabel[]  jl_list=new JLabel[8];
						for(int i=0;i<8;i++) jl_list[i]=new JLabel("");
						for(int i=0;i<8;i++){
							if(i>=6) jtlist[i].setText(""+r.getInt(i+1));
							else{    jtlist[i].setText(r.getString(i+1));     }
							jl_list[i].setText(getChineseName(colnums.getColumnName(i+1)));	
							jtlist[i].setEditable(false);
						}
						jtlist[7].setVisible(false);
							jb_update.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {

								try {
									int price=Integer.parseInt(jtlist[6].getText());
									int amount=Integer.parseInt(jtlist[7].getText());
									amount+=1;//�˻�
									jtlist[7].setText(amount+"");
									Connection con=dBconncetion.getCon();
									PreparedStatement ps=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_Goods_update));
								    for(int i=1;i<=7;i++){ps.setString(i, jtlist[i].getText());}
									//���ã�
								    ps.setString(8, jtlist[0].getText());
									ps.executeUpdate();
									if(!ps.isClosed())ps.close();
									//�õ�����
									Statement s1=con.createStatement();
									ResultSet r=s1.executeQuery(" select buydate ,vipid from "+Connecter.TABLENAME_SELL +" where sellid = "+ jtlist[0].getText()+" ;");
									if(!r.next()){JOptionPane.showMessageDialog(null, "�����޼�¼");return;}
									
									//SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
									Timestamp date=r.getTimestamp(1);
									int       vipid=r.getInt(2);
									System.out.println("date:"+date+" "+"vipid:"+vipid);
									if(!s1.isClosed())s1.close();
									//����selltable
									ps=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_sellBack_Update_selltable));
									ps.setTimestamp(1, date);
									ps.setString(2, jtlist[0].getText());
									ps.setInt(3,vipid);
									ps.executeUpdate();
									if(!ps.isClosed())ps.close();
									//�õ��������
									ps=con.prepareStatement(" select actalget from " + Connecter.TABLENAME_ONCEDETIAL+" where buydate= ? and vipid= ? ");
									ps.setTimestamp(1, date);
									ps.setInt(2, vipid);
									//����oncedetial
									r=ps.executeQuery();
									r.next();
									int actalget=r.getInt(1);
									ps=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_sellBack_UpdateOnceDetial));
									if(price>actalget)price=actalget;
									ps.setInt(1, price);
									ps.setTimestamp(2, date);
									ps.setInt(3, vipid);
									ps.executeUpdate();
									//```````
									//���»�Ա��
									ps=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_updateViptable));
									ps.setInt(1, price);
									ps.setInt(2, vipid);
									ps.executeUpdate();
			
									//```````
									JOptionPane.showMessageDialog(null, "�˻��ɹ�");
									DefaultTableModel dfm=(DefaultTableModel )jt.getModel();
									dfm.removeRow(jt.getSelectedRow());
									jt.validate();
									jt.repaint();
									jfedit.dispose();

									
								} catch (SQLException e1) {
									
									JOptionPane.showMessageDialog(null, "sql���ʧ��\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
									e1.printStackTrace();
									return;
									
									
								} catch (Exception e1) {
									// TODO: handle exception
									JOptionPane.showMessageDialog(null, "����:����ȷ����"+"\n����:"+e1.getMessage());											
									e1.printStackTrace();
									return;
								}
								
								
								
								
							}
						});
				  JPanel jp_edit=new JPanel(new GridLayout(9, 2));			
					for(int i=0;i<8;i++){jp_edit.add(jl_list[i]);jp_edit.add(jtlist[i]);}
					jp_edit.add(jb_update);
					jp_edit.add(new JLabel(""));
					jfedit.setSize(400, 600);
					jfedit.setLocation(400, 200);
					jfedit.getContentPane().add(jp_edit, BorderLayout.CENTER);
					jfedit.setVisible(true);
						
						
					} catch (SQLException e1) {
						
						JOptionPane.showMessageDialog(null, "sql���ʧ��,����д��ȷ��Ա���.\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
						e1.printStackTrace();
						return ;
						
						
						
						
					}
					
					
					
				}
			});
	  		 JPanel jp_getMoney_r3=new JPanel(new GridLayout(1, 3));
	  		  jp_getMoney_r3.add(new JLabel("����ɨ���:"));
	  		  jp_getMoney_r3.add(jt_scanf);
	  		  jp_getMoney_r3.add(jb_scanf_edit);
	  		  doc.addDocumentListener(new DocListener1(jt_scanf, tableheaderNameVec, tableDateRowVec, jp_tableDetail, null, tableVec,jp_getMoney_r3,jb_scanf_edit));
	    	  jp_getMoney_r3.setBorder(new LineBorder(Color.black));
	    	  jp_getMoney_r3.validate();
	    	  jp_getMoney_r3.repaint();
	    	  jp_detialPanel.setLayout(new BorderLayout());
	    	  jp_detialPanel.add(jp_getMoney_r3,BorderLayout.NORTH);
	    	  jp_detialPanel.add(jp_tableDetail, BorderLayout.CENTER);
	    	  jp_detialPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "ɨ���˻�"));
	    	  jp_detialPanel.validate();
	    	  jp_detialPanel.repaint();
	    	  
	    	  allComponetPanel.setLayout(new GridLayout(1, 2));
	    	  allComponetPanel.add(jp_detialPanel);
	    	  
	  		
	}
	
	
	
	
	
	
	
	public void amongSearch(JScrollPane jp,CheckboxGroup cgroup,Map<String, Checkbox> map,JTextField jTextField,Vector<JTable> tabVec_among) {
		final JScrollPane jp_searchDetail=jp;
		final CheckboxGroup checkGroup=cgroup;
		final Map<String, Checkbox> checlMap=map;
		final JTextField jt=jTextField;
		if( checkGroup.getSelectedCheckbox()==null){
			JOptionPane.showMessageDialog(null, "����:δѡ���ѯ��ʽ");
			return;
		}
		if((jt.getText().equalsIgnoreCase("") || jt.getText()==null  ) &&  checkGroup.getSelectedCheckbox().getLabel().equalsIgnoreCase("������")==false){
			//����������ѯ�ͱ�����
			JOptionPane.showMessageDialog(null, "����:����ȷ��д��Ҫ��ѯ����Ϣ\nע:������Ҫ�ð��.�ֿ�����:8.23");
			return;
		}
		String checkboxString=cgroup.getSelectedCheckbox().getLabel();//�õ��ַ���
		java.sql.PreparedStatement s=null;
    	  ResultSet r=null;
    	  ResultSetMetaData tableheader=null;
    	  Vector<String> tableheaderNameVec=new Vector<String>();
    	  Vector<Vector<String>> tableDateRowVec=new Vector<>();
    	  Connection con=dBconncetion.getCon();
    	  if(checkboxString.equalsIgnoreCase("����") || checkboxString.equalsIgnoreCase("����") || checkboxString.equalsIgnoreCase("����")){
    		  checkboxString="��Ʒ"+checkboxString;
    		  
    	  }
		  final JTable jTable=creatSearchTableByType(jt, checkboxString, s, r, tableheader, tableheaderNameVec, tableDateRowVec, con);
			  if(jTable==null){return ;}	
			  jTable.setCellSelectionEnabled(false);
			  jTable.setRowSelectionAllowed(true);
			  jTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			  tabVec_among.clear();
			  tabVec_among.addElement(jTable);
			  jp_searchDetail.setViewportView(jTable);
			  jp_searchDetail.setBorder(new LineBorder(Color.black));
			  jp.validate();
			  jp.repaint();
		
	}
		
	public JTable creatSearchTableByType(JTextField jt ,String checkboxString  , java.sql.PreparedStatement s, ResultSet r,ResultSetMetaData tableheader,Vector<String> tableheaderNameVec,Vector<Vector<String>> tableDateRowVec,Connection con) {
		 JTable jtable=null;
		try {
			
			if(checkboxString.equalsIgnoreCase(getChineseName("price"))){
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_Goods_searchby_price));
				s.setInt(1, Integer.parseInt(jt.getText()));
				r=s.executeQuery();//��ѯ��
				  tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
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
				
			}else if (checkboxString.equalsIgnoreCase(getChineseName("style"))) {
				
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_Goods_searchby_style));
	
					s.setString(1,  "%"+jt.getText()+ "%");
					
				
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
				
				
				
			}else if (checkboxString.equalsIgnoreCase(getChineseName("goodsname"))) {
			
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_Goods_searchby_goodsname));
				s.setString(1,  "%"+jt.getText()+ "%");
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
				
				
				
			}else if (checkboxString.equalsIgnoreCase(getChineseName("lining"))) {	
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_Goods_searchby_lining));
				s.setString(1,  "%"+jt.getText()+ "%");
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
			}else if (checkboxString.equalsIgnoreCase(getChineseName("size"))) {	
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_Goods_searchby_size));
				//System.out.println(toDateString(jt.getText(), 2));
				s.setString(1,  "%"+jt.getText()+ "%");
				r=s.executeQuery();//��ѯ��
				  tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
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
			else if (checkboxString.equalsIgnoreCase(getChineseName("amount"))) {	
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_Goods_searchby_amount));
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
			else if (checkboxString.equalsIgnoreCase(getChineseName("color"))) {	
				s=con.prepareStatement(dBconncetion.getSqlStatementMap().get(Connecter.sql_mode_Goods_searchby_color));
				s.setString(1,  "%"+jt.getText()+ "%");
				r=s.executeQuery();//��ѯ��
				  tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
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
			}else if (checkboxString.equalsIgnoreCase("������")) {	
				Statement s1=con.createStatement();
				
				  r=s1.executeQuery("select sum(amount) allgoods from "+Connecter.TABLENAME_GOODS+"  ;");//��ѯ��
				  tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  while(r.next()){
					  //����һ��
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						 
						  v.add(""+r.getInt(1));//��ӽ�ȥ							  
					  }
					  tableDateRowVec.add(v);
				  }
				  DefaultTableModel dtmod=new DefaultTableModel(tableDateRowVec, tableheaderNameVec);
				  if(!r.isClosed())r.close();
				  if(!s1.isClosed())s1.close();//�ر�����
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
	
	public JTable creatJtable(String sql, Statement s,java.sql.PreparedStatement s1, ResultSet r,ResultSetMetaData tableheader,Vector<String> tableheaderNameVec,Vector<Vector<String>> tableDateRowVec,Connection con) {
		try {
			
			JTable jtable=null;
			if(sql==null){
				//Ԥ��������
				
				r=s1.executeQuery();
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
				//if(!r.next())return null;
				tableheader=r.getMetaData();
				  int tableColumNum= tableheader.getColumnCount();
				
				  for(int i=1;i<=tableColumNum;i++)tableheaderNameVec.addElement(getChineseName(tableheader.getColumnName(i)));
				  //������ͷvec
				  while(r.next()){
					  //����һ��
					  System.out.println(1);
					  Vector<String> v=new Vector<String>();
					  for(int j=1;j<=tableColumNum;j++){
						 
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
		if(thisone==searchAndUpdateLable){//``````````````````````````````````�������޸�
			 thisone.setForeground(Color.BLUE);
	    	  basePanle.removeAll();//������嶼�Ƴ�
	    	  detPanle.removeAll();//�ù�������Ƴ�
	    	  //```````````````````````````bounder
	    	  final JPanel allPanel=new JPanel();
	    	  
	    	  searchPabelPaint(allPanel);
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  detPanle.setLayout(new BorderLayout());
	    	  detPanle.add(allPanel, BorderLayout.CENTER);
	    	  
	    	  
	    	  //````````````````````````````bouder
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
		else if (thisone==insertGoodsLable) {//```````````````````````````````¼�����````````````````````````````````````````````
			  thisone.setForeground(Color.BLUE);
	    	  basePanle.removeAll();//������嶼�Ƴ�
	    	  detPanle.removeAll();//�ù�������Ƴ�
	    	  //```````````````````````````bounder
			  
	    	  final JPanel insertPanel=new JPanel();
	    	  insertPanelPaint(insertPanel);
	    	  
	    	  detPanle.setLayout(new BorderLayout());
	    	  detPanle.add(insertPanel,BorderLayout.CENTER);
	    	  //````````````````````````````bouder
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
		else if (thisone==returnbackLable) {//```````````````````````````````¼�����````````````````````````````````````````````
			  thisone.setForeground(Color.BLUE);
	    	  basePanle.removeAll();//������嶼�Ƴ�
	    	  detPanle.removeAll();//�ù�������Ƴ�
	    	  //```````````````````````````bounder
			  
	    	  final JPanel returnbackPanel=new JPanel();
	    	  returnBackGoodsPanelPaint(returnbackPanel);
	    	  
	    	  detPanle.setLayout(new BorderLayout());
	    	  detPanle.add( returnbackPanel,BorderLayout.CENTER);
	    	  //````````````````````````````bouder
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
	@Override
	public JLabel getFirstLable() {
		return insertGoodsLable;
	}
	@Override
	public JLabel[] getJlabelList() {
		return new JLabel[]{
				searchAndUpdateLable,
				insertGoodsLable,
				returnbackLable
		};
	}
	@Override
	public JButton getButton() {
		return goodsSelectButton;
	}
	class LabAndJtxt{
		public JTextField jt;
		public JLabel jl;
		public int state;
		public JTextArea jta;
	
		public LabAndJtxt(JTextField jt,JLabel jl,JTextArea jta) {
			// TODO Auto-generated constructor stub
			this.jt=jt;
			this.jl=jl;
			state=-1;//0��1��-1��ʼ
			this.jta=jta;
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
		public JButton jupdate;
		public DocListener(JTextArea jt_scanf,Vector<String> tableheaderNameVec,Vector<Vector<String>> tableDateRowVec,JScrollPane js_tableDetialGoods,Vector<Integer> vipId,Vector<JTable> tableVec,JPanel jp_txtpanel,JButton jupdate) {
			this. jt_scanf=jt_scanf;
			this.tableheaderNameVec= tableheaderNameVec;
			this.js_tableDetialGoods=js_tableDetialGoods;
			this.tableDateRowVec=tableDateRowVec;
			this.vipId=vipId;
			this.tableVec=tableVec;
			this.jp_getMoney_r3=jp_txtpanel;
			this.jupdate=jupdate;
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
					tableDateRowVec.clear();
					JTable jt_goodsdetial=  creatJtable(sqlstr, s, null, r, tableheader, tableheaderNameVec, tableDateRowVec, con);
					if(jt_goodsdetial==null){
						
						return;
						
					}
				
				
					
					tableVec.clear();
					tableVec.add(jt_goodsdetial);//�±�
					jt_goodsdetial.setCellSelectionEnabled(false);
					jt_goodsdetial.setRowSelectionAllowed(true);
					jt_goodsdetial.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
					jt_goodsdetial.setRowSelectionInterval(tableDateRowVec.size()-1, tableDateRowVec.size()-1);//���һ��
					js_tableDetialGoods.setViewportView(jt_goodsdetial);
					js_tableDetialGoods.validate();
					js_tableDetialGoods.repaint();
					JTextArea  jnew=new JTextArea();
					jnew.setText("");
					Document doc= jnew.getDocument();
					doc.addDocumentListener(new DocListener(jnew, tableheaderNameVec, tableDateRowVec, js_tableDetialGoods, vipId, tableVec, jp_getMoney_r3,jupdate));
				    // jp_getMoney_r3=new JPanel(new GridLayout(1, 3));
					jp_getMoney_r3.removeAll();
					jp_getMoney_r3.setLayout(new GridLayout(1, 3));
					jp_getMoney_r3.add(new JLabel("ɨ���:"));
					
					
					jp_getMoney_r3.add(jnew);
					
					jp_getMoney_r3.add(jupdate);
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
	class DocListener1 implements DocumentListener{
		public JTextArea jt_scanf;
		public Vector<String> tableheaderNameVec;
		public Vector<Vector<String>> tableDateRowVec;
		public JScrollPane js_tableDetialGoods;
		public Vector<Integer> vipId;
		public Vector<JTable> tableVec;
		public JPanel jp_getMoney_r3;
		public JButton jupdate;
		public DocListener1(JTextArea jt_scanf,Vector<String> tableheaderNameVec,Vector<Vector<String>> tableDateRowVec,JScrollPane js_tableDetialGoods,Vector<Integer> vipId,Vector<JTable> tableVec,JPanel jp_txtpanel,JButton jupdate) {
			this. jt_scanf=jt_scanf;
			this.tableheaderNameVec= tableheaderNameVec;
			this.js_tableDetialGoods=js_tableDetialGoods;
			this.tableDateRowVec=tableDateRowVec;
			this.vipId=vipId;
			this.tableVec=tableVec;
			this.jp_getMoney_r3=jp_txtpanel;
			this.jupdate=jupdate;
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
					String sqlstr=" select goodsid ,style,size,lining,goodsname,color,price,amount,buydate from "+ Connecter.TABLENAME_GOODS+" g, "+Connecter.TABLENAME_SELL+" s"+ " where goodsid= " + goodsid+" and s.sellid=g.goodsid ;" ;
					ResultSet r=null;
					ResultSetMetaData tableheader=null;
					tableheaderNameVec.clear();
					tableDateRowVec.clear();
					JTable jt_goodsdetial=  creatJtable(sqlstr, s, null, r, tableheader, tableheaderNameVec, tableDateRowVec, con);
					if(jt_goodsdetial==null){
						
						return;
						
					}
			
					tableVec.clear();
					tableVec.add(jt_goodsdetial);//�±�
					jt_goodsdetial.setCellSelectionEnabled(false);
					jt_goodsdetial.setRowSelectionAllowed(true);
					jt_goodsdetial.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
					jt_goodsdetial.setRowSelectionInterval(tableDateRowVec.size()-1, tableDateRowVec.size()-1);//���һ��
					js_tableDetialGoods.setViewportView(jt_goodsdetial);
					js_tableDetialGoods.validate();
					js_tableDetialGoods.repaint();
					JTextArea  jnew=new JTextArea();
					jnew.setText("");
					Document doc= jnew.getDocument();
					doc.addDocumentListener(new DocListener(jnew, tableheaderNameVec, tableDateRowVec, js_tableDetialGoods, vipId, tableVec, jp_getMoney_r3,jupdate));
				    // jp_getMoney_r3=new JPanel(new GridLayout(1, 3));
					jp_getMoney_r3.removeAll();
					jp_getMoney_r3.setLayout(new GridLayout(1, 3));
					jp_getMoney_r3.add(new JLabel("ɨ���:"));
					
					
					jp_getMoney_r3.add(jnew);
					
					jp_getMoney_r3.add(jupdate);
					jp_getMoney_r3.validate();
					jp_getMoney_r3.repaint();
					jnew.setVisible(true);
					jnew.requestFocus();
					
				}
				
				
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "sql�������ʧ��\n�������:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
				e1.printStackTrace();
				
			}
			catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "����:"+"\n����:"+e1.getMessage());											
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
	
}
