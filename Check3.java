 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;

 
public class Check3 
        {
        
            Statement st;
            ResultSet set;
            String arr[];
            String arr2[][]=null; 
            Connection conn;
            JPanel p3;
           
            MyTable tab;
           
            int quan=0;
            
          
            public Check3()
            {  
            	
            	
            	  try{ 
            			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  				
    					conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=Database1.mdb;DriverID=22;READONLY=true;) ","","");    				    			
    	   				 st=conn.createStatement();
    				 							 
	      			 }catch(Exception e){e.printStackTrace();}
            	
            	 String arr[]={"ITEM DESC.","QUANTITY"};                     
        		 tab=new MyTable();
        		 tab.setData(arr2,arr);
        		 
        		 JPanel p1c=new JPanel();
        	
        		 
        		 
        	
        		 
        		 JPanel p1=new JPanel(){public Dimension getPreferredSize(){return new Dimension(450,240);}};
        		 p1.setLayout(new BorderLayout());
        		 JPanel p2=new JPanel();
        		 p2.setLayout(new BorderLayout());
        		
        		 
        		 JPanel p4=new JPanel(){public Dimension getPreferredSize(){return new Dimension(120,100);}};
        		 p4.add(new JLabel("Reminder! The following item quantity is low !"));
        	
	 
        		 p2.add(new JScrollPane(tab.getTb()),"Center");
        		
        		 
        		 p1.add(p2,BorderLayout.CENTER);
        		
        		 p1.add(p4,BorderLayout.SOUTH);
        		 
        		 Object[] message = new Object[1];          
                 message[0]=p1;
            
                 int res2=load();
         
                 if(res2!=0)
                 {
                
                 String[] options = {"OK","CANCEL"};
               
		    		int result = JOptionPane.showOptionDialog(
		    		null,
		    		message,
		    		"LOW-STOCK",
		    		JOptionPane.DEFAULT_OPTION,
		    		JOptionPane.INFORMATION_MESSAGE,
		    		null,
		    		options,
		    		options[0]

						);
                 }
                
      
             }
       
          public int load()
          	{
          		int ret=1;
          		arr=new String[2];
          		try
                {
                	set=st.executeQuery("Select count(*) as nn from Inventory inner join ItemProfile ON Inventory.ID=ItemProfile.ID where Quantity<10");
                	set.next();
                	int res=set.getInt("nn");
                	if(res==0)return 0;
                }catch(Exception ee){}
                
            	try
            	{   
            	    
            	    
                    
            		set=st.executeQuery("Select ItemD, Quantity from Inventory inner join ItemProfile ON Inventory.ID=ItemProfile.ID where Quantity<10");
            		while(set.next())
            		{
            		    arr[0]=set.getString("Itemd");
            			arr[1]=set.getString("Quantity");
     
            		          			
            			tab.insert(arr);
            		
            		}
            	 
            	}catch(Exception ee){ee.printStackTrace();}  
            	return ret;	
          		
          	}
            public static void main(String args[]){new Check();}
                
        }