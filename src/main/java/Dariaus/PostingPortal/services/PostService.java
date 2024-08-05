package Dariaus.PostingPortal.services;

import Dariaus.PostingPortal.models.Post;
import Dariaus.PostingPortal.repositories.PostRepository;

import java.sql.SQLException;
import java.util.List;

public class PostService {

    PostRepository pr = new PostRepository();

    public void createPost(Post post) throws SQLException {
        pr.createPost(post);
    }
    public List<Post> getPosts() throws SQLException {
        return pr.getPosts();
    }
}
