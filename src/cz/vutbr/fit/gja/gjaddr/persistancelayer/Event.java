
package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.EventsEnum;
import java.io.Serializable;
import java.util.Date;

/**
 * Class for one date record.
 *
 * @author Bc. Radek Gajdusek <xgajdu07@stud.fit.vutbr.cz>
 */
public class Event implements Serializable {
	
  static private final long serialVersionUID = 6L;
	
	private EventsEnum type;
  private Date date;

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public EventsEnum getType() {
    return type;
  }

  public void setType(EventsEnum type) {
    this.type = type;
  }

  public Event(EventsEnum type, Date date) {
    this.type = type;
    this.date = date;
  } 

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Event other = (Event) obj;
    if (this.type != other.type) {
      return false;
    }
    if (this.date != other.date && (this.date == null || !this.date.equals(other.date))) {
      return false;
    }
    return true;
  }  
  
  @Override
  public String toString() {
    return "Dates{" + "type=" + type + ", date=" + date + '}';
  }  
}
