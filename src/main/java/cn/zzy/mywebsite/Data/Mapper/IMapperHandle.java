package cn.zzy.mywebsite.Data.Mapper;

import java.util.List;

public interface IMapperHandle<T> {

    public void Add(T t);

    public void Delete(int id);

    public void Update(T t);

    public T Find(int id);

    public List<T> FindAll();
}
