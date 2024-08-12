package Dariaus.PostingPortal.repositories;

import Dariaus.PostingPortal.models.Post;
import Dariaus.PostingPortal.utils.Connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostRepository {

    public void createPost(Post post) throws SQLException {
        PreparedStatement ps = Connect.SQLConnection("INSERT INTO posts (title, content, number, created_at) VALUES (?,?,?,?)");
        ps.setString(1, post.getTitle());
        ps.setString(2, post.getContent());
        ps.setString(3, post.getNumber());
        ps.setString(4, String.valueOf(LocalDateTime.now()));
        ps.execute();
    }
    public List<Post> getPosts() throws SQLException {
        List<Post> postList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        PreparedStatement ps = Connect.SQLConnection("SELECT * FROM posts ORDER BY id DESC LIMIT 20");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Post post = new Post(rs.getInt("id"), rs.getString("title"), rs.getString("content"),
                    rs.getString("number"), LocalDateTime.parse(rs.getString("created_at"), formatter));
            postList.add(post);
        }
        return postList;
    }
    public List<Post> getMorePosts(int id) throws SQLException {
        List<Post> postList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        PreparedStatement ps = Connect.SQLConnection("SELECT * FROM posts WHERE id < ? ORDER BY id DESC LIMIT 20");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Post post = new Post(rs.getInt("id"), rs.getString("title"), rs.getString("content"),
                    rs.getString("number"), LocalDateTime.parse(rs.getString("created_at"), formatter));
            postList.add(post);
        }
        return postList;
    }
    public static void pendingPost(String title, String number, String content, UUID uniqueKey) throws SQLException {
        PreparedStatement ps = Connect.SQLConnection("INSERT INTO pending_posts (title, number, content, uid) VALUES (?,?,?,?)");
        ps.setString(1, title);
        ps.setString(2, number);
        ps.setString(3, content);
        ps.setString(4, String.valueOf(uniqueKey));
        ps.execute();
    }
    public void createPostWithPayment(UUID uniqueKey) throws SQLException {
        PreparedStatement psRetrievePost = Connect.SQLConnection("SELECT * FROM pending_posts WHERE uid = ?");
        psRetrievePost.setString(1, String.valueOf(uniqueKey));
        ResultSet rs = psRetrievePost.executeQuery();
        if(rs.next()){
            PreparedStatement ps = Connect.SQLConnection("INSERT INTO posts (title, content, number, created_at) VALUES (?,?,?,?)");
            ps.setString(1, rs.getString("title"));
            ps.setString(2, rs.getString("content"));
            ps.setString(3, rs.getString("number"));
            ps.setString(4, String.valueOf(LocalDateTime.now()));
            ps.execute();
        }
    }
    public static void removePending(UUID uniqueKey) throws SQLException {
        PreparedStatement ps = Connect.SQLConnection("DELETE FROM pending_posts WHERE uid = ?");
        ps.setString(1, String.valueOf(uniqueKey));
        ps.execute();
    }
}
