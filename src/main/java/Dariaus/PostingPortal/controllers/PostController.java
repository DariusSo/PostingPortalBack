package Dariaus.PostingPortal.controllers;

import Dariaus.PostingPortal.models.Post;
import Dariaus.PostingPortal.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            status = HttpStatus.NOT_IMPLEMENTED;
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
}
