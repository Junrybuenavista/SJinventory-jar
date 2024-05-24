import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;

public class EditStock extends JInternalFrame implements ActionListener,FocusListener{


    Connection conn;
    Statement st;
    ResultSet set;
    DecimalFormat form;
    JButton b1,b3;
    TextField t2,t3,t4,t5,t6;
    JComboBox box;
    JDesktopPane desk;
    DeleteStock stock;
    String ID1;
    public void focusLost(FocusEvent e)
			{  
				
				try{t2.setText(form.format(Double.parseDouble(t2.getText())));}catch(Exception ee){ee.printStackTrace();JOptionPane.showMessageDialog(this,"Invalid Input1");t2.setText(".00");return;}
				try{t3.setText(form.format(Double.parseDouble(t3.getText())));}catch(Exception ee){JOptionPane.showMessageDialog(this,"Invalid Input2");t3.setText(".00");return;}
			
		
			}
	public void focusGained(FocusEvent e){}		 
    public EditStock(JDesktopPane desk, String ID1,Statement st,DeleteStock stock) 
    {
    	super("ITEM PROFILE", true, // resizable
					true, // closable
					true, // maximizable
					true);
    	this.desk=desk;
    	this.ID1=ID1;
    	this.st=st;
    	this.stock=stock;
    	
    	
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
        
        load();
        add(p11,BorderLayout.CENTER);
        add(p3,BorderLayout.SOUTH);
        
    	
    	setVisible(true);
        desk.add(this); 
    	setSize(400,275);
    	moveToFront();
    	
    }
    public void load()
    	{
    		try
    		{
    		  set= st.executeQuery("Select * From ItemProfile where ID="+ID1);
    		  set.next();
    		  t5.setText(set.getString("ItemD"));
    		  t4.setText(set.getString("Supplier"));
    		  t6.setText(set.getString("Image"));
    		  t2.setText(set.getString("SupplierP"));
    		  t3.setText(set.getString("SRP"));
    		  box.setSelectedItem(set.getString("Unit"));
    		}
    		catch(Exception ee){ee.printStackTrace();}	
    	}
    public void save()
    	{ 
    	    try
    	    {
    	    	st.execute("UPDATE  ItemProfile set unit='"+box.getSelectedItem()+"', ItemD='"+t5.getText()+"', Supplier='"+t4.getText()+"', SupplierP='"+t2.getText()+"', SRP='"+t3.getText()+"', Image='"+t6.getText()+"' where ID="+ID1);
    	    	//st.execute("Insert Into ItemProfile(Unit,ItemD,Supplier,SupplierP,SRP,image) values('"+box.getSelectedItem()+"','"+t5.getText()+"','"+t4.getText()+"',"+t2.getText()+","+t3.getText()+",'image\\"+t6.getText()+".jpg')");
    	    }
    	    catch(Exception ee){ee.printStackTrace();}
    	    JOptionPane.showMessageDialog(this,"Succesfully Save !");
    	    stock.load();
    	    this.dispose();
    	}
    public void actionPerformed(ActionEvent e)
    	{
    		if(e.getSource()==b1){save(); t5.setText("");t4.setText("");t2.setText(".00");t3.setText(".00");}
    		if(e.getSource()==b3){this.dispose();}
    	}
    public static void main(String args[]){}
    
}