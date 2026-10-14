package net.nco.sklad.services;

/**
 *
 * @param <T>
 */
public interface DocumentRecalculate<T extends CommittableDocument> {

    public Class<T> getSupportedClass();

    public void recalculate(T document, boolean isCommittable);
}
