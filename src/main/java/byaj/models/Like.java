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
@Table(name = "likeData")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@NotNull
    //@Min(1)
    private int likeID;

    private String likeValue;

    private String likeType;

    private String likeAuthor;
    
    @Column(columnDefinition="integer default -1")
    private int likeUser;

    private Date likeDate=new Date();


    public int getLikeID() {
        return likeID;
    }

    /*public void setMateID(int likeID) {
        this.likeID = likeID;
    }*/
    public String getLikeValue() {
        return likeValue;
    }

    public void setLikeValue (String likeValue) {
        this.likeValue = likeValue;
    }

    public String getLikeType() {
        return likeType;
    }

    public void setLikeType (String likeType) {
        this.likeType = likeType;
    }

    public String getLikeAuthor() {
        return likeAuthor;
    }

    public void setLikeAuthor (String likeAuthor) {
        this.likeAuthor = likeAuthor;
    }

    public int getLikeUser() {
        return likeUser;
    }

    public void setLikeUser (int likeUser) {
        this.likeUser = likeUser;
    }

    public Date getLikeDate() {
        return likeDate;
    }


    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy hh:mm a zzzz", Locale.US);
        return format.format(likeDate);
    }
}
