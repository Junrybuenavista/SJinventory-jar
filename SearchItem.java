 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;

 
public class SearchItem
        {
        	Statement st;
        	ResultSet set;
        	
    		public SearchItem(Inventory_Out inv)
            {    
            	 
            	 JPanel p1=new JPanel(){public Dimension getPreferredSize(){return new Dimension(450,40);}};
            	 JTextField txt=new JTextField(15);
            	 String arr[]={"ITEM DESC.","CUSTOMER NAME"};
		         JComboBox box=new JComboBox(arr);
        		 p1.add(new JLabel("SEARCH BY:"));p1.add(box);p1.add(txt);
        		  	 
		 		
        		 
        		 Object[] message = new Object[1];          
                 message[0]=p1;
               
                 String[] options = {"OK","CANCEL"};
               
		    		int result = JOptionPane.showOptionDialog(
		    		null,
		    		message,
		    		"IN-STOCK",
		    		JOptionPane.DEFAULT_OPTION,
		    		JOptionPane.INFORMATION_MESSAGE,
		    		null,
		    		options,
		    		options[0]

						);
						if(result==0)
						{  
                          if(box.getSelectedIndex()==0)
                          	{System.out.println("fffffffffffffff");
						  		inv.load("SELECT * FROM OutStock where ItemD like '"+txt.getText()+"%'");
            			
                          	}
                          if(box.getSelectedIndex()==1)
                          	{
						  			inv.load("SELECT * FROM OutStock where CustName like '"+txt.getText()+"%'");
                          	}
						  System.out.println("load");
						}	
                
           	
             
             }
       
       
            public static void main(String args[]){}
                
        }