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
		if(str.equalsIgnoreCase("id"))return new String("��Ա���");
		if(str.equalsIgnoreCase("phone"))return new String("�绰");
		if(str.equalsIgnoreCase("fname"))return new String("����");
		if(str.equalsIgnoreCase("birthday"))return new String("����");
		if(str.equalsIgnoreCase("score"))return new String("����");
		if(str.equalsIgnoreCase("datejoin"))return new String("��������");
		
		if(str.equalsIgnoreCase("goodsid"))return new String("��Ʒ���");
		if(str.equalsIgnoreCase("goodsname"))return new String("��Ʒ����");
		if(str.equalsIgnoreCase("lining"))return new String("��Ʒ����");
		if(str.equalsIgnoreCase("style"))return new String("��Ʒ����");
		if(str.equalsIgnoreCase("color"))return new String("��ɫ");
		if(str.equalsIgnoreCase("price"))return new String("�۸�");
		if(str.equalsIgnoreCase("size"))return new String("��С");
		if(str.equalsIgnoreCase("amount"))return new String("����");
		if(str.equalsIgnoreCase("buydate"))return new String("��������");
		if(str.equalsIgnoreCase("totalmoney"))return new String("�ܽ��");
		if(str.equalsIgnoreCase("actalget"))return new String("ʵ�ս��");
		if(str.equalsIgnoreCase("allgoods"))return new String("�������");
		
		if(str.equalsIgnoreCase("��Ʒ���"))return new String("goodsid");
		if(str.equalsIgnoreCase("��Ʒ����"))return new String("goodsname");
		if(str.equalsIgnoreCase("��Ʒ����"))return new String("lining");
		if(str.equalsIgnoreCase("��Ʒ����"))return new String("style");
		if(str.equalsIgnoreCase("��ɫ"))return new String("color");
		if(str.equalsIgnoreCase("�۸�"))return new String("price");
		if(str.equalsIgnoreCase("��С"))return new String("size");
		if(str.equalsIgnoreCase("����"))return new String("amount");
		if(str.equalsIgnoreCase("ʵ�ս��"))return new String("actalget");
		if(str.equalsIgnoreCase("�������"))return new String("allgoods");
			
		if(str.equalsIgnoreCase("��Ա���"))return new String("id");
		if(str.equalsIgnoreCase("�绰"))return new String("phone");
		if(str.equalsIgnoreCase("����"))return new String("fname");
		if(str.equalsIgnoreCase("����"))return new String("birthday");
		if(str.equalsIgnoreCase("����"))return new String("score");
		if(str.equalsIgnoreCase("��������"))return new String("datejoin");		
		if(str.equalsIgnoreCase("��������"))return new String("buydate");
		
		
		
		
		return null;			
	}
	public String toDateString(String str,int mode) {
		try {
			if(mode==1){//����
			
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
		JOptionPane.showMessageDialog(null, "ת������");
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
		
	
	
}
