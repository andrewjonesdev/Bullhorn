package byaj.models;

import byaj.repositories.UserRepository;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

/**
 * Created by student on 6/27/17.
 */
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    //@NotNull
    //@Min(1)
    private int postID;

    @ManyToMany(mappedBy = "likes")
    private Collection<User> users;

    private String postName;

    private String postAuthor;

    @Column(columnDefinition="integer default -1")
    private int postUser;

    @Lob
    @Type(type = "text")
    //@Column(columnDefinition="blob default http://res.cloudinary.com/andrewjonesdev/image/upload/c_fill,h_100,w_100/v1499897311/Empty_xay49d.png")
    private String picUrl;

    @Lob
    @Type(type = "text")
    //@Column(columnDefinition="blob default http://res.cloudinary.com/andrewjonesdev/image/upload/c_fill,h_100,w_100/v1499897311/Empty_xay49d.png")
    private String picOriginUrl;

    private Date postDate=new Date();



    public int getPostID() {
        return postID;
    }

    /*public void setMateID(int postID) {
        this.postID = postID;
    }*/
    public String getPostName() {
        return postName;
    }

    public void setPostName (String postName) {
        this.postName = postName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl (String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicOriginUrl() {
        return picOriginUrl;
    }

    public void setPicOriginUrl (String picOriginUrl) {
        this.picOriginUrl = picOriginUrl;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor (String postAuthor) {
        this.postAuthor = postAuthor;
    }
    
    public int getPostUser() {
        return postUser;
    }

    public void setPostUser (int postUser) {
        this.postUser = postUser;
    }

    public Date getPostDate() {
        return postDate;
    }

    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy hh:mm a zzzz", Locale.US);
        return format.format(postDate);
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public boolean usersContains(User user){
        if(users.contains(user)){
            return true;
        }
        else{
            return false;
        }
    }
}
