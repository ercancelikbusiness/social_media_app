package com.ercancelik.questapp.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ercancelik.questapp.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	// aslında aşağıdaki And li metod  bu anlama gelir :
		//SELECT c FROM Comment c WHERE c.user.id = :userId AND c.post.id = :postId
		
		/*Spring Data JPA, method isimlerinde sadece And değil, başka anahtar kelimeleri de tanır:

			Anahtar Kelime	Anlamı (SQL Karşılığı)	Örnek
			And	AND	findByNameAndAge
			Or	OR	findByNameOrAge
			Between	BETWEEN	findByAgeBetween(int min, int max)
			LessThan	<	findByAgeLessThan(int age)
			Like	LIKE	findByNameLike(String name)
			In	IN	findByIdIn(List<Long> ids)
			IsNull	IS NULL	findByDeletedDateIsNull()
			*/
	
	
	List<Comment> findByUserIdAndPostId(Long userId, Long postId);

	List<Comment> findByUserId(Long userId);

	List<Comment> findByPostId(Long postId);
	
	@Query(value = "select 'commented on', c.post_id, u.avatar, u.user_name from "
			+ "comment c left join user u on u.id = c.user_id "
			+ "where c.post_id in :postIds limit 5", nativeQuery = true)
	List<Object> findUserCommentsByPostId(@Param("postIds") List<Long> postIds);
	
	

}
