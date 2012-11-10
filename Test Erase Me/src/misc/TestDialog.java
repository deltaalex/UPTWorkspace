package misc;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class TestDialog extends Dialog {

	protected TestDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) 
	{
		Composite c = new Composite(parent, SWT.None);
		Label label = new Label(c, SWT.None);
		label.setText("aaa");
		
		
		return c;
	}

}
