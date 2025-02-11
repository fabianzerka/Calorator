package com.calorator.repository.impl;

import com.calorator.entity.FoodEntryEntity;
import com.calorator.repository.FoodEntryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class FoodEntryRepositoryImpl implements FoodEntryRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public void save(FoodEntryEntity foodEntry) {
        em.persist(foodEntry);
    }

    @Override
    public FoodEntryEntity findById(Long id) {
        return em.find(FoodEntryEntity.class, id);
    }

    @Override
    public List<FoodEntryEntity> findFoodEntriesLast7Days(long userId) {
        try {
            LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
            return em.createQuery(
                            "SELECT f FROM FoodEntryEntity f WHERE f.user.id = :userId AND f.entryDate >= :sevenDaysAgo",
                            FoodEntryEntity.class
                    )
                    .setParameter("userId", userId)
                    .setParameter("sevenDaysAgo", sevenDaysAgo)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching food entries from the last 7 days.", e);
        }
    }

    @Override
    public Long countFoodEntriesLast7Days() {
        try {
            LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
            return em.createQuery(
                            "SELECT COUNT(f) FROM FoodEntryEntity f WHERE f.entryDate >= :sevenDaysAgo",
                            Long.class
                    )
                    .setParameter("sevenDaysAgo", sevenDaysAgo)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while counting food entries from the last 7 days.", e);
        }
    }

    @Override
    public void update(FoodEntryEntity foodEntry) {
        em.merge(foodEntry);
    }

    @Override
    public List<FoodEntryEntity> findAll() {
        return em.createQuery("SELECT f FROM FoodEntryEntity f", FoodEntryEntity.class)
                .getResultList();
    }

    @Override
    public void delete(Long id) {
        em.remove(findById(id));
    }

    @Override
    public BigDecimal calculateMonthlySpending(Long userId, int month, int year) {
        try {
            String sql = "SELECT COALESCE(SUM(price), 0) FROM food_entries " +
                    "WHERE user_id = :userId AND MONTH(entry_date) = :month AND YEAR(entry_date) = :year";

            return (BigDecimal) em.createNativeQuery(sql)
                    .setParameter("userId", userId)
                    .setParameter("month", month)
                    .setParameter("year", year)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while calculating monthly spending.", e);
        }
    }

    @Override
    public List<FoodEntryEntity> entryDateFiltering(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        try {
            return em.createQuery(
                            "SELECT f FROM FoodEntryEntity f WHERE f.user.id = :userId AND f.entryDate BETWEEN :startDate AND :endDate",
                            FoodEntryEntity.class
                    )
                    .setParameter("userId", userId)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching food entries by date interval.", e);
        }
    }
}