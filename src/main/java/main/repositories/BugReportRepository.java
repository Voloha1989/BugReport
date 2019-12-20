package main.repositories;

import main.entity.EntityBugReport;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface BugReportRepository extends CrudRepository<EntityBugReport, Long> {

    EntityBugReport findById(long id);
    List<EntityBugReport> findAllByIdIsNotNull();
    boolean existsById(long id);
    void deleteById(long id);
}
