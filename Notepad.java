import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.text.*;

public class Notepad extends JFrame
 {
                     int pos=-1;	
	private String pop[] = {"Cut","Copy","Paste","Delete"};
	private JMenuItem cutItem, copyItem, pasteItem, deleteItem, selectAllItem;
	private JMenuItem npopItem = new JMenuItem("Select all");
    private JMenuItem popItems[] = new JMenuItem[pop.length];
    
	private Color colorValues[] = { Color.black, Color.blue, Color.red, Color.green, Color.pink, Color.orange };
	private JRadioButtonMenuItem colorItems[], typeItems[], sizeItems[];
	private JCheckBoxMenuItem styleItems[];
	private JTextArea textArea;
	private ButtonGroup typeGroup, colorGroup, sizeGroup;
	private int style;
	private int sizeValues[] ={10,12,14,16,20,28,36,48,72};
	private int size = 12;
    private String txtmsg = "";
    private String wtf;

	public Notepad()
	{
		super("NotePad Project");
		
		Actions acts = new Actions();

		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');

		JMenuItem newItem = new JMenuItem("New");
        newItem.setMnemonic('N');
        newItem.addActionListener(
        	new ActionListener() {
        		public void actionPerformed( ActionEvent e )
        		{
    	                             if ( textArea.getText().equals("") )
        			textArea.setDocument( new PlainDocument() );
        		      else {
        		                SaveIfNewPressed sinp = new SaveIfNewPressed();
        		              }
        		}
        	}
        );

        fileMenu.add(newItem);

        
        JMenuItem openItem = new JMenuItem("Open");
        openItem.setMnemonic('O');
        openItem.addActionListener(
        	new ActionListener() {
        		public void actionPerformed( ActionEvent e )
        		{
        			if ( textArea.getText().equals("") )
        			openFile();
        			else {
        				SaveIfOpenPressed siop = new SaveIfOpenPressed();
        				}
        		}
        	}
        );
        
        fileMenu.add(openItem);
        
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setMnemonic('S');
        saveItem.addActionListener(
        	new ActionListener() {
        		public void actionPerformed( ActionEvent e )
        		{
        			saveFile();
        		}
        	}
        );
        
        fileMenu.add(saveItem);
        fileMenu.addSeparator();

		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic('x');
		exitItem.addActionListener(
			new ActionListener() {
				public void actionPerformed( ActionEvent e )
				{
					System.out.println("Thank you for using this program\n");
					System.exit(0);
				}
			}
		);

		fileMenu.add(exitItem);

		bar.add(fileMenu);
		
		JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic('E');
        
        cutItem = new JMenuItem("Cut");
        cutItem.addActionListener(acts);
        
        editMenu.add(cutItem);
        
        copyItem = new JMenuItem("Copy");
        copyItem.addActionListener(acts);
        
        editMenu.add(copyItem);
        
        
        
        
        pasteItem = new JMenuItem("Paste");
        pasteItem.addActionListener(acts);
        
        editMenu.add(pasteItem);
		
		deleteItem = new JMenuItem("Delete");
		deleteItem.addActionListener(acts);
        
        editMenu.add(deleteItem);
        
        editMenu.addSeparator();
        
        selectAllItem = new JMenuItem("Select all");
        selectAllItem.addActionListener(acts);
        
        editMenu.add(selectAllItem);
		
		bar.add(editMenu);
		
		JMenu formatMenu = new JMenu("Format");
		formatMenu.setMnemonic('R');
		
		JMenu fontMenu = new JMenu("Font");
		
		JMenu styleMenu = new JMenu("Style");
		String styleNames[] = { "Bold", "Italic" };
		styleItems = new JCheckBoxMenuItem[styleNames.length];
		StyleHandler styleHandler = new StyleHandler();
		
		for ( int i = 0; i < styleNames.length; i++) {
			styleItems[i] = new JCheckBoxMenuItem(styleNames[i]);
			styleMenu.add(styleItems[i]);
			styleItems[i].addItemListener( styleHandler );
		}
		
		fontMenu.add(styleMenu);
		
		JMenu typeMenu = new JMenu("Type");
		String fontNames[] = { "TimesRoman", "Courier", "Helvetica" };
		typeItems = new JRadioButtonMenuItem[fontNames.length];
		typeGroup = new ButtonGroup();
		ItemHandler itemHandler = new ItemHandler();
		
		for( int i = 0; i < typeItems.length; i++) {
			typeItems[i] = new JRadioButtonMenuItem( fontNames[i] );
			typeMenu.add(typeItems[i]);
			typeGroup.add(typeItems[i]);
			typeItems[i].addActionListener(itemHandler);
		}
		
		typeItems[0].setSelected(true);
		
		fontMenu.add(typeMenu);
		
		JMenu colorMenu = new JMenu("Color");
		String colors[] = {"Black", "Blue", "Red", "Green", "Pink", "Orange"};
		colorItems = new JRadioButtonMenuItem[colors.length];
		colorGroup = new ButtonGroup();
		
		for ( int i = 0; i < colors.length; i++) {
			colorItems[i] = new JRadioButtonMenuItem(colors[i]);
			colorMenu.add(colorItems[i]);
			colorGroup.add(colorItems[i]);
			colorItems[i].addActionListener( itemHandler);
		}

		colorItems[0].setSelected(true);

		fontMenu.add(colorMenu);
		
		JMenu sizeMenu = new JMenu("Size");
		String sizeNumbers[] = {"10","12","14","16","20","28","36","48","72"};
		sizeItems = new JRadioButtonMenuItem[sizeNumbers.length];
		sizeGroup = new ButtonGroup();
		
		for ( int i=0; i<sizeNumbers.length; i++) {
			sizeItems[i] = new JRadioButtonMenuItem(sizeNumbers[i]);
		    sizeMenu.add(sizeItems[i]);
		    sizeGroup.add(sizeItems[i]);
		    sizeItems[i].addActionListener(itemHandler);
		}
		
		sizeItems[1].setSelected(true);
		
		fontMenu.add(sizeMenu);
		
		formatMenu.add(fontMenu);
		
		bar.add(formatMenu);
		
		JMenu searchMenu = new JMenu("Search");
		searchMenu.setMnemonic('S');
		
		JMenuItem findItem = new JMenuItem("Find");
		findItem.addActionListener(
			new ActionListener() {
				public void actionPerformed( ActionEvent e ) 
				{
					try {
						wtf = JOptionPane.showInputDialog("Type the word to find");
                        wtf=wtf.trim();
                        while ( textArea.getText().indexOf(wtf) == -1 )
                        {
                          JOptionPane.showMessageDialog( null, "Word not found!", "No match",
                            JOptionPane.INFORMATION_MESSAGE );
                            wtf = JOptionPane.showInputDialog("Type the word to find");
                        }
                         pos = textArea.getText().indexOf(wtf) + wtf.length();
                        textArea.setSelectionStart(textArea.getText().indexOf(wtf));
                        textArea.setSelectionEnd(textArea.getText().indexOf(wtf)+wtf.length());
                    }
                    
                    catch( Exception ex ) {
                    	JOptionPane.showMessageDialog(null, "Search canceled", "aborted",
        	                                          JOptionPane.INFORMATION_MESSAGE ); }
                }
            }
        );
     
		searchMenu.add(findItem);
		
		JMenuItem findnextItem = new JMenuItem("Find Next");
		findnextItem.addActionListener(
			new ActionListener() {
				public void actionPerformed( ActionEvent e )
				{
			 textArea.setSelectionStart(textArea.getText().indexOf(wtf, (int)textArea.getText().indexOf(wtf)+1));
                                                                    textArea.setSelectionEnd(textArea.getText().indexOf(wtf, (int)textArea.getText().indexOf(wtf)+1));
				}
            }
		);
		
		searchMenu.add(findnextItem);
		
		bar.add(searchMenu);
					 
		JMenu helpMenu = new JMenu("About");
		helpMenu.setMnemonic('H');
		
		JMenuItem aboutItem = new JMenuItem("Developers...");
		aboutItem.addActionListener(
			new ActionListener() {
				public void actionPerformed( ActionEvent e )
				{
					String s = new String("This Notepad project was developed by:\n" +
					"Vasu goel(RA1711003030560)\n\n" +
                                        "Master of Computer Application\n Third year   2019-2020\n\n" +
					"SRM Institute Of Technology\n Uttar Pradesh Technical University\n\n");
					
					 
					JOptionPane.showMessageDialog( null, s, "Author's Info.", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		);
		
		helpMenu.add( aboutItem);
		
		bar.add(helpMenu);

		textArea = new JTextArea("");
		getContentPane().setLayout( new BorderLayout());
		getContentPane().add( textArea, BorderLayout.CENTER);
		getContentPane().add( new JScrollPane(textArea) );
		
		final JPopupMenu popupMenu = new JPopupMenu();
		
		for( int i = 0; i<pop.length; i++) {
			popItems[i] = new JMenuItem(pop[i]);
			popupMenu.add(popItems[i]);
			popItems[i].addActionListener(acts);
		}
		popupMenu.addSeparator();
		popupMenu.add(npopItem);
		npopItem.addActionListener(acts);
		
		textArea.addMouseListener(
			new MouseAdapter() {
				public void mousePressed( MouseEvent e )
				{
					checkForTriggerEvent(e);
				}
				public void mouseReleased( MouseEvent e)
				{
					checkForTriggerEvent(e);
				}

				private void checkForTriggerEvent( MouseEvent e )
				{
					if(e.isPopupTrigger())
					popupMenu.show( e.getComponent(), e.getX(), e.getY());
				}
			}
		);
		
		setSize(600,500);
		show();
	}

	

    private void openFile()
    {
    	JFileChooser fch = new JFileChooser();
        fch.setFileSelectionMode( JFileChooser.FILES_ONLY );

        int result = fch.showOpenDialog( this );

        if ( result == JFileChooser.CANCEL_OPTION )
        return;
        
        else
        try{
        	FileInputStream fis = new FileInputStream( fch.getSelectedFile() );
            BufferedInputStream bis = new BufferedInputStream( fis );
            
            int c = bis.read();
            while ( c != -1 )
            {
            	txtmsg = txtmsg + (char) c;
                c = bis.read();
            }
            
            textArea.setText( txtmsg );
            bis.close();
        }
        
        catch( Exception ex )
        {
        	JOptionPane.showMessageDialog( this, "An exceptin has accured", "Error",
        	                              JOptionPane.ERROR_MESSAGE );
		}
	}


    private void saveFile()
    {
        JFileChooser fch = new JFileChooser();
        fch.setFileSelectionMode( JFileChooser.FILES_ONLY );

        int result = fch.showSaveDialog( this );

        if ( result == JFileChooser.CANCEL_OPTION )
        return;
        
        else
    	try {
    		FileOutputStream fos = new FileOutputStream( fch.getSelectedFile() );
    		BufferedOutputStream bos = new BufferedOutputStream( fos );
            bos.write( textArea.getText().getBytes() );
            bos.flush();
        	fos.close();
        }
		
		catch( Exception ex )
		{
			JOptionPane.showMessageDialog(this, "An exceptin has accured", "Error", JOptionPane.ERROR_MESSAGE );
		}
	}


	public static void main( String args[] )
	{
		Notepad app = new Notepad();
		app.addWindowListener(
			new WindowAdapter() {
				public void windowClosing( WindowEvent e )
				{
                                        System.out.println("Thank you for using this program.. \n");
					System.exit(0);
				}
			}
		);
	}
	
	
	class ItemHandler implements ActionListener {
		public void actionPerformed ( ActionEvent e )
		{
			for ( int i=0; i<sizeItems.length; i++ )
			if ( sizeItems[i].isSelected() ) {
				size=sizeValues[i];
				textArea.setFont( new Font( textArea.getFont().getName(), style, size) );
				break;
			}


			for ( int i = 0; i < colorItems.length; i++ )
			if ( colorItems[i].isSelected() ) {
				textArea.setForeground( colorValues[i] );
				break;
			}

			for ( int i = 0; i < typeItems.length; i++ )
			if ( e.getSource() == typeItems[i] ) {
				textArea.setFont( new Font( typeItems[i].getText(), style, size) );
				break;
			}
		}
	}

	class StyleHandler implements ItemListener {
		public void itemStateChanged( ItemEvent e )
		{                   
			style = 0;

			if ( styleItems[0].isSelected() )
                       			 style = Font.BOLD;

			if ( styleItems[1].isSelected() )
                    				    style+= Font.ITALIC;
  	textArea.setFont( new Font( textArea.getFont().getName(), style, size) );
		}
	}
	
	private class SaveIfNewPressed extends JFrame 
       {
		private JButton yesButton, noButton;
		private JLabel label;
		
		public SaveIfNewPressed()
		{
			Container c = getContentPane();
			c.setLayout( new FlowLayout() );
			
			label = new JLabel("Do you want to save changes to the previous document?");
			c.add(label);
			
			yesButton = new JButton("Yes");
			yesButton.addActionListener(
				new ActionListener() {
					public void actionPerformed( ActionEvent e )
					{
						saveFile();
						textArea.setDocument( new PlainDocument() );
						dispose();
					}
				}
			);
			c.add(yesButton);
			
			noButton = new JButton("No");
			noButton.addActionListener(
				new ActionListener() {
					public void actionPerformed( ActionEvent e )
					{
						textArea.setDocument( new PlainDocument() );
						dispose();
					}
				}
			);
			c.add(noButton);
			
			setSize( 350, 100 );
			show();
		}
	}
	
	private class SaveIfOpenPressed extends JFrame {
		private JButton yesButton, noButton;
		private JLabel label;
		
		public SaveIfOpenPressed()
		{
			Container c = getContentPane();
			c.setLayout( new FlowLayout() );
			
			label = new JLabel("Do you want to save changes to the previous document?");
			c.add(label);
			
			yesButton = new JButton("Yes");
			yesButton.addActionListener(
				new ActionListener() {
					public void actionPerformed( ActionEvent e )
					{
						saveFile();
						textArea.setDocument( new PlainDocument() );
						openFile();
						dispose();
					}
				}
			);
			c.add(yesButton);
			
			noButton = new JButton("No");
			noButton.addActionListener(
				new ActionListener() {
					public void actionPerformed( ActionEvent e )
					{
						textArea.setDocument( new PlainDocument() );
						openFile();
						dispose();
					}
				}
			);
			c.add(noButton);
			
			setSize( 350, 100 );
			show();
		}
	}
	
       private class Actions implements ActionListener {
		
		public void actionPerformed( ActionEvent e ) {
			
	if( e.getSource() == cutItem || e.getSource() == popItems[0] ) doCut();
	else if( e.getSource() == copyItem || e.getSource() == popItems[1] ) doCopy();
	else if(e.getSource() == pasteItem || e.getSource() == popItems[2]) doPaste();
	else if(e.getSource() == deleteItem || e.getSource() == popItems[3]) doDelete();
	else if(e.getSource() == selectAllItem || e.getSource() == npopItem) doSelectAll();
    }
}

public void doCut() { textArea.cut(); }

public void doCopy() { textArea.copy(); }

public void doPaste() { textArea.paste(); }

public void doDelete() 
{
    String selection = textArea.getSelectedText();
    textArea.replaceRange( "", textArea.getSelectionStart(), textArea.getSelectionEnd() ); 
 }
public void doSelectAll() { textArea.selectAll(); }

}
