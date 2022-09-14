package org.bist.activitydiagram;

/**Class for path of transition
 * @param <X> coordinate
 * @param <Y> coordinate
 */
public class Path<X,Y> {
    public X item1;
    public Y item2;

    public Path(X item1, Y item2) {
        this.item1 = item1;
        this.item2 = item2;
    }
}
