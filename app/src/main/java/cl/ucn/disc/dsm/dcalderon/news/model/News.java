/*
 * Copyright Staday.year Diego.Calderon.Carvajal, diego.calderon@alumnos.ucn.cl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cl.ucn.disc.dsm.dcalderon.news.model;

import org.threeten.bp.ZonedDateTime;

/**
 * The Domain model: News
 *
 * @author Diego Calderon-Carvajal
 */
public class News {
    /**
     * Unique id
     */
    private Long id;

    /**
     * The Title.
     * Restrictions: not null, size > 2.
     */
    private String title;

    /**
     * The Sources.
     */
    private String sources;

    /**
     * The Author.
     */
    private String author;

    /**
     * The URL.
     */
    private String url;

    /**
     * The URL of image.
     */
    private String urlImage;

    /**
     * The Description.
     */
    private String description;

    /**
     * The Content.
     */
    private String content;

    /**
     * The Date of publish.
     */
    private ZonedDateTime publishedAt;

    public News(Long id, String title, String sources, String author, String url, String urlImage,
                String description, String content, ZonedDateTime publishedAt) {
        this.id = id;
        this.title = title;
        this.sources = sources;
        this.author = author;
        this.url = url;
        this.urlImage = urlImage;
        this.description = description;
        this.content = content;
        this.publishedAt = publishedAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSources() {
        return sources;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public ZonedDateTime getPublishedAt() {
        return publishedAt;
    }

    public void printNews(){
        System.out.println(getId() + "\n" + getTitle() + "\n" + getAuthor() + "\n" + getContent()
                + "\n" + getDescription() + "\n" + getSources() + "\n" + getUrl()
                + "\n" + getUrlImage() + "\n" + getPublishedAt());
    }
}
