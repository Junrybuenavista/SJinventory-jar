 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;

 
public class search2
        {
        	Statement st;
        	ResultSet set;
        	
    		public search2(Inventory inv)
            {    
            	 
            	 JPanel p1=new JPanel(){public Dimension getPreferredSize(){return new Dimension(450,40);}};
            	 JTextField txt=new JTextField(15);
            	 String arr[]={"ITEM DESC.","SUPPLIER"};
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
						  		inv.load("SELECT Inventory.ID, Inventory.DateR, Inventory.Quantity, Inventory.Note1,"+
            			" ItemProfile.Unit, ItemProfile.ItemD, ItemProfile.Supplier, ItemProfile.SupplierP, ItemProfile.SRP"+
            			" FROM Inventory INNER JOIN ItemProfile ON Inventory.ID=ItemProfile.ID where ItemD like '"+txt.getText()+"%'");
            			
                          	}
                          if(box.getSelectedIndex()==1)
                          	{
						  			inv.load("SELECT Inventory.ID, Inventory.DateR, Inventory.Quantity, Inventory.Note1,"+
            			" ItemProfile.Unit, ItemProfile.ItemD, ItemProfile.Supplier, ItemProfile.SupplierP, ItemProfile.SRP"+
            			" FROM Inventory INNER JOIN ItemProfile ON Inventory.ID=ItemProfile.ID where Supplier like '"+txt.getText()+"%'");
                          	}
						  System.out.println("load");
						}	
                
           	
             
             }
       
       
            public static void main(String args[]){}
                
        }