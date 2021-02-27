package com.example.gg_assignment.TabViewAdapter;

public class TopAlbums {
    String ImgUrl1,title1,artists1,ImgUrl2,title2,artists2;;

    public TopAlbums(String imgUrl1, String title1, String artists1, String imgUrl2, String title2, String artists2) {
        this.ImgUrl1 = imgUrl1;
        this.title1 = title1;
        this.artists1 = artists1;
        this.ImgUrl2 = imgUrl2;
        this.title2 = title2;
        this.artists2 = artists2;
    }


    public String getImgUrl1() {
        return ImgUrl1;
    }

    public String getTitle1() {
        return title1;
    }

    public String getArtists1() {
        return artists1;
    }

    public String getImgUrl2() {
        return ImgUrl2;
    }

    public String getTitle2() {
        return title2;
    }

    public String getArtists2() {
        return artists2;
    }
}
