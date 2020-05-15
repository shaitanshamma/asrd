package com.kropotov.asrd.repositories;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

//@RequiredArgsConstructor
public class HistoryHibernateEnversRepository<T, ID> implements HistoryRepository<T, ID> {

    @Autowired
    private EntityManager entityManager;

//    private EntityInformation<T, ID> entityInformation;

    @Override

    public List<T> getHistoryById(Class<T> entityType, ID id) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

//        Class<T> entityType = this.entityInformation.getJavaType();

        List results = auditReader.createQuery().forRevisionsOfEntityWithChanges(entityType, false)
                .add(AuditEntity.id().eq(id))
                .getResultList();

        //Class c = Employee.class;

        Object previousEntity = null;
        for (Object row : results) {
            Object[] rowArray = (Object[]) row;
            final T entity = (T) rowArray[0];
            final RevisionType revisionType = (RevisionType) rowArray[2];
            final Set<String> propertiesChanged = (Set<String>) rowArray[3];
            for (String propertyName : propertiesChanged) {
                try {
                    Field f = entityType.getDeclaredField(propertyName);
                    f.setAccessible(true);
                    System.out.println(propertyName + " : " + f.get(entity));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                // using the property name here you know
                // 1. that the property changed in this revision (no compare needed)
                // 2. Can get old/new values easily from previousEntity and entity
            }
        }
        return null;
    }
}
