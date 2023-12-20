package com.team10.backoffice.domain.post.repository;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team10.backoffice.domain.post.entity.Post;
import com.team10.backoffice.domain.post.entity.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public List< Post > findOrderByContentLengthDesc() {
		QPost post = new QPost( "post" );
		return jpaQueryFactory.selectFrom( post ).orderBy( post.content.length().desc() ).fetch();
	}

	public Page< Post > search( int coutPerPage, Pageable pageable ) {
		QPost post = new QPost( "post" );
		var query = jpaQueryFactory.select( post )
				.from( post )
				.offset( pageable.getOffset() )
				.limit( pageable.getPageSize() );

		var posts = query.fetch();
		long totalSize = jpaQueryFactory.select( Wildcard.count )
				.from( post )
				.fetch().get( 0 );

		var ret = PageableExecutionUtils.getPage( posts, pageable, () -> totalSize );

		return ret;
	}
}
