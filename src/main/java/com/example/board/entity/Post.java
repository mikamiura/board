//掲示板の投稿データを格納する Post という Bean
//エンティティ パラメーターを取得する場所

package com.example.board.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/** 投稿.*/
@Entity
@Table(name = "posts")
@Data //フィールド変数にアクセサーを記述する必要がなくなる
public class Post {

	/** ID */
	@Id
	@Column
	private String id = null;


	/** 投稿者 */
	@Column(length = 20, nullable = false)
	private String author = null;

	/** タイトル */
	@Column(length = 20, nullable = false)
	private String title = null;

	/** 内容 */
	@Column(length = 1000, nullable = false)
	private String body = null;

	/** 登録日時 */
	private Date createdDate = null;

	/** 更新日時 */
	private Date updatedDate = null;

	/** 削除済 */
	private boolean deleted = false;

}
