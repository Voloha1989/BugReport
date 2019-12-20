package main.interfaces;

import main.entity.EntityReportCorrections;
import java.util.List;

public interface ReportCorrectionsDao {

    EntityReportCorrections findById(long id);
    List<EntityReportCorrections> findAllByIdIsNotNull();
    void save(EntityReportCorrections reportCorrections);
    List<EntityReportCorrections> findAllByBugId(long bugId);
    boolean existsById(long id);
    void deleteById(long id);
    void deleteAllByBugId(long bugId);
}
