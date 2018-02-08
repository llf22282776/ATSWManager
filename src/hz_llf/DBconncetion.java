package hz_llf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.jar.Attributes.Name;

import javax.swing.JOptionPane;

public class DBconncetion {
		//���ݿ�������
	private String connectionUrl;
	private String username;
	private String pwd;
	private Connection con;
	private Vector<Connecter> conlist;//���е����Ӷ���������
	
	public DBconncetion(String conurl,String username,String pwd) {
		// TODO Auto-generated constructor stub
		this.connectionUrl=conurl;
		this.username=username;
		this.pwd=pwd;
		this.conlist=new Vector<Connecter>();
		
	}
	
	public boolean	canConnect() {
		try {
			con=DriverManager.getConnection(connectionUrl, username, pwd);
		
		
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "sql����ʧ��:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
			e1.printStackTrace();
			return false;
		}//�������ݿ�
	try {
		if(!con.isClosed())con.close();
	} catch (SQLException e1) {
		JOptionPane.showMessageDialog(null, "sql����ʧ��:"+e1.getErrorCode()+"\n����:"+e1.getMessage());											
		e1.printStackTrace();
	}	
	return true;
	
	
	
}
	
	public synchronized Connecter getOneConnecter(int modetype) {
		Connection tempconnect=null;
		try {
		       tempconnect=DriverManager.getConnection(connectionUrl, username, pwd);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		}
		Connecter con1=new Connecter(tempconnect, modetype);//����һ���ض�������
		conlist.add(con1);//����
		return con1;//�����������
		
		
		
	}
	public void  relaseAllConnector() throws SQLException {
		for(Connecter c:conlist){
			if(c.getCon().isClosed()==false)
			c.getCon().close();
			
		}
		
		
		
	}
	
	
	
}