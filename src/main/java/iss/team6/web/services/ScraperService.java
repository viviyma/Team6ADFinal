package iss.team6.web.services;

import java.util.Set;

import iss.team6.web.models.NewsDTO;

public interface ScraperService {
    Set<NewsDTO> getNews();
}
