# FigShare Java API

A Java API to interface with the FigShare API.

Currently supporting the following operations:

* list articles
* create article
* upload file

## Basic usage

    String clientKey = "1234";
    String clientSecret = "1234";
    String tokenKey = "1234";
    String tokenSecret = "1234";

    List<Article> articles = FigShareClient.to("http://api.figshare.com/", 
            1, 
            clientKey, 
            clientSecret, 
            tokenKey, 
            tokenSecret)
        .articles()
    ;
    System.out.println(articles);

Should print something similar to:

    {"filtered_by": "permissions", "count": 1, "items": [{"article_id": 123456789, "title": "Viol\u00e3o", "master_publisher_id": 0, "defined_type": "figure", "status": "Private", "version": 1, "published_date": "09:27, Jun 03, 2015", "description": "<p>A test guitar</p>", "description_nohtml": "A test guitar", "total_size": "116.88 KB", "authors": [{"first_name": "Bruno", "last_name": "Kinoshita", "id": 99999, "full_name": "Bruno Kinoshita"}], "tags": [{"id": 123, "name": "guitar"}], "categories": [{"id": 456, "name": "Instrumentation, Techniques, and Astronomical Observations"}], "files": [{"size": "120 KB", "thumb": "http://figshare.com/read/private/543/12455.jpg", "id": 789, "mime_type": "image/jpeg", "name": "IMG-201503111.jpg"}], "links": [{"link": "http://google.com", "id": 123}, {"link": "http://github.com", "id": 456}]}]}

## Create an article

