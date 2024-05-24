import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableCellRenderer;

public class DeleteStock extends JInternalFrame implements ActionListener{
    
    Connection conn;
    Statement st;
    ResultSet set;
    DecimalFormat form;
    MyTable tab;
    String arr2[][]=null; 
    String arr[];	
    JButton b1,b2,b3;
    JDesktopPane desk;
    	
    public void actionPerformed(ActionEvent e)
    	{
    		if(e.getSource()==b1)
    			{   
    				int reply = JOptionPane.showConfirmDialog(this, "are you sure you want to delete this item ?", title, JOptionPane.YES_NO_OPTION);
    				if (reply == JOptionPane.YES_OPTION)
    				 {
      						delete();
   					 }
    			}
    		if(e.getSource()==b2){this.dispose();}
    		if(e.getSource()==b3)
    			{
    				new EditStock(desk,tab.getValue(tab.getTb().getSelectedRow(),0),st,this);
    			}
    	} 	
    public DeleteStock(JDesktopPane desk) {
    	
    	super("EDIT ITEM", true, // resizable
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
	    form=new DecimalFormat(".00");
	    b1=new JButton("Delete");b1.addActionListener(this);
	    b2=new JButton("Close");b2.addActionListener(this);
	    b3=new JButton("EDIT");b3.addActionListener(this);
	    	    
	    String arr[]={"ID","ITEM DESC.","UNIT","SUPPLIER","SUPPLIER PRICE","SRP"};                     
        tab=new MyTable();
        tab.setData(arr2,arr);
        
        tab.getTb().getColumnModel().getColumn(0).setMinWidth(0);
		tab.getTb().getColumnModel().getColumn(0).setMaxWidth(0);
		
	
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		tab.getTb().getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		tab.getTb().getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		
        
	    
	    JPanel pn=new JPanel(){public Dimension getPreferredSize(){return new Dimension(400,450);}};
	 
	    pn.setLayout(new BorderLayout());
	    pn.add(new JScrollPane(tab.getTb()),"Center"); 
	    JPanel ps=new JPanel(){public Dimension getPreferredSize(){return new Dimension(400,150);}};
	    ps.add(b1);ps.add(b3);ps.add(b2);
	    
	    
	    con.add(pn,"North");
	    con.add(ps,"Center"); 
	    load();	 
	    show();
	    desk.add(this);
    	setSize(800,550);
    	moveToFront();
    }
    public void delete()
    	{
    		try
    		{
    		 st.execute("Delete from ItemProfile where ID="+tab.getValue(tab.getTb().getSelectedRow(),0)+"");		
    		}catch(Exception ee){ee.printStackTrace();return;}
    		load();
    	}
    public void load()
    {
    	 arr=new String[6];
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
                    	                      
            		set=st.executeQuery("Select * from ItemProfile ");
            		while(set.next())
            		{
            			arr[0]=set.getString("ID");
            			arr[1]=set.getString("ItemD");
            			arr[2]=set.getString("Unit");
            			arr[3]=set.getString("Supplier");
            			arr[4]=form.format(set.getDouble("SupplierP"));
            			arr[5]=form.format(set.getDouble("SRP"));
            			
            			tab.insert(arr);
            		}
            	 }catch(Exception ee){ee.printStackTrace();}
    }
    public static void main(String args[]){}
}