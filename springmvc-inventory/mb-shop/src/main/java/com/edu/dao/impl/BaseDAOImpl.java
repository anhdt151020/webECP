package com.edu.dao.impl;

import com.edu.dao.BaseDAO;
import com.edu.model.Paging;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
@Transactional(rollbackFor = Exception.class)
public class BaseDAOImpl<E> implements BaseDAO<E> {
    final static Logger log = Logger.getLogger(BaseDAOImpl.class);
    @Autowired
    SessionFactory mySessionFactory;

    @Override
    public List<E> findAll() {
        StringBuilder queryString = new StringBuilder("");
        queryString.append(" from ").append(getGenericName()).append(" as model where model.activeFlag>=1");

        log.info("[Query::find all records] " + queryString.toString());
        List<E> listRS = mySessionFactory.getCurrentSession().createQuery(queryString.toString()).list();
        return CollectionUtils.isEmpty(listRS) ? new ArrayList<>() : listRS;
    }

    @Override
    public List<E> findAll(String sql, Map<String, Object> mapParams, Paging paging) {
        StringBuilder queryString = new StringBuilder("");
        queryString.append(" from ").append(getGenericName()).append(" as model where model.activeFlag>=1");

        StringBuilder queryString2 = new StringBuilder();
        queryString2.append(" select count(*) from ").append(getGenericName()).append(" as model where model.activeFlag>=1");

        if (sql != null && !sql.isEmpty()) {
            queryString.append(sql);
            queryString2.append(sql);
        }
        Query<E> query = mySessionFactory.getCurrentSession().createQuery(queryString.toString());
        Query<E> query2 = mySessionFactory.getCurrentSession().createQuery(queryString2.toString());
        if (mapParams != null && !mapParams.isEmpty()) {
            for (String key: mapParams.keySet()) {
                query.setParameter(key, mapParams.get(key));
                query2.setParameter(key, mapParams.get(key));
            }
        }
        if (paging != null) {
            query.setFirstResult(paging.getOffset());
            query.setMaxResults(paging.getRecordPerPage());
            long totalRecords = (long) query2.uniqueResult();
            paging.setTotalRecord(totalRecords);
        }

        log.info("[Query::find all records, by mapParams, pageable] " + queryString.toString());
        List<E> listRS = query.list();
        return CollectionUtils.isEmpty(listRS) ? new ArrayList<>() : listRS;
    }

    @Override
    public E findById(Class<E> e, Serializable id) {

        log.info("[Query::find record by id] ");
        return mySessionFactory.getCurrentSession().get(e, id); //else null
    }

    @Override
    public List<E> findByProperty(String prop, Object val) {
        StringBuilder queryString = new StringBuilder("");
        queryString.append(" from ").append(getGenericName()).append(" as model where model.activeFlag>=1");
        queryString.append(" and model.").append(prop).append(" =:value") ;
        Query<E> query = mySessionFactory.getCurrentSession().createQuery(queryString.toString());
        query.setParameter("value", val);

        log.info("[Query::find record by property] " + queryString.toString());
        List<E> listRS = query.getResultList();
        return CollectionUtils.isEmpty(listRS) ? new ArrayList<>() : listRS;
    }

    @Override
    public void save(E instance) {

        log.info("[Query::insert record] ");
        mySessionFactory.getCurrentSession().persist(instance);
    }

    @Override
    public void update(E instance) {

        log.info("[Query::update record] ");
        mySessionFactory.getCurrentSession().merge(instance);
    }

    public String getGenericName() {
        String s = getClass().getGenericSuperclass().toString();
        Pattern pattern = Pattern.compile("\\<(.*?)\\>");
        Matcher m = pattern.matcher(s);
        String generic = "null";
        if (m.find()) {
            generic = m.group(1);
        }
        return generic;
    }
}
