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
public class PostBuilder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@NotNull
    //@Min(1)
    private int postBuilderID;

    private String postBuilderValue;

    private String postBuilderAuthor;
    
    @Column(columnDefinition="integer default -1")
    private int postBuilderUser;

    private Date postBuilderDate=new Date();


    public int getPostBuilderID() {
        return postBuilderID;
    }

    /*public void setMateID(int postBuilderID) {
        this.postBuilderID = postBuilderID;
    }*/
    public String getPostBuilderValue() {
        return postBuilderValue;
    }

    public void setPostBuilderValue (String postBuilderValue) {
        this.postBuilderValue = postBuilderValue;
    }

    public String getPostBuilderAuthor() {
        return postBuilderAuthor;
    }

    public void setPostBuilderAuthor (String postBuilderAuthor) {
        this.postBuilderAuthor = postBuilderAuthor;
    }
    
    public int getPostBuilderUser() {
        return postBuilderUser;
    }

    public void setPostBuilderUser (int postBuilderUser) {
        this.postBuilderUser = postBuilderUser;
    }

    public Date getPostBuilderDate() {
        return postBuilderDate;
    }


    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy hh:mm a zzzz", Locale.US);
        return format.format(postBuilderDate);
    }
}
