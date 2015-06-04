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

    [Article [articleId=123456789, title=Violão, masterPublisherId=0, definedType=figure, status=Private, version=1, publishedDate=09:27, Jun 03, 2015, description=<p>A test guitar</p>, descriptionNohtml=A test guitar, totalSize=116.88 KB, authors=[Author [firstName=Bruno, lastName=Kinoshita, id=123, fullName=Bruno Kinoshita]], tags=[Tag [id=456, name=guitar]], files=[File [size=120 KB, thumb=http://figshare.com/read/private/23232/3232.jpg, id=567, mimeType=image/jpeg, name=IMG-87878-WA0002.jpg]], links=[Link [id=124, link=http://google.com], Link [id=5454, link=http://github.com]]]]


## Create an article

