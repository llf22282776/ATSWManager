package hz_llf;

import java.beans.DefaultPersistenceDelegate;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class Connecter {
		
	
		private Connection con;
		private Map<Integer, String> sqlStatementMap;//语句和意义组成的键值对集合
		private int contype;
		public static final String VIP_BIRTHDAYBASEYEAR="2010";
		public static final String TABLENAME_VIP="viptable";
		public static final String TABLENAME_GOODS="goodstable";
		public static final String TABLENAME_SELL="selltable";
		public static final String TABLENAME_ONCEDETIAL="oncedetial";
		public static final int sql_mode_vip_searchby_id=1;
		public static final int sql_mode_vip_searchby_phone=2;
		public static final int sql_mode_vip_searchby_name=3;
		public static final int sql_mode_vip_delete=4;
		public static final int sql_mode_vip_insert=5;
		public static final int sql_mode_vip_hoborry=6;
		public static final int sql_mode_vip_score=7;
		public static final int sql_mode_vip_birthday=8;
		public static final int sql_mode_vip_score_thismonth=9;
		public static final int sql_mode_vip_searchby_birthday = 10;
		public static final int sql_mode_vip_searchby_datejoin = 11 ;
		public static final int sql_mode_vip_searchby_score = 12;
		public static final int sql_mode_vip_searchOneMsg=13;
		public static final int sql_mode_sell_todaycount=14;
		public static final int sql_mode_sell_insert=15;
		public static final int sql_mode_sell_addScore=16;
		public static final int sql_mode_sell_reduceGoodsNum=17;
		public static final int sql_mode_sell_addGoodsNum=18;
		public static final int sql_mode_goods_insert=19;
		public static final int  sql_mode_Goods_searchby_style=20;
		public static final int  sql_mode_Goods_searchby_size =21;
		public static final int  sql_mode_Goods_searchby_lining=22;
		public static final int  sql_mode_Goods_searchby_goodsname=23;
		public static final int  sql_mode_Goods_searchby_color=24;
		public static final int  sql_mode_Goods_searchby_price=25;
		public static final int  sql_mode_Goods_searchby_amount=26;
		public static final int  sql_mode_Goods_update=27;
		public static final int  sql_mode_OnceDetial_insert=28;
		public static final int  sql_mode_OnceDetial_SearchBewteen=29;
		public static final int  sql_mode_vip_searchThereby_phone=30;
		public static final int  sql_mode_sellBack_Update_selltable=31;
		public static final int sql_mode_sellBack_UpdateOnceDetial=32;
		public static final int sql_mode_updateViptable=33;
		public static final int sql_mode_selectAllVip=34;
		
	public Connection getCon() {
			return con;
		}


		public void setCon(Connection con) {
			this.con = con;
		}


	public Connecter(Connection con,int contype) {
		
		this.con=con;
		this.contype=contype;
		this.sqlStatementMap=new HashMap<Integer, String>();
		setSQLstetment();//设置sql语句
		
		
		
	}
	private void setSQLstetment(){
		//设置特定的sql语句
		String viptable=Connecter.TABLENAME_VIP;
		String selltable=Connecter.TABLENAME_SELL;
		String goodstable=Connecter.TABLENAME_GOODS;
		String onceDetial=Connecter.TABLENAME_ONCEDETIAL;
			//``````搜索```````
		sqlStatementMap.put(Connecter. sql_mode_vip_searchby_id, 
				"select * from "+
				viptable+
				" where id="+				
				" ? ;");
		sqlStatementMap.put(Connecter.sql_mode_vip_searchby_phone,"select * from "+
				viptable+
				" where phone like ?;");
		sqlStatementMap.put(Connecter.sql_mode_vip_searchThereby_phone,"select fname,phone,id from "+
				viptable+
				" where phone like ?;");
		sqlStatementMap.put(Connecter. sql_mode_vip_searchby_name,"select * from "+
				viptable+
				" where fname like ?;");
		sqlStatementMap.put(Connecter. sql_mode_vip_searchby_birthday,"select * from "+
				viptable+
				" where birthday = ?;");
		sqlStatementMap.put(Connecter. sql_mode_vip_searchby_datejoin,"select * from "+
				viptable+
				" where datejoin= ?;");
		sqlStatementMap.put(Connecter. sql_mode_vip_searchby_score,"select * from "+
				viptable+
				" where score= ?;");
		sqlStatementMap.put(Connecter. sql_mode_vip_searchOneMsg,"select id, phone, fname, goodsid,goodsname,price from "+
				viptable+" v, "+selltable+" s, "+ goodstable+" g "+
				" where v.id =? and  v.id = s.vipid and g.goodsid=s.sellid and  s.buydate between ? and ? ;");
		
		
		
		//删除`````````
		sqlStatementMap.put(Connecter.sql_mode_vip_delete," delete from "+
				viptable+
				" where id=?;");
		
		
		
		//添加
		
		sqlStatementMap.put(Connecter.sql_mode_vip_insert," insert into "+
				viptable+" (id, birthday, phone, fname, datejoin, score) "+
				" values   (? , ? , ? , ? , ? , ? );");
		//喜好分析,就到这为止le
		sqlStatementMap.put(Connecter.sql_mode_vip_hoborry, " select ? ,count(*) num "+
							" from "+
							viptable + " v , "+selltable+" s, "+goodstable+" g "+
							" where v.id=s.vipid and s.sellid=g.goodsid "+
							" group by ? "+
							" order by num;"
							
		
				
				);
		//积分榜
		sqlStatementMap.put(Connecter.sql_mode_vip_score,
				" select * from "+viptable+ "  order by score desc limit 0 , 10 ;"
				);
		//当月生日会员,所以生日年都是2010
		sqlStatementMap.put(Connecter.sql_mode_vip_birthday, 
				
				" select * from "+viptable+" where birthday between ? " + " and ? order by birthday asc;"
			
				);
		//当月积分
		sqlStatementMap.put(Connecter.sql_mode_vip_score_thismonth,
				" select top 10  ,sum(price) p from "+
		viptable+" v, "+selltable+" s, "+goodstable+" g "+
		" where v.id=s.vipid and s.sellid=g.goodsid "+		
		" group by v.id "+
		" order by p  ;"
				);
		
		sqlStatementMap.put(Connecter.sql_mode_sell_todaycount,
				 "select goodsname,goodsid,price ,color,lining,size from "+
							viptable+" v, "+selltable+" s, "+ goodstable+" g "+
							" where  v.id = s.vipid and g.goodsid=s.sellid and  s.buydate between ? and ? ;");
		sqlStatementMap.put(Connecter.sql_mode_sell_insert,
				 " insert into "+ selltable +" values (?,?,?);");
		sqlStatementMap.put(Connecter.sql_mode_goods_insert,
				 " insert into "+ goodstable +" values (?,?,?,?,?,?,?,?);");
		sqlStatementMap.put(Connecter.sql_mode_sell_addScore,
				 " update "+ viptable +" set score=score + ? where id=? ;");
		sqlStatementMap.put(Connecter.sql_mode_sell_reduceGoodsNum,
				 " update "+ goodstable +" set amount=amount-1 where goodsid=? ;");
		sqlStatementMap.put(Connecter.sql_mode_sell_addGoodsNum,
				 " update "+ goodstable +" set amount=amount+1 where goodsid=? ;");
		sqlStatementMap.put(Connecter. sql_mode_Goods_searchby_style, 
				"select * from "+
				goodstable+
				" where style like  "+				
				" ? ;");
		sqlStatementMap.put(Connecter.sql_mode_Goods_searchby_size,"select * from "+
				goodstable+
				" where size like ?;");
		sqlStatementMap.put(Connecter. sql_mode_Goods_searchby_lining,"select * from "+
				goodstable+
				" where lining like ?;");
		sqlStatementMap.put(Connecter. sql_mode_Goods_searchby_goodsname,"select * from "+
				goodstable+
				" where goodsname like ?;");
		sqlStatementMap.put(Connecter. sql_mode_Goods_searchby_color,"select * from "+
				goodstable+
				" where color like ?;");
		sqlStatementMap.put(Connecter. sql_mode_Goods_searchby_price,"select * from "+
				goodstable+
				" where price = ?;");
		sqlStatementMap.put(Connecter. sql_mode_Goods_searchby_amount,"select * from "+
				goodstable+
				" where amount = ?;");
		sqlStatementMap.put(Connecter. sql_mode_Goods_update," update "+
				goodstable+
				" set style=?,size=?,lining=?,goodsname=?,color=?,price=?,amount=? where goodsid=?;");
		sqlStatementMap.put(Connecter. sql_mode_OnceDetial_insert," insert into "+
				onceDetial+
				" values(?,?,?);");
		sqlStatementMap.put(Connecter. sql_mode_OnceDetial_SearchBewteen," select sum(actalget) "+
				onceDetial+
				" where buydate between ? and ?;");
		sqlStatementMap.put(Connecter. sql_mode_sellBack_UpdateOnceDetial," update  "+
				onceDetial+
				" set actalget=actalget- ? "+
				" where buydate = ? and vipid = ?;");
		sqlStatementMap.put(Connecter. sql_mode_sellBack_Update_selltable," delete from  "+
				selltable+
				
				" where buydate = ? and sellid = ? and vipid= ?;");
		
		sqlStatementMap.put(Connecter. sql_mode_updateViptable," update   "+
				viptable+
				" set score=score-? "+
				" where id= ?;");
		
		sqlStatementMap.put(Connecter. sql_mode_selectAllVip," select * from "+
				viptable+
				" "+
				";");
		//
	}


	public Map<Integer, String> getSqlStatementMap() {
		return sqlStatementMap;
	}


	public void setSqlStatementMap(Map<Integer, String> sqlStatementMap) {
		this.sqlStatementMap = sqlStatementMap;
	}
	
	
	
	
	
	
	
}
