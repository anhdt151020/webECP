package com.edu.hutech.dao.impl;

import com.edu.hutech.dao.INewDAO;
import com.edu.hutech.mapper.impl.NewMapper;
import com.edu.hutech.model.NewModel;
import com.edu.hutech.paging.Pageble;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class NewDAO extends AbstractDAO<NewModel> implements INewDAO {


    @Override
    public List<NewModel> findByCategoryId(Long categoryId) {
        String sql = "SELECT * FROM news WHERE categoryid = ?";
        return query(sql, new NewMapper(), categoryId);
    }

    @Override
    public List<NewModel> findAll(Pageble pageble) {
        StringBuilder sql = new StringBuilder("SELECT * FROM news");
        if (pageble.getSorter() != null &&
                StringUtils.isNotBlank(pageble.getSorter().getSortName()) &&
                StringUtils.isNotBlank(pageble.getSorter().getSortBy())   ) {
            sql.append(" ORDER BY " + pageble.getSorter().getSortName() + " " + pageble.getSorter().getSortBy()+"");
        }
        if (pageble.getOffset() != null && pageble.getLimit() != null) {
            sql.append(" LIMIT " + pageble.getOffset() + ", " + pageble.getLimit()+"");
        }
        return query(sql.toString(), new NewMapper());
    }

    @Override
    public NewModel findOne(Long id) {
        String sql = "SELECT * FROM news WHERE id = ?";
        List<NewModel> news = query(sql, new NewMapper(), id);

        return (news.isEmpty() == true) ? null : news.get(0);
    }

    @Override
    public Long save(NewModel newModel) {
        StringBuilder sql = new StringBuilder("INSERT INTO news (title, thumbnail, shortdescription, content, ");
        sql.append("categoryid, createddate, createdby) VALUES (?,?,?,?,?,?,?)");
        return  insert(sql.toString(), newModel.getTitle(), newModel.getThumbnail(),
                newModel.getShortDescription(), newModel.getContent(),
                newModel.getCategoryId(), newModel.getCreatedDate(), newModel.getCreatedBy());
    }

    @Override
    public void update(NewModel updateNew) {
        StringBuilder sql = new StringBuilder("UPDATE news SET ");
        sql.append("title = ?, thumbnail = ?, shortdescription = ?, content = ?, categoryid = ?, ");
        sql.append("createddate = ?, createdby = ?, modifieddate = ?, modifiedby = ? ");
        sql.append("WHERE id = ?");
        update(sql.toString(), updateNew.getTitle(), updateNew.getThumbnail(),
                updateNew.getShortDescription(), updateNew.getContent(),updateNew.getCategoryId(),
                updateNew.getCreatedDate(), updateNew.getCreatedBy(),
                updateNew.getModifiedDate(), updateNew.getModifiedBy(), updateNew.getId());
    }

    @Override
    public void delete(long id) {
        /*user - comment - news, ta phải xóa data comment trước thì user & news mới xóa được */
        String sql = "DELETE FROM news WHERE id = ?";
        update(sql, id);
    }

    @Override
    public int getTotalItem() {
        String sql = "SELECT count(*) FROM news";
        return count(sql);
    }

}
