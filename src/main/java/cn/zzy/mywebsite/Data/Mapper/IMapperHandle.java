package cn.zzy.mywebsite.Data.Mapper;

import cn.zzy.mywebsite.Data.Entity.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IMapperHandle<T> {

    public void Add(T t);

    public void Delete(int id);

    public void Update(T t);

    public T Find(int id);

    public List<T> FindAll();
}
