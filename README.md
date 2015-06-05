# FigShare Java API

A Java API to interface with the FigShare API.

Currently supporting the following operations:

* Retrieve all articles
* Create an article
* Upload a file to an article

## Basic usage

    String clientKey = "1234";
    String clientSecret = "1234";
    String tokenKey = "1234";
    String tokenSecret = "1234";

    FigShareClient figshare = FigShareClient.to(
            "http://api.figshare.com/", 
            1, 
            clientKey, 
            clientSecret, 
            tokenKey, 
            tokenSecret)
    ;

## Retrieve all articles

    List<Article> articles = figshare.articles();
    System.out.println(articles);

Should print something similar to:

    [Article [articleId=123456789, title=Violão, masterPublisherId=0, definedType=figure, status=Private, version=1, publishedDate=09:27, Jun 03, 2015, description=<p>A test guitar</p>, descriptionNohtml=A test guitar, totalSize=116.88 KB, authors=[Author [firstName=Bruno, lastName=Kinoshita, id=123, fullName=Bruno Kinoshita]], tags=[Tag [id=456, name=guitar]], files=[File [size=120 KB, thumb=http://figshare.com/read/private/23232/3232.jpg, id=567, mimeType=image/jpeg, name=IMG-87878-WA0002.jpg]], links=[Link [id=124, link=http://google.com], Link [id=5454, link=http://github.com]]]]

## Create an article

    String clientKey = "1234";
    String clientSecret = "1234";
    String tokenKey = "1234";
    String tokenSecret = "1234";

    Article article = figshare.createArticle(
            "Test_" + Thread.currentThread().getName() + "_" + System.nanoTime(), 
            "A test article...", 
            "dataset");
    System.out.println(article);

Should print something similar to:

    Article [articleId=1234, title=Test_main_6851933204498, masterPublisherId=null, definedType=dataset, status=Drafts, version=1, publishedDate=06:25, May 31, 2015, description=A test article..., descriptionNohtml=A test article..., totalSize=false, authors=[Author [firstName=Bruno, lastName=Kinoshita, id=2131, fullName=Bruno Kinoshita]], tags=[], categories=[], files=[], links=null, doi=http://dx.doi.org/3234/m9.figshare.1323181, downloads=0, owners=null, shares=0, views=0]

## Upload a file to an article

    File attachment = new File("/home/kinow/Desktop/images/gDv6mJS.gif");
    org.biouno.figshare.v1.model.File uploaded = figshare.uploadFile(article.getArticleId(), attachment);
    System.out.println(uploaded);

Should print something similar to:

    File [size=1.90 MB, thumb=null, id=31432, mimeType=image/gif, name=gDv6mJS.gif]
    