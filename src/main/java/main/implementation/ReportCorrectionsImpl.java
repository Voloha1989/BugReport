package main.implementation;

import main.entity.EntityReportCorrections;
import main.interfaces.ReportCorrectionsDao;
import main.repositories.ReportCorrectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.util.List;

@Service("jpaReportCorrections")
@Transactional
@Repository
public class ReportCorrectionsImpl implements ReportCorrectionsDao {

    private final ReportCorrectionsRepository repository;

    @Autowired
    public ReportCorrectionsImpl(ReportCorrectionsRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityReportCorrections findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<EntityReportCorrections> findAllByIdIsNotNull() {
        return repository.findAllByIdIsNotNull();
    }

    @Override
    public void save(EntityReportCorrections reportCorrections) {
        repository.save(reportCorrections);
    }

    @Override
    public List<EntityReportCorrections> findAllByBugId(long bugId) {
        return repository.findAllByBugId(bugId);
    }

    @Override
    public boolean existsById(long id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByBugId(long bugId) {
        repository.deleteAllByBugId(bugId);
    }
}
