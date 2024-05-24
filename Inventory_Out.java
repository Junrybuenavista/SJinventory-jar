import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableCellRenderer;

public class Inventory_Out extends JInternalFrame implements ActionListener{
    
    Connection conn;
    Statement st;
    ResultSet set;
    MyTable tab;
    String arr2[][]=null; 
    String arr[];	
    JButton b1,b2;
    DecimalFormat form;
    JDesktopPane desk;
  
    public void actionPerformed(ActionEvent e)
    	{
    		if(e.getSource()==b1)
    			{
    				new SearchItem(this);
    			}
    		if(e.getSource()==b2)
    			{
    			    this.dispose();
    			}
    	} 	
    public Inventory_Out(JDesktopPane desk) {
    	
    	super("OUT STOCK INVENTORY", true, // resizable
					true, // closable
					true, // maximizable
					true);
		this.desk=desk;			
    	
    	 try{ 
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  				
    		conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=Database1.mdb;DriverID=22;READONLY=true;) ","","");    				    			
    	    st=conn.createStatement();
    				 							 
	      }catch(Exception e){e.printStackTrace();}
	    Container con=getContentPane();
	    con.setLayout(new BorderLayout());
	    form=new DecimalFormat("###,###.00");
	    
	    b1=new JButton("SEARCH");b1.addActionListener(this);
	    b2=new JButton("CLOSE");b2.addActionListener(this);
	    	    
	    String arr[]={"ID","ITEM DESC.","QUANTITY","UNIT","DATE PURCHASE","CUSTOMER NAME","SRP","TOTAL AMOUNT","NOTE"};                     
        tab=new MyTable();
        tab.setData(arr2,arr);
        tab.getTb().getColumnModel().getColumn(0).setMinWidth(0);
		tab.getTb().getColumnModel().getColumn(0).setMaxWidth(0);
		
	
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		tab.getTb().getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tab.getTb().getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		tab.getTb().getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		tab.getTb().getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
	    
	    JPanel pn=new JPanel(){public Dimension getPreferredSize(){return new Dimension(400,450);}};
	 
	    pn.setLayout(new BorderLayout());
	    pn.add(new JScrollPane(tab.getTb()),"Center"); 
	    JPanel ps=new JPanel(){public Dimension getPreferredSize(){return new Dimension(400,150);}};
	    ps.add(b1);ps.add(b2);
	    
	    
	    con.add(pn,"North");
	    con.add(ps,"Center"); 
	    load("SELECT * FROM OutStock ");	 
	    	
	    show();
	    desk.add(this); 
    	setSize(1100,550);
    	moveToFront();
    }
    
    public void load(String ins)
    {
    	 arr=new String[9];
               try
               	{
               		while(true)
               			{ 
               				tab.delete(0);
               			}
               			
               	}
               	catch(Exception ee){}
              
               		
            		try
            		{   
                    	                      
            		set=st.executeQuery(ins);
            		Date dtemp;
            		while(set.next())
            		{  
            			arr[0]=set.getString("ID");
            			dtemp=set.getDate("DateR");
            			String temps="";
            			if(dtemp.getDate()<10)temps+=0;
            			temps+=dtemp.getDate();
            			arr[4]=getMonth(dtemp.getMonth()+1)+"-"+temps+"-"+getyears(dtemp.getYear());
            			arr[2]=set.getString("Quantity");
            			arr[3]=set.getString("Unit");
            			arr[1]=set.getString("ItemD");
            			arr[5]=set.getString("CustName");
            			arr[6]=form.format(set.getDouble("SRP"));
            			arr[7]=form.format(Integer.parseInt(arr[2])*Double.parseDouble(arr[6]));
            			arr[8]=set.getString("Note1");
            			
            			tab.insert(arr);
            		}
            	 }catch(Exception ee){ee.printStackTrace();}
    }
    	public int getyears(int ins)
         		{   
         			int ret=-1;
         			
         			if(ins==114)ret=2014;
         			if(ins==115)ret=2015;
         			if(ins==116)ret=2016;
         			if(ins==117)ret=2017;
         			if(ins==118)ret=2018;
         			if(ins==119)ret=2019;
         			if(ins==120)ret=2020;
         			return ret;
         		}  
        	public String getMonth(int i)
    		{
    			String ret="";
    			if(i==1)ret="Jan";
    			if(i==2)ret="Feb";
    			if(i==3)ret="Mar";
    			if(i==4)ret="Apr";
    			if(i==5)ret="May";
    			if(i==6)ret="Jun";
    			if(i==7)ret="Jul";
    			if(i==8)ret="Aug";
    			if(i==9)ret="Sep";
    			if(i==10)ret="Oct";
    			if(i==11)ret="Nov";
    			if(i==12)ret="Dec";
    			return ret;
   		 } 		
    public static void main(String args[]){}
}