package com.kubernetes.api.model;

public enum DeletionPropagation {
    /*
     * Orphans the dependents.
     */
    ORPHAN("Orphan"),
    /*
     * Deletes the object from the key-value store, the garbage collector will
     * delete the dependents in the background.
     */
    BACKGROUND("Background"),
    /*
     * The object exists in the key-value store until the garbage collector
     * deletes all the dependents whose ownerReference.blockOwnerDeletion=true
     * from the key-value store.  API sever will put the "foregroundDeletion"
     * finalizer on the object, and sets its deletionTimestamp.  This policy is
     * cascading, i.e., the dependents will be deleted with Foreground.
     */
    FOREGROUND("Foreground");
    private final String value;
    DeletionPropagation(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return value;
    }
}
