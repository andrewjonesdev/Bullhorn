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
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@NotNull
    //@Min(1)
    private int followID;

    private String followValue;

    private String followType;

    private String followAuthor;
    
    @Column(columnDefinition="integer default -1")
    private int followUser;

    private Date followDate=new Date();


    public int getFollowID() {
        return followID;
    }

    /*public void setMateID(int followID) {
        this.followID = followID;
    }*/
    public String getFollowValue() {
        return followValue;
    }

    public void setFollowValue (String followValue) {
        this.followValue = followValue;
    }

    public String getFollowType() {
        return followType;
    }

    public void setFollowType (String followType) {
        this.followType = followType;
    }

    public String getFollowAuthor() {
        return followAuthor;
    }

    public void setFollowAuthor (String followAuthor) {
        this.followAuthor = followAuthor;
    }

    public int getFollowUser() {
        return followUser;
    }

    public void setFollowUser (int followUser) {
        this.followUser = followUser;
    }

    public Date getFollowDate() {
        return followDate;
    }


    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy hh:mm a zzzz", Locale.US);
        return format.format(followDate);
    }
}
