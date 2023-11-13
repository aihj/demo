package zerobase23.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase23.demo.domain.Web;


@Repository
public interface WebRepository extends JpaRepository<Web, Integer> {


}
