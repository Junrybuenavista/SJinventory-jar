 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;

 
public class ItemDetails implements TextListener
        {
        
            final Statement st;
            ResultSet set;
            String arr[];
            String arr2[][]=null; 
            TextField t1,t2,t3,t4;      
            JLabel lab1;
            JPanel p3;
            Inventory inv;
            MyTable tab;
            JComboBox comboM,comboD,comboY;
            int quan=0;
            ImageIcon icon;
            String img;
         
            public void textValueChanged(TextEvent e)
			{
				t3.setText(Integer.parseInt(t1.getText())*Integer.parseInt(t2.getText())+"");
			}  
            public void load()
            {  arr=new String[3];
              
            	try
            	{   
            	    
            	    
                    
            		set=st.executeQuery("Select * from ItemProfile order by ItemD");
            		while(set.next())
            		{
            		    arr[0]=set.getString("ID");
            			arr[1]=set.getString("ItemD");
            			arr[2]=set.getString("Supplier");	
     
            		          			
            			tab.insert(arr);
            		
            		}
            	 
            	}catch(Exception ee){ee.printStackTrace();}
            	
               p3.add(lab1);
            }
        
          
        	
        	public ItemDetails(final Statement st,Inventory inv)
        	{
        		 this.st=st;
        		 this.inv=inv;
        		 String arr[]={"ID","ITEM DESC.","SUPPLIER"};                     
        		 tab=new MyTable();
        		 tab.setData(arr2,arr);
        		 
        		 tab.getTb().getColumnModel().getColumn(0).setMinWidth(0);
				 tab.getTb().getColumnModel().getColumn(0).setMaxWidth(0);
        		 
        		 JPanel p1c=new JPanel();
        		 setCombo();
        		 p1c.add(comboM);p1c.add(comboD);p1c.add(comboY);
        		  tab.getTb().addMouseListener(new MouseAdapter(){
     				public void mouseClicked(MouseEvent e)
     		           {
     		           	        
     		           	        Date in_date=null;
     		           	        String notes="";
      							if (e.getClickCount() == 1)
      							{
      								 
                    		         try
                    		         	{
                    		         		set=st.executeQuery("Select * from ItemProfile where ID="+tab.getValue(tab.getTb().getSelectedRow(),0));
                    		         		set.next();
                      						img=set.getString("image");
                      						icon=new ImageIcon(img);
            			 					lab1.setIcon(icon);
         		
                    		         	}
                    		         catch(Exception ee){}
                    		         	
									   try
                    		         	{
                    		         		set=st.executeQuery("Select * from Inventory where ID="+tab.getValue(tab.getTb().getSelectedRow(),0));
                    		         		set.next();
                    		         		quan=set.getInt("Quantity");
                    		         		in_date=set.getDate("DateR");
                    		         		notes=set.getString("noTe1");
                    		         		System.out.println(in_date.getMonth()+" "+in_date.getDate()+" "+in_date.getYear());
                    		         		
                    		         	}
                    		         catch(Exception ee){quan=0;}
         						}
         						t1.setText(quan+"");
         						comboM.setSelectedIndex(in_date.getMonth());
         						comboD.setSelectedIndex(in_date.getDate()-1);
         						comboY.setSelectedIndex(getyears(in_date.getYear()));
         						t4.setText(notes);
      					}
      					
      					});
        		 
        		 icon= new ImageIcon("");
        		 lab1 = new JLabel(icon);
        		 
        		 JPanel p1=new JPanel(){public Dimension getPreferredSize(){return new Dimension(450,240);}};
        		 p1.setLayout(new BorderLayout());
        		 JPanel p2=new JPanel();
        		 p2.setLayout(new BorderLayout());
        		 p3=new JPanel(){public Dimension getPreferredSize(){return new Dimension(140,160);}};
        		 
        		 JPanel p4=new JPanel(){public Dimension getPreferredSize(){return new Dimension(120,100);}};
        		 p3.setLayout(new BorderLayout());p3.setBackground(Color.BLACK);
        		 p3.add(lab1);
        		 t1=new TextField(5);t1.setEditable(false);
        		 t2=new TextField(5);t2.setText("0");
        		 t3=new TextField(5);
        		 t4=new TextField(52);
        		 
        		 p4.add(new JLabel("Current Stock:"));p4.add(t1);
        	
        		 p4.add(new JLabel("Enter IN-STOCK Quantity:"));p4.add(t2);
        		  
        		 p4.add(new JLabel("Last IN-STOCK DATE"));p4.add(p1c);
        		 p4.add(new JLabel("                       "));
        		 p4.add(new JLabel("NOTE:"));p4.add(t4);
        		 
        		 p2.add(new JScrollPane(tab.getTb()),"Center");
        		
        		 
        		 p1.add(p2,BorderLayout.CENTER);
        		 p1.add(p3,BorderLayout.WEST);
        		 p1.add(p4,BorderLayout.SOUTH);
        		 
        		 Object[] message = new Object[1];          
                 message[0]=p1;
                                         
             
                

                 String[] options = {"OK","CANCEL"};
                 load();
		    		int result = JOptionPane.showOptionDialog(
		    		inv,
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
						 try
						  {
						  
						  	if(quan!=0)
						  		{
						  			st.execute("UPDATE  Inventory set DateR='"+comboY.getSelectedItem()+"-"+(comboM.getSelectedIndex()+1)+"-"+comboD.getSelectedItem()+"', Quantity='"+(quan+Integer.parseInt(t2.getText()))+"', note1='"+t4.getText()+"' where ID="+tab.getValue(tab.getTb().getSelectedRow(),0));
						  			
						  		}
						  	else
						  		{
						  		    st.execute("Insert Into Inventory(ID,DateR,Quantity,Note1) values('"+tab.getValue(tab.getTb().getSelectedRow(),0)+"','"+comboY.getSelectedItem()+"-"+(comboM.getSelectedIndex()+1)+"-"+comboD.getSelectedItem()+"','"+Integer.parseInt(t2.getText())+"','"+t4.getText()+"')");	
						  		}	
						  	
						  }catch(Exception ee){ee.printStackTrace();}
						  inv.load("SELECT Inventory.ID, Inventory.DateR, Inventory.Quantity, Inventory.Note1,"+
            			" ItemProfile.Unit, ItemProfile.ItemD, ItemProfile.Supplier, ItemProfile.SupplierP, ItemProfile.SRP"+
            			" FROM Inventory INNER JOIN ItemProfile ON Inventory.ID=ItemProfile.ID ");
						  System.out.println("load");
						}
						
				 
               
           }
           
            public void setCombo()
         	{
         		String s1[]={"January","Febuary","March","April","May","June","July","August","September","October","November","December"};
         	    String s2[]={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
         	    String s3[]={"2014","2015","2016","2017","2018","2019","2020"};	
         		comboM=new JComboBox(s1);comboD=new JComboBox(s2);comboY=new JComboBox(s3);
         	}
         	public int getyears(int ins)
         		{   
         			int ret=-1;
         			
         			if(ins==114)ret=0;
         			if(ins==115)ret=1;
         			if(ins==116)ret=2;
         			if(ins==117)ret=3;
         			if(ins==118)ret=4;
         			if(ins==119)ret=5;
         			if(ins==120)ret=6;
         			return ret;
         		}  
        	public String getMonth(int i)
    		{
    			String ret="";
    			if(i==1)ret="January";
    			if(i==2)ret="February";
    			if(i==3)ret="March";
    			if(i==4)ret="April";
    			if(i==5)ret="May";
    			if(i==6)ret="June";
    			if(i==7)ret="July";
    			if(i==8)ret="August";
    			if(i==9)ret="September";
    			if(i==10)ret="October";
    			if(i==11)ret="November";
    			if(i==12)ret="December";
    			return ret;
   		 } 		
                
        }