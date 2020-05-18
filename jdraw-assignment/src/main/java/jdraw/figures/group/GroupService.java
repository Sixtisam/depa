package jdraw.figures.group;

import java.util.ArrayList;
import java.util.List;

import jdraw.commands.GroupCommand;
import jdraw.commands.UngroupCommand;
import jdraw.figures.decorators.AbstractFigureDecorator;
import jdraw.framework.DrawModel;
import jdraw.framework.Figure;
import jdraw.framework.FigureGroup;

public class GroupService {

	public static FigureGroup group(DrawModel model, List<Figure> figures) {
		figures.forEach(s -> model.removeFigure(s));
		Group group = new Group(figures);
		model.addFigure(group);
		model.getDrawCommandHandler().addCommand(new GroupCommand(model, group, model.getFigureIndex(group)));
		return group;
	}

	public static FigureGroup group(DrawModel model, FigureGroup group, int index) {
		group.getFigureParts().forEach(s -> model.removeFigure(s));
		model.addFigure(group);
		model.setFigureIndex(group, index);
		model.getDrawCommandHandler().addCommand(new GroupCommand(model, group, index));
		return group;
	}

	public static List<Figure> ungroup(DrawModel model, FigureGroup group) {
		AbstractFigureDecorator dec = (AbstractFigureDecorator) group.getOuter();
		int index;
		if (dec != null) {
			index = model.getFigureIndex(dec);
			model.removeFigure(dec);
			dec.setInner(null);
			group.getFigureParts()
					.forEach(fp -> {
						AbstractFigureDecorator newDec = (AbstractFigureDecorator) dec.clone();
						newDec.setInner(fp);
						model.addFigure(newDec);
					});
		} else {
			index = model.getFigureIndex(group);
			model.removeFigure(group);
			group.getFigureParts().forEach(fp -> model.addFigure(fp));
		}
		List<Figure> ungroupedFigures = new ArrayList<>();
		group.getFigureParts().forEach(ungroupedFigures::add);
		model.getDrawCommandHandler().addCommand(new UngroupCommand(model, group, index));
		return ungroupedFigures;
	}
}
