package cn.zzy.mywebsite.Controller.TeamplatePageController;

import cn.zzy.mywebsite.Data.Entity.Article;
import cn.zzy.mywebsite.Data.Entity.ArticleInfo;
import cn.zzy.mywebsite.Data.Mapper.ArticleInfoMapper;
import cn.zzy.mywebsite.Data.Mapper.ArticleMapper;
import cn.zzy.mywebsite.Data.ResponseJson;
import cn.zzy.mywebsite.Exception.AssetNotFoundException;
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
    public ResponseJson AddArticle(Article article)
    {
        if(article == null)
        {
            throw new IllegalArgumentException("参数Article为空");
        }
        else if(article.getTitle()==null || "".equals(article.getTitle()))
        {
            throw new IllegalArgumentException("博客标题不能为空");
        }
        article.setTime(new Date());
        ArticleInfo articleInfo = new ArticleInfo(article);
        articleInfoMapper.Add(articleInfo);
        article.setArticleInfoID(articleInfo.getId());
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
        if(article!=null)
        {
            model.addAttribute("article",article);
            model.addAttribute("tagList",GetTagList(article.getTag()));
        }
        return "UpdateBlog";
    }

    @RequestMapping(value = "/Update",method = RequestMethod.POST)
    @ResponseBody()
    @PreAuthorize("isAuthenticated()")
    public ResponseJson UpdateArticle(Article article)
    {
        if(article==null)
        {
            throw new IllegalArgumentException("参数Article为空");
        }
        if(article.getTitle()==null || "".equals(article.getTitle()))
        {
            throw new IllegalArgumentException("博客标题不能为空");
        }
        Article newArticle = articleMapper.Find(article.getId());
        if(newArticle==null)
        {
            throw new AssetNotFoundException("没有找到该博客,articleID:"+article.getId());
        }
        newArticle.setTitle(article.getTitle());
        newArticle.setContent(article.getContent());
        newArticle.setTag(article.getTag());
        articleMapper.Update(newArticle);
        ArticleInfo articleInfo = articleInfoMapper.Find(newArticle.getArticleInfoID());
        articleInfo.setTitle(article.getTitle());
        articleInfo.setTag(article.getTag());
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

    private List<String> GetTagList(String tagStr)
    {
        List<String> tagList = new ArrayList<>();
        if(tagStr!=null && !"".equals(tagStr))
        {
            for (String item : tagStr.split("\\|"))
            {
                tagList.add(item);
            }
        }
        return tagList;
    }
}
