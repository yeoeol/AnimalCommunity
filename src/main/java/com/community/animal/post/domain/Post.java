package com.community.animal.post.domain;

import static jakarta.persistence.FetchType.*;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.community.animal.post.dto.PostRequest;
import com.community.animal.post.dto.PostResponse;
import com.community.animal.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Post {

	@Id @GeneratedValue
	@Column(name = "post_id")
	private Long postId;

	private String postTitle;
	private String postContent;

	private Long postLike;
	private Long postHit;

	// private Image postImage;
	@Enumerated(value = EnumType.STRING)
	private PostCategory postCategory;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@CreationTimestamp
	private LocalDateTime regDate;

	@Builder
	public Post(Long postId, String postTitle, String postContent, Long postLike, Long postHit,
		PostCategory postCategory, User user, LocalDateTime regDate) {

		this.postId = postId;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.postLike = postLike;
		this.postHit = postHit;
		this.postCategory = postCategory;
		this.user = user;
		this.regDate = regDate;
	}

	// public void addUser(User user) {
	// 	this.user = user;
	// }
}
