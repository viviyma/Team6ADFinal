package iss.team6.web.services;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import iss.team6.web.models.NewsDTO;

@Service
public class ScraperServiceImpl implements ScraperService{
	
	String url = "https://www.google.com.sg/search?q=recycling&sxsrf=ALiCzsag0DxKiIKf7g-52LfyV8gpPyy8MQ:1660477101475&source=lnms&tbm=nws&sa=X&ved=2ahUKEwjb4qmen8b5AhXU7TgGHR1eCUsQ_AUoA3oECAIQBQ&biw=1280&bih=577&dpr=1.5";
    @Override
    public Set<NewsDTO> getNews() {
        //Using a set here to only store unique elements
        Set<NewsDTO> responseDTOS = new HashSet<>();
        
        Document document;
        try {
            document = Jsoup.connect(url).get();
            
            //Elements elements_title = document.getElementsByClass("mCBkyc y355M ynAwRc MBeuO nDgy9d");
            Elements elements_link = document.getElementsByClass("WlydOe");
         
       
            for (Element ele: elements_link) {
                NewsDTO responseDTO = new NewsDTO();
                responseDTO.setTitle(ele.getElementsByClass("mCBkyc y355M ynAwRc MBeuO nDgy9d").text());
                responseDTO.setDescription(ele.getElementsByClass("GI74Re nDgy9d").text());
                responseDTO.setUrl(ele.attr("href"));
                responseDTOS.add(responseDTO);
            }
        
        /*for (int i=0; i<responseDTOS.size(); i++) {
            ResponseDTOS(responseDTO)
            for(ResponseDTO obj: responseDTOS) {
                obj.setUrl(ele.attr("href"));
            }*/
            
        
        
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



       return responseDTOS;
    }

}
