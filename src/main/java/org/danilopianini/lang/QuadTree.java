/*******************************************************************************
 * Copyright (C) 2009, 2015, Danilo Pianini and contributors
 * listed in the project's build.gradle or pom.xml file.
 *
 * This file is distributed under the terms of the Apache License, version 2.0
 *******************************************************************************/
package org.danilopianini.lang;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * 
 * @deprecated This class is bugged, should be used by no one, and will be
 *             removed in future versions of this software.
 * 
 * @param <E>
 */
@Deprecated
public class QuadTree<E> implements Serializable {

    private static final long serialVersionUID = -8765593946059102012L;

    private QuadTree<E> botLeft;
    private QuadTree<E> botRight;
    private final Rectangle2D bounds;
    private final List<QuadTreeEntry<E>> elements;
    private final int elems;
    private final double maxX;
    private final double maxY;
    private final double minX;
    private final double minY;
    private QuadTree<E> topLeft;
    private QuadTree<E> topRight;

    private static class QuadTreeEntry<E> implements Serializable {
        private static final long serialVersionUID = 9021533648086596986L;
        private final E element;
        private double x, y;

        QuadTreeEntry(final E el, final double xp, final double yp) {
            element = el;
            x = xp;
            y = yp;
        }

        public String toString() {
            return element.toString();
        }
    }

    /**
     * @param x
     *            minimum x
     * @param y
     *            minimum y
     * @param mx
     *            maximum x
     * @param my
     *            maximum y
     * @param elemPerQuad
     *            maximum number of elements per quad
     */
    public QuadTree(final double x, final double y, final double mx, final double my, final int elemPerQuad) {
        bounds = new Rectangle2D.Double();
        minX = x;
        maxX = mx;
        minY = y;
        maxY = my;
        bounds.setFrameFromDiagonal(minX, minY, maxX, maxY);
        elements = new ArrayList<>(elemPerQuad);
        elems = elemPerQuad;
    }

    private boolean contains(final double x, final double y) {
        return y >= minY && y <= maxY && x >= minX && x <= maxX;
    }

    /**
     * Deletes an element from the QuadTree.
     * 
     * @param e
     *            The element to delete
     * @param x
     *            the x position of the element
     * @param y
     *            the y position of the element
     * @return true if the element is found and removed
     */
    public boolean delete(final E e, final double x, final double y) {
        if (!contains(x, y)) {
            return false;
        }
        if (remove(e, x, y)) {
            return true;
        } else {
            if (topRight.delete(e, x, y)) {
                return true;
            }
            if (topLeft.delete(e, x, y)) {
                return true;
            }
            if (botRight.delete(e, x, y)) {
                return true;
            }
            if (botLeft.delete(e, x, y)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasChildren() {
        return topLeft != null;
    }

    /**
     * Inserts an element in the QuadTree.
     * 
     * @param e
     *            The element to add
     * @param x
     *            the x position of the element
     * @param y
     *            the y position of the element
     * @return true if the element is correctly inserted, false otherwise (e.g.
     *         because the element is out of the indexed space)
     */
    public boolean insert(final E e, final double x, final double y) {
        return insert(new QuadTreeEntry<E>(e, x, y));
    }

    private boolean insert(final QuadTreeEntry<E> e) {
        if (!contains(e.x, e.y)) {
            return false;
        }
        if (set(e)) {
            return true;
        } else {
            subdivide();
            if (topRight.insert(e)) {
                return true;
            }
            if (topLeft.insert(e)) {
                return true;
            }
            if (botRight.insert(e)) {
                return true;
            }
            if (botLeft.insert(e)) {
                return true;
            }
            /*
             * Point on the bounds of the subsets. Force inclusion here.
             */
            elements.add(e);
            return true;
        }
    }

    /**
     * @return the maximum number of elements per node
     */
    public int getMaxElementsNumber() {
        return elems;
    }

    /**
     * If an element is moved, updates the QuadTree accordingly.
     * 
     * @param e
     *            the element
     * @param sx
     *            the start x
     * @param sy
     *            the start y
     * @param fx
     *            the final x
     * @param fy
     *            the final y
     * @return true if the element is found and no error occurred
     */
    public boolean move(final E e, final double sx, final double sy, final double fx, final double fy) {
        if (sx == fx && sy == fy) {
            return true;
        }
        if (!contains(sx, sy)) {
            return false;
        }
        final QuadTree<E> currentContainer = searchForEntry(e, sx, sy);
        final int pos = currentContainer.searchAtThisLevel(e, sx, sy);
        final QuadTreeEntry<E> entry = currentContainer.elements.get(pos);
        entry.x = fx;
        entry.y = fy;
        if (!currentContainer.contains(fx, fy)) {
            currentContainer.elements.remove(pos);
            insert(entry);
        }
        return true;
    }

    /**
     * @param range
     *            the range where to retrieve the objects
     * @return a list of the objects in the range
     */
    public List<E> query(final Rectangle2D range) {
        final List<E> result = new ArrayList<>();
        query(range, result);
        return result;
    }

    private void query(final Rectangle2D range, final List<E> results) {
        if (bounds.intersects(range)) {
            for (final QuadTreeEntry<E> entry : elements) {
                if (range.contains(entry.x, entry.y)) {
                    results.add(entry.element);
                }
            }
            if (hasChildren()) {
                topLeft.query(range, results);
                topRight.query(range, results);
                botLeft.query(range, results);
                botRight.query(range, results);
            }
        }
    }

    @SuppressFBWarnings("FE_FLOATING_POINT_EQUALITY")
    private boolean remove(final E e, final double x, final double y) {
        for (int i = 0; i < elements.size(); i++) {
            final QuadTreeEntry<E> entry = elements.get(i);
            if (entry.x == x && entry.y == y && entry.element.equals(e)) {
                elements.remove(i);
                return true;
            }
        }
        return false;
    }

    @SuppressFBWarnings("FE_FLOATING_POINT_EQUALITY")
    private int searchAtThisLevel(final E e, final double x, final double y) {
        for (int i = 0; i < elements.size(); i++) {
            final QuadTreeEntry<E> entry = elements.get(i);
            if (entry.x == x && entry.y == y && entry.element.equals(e)) {
                return i;
            }
        }
        return -1;
    }

    private QuadTree<E> searchForEntry(final E e, final double sx, final double sy) {
        if (!contains(sx, sy)) {
            return null;
        }
        final int index = searchAtThisLevel(e, sx, sy);
        if (index >= 0) {
            return this;
        } else {
            if (hasChildren()) {
                QuadTree<E> result = topRight.searchForEntry(e, sx, sy);
                if (result == null) {
                    result = topLeft.searchForEntry(e, sx, sy);
                    if (result == null) {
                        result = botRight.searchForEntry(e, sx, sy);
                        if (result == null) {
                            result = botLeft.searchForEntry(e, sx, sy);
                        }
                    }
                }
                return result;
            }
            return null;
        }
    }

    private boolean set(final QuadTreeEntry<E> e) {
        if (elements.size() >= elems) {
            return false;
        }
        elements.add(e);
        return true;
    }

    /**
     * <p>
     * Subdivides this Quadtree into 4 other quadtrees.
     * </p>
     * <p>
     * This is usually used, when this Quadtree already has an Element.
     * </p>
     * 
     * @return whether this Quadtree was subdivided, or didn't subdivide,
     *         because it was already subdivided.
     */
    protected boolean subdivide() {
        if (hasChildren()) {
            return false;
        }
        final double cx = bounds.getCenterX();
        final double cy = bounds.getCenterY();
        topLeft = new QuadTree<E>(minX, cy, cx, maxY, getMaxElementsNumber());
        topRight = new QuadTree<E>(cx, cy, maxX, maxY, getMaxElementsNumber());
        botLeft = new QuadTree<E>(minX, minY, cx, cy, getMaxElementsNumber());
        botRight = new QuadTree<E>(cx, minY, maxX, cy, getMaxElementsNumber());
        return true;
    }

    @Override
    public String toString() {
        return bounds.toString() + ' ' + elements.toString();
    }
}