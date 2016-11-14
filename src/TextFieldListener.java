import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


public class TextFieldListener implements DocumentListener
{
	private FlagFrame frame;
	
	public TextFieldListener(FlagFrame f)
	{
		this.frame = f;
	}
	
	@Override
	public void insertUpdate(DocumentEvent e)
	{
		try
		{
			Document d = e.getDocument();
			frame.changeImage(d.getText(0, d.getLength()));
		} 
		catch (BadLocationException e1)
		{
			e1.printStackTrace();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e)
	{}

	@Override
	public void changedUpdate(DocumentEvent e)
	{}

}
