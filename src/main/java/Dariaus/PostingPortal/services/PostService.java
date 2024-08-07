package Dariaus.PostingPortal.services;

import Dariaus.PostingPortal.models.Post;
import Dariaus.PostingPortal.repositories.PostRepository;

import java.sql.SQLException;
import java.util.List;

public class PostService {

    PostRepository pr = new PostRepository();

    public void createPost(Post post) throws SQLException {
        if(post.getNumber().length() == 9 || post.getNumber().length() == 12){
            pr.createPost(post);
        }
        else{
            throw new NumberFormatException();
        }
    }
    public List<Post> getPosts() throws SQLException {
        return pr.getPosts();
    }
}
