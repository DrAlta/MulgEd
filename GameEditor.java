import javax.swing.*;
import java.util.*;
import java.text.*;
import java.io.*;
import java.awt.event.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Rectangle;

//TODO: issues:
//	Executing the browser on the MulgEd page URL
//	Finding the images, and if not present, say error

public class GameEditor extends MyDialog {
	// Menu messages
	static final int MSG_GAMENEW = 1;
	static final int MSG_GAMEOPEN = 2;
	static final int MSG_GAMESAVE = 3;
	static final int MSG_GAMESAVEAS = 4;
	static final int MSG_GAMEANALYZE = 5;
	static final int MSG_GAMEIMPORT = 6;
	static final int MSG_GAMEEXPORT = 7;
	static final int MSG_GAMEEXIT = 8;
	
	static final int MSG_TILESBW = 101;
	static final int MSG_TILESGRAYSCALE = 102;
	static final int MSG_TILESCOLOR = 103;
	
	static final int MSG_HELPWHATSNEW = 201;
	static final int MSG_HELPABOUT = 202;
	
	static final int MSG_LEVELADD = 301;
	static final int MSG_LEVELEDIT = 302;
	static final int MSG_LEVELDELETE = 303;
	static final int MSG_LEVELANALYZE = 304;
	static final int MSG_LEVELPROPERTIES = 305;
	
	static final int MSG_NOTEADD = 401;
	static final int MSG_NOTEEDIT = 402;
	static final int MSG_NOTEDELETE = 403;
	
	// Controls messages
	static final int MSG_DEBUG = 1001;
	static final int MSG_GAMENAMECHANGED = 1002;
	static final int MSG_AUTHORNAMECHANGED = 1003;
	static final int MSG_LEVELCHANGED = 1004;
	static final int MSG_NOTECHANGED = 1005;
	static final int MSG_LEVELMOUSEDOWN = 1006;
	static final int MSG_LEVELMOUSEUP = 1007;
	static final int MSG_LEVELMOUSEMOVE = 1008;
	static final int MSG_LEVELMOUSELEAVE = 1009;
	
	// Constant strings for executing "What's new"
	static final String TEXTED_NO      = "more \"What's New.txt\"";
	static final String TEXTED_WINDOWS = "start \"What's New.txt\"";
	static final String TEXTED_LINUX   = "pico \"What's New.txt\"";
	
	JMenuBar menubar = new JMenuBar();
	boolean changed = false;
	boolean levelDrag = false;
	int dragFrom;
	Game game;
	ListBox levelList = new ListBox(this,MSG_LEVELCHANGED, MSG_LEVELEDIT,MSG_LEVELEDIT,MSG_LEVELADD, MSG_LEVELDELETE, MSG_LEVELMOUSEUP, MSG_LEVELMOUSEDOWN, MSG_LEVELMOUSEMOVE, MSG_LEVELMOUSELEAVE);
	JScrollPane levelScroll;
	ListBox noteList = new ListBox(this,MSG_NOTECHANGED, MSG_NOTEEDIT, MSG_NOTEEDIT, MSG_NOTEADD, MSG_NOTEDELETE,0,0,0,0);
	JScrollPane noteScroll;
	MyCheckBox debug = new MyCheckBox(this,MSG_DEBUG,"Debug");
	MyTextField gameName = new MyTextField(this,0,MSG_GAMENAMECHANGED,"");
	MyTextField authorName = new MyTextField(this,0,MSG_AUTHORNAMECHANGED,"");
	BlackLabel lcreated = new BlackLabel("");
	BlackLabel lchanged = new BlackLabel("");
	BlackLabel lchangeCount = new BlackLabel("");
	JProgressBar gauge = new JProgressBar(0,100);
	String showingFile;
	LevelEditor editor;
	LevelAnalyzer analyzer = new LevelAnalyzer(this);
	GameAnalyzer gameAnalyzer = new GameAnalyzer(this);
	LevelPropertiesEditor propertiesEditor = new LevelPropertiesEditor(this);
	About about = new About(this);
	SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");
	MyMenuItem  GameNew, GameOpen, GameSave, GameSaveAs, GameAnalyze, GameImport, GameExport, GameExit,
							LevelAdd, LevelEdit, LevelDelete, LevelAnalyze, LevelProperties,
							NoteAdd, NoteEdit, NoteDelete;
	JFileChooser pdbOpener = new JFileChooser();
	JFileChooser pdbSaver = new JFileChooser();
	JFileChooser levOpener = new JFileChooser();
	JFileChooser levSaver = new JFileChooser();
	ExtentionFileFilter pdbFilter = new ExtentionFileFilter("pdb","Palm Installable Databases (*.pdb)");
	ExtentionFileFilter levFilter = new ExtentionFileFilter("lev","Mulg II precompiled levels (*.lev)");
//	ExtentionFileFilter anyFilter = new ExtentionFileFilter(null,"All files");	
	
	public GameEditor() {
		super("MulgEd", false);
		setSize(420,300);
		initMenu();
		addComponents();
		levelList.setSelectionMode(0);
		noteList.setSelectionMode(0);
		showingFile = "";
		setGame(new Game());
		editor = new LevelEditor(this, propertiesEditor);
		initFileChoosers();
	}
	
	void initFileChoosers() {
		pdbOpener.setDialogType(JFileChooser.OPEN_DIALOG);
		pdbOpener.setDialogTitle("Open Mulg II game file");
		pdbOpener.addChoosableFileFilter(pdbFilter);
		pdbOpener.addChoosableFileFilter(pdbOpener.getAcceptAllFileFilter());
		pdbOpener.setFileFilter(pdbFilter);
		pdbOpener.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		pdbSaver.setDialogType(JFileChooser.SAVE_DIALOG);
		pdbSaver.setDialogTitle("Save Mulg II game file");
		pdbSaver.addChoosableFileFilter(pdbFilter);
		pdbSaver.addChoosableFileFilter(pdbSaver.getAcceptAllFileFilter());
		pdbSaver.setFileFilter(pdbFilter);
		pdbSaver.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		levOpener.setDialogType(JFileChooser.OPEN_DIALOG);
		levOpener.setDialogTitle("Import Mulg II precompiled game file");
		levOpener.addChoosableFileFilter(levFilter);
		levOpener.addChoosableFileFilter(levOpener.getAcceptAllFileFilter());
		levOpener.setFileFilter(levFilter);
		levOpener.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		levSaver.setDialogType(JFileChooser.SAVE_DIALOG);
		levSaver.setDialogTitle("Export Mulg II precompiled game file");
		levSaver.addChoosableFileFilter(levFilter);
		levSaver.addChoosableFileFilter(levSaver.getAcceptAllFileFilter());
		levSaver.setFileFilter(levFilter);
		levSaver.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}
	
	void initMenu() {
		JMenu m;
		ButtonGroup bg;
		MyRadioButtonMenuItem mi;
		m = new JMenu("Game");
		m.setMnemonic(KeyEvent.VK_G);
		m.add(GameNew = new MyMenuItem(this,MSG_GAMENEW,"New",KeyStroke.getKeyStroke("control N"), KeyEvent.VK_N));
		m.add(GameOpen = new MyMenuItem(this,MSG_GAMEOPEN,"Open ...",KeyStroke.getKeyStroke("control O"), KeyEvent.VK_O));
		m.add(GameSave = new MyMenuItem(this,MSG_GAMESAVE,"Save",KeyStroke.getKeyStroke("control S"), KeyEvent.VK_S));
		m.add(GameSaveAs = new MyMenuItem(this,MSG_GAMESAVEAS,"Save as ...", KeyEvent.VK_A));
		m.add(GameAnalyze = new MyMenuItem(this,MSG_GAMEANALYZE,"Analyze ...", KeyEvent.VK_Y));
		m.add(new JSeparator());
		m.add(GameImport = new MyMenuItem(this,MSG_GAMEIMPORT,"Import .LEV ...", KeyEvent.VK_I));
		m.add(GameExport = new MyMenuItem(this,MSG_GAMEEXPORT,"Export .LEV ...", KeyEvent.VK_E));
		m.add(new JSeparator());
		m.add(GameExit = new MyMenuItem(this,MSG_GAMEEXIT,"Exit",KeyStroke.getKeyStroke("control X"), KeyEvent.VK_X));
		menubar.add(m);
		
		m = new JMenu("Level");
		m.setMnemonic(KeyEvent.VK_L);
		m.add(LevelAdd = new MyMenuItem(this,MSG_LEVELADD,"Add ...",KeyStroke.getKeyStroke("INSERT"), KeyEvent.VK_A));
		m.add(LevelEdit = new MyMenuItem(this,MSG_LEVELEDIT,"Edit ...",KeyStroke.getKeyStroke("ENTER"), KeyEvent.VK_E));
		m.add(LevelDelete = new MyMenuItem(this,MSG_LEVELDELETE,"Delete",KeyStroke.getKeyStroke("DELETE"), KeyEvent.VK_D));
		m.add(LevelAnalyze = new MyMenuItem(this,MSG_LEVELANALYZE,"Analyze ...", KeyEvent.VK_Y));
		m.add(LevelProperties = new MyMenuItem(this,MSG_LEVELPROPERTIES,"Properties ...",KeyStroke.getKeyStroke("alt ENTER"), KeyEvent.VK_P));
		menubar.add(m);
		
		m = new JMenu("Note");
		m.setMnemonic(KeyEvent.VK_N);
		m.add(NoteAdd = new MyMenuItem(this,MSG_NOTEADD,"Add ...",KeyStroke.getKeyStroke("shift INSERT"), KeyEvent.VK_A));
		m.add(NoteEdit = new MyMenuItem(this,MSG_NOTEEDIT,"Edit ...",KeyStroke.getKeyStroke("shift ENTER"), KeyEvent.VK_E));
		m.add(NoteDelete = new MyMenuItem(this,MSG_NOTEDELETE,"Delete",KeyStroke.getKeyStroke("shift DELETE"), KeyEvent.VK_D));
		menubar.add(m);
		
		m = new JMenu("Tiles");
		m.setMnemonic(KeyEvent.VK_T);
		bg = new ButtonGroup();
		mi = new MyRadioButtonMenuItem(this,MSG_TILESBW,"Black & White", true, KeyEvent.VK_B);
		bg.add(mi);
		m.add(mi);
		mi = new MyRadioButtonMenuItem(this,MSG_TILESGRAYSCALE,"4bit GrayScale", false, KeyEvent.VK_4);
		bg.add(mi);
		m.add(mi);
		mi = new MyRadioButtonMenuItem(this,MSG_TILESCOLOR,"2bit GrayScale", false, KeyEvent.VK_2);
		bg.add(mi);
		m.add(mi);
		menubar.add(m);
		
		m = new JMenu("Help");
		m.setMnemonic(KeyEvent.VK_H);
		m.add(new MyMenuItem(this,MSG_HELPWHATSNEW,"What's new?",KeyStroke.getKeyStroke("shift F1"), KeyEvent.VK_W));
		m.add(new MyMenuItem(this,MSG_HELPABOUT,"About MulgEd",KeyStroke.getKeyStroke("F1"), KeyEvent.VK_A));
		menubar.add(m);
		setJMenuBar(menubar);
	}
	
	public void addComponents() {
		GridBagConstraints c = new GridBagConstraints();
		BlackLabel l;
		MyTextField tf;
		MyCheckBox cb;
		
		GridBagLayout gbl = new GridBagLayout();
		JPanel p = new JPanel(gbl);
		layout.setConstraints(p,new GridBagConstraints(0,0,3,1,1,1,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		paneadd(p);
		
		l = new BlackLabel("Levels:");
		gbl.setConstraints(l,new GridBagConstraints(0,0,1,1,0,0,c.WEST,c.BOTH,new Insets(1,1,1,1),0,0));
		p.add(l);
		l = new BlackLabel("Notes:");
		gbl.setConstraints(l,new GridBagConstraints(1,0,1,1,0,0,c.WEST,c.BOTH,new Insets(1,1,1,1),0,0));
		p.add(l);
		
		levelScroll = new JScrollPane(levelList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		gbl.setConstraints(levelScroll,new GridBagConstraints(0,1,1,1,0.7,1,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		p.add(levelScroll);
		
		noteScroll = new JScrollPane(noteList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		gbl.setConstraints(noteScroll,new GridBagConstraints(1,1,1,1,1.3,1,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		p.add(noteScroll);
		
		l = new BlackLabel("Game name:");
		layout.setConstraints(l,new GridBagConstraints(0,1,1,1,0,0,c.WEST,c.BOTH,new Insets(1,1,1,1),0,0));
		paneadd(l);
		layout.setConstraints(gameName,new GridBagConstraints(1,1,1,1,1,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(gameName);
		debug.setMnemonic(KeyEvent.VK_D);
		layout.setConstraints(debug,new GridBagConstraints(2,1,1,1,0,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(debug);
		
		l = new BlackLabel("Author Name:");
		layout.setConstraints(l,new GridBagConstraints(0,2,1,1,0,0,c.WEST,c.BOTH,new Insets(1,1,1,1),0,0));
		paneadd(l);
		layout.setConstraints(authorName,new GridBagConstraints(1,2,2,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(authorName);
		
		l = new BlackLabel("Created:");
		layout.setConstraints(l,new GridBagConstraints(0,3,1,1,0,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(l);
		layout.setConstraints(lcreated,new GridBagConstraints(1,3,2,1,0,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(lcreated);
		
		l = new BlackLabel("Changed:");
		layout.setConstraints(l,new GridBagConstraints(0,4,1,1,0,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(l);
		layout.setConstraints(lchanged,new GridBagConstraints(1,4,2,1,0,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(lchanged);
		
		l = new BlackLabel("Change Count:");
		layout.setConstraints(l,new GridBagConstraints(0,5,1,1,0,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(l);
		layout.setConstraints(lchangeCount,new GridBagConstraints(1,5,2,1,0,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(lchangeCount);
		
		layout.setConstraints(gauge,new GridBagConstraints(0,6,3,1,0,0,c.WEST,c.BOTH,new Insets(1,1,1,1),0,0));
		paneadd(gauge);
	}
	
	public void windowClosing(WindowEvent e) {
		process(MSG_GAMEEXIT,0,0,0);
	}
	
	public void process(int message, int x, int y, int z) {
		int index = 0;
		switch(message) {
			case MSG_GAMENEW:
				if (changed) {
					switch (JOptionPane.showConfirmDialog(this,"Do you wish to save the changes to game \""+game.name+"\" ?","Save changes?",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
						case JOptionPane.YES_OPTION:
							process(MSG_GAMESAVE,0,0,0);
							break;
						case JOptionPane.NO_OPTION:
							break;
						case JOptionPane.CANCEL_OPTION:
							return;
					}
				}
				authorName.setEnabled(true);
				showingFile = "";
				setGame(new Game());
				break;
			case MSG_GAMEOPEN:
				if (changed) {
					switch (JOptionPane.showConfirmDialog(this,"Do you want to save changes to game \""+game.name+"\"","Save changes?",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE)) {
						case JOptionPane.YES_OPTION:
							process(MSG_GAMESAVE,0,0,0);
							break;
						case JOptionPane.NO_OPTION:
							break;
						case JOptionPane.CANCEL_OPTION:
							return;
					}
				}
				if (pdbOpener.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
					try {
						showingFile = pdbOpener.getSelectedFile().toString();
						setGame(Game.loadFromFile(showingFile));
						authorName.setEnabled(false);
					} catch (IOException ioe) {
						JOptionPane.showMessageDialog(this,"Error loading file:"+ioe.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
					}
				}
				break;
			case MSG_GAMESAVE:
				if (showingFile.equals("")) {
					process(MSG_GAMESAVEAS,0,0,0);
				} else {
					if (game.author.equals("")) {
						if (JOptionPane.showConfirmDialog(this, "Author name was not specified.\n"+
																										"You will not be able to change the author name once the files is saved.\n"+
																										"Do you wish to save the file without specifying the author name?",
																										"Are you sure?",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
							return;
					}					
					try {
						game.modificationDate = game.now();
						game.modificationCount++;
						game.saveToFile(showingFile);
						lchanged.setText(format(game.palm2javaDate(game.modificationDate)));
						lchangeCount.setText(String.valueOf(game.modificationCount));
						authorName.setEnabled(false);
						changed = false;
					} catch (IOException ioe) {
						JOptionPane.showMessageDialog(this,"Error saving file","Error",JOptionPane.ERROR_MESSAGE);
					}
				}
				break;
			case MSG_GAMESAVEAS:
				if (game.author.equals("")) {
					if (JOptionPane.showConfirmDialog(this, "Author name was not specified.\n"+
																									"You will not be able to change the author name once the files is saved.\n"+
																									"Do you wish to save the file without specifying the author name?",
																									"Are you sure?",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
						return;
				}
				pdbSaver.setSelectedFile(new File(showingFile));
				if (pdbSaver.showSaveDialog(this) == pdbSaver.APPROVE_OPTION) {
						try {
							showingFile = pdbSaver.getSelectedFile().toString();
							game.modificationDate = game.now();
							game.modificationCount++;
							game.saveToFile(showingFile);
							lchanged.setText(format(game.palm2javaDate(game.modificationDate)));
							lchangeCount.setText(String.valueOf(game.modificationCount));
							authorName.setEnabled(false);
							changed = false;
						} catch (IOException ioe) {
							JOptionPane.showMessageDialog(this,"Error saving file","Error",JOptionPane.ERROR_MESSAGE);
						}
				}
				break;
			case MSG_GAMEANALYZE:
				gameAnalyzer.analyze(game);
				gameAnalyzer.showDlg();
				break;
			case MSG_GAMEIMPORT:
				if (changed) {
					switch (JOptionPane.showConfirmDialog(this,"Do you want to save changes to game \""+game.name+"\"","Save changes?",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE)) {
						case JOptionPane.YES_OPTION:
							process(MSG_GAMESAVE,0,0,0);
							break;
						case JOptionPane.NO_OPTION:
							break;
						case JOptionPane.CANCEL_OPTION:
							return;
					}
				}
				if (levOpener.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
					try {
						String fname = levOpener.getSelectedFile().toString();
						Game g = Game.importFromFile(fname);
						if (g != null) {
							showingFile = fname;
							setGame(g);
							authorName.setEnabled(false);
							changed = true;
						}
					} catch (IOException ioe) {
						JOptionPane.showMessageDialog(this,"Error importing file","Error",JOptionPane.ERROR_MESSAGE);
					}
				}
				break;
			case MSG_GAMEEXPORT:
				levSaver.setSelectedFile(new File(showingFile));
				if (levSaver.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
					try {
						game.modificationDate = game.now();
						game.modificationCount++;
						game.exportToFile(levSaver.getSelectedFile().toString());
						lchanged.setText(format(game.palm2javaDate(game.modificationDate)));
						lchangeCount.setText(String.valueOf(game.modificationCount));
						authorName.setEnabled(false);
						changed = false;
					} catch (IOException ioe) {
						JOptionPane.showMessageDialog(this,"Error exporting file","Error",JOptionPane.ERROR_MESSAGE);
					}
				}
				break;
			case MSG_GAMEEXIT:
				if (changed) {
					switch (JOptionPane.showConfirmDialog(this,"Do you want to save changes to game \""+game.name+"\"?","Save changes?",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
						case JOptionPane.YES_OPTION:
							process(MSG_GAMESAVE,0,0,0);
							break;
						case JOptionPane.NO_OPTION:
							break;
						case JOptionPane.CANCEL_OPTION:
							return;
					}
				}
				System.exit(0);
				break;
			case MSG_TILESBW:
				editor.loadTileImages(this, "2BitTiles", gauge, true);
				break;
			case MSG_TILESGRAYSCALE:
				editor.loadTileImages(this, "4BitTiles", gauge, true);
				break;
			case MSG_TILESCOLOR:
				editor.loadTileImages(this, "8BitTiles", gauge, true);
				break;
			case MSG_HELPABOUT:
				about.showDlg();
				break;
			case MSG_HELPWHATSNEW:
				try {
					String ss = (String)System.getProperties().get("os.name");
					if (ss == null)
						Runtime.getRuntime().exec(TEXTED_NO);
					else if (ss.startsWith("Windows"))
						Runtime.getRuntime().exec(TEXTED_WINDOWS);
					else if (ss.startsWith("Linux"))
						Runtime.getRuntime().exec(TEXTED_LINUX);
					else
						Runtime.getRuntime().exec(TEXTED_NO);
				} catch (IOException ioe) {}
				break;
			case MSG_LEVELADD:
				if (game.levelCount >= Game.MAX_LEVELS) {
					JOptionPane.showMessageDialog(this, "You cannot add another level to this game.\n"+
																							"The maximum number of levels, "+Game.MAX_LEVELS+", has been reached.",
																							"You cannot do that",JOptionPane.INFORMATION_MESSAGE);
				} else {
					propertiesEditor.width.setValue(1);
					propertiesEditor.height.setValue(1);
					propertiesEditor.name.setText("Level "+String.valueOf(game.levelCount+1));
					propertiesEditor.setTitle("Add new level");
					if (propertiesEditor.showDlg() == OK) {
						Level level = new Level();
						level.name = propertiesEditor.name.getText();
						level.adjustWidth(Level.Screens2TilesW(propertiesEditor.width.getValue()));
						level.adjustHeight(Level.Screens2TilesH(propertiesEditor.height.getValue()));
						game.levels[game.levelCount] = level;
						levelList.model.addElement(game.levels[game.levelCount].name);
						levelList.setSelectedIndex(game.levelCount);
						game.levelCount++;
						changed = true;
						if (game.levelCount < Game.MAX_LEVELS)
							LevelAdd.setEnabled(true);
						else
							LevelAdd.setEnabled(false);
					}
				}
				break;
			case MSG_LEVELEDIT:
				levelDrag = false;
				index = levelList.getSelectedIndex();
				if (index >= 0) {
					editor.level = new Level(game.levels[index]);
					editor.game = game;
					editor.prepareLevel();
					if (editor.showDlg() == OK) {
						game.levels[index] = editor.level;
						levelList.model.set(index,game.levels[index].name);
						changed = true;
					}
				}
				break;
			case MSG_LEVELDELETE:
				index = levelList.getSelectedIndex();
				if (index >= 0) {
					if (JOptionPane.showConfirmDialog(this, "Really delete level \""+game.levels[index].name+"\"?",
																									"Are you sure?",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						levelList.model.removeElementAt(index);
						for (int i = index+1; i<game.levelCount; i++) {
							game.levels[i-1] = game.levels[i];
						}
						if (game.levelCount > 0)
							game.levels[game.levelCount-1] = null;
						game.levelCount--;
						changed = true;
						if (game.levelCount < Game.MAX_LEVELS) 
							LevelAdd.setEnabled(true);
						else 
							LevelAdd.setEnabled(false);
						selectNoLevel();
					}
				}
				break;
			case MSG_LEVELANALYZE:
				index = levelList.getSelectedIndex();
				if (index >= 0) {
					analyzer.analyze(game,game.levels[index]);
					analyzer.showDlg();
				}
				break;
			case MSG_LEVELPROPERTIES:
				index = levelList.getSelectedIndex();
				if (index >= 0) {
					propertiesEditor.width.setValue(Level.Tiles2ScreensW(game.levels[index].width));
					propertiesEditor.height.setValue(Level.Tiles2ScreensH(game.levels[index].height));
					propertiesEditor.name.setText(game.levels[index].name);
					propertiesEditor.setTitle("Edit level properties");
					if (propertiesEditor.showDlg() == OK) {
						game.levels[index].name = propertiesEditor.name.getText();
						game.levels[index].adjustWidth(Level.Screens2TilesW(propertiesEditor.width.getValue()));
						game.levels[index].adjustHeight(Level.Screens2TilesH(propertiesEditor.height.getValue()));
						levelList.model.set(index,game.levels[index].name);
						changed = true;
					}
				}
				break;
			case MSG_NOTEADD:
				String newText = (String)JOptionPane.showInputDialog(this,"Please enter new text for the note","New Note",JOptionPane.PLAIN_MESSAGE);
				if ((newText != null) && (newText.length() > 0)) {
					game.notes[game.noteCount] = newText;
					game.noteCount++;
					noteList.model.addElement(newText);
					noteList.setSelectedIndex(noteList.model.getSize()-1);
					changed = true;
					if (game.noteCount < Game.MAX_NOTES)
						NoteAdd.setEnabled(true);
					else 
						NoteAdd.setEnabled(false);
				}
				break;
			case MSG_NOTEEDIT:
				index = noteList.getSelectedIndex();
				if (index >= 0) {
					String noteText = (String)noteList.model.getElementAt(index);
					String newNoteText = (String)JOptionPane.showInputDialog(this,"Please edit the text of this note.","Edit note",JOptionPane.PLAIN_MESSAGE,null,null,noteText);
					if (newNoteText != null) {
						game.notes[index] = newNoteText;
						noteList.model.set(index,newNoteText);
						changed = true;
					}
				}
				break;
			case MSG_NOTEDELETE:
				index = noteList.getSelectedIndex();
				if (index >= 0) {
					if (JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the note \""+game.notes[index]+"\" ?\n"+
																							"This action might have unexpected, non-reversable consequences if\n"+
																							"any letter tile has been associated with this note.","Are you sure ?",
																							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						noteList.model.removeElementAt(index);
						for (int i = index+1; i<game.noteCount; i++) {
							game.notes[i-1] = game.notes[i];
						}
						if (game.noteCount > 0)
							game.notes[game.noteCount-1] = null;
						game.noteCount--;
						changed = true;
						selectNoNote();
					}
				}
				break;
			case MSG_DEBUG:
				game.debug = debug.isSelected();
				changed = true;
				break;
			case MSG_GAMENAMECHANGED:
				game.name = gameName.getText();
				break;
			case MSG_AUTHORNAMECHANGED:
				game.author = authorName.getText();
				break;
			case MSG_LEVELCHANGED:
				if (levelList.getSelectedIndex() == -1) {
					LevelDelete.setEnabled(false);
					LevelEdit.setEnabled(false);
					LevelAnalyze.setEnabled(false);
					LevelProperties.setEnabled(false);
				} else {
					LevelDelete.setEnabled(true);
					LevelEdit.setEnabled(true);
					LevelAnalyze.setEnabled(true);
					LevelProperties.setEnabled(true);
				}
				break;
			case MSG_NOTECHANGED:
				if (noteList.getSelectedIndex() == -1) {
					NoteEdit.setEnabled(false);
					NoteDelete.setEnabled(false);
				} else {
					NoteEdit.setEnabled(true);
					NoteDelete.setEnabled(true);
				}
				break;
			case MSG_LEVELMOUSEUP:
				levelDrag = false;
				break;
			case MSG_LEVELMOUSEDOWN:
				dragFrom = levelList.getSelectedIndex();
				levelDrag = true;
				break;
			case MSG_LEVELMOUSELEAVE:
//				levelDrag = false;
				break;
			case MSG_LEVELMOUSEMOVE:
				if (levelDrag) {
					int current = levelList.getSelectedIndex();
					if (current == -1)
						levelDrag = false;
					else {
						if (current != dragFrom) {
							Object s = levelList.model.getElementAt(dragFrom);
							levelList.model.set(dragFrom,levelList.model.getElementAt(current));
							levelList.model.set(current,s);
							Level tempLevel = game.levels[dragFrom];
							game.levels[dragFrom] = game.levels[current];
							game.levels[current] = tempLevel;
							levelList.setSelectedIndex(current);
							dragFrom = current;
						}
					}
				}
				break;
		}
	}
	
	int getLevelIndex(int x, int y) {
		for (int i=0; i<levelList.model.getSize(); i++) {
			Rectangle r = levelList.getCellBounds(i,i);
			if (r.contains(x,y))
				return i;
		}
		return -1;
	}
		
	String format(long time) {
		return formatter.format(new Date(time));
	}
	
	void selectNoLevel() {
		levelList.setSelectedIndex(-1);
		process(MSG_LEVELCHANGED,0,0,0);
	}
	
	void selectNoNote() {
		noteList.setSelectedIndex(-1);
		process(MSG_NOTECHANGED,0,0,0);
	}
	
	void setGame(Game g) {
		game = g;
		levelList.model.removeAllElements();
		for (int i=0; i<game.levelCount; i++)
			levelList.model.addElement(game.levels[i].name);
		noteList.model.removeAllElements();
		for (int i=0; i<game.noteCount; i++)
			noteList.model.addElement(game.notes[i]);
		setTitle("MulgEd - "+showingFile);
		gameName.setText(game.name);
		authorName.setText(game.author);
		if (game.creationDate == 0xdf77dfb2)
			lcreated.setText("n/a");
		else
			lcreated.setText(format(game.palm2javaDate(game.creationDate)));
		if (game.modificationDate == 0xdf77dfb2)
			lchanged.setText("n/a");
		else
			lchanged.setText(format(game.palm2javaDate(game.modificationDate)));
		lchangeCount.setText(String.valueOf(game.modificationCount));
		debug.setSelected(game.debug);
		changed = false;
		if (game.levelCount < Game.MAX_LEVELS) 
			LevelAdd.setEnabled(true);
		else 
			LevelAdd.setEnabled(false);
		if (game.noteCount < Game.MAX_NOTES) 
			NoteAdd.setEnabled(true);
		else 
			NoteAdd.setEnabled(false);
		selectNoLevel();
		selectNoNote();
	}
	
	
}