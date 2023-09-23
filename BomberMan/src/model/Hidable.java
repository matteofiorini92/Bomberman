package model;

/**
 * this interface has the sole purpose of marking objects as Hidable (e.g. power ups and the exit).
 * it doesn't have methods as the classes that implements it have a passive role (being hidden)
 * it's used by methods of the Hiding interface to identify what objects can be hidden by an Hiding object
 * @author Matteo
 *
 */
public interface Hidable {}
