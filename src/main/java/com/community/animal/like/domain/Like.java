package com.community.animal.like.domain;

import static jakarta.persistence.FetchType.*;

import com.community.animal.post.domain.Post;
import com.community.animal.user.domain.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@Builder
	public Like(User user, Post post) {
		this.user = user;
		this.post = post;
	}
}
