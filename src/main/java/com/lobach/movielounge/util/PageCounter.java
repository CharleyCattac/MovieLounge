package com.lobach.movielounge.util;

public class PageCounter {
    private PageCounter() {}

    public static int countPages(int listSize, int itemsPerPage) {
        int pages = listSize/itemsPerPage;
        if (listSize % itemsPerPage != 0) {
            pages++;
        }
        return pages;
    }
}
