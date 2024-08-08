package Dariaus.PostingPortal.controllers;

import Dariaus.PostingPortal.models.Post;
import Dariaus.PostingPortal.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class PostController {

    PostService ps = new PostService();

    @CrossOrigin
    @PostMapping("/createPost")
    public ResponseEntity<String> createPost(@RequestBody Post post) throws SQLException {
        HttpStatus status;
        String s;
        try{
            ps.createPost(post);
            status = HttpStatus.CREATED;
            s = "Successfully posted";
        }catch (NumberFormatException e){
            status = HttpStatus.BAD_REQUEST;
            s = "Bad number, try again";
        }catch (SQLException e){
            status = HttpStatus.NOT_IMPLEMENTED;
            s = "Could not connect to database";
        }
        return new ResponseEntity<>(s, status);
    }

    @CrossOrigin
    @GetMapping("/getPosts")
    public ResponseEntity<List<Post>> getPosts() throws SQLException {
        List<Post> postList = new ArrayList<>();
        HttpStatus status;
        try{
            postList = ps.getPosts();
            status = HttpStatus.OK;
        }catch (SQLException e){
            status = HttpStatus.NOT_IMPLEMENTED;
        }
        return new ResponseEntity<>(postList, status);
    }
    @CrossOrigin
    @GetMapping("/getMorePosts")
    public ResponseEntity<List<Post>> getMorePosts(int id) throws SQLException {
        List<Post> postList = new ArrayList<>();
        HttpStatus status;
        try{
            postList = ps.getMorePosts(id);
            status = HttpStatus.OK;
        }catch (SQLException e){
            status = HttpStatus.NOT_IMPLEMENTED;
        }
        return new ResponseEntity<>(postList, status);
    }
    @CrossOrigin
    @GetMapping("/redirect")
    public RedirectView redirect(UUID uniqueKey) throws SQLException {
        ps.createPostWithPayment(uniqueKey);
        return new RedirectView("http://host.docker.internal/index.html");
    }
}
