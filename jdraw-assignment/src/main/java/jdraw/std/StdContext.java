/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */
package jdraw.std;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import jdraw.commands.AddFigureCommand;
import jdraw.figures.decorators.AbstractFigureDecorator;
import jdraw.figures.decorators.BorderDecorator;
import jdraw.figures.decorators.GreenDecorator;
import jdraw.figures.group.GroupService;
import jdraw.framework.DrawCommandHandler;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawToolFactory;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureGroup;
import jdraw.grids.BoundingBoxGrid;
import jdraw.grids.RectGrid;

/**
 * Standard implementation of interface DrawContext.
 * 
 * @see DrawView
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.6, 24.09.09
 */
@SuppressWarnings("serial")
public class StdContext extends AbstractContext {

	private List<Figure> clipboard;

	/**
	 * Constructs a standard context with a default set of drawing tools.
	 * 
	 * @param view the view that is displaying the actual drawing.
	 */
	public StdContext(DrawView view) {
		super(view, null);
	}

	/**
	 * Constructs a standard context. The drawing tools available can be
	 * parameterized using <code>toolFactories</code>.
	 * 
	 * @param view          the view that is displaying the actual drawing.
	 * @param toolFactories a list of DrawToolFactories that are available to the
	 *                      user
	 */
	public StdContext(DrawView view, List<DrawToolFactory> toolFactories) {
		super(view, toolFactories);
	}

	/**
	 * Creates and initializes the "Edit" menu.
	 * 
	 * @return the new "Edit" menu.
	 */
	@Override
	protected JMenu createEditMenu() {
		JMenu editMenu = new JMenu("Edit");
		final JMenuItem undo = new JMenuItem("Undo");
		undo.setAccelerator(KeyStroke.getKeyStroke("control Z"));
		editMenu.add(undo);
		undo.addActionListener(e -> {
			final DrawCommandHandler h = getModel().getDrawCommandHandler();
			if (h.undoPossible()) {
				h.undo();
			}
		});

		final JMenuItem redo = new JMenuItem("Redo");
		redo.setAccelerator(KeyStroke.getKeyStroke("control Y"));
		editMenu.add(redo);
		redo.addActionListener(e -> {
			final DrawCommandHandler h = getModel().getDrawCommandHandler();
			if (h.redoPossible()) {
				h.redo();
			}
		});
		editMenu.addSeparator();

		JMenuItem sa = new JMenuItem("SelectAll");
		sa.setAccelerator(KeyStroke.getKeyStroke("control A"));
		editMenu.add(sa);
		sa.addActionListener(e -> {
			getModel().getFigures().forEachOrdered(f -> getView().addToSelection(f));
			getView().repaint();
		});

		editMenu.addSeparator();
		JMenuItem cutMenu = editMenu.add("Cut");
		cutMenu.setAccelerator(KeyStroke.getKeyStroke("control X"));
		cutMenu.addActionListener(e -> {
			clipboard = getView().getSelection();
			// retain figure order so the selection order does not influences order in group
			clipboard.sort(Comparator.comparing(f -> getView().getModel().getFigureIndex(f))); // SLOW
			clipboard.forEach(f -> getView().getModel().removeFigure(f));
			getModel().getDrawCommandHandler().beginScript();
			for (Figure figure : clipboard) {
				getModel().getDrawCommandHandler().addCommand(new RemoveFigureCommand(getModel(), figure));
			}
			getModel().getDrawCommandHandler().endScript();
		});

		JMenuItem copyMenu = editMenu.add("Copy");
		copyMenu.setAccelerator(KeyStroke.getKeyStroke("control C"));
		copyMenu.addActionListener(e -> {
			clipboard = getView().getSelection();
			// retain figure order so the selection order does not influences order in group
			clipboard.sort(Comparator.comparing(f -> getView().getModel().getFigureIndex(f))); // SLOW
		});

		JMenuItem pasteMenu = editMenu.add("Paste");
		pasteMenu.setAccelerator(KeyStroke.getKeyStroke("control V"));
		pasteMenu.addActionListener(e -> {
			if (clipboard != null && !clipboard.isEmpty()) {
				getView().clearSelection();
				getModel().getDrawCommandHandler().beginScript();
				clipboard.stream()
						.map(Figure::clone)
						.forEachOrdered(f -> {
							getModel().getDrawCommandHandler().addCommand(new AddFigureCommand(getModel(), f));
							getView().getModel().addFigure(f);
							getView().addToSelection(f);
						});
				getModel().getDrawCommandHandler().endScript();
			}
		});

		editMenu.addSeparator();
		JMenuItem clear = new JMenuItem("Clear");
		editMenu.add(clear);
		clear.addActionListener(e -> {
			getModel().removeAllFigures();
		});

		editMenu.addSeparator();
		JMenuItem groupMenu = new JMenuItem("Group");
		groupMenu.addActionListener(e -> {
			List<Figure> selected = getView().getSelection();
			selected.sort(Comparator.comparing(f -> getView().getModel().getFigureIndex(f))); // SLOW
			FigureGroup group = GroupService.group(getModel(), selected);
			// retain figure order so the selection order does not influences order in group
			getView().addToSelection(group);
		});
		editMenu.add(groupMenu);

		JMenuItem ungroup = new JMenuItem("Ungroup");
		ungroup.addActionListener(e -> {
			List<Figure> selectedFigures = getView().getSelection();
			for (Figure f : selectedFigures) {
				if (f.isOfType(FigureGroup.class)) {
					List<Figure> ungroupedFigures = GroupService.ungroup(getModel(), f.getOfType(FigureGroup.class));
					ungroupedFigures.forEach(f2 -> getView().addToSelection(f2));
				}
			}
		});
		editMenu.add(ungroup);

		editMenu.addSeparator();

		JMenu orderMenu = new JMenu("Order...");
		JMenuItem frontItem = new JMenuItem("Bring To Front");
		frontItem.addActionListener(e -> {
			bringToFront(getView().getModel(), getView().getSelection());
		});
		orderMenu.add(frontItem);
		JMenuItem backItem = new JMenuItem("Send To Back");
		backItem.addActionListener(e -> {
			sendToBack(getView().getModel(), getView().getSelection());
		});
		orderMenu.add(backItem);
		editMenu.add(orderMenu);

		JMenu grid = new JMenu("Grid...");
		grid.add("Normal Grid").addActionListener(event -> {
			getView().setGrid(null);
		});

		grid.add("Rect 20x20").addActionListener(event -> {
			getView().setGrid(new RectGrid());
		});

		grid.add("BoundingBox").addActionListener(event -> {
			getView().setGrid(new BoundingBoxGrid(this));
		});
		editMenu.add(grid);

		editMenu.addSeparator();

		JMenu decoratorMenu = new JMenu("Decorators");
		decoratorMenu.add("Toggle Green").addActionListener(event -> {
			List<Figure> s = getView().getSelection();
			getView().clearSelection();
			for (Figure f : s) {
				if (f.isOfType(GreenDecorator.class)) {
					GreenDecorator gd = f.getOfType(GreenDecorator.class);

					if (gd.getOuter() == null) { // green greendecorator is outermost
						Figure f2 = gd.getInner();
						f2.setOuter(null);
						int index = getModel().getFigureIndex(f);
						getModel().addFigure(f2);
						getModel().removeFigure(f);
						getModel().setFigureIndex(f2, index);
						getView().addToSelection(f2);
					} else { // if green decorator is somewhere between, just remove it
						AbstractFigureDecorator outer = (AbstractFigureDecorator) gd.getOuter();
						outer.setInner(gd.getInner());
						// selection does not need to change
						// no figures have to be removed from selection + model
					}
				} else {
					Figure f2 = new GreenDecorator(f);
					int index = getModel().getFigureIndex(f);
					getModel().addFigure(f2);
					getModel().removeFigure(f);
					getModel().setFigureIndex(f2, index);
					getView().addToSelection(f2);
				}
			}
		});
		JMenuItem addBorder = new JMenuItem("Add Border Decorator");
		decoratorMenu.add(addBorder);
		addBorder.addActionListener(e -> {
			List<Figure> s = getView().getSelection();
			getView().clearSelection();
			for (Figure f : s) {
				BorderDecorator dec = new BorderDecorator(f);
				getModel().removeFigure(f);
				getModel().addFigure(dec);
				getView().addToSelection(dec);
			}
		});
		editMenu.add(decoratorMenu);

		return editMenu;
	}

	/**
	 * Creates and initializes items in the file menu.
	 * 
	 * @return the new "File" menu.
	 */
	@Override
	protected JMenu createFileMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem open = new JMenuItem("Open");
		fileMenu.add(open);
		open.setAccelerator(KeyStroke.getKeyStroke("control O"));
		open.addActionListener(e -> doOpen());

		JMenuItem save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke("control S"));
		fileMenu.add(save);
		save.addActionListener(e -> doSave());

		JMenuItem exit = new JMenuItem("Exit");
		fileMenu.add(exit);
		exit.addActionListener(e -> System.exit(0));

		return fileMenu;
	}

	@Override
	protected void doRegisterDrawTools() {
		getToolFactories().forEach(toolFactory -> {
			if (toolFactory == null) {
				addTool(null);
			} else {
				addTool(toolFactory.createTool(this));
			}
		});
	}

	/**
	 * Changes the order of figures and moves the figures in the selection to the
	 * front, i.e. moves them to the end of the list of figures.
	 * 
	 * @param model     model in which the order has to be changed
	 * @param selection selection which is moved to front
	 */
	public void bringToFront(DrawModel model, List<Figure> selection) {
		// the figures in the selection are ordered according to the order in the model
		List<Figure> orderedSelection = model.getFigures().filter(f -> selection.contains(f))
				.collect(Collectors.toList());
		Collections.reverse(orderedSelection);
		int pos = (int) model.getFigures().count();
		for (Figure f : orderedSelection) {
			model.setFigureIndex(f, --pos);
		}
	}

	/**
	 * Changes the order of figures and moves the figures in the selection to the
	 * back, i.e. moves them to the front of the list of figures.
	 * 
	 * @param model     model in which the order has to be changed
	 * @param selection selection which is moved to the back
	 */
	public void sendToBack(DrawModel model, List<Figure> selection) {
		// the figures in the selection are ordered according to the order in the model
		List<Figure> orderedSelection = model.getFigures().filter(f -> selection.contains(f))
				.collect(Collectors.toList());
		int pos = 0;
		for (Figure f : orderedSelection) {
			model.setFigureIndex(f, pos++);
		}
	}

	/**
	 * Handles the saving of a drawing to a file.
	 */
	private void doSave() {
		JFileChooser chooser = new JFileChooser(getClass().getResource("").getFile());
		chooser.setDialogTitle("Save Graphic");
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);

		chooser.setFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.draw)", "draw"));
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.xml)", "xml"));
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.json)", "json"));

		int res = chooser.showSaveDialog(this);

		if (res == JFileChooser.APPROVE_OPTION) {
			// save graphic
			File file = chooser.getSelectedFile();
			FileFilter filter = chooser.getFileFilter();
			if (filter instanceof FileNameExtensionFilter && !filter.accept(file)) {
				file = new File(chooser.getCurrentDirectory(),
						file.getName() + "." + ((FileNameExtensionFilter) filter).getExtensions()[0]);
			}
			System.out.println("save current graphic to file " + file.getName() + " using format "
					+ ((FileNameExtensionFilter) filter).getExtensions()[0]);
		}
	}

	/**
	 * Handles the opening of a new drawing from a file.
	 */
	private void doOpen() {
		JFileChooser chooser = new JFileChooser(getClass().getResource("").getFile());
		chooser.setDialogTitle("Open Graphic");
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			@Override
			public String getDescription() {
				return "JDraw Graphic (*.draw)";
			}

			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().endsWith(".draw");
			}
		});
		int res = chooser.showOpenDialog(this);

		if (res == JFileChooser.APPROVE_OPTION) {
			// read jdraw graphic
			System.out.println("read file " + chooser.getSelectedFile().getName());
		}
	}

}
