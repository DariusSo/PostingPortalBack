package Dariaus.PostingPortal.services;

import Dariaus.PostingPortal.models.Post;
import Dariaus.PostingPortal.repositories.PostRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

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
    public List<Post> getMorePosts(int id) throws SQLException {
        return pr.getMorePosts(id);
    }
    public void createPostWithPayment(UUID uniqueKey) throws SQLException {
        pr.createPostWithPayment(uniqueKey);
    }
}
