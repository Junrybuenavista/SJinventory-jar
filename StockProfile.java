import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;

public class StockProfile extends JInternalFrame implements ActionListener,FocusListener{


    Connection conn;
    Statement st;
    DecimalFormat form;
    JButton b1,b3;
    TextField t2,t3,t4,t5,t6;
    JComboBox box;
    JDesktopPane desk;
    public void focusLost(FocusEvent e)
			{  
				
				try{t2.setText(form.format(Double.parseDouble(t2.getText())));}catch(Exception ee){ee.printStackTrace();JOptionPane.showMessageDialog(this,"Invalid Input1");t2.setText(".00");return;}
				try{t3.setText(form.format(Double.parseDouble(t3.getText())));}catch(Exception ee){JOptionPane.showMessageDialog(this,"Invalid Input2");t3.setText(".00");return;}
			
		
			}
	public void focusGained(FocusEvent e){}		 
    public StockProfile(JDesktopPane desk) 
    {
    	super("ITEM PROFILE", true, // resizable
					true, // closable
					true, // maximizable
					true);
    	this.desk=desk;
    	 try{ 
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  				
    		conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=Database1.mdb;DriverID=22;READONLY=true;) ","","");    				    			
    	    st=conn.createStatement();
    				 							 
	      }catch(Exception e){e.printStackTrace();}
    	
    	String arrc[]={"Liter/s","Galon/s","Bottle/s","Kilo/s","Case","Pack/s","Bag/s","Box"};
        box =new JComboBox(arrc);
       	
    	form=new DecimalFormat(".00");
    	this.st=st;
    	GridLayout grid=new GridLayout(6,2);
    	grid.setVgap(6);
    	setLayout(new BorderLayout());
    

        JPanel p11=new JPanel(){public Dimension getPreferredSize(){return new Dimension(150,150);}};
        JPanel p1=new JPanel(){public Dimension getPreferredSize(){return new Dimension(350,175);}};
        JPanel p3=new JPanel(){public Dimension getPreferredSize(){return new Dimension(350,40);}};
        
        p1.setLayout(grid);
       
                       
      
        b1=new JButton("SAVE");b1.addActionListener(this);
       
        b3=new JButton("CANCEL");b3.addActionListener(this);
       
        t2=new TextField(15);t2.setText(".00");t2.addFocusListener(this);
        t3=new TextField(15);t3.setText(".00");t3.addFocusListener(this);
        t4=new TextField(15);
        t5=new TextField(15);
        t6=new TextField(15);t6.setText("noimage");
        
        p1.add(new JLabel("ITEM DESCRIPTION:"));p1.add(t5);
        
        p1.add(new JLabel("SUPPLIER:"));p1.add(t4);
        
        p1.add(new JLabel("SUPPLIER PRICE:"));p1.add(t2);
        p1.add(new JLabel("SUGGESTED RETAIL PRICE:"));p1.add(t3);
        p1.add(new JLabel("IMAGE:"));p1.add(t6);
        p1.add(new JLabel("UNIT:"));p1.add(box);
        p11.add(p1);
        
        p3.add(b1);p3.add(b3);
        
        
        add(p11,BorderLayout.CENTER);
        add(p3,BorderLayout.SOUTH);
        
    	
    	setVisible(true);
        desk.add(this); 
    	setSize(400,275);
    	moveToFront();
    }
    public void save()
    	{ 
    	    try
    	    {
    	    	st.execute("Insert Into ItemProfile(Unit,ItemD,Supplier,SupplierP,SRP,image) values('"+box.getSelectedItem()+"','"+t5.getText()+"','"+t4.getText()+"',"+t2.getText()+","+t3.getText()+",'image\\"+t6.getText()+".jpg')");
    	    }
    	    catch(Exception ee){ee.printStackTrace();}
    	    JOptionPane.showMessageDialog(this,"Succesfully Save !");
    	}
    public void actionPerformed(ActionEvent e)
    	{
    		if(e.getSource()==b1){save(); t5.setText("");t4.setText("");t2.setText(".00");t3.setText(".00");}
    		if(e.getSource()==b3){this.dispose();}
    	}
    public static void main(String args[]){}
    
}