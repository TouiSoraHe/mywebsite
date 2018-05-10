package cn.zzy.mywebsite.Controller.TeamplatePageController;

import cn.zzy.mywebsite.Data.Entity.Article;
import cn.zzy.mywebsite.Data.Entity.ArticleInfo;
import cn.zzy.mywebsite.Data.Mapper.ArticleInfoMapper;
import cn.zzy.mywebsite.Data.Mapper.ArticleMapper;
import cn.zzy.mywebsite.Data.ResponseJson;
import cn.zzy.mywebsite.Exception.AssetNotFoundException;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/Blog")
public class BlogController {

    @Autowired
    private ArticleInfoMapper articleInfoMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @RequestMapping(method = RequestMethod.GET)
    public String BlogPage(Model model)
    {
        model.addAttribute("articleInfoMap",GetArticleMap(articleInfoMapper.FindAll()));
        return "Blog";
    }

    @RequestMapping(value = "/{ArticleID}",method = RequestMethod.POST)
    @ResponseBody()
    public ResponseJson GetArticle(@PathVariable("ArticleID")Integer articleID)
    {
        if(articleID == null)
        {
            throw new IllegalArgumentException("ArticleID不能为空");
        }
        Article article = articleMapper.Find(articleID);
        if(article == null){
            throw new AssetNotFoundException("没有找到该博客");
        }
        return ResponseJson.CreateSuccess(article);
    }

    @RequestMapping(value = "/{ArticleID}",method = RequestMethod.GET)
    public String GetArticle(@PathVariable("ArticleID")Integer articleID, Model model)
    {
        if(articleID == null)
        {
            throw new IllegalArgumentException("ArticleID不能为空");
        }
        model.addAttribute("articleInfoMap",GetArticleMap(articleInfoMapper.FindAll()));
        model.addAttribute("article",articleMapper.Find(articleID));
        return "Blog";
    }

    @RequestMapping(value = "/AddBlog",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String AddArticle()
    {
        return "AddBlog";
    }

    @RequestMapping(value = "/AddBlog",method = RequestMethod.POST)
    @ResponseBody()
    @PreAuthorize("isAuthenticated()")
    public ResponseJson AddArticle(String title,String content)
    {
        if(title==null || "".equals(title))
        {
            throw new IllegalArgumentException("博客标题不能为空");
        }
        ArticleInfo articleInfo = new ArticleInfo(title,new Date(),0);
        articleInfoMapper.Add(articleInfo);
        Article article = new Article(title,new Date(),content,articleInfo.getId());
        articleMapper.Add(article);
        articleInfo.setArticleID(article.getId());
        articleInfoMapper.Update(articleInfo);
        return ResponseJson.CreateSuccess(String.valueOf(article.getId()));
    }


    @RequestMapping(value = "/Delete/{ArticleID}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String DeleteArticle(@PathVariable("ArticleID")Integer articleID,Model model)
    {
        if (articleID==null)
        {
            throw new IllegalArgumentException("ArticleID不能为空");
        }
        articleMapper.Delete(articleID);
        articleInfoMapper.Delete(articleID);
        return BlogPage(model);
    }

    @RequestMapping(value = "/Update/{ArticleID}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String UpdateArticle(@PathVariable("ArticleID")Integer articleID,Model model)
    {
        if (articleID == null)
        {
            throw new IllegalArgumentException("ArticleID不能为空");
        }
        Article article = articleMapper.Find(articleID);
        model.addAttribute("article",article);
        return "UpdateBlog";
    }

    @RequestMapping(value = "/Update",method = RequestMethod.POST)
    @ResponseBody()
    @PreAuthorize("isAuthenticated()")
    public ResponseJson UpdateArticle(String title,String content,Integer articleID)
    {
        if (articleID == null)
        {
            throw new IllegalArgumentException("ArticleID不能为空");
        }
        if(title==null || "".equals(title))
        {
            throw new IllegalArgumentException("博客标题不能为空");
        }
        Article article = articleMapper.Find(articleID);
        article.setTitle(title);
        article.setContent(content);
        articleMapper.Update(article);
        ArticleInfo articleInfo = articleInfoMapper.Find(article.getArticleInfoID());
        articleInfo.setTitle(title);
        articleInfoMapper.Update(articleInfo);
        return ResponseJson.CreateSuccess(String.valueOf(article.getId()));
    }

    private HashMap<Integer,HashMap<Integer,List<ArticleInfo>>> GetArticleMap(List<ArticleInfo> articles)
    {
        HashMap<Integer,HashMap<Integer,List<ArticleInfo>>> articleMap = new HashMap<>();
        for (ArticleInfo item:articles) {
            Calendar calendar = new Calendar.Builder().setInstant(item.getTime()).build();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH)+1;
            if(!articleMap.containsKey(year)) {
                articleMap.put(year,new HashMap<>());
            }
            if(!articleMap.get(year).containsKey(month)){
                articleMap.get(year).put(month,new ArrayList<>());
            }
            articleMap.get(year).get(month).add(item);
        }
        return articleMap;
    }
}
