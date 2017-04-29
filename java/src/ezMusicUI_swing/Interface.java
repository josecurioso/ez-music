package ezMusicUI_swing;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Tree;

public class Interface extends ApplicationWindow {
	private Text text;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Table table;

	/**
	 * Create the application window.
	 */
	public Interface() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setBounds(10, 0, 682, 60);
		
		Label lblLink = new Label(composite, SWT.NONE);
		lblLink.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 10, SWT.NORMAL));
		lblLink.setBounds(10, 29, 32, 15);
		lblLink.setText("Link:");
		
		text = new Text(composite, SWT.BORDER);
		text.setBounds(49, 26, 529, 21);
		
		Button btnNewButton = new Button(composite, SWT.FLAT | SWT.CENTER);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 10, SWT.NORMAL));
		btnNewButton.setTouchEnabled(true);
		btnNewButton.setBounds(584, 20, 88, 34);
		btnNewButton.setText("Download!");
		
		Button btnAutodetectYoutubeLink = new Button(container, SWT.CHECK);
		btnAutodetectYoutubeLink.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 10, SWT.NORMAL));
		btnAutodetectYoutubeLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnAutodetectYoutubeLink.setBounds(62, 66, 192, 16);
		btnAutodetectYoutubeLink.setText("Autodetect YouTube link");
		
		Button btnAutodetectYoutubeLink_1 = new Button(container, SWT.CHECK);
		btnAutodetectYoutubeLink_1.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 10, SWT.NORMAL));
		btnAutodetectYoutubeLink_1.setBounds(62, 89, 192, 16);
		btnAutodetectYoutubeLink_1.setText("Autodetect SoundCloud link");
		
		ScrolledForm scrldfrmNewScrolledform = formToolkit.createScrolledForm(container);
		scrldfrmNewScrolledform.setAlwaysShowScrollBars(true);
		scrldfrmNewScrolledform.setTouchEnabled(true);
		scrldfrmNewScrolledform.setBounds(52, 120, 606, 298);
		formToolkit.paintBordersFor(scrldfrmNewScrolledform);
		scrldfrmNewScrolledform.setText("Download Queue");
		
		table = formToolkit.createTable(scrldfrmNewScrolledform.getBody(), SWT.FULL_SELECTION | SWT.MULTI);
		table.setTouchEnabled(true);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 10, SWT.NORMAL));
		table.setBounds(10, 10, 569, 228);
		formToolkit.paintBordersFor(table);
		
		TableItem tableItem = new TableItem(table, SWT.NONE);
		tableItem.setText("Cara al sol");
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Thumbnail");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("Title");
		
		TableColumn tblclmnArtist = new TableColumn(table, SWT.NONE);
		tblclmnArtist.setWidth(100);
		tblclmnArtist.setText("Artist");
		
		TableColumn tblclmnAlbum = new TableColumn(table, SWT.NONE);
		tblclmnAlbum.setWidth(100);
		tblclmnAlbum.setText("Album");
		
		TableItem tableItem_1 = new TableItem(table, SWT.NONE);
		tableItem_1.setText("Ala es grande");
		
		TableItem tableItem_2 = new TableItem(table, SWT.NONE);
		tableItem_2.setText("Kebab");

		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		
		MenuManager menuManager_1 = new MenuManager("New MenuManager");
		menuManager.add(menuManager_1);
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Interface window = new Interface();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("New Application");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(718, 545);
	}
}
