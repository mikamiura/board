//Post のインスタンスを生成する PostFactory

//PostFactory は新規投稿、投稿の編集、投稿の削除といったパターンでの投稿のインスタンスを生成する
//Factory Method というデザインパターンで設計されたクラス
package com.example.board.factory;

import java.util.Date;
import java.util.UUID;

import com.example.board.entity.Post;

/**
 * 投稿のファクトリークラス.
 */
 public class PostFactory {

    /** 非公開 */
    private PostFactory() {
    }

    /**
     * 新規の投稿を生成する
     *
     * @return 新規の投稿
     */
    public static Post newPost() {
        Post post = new Post();     ////PostクラスnewPostメソッドでpostオブジェクト生成.
        return post;  ////生成したpostを返す
    }

    /**
     * 入力内容を設定した投稿を生成する
     *
     * @param post 投稿
     * @return 投稿
     */
    public static Post createPost(Post post) { //?投稿したもの
        String id = UUID.randomUUID().toString();  ////文字列idにUUIDクラスのメソッドでユニークなidを作る
        post.setId(id);         ////postのsetIdに上のidを格納
        Date current = new Date();
        post.setCreatedDate(current); ////?  currentオブジェクトをどうする？
        post.setUpdatedDate(current);
        return post;           ////postに格納したものをPostクラスへ返す
    }

    /**
     * 更新内容を設定した投稿を生成する
     *
     * @param post 投稿
     * @return 投稿
     */
    public static Post updatePost(Post post, Post form) {
        post.setAuthor(form.getAuthor());
        post.setTitle(form.getTitle());
        post.setBody(form.getBody());
        Date current = new Date();
        post.setUpdatedDate(current);
        return post;
    }
    /**
     * 削除内容を設定した投稿を生成する
     *
     * @param post 投稿
     * @return 投稿
     */
    public static Post deletePost(Post post) {
        post.setDeleted(true);
        Date current = new Date();
        post.setUpdatedDate(current);
        return post;
    }

 }