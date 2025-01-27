package com.community.animal.post.domain;

import static jakarta.persistence.FetchType.*;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.community.animal.post.dto.PostRequest;
import com.community.animal.post.dto.PostResponse;
import com.community.animal.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Post {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@CreatedDate
	private LocalDateTime regDate;

	@Builder
	public Post(String postTitle, String postContent, Long postLike, Long postHit, PostCategory postCategory, User user) {
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.postLike = postLike;
		this.postHit = postHit;
		this.postCategory = postCategory;
		this.user = user;
	}

	public void addPostHit() {
		this.postHit++;
	}

	public void update(PostRequest dto) {
		this.postTitle = dto.getPostTitle() == null ? this.postTitle : dto.getPostTitle();
		this.postContent = dto.getPostContent() == null ? this.postContent : dto.getPostContent();
		this.postCategory = dto.getPostCategory() == null ? this.postCategory : dto.getPostCategory();
	}
}
