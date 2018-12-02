package io.hsar.Assembler.model;

import io.hsar.Assembler.sites.SiteUtils;

/**
 * @author HSAR
 *
 *         Represents a story which has not been fetched yet.
 */
public class StorySkeleton implements Comparable<StorySkeleton> {

    private String title;
    private String url;
    private int ID;
    private String author;
    private Story story = null;

    public StorySkeleton(String title, String author, String url) {
        super();
        this.title = title;
        this.author = author;
        this.url = url;
        ID = getIDfromURL(url);
    }

    public String getTitle() {
        return title;
    }

    public String getSeries() {
        return title.split(": ")[0];
    }

    public String getAuthor() {
        return author;
    }

    /**
     * @param url - Valid story url
     * @return Story ID as int, -1 if parse failed.
     */
    public static int getIDfromURL(String url) {
        try {
            String[] segments = url.split("/");
            String idStr = segments[segments.length - 1];
            return Integer.parseInt(idStr);
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }

    public int getID() {
        return ID;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public Story getStory() {
        return story;
    }

    public String getURLString() {
        return url;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ID;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StorySkeleton other = (StorySkeleton) obj;
        if (ID != other.ID)
            return false;
        return true;
    }

    @Override
    public int compareTo(StorySkeleton o) {
        if (o instanceof StorySkeleton) {
            return this.ID - o.getID();
        } else {
            return 0;
        }
    }

    Story fetch() {
        if (story == null) {
            story = SiteUtils.getFromURL(url).getStory(this);
        }
        return story;
    }

}
