package com.ercancelik.questapp.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ercancelik.questapp.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByUserId(Long userId); // farkettiysen her postservice katmanındaki metodu buraya almadık çünkü bazıları zaten jpa da hazırdır
										  //  hazır olmayanları ( yani kendi belirlediklerimizi) buraya tanımlamalıyız fakat onunda bir kuralı
										  // var mesela  metod ismi abcuserId olsaydı tanımlasakda olmazdı farkettiysen  metodu parçalarsak
										  // findBy-UserId yani findById zaten  jpa da hazır idi  biz sonuna Id değilde UserId yazmışısız
	// o halde  UserId yi jpa ya ifade ederiz bunuda parametrede  belirterek sana verilen userId ye göre findBy metodunu kullancaksın demişisiz

	@Query(value = "select id from post where user_id = :userId order by create_date desc limit 5", 
			nativeQuery = true)
	List<Long> findTopByUserId(@Param("userId") Long userId);
	
}
