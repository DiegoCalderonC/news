/*
 * Copyright $taday.year Diego.Calderon.Carvajal, diego.calderon@alumnos.ucn.cl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cl.ucn.disc.dsm.dcalderon.news.services;

import com.kwabenaberko.newsapilib.models.Article;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cl.ucn.disc.dsm.dcalderon.news.model.News;
import cl.ucn.disc.dsm.dcalderon.news.model.Validation;

public class ContractsImplNewsApi implements Contracts {
    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(ContractsImplNewsApi.class);

    /**
     * The connection to NewsApi.
     */
    private final NewsApiService newsApiService;

     /**
      * The Constructor.
      *
      * @param theApiKey to use.
      */
     public ContractsImplNewsApi(final String theApiKey) {
         Validation.minSize(theApiKey, 10 ,  "ApiKey !!");
         this   .newsApiService = new   NewsApiService(theApiKey);
     }
     /**
      * The Assembler/Transformer pattern!
      *
      * @param article used to source.
      * @return the News.
      */
     private static News toNews(final Article article) {
         Validation.notNull(article, "Article null !?!");

         // Warning message?
         boolean needFix = false;

         // Fix the author null : (
         if  (article.getAuthor() == null   ) {
             article.setAuthor("No author*");
             needFix = true;
         }

         // Fix more restrictions :(
         if  (article.getDescription() == null    || article.getDescription().length() == 0) {
             article.setDescription("No description*");
             needFix = true   ;
         }

         // .. yes, warning message.
         if  (needFix) {
             // Debug of Article
             log.warn("Article with invalid restrictions: {}.", ToStringBuilder.reflectionToString(
                     article, ToStringStyle.MULTI_LINE_STYLE            ));
         }

         // The date
         ZonedDateTime publishedAt = ZonedDateTime
                 .parse(article.getPublishedAt())
                 .withZoneSameInstant(ZoneId.of("-3"   ));

         if(article.getAuthor() == null || article.getAuthor().length() == 0){
             log.warn("Article without author !!");
             return null;
         }
         if(article.getDescription() == null){
             log.warn("Description without author !!");
             return null;
         }

         // The News
         return new   News(
                 article.getTitle(),
                 article.getSource().getName(),
                 article.getAuthor(),
                 article.getUrl(),
                 article.getUrlToImage(),
                 article.getDescription(),
                 article.getDescription(), // FIXME: Where is the content?
                 publishedAt
         );
     }

    /**
     * Filter the stream.
     *
     * @param idExtractor
     * @param <T> news to filter
     * @return true if the news already exist.
     */
    private static <T> Predicate<T> distinctById(Function<? super T, ?> idExtractor){
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(idExtractor.apply(t), Boolean.TRUE) == null;
    }

     /**
      * Get the list of News.
      *
      * @param size of the list.
      * @return the List of News.
      */
     @Override
     public List<News> retrieveNews(final Integer size) {
         try   {
             // Request to NewsApi
             List<Article> articles = newsApiService.getTopHeadlines(
                     "general", size
             );

             // The List of Articles to List of News
             List<News> news = new   ArrayList<>();

             for   (Article article : articles) {
                 // log.debug("Article: {}", ToStringBuilder.reflectionToString(article, ToStringStyle.MULTI_LINE_STYLE));
                 news.add(toNews(article));
             }
             return news.stream()
                     // Remote the duplicates (by id).
                     .filter(distinctById(News::getId))
                     // Sort the stream by publishedAt
                     .sorted((k1, k2) -> k2.getPublishedAt().compareTo(k1.getPublishedAt()))
                     // Retrun the stream to lista
                     .collect(Collectors.toList());

         } catch (IOException ex) {
             // Encapsulate!
             throw  new RuntimeException(ex);
         }
     }

     /**
      * Save one News into the System.
      *
      * @param news to save.
      */
     @Override
     public void    saveNews(final News news) {
         throw new NotImplementedException("Can't save in NewsAPI");
     }
}