package nl.knaw.dans.farm.rdb;

import java.io.Serializable;

import org.joda.time.DateTime;

public interface Entity extends Serializable
{

     Long getId();

     /**
      * Gets the creation date of this entity or <code>null</code> if this entity is not persisted.
      *
      * @return the creation date of this entity
      */
     DateTime getRecordCreationDate();
     
     DateTime getRecordLastModified();

     boolean isPersisted();

     boolean isTransient();

}