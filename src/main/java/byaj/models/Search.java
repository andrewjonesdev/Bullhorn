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
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@NotNull
    //@Min(1)
    private int searchID;

    private String searchValue;

    private String searchType;

    private String searchAuthor;
    
    @Column(columnDefinition="integer default -1")
    private int searchUser;

    private Date searchDate=new Date();


    public int getSearchID() {
        return searchID;
    }

    /*public void setMateID(int searchID) {
        this.searchID = searchID;
    }*/
    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue (String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType (String searchType) {
        this.searchType = searchType;
    }

    public String getSearchAuthor() {
        return searchAuthor;
    }

    public void setSearchAuthor (String searchAuthor) {
        this.searchAuthor = searchAuthor;
    }

    public int getSearchUser() {
        return searchUser;
    }

    public void setSearchUser (int searchUser) {
        this.searchUser = searchUser;
    }

    public Date getSearchDate() {
        return searchDate;
    }


    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy hh:mm a zzzz", Locale.US);
        return format.format(searchDate);
    }
}
