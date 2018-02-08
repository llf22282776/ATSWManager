package hz_llf;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import hz_llf.SellManger.DocListener;

public abstract class Amanger {
	private boolean isFirstCount;

	
	public boolean isFirstCount() {
		return isFirstCount;
	}
	public void setFirstCount(boolean isFirstCount) {
		this.isFirstCount = isFirstCount;
	}
	public void setlastOne(JLabel jl){
		
		
	}
    public JLabel getlastOne(){
		return null;
		
	}
	public int  getModeState () {
		return 0;
		
	}
	public void  actionAsSelected(JLabel jLabel) {
		
	}
	public JLabel getFirstLable() {
		return null;
	}
	public JLabel[] getJlabelList() {
		return null;
	}
	public JButton getButton() {
		return null;
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
		if(str.equalsIgnoreCase("actalget"))return new String("实收金额");
		if(str.equalsIgnoreCase("allgoods"))return new String("库存总数");
		
		if(str.equalsIgnoreCase("商品编号"))return new String("goodsid");
		if(str.equalsIgnoreCase("商品名称"))return new String("goodsname");
		if(str.equalsIgnoreCase("商品面料"))return new String("lining");
		if(str.equalsIgnoreCase("商品类型"))return new String("style");
		if(str.equalsIgnoreCase("颜色"))return new String("color");
		if(str.equalsIgnoreCase("价格"))return new String("price");
		if(str.equalsIgnoreCase("大小"))return new String("size");
		if(str.equalsIgnoreCase("数量"))return new String("amount");
		if(str.equalsIgnoreCase("实收金额"))return new String("actalget");
		if(str.equalsIgnoreCase("库存总数"))return new String("allgoods");
			
		if(str.equalsIgnoreCase("会员编号"))return new String("id");
		if(str.equalsIgnoreCase("电话"))return new String("phone");
		if(str.equalsIgnoreCase("姓名"))return new String("fname");
		if(str.equalsIgnoreCase("生日"))return new String("birthday");
		if(str.equalsIgnoreCase("积分"))return new String("score");
		if(str.equalsIgnoreCase("加入日期"))return new String("datejoin");		
		if(str.equalsIgnoreCase("购买日期"))return new String("buydate");
		
		
		
		
		return null;			
	}
	public String toDateString(String str,int mode) {
		try {
			if(mode==1){//生日
			
			return new String(Connecter.VIP_BIRTHDAYBASEYEAR+"-"+str.substring(0,str.indexOf("."))+"-"+str.substring(str.indexOf(".")+".".length()));
			
			
		}else if(mode==2){
			
			return new String(str.substring(0,str.indexOf("."))+"-"+str.substring(str.indexOf(".")+".".length(),str.indexOf(".", str.indexOf(".")+".".length()))+"-"+str.substring(str.indexOf(".",str.indexOf(".", str.indexOf(".")+".".length()))+".".length()));
			
		}else if(mode==3){
			System.out.println("str "+str);
			String s=new String(str.substring(0,str.indexOf("."))+"-"+str.substring(str.indexOf(".")+".".length(),str.indexOf(".", str.indexOf(".")+".".length()))+"-"+str.substring(str.indexOf(".",str.indexOf(".", str.indexOf(".")+".".length()))+".".length()));
			return new String(s+" 00:00:00");
			
		}else {
			String s=new String(str.substring(0,str.indexOf("."))+"-"+str.substring(str.indexOf(".")+".".length(),str.indexOf(".", str.indexOf(".")+".".length()))+"-"+str.substring(str.indexOf(".",str.indexOf(".", str.indexOf(".")+".".length()))+".".length()));
			return new String(s+" 23:59:59");
		}
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null, "转换错误");
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
		
	
	
}
