package cn.zzy.mywebsite.Data.Mapper;

import cn.zzy.mywebsite.Data.Entity.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ArticleMapper extends IMapperHandle<Article>{

    @Override
    @Insert("INSERT INTO article (title,content,time,articleInfoID,tag) VALUES(#{title}, #{content},#{time},#{articleInfoID},#{tag})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void Add(Article article);

    @Override
    @Delete("DELETE FROM article WHERE id =#{id}")
    void Delete(int id);

    @Override
    @Update("UPDATE article SET title=#{title},content=#{content},time=#{time},articleInfoID=#{articleInfoID},tag=#{tag} WHERE id =#{id}")
    void Update(Article article);

    @Override
    @Select("SELECT * FROM article WHERE id = #{id}")
    Article Find(int id);

    @Override
    @Select("SELECT * FROM article")
    List<Article> FindAll();
}
