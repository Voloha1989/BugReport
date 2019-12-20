package main.implementation;

import main.entity.EntityBugReport;
import main.interfaces.BugReportDao;
import main.repositories.BugReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.util.List;

@Service("jpaBugReport")
@Transactional
@Repository
public class BugReportImpl implements BugReportDao {

    private final BugReportRepository repository;

    @Autowired
    public BugReportImpl(BugReportRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityBugReport findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<EntityBugReport> findAllByIdIsNotNull() {
        return repository.findAllByIdIsNotNull();
    }

    @Override
    public void save(EntityBugReport bugReport) {
        repository.save(bugReport);
    }


    @Override
    public boolean existsById(long id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
