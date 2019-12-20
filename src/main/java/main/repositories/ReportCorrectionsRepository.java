package main.repositories;

import main.entity.EntityReportCorrections;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ReportCorrectionsRepository extends CrudRepository<EntityReportCorrections, Long> {

    EntityReportCorrections findById(long id);
    List<EntityReportCorrections> findAllByIdIsNotNull();
    List<EntityReportCorrections> findAllByBugId(long bugId);
    boolean existsById(long id);
    void deleteById(long id);
    void deleteAllByBugId(long bugId);
}
