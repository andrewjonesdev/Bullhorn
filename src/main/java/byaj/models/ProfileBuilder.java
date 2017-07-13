package byaj.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by student on 6/27/17.
 */
@Entity
public class ProfileBuilder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@NotNull
    //@Min(1)
    private int profileBuilderID;

    private String profileBuilderValue;

    private String profileBuilderAuthor;
    
    @Column(columnDefinition="integer default -1")
    private int profileBuilderUser;

    private Date profileBuilderDate=new Date();


    public int getProfileBuilderID() {
        return profileBuilderID;
    }

    /*public void setMateID(int profileBuilderID) {
        this.profileBuilderID = profileBuilderID;
    }*/
    public String getProfileBuilderValue() {
        return profileBuilderValue;
    }

    public void setProfileBuilderValue (String profileBuilderValue) {
        this.profileBuilderValue = profileBuilderValue;
    }

    public String getProfileBuilderAuthor() {
        return profileBuilderAuthor;
    }

    public void setProfileBuilderAuthor (String profileBuilderAuthor) {
        this.profileBuilderAuthor = profileBuilderAuthor;
    }
    
    public int getProfileBuilderUser() {
        return profileBuilderUser;
    }

    public void setProfileBuilderUser (int profileBuilderUser) {
        this.profileBuilderUser = profileBuilderUser;
    }

    public Date getProfileBuilderDate() {
        return profileBuilderDate;
    }


    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy hh:mm a zzzz", Locale.US);
        return format.format(profileBuilderDate);
    }
}
