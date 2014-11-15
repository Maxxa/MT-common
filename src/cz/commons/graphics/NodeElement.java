package cz.commons.graphics;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Basic class for elements with <b>single</b> connection point
 * 
 * @author Martin Šára
 * @author Viktor Krejčíř (refactoring)
 */
public abstract class NodeElement extends Element implements ConnectibleElement {

	private final DoubleProperty centerX = new SimpleDoubleProperty();
	private final DoubleProperty centerY = new SimpleDoubleProperty();

	/**
	 * Create new element on specified location
	 * 
	 * @param x
	 *            location of element
	 * @param y
	 *            location of element
	 * @param width
	 *            of element
	 * @param height
	 *            of element
	 * @param id
	 *            - unique id of element
	 */
	protected NodeElement(int x, int y, int width, int height, int id) {
		this.id = id;
		setTranslateX(x);
		setTranslateY(y);
		doBindings(width, height);
	}

	/**
	 * Create new element moved by x,y from anchor element.
	 * 
	 * @param anchor
	 *            Anchor element used moved from.
	 * @param dx
	 *            moved by on x axis.
	 * @param dy
	 *            moved by on y axis
	 * @param width
	 *            of element
	 * @param height
	 *            of element
	 * @param id
	 *            - unique id of element
	 */
	protected NodeElement(NodeElement anchor, int dx, int dy, int width,
			int height, int id) {
		this.id = id;
		setTranslateX(dx + anchor.getTranslateX());
		setTranslateY(dy + anchor.getTranslateY());
		setAnchorNode(anchor);
		doBindings(width, height);
	}

	/**
	 * Binds center properties to translate properties. Therefore - when
	 * translate property changes (via animation), the centerX and Y changes
	 * too.
	 * 
	 * @param width
	 *            of element
	 * @param height
	 *            of element
	 */
	private void doBindings(int width, int height) {
		centerX.bind(Bindings.add(width / 2, translateXProperty()));
		centerY.bind(Bindings.add(height / 2, translateYProperty()));
	}

	@Override
	public DoubleProperty connectXProperty() {
		return centerX;
	}

	@Override
	public DoubleProperty connectYProperty() {
		return centerY;
	}

	/**
	 * Binding position to anchor element. Therefore when anchor position
	 * changes, position of this element changes too
	 * 
	 * @param anchor
	 *            anchor element
	 */
	protected final void setAnchorNode(NodeElement anchor) {
		// zachovani celych cisel (animace pracuji s realnymi cisly)
		long dx = Math.round(getTranslateX() - anchor.getTranslateX());
		long dy = Math.round(getTranslateY() - anchor.getTranslateY());

		translateXProperty()
				.bind(Bindings.add(dx, anchor.translateXProperty()));
		translateYProperty()
				.bind(Bindings.add(dy, anchor.translateYProperty()));
	}

	/**
	 * Unbinds from anchor.This element makes it no longer dependent on anchor's
	 * position.
	 */
	protected void removeAnchorNode() {
		translateXProperty().unbind();
		translateYProperty().unbind();
	}

}
