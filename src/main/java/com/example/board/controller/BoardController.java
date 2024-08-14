package com.example.board.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.board.entity.Post;
import com.example.board.factory.PostFactory;
import com.example.board.repository.PostRepository;

/**
 * 掲示板のフロントコントローラー.
 */
@Controller
public class BoardController {
	@Autowired
	private PostRepository repository;

	/**
	* 一覧を表示する。
	*
	* @param model モデル
	* @return テンプレート
	*/
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("form", PostFactory.newPost());
		model = this.setList(model);
		model.addAttribute("path", "create");
		return "layout";
	}

	/**
	 * 登録する。
	 *
	 * @param form  フォーム
	 * @param model モデル
	 * @return テンプレート
	*/
	@PostMapping("/create") //create メソッドで新規投稿の処理を行う
	public String create(@ModelAttribute("form") @Validated Post form, BindingResult result, Model model) {
		if (result.hasErrors()) {//!が付いてる為エラーがでない場合を判定
			model = this.setList(model);// エラーがない場合の処理、投稿の保存処理を記述
			model.addAttribute("path", "create");
			return "layout";
		} else {
			//PostRepository#saveAndFlashメソッドの呼び出しでformに格納された投稿内容を保存
			repository.saveAndFlush(PostFactory.createPost(form));
			return "redirect:/";//登録後は登録した内容を含む一覧を表示するためにindexメソッドにリダイレクトする。
		}
	}

	/**
	       * 一覧を設定する。
	       *
	       * @param model モデル
	       * @return 一覧を設定したモデル
	       */
	//setListメソッドはAutowired アノテーションで PostRepositoryを注入してfindAllメソッドで投稿一覧を取得している
	private Model setList(Model model) {
		List<Post> list = repository.findAll();
		model.addAttribute("list", list);
		return model;
	}

	/**
	 * 編集する投稿を表示する
	 *
	 * @param form  フォーム
	 * @param model モデル
	 * @return テンプレート
	 */
	@GetMapping("/edit") //editメソッドは編集する投稿を PostRepository#findById メソッドで取り出す
	public String edit(@ModelAttribute("form") Post form, Model model) {
		Optional<Post> post = repository.findById(form.getId());
		model.addAttribute("form", post);
		model = setList(model);
		model.addAttribute("path", "update");
		return "layout";
	}

	/**
	 * 更新する
	 *
	 * @param form  フォーム
	 * @param model モデル
	 * @return テンプレート
	 */
	@PostMapping("/update") //updateメソッドは編集する投稿を PostRepository#findByIdメソッドで取り出す
	//form に格納された投稿の値を PostFactory#updatePost メソッドで上書きしてから
	//PostRepository#saveAndFlash メソッドを呼び出して保存
	public String update(@ModelAttribute("form") @Validated Post form, BindingResult result, Model model) {
		Optional<Post> post = repository.findById(form.getId());
		if (result.hasErrors()) {
			model.addAttribute("form", form);
			model = this.setList(model);
			model.addAttribute("path", "update");
			return "layout";
		} else {
			repository.saveAndFlush(PostFactory.updatePost(post.get(), form));
			return "redirect:/";//最後にindexメソッドにリダイレクトして投稿一覧を表示
		}
	}

	/**
	* 削除する
	*
	* @param form  フォーム
	* @param model モデル
	* @return テンプレート
	*/
	@GetMapping(value = "/delete")
	// delete メソッドは削除する投稿を PostRepository#findById メソッドで取り出し
	//PostFactory#deletePost メソッドで Post#setDeleted(true) で
	//「削除済」を設定してから PostRepository#saveAndFlash メソッドを呼び出して保存
	public String delete(@ModelAttribute("form") Post form, Model model) {
		Optional<Post> post = repository.findById(form.getId());
		repository.saveAndFlush(PostFactory.deletePost(post.get()));
		return "redirect:/";//最後に indexメソッドにリダイレクトして投稿一覧を表示
	}
}
