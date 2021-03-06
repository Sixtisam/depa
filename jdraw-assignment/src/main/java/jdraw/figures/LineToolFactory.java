package jdraw.figures;

import jdraw.figures.line.LineTool;
import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawToolFactory;

public class LineToolFactory implements DrawToolFactory {
	private String name;
	private String iconName;

	@Override
	public String getIconName() {
		return iconName;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public DrawTool createTool(DrawContext context) {
		return new LineTool(context);
	}
}
