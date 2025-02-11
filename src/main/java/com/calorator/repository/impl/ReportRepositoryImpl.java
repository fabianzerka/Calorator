package com.calorator.repository.impl;

import com.calorator.entity.ReportEntity;
import com.calorator.repository.ReportRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReportRepositoryImpl implements ReportRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(ReportEntity reportEntity) {
        em.persist(reportEntity);
    }

    @Override
    public ReportEntity findById(Long id) {
        return em.find(ReportEntity.class, id);
    }

    @Override
    public List<ReportEntity> findAll() {
        return em.createQuery("SELECT r FROM ReportEntity r", ReportEntity.class)
                .getResultList();
    }


    @Override
    public void update(ReportEntity reportEntity) {
        em.merge(reportEntity);
    }

    @Override
    public ReportEntity findByReportDate(String reportDate) {
        try {
            return em.createQuery("SELECT r FROM ReportEntity r WHERE r.reportDate = :reportDate", ReportEntity.class)
                    .setParameter("reportDate", reportDate)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        ReportEntity report = findById(id);
        if (report != null) {
            em.remove(report);
        }
    }




}
