package Dariaus.PostingPortal.controllers;

import Dariaus.PostingPortal.models.Post;
import Dariaus.PostingPortal.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class PostController {

    PostService ps = new PostService();

    @CrossOrigin
    @PostMapping("/createPost")
    public void createPost(@RequestBody Post post) throws SQLException {
        ps.createPost(post);
    }

    @CrossOrigin
    @GetMapping("/getPosts")
    public List<Post> getPosts() throws SQLException {
        return ps.getPosts();
    }
}
