package main.interfaces;

import main.entity.EntityBugReport;
import java.util.List;

public interface BugReportDao {

    EntityBugReport findById(long id);
    List<EntityBugReport> findAllByIdIsNotNull();
    void save(EntityBugReport bugReport);
    boolean existsById(long id);
    void deleteById(long id);
}
