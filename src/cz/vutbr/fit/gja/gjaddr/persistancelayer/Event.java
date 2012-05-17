package cz.vutbr.fit.gja.gjaddr.persistancelayer;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.util.EventsEnum;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.LoggerFactory;

/**
 * Class for one date record.
 *
 * @author Bc. Radek Gajdusek <xgajdu07@stud.fit.vutbr.cz>
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class Event implements Serializable {

    static private final long serialVersionUID = 6L;
    private EventsEnum type;
    private Date date;
    private Integer yearShowingDisabled;

    /**
     * No-args constructor. Sets everything to NULL.
     */
    public Event() {
        this.type = null;
        this.date = null;
        this.yearShowingDisabled = null;
    }

    /**
     * Constructor. Sets type and date.
     *
     * @param type
     * @param date
     */
    public Event(EventsEnum type, Date date) {
        this.type = type;
        this.date = date;
        this.yearShowingDisabled = null;
    }

    /**
     * Get date of event.
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set day of event.
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Get event type.
     *
     * @return
     */
    public EventsEnum getType() {
        return type;
    }

    /**
     * Set event type.
     *
     * @param type
     */
    public void setType(EventsEnum type) {
        this.type = type;
    }

    /**
     * Is showing of this event disabled?
     *
     * @return
     */
    public boolean isShowingDisabled() {
        return this.yearShowingDisabled != null
                && this.yearShowingDisabled == Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * Get year when showing is disabled.
     *
     * @return
     */
    public Integer getYearShowingDisabled() {
        return yearShowingDisabled;
    }

    /**
     * Set year when showing is disabled.
     *
     * @param yearShowingDisabled
     */
    public void setYearShowingDisabled(Integer yearShowingDisabled) {
        this.yearShowingDisabled = yearShowingDisabled;
    }

    /**
     * Set year when showing is disabled to current year.
     */
    public void setYearShowingDisabled() {
        this.setYearShowingDisabled(Calendar.getInstance().get(Calendar.YEAR));
    }
    
    /**
     * Enable showing of this event.
     */
    public void setShowingEnabled() {
        this.yearShowingDisabled = null;
    }

    /**
     * Is this event birthday?
     *
     * @return
     */
    public boolean isBirthday() {
        return this.getType().equals(EventsEnum.BIRTHDAY);
    }

    /**
     * Is this event name day?
     *
     * @return
     */
    public boolean isNameDay() {
        return this.getType().equals(EventsEnum.NAMEDAY);
    }

    /**
     * Is this event celebration?
     *
     * @return
     */
    public boolean isCelebration() {
        return this.getType().equals(EventsEnum.CELEBRATION);
    }

    /**
     * Is this event other?
     *
     * @return
     */
    public boolean isOther() {
        return this.getType().equals(EventsEnum.OTHER);
    }

    /**
     * Is the event today?
     *
     * @return
     */
    public boolean isToday() {
        Calendar today = Calendar.getInstance();
        Calendar check = Calendar.getInstance();
        check.setTime(this.date);
        if (today.get(Calendar.MONTH) == check.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) == check.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }
        return false;
    }
    
    /**
     * Is this event happening within one month?
     * 
     * @return 
     */
    public boolean shouldBeNotified() {
        // is showing disabled?
        if (this.isShowingDisabled()) {
            return false;
        }
        // date today
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, -1);
		// date within one month
		Calendar month = Calendar.getInstance();
        switch (Settings.instance().getNotificationsSettings()) {
            case MONTH:
                month.add(Calendar.DAY_OF_YEAR, +30);
                break;
            case WEEK:
                month.add(Calendar.DAY_OF_YEAR, +7);
                break;
            case DAY:
                month.add(Calendar.DAY_OF_YEAR, +1);
                break;
            default:
                return false;
        }
		
		// birthday of contact
		Calendar bday = Calendar.getInstance();
        bday.setTime(this.date);
        bday.set(Calendar.YEAR, today.get(Calendar.YEAR));
        if (bday.after(today) && bday.before(month)) {
            return true;
        }
        return false;
    }

    /**
     * Check equal. Classes are equal if dates are equal.
     *
     * @param obj
     * @return
     */
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

    /**
     * Method prints type and date as string.
     *
     * @return
     */
    @Override
    public String toString() {
        return "Dates{" + "type=" + type + ", date=" + date + "}";
    }
}
